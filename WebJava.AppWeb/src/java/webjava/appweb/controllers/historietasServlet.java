package webjava.appweb.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import webjava.accesoadatos.HistorietaDAL;
import webjava.entidadesdenegocio.*;
import webjava.appweb.utils.*;

@WebServlet(name = "historietasServlet", urlPatterns = {"/historietas"})
public class historietasServlet extends HttpServlet {

    private Historieta obtenerHistorieta(HttpServletRequest request) {
    String accion = Utilidad.getParameter(request, "accion", "index");
    Historieta historieta = new Historieta();
    historieta.setAutor(Utilidad.getParameter(request, "autor", ""));
    historieta.setNombre(Utilidad.getParameter(request, "nombre", ""));
    historieta.setDescripcion(Utilidad.getParameter(request, "descripcion", ""));
    historieta.setPrecio(Utilidad.getParameter(request, "precio", ""));
    historieta.setPrecioanterior(Utilidad.getParameter(request, "precioanterior", ""));
    historieta.setLink(Utilidad.getParameter(request, "link", ""));
    historieta.setEditorioal(Utilidad.getParameter(request, "editorial", ""));
    historieta.setEdicion(Byte.parseByte(Utilidad.getParameter(request, "edicion", "")));
    historieta.setFechapublicacion(accion);
    historieta.setCantidadpag(Byte.parseByte(Utilidad.getParameter(request, "cantidadpg", "")));
    historieta.setImagen(Utilidad.getParameter(request, "imagen", ""));
    
    if (accion.equals("index")) {
        historieta.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
        historieta.setTop_aux(historieta.getTop_aux() == 0 ? Integer.MAX_VALUE : historieta.getTop_aux());
        }
        return historieta;
    }
    
      private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Historieta historieta = new Historieta();
            historieta.setTop_aux(10);
            ArrayList<Historieta> historietas = HistorietaDAL.buscar(historieta);
            request.setAttribute("usuarios", historietas);
            request.setAttribute("top_aux", historieta.getTop_aux());
            request.getRequestDispatcher("Views/historietas/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
      
      private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Historieta historieta = obtenerHistorieta(request);
            ArrayList<Historieta> historietas = HistorietaDAL.buscar(historieta);
            request.setAttribute("usuarios", historietas);
            request.setAttribute("top_aux", historieta.getTop_aux());
            request.getRequestDispatcher("Views/historietas/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
     
      private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/historietas/create.jsp").forward(request, response);
    }
      
       private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Historieta historieta = obtenerHistorieta(request);
            int result = HistorietaDAL.crear(historieta);
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
            Historieta historieta = obtenerHistorieta(request);
            Historieta historieta_result = HistorietaDAL.obtenerPorId(historieta);
            if (historieta_result.getId() > 0) {
                Historieta historiet = new Historieta();
                historiet.setId(historieta_result.getId());
                historieta_result.setHistorietas(HistorietaDAL.obtenerPorId(historiet));
                request.setAttribute("usuario", historieta_result);
            } else {
                Utilidad.enviarError("El Id:" + historieta_result.getId() + " no existe en la tabla de Usuario", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
     
     private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/historietas/edit.jsp").forward(request, response);
    }

    private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Historieta historieta = obtenerHistorieta(request);
            int result = HistorietaDAL.modificar(historieta);
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
        request.getRequestDispatcher("Views/historietas/details.jsp").forward(request, response);
    }

    private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/historietas/delete.jsp").forward(request, response);
    }
    
      private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Historieta historieta = obtenerHistorieta(request);
            int result = HistorietaDAL.eliminar(historieta);
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
        SessionUser.authorize(request, response, () -> {
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
        });
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionUser.authorize(request, response, () -> {
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
        });
    }
// </editor-fold>
    
}
