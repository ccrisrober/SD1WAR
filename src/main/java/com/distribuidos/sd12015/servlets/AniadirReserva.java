/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.distribuidos.sd12015.servlets;

import com.distribuidos.sd12015.data.ClaseConFechaEntradaYSalidaYNif;
import com.distribuidos.sd12015.data.ClaseConOkYDuple;
import com.distribuidos.sd12015.models.Huesped;
import com.distribuidos.sd12015.models.Reserva;
import com.distribuidos.sd12015.rest.ServicioREST;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Cristian
 */
public class AniadirReserva extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("huespeds", (List<Huesped>) GenericHttpServlet.miStream.fromXML(GenericHttpServlet.sr.getHuespeds()));
        request.setAttribute("reserva", new Reserva());
        request.getRequestDispatcher("WEB-INF/views/reservas/add.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> errors = new LinkedList<String>();
        try {
            request.setCharacterEncoding("UTF-8");
            String initDate = request.getParameter("reserva.fechaEntrada");
            String finalDate = request.getParameter("reserva.fechaSalida");
            String NIF = request.getParameter("reserva.NIF");
            Date d1 = ServicioREST.strToDate(initDate);
            Date d2 = ServicioREST.strToDate(finalDate);
            if(d2.compareTo(d1) < 1) {
                errors.add("La fecha de salida debe ser posterior a la fecha de inicio");
            }
            boolean haveErrors = !errors.isEmpty();
            if(!haveErrors) {
                ClaseConFechaEntradaYSalidaYNif fesn = new ClaseConFechaEntradaYSalidaYNif(NIF, d1, d2);
                String addstr = GenericHttpServlet.sr.addReserva(GenericHttpServlet.miStream.toXML(fesn));
                ClaseConOkYDuple addReserva = (ClaseConOkYDuple) GenericHttpServlet.miStream.fromXML(addstr);
                if(addReserva.isOk()) {
                    response.sendRedirect("./VerReserva?NIF=" + addReserva.getDuple().getF() + "&fechaInicio=" + ServicioREST.dateToStr(addReserva.getDuple().getE()));
                }
                //TODO: Y ahora??
            } else {
                request.setAttribute("huespeds", (List<Huesped>) GenericHttpServlet.miStream.fromXML(GenericHttpServlet.sr.getHuespeds()));
                request.setAttribute("reserva", new Reserva(NIF, -1, d2, d2));
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("WEB-INF/views/reservas/add.jsp").forward(request, response);
            }
        } catch (ParseException ex) {
            errors.add(ex.getMessage());
            errors.add("Error al convertir fechas");
        }
    }
}
