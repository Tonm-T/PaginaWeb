package webjava.accesoadatos;

import java.util.*;
import java.sql.*;
import webjava.entidadesdenegocio.*;

public class categoriasDAL {
    static String obtenerCampos() {
    return "c.id, c.nombre, c.clasificacion";
    }
    
    private static String obtenerSelect (categorias pCategorias) {
    String sql;
    sql = "SELECT";
    if (pCategorias.getTop_aux() > 0 && comunBD.TIPODB == comunBD.TipoDB.SQLSERVER) {
    sql += "TOP" + pCategorias.getTop_aux() + " ";
    }
    sql += (obtenerCampos() + " FROM categorias c");
        return sql;
  }
    private static String agregarOrderBy (categorias pCategorias) {
    String sql = "ORDER BY c.id DESC";
    if (pCategorias.getTop_aux() > 0 && comunBD.TIPODB == comunBD.TipoDB.SQLSERVER) {
    sql += "LIMIT" + pCategorias.getTop_aux() + " ";
    }
    return sql;
    }
    
    public static int crear (categorias pCategorias) throws Exception {
    int result = 0;
    String sql;
    try (Connection conn = comunBD.obtenerConexion();) {
    sql = "INSERT INTO categorias (nombre, clasificacion)";
    try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
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
    
    public static int modificar (categorias pCategorias) throws Exception {
    int result ;
    String sql;
    try (Connection conn = comunBD.obtenerConexion();) {
    sql = "UPDATE categorias SET nombre=?, clasificacion=?";
    try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
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
    
    public static int eliminar (categorias pCategorias) throws Exception {
    int result;
    String sql;
    try (Connection conn = comunBD.obtenerConexion();) {
    sql = "DELETE FROM categorias WHERE id=?";
    try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
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
    
      static int asignarDatosResultSet(categorias pCategorias, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pCategorias.setId(pResultSet.getInt(pIndex));
        pIndex++;
        pCategorias.setNombre(pResultSet.getString(pIndex));
        return pIndex;
    }
      
      private static void obtenerDatos(PreparedStatement pPS, ArrayList<categorias> pCategorias) throws Exception {
        try (ResultSet resultSet = comunBD.obtenerResultSet(pPS);) {
            while (resultSet.next()) {
                categorias categoria = new categorias(); 
                asignarDatosResultSet(categoria, resultSet, 0);
                pCategorias.add(categoria);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    public static categorias obtenerPorId(categorias PCategorias) throws Exception {
        categorias Categorias = new categorias();
        ArrayList<categorias> categorias = new ArrayList();
        try (Connection conn = comunBD.obtenerConexion();) { 
            String sql = obtenerSelect(PCategorias);
            sql += " WHERE r.Id=?";
            try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
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
            Categorias = categorias.get(0);
        }
        
        return Categorias;
    }
    
     static void querySelect(categorias pCategorias, comunBD.utilQuery pUtilQuery) throws SQLException {
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
    
    public static ArrayList<categorias> buscar(categorias pCategorias) throws Exception {
        ArrayList<categorias> categoria = new ArrayList();
        try (Connection conn = comunBD.obtenerConexion();) {
            String sql = obtenerSelect(pCategorias);
            comunBD comundb = new comunBD();
            comunBD.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0); 
            querySelect(pCategorias, utilQuery);
            sql = utilQuery.getSQL(); 
            sql += agregarOrderBy(pCategorias);
            try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
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
