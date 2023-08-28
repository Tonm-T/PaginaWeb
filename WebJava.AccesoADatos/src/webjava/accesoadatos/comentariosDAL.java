package webjava.accesoadatos;

import java.util.*;
import java.sql.*;
import webjava.entidadesdenegocio.*;

public class comentariosDAL {
     static String obtenerCampos() {
    return "c,id,";
    }
    
    private static String obtenerSelect(comentario pComentario) {
    String sql;
    sql = "SELECT";
    if (pComentario.getTop_aux() > 0 && comunBD.TIPODB == comunBD.TipoDB.SQLSERVER) {
    sql += "TOP" + pComentario.getTop_aux() + " ";
    }
        sql += (obtenerCampos() + " FROM Rol r");
        return sql;
    }
    
    private static String agregarOrderBy(comentario pComentario) {
        String sql = " ORDER BY r.Id DESC";
        if (pComentario.getTop_aux() > 0 && comunBD.TIPODB == comunBD.TipoDB.MYSQL) {
            sql += " LIMIT " + pComentario.getTop_aux() + " ";
        }
        return sql;
    }
    
    public static int crear(comentario pComentario) throws Exception {
        int result;
        String sql;
        try (Connection conn = comunBD.obtenerConexion();) { 
            sql = "INSERT INTO rol(Nombre) VALUES(?)";
            try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
                ps.setString(1, pComentario.getContenido());
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
    
     public static int modificar(comentario pComentario) throws Exception {
        int result;
        String sql;
        try (Connection conn = comunBD.obtenerConexion();) {
            sql = "UPDATE Rol SET Nombre=? WHERE Id=?";
            try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
                ps.setString(1, pComentario.getContenido());
                ps.setInt(2, pComentario.getId());
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
    
    public static int eliminar(comentario pComentario) throws Exception {
        int result;
        String sql;
        try (Connection conn = comunBD.obtenerConexion();) {
            sql = "DELETE FROM Rol WHERE Id=?";
            try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pComentario.getId());
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
    
    static int asignarDatosResultSet(comentario pComentario, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pComentario.setId(pResultSet.getInt(pIndex));
        pIndex++;
        pComentario.setContenido(pResultSet.getString(pIndex));
        return pIndex;
    }
    
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<comentario> pComentarios) throws Exception {
        try (ResultSet resultSet = comunBD.obtenerResultSet(pPS);) {
            while (resultSet.next()) {
                comentario comentarios = new comentario(); 
                asignarDatosResultSet(comentarios, resultSet, 0);
                pComentarios.add(comentarios);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    public static comentario obtenerPorId(comentario pComentario) throws Exception {
        comentario comentario = new comentario();
        ArrayList<comentario> comentarios = new ArrayList();
        try (Connection conn = comunBD.obtenerConexion();) { 
            String sql = obtenerSelect(pComentario);
            sql += " WHERE r.Id=?";
            try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pComentario.getId());
                obtenerDatos(ps, comentarios);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        
        if (comentarios.size() > 0) {
            comentario = comentarios.get(0);
        }
        
        return comentario;
    }
    
    public static ArrayList<comentario> obtenerTodos() throws Exception {
        ArrayList<comentario> comentarios = new ArrayList<>();
        try (Connection conn = comunBD.obtenerConexion();) {
            String sql = obtenerSelect(new comentario());
            sql += agregarOrderBy(new comentario());
            try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
                obtenerDatos(ps, comentarios);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } 
        catch (SQLException ex) {
            throw ex;
        }
        
        return comentarios;
    }
    
    static void querySelect(comentario pComentario, comunBD.utilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pComentario.getId() > 0) {
            pUtilQuery.AgregarNumWhere(" r.Id=? ");
            if (statement != null) { 
                statement.setInt(pUtilQuery.getNumWhere(), pComentario.getId()); 
            }
        }

        if (pComentario.getContenido()!= null && pComentario.getContenido().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" r.Nombre LIKE ? "); 
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pComentario.getContenido()+ "%"); 
            }
        }
    }
    
    public static ArrayList<comentario> buscar(comentario pComentario) throws Exception {
        ArrayList<comentario> comentarios = new ArrayList();
        try (Connection conn = comunBD.obtenerConexion();) {
            String sql = obtenerSelect(pComentario);
            comunBD comundb = new comunBD();
            comunBD.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0); 
            querySelect(pComentario, utilQuery);
            sql = utilQuery.getSQL(); 
            sql += agregarOrderBy(pComentario);
            try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0); 
                querySelect(pComentario, utilQuery);
                obtenerDatos(ps, comentarios);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return comentarios;
    }
}

