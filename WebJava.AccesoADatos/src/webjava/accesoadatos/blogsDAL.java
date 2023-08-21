package webjava.accesoadatos;

import java.util.*;
import java.sql.*;
import webjava.entidadesdenegocio.*;
import java.time.LocalDate;

public class blogsDAL {
    public static int crear (blogs pBlogs) throws Exception {
    int result;
    String sql;
        try (Connection conn = comunBD.obtenerConexion();) {
        sql = "INSERT INTO (Idblogs, autor, nombre, descripcion, contenido, fechacreacion, imagendescripcion, imagencontenido,) VALUES (?,?,?,?,?,?,?,?)";
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
}