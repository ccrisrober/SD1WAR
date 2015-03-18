/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.distribuidos.sd12015.servlets.ws;

import com.distribuidos.sd12015.data.ClaseConError;
import com.distribuidos.sd12015.models.Domicilio;
import com.distribuidos.sd12015.models.Huesped;
import com.distribuidos.sd12015.rest.ServicioREST;
import com.distribuidos.sd12015.servlets.GenericHttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
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
public class AniadirHuesped extends HttpServlet {

    protected static List<String> params;
    protected static final String NIF = "huesped.NIF";
    protected static final String NOMBRE = "huesped.nombre";
    protected static final String APELLIDOS = "huesped.apellidos";
    protected static final String NACIMIENTO = "huesped.nacimiento";

    protected static final String DIRECCION = "huesped.domicilio.direccion";
    protected static final String LOCALIDAD = "huesped.domicilio.localidad";
    protected static final String CODIGOPOSTAL = "huesped.domicilio.codigoPostal";
    protected static final String PROVINCIA = "huesped.domicilio.provincia";

    protected static final String TELEFONOFIJO = "huesped.telefonoFijo";
    protected static final String TELEFONOMOVIL = "huesped.telefonoMovil";
    protected static final String EMAIL = "huesped.email";

    static {
        params = new LinkedList<>();
        params.add(NIF);
        params.add(NOMBRE);
        params.add(APELLIDOS);

        params.add(NACIMIENTO);
        params.add(DIRECCION);
        params.add(LOCALIDAD);
        params.add(CODIGOPOSTAL);
        params.add(PROVINCIA);
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
                    ClaseConError error = new ClaseConError(500, "Servicio no encontrado");
                    String errorStr = GenericHttpServlet.miStream.toXML(error);
                    out.append(errorStr);
                }
                return;
            }
            values.put(p, value);
        }
        try (PrintWriter out = response.getWriter()) {
            Domicilio d = new Domicilio(values.get(DIRECCION), values.get(LOCALIDAD), Integer.parseInt(values.get(CODIGOPOSTAL)), values.get(PROVINCIA));
            Huesped h = new Huesped(values.get(NIF), values.get(NOMBRE), values.get(APELLIDOS), ServicioREST.strToDate(values.get(NACIMIENTO)), d);
            String fijo = request.getParameter(TELEFONOFIJO);
            String movil = request.getParameter(TELEFONOMOVIL);
            String email = request.getParameter(EMAIL);
            if (!fijo.isEmpty()) {
                h.setTelefonoFijo(request.getParameter(TELEFONOFIJO));
            }
            if (!movil.isEmpty()) {
                h.setTelefonoMovil(request.getParameter(TELEFONOMOVIL));
            }
            if (!email.isEmpty()) {
                h.setEmail(request.getParameter(EMAIL));
            }
            String addstr = GenericHttpServlet.sr.addHuesped(GenericHttpServlet.miStream.toXML(h));
            out.append(addstr);
        } catch (ParseException ex) {
            Logger.getLogger(AniadirHuesped.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
