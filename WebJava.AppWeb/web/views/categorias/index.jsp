<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="webjava.entidadesdenegocio.Categoria"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>

<% ArrayList<Categoria> categorias = (ArrayList<Categoria>) request.getAttribute("categorias");
    int numPage = 1;
    int numReg = 10;
    int countReg = 0;
    if (categorias == null) {
        categorias = new ArrayList();
    } else if (categorias.size() > numReg) {
        double divNumPage = (double) categorias.size() / (double) numReg;
        numPage = (int) Math.ceil(divNumPage);
    }
    String strTop_aux = request.getParameter("top_aux");
    int top_aux = 10;
    if (strTop_aux != null && strTop_aux.trim().length() > 0) {
        top_aux = Integer.parseInt(strTop_aux);
    }
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


        <title>Lista de Contactos</title>

        <jsp:include page="/views/shared/title.jsp" />
    </head>
    <body>
        <jsp:include page="/views/shared/headerBody.jsp" />

        <main class="container">   
            <h5>Buscar categorias</h5>
            <form action="categorias" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <div class="row">
                    <div class="input-field col l6 s12">
                        <input  id="txtNombre" type="text" name="nombre">
                        <label for="txtNombre">Nombre</label>
                    </div>                   
                    <div class="input-field col l3 s12">                           
                    </div> 
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">search</i>Buscar</button>
                        <a href="Contacto?accion=create" class="waves-effect waves-light btn blue"><i class="material-icons right">add</i>Crear</a>                          
                    </div>
                </div>
            </form>
    </body>
</html>
