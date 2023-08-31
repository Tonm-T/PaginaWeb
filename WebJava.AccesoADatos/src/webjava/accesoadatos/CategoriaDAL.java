package webjava.accesoadatos;

import java.util.*;
import java.sql.*;
import webjava.entidadesdenegocio.*;

public class CategoriaDAL {
     static String obtenerCampos() {
    return "c.id, c.nombre, c.clasificacion";
    }
    
    private static String obtenerSelect (Categoria pCategorias) {
    String sql;
    sql = "SELECT";
    if (pCategorias.getTop_aux() > 0 && ComunBD.TIPODB == ComunBD.TipoDB.SQLSERVER) {
    sql += "TOP" + pCategorias.getTop_aux() + " ";
    }
    sql += (obtenerCampos() + " FROM categorias c");
        return sql;
  }
    private static String agregarOrderBy (Categoria pCategorias) {
    String sql = "ORDER BY c.id DESC";
    if (pCategorias.getTop_aux() > 0 && ComunBD.TIPODB == ComunBD.TipoDB.SQLSERVER) {
    sql += "LIMIT" + pCategorias.getTop_aux() + " ";
    }
    return sql;
    }
    
    public static int crear (Categoria pCategorias) throws Exception {
    int result = 0;
    String sql;
    try (Connection conn = ComunBD.obtenerConexion();) {
    sql = "INSERT INTO categorias (nombre, clasificacion)";
    try (PreparedStatement ps = ComunBD.createPreparedStatement(conn, sql);) {
    ps.setString(1, pCategorias.getNombre());
    ps.setString(2, pCategorias.getClasificacion());
       } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return result;
    }
    
    public static int modificar (Categoria pCategorias) throws Exception {
    int result ;
    String sql;
    try (Connection conn = ComunBD.obtenerConexion();) {
    sql = "UPDATE categorias SET nombre=?, clasificacion=?";
    try (PreparedStatement ps = ComunBD.createPreparedStatement(conn, sql);) {
    ps.setString(1, pCategorias.getNombre());
    ps.setString(2, pCategorias.getClasificacion());
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
    
    public static int eliminar (Categoria pCategorias) throws Exception {
    int result;
    String sql;
    try (Connection conn = ComunBD.obtenerConexion();) {
    sql = "DELETE FROM categorias WHERE id=?";
    try (PreparedStatement ps = ComunBD.createPreparedStatement(conn, sql);) {
    ps.setInt(1, pCategorias.getId());
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
    
      static int asignarDatosResultSet(Categoria pCategorias, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pCategorias.setId(pResultSet.getInt(pIndex));
        pIndex++;
        pCategorias.setNombre(pResultSet.getString(pIndex));
        return pIndex;
    }
      
      private static void obtenerDatos(PreparedStatement pPS, ArrayList<Categoria> pCategorias) throws Exception {
        try (ResultSet resultSet = ComunBD.obtenerResultSet(pPS);) {
            while (resultSet.next()) {
                Categoria categoria = new Categoria(); 
                asignarDatosResultSet(categoria, resultSet, 0);
                pCategorias.add(categoria);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    public static Categoria obtenerPorId(Categoria PCategorias) throws Exception {
        Categoria Categoria = new Categoria();
        ArrayList<Categoria> categorias = new ArrayList();
        try (Connection conn = ComunBD.obtenerConexion();) { 
            String sql = obtenerSelect(PCategorias);
            sql += " WHERE r.Id=?";
            try (PreparedStatement ps = ComunBD.createPreparedStatement(conn, sql);) {
                ps.setInt(1, PCategorias.getId());
                obtenerDatos(ps, categorias);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        
        if (categorias.size() > 0) {
            Categoria = categorias.get(0);
        }
        
        return Categoria;
    }
    
     static void querySelect(Categoria pCategorias, ComunBD.utilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pCategorias.getId() > 0) {
            pUtilQuery.AgregarNumWhere(" r.Id=? ");
            if (statement != null) { 
                statement.setInt(pUtilQuery.getNumWhere(), pCategorias.getId()); 
            }
        }

        if (pCategorias.getNombre() != null && pCategorias.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" r.Nombre LIKE ? "); 
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pCategorias.getNombre() + "%"); 
            }
        }
    }
    
    public static ArrayList<Categoria> buscar(Categoria pCategorias) throws Exception {
        ArrayList<Categoria> categoria = new ArrayList();
        try (Connection conn = ComunBD.obtenerConexion();) {
            String sql = obtenerSelect(pCategorias);
            ComunBD comundb = new ComunBD();
            ComunBD.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0); 
            querySelect(pCategorias, utilQuery);
            sql = utilQuery.getSQL(); 
            sql += agregarOrderBy(pCategorias);
            try (PreparedStatement ps = ComunBD.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0); 
                querySelect(pCategorias , utilQuery);
                obtenerDatos(ps, categoria);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return categoria;
    }
}
