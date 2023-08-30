<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="webjava.appweb.utils.*"%>
<%@page import="jakarta.servlet.http.HttpServletRequest"%>

<% /* if (SessionUser.isAuth(request) == false) {
         response.sendRedirect("Usuario?accion=login");
    }*/
%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/views/shared/title.jsp" />
        <title>Principal</title>
    </head>
    <body>
        <jsp:include page="/views/shared/headerBody.jsp" />  
        <main class="container"> 

            <!-- AQUI EMPIEZA LA SECCION DE CATEGORIAS -->
            <div class="container-fluid py-5">
                <div class="container pt-5 pb-3">
                    <div class="text-center mb-5 ">
                        <h5 class="text-primary text-uppercase mb-3 ">
                            <span>
                                <i class="bi bi-wrench-adjustable">Administrador</i>
                            </span>
                        </h5>
                        <h1>Explora las categorias para mantenimiento</h1>
                    </div>
                    <div class="row">
                        <div class="col-lg-3 col-md-6 mb-4">
                            <div class="cat-item position-relative overflow-hidden rounded mb-2">
                                <img class="img-fluid" src="./lib/image/course-6.jpg" alt="">
                                <a class="cat-overlay text-white text-decoration-none" class="nav-link" asp-area="" asp-area="" asp-controller="Blogs" asp-action="Index">
                                    <h4 class="text-white font-weight-medium">Blogs</h4>
                                    <span class="adminletras">Ver registros</span>
                                </a>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6 mb-4">
                            <div class="cat-item position-relative overflow-hidden rounded mb-2">
                                <img class="img-fluid" src="./lib/image/cat-1.jpg" alt="">
                                <a class="cat-overlay text-white text-decoration-none" class="nav-link" asp-area="" asp-area="" asp-controller="Rol" asp-action="Index">
                                    <h4 class="text-white font-weight-medium">Roles</h4>
                                    <span class="adminletras">ver registros</span>
                                </a>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6 mb-4">
                            <div class="cat-item position-relative overflow-hidden rounded mb-2">
                                <img class="img-fluid" src="./lib/image/cat-5.jpg" alt="">
                                <a class="cat-overlay text-white text-decoration-none" class="nav-link" asp-area="" asp-area="" asp-controller="Usuario" asp-action="Index">
                                    <h4 class="text-white font-weight-medium">Usuarios</h4>
                                    <span class="adminletras">Ver registros</span>
                                </a>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6 mb-4">
                            <div class="cat-item position-relative overflow-hidden rounded mb-2">
                                <img class="img-fluid" src="./lib/image/cat-2.jpg" alt="">
                                <a class="cat-overlay text-white text-decoration-none" class="nav-link" asp-area="" asp-area="" asp-controller="Categoria" asp-action="Index">
                                    <h4 class="text-white font-weight-medium">Categorias</h4>
                                    <span class="adminletras">ver registros</span>
                                </a>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6 mb-4">
                            <div class="cat-item position-relative overflow-hidden rounded mb-2">
                                <img class="img-fluid" src="./lib/image/cat-3.jpg" alt="">
                                <a class="cat-overlay text-white text-decoration-none" class="nav-link" asp-area="" asp-controller="Curso" asp-action="Index">
                                    <h4 class="text-white font-weight-medium">Cursos</h4>
                                    <span class="adminletras">Ver registros</span>
                                </a>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6 mb-4">
                            <div class="cat-item position-relative overflow-hidden rounded mb-2">
                                <img class="img-fluid" src="./lib/image/cat-4.jpg" alt="">
                                <a class="cat-overlay text-white text-decoration-none" class="nav-link" asp-area="" asp-area="" asp-controller="Libros" asp-action="Index">
                                    <h4 class="text-white font-weight-medium">Libros </h4>
                                    <span class="adminletras">Ver registros</span>
                                </a>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6 mb-4">
                            <div class="cat-item position-relative overflow-hidden rounded mb-2">
                                <img class="img-fluid" src="./lib/image/cat-7.jpg" alt="">
                                <a class="cat-overlay text-white text-decoration-none" class="nav-link" asp-area="" asp-area="" asp-controller="Compras" asp-action="Index">
                                    <h4 class="text-white font-weight-medium">Compras</h4>
                                    <span class="adminletras">Ver registros</span>
                                </a>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6 mb-4">
                            <div class="cat-item position-relative overflow-hidden rounded mb-2">
                                <img class="img-fluid" src="./lib/image/cat-8.jpg" alt="">
                                <a class="cat-overlay text-white text-decoration-none" class="nav-link" asp-area="" asp-area="" asp-controller="CompraLibro" asp-action="Index">
                                    <h4 class="text-white font-weight-medium">Compras de libros</h4>
                                    <span class="adminletras">Ver registros</span>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- AQUI TERMINA LA SECCION DE CATEGORIAS -->
            
        </main>
        <jsp:include page="/views/shared/footerBody.jsp" />
    </body>
</html>