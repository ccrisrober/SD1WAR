/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.distribuidos.sd12015.servlets;

import com.distribuidos.sd12015.data.ClaseConFechaYNif;
import com.distribuidos.sd12015.data.ClaseConOk;
import com.distribuidos.sd12015.rest.ServicioREST;
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
public class EliminarReserva extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String NIF = request.getParameter("NIF");
            String fechaInicio = request.getParameter("fechaInicio");
            ClaseConFechaYNif fnif = new ClaseConFechaYNif(ServicioREST.strToDate(fechaInicio), NIF);
            String str = GenericHttpServlet.miStream.toXML(fnif);
            String okstr = GenericHttpServlet.sr.deleteReserva(str);
            ClaseConOk removeHuesped = (ClaseConOk) GenericHttpServlet.miStream.fromXML(okstr);
            
            String message;
            boolean isOk = removeHuesped.isOk();
            if(isOk) {
                message = "Se ha borrado con éxito";
            } else {
                message = "No se ha podido borrar";
            }
            request.getSession().setAttribute("ok", isOk);
            request.getSession().setAttribute("msg", message);
            response.sendRedirect("./Reservas");
        } catch (ParseException ex) {
            Logger.getLogger(EliminarReserva.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
