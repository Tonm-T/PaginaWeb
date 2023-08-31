package webjava.accesoadatos;

import java.util.*;
import java.sql.*;
import webjava.entidadesdenegocio.*;

public class ComentarioDAL {
    static String obtenerCampos() {
    return "c,id,";
    }
    
    private static String obtenerSelect(Comentario pComentario) {
    String sql;
    sql = "SELECT";
    if (pComentario.getTop_aux() > 0 && ComunBD.TIPODB == ComunBD.TipoDB.SQLSERVER) {
    sql += "TOP" + pComentario.getTop_aux() + " ";
    }
        sql += (obtenerCampos() + " FROM Rol r");
        return sql;
    }
    
    private static String agregarOrderBy(Comentario pComentario) {
        String sql = " ORDER BY r.Id DESC";
        if (pComentario.getTop_aux() > 0 && ComunBD.TIPODB == ComunBD.TipoDB.MYSQL) {
            sql += " LIMIT " + pComentario.getTop_aux() + " ";
        }
        return sql;
    }
    
    public static int crear(Comentario pComentario) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunBD.obtenerConexion();) { 
            sql = "INSERT INTO rol(Nombre) VALUES(?)";
            try (PreparedStatement ps = ComunBD.createPreparedStatement(conn, sql);) {
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
    
     public static int modificar(Comentario pComentario) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunBD.obtenerConexion();) {
            sql = "UPDATE Rol SET Nombre=? WHERE Id=?";
            try (PreparedStatement ps = ComunBD.createPreparedStatement(conn, sql);) {
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
    
    public static int eliminar(Comentario pComentario) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunBD.obtenerConexion();) {
            sql = "DELETE FROM Rol WHERE Id=?";
            try (PreparedStatement ps = ComunBD.createPreparedStatement(conn, sql);) {
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
    
    static int asignarDatosResultSet(Comentario pComentario, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pComentario.setId(pResultSet.getInt(pIndex));
        pIndex++;
        pComentario.setContenido(pResultSet.getString(pIndex));
        return pIndex;
    }
    
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Comentario> pComentarios) throws Exception {
        try (ResultSet resultSet = ComunBD.obtenerResultSet(pPS);) {
            while (resultSet.next()) {
                Comentario comentarios = new Comentario(); 
                asignarDatosResultSet(comentarios, resultSet, 0);
                pComentarios.add(comentarios);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    public static Comentario obtenerPorId(Comentario pComentario) throws Exception {
        Comentario comentario = new Comentario();
        ArrayList<Comentario> comentarios = new ArrayList();
        try (Connection conn = ComunBD.obtenerConexion();) { 
            String sql = obtenerSelect(pComentario);
            sql += " WHERE r.Id=?";
            try (PreparedStatement ps = ComunBD.createPreparedStatement(conn, sql);) {
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
    
    public static ArrayList<Comentario> obtenerTodos() throws Exception {
        ArrayList<Comentario> comentarios = new ArrayList<>();
        try (Connection conn = ComunBD.obtenerConexion();) {
            String sql = obtenerSelect(new Comentario());
            sql += agregarOrderBy(new Comentario());
            try (PreparedStatement ps = ComunBD.createPreparedStatement(conn, sql);) {
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
    
    static void querySelect(Comentario pComentario, ComunBD.utilQuery pUtilQuery) throws SQLException {
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
    
    public static ArrayList<Comentario> buscar(Comentario pComentario) throws Exception {
        ArrayList<Comentario> comentarios = new ArrayList();
        try (Connection conn = ComunBD.obtenerConexion();) {
            String sql = obtenerSelect(pComentario);
            ComunBD comundb = new ComunBD();
            ComunBD.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0); 
            querySelect(pComentario, utilQuery);
            sql = utilQuery.getSQL(); 
            sql += agregarOrderBy(pComentario);
            try (PreparedStatement ps = ComunBD.createPreparedStatement(conn, sql);) {
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
