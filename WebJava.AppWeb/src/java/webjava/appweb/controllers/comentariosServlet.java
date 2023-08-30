package webjava.appweb.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import webjava.accesoadatos.comentariosDAL;
import webjava.appweb.utils.SessionUser;
import webjava.appweb.utils.Utilidad;
import webjava.entidadesdenegocio.comentario;


@WebServlet(name = "comentariosServlet", urlPatterns = {"/comentarios"})
public class comentariosServlet extends HttpServlet {
    
    private comentario obtenerComentarios(HttpServletRequest request) {
    String accion = Utilidad.getParameter(request, "accion", "index");
    comentario Comentario = new comentario();
    if (accion.equals("create") == false) {
        Comentario.setId(Integer.parseInt(Utilidad.getParameter(request, "id", "0")));
    }
    Comentario.setContenido(Utilidad.getParameter(request, "contenido", ""));
        if (accion.equals("index")) {
            Comentario.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            Comentario.setTop_aux(Comentario.getTop_aux() == 0 ? Integer.MAX_VALUE : Comentario.getTop_aux());
        }
        
        return Comentario;
    }
    
   private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            comentario Comentario = new comentario();
            Comentario.setTop_aux(10);
            ArrayList<comentario> comentarios = comentariosDAL.buscar(Comentario);
            request.setAttribute("comentarios", comentarios);
            request.setAttribute("top_aux", Comentario.getTop_aux());             
            request.getRequestDispatcher("Views/comentario/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            comentario Comentario = obtenerComentarios(request);
            ArrayList<comentario> comentario = comentariosDAL.buscar(Comentario);
            request.setAttribute("comentario", comentario);
            request.setAttribute("top_aux", Comentario.getTop_aux());
            request.getRequestDispatcher("Views/comentario/index.jsp").forward(request, response);
        } catch (Exception ex) { 
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/comentario/create.jsp").forward(request, response);
    }
    
    private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            comentario Comentario = obtenerComentarios(request);
            int result = comentariosDAL.crear(Comentario);
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
            comentario Comentario = obtenerComentarios(request);
            comentario comentario_result = comentariosDAL.obtenerPorId(Comentario);
            if (comentario_result.getId() > 0) {
                request.setAttribute("comentario", comentario_result);
            } else {
                Utilidad.enviarError("El Id:" + Comentario.getId() + " no existe en la tabla de Rol", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/comentario/edit.jsp").forward(request, response);
    }
    
    private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            comentario Comentario = obtenerComentarios(request);
            int result = comentariosDAL.modificar(Comentario);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro actualizar el registro", request, response);
            }
        } catch (Exception ex) {
            // Enviar al jsp de error si hay un Exception
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void doGetRequestDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/comentario/details.jsp").forward(request, response);
    }
    
    private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/comentario/delete.jsp").forward(request, response);
    }
    
     private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            comentario Comentario = obtenerComentarios(request);
            int result = comentariosDAL.eliminar(Comentario);
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
 @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //SessionUser.authorize(request, response, () -> {
            String accion = Utilidad.getParameter(request, "accion", "index");
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
                default:
                    request.setAttribute("accion", accion);
                    doGetRequestIndex(request, response);
            }
        //});
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //SessionUser.authorize(request, response, () -> {
            String accion = Utilidad.getParameter(request, "accion", "index");
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
                default:
                    request.setAttribute("accion", accion);
                    doGetRequestIndex(request, response);
            }
       // });
    }
// </editor-fold>
    
}
