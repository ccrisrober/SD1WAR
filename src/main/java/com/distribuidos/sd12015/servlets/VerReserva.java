/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.distribuidos.sd12015.servlets;

import com.distribuidos.sd12015.data.ClaseConFechaYNif;
import com.distribuidos.sd12015.exceptions.NotFoundException;
import com.distribuidos.sd12015.models.Reserva;
import com.distribuidos.sd12015.rest.ServicioREST;
import static com.distribuidos.sd12015.servlets.GenericHttpServlet.miStream;
import java.io.IOException;
import java.text.ParseException;
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
public class VerReserva extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // TODO: Controlar los errores
            response.setContentType("text/html;charset=UTF-8");
            ClaseConFechaYNif fn = new ClaseConFechaYNif(ServicioREST.strToDate(request.getParameter("fechaInicio")), request.getParameter("NIF"));

            String reservaStr = GenericHttpServlet.sr.getReserva(miStream.toXML(fn));
            Reserva r = (Reserva) GenericHttpServlet.miStream.fromXML(reservaStr);
            if (r == null) {
                //throws;
            }
            request.setAttribute("reserva", r);
            request.getRequestDispatcher("WEB-INF/views/reservas/view.jsp").forward(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(VerReserva.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotFoundException ex) {
            Logger.getLogger(VerReserva.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
