<%-- 
    Document   : calcular
    Created on : 15/05/2016, 11:19:12 AM
    Author     : christianmtr
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="org.jgap.Configuration"%>
<%@page import="org.jgap.IChromosome"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="pe.christianmtr.cambiomonedas_web.cambiador.CambioMinimo, pe.christianmtr.cambiomonedas_web.cambiador.CambioMinimoFuncionAptitud" %>
<%
    IChromosome cromosomaMasApto;

    String error = null;

    Double fitness = 0.0;
    int sol5 = 0;
    int sol2 = 0;
    int sol1 = 0;
    int c50 = 0;
    int c20 = 0;
    int c10 = 0;
    int centimos = 0;
    int monedasUsadas = 0;
    long tiempo = 0;

    int t = 0;

    try {
        t = Integer.parseInt(request.getParameter("monto"));
        if (t < 1 || t >= CambioMinimoFuncionAptitud.MAX_MONTO) {
            error = "El monto excede el mímino o máximo permitido.";
        } else {
            try {
                cromosomaMasApto = CambioMinimo.calcularCambioMinimo(t);
            } catch (Exception e) {
            }
        }
    } catch (Exception e) {
    }
    Configuration.reset();
    try {
        t = Integer.parseInt(request.getParameter("monto"));
        if (t < 1 || t >= CambioMinimoFuncionAptitud.MAX_MONTO) {
            error = "El monto excede el mímino o máximo permitido.";
        } else {
            try {
                cromosomaMasApto = CambioMinimo.calcularCambioMinimo(t);
                fitness = cromosomaMasApto.getFitnessValue();
                sol5 = CambioMinimoFuncionAptitud.getNumeroDeComendasDeGen(cromosomaMasApto, 0);
                sol2 = CambioMinimoFuncionAptitud.getNumeroDeComendasDeGen(cromosomaMasApto, 1);
                sol1 = CambioMinimoFuncionAptitud.getNumeroDeComendasDeGen(cromosomaMasApto, 2);
                c50 = CambioMinimoFuncionAptitud.getNumeroDeComendasDeGen(cromosomaMasApto, 3);
                c20 = CambioMinimoFuncionAptitud.getNumeroDeComendasDeGen(cromosomaMasApto, 4);
                c10 = CambioMinimoFuncionAptitud.getNumeroDeComendasDeGen(cromosomaMasApto, 5);
                centimos = CambioMinimoFuncionAptitud.montoCambioMoneda(cromosomaMasApto);
                monedasUsadas = CambioMinimoFuncionAptitud.getNumeroTotalMonedas(cromosomaMasApto);
                tiempo = CambioMinimo.tiempoTotal;
            } catch (Exception e) {
                error = "Error al cargar >> CambioMinimo.calcularCambioMinimo(t)</br>" + e;
            }
        }
    } catch (Exception e) {
        error = "Error en los parámetros" + e;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cambio monedas - Algoritmos genéticos</title>

        <link href="recursos/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <script src="recursos/bootstrap/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row center-block">
                <div class="col-lg-6 col-lg-offset-3 col-md-6 col-md-offset-3 col-sm-12 col-xs-12 text-center">
                    <div class="row">
                        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <img src="recursos/uss.png" class="img-responsive center-block" alt="Logo USS">
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-lg-12 col-md-12">
                            <h2>Sistemas inteligentes</h2>
                            <h3>Dr. Jorge Gutierrez Gutierrez</h3>
                            <h4>Christian M. Torres Romero</h4>
                            <h4>Irvin Castro Fernandez</h4>
                        </div>
                    </div>
                    <hr>
                </div>
            </div>
            <div class="row center-block">
                <div class="col-lg-6 col-lg-offset-3 col-md-6 col-md-offset-3 col-sm-12 col-xs-12">
                    <h3 class="text-center">Resultado</h3>
                    <div class="table-responsive">
                        <table class="table">
                            <tbody>
                                <%
                                    out.print("<tr><td>");
                                    out.print("Monto cambiado</td><td colspan='2'>" + t);
                                    out.print("</td></tr>");
                                    out.print("<tr><td>");
                                    out.print("Fitness</td><td colspan='3'>" + fitness);
                                    out.print("</td></tr>");
                                    out.print("<tr><td colspan='2'>");
                                    out.print("Distribución de monedas.</td>");
                                    out.print("</td></tr>");
                                    out.print("<tr><td>");
                                    if (sol5 > 0) {
                                        out.print("5 Soles</td><td>" + sol5);
                                        out.print("</td><td>");
                                        for (int i = 1; i <= sol5; i++) {
                                            out.print("<img src='recursos/Cinco_soles.png' class='img-responsive' alt='Moneda de 5 soles'>");
                                        }
                                    } else {
                                        out.print("5 Soles</td><td colspan='2'>" + sol5);
                                    }
                                    out.print("</td></tr>");
                                    out.print("<tr><td>");
                                    if (sol2 > 0) {
                                        out.print("2 Soles</td><td>" + sol2);
                                        out.print("</td><td>");
                                        for (int i = 1; i <= sol2; i++) {
                                            out.print("<img src='recursos/dos_soles.png' class='img-responsive' alt='Moneda de 2 soles'>");
                                        }
                                    } else {
                                        out.print("2 Soles</td><td colspan='2'>" + sol2);
                                    }
                                    out.print("</td></tr>");
                                    out.print("<tr><td>");
                                    if (sol1 > 0) {
                                        out.print("1 Sol</td><td>" + sol1);
                                        out.print("</td><td>");
                                        for (int i = 1; i <= sol1; i++) {
                                            out.print("<img src='recursos/un_sol.png' class='img-responsive' alt='Moneda de 1 sol'>");
                                        }
                                    } else {
                                        out.print("1 Sol</td><td colspan='2'>" + sol1);
                                    }
                                    out.print("</td></tr>");
                                    out.print("<tr><td>");
                                    if (c50 > 0) {
                                        out.print("50 centimos</td><td>" + c50);
                                        out.print("</td><td>");
                                        for (int i = 1; i <= c50; i++) {
                                            out.print("<img src='recursos/50_cent.png' class='img-responsive' alt='Moneda de 50 centimos'>");
                                        }
                                    } else {
                                        out.print("50 centimos</td><td colspan='2'>" + c50);
                                    }
                                    out.print("</td></tr>");
                                    out.print("<tr><td>");
                                    if (c20 > 0) {
                                        out.print("20 centimos</td><td>" + c20);
                                        out.print("</td><td>");
                                        for (int i = 1; i <= c20; i++) {
                                            out.print("<img src='recursos/20_cent.png' class='img-responsive' alt='Moneda de 20 centimos'>");
                                        }
                                    } else {
                                        out.print("20 centimos</td><td colspan='2'>" + c20);
                                    }
                                    out.print("</td></tr>");
                                    out.print("<tr><td>");
                                    if (c10 > 0) {
                                        out.print("10 centimos</td><td>" + c10);
                                        out.print("</td><td>");
                                        for (int i = 1; i <= c10; i++) {
                                            out.print("<img src='recursos/10_cent.png' class='img-responsive' alt='Moneda de 10 centimos'>");
                                        }
                                    } else {
                                        out.print("10 centimos</td><td colspan='2'>" + c10);
                                    }
                                    out.print("</td></tr>");
                                    out.print("<tr><td>");
                                    out.print("Monto en monedas</td><td colspan='2'>" + centimos);
                                    out.print("</td></tr>");
                                    out.print("<tr><td>");
                                    out.print("N° monedasusadas</td><td colspan='2'>" + monedasUsadas);
                                    out.print("</td></tr>");
                                    out.print("<tr><td>");
                                    out.print("Tiempo de evolución</td><td colspan='2'>" + tiempo + "ms");
                                    out.print("</td></tr>");
                                %>
                            </tbody>
                        </table>
                    </div>
                    <hr>
                    <div class="container-fluid">
                        <a href="/CambioMonedas_web/" class="btn btn-primary btn-lg center-block">Regresar</a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
