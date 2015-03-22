/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.distribuidos.sd12015.servlets.ws;

import com.distribuidos.sd12015.data.ClaseConError;
import com.distribuidos.sd12015.data.ClaseConFechaYNif;
import com.distribuidos.sd12015.rest.ServicioREST;
import com.distribuidos.sd12015.servlets.GenericHttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
        response.setContentType("text/xml;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            ClaseConError error = new ClaseConError(404, "La página no existe");
            String errorStr = GenericHttpServlet.miStream.toXML(error);
            out.append(errorStr);
        }
    }

    protected static List<String> params;
    protected static final String NIF = "reserva.NIF";
    protected static final String FECHA_INICIO = "reserva.fechaInicio";

    static {
        params = new LinkedList<>();
        params.add(NIF);
        params.add(FECHA_INICIO);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/xml;charset=UTF-8");
        Map<String, String> values = new HashMap<>();
        for (String p : params) {
            String value = request.getParameter(p);
            if (value == null || value.isEmpty()) {
                try (PrintWriter out = response.getWriter()) {
                    ClaseConError error = new ClaseConError(500, "Servicio no encontrado");
                    String errorStr = GenericHttpServlet.miStream.toXML(error);
                    out.append(errorStr);
                }
                return;
            }
            values.put(p, value);
        }

        try (PrintWriter out = response.getWriter()) {
            ClaseConFechaYNif fn = new ClaseConFechaYNif(ServicioREST.strToDate(values.get(FECHA_INICIO)),
                    values.get(NIF));
            String okstr = GenericHttpServlet.sr.deleteReserva(GenericHttpServlet.miStream.toXML(fn));
            out.append(okstr);
        } catch (ParseException ex) {
            try (PrintWriter out = response.getWriter()) {
                ClaseConError error = new ClaseConError(403, "Error de acceso");
                String errorStr = GenericHttpServlet.miStream.toXML(error);
                out.append(errorStr);
            }
        }
    }
}
