/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.distribuidos.sd12015.servlets;

import com.distribuidos.sd12015.data.ClaseConNif;
import com.distribuidos.sd12015.data.ClaseConNifYHuesped;
import com.distribuidos.sd12015.data.ClaseConOk;
import com.distribuidos.sd12015.exceptions.NotFoundException;
import com.distribuidos.sd12015.models.Huesped;
import java.io.IOException;
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
public class EditarHuesped extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            ClaseConNif nif = new ClaseConNif(request.getParameter("NIF"));
            String huespedstr = GenericHttpServlet.sr.getHuesped(GenericHttpServlet.miStream.toXML(nif));
            Huesped h = (Huesped) GenericHttpServlet.miStream.fromXML(huespedstr);
            if (h == null) {
                //throws;
            }
            request.setAttribute("huesped", h);
            request.setAttribute("oldNIF", h.getNIF());
            request.getRequestDispatcher("WEB-INF/views/huespeds/edit.jsp").forward(request, response);
        } catch (NotFoundException ex) {
            Logger.getLogger(EditarHuesped.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("huesped.nombre");
        String surname = request.getParameter("huesped.apellidos");
        String NIF = request.getParameter("huesped.NIF");
        List<String> errors = new LinkedList<String>();
        if(name.length() < 3) {
            errors.add("Mínimo 3 caracteres para nombre.");
        }
        if(name.length() > 20) {
            errors.add("Máximo 20 caracteres para nombre.");
        }
        if(surname.length() < 5) {
            errors.add("Mínimo 5 caracteres para apellidos.");
        }
        if(surname.length() > 30) {
            errors.add("Máximo 30 caracteres para apellidos.");
        }
        if(NIF.length() != 9) {
            errors.add("NIF de 9 caracteres.");
        }
        if(!errors.isEmpty()) {
            Huesped h = new Huesped(NIF, name, surname);
            request.setAttribute("huesped", h);
            request.setAttribute("oldNIF", request.getParameter("huesped.id"));
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("WEB-INF/views/huespeds/edit.jsp").forward(request, response);
        } else {
            ClaseConNifYHuesped nh = new ClaseConNifYHuesped(request.getParameter("huesped.id"), new Huesped(NIF, name, surname));

            String editstr = GenericHttpServlet.sr.setHuesped(GenericHttpServlet.miStream.toXML(nh));
            ClaseConOk edit = (ClaseConOk) GenericHttpServlet.miStream.fromXML(editstr);
            if(edit.isOk()) {
                response.sendRedirect("./VerHuesped?NIF=" + NIF);
            } else {
                // TODO: ERROR
            }
        }
    }
}
