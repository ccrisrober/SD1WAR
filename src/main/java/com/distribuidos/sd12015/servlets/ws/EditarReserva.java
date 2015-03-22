/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.distribuidos.sd12015.servlets.ws;

import com.distribuidos.sd12015.data.ClaseConError;
import com.distribuidos.sd12015.data.ClaseConReservaYNif;
import com.distribuidos.sd12015.exceptions.NotFoundException;
import com.distribuidos.sd12015.models.Reserva;
import com.distribuidos.sd12015.rest.ServicioREST;
import com.distribuidos.sd12015.servlets.GenericHttpServlet;
import static com.distribuidos.sd12015.servlets.GenericHttpServlet.miStream;
import static com.distribuidos.sd12015.servlets.GenericHttpServlet.sr;
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
public class EditarReserva extends HttpServlet {

    protected static List<String> params;
    protected static final String OLDNIF = "reserva.oldNIF";
    protected static final String NIF = "reserva.NIF";
    protected static final String HABITACION = "reserva.habitacion";
    protected static final String FECHAIN = "reserva.fechaInicio";
    protected static final String FECHAOUT = "reserva.fechaSalida";

    static {
        params = new LinkedList<>();
        params.add(NIF);
        params.add(OLDNIF);
        params.add(HABITACION);
        params.add(FECHAIN);
        params.add(FECHAOUT);
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
        response.setContentType("text/xml;charset=UTF-8");
        Map<String, String> values = new HashMap<>();
        for (String p : params) {
            String value = request.getParameter(p);
            if (value == null || value.isEmpty()) {
                try (PrintWriter out = response.getWriter()) {
                    ClaseConError error = new ClaseConError(401, "No estás autorizado para ver esta página");
                    String errorStr = GenericHttpServlet.miStream.toXML(error);
                    out.append(errorStr);
                }
                return;
            }
            values.put(p, value);
        }

        try (PrintWriter out = response.getWriter()) {
            int hab = Integer.parseInt(values.get(HABITACION));
            Date fin = ServicioREST.strToDate(values.get(FECHAIN));
            Date fout = ServicioREST.strToDate(values.get(FECHAOUT));
            ClaseConReservaYNif rn = new ClaseConReservaYNif(new Reserva(values.get(NIF), hab, fin, fout), values.get(OLDNIF));
            String setReserva = sr.setReserva(miStream.toXML(rn));
            out.write(setReserva);
        } catch (ParseException | NotFoundException ex) {
            try (PrintWriter out = response.getWriter()) {
                ClaseConError error = new ClaseConError(500, "Servicio no encontrado");
                String errorStr = GenericHttpServlet.miStream.toXML(error);
                out.append(errorStr);
            }
        }
    }
}
