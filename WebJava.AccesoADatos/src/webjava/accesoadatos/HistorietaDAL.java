package webjava.accesoadatos;

import java.util.*;
import java.sql.*;
import webjava.entidadesdenegocio.*;
import java.time.LocalDate;

public class HistorietaDAL {
    static String obtenerCampos() {
        return "h.id, h.autor, h.categorias, h.nombre, h.descripcion, h.precio, h.precioanterior, h.link, h.editorial, h.edicion, h.fechapublicacion, h.cantidad, h.imagen";
    }
    
    private static String obtenerSelect(Historieta pHistorietas) {
        String sql;
        sql = "SELECT ";
        if (pHistorietas.getTop_aux() > 0 && ComunBD.TIPODB == ComunBD.TipoDB.SQLSERVER) {            
            sql += "TOP" + pHistorietas.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM historietas h");
        return sql;
    }
    
    private static String agregarOrderBy(Historieta pHistorietas) {
        String sql = " ORDER BY r.Id DESC";
        if (pHistorietas.getTop_aux() > 0 && ComunBD.TIPODB == ComunBD.TipoDB.MYSQL) {
            sql += " LIMIT " + pHistorietas.getTop_aux() + " ";
        }
        return sql;
    }
    
    public static int crear(Historieta pHistorietas) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunBD.obtenerConexion();) { 
            sql = "INSERT INTO rol(autor, nombre, descripcion, precio, precioanterior, link, editorial, edicion, fechapublicacion, cantidad, imagen)";
            try (PreparedStatement ps = ComunBD.createPreparedStatement(conn, sql);) {
                ps.setString(1, pHistorietas.getAutor());
                ps.setString(2, pHistorietas.getNombre());
                ps.setString(3, pHistorietas.getDescripcion());
                ps.setString(4, pHistorietas.getPrecio());
                ps.setString(5, pHistorietas.getPrecioanterior());
                ps.setString(6, pHistorietas.getLink());
                
                result = ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return result;
    }
    
    public static int modificar(Historieta pHistorietas) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunBD.obtenerConexion();) {
            sql = "UPDATE Rol SET Nombre=? WHERE Id=?";
            try (PreparedStatement ps = ComunBD.createPreparedStatement(conn, sql);) {
                ps.setString(1, pHistorietas.getNombre());
                ps.setInt(2, pHistorietas.getId());
                result = ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return result;
    }
    
     public static int eliminar(Historieta pHistorietas) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunBD.obtenerConexion();) {
            sql = "DELETE FROM Rol WHERE Id=?";
            try (PreparedStatement ps = ComunBD.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pHistorietas.getId());
                result = ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return result;
    } 
    
    static int asignarDatosResultSet(Historieta pHistorietas, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pHistorietas.setId(pResultSet.getInt(pIndex));
        pIndex++;
        pHistorietas.setNombre(pResultSet.getString(pIndex));
        return pIndex;
    }
    
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Historieta> pHistorietas) throws Exception {
        try (ResultSet resultSet = ComunBD.obtenerResultSet(pPS);) {
            while (resultSet.next()) {
                Historieta historieta = new Historieta(); 
                asignarDatosResultSet(historieta, resultSet, 0);
                pHistorietas.add(historieta);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
     public static Historieta obtenerPorId(Historieta pHistorietas) throws Exception {
        Historieta historieta = new Historieta();
        ArrayList<Historieta> historias = new ArrayList();
        try (Connection conn = ComunBD.obtenerConexion();) { 
            String sql = obtenerSelect(pHistorietas);
            sql += " WHERE r.Id=?";
            try (PreparedStatement ps = ComunBD.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pHistorietas.getId());
                obtenerDatos(ps, historias);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        
        if (historias.size() > 0) {
            historieta = historias.get(0);
        }
        
        return historieta;
    }
    
    public static ArrayList<Historieta> obtenerTodos() throws Exception {
        ArrayList<Historieta> historias = new ArrayList<>();
        try (Connection conn = ComunBD.obtenerConexion();) {
            String sql = obtenerSelect(new Historieta());
            sql += agregarOrderBy(new Historieta());
            try (PreparedStatement ps = ComunBD.createPreparedStatement(conn, sql);) {
                obtenerDatos(ps, historias);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } 
        catch (SQLException ex) {
            throw ex;
        }
        
        return historias;
    }
    
    static void querySelect(Historieta pHistorietas, ComunBD.utilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pHistorietas.getId() > 0) {
            pUtilQuery.AgregarNumWhere(" r.Id=? ");
            if (statement != null) { 
                statement.setInt(pUtilQuery.getNumWhere(), pHistorietas.getId()); 
            }
        }

        if (pHistorietas.getNombre() != null && pHistorietas.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" r.Nombre LIKE ? "); 
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pHistorietas.getNombre() + "%"); 
            }
        }
    }
    
    public static ArrayList<Historieta> buscar(Historieta pHistorietas) throws Exception {
        ArrayList<Historieta> historia = new ArrayList();
        try (Connection conn = ComunBD.obtenerConexion();) {
            String sql = obtenerSelect(pHistorietas);
            ComunBD comundb = new ComunBD();
            ComunBD.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0); 
            querySelect(pHistorietas, utilQuery);
            sql = utilQuery.getSQL(); 
            sql += agregarOrderBy(pHistorietas);
            try (PreparedStatement ps = ComunBD.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0); 
                querySelect(pHistorietas, utilQuery);
                obtenerDatos(ps, historia);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return historia;
    }
}
