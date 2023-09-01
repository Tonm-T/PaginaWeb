<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="webjava.appweb.utils.*"%>
<%@page import="jakarta.servlet.http.HttpServletRequest"%>

<% /* if (SessionUser.isAuth(request) == false) {
         response.sendRedirect("Usuario?accion=login");
    }*/
%>
<!DOCTYPE html>
<html>
    <header class="header">
        <jsp:include page="/views/shared/title.jsp" />
        
    </header>
    <!-- Header End -->
        
    <body>
        <jsp:include page="/views/shared/headerBody.jsp" />  
        <main> 
                <!-- Header Section Begin -->
                
                
        <section class="text-gray-600 body-font">
            <div class="container px-5 py-24 mx-auto">
            <div class="flex flex-col text-center w-full mb-20">
              <h1 class="sm:text-3xl text-2xl font-medium title-font mb-4 text-gray-900">HOME</h1>
            </div>
            <div class="flex flex-wrap -m-4">
              <div class="lg:w-1/3 sm:w-1/2 p-4">
                <div class="flex relative">
                  <img alt="gallery" class="absolute inset-0 w-full h-full object-cover object-center" src="wwwroot/img/popular/popular-2.jpg">
                  <div class="px-8 py-10 relative z-10 w-full border-4 border-gray-200 bg-white opacity-0 hover:opacity-100">
                    <h2 class="tracking-widest text-sm title-font font-medium text-indigo-500 mb-1">SHOW ME </h2>
                    <a> <h1 class="title-font text-lg font-medium text-gray-900 mb-3">Ver registros</h1> </a>
                    <p class="leading-relaxed">Plataforma de Mangas, Historietas y libros. Plataforma de Mangas, Historietas y libros. Plataforma de Mangas, Historietas y libros.</p>
                  </div>
                </div>
              </div>
              <div class="lg:w-1/3 sm:w-1/2 p-4">
                <div class="flex relative">
                  <img alt="gallery" class="absolute inset-0 w-full h-full object-cover object-center" src="wwwroot/img/popular/popular-1.jpg">
                  <div class="px-8 py-10 relative z-10 w-full border-4 border-gray-200 bg-white opacity-0 hover:opacity-100">
                    <h2 class="tracking-widest text-sm title-font font-medium text-indigo-500 mb-1">SHOW ME</h2>
                    <a><h1 class="title-font text-lg font-medium text-gray-900 mb-3">Ver registros</h1></a>
                    <p class="leading-relaxed">Plataforma de Mangas, Historietas y libros. Plataforma de Mangas, Historietas y libros. Plataforma de Mangas, Historietas y libros.</p>
                  </div>
                </div>
              </div>
              <div class="lg:w-1/3 sm:w-1/2 p-4">
                <div class="flex relative">
                  <img alt="gallery" class="absolute inset-0 w-full h-full object-cover object-center" src="wwwroot/img/popular/popular-3.jpg">
                  <div class="px-8 py-10 relative z-10 w-full border-4 border-gray-200 bg-white opacity-0 hover:opacity-100">
                    <h2 class="tracking-widest text-sm title-font font-medium text-indigo-500 mb-1">SHOW ME</h2>
                    <a><h1 class="title-font text-lg font-medium text-gray-900 mb-3">Ver registros</h1></a>
                    <p class="leading-relaxed">Plataforma de Mangas, Historietas y libros. Plataforma de Mangas, Historietas y libros. Plataforma de Mangas, Historietas y libros.</p>
                  </div>
                </div>
              </div>
              <div class="lg:w-1/3 sm:w-1/2 p-4">
                <div class="flex relative">
                  <img alt="gallery" class="absolute inset-0 w-full h-full object-cover object-center" src="wwwroot/img/popular/popular-4.jpg">
                  <div class="px-8 py-10 relative z-10 w-full border-4 border-gray-200 bg-white opacity-0 hover:opacity-100">
                    <h2 class="tracking-widest text-sm title-font font-medium text-indigo-500 mb-1">SHOW ME</h2>
                    <a><h1 class="title-font text-lg font-medium text-gray-900 mb-3">SHOW ME</h1></a>
                    <p class="leading-relaxed">Plataforma de Mangas, Historietas y libros. Plataforma de Mangas, Historietas y libros. Plataforma de Mangas, Historietas y libros.</p>
                  </div>
                </div>
              </div>
              <div class="lg:w-1/3 sm:w-1/2 p-4">
                <div class="flex relative">
                  <img alt="gallery" class="absolute inset-0 w-full h-full object-cover object-center" src="wwwroot/img/popular/popular-5.jpg">
                  <div class="px-8 py-10 relative z-10 w-full border-4 border-gray-200 bg-white opacity-0 hover:opacity-100">
                    <h2 class="tracking-widest text-sm title-font font-medium text-indigo-500 mb-1">SHOW ME</h2>
                    <a><h1 class="title-font text-lg font-medium text-gray-900 mb-3">SHOW ME</h1></a>
                    <p class="leading-relaxed">Plataforma de Mangas, Historietas y libros. Plataforma de Mangas, Historietas y libros. Plataforma de Mangas, Historietas y libros.</p>
                  </div>
                </div>
              </div>
              <div class="lg:w-1/3 sm:w-1/2 p-4">
                <div class="flex relative">
                  <img alt="gallery" class="absolute inset-0 w-full h-full object-cover object-center" src="wwwroot/img/popular/popular-6.jpg">
                  <div class="px-8 py-10 relative z-10 w-full border-4 border-gray-200 bg-white opacity-0 hover:opacity-100">
                    <h2 class="tracking-widest text-sm title-font font-medium text-indigo-500 mb-1">SHOW ME</h2>
                    <a><h1 class="title-font text-lg font-medium text-gray-900 mb-3">Ver registros</h1></a>
                    <p class="leading-relaxed">Plataforma de Mangas, Historietas y libros. Plataforma de Mangas, Historietas y libros. Plataforma de Mangas, Historietas y libros.</p>
                  </div>
                </div>
              </div>
            </div>
            </div>
            </section>
           
        </main>
        <jsp:include page="/views/shared/footerBody.jsp" />
    </body>
</html>