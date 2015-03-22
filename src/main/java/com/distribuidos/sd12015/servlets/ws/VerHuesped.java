/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.distribuidos.sd12015.servlets.ws;

import com.distribuidos.sd12015.data.ClaseConError;
import com.distribuidos.sd12015.data.ClaseConNif;
import com.distribuidos.sd12015.exceptions.NotFoundException;
import com.distribuidos.sd12015.servlets.GenericHttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Cristian
 */
public class VerHuesped extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ClaseConNif nif = new ClaseConNif(request.getParameter("NIF"));
            String huespedStr = GenericHttpServlet.sr.getHuesped(GenericHttpServlet.miStream.toXML(nif));
            String header = request.getHeader("Accept");
            response.setContentType("text/xml;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.append(huespedStr);
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
