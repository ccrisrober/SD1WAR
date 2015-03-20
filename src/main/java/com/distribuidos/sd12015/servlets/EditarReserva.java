/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.distribuidos.sd12015.servlets;

import com.distribuidos.sd12015.data.ClaseConFechaEntradaYSalidaYNif;
import com.distribuidos.sd12015.data.ClaseConFechaYNif;
import com.distribuidos.sd12015.data.ClaseConOkYDuple;
import com.distribuidos.sd12015.data.ClaseConReservaYNif;
import com.distribuidos.sd12015.exceptions.NotFoundException;
import com.distribuidos.sd12015.models.Huesped;
import com.distribuidos.sd12015.models.Reserva;
import com.distribuidos.sd12015.rest.ServicioREST;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Cristian
 */
public class EditarReserva extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            ClaseConFechaYNif fn = new ClaseConFechaYNif(
                    ServicioREST.strToDate(request.getParameter("fechaInicio")),
                    request.getParameter("NIF"));
            String reservastr = GenericHttpServlet.sr.getReserva(GenericHttpServlet.miStream.toXML(fn));
            Reserva r = (Reserva) GenericHttpServlet.miStream.fromXML(reservastr);
            if(r == null) {
                request.getSession().setAttribute("error", true);
                request.getSession().setAttribute("msg", "Reserva no encontrada");
                response.sendRedirect("./Reservas");
                return;
            }
            request.setAttribute("reserva", r);
            request.setAttribute("oldNIF", r.getNIF());
            request.setAttribute("huespeds", (List<Huesped>) GenericHttpServlet.miStream.fromXML(GenericHttpServlet.sr.getHuespeds()));
            request.getRequestDispatcher("WEB-INF/views/reservas/edit.jsp").forward(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(EditarReserva.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotFoundException ex) {
            request.getSession().setAttribute("error", true);
            request.getSession().setAttribute("msg", "Reserva no encontrada");
            response.sendRedirect("./Reservas");
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> errors = new LinkedList<>();
        try {
            request.setCharacterEncoding("UTF-8");
            String oldNIF = request.getParameter("oldNIF");
            String habitacion = request.getParameter("reserva.habitacion");
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
                ClaseConReservaYNif fesn = new ClaseConReservaYNif(new Reserva(NIF, Integer.parseInt(habitacion), d1, d2), oldNIF);
                String addstr = GenericHttpServlet.sr.setReserva(GenericHttpServlet.miStream.toXML(fesn));
                ClaseConOkYDuple editReserva = (ClaseConOkYDuple) GenericHttpServlet.miStream.fromXML(addstr);
                if(editReserva.isOk()) {
                    response.sendRedirect("./VerReserva?NIF=" + editReserva.getDuple().getF() + "&fechaInicio=" + ServicioREST.dateToStr(editReserva.getDuple().getE()));
                } else {
                    request.getSession().setAttribute("error", true);
                    request.getSession().setAttribute("msg", "No se ha podido añadir");
                    response.sendRedirect("./Reservas");
                }
            } else {
                request.setAttribute("huespeds", (List<Huesped>) GenericHttpServlet.miStream.fromXML(GenericHttpServlet.sr.getHuespeds()));
                request.setAttribute("reserva", new Reserva(NIF, -1, d1, d2));
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("WEB-INF/views/reservas/edit.jsp").forward(request, response);
            }
        } catch (ParseException ex) {
            errors.add(ex.getMessage());
            errors.add("Error al convertir fechas");
        } catch (NotFoundException ex) {
            errors.add("Reserva no encontrada");
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("WEB-INF/views/reservas/edit.jsp").forward(request, response);
        }
    }

}
