/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.distribuidos.sd12015.servlets.ws;

import com.distribuidos.sd12015.data.ClaseConError;
import com.distribuidos.sd12015.data.ClaseConFechaYNif;
import com.distribuidos.sd12015.exceptions.NotFoundException;
import com.distribuidos.sd12015.rest.ServicioREST;
import com.distribuidos.sd12015.servlets.GenericHttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
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
            response.setContentType("text/xml;charset=UTF-8");

            String nif = request.getParameter("NIF");
            if (nif == null) {
                try (PrintWriter out = response.getWriter()) {
                    ClaseConError error = new ClaseConError(500, "Servicio no encontrado");
                    String errorStr = GenericHttpServlet.miStream.toXML(error);
                    out.append(errorStr);
                }
                return;
            }
            String date = request.getParameter("fechaInicio");
            if (date == null) {
                try (PrintWriter out = response.getWriter()) {
                    ClaseConError error = new ClaseConError(500, "Servicio no encontrado");
                    String errorStr = GenericHttpServlet.miStream.toXML(error);
                    out.append(errorStr);
                }
                return;
            }
            ClaseConFechaYNif fn = new ClaseConFechaYNif(ServicioREST.strToDate(date), nif);
            String reservaStr = GenericHttpServlet.sr.getReserva(GenericHttpServlet.miStream.toXML(fn));
            try (PrintWriter out = response.getWriter()) {
                out.append(reservaStr);
            }
        } catch (ParseException ex) {
            try (PrintWriter out = response.getWriter()) {
                ClaseConError error = new ClaseConError(403, "Error de acceso");
                String errorStr = GenericHttpServlet.miStream.toXML(error);
                out.append(errorStr);
            }
        } catch (NotFoundException ex) {
            try (PrintWriter out = response.getWriter()) {
                ClaseConError error = new ClaseConError(404, ex.getMessage());
                String errorStr = GenericHttpServlet.miStream.toXML(error);
                out.append(errorStr);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/xml;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            ClaseConError error = new ClaseConError(404, "La página no existe");
            String errorStr = GenericHttpServlet.miStream.toXML(error);
            out.append(errorStr);
        }
    }
}
