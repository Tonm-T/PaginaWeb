package webjava.accesoadatos;
import java.util.*;
import java.sql.*;
import webjava.entidadesdenegocio.*;

public class RolDAL {
    static String obtenerCampos() {
        return "r.id, r.Nombre";
    }
    
    private static String obtenerSelect(Rol pRol) {
        String sql;
        sql = "SELECT ";
        if (pRol.getTop_aux() > 0 && ComunBD.TIPODB == ComunBD.TipoDB.SQLSERVER) {            
            sql += "TOP" + pRol.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Rol r");
        return sql;
    }
    
    private static String agregarOrderBy(Rol pRol) {
        String sql = " ORDER BY r.Id DESC";
        if (pRol.getTop_aux() > 0 && ComunBD.TIPODB == ComunBD.TipoDB.MYSQL) {
            sql += " LIMIT " + pRol.getTop_aux() + " ";
        }
        return sql;
    }
    
    public static int crear(Rol pRol) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunBD.obtenerConexion();) { 
            sql = "INSERT INTO rol(Nombre) VALUES(?)";
            try (PreparedStatement ps = ComunBD.createPreparedStatement(conn, sql);) {
                ps.setString(1, pRol.getNombre());
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
    
    public static int modificar(Rol pRol) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunBD.obtenerConexion();) {
            sql = "UPDATE Rol SET Nombre=? WHERE Id=?";
            try (PreparedStatement ps = ComunBD.createPreparedStatement(conn, sql);) {
                ps.setString(1, pRol.getNombre());
                ps.setInt(2, pRol.getId());
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
    
    public static int eliminar(Rol pRol) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunBD.obtenerConexion();) {
            sql = "DELETE FROM Rol WHERE Id=?";
            try (PreparedStatement ps = ComunBD.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pRol.getId());
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
    
    static int asignarDatosResultSet(Rol pRol, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pRol.setId(pResultSet.getInt(pIndex));
        pIndex++;
        pRol.setNombre(pResultSet.getString(pIndex));
        return pIndex;
    }
    
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Rol> pRoles) throws Exception {
        try (ResultSet resultSet = ComunBD.obtenerResultSet(pPS);) {
            while (resultSet.next()) {
                Rol rol = new Rol(); 
                asignarDatosResultSet(rol, resultSet, 0);
                pRoles.add(rol);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    public static Rol obtenerPorId(Rol pRol) throws Exception {
        Rol rol = new Rol();
        ArrayList<Rol> roles = new ArrayList();
        try (Connection conn = ComunBD.obtenerConexion();) { 
            String sql = obtenerSelect(pRol);
            sql += " WHERE r.Id=?";
            try (PreparedStatement ps = ComunBD.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pRol.getId());
                obtenerDatos(ps, roles);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        
        if (roles.size() > 0) {
            rol = roles.get(0);
        }
        
        return rol;
    }
    
    public static ArrayList<Rol> obtenerTodos() throws Exception {
        ArrayList<Rol> roles = new ArrayList<>();
        try (Connection conn = ComunBD.obtenerConexion();) {
            String sql = obtenerSelect(new Rol());
            sql += agregarOrderBy(new Rol());
            try (PreparedStatement ps = ComunBD.createPreparedStatement(conn, sql);) {
                obtenerDatos(ps, roles);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } 
        catch (SQLException ex) {
            throw ex;
        }
        
        return roles;
    }
    
    static void querySelect(Rol pRol, ComunBD.utilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pRol.getId() > 0) {
            pUtilQuery.AgregarNumWhere(" r.Id=? ");
            if (statement != null) { 
                statement.setInt(pUtilQuery.getNumWhere(), pRol.getId()); 
            }
        }

        if (pRol.getNombre() != null && pRol.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" r.Nombre LIKE ? "); 
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pRol.getNombre() + "%"); 
            }
        }
    }
    
    public static ArrayList<Rol> buscar(Rol pRol) throws Exception {
        ArrayList<Rol> roles = new ArrayList();
        try (Connection conn = ComunBD.obtenerConexion();) {
            String sql = obtenerSelect(pRol);
            ComunBD comundb = new ComunBD();
            ComunBD.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0); 
            querySelect(pRol, utilQuery);
            sql = utilQuery.getSQL(); 
            sql += agregarOrderBy(pRol);
            try (PreparedStatement ps = ComunBD.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0); 
                querySelect(pRol, utilQuery);
                obtenerDatos(ps, roles);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return roles;
    }
}
