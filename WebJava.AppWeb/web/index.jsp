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
            <div class="row">
                <div class="col l12 s12">
                    <h1 class="bg">Bienvenidos</h1> 
                    <span>Al sistema para aprender a como colocarle seguridad a sus aplicaciones web</span> 
                </div>
            </div> 
            
            <div class="mb-3">
                <label for="exampleFormControlInput1" class="form-label">Email address</label>
                <input type="email" class="form-control" id="exampleFormControlInput1" placeholder="name@example.com">
            </div>
            <div class="mb-3">
                <label for="exampleFormControlTextarea1" class="form-label">Example textarea</label>
                <textarea class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
            </div>

            
            
        </main>
        <jsp:include page="/views/shared/footerBody.jsp" />
    </body>
</html>