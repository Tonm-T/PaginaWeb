package webjava.appweb.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.util.ArrayList;
import webjava.accesoadatos.RolDAL;
import webjava.accesoadatos.UsuarioDAL;
import webjava.appweb.utils.*;
import webjava.entidadesdenegocio.Rol;
import webjava.entidadesdenegocio.Usuario;

@WebServlet(name = "usuariosServlet", urlPatterns = {"/usuarios"})
public class usuariosServlet extends HttpServlet {
      
     private Usuario obtenerUsuarios(HttpServletRequest request) {
        String accion = Utilidad.getParameter(request, "accion", "index");
        Usuario usuario = new Usuario();
        usuario.setNombre(Utilidad.getParameter(request, "nombre", ""));
        usuario.setApellido(Utilidad.getParameter(request, "apellido", ""));
        usuario.setLogin(Utilidad.getParameter(request, "login", ""));
        usuario.setPassword(Utilidad.getParameter(request, "password", ""));
        usuario.setRolid(Integer.parseInt(Utilidad.getParameter(request, "rolid", "0")));
        usuario.setEstatus(Byte.parseByte(Utilidad.getParameter(request, "estatus", "0")));
        
        if (accion.equals("index")) {
        usuario.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
        usuario.setTop_aux(usuario.getTop_aux() == 0 ? Integer.MAX_VALUE : usuario.getTop_aux());
        }
        
        if (accion.equals("login") || accion.equals("create") || accion.equals("cambiarpas")) {
        usuario.setPassword(Utilidad.getParameter(request, "password", ""));
        usuario.setConfirmPassword_aux(Utilidad.getParameter(request, "ConfirmPassword_aux", ""));
        if (accion.equals("cambiarpass")) {
                usuario.setId(Integer.parseInt(Utilidad.getParameter(request, "id", "0")));
            }
        } else {
            usuario.setId(Integer.parseInt(Utilidad.getParameter(request, "id", "0")));
        }
        return usuario;
    }
     
     private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Usuario usuario = new Usuario();
            usuario.setTop_aux(10);
            ArrayList<Usuario> usuarios = UsuarioDAL.buscarIncluirRol(usuario);
            request.setAttribute("usuarios", usuarios);
            request.setAttribute("top_aux", usuario.getTop_aux());
            request.getRequestDispatcher("views/usuarios/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
     
     private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Usuario usuario = obtenerUsuarios(request);
            ArrayList<Usuario> usuarios = UsuarioDAL.buscarIncluirRol(usuario);
            request.setAttribute("usuarios", usuarios);
            request.setAttribute("top_aux", usuario.getTop_aux());
            request.getRequestDispatcher("views/usuarios/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
     
      private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("views/usuarios/create.jsp").forward(request, response);
    }

    private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Usuario usuario = obtenerUsuarios(request);
            int result = UsuarioDAL.crear(usuario);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro registrar un nuevo registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
     private void requestObtenerPorId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Usuario usuario = obtenerUsuarios(request);
            Usuario usuario_result = UsuarioDAL.obtenerPorId(usuario);
            if (usuario_result.getId() > 0) {
                Rol rol = new Rol();
                rol.setId(usuario_result.getRolid());
                usuario_result.setRol(RolDAL.obtenerPorId(rol));
                request.setAttribute("usuario", usuario_result);
            } else {
                Utilidad.enviarError("El Id:" + usuario_result.getId() + " no existe en la tabla de Usuario", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
     
     private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("views/usuarios/edit.jsp").forward(request, response);
    }

    private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Usuario usuario = obtenerUsuarios(request);
            int result = UsuarioDAL.modificar(usuario);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro actualizar el registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void doGetRequestDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("views/usuarios/details.jsp").forward(request, response);
    }

    private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("views/usuarios/delete.jsp").forward(request, response);
    }
    
      private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Usuario usuario = obtenerUsuarios(request);
            int result = UsuarioDAL.eliminar(usuario);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro eliminar el registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionUser.cerrarSession(request);
        request.getRequestDispatcher("views/usuarios/login.jsp").forward(request, response);
    }
    
    private void doPostRequestLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Usuario usuario = obtenerUsuarios(request);
            Usuario usuario_auth = UsuarioDAL.login(usuario);
            if (usuario_auth.getId() != 0 && usuario_auth.getLogin().equals(usuario.getLogin())) {
                Rol rol = new Rol();
                rol.setId(usuario_auth.getRolid());
                usuario_auth.setRol(RolDAL.obtenerPorId(rol));
                SessionUser.autenticarUser(request, usuario_auth);
                response.sendRedirect("Home");
            } else {
                request.setAttribute("error", "Credenciales incorrectas");
                request.setAttribute("accion", "login");
                doGetRequestLogin(request, response);
            }
        } catch (Exception ex) {
            request.setAttribute("error", ex.getMessage());
        }
    }

    private void doGetRequestCambiarPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Usuario usuario = new Usuario();
            usuario.setLogin(SessionUser.getUser(request)); 
            Usuario usuario_result = UsuarioDAL.buscar(usuario).get(0);
            if (usuario_result.getId() > 0) {
                request.setAttribute("usuario", usuario_result);
                request.getRequestDispatcher("views/usuarios/cambiarPassword.jsp").forward(request, response);
            } else {
                Utilidad.enviarError("El Id:" + usuario_result.getId() + " no existe en la tabla de Usuario", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void doPostRequestCambiarPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Usuario usuario = obtenerUsuarios(request);
            String passActual = Utilidad.getParameter(request, "passwordActual", "");
            int result = UsuarioDAL.cambiarPassword(usuario, passActual);
            if (result != 0) {
                response.sendRedirect("Usuario?accion=login");
            } else {
                Utilidad.enviarError("No se logro cambiar el password", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = Utilidad.getParameter(request, "accion", "index");
        if (accion.equals("login")) {
            request.setAttribute("accion", accion);
            doGetRequestLogin(request, response);
        } else { 
            SessionUser.authorize(request, response, () -> {
                switch (accion) {
                    case "index":
                        request.setAttribute("accion", accion);
                        doGetRequestIndex(request, response);
                        break;
                    case "create":
                        request.setAttribute("accion", accion);
                        doGetRequestCreate(request, response);
                        break;
                    case "edit":
                        request.setAttribute("accion", accion);
                        doGetRequestEdit(request, response);
                        break;
                    case "delete":
                        request.setAttribute("accion", accion);
                        doGetRequestDelete(request, response);
                        break;
                    case "details":
                        request.setAttribute("accion", accion);
                        doGetRequestDetails(request, response);
                        break;
                    case "cambiarpass":
                        request.setAttribute("accion", accion);
                        doGetRequestCambiarPassword(request, response);
                        break;
                    default:
                        request.setAttribute("accion", accion);
                        doGetRequestIndex(request, response);
                }
            });
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = Utilidad.getParameter(request, "accion", "index");
        if (accion.equals("login")) {
            request.setAttribute("accion", accion);
            doPostRequestLogin(request, response);
        } else { 
            SessionUser.authorize(request, response, () -> {
                switch (accion) {
                    case "index":
                        request.setAttribute("accion", accion);
                        doPostRequestIndex(request, response);
                        break;
                    case "create":
                        request.setAttribute("accion", accion);
                        doPostRequestCreate(request, response);
                        break;
                    case "edit":
                        request.setAttribute("accion", accion);
                        doPostRequestEdit(request, response);
                        break;
                    case "delete":
                        request.setAttribute("accion", accion);
                        doPostRequestDelete(request, response);
                        break;
                    case "cambiarpass":
                        request.setAttribute("accion", accion);
                        doPostRequestCambiarPassword(request, response);
                        break;
                    default:
                        request.setAttribute("accion", accion);
                        doGetRequestIndex(request, response);
                }
            });
        }
    }
// </editor-fold>
    
}