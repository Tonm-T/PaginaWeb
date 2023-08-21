package webjava.accesoadatos;

import java.util.*;
import java.sql.*;
import webjava.entidadesdenegocio.*;
import java.time.LocalDate;

public class blogsDAL {
    
    static String obtenerCampos () {
    return "b.id, b.autor, b.nombre, b.descripcion, b.contenido, b.fechacreacion, b.imagendescripcion, b.imagencontenido";
    }
    
    private static String obtenerSelect (blogs pBlogs) {
    String sql;
    sql = "SELECT";
    if (pBlogs.getTop_aux() > 0 && comunBD.TIPODB == comunBD.TipoDB.SQLSERVER) {
    sql += "TOP" + pBlogs.getTop_aux() + " ";
    }
    sql += (obtenerCampos() + "FROM blogs b");
    return sql;
    }
    
    private static String agregarOrderBy (blogs pBlogs) {
        String sql = "ORDER BY b.id DESC";
        if (pBlogs.getTop_aux() > 0 && comunBD.TIPODB == comunBD.TipoDB.MYSQL) {
        sql += "LIMIT" + pBlogs.getTop_aux() + " ";
        }
    return sql;
    }
    
    public static int crear (blogs pBlogs) throws Exception {
    int result;
    String sql;
        try (Connection conn = comunBD.obtenerConexion();) {
        sql = "INSERT INTO (Idblogs, autor, nombre, descripcion, contenido, fechacreacion, imagendescripcion, imagencontenido) VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
        ps.setInt(1, pBlogs.getCategoriasid());
        ps.setString(2, pBlogs.getAutor());
        ps.setString(3, pBlogs.getNombre());
        ps.setString(4, pBlogs.getDescripcion());
        ps.setString(5, pBlogs.getContenido());
        ps.setDate(6, java.sql.Date.valueOf(LocalDate.now()));
        ps.setString (7, pBlogs.getImagendescripcion());
        ps.setString(8, pBlogs.getImagencontenido());
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
    
    public static int modificar (blogs pBlogs) throws Exception {
    int result = 0;
    String sql;
    try (Connection conn = comunBD.obtenerConexion();) {
    sql = "UPDATE blogs SET id=?, autor=?, nombre=?, descripcion=?, contenido=?, fechacreacion=?, imagendescripcion=?, imagencontenido=?, ";
    try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
       ps.setInt(1, pBlogs.getCategoriasid());
        ps.setString(2, pBlogs.getAutor());
        ps.setString(3, pBlogs.getNombre());
        ps.setString(4, pBlogs.getDescripcion());
        ps.setString(5, pBlogs.getContenido());
        ps.setDate(6, java.sql.Date.valueOf(LocalDate.now()));
        ps.setString (7, pBlogs.getImagendescripcion());
        ps.setString(8, pBlogs.getImagencontenido());
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
    
    public static int eliminar (blogs pBlogs) throws Exception {
     int result;
        String sql;
        try (Connection conn = comunBD.obtenerConexion();) {
            sql = "DELETE FROM Rol WHERE Id=?";
            try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
                   ps.setInt(1, pBlogs.getCategoriasid());
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
    
     static int asignarDatosResultSet(blogs pBlogs, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pBlogs.setId(pResultSet.getInt(pIndex));
        pIndex++;
        pBlogs.setNombre(pResultSet.getString(pIndex));
        return pIndex;
    }
     
      private static void obtenerDatos(PreparedStatement pPS, ArrayList<blogs> pBlogs) throws Exception {
        try (ResultSet resultSet = comunBD.obtenerResultSet(pPS);) {
            while (resultSet.next()) {
                blogs blogs = new blogs(); 
                asignarDatosResultSet(blogs, resultSet, 0);
                pBlogs.add(blogs);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
      
      public static blogs obtenerPorId(blogs pBlogs) throws Exception {
        blogs Blogs = new blogs();
        ArrayList<blogs> blog = new ArrayList();
        try (Connection conn = comunBD.obtenerConexion();) { 
            String sql = obtenerSelect(pBlogs);
            sql += " WHERE r.Id=?";
            try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pBlogs.getId());
                obtenerDatos(ps, blog);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        
        if (blog.size() > 0) {
            Blogs = blog.get(0);
        }
        
        return Blogs;
    }
      
       public static ArrayList<blogs> obtenerTodos() throws Exception {
        ArrayList<blogs> blog = new ArrayList<>();
        try (Connection conn = comunBD.obtenerConexion();) {
            String sql = obtenerSelect(new blogs());
            sql += agregarOrderBy(new blogs());
            try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
                obtenerDatos(ps, blog);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } 
        catch (SQLException ex) {
            throw ex;
        }
        
        return blog;
    }
       
       static void querySelect(blogs pBlogs, comunBD.utilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pBlogs.getId() > 0) {
            pUtilQuery.AgregarNumWhere(" r.Id=? ");
            if (statement != null) { 
                statement.setInt(pUtilQuery.getNumWhere(), pBlogs.getId()); 
            }
        }

        if (pBlogs.getNombre() != null && pBlogs.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" r.Nombre LIKE ? "); 
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pBlogs.getNombre() + "%"); 
            }
        }
    }
    
    public static ArrayList<blogs> buscar(blogs pBlogs) throws Exception {
        ArrayList<blogs> blog = new ArrayList();
        try (Connection conn = comunBD.obtenerConexion();) {
            String sql = obtenerSelect(pBlogs);
            comunBD comundb = new comunBD();
            comunBD.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0); 
            querySelect(pBlogs, utilQuery);
            sql = utilQuery.getSQL(); 
            sql += agregarOrderBy(pBlogs);
            try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0); 
                querySelect(pBlogs, utilQuery);
                obtenerDatos(ps, blog);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return blog;
    }
}