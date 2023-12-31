package webjava.appweb.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServlet;

import java.io.IOException;
import java.util.ArrayList;
import webjava.accesoadatos.CategoriaDAL;
import jakarta.servlet.annotation.WebServlet;
import webjava.appweb.utils.SessionUser;
import webjava.entidadesdenegocio.Categoria;
import webjava.appweb.utils.*;

@WebServlet(name = "categoriaServlet", urlPatterns = {"/categoria"})
public class categoriaServlet extends HttpServlet {

    private Categoria obtenerCategoria(HttpServletRequest request) {
        String accion = Utilidad.getParameter(request, "accion", "index");
        Categoria Categoria = new Categoria();
        if (accion.equals("create") == false) {
            Categoria.setId(Integer.parseInt(Utilidad.getParameter(request, "id", "0")));
        }
        Categoria.setNombre(Utilidad.getParameter(request, "nombre", ""));
        if (accion.equals("index")) {
            Categoria.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            Categoria.setTop_aux(Categoria.getTop_aux() == 0 ? Integer.MAX_VALUE : Categoria.getTop_aux());
        }

        return Categoria;
    }

    private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Categoria Categoria = new Categoria();
            Categoria.setTop_aux(10);
            ArrayList<Categoria> categorias = CategoriaDAL.buscar(Categoria);
            request.setAttribute("categorias", categorias);
            request.setAttribute("top_aux", Categoria.getTop_aux());
            request.getRequestDispatcher("Views/categoria/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Categoria Categoria = obtenerCategoria(request);
            ArrayList<Categoria> categorias = CategoriaDAL.buscar(Categoria);
            request.setAttribute("categorias", categorias);
            request.setAttribute("top_aux", Categoria.getTop_aux());
            request.getRequestDispatcher("Views/categoria/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/categoria/create.jsp").forward(request, response);
    }

    private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Categoria Categoria = obtenerCategoria(request);
            int result = CategoriaDAL.crear(Categoria);
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
            Categoria Categoria = obtenerCategoria(request);
            Categoria categoria_result = CategoriaDAL.obtenerPorId(Categoria);
            if (categoria_result.getId() > 0) {
                request.setAttribute("categoria", categoria_result);
            } else {
                Utilidad.enviarError("El Id:" + Categoria.getId() + " no existe en la tabla de Categoria", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/categoria/edit.jsp").forward(request, response);
    }

    private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Categoria Categoria = obtenerCategoria(request);
            int result = CategoriaDAL.modificar(Categoria);
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
        request.getRequestDispatcher("Views/categoria/details.jsp").forward(request, response);
    }

    private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/categoria/delete.jsp").forward(request, response);
    }

    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Categoria Categoria = obtenerCategoria(request);
            int result = CategoriaDAL.eliminar(Categoria);
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
