package webjava.appweb.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import webjava.accesoadatos.rolDAL;
import webjava.entidadesdenegocio.*;
import webjava.appweb.utils.*;

@WebServlet(name = "historietasServlet", urlPatterns = {"/historietasServlet"})
public class historietasServlet extends HttpServlet {

    private historietas obtenerHistorieta(HttpServletRequest request) {
    String accion = Utilidad.getParameter(request, "accion", "index");
    historietas historieta = new historietas();
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
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet historietasServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet historietasServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
