package webjava.accesoadatos;

import java.util.*;
import java.sql.*;
import webjava.entidadesdenegocio.*;
import java.time.LocalDate;

public class usuariosDAL {
    public static String encriptarMD5(String txt) throws Exception {
        try {
            StringBuffer sb;
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(txt.getBytes());
            sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException ex) {
            throw ex;
        }
    }
    
    static String obtenerCampos() {
        return "u.Id, u.IdRol, u.Nombre, u.Apellido, u.Login, u.Estatus, u.FechaRegistro";
    }
    
     private static String obtenerSelect(usuarios pusuario) {
        String sql;
        sql = "SELECT ";
        if (pusuario.getTop_aux() > 0 && comunBD.TIPODB == comunBD.TipoDB.SQLSERVER) {
             sql += "TOP " + pusuario.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Usuario u");
        return sql;
    }
        
     private static String agregarOrderBy(usuarios pusuario) {
        String sql = " ORDER BY u.Id DESC";
        if (pusuario.getTop_aux() > 0 && comunBD.TIPODB == comunBD.TipoDB.MYSQL) {
            sql += " LIMIT " + pusuario.getTop_aux() + " ";
        }
        return sql;
    }
     
     private static boolean existeLogin(usuarios pUsuario) throws Exception {
        boolean existe = false;
        ArrayList<usuarios> usuarios = new ArrayList();
        try (Connection conn = comunBD.obtenerConexion();) {
            String sql = obtenerSelect(pUsuario);
            sql += " WHERE u.Id<>? AND u.Login=?";
            try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pUsuario.getId());
                ps.setString(2, pUsuario.getLogin());
                obtenerDatos(ps, usuarios);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        if (usuarios.size() > 0) {
            usuarios usuario = usuarios.get(0);
            if (usuario.getId() > 0 && usuario.getLogin().equals(pUsuario.getLogin())) {
                existe = true;
            }
        }
        return existe;
    }
    
     public static int crear(usuarios pUsuario) throws Exception {
        int result;
        String sql;
        boolean existe = existeLogin(pUsuario);
        if (existe == false) {
            try (Connection conn = comunBD.obtenerConexion();) {
                sql = "INSERT INTO Usuario(Idrol,Nombre,Apellido,Login,Pass,Estatus,FechaRegistro) VALUES(?,?,?,?,?,?,?)";
                try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
                    ps.setInt(1, pUsuario.getRolid());
                    ps.setString(2, pUsuario.getNombre());
                    ps.setString(3, pUsuario.getApellido()); 
                    ps.setString(4, pUsuario.getLogin());
                    ps.setString(5, encriptarMD5(pUsuario.getPassword())); 
                    ps.setByte(6, pUsuario.getEstatus());
                    ps.setDate(7, java.sql.Date.valueOf(LocalDate.now()));
                    result = ps.executeUpdate();
                    ps.close();
                } catch (SQLException ex) {
                    throw ex;
                }
                conn.close();
            }
            catch (SQLException ex) {
                throw ex;
            }
        } else {
            result = 0;
            throw new RuntimeException("Login ya existe");
        }
        return result;
    }
     
     public static int modificar(usuarios pUsuario) throws Exception {
        int result;
        String sql;
        boolean existe = existeLogin(pUsuario);
        if (existe == false) {
            try (Connection conn = comunBD.obtenerConexion();) {                
                sql = "UPDATE Usuario SET IdRol=?, Nombre=?, Apellido=?, Login=?, Estatus=? WHERE Id=?";
                try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
                    ps.setInt(1, pUsuario.getRolid());
                    ps.setString(2, pUsuario.getNombre());  
                    ps.setString(3, pUsuario.getApellido());
                    ps.setString(4, pUsuario.getLogin());
                    ps.setByte(5, pUsuario.getEstatus());
                    ps.setInt(6, pUsuario.getId());
                    result = ps.executeUpdate();
                    ps.close();
                } catch (SQLException ex) {
                    throw ex;
                }
                conn.close();
            } 
            catch (SQLException ex) {
                throw ex;
            }
        } else {
            result = 0;
            throw new RuntimeException("Login ya existe");
        }
        return result;
    }
    
      public static int eliminar(usuarios pUsuario) throws Exception {
        int result;
        String sql;
        try (Connection conn = comunBD.obtenerConexion();) { 
            sql = "DELETE FROM Usuario WHERE Id=?"; 
            try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pUsuario.getId());
                result = ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return result;
    }
      
      static int asignarDatosResultSet(usuarios pUsuario, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pUsuario.setId(pResultSet.getInt(pIndex)); 
        pIndex++;
        pUsuario.setRolid(pResultSet.getInt(pIndex)); 
        pIndex++;
        pUsuario.setNombre(pResultSet.getString(pIndex)); 
        pIndex++;
        pUsuario.setApellido(pResultSet.getString(pIndex)); 
        pIndex++;
        pUsuario.setLogin(pResultSet.getString(pIndex)); 
        pIndex++;
        pUsuario.setEstatus(pResultSet.getByte(pIndex)); 
        pIndex++;
        pUsuario.setFecha(pResultSet.getDate(pIndex).toLocalDate()); 
        return pIndex;
    }
    
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<usuarios> pUsuarios) throws Exception {
        try (ResultSet resultSet = comunBD.obtenerResultSet(pPS);) { 
            while (resultSet.next()) {
                usuarios usuario = new usuarios();
                asignarDatosResultSet(usuario, resultSet, 0);
                pUsuarios.add(usuario);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    private static void obtenerDatosIncluirRol(PreparedStatement pPS, ArrayList<usuarios> pUsuarios) throws Exception {
        try (ResultSet resultSet = comunBD.obtenerResultSet(pPS);) {
            HashMap<Integer, rol> rolMap = new HashMap(); 
            while (resultSet.next()) {
                usuarios usuario = new usuarios();
                int index = asignarDatosResultSet(usuario, resultSet, 0);
                if (rolMap.containsKey(usuario.getRolid()) == false) {
                    rol rol = new rol();
                    rolDAL.asignarDatosResultSet(rol, resultSet, index);
                    rolMap.put(rol.getId(), rol); 
                    usuario.setRol(rol); 
                } else {
                    usuario.setRol(rolMap.get(usuario.getRolid())); 
                }
                pUsuarios.add(usuario); 
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex; 
        }
    }
    
    public static usuarios obtenerPorId(usuarios pUsuario) throws Exception {
        usuarios usuario = new usuarios();
        ArrayList<usuarios> usuarios = new ArrayList();
        try (Connection conn = comunBD.obtenerConexion();) {
            String sql = obtenerSelect(pUsuario);
            sql += " WHERE u.Id=?";
            try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pUsuario.getId());
                obtenerDatos(ps, usuarios);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        if (usuarios.size() > 0) {
            usuario = usuarios.get(0);
        }
        return usuario;
    }
    
    public static ArrayList<usuarios> obtenerTodos() throws Exception {
        ArrayList<usuarios> usuarios;
        usuarios = new ArrayList<>();
        try (Connection conn = comunBD.obtenerConexion();) {
            String sql = obtenerSelect(new usuarios()); 
            sql += agregarOrderBy(new usuarios());
            try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
                obtenerDatos(ps, usuarios);
                ps.close();
            } catch (SQLException ex) {
                throw ex; 
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return usuarios;
    }
    
    static void querySelect(usuarios pUsuario, comunBD.utilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pUsuario.getId() > 0) {
            pUtilQuery.AgregarNumWhere(" u.Id=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pUsuario.getId());
            }
        }

        if (pUsuario.getRolid() > 0) {
            pUtilQuery.AgregarNumWhere(" u.IdRol=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pUsuario.getRolid());
            }
        }
        
        if (pUsuario.getNombre() != null && pUsuario.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" u.Nombre LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pUsuario.getNombre() + "%");
            }
        }

        if (pUsuario.getApellido() != null && pUsuario.getApellido().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" u.Apellido LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pUsuario.getApellido() + "%");
            }
        }

        if (pUsuario.getLogin() != null && pUsuario.getLogin().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" u.Login=? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), pUsuario.getLogin());
            }
        }

        if (pUsuario.getEstatus() > 0) {
            pUtilQuery.AgregarNumWhere(" u.Estatus=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pUsuario.getEstatus());
            }
        }
    }
    
    public static ArrayList<usuarios> buscar(usuarios pUsuario) throws Exception {
        ArrayList<usuarios> usuarios = new ArrayList();
        try (Connection conn = comunBD.obtenerConexion();) {
            String sql = obtenerSelect(pUsuario);
            comunBD comundb = new comunBD();
            comunBD.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0);
            querySelect(pUsuario, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pUsuario);
            try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pUsuario, utilQuery);
                obtenerDatos(ps, usuarios);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } 
        catch (SQLException ex) {
            throw ex;
        }
        return usuarios;
    }
    
    public static usuarios login(usuarios pUsuario) throws Exception {
        usuarios usuario = new usuarios();
        ArrayList<usuarios> listausuarios = new ArrayList();
        String password = encriptarMD5(pUsuario.getPassword());
        try (Connection conn = comunBD.obtenerConexion();) {
            String sql = obtenerSelect(pUsuario);
            sql += " WHERE u.Login=? AND u.Password=? AND u.Estatus=?";
            try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
                ps.setString(1, pUsuario.getLogin());
                ps.setString(2, password);
                ps.setByte(3, usuarios.EstatusUsuario.ACTIVO);
                obtenerDatos(ps, listausuarios);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } 
        catch (SQLException ex) {
            throw ex;
        }
        if (listausuarios.size() > 0) {
            usuario = listausuarios.get(0);
        }
        return usuario;
    }
    
    public static int cambiarPassword(usuarios pUsuario, String pPasswordAnt) throws Exception {
        int result;
        String sql;
        usuarios usuarioAnt = new usuarios();
        usuarioAnt.setLogin(pUsuario.getLogin());
        usuarioAnt.setPassword(pPasswordAnt);
        usuarios usuarioAut = login(usuarioAnt);

        if (usuarioAut.getId() > 0 && usuarioAut.getLogin().equals(pUsuario.getLogin())) {
            try (Connection conn = comunBD.obtenerConexion();) {
                sql = "UPDATE Usuario SET Password=? WHERE Id=?";
                try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
                    ps.setString(1, encriptarMD5(pUsuario.getPassword())); 
                    ps.setInt(2, pUsuario.getId());
                    result = ps.executeUpdate();
                    ps.close();
                } catch (SQLException ex) {
                    throw ex;
                }
                conn.close();
            }
            catch (SQLException ex) {
                throw ex;
            }
        } else {
            result = 0;
            throw new RuntimeException("El password actual es incorrecto");
        }
        return result;
    }
    
    public static ArrayList<usuarios> buscarIncluirRol(usuarios pUsuario) throws Exception {
        ArrayList<usuarios> usuarios = new ArrayList();
        try (Connection conn = comunBD.obtenerConexion();) {
            String sql = "SELECT ";
            if (pUsuario.getTop_aux() > 0 && comunBD.TIPODB == comunBD.TipoDB.SQLSERVER) {
                sql += "TOP " + pUsuario.getTop_aux() + " "; 
            }
            sql += obtenerCampos();
            sql += ",";
            sql += rolDAL.obtenerCampos();
            sql += " FROM Usuario u";
            sql += " JOIN Rol r on (u.IdRol=r.Id)";
            comunBD comundb = new comunBD();
            comunBD.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0);
            querySelect(pUsuario, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pUsuario);
            try (PreparedStatement ps = comunBD.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pUsuario, utilQuery);
                obtenerDatosIncluirRol(ps, usuarios);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return usuarios;
    }
}
