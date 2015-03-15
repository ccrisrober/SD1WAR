/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.distribuidos.sd12015.servlets.ws;

import com.distribuidos.sd12015.data.ClaseConError;
import com.distribuidos.sd12015.servlets.GenericHttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
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
public class BuscarHuesped extends HttpServlet {

    protected static List<String> params;
    
    protected static final String TYPE = "search.type";
    protected static final String VALUE = "search.value";

    static {
        params = new LinkedList<String>();
        params.add(TYPE);
        params.add(VALUE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
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
        String res = null;
        if (values.get(TYPE).compareToIgnoreCase("apellidos") == 0) {
            res = GenericHttpServlet.sr.findByName(values.get(VALUE));
        } else if (values.get(TYPE).compareToIgnoreCase("nombre") == 0) {
            res = GenericHttpServlet.sr.findByName(values.get(VALUE));
        }
        if (res == null) {
            try (PrintWriter out = response.getWriter()) {
                ClaseConError error = new ClaseConError(500, "Servicio no encontrado");
                String errorStr = GenericHttpServlet.miStream.toXML(error);
                out.append(errorStr);
            }
            return;
        }
        try (PrintWriter out = response.getWriter()) {
            out.append(res);
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
