package webjava.accesoadatos;

import java.util.*;
import java.sql.*;
import webjava.entidadesdenegocio.*;

public class rolDAL {
     static String obtenerCampos() {
        return "r.Id, r.Nombre";
    }
    
    private static String obtenerSelect(rol pRol) {
        String sql;
        sql = "SELECT ";
        if (pRol.getTop_aux() > 0 && comunBD.TIPODB == comunBD.TipoDB.SQLSERVER) {            
            sql += "TOP" + pRol.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Rol r");
        return sql;
    }
    
    private static String agregarOrderBy(rol pRol) {
        String sql = " ORDER BY r.Id DESC";
        if (pRol.getTop_aux() > 0 && comunBD.TIPODB == comunBD.TipoDB.MYSQL) {
            sql += " LIMIT " + pRol.getTop_aux() + " ";
        }
        return sql;
    }
    
    public static int crear(rol pRol) throws Exception {
        int result;
        String sql;
        try (Connection conn = comunBD.obtenerConexion();) { 
            sql = "INSERT INTO rol(Nombre) VALUES(?)";
            try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
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
    
    public static int modificar(rol pRol) throws Exception {
        int result;
        String sql;
        try (Connection conn = comunBD.obtenerConexion();) {
            sql = "UPDATE Rol SET Nombre=? WHERE Id=?";
            try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
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
    
    public static int eliminar(rol pRol) throws Exception {
        int result;
        String sql;
        try (Connection conn = comunBD.obtenerConexion();) {
            sql = "DELETE FROM Rol WHERE Id=?";
            try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
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
    
    static int asignarDatosResultSet(rol pRol, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pRol.setId(pResultSet.getInt(pIndex));
        pIndex++;
        pRol.setNombre(pResultSet.getString(pIndex));
        return pIndex;
    }
    
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<rol> pRoles) throws Exception {
        try (ResultSet resultSet = comunBD.obtenerResultSet(pPS);) {
            while (resultSet.next()) {
                rol rol = new rol(); 
                asignarDatosResultSet(rol, resultSet, 0);
                pRoles.add(rol);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    public static rol obtenerPorId(rol pRol) throws Exception {
        rol rol = new rol();
        ArrayList<rol> roles = new ArrayList();
        try (Connection conn = comunBD.obtenerConexion();) { 
            String sql = obtenerSelect(pRol);
            sql += " WHERE r.Id=?";
            try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
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
    
    public static ArrayList<rol> obtenerTodos() throws Exception {
        ArrayList<rol> roles = new ArrayList<>();
        try (Connection conn = comunBD.obtenerConexion();) {
            String sql = obtenerSelect(new rol());
            sql += agregarOrderBy(new rol());
            try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
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
    
    static void querySelect(rol pRol, comunBD.utilQuery pUtilQuery) throws SQLException {
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
    
    public static ArrayList<rol> buscar(rol pRol) throws Exception {
        ArrayList<rol> roles = new ArrayList();
        try (Connection conn = comunBD.obtenerConexion();) {
            String sql = obtenerSelect(pRol);
            comunBD comundb = new comunBD();
            comunBD.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0); 
            querySelect(pRol, utilQuery);
            sql = utilQuery.getSQL(); 
            sql += agregarOrderBy(pRol);
            try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
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
