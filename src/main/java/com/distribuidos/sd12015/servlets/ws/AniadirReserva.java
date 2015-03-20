/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.distribuidos.sd12015.servlets.ws;

import com.distribuidos.sd12015.data.ClaseConError;
import com.distribuidos.sd12015.data.ClaseConFechaEntradaYSalidaYNif;
import com.distribuidos.sd12015.data.ClaseConOkYDuple;
import com.distribuidos.sd12015.rest.ServicioREST;
import com.distribuidos.sd12015.servlets.GenericHttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
public class AniadirReserva extends HttpServlet {

    protected static List<String> params;
    protected static final String NIF = "reserva.NIF";
    protected static final String FECHAINICIO = "reserva.fechaInicio";
    protected static final String FECHAFIN = "reserva.fechaFin";

    static {
        params = new LinkedList<>();
        params.add(NIF);
        params.add(FECHAINICIO);
        params.add(FECHAFIN);
    }

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

            Date d1;
            try {
                d1 = ServicioREST.strToDate(values.get(FECHAINICIO));
            } catch (ParseException ex) {
                ClaseConError error = new ClaseConError(401, "Fecha inicio no parseable");
                out.write(GenericHttpServlet.miStream.toXML(error));
                return;
            }
            Date d2;
            try {
                d2 = ServicioREST.strToDate(values.get(FECHAFIN));
            } catch (ParseException ex) {
                ClaseConError error = new ClaseConError(401, "Fecha final no parseable");
                out.write(GenericHttpServlet.miStream.toXML(error));
                return;
            }
            ClaseConFechaEntradaYSalidaYNif fesn = new ClaseConFechaEntradaYSalidaYNif(NIF, d1, d2);
            String addstr = GenericHttpServlet.sr.addReserva(GenericHttpServlet.miStream.toXML(fesn));
            out.write(addstr);
        }
    }
}
