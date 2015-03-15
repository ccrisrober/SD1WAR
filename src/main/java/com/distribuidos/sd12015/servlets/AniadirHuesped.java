/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.distribuidos.sd12015.servlets;

import com.distribuidos.sd12015.data.ClaseConOk;
import com.distribuidos.sd12015.models.Huesped;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Cristian
 */
public class AniadirHuesped extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("huesped", new Huesped());
        request.getRequestDispatcher("WEB-INF/views/huespeds/add.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("huesped.nombre");
        String surname = request.getParameter("huesped.apellidos");
        String NIF = request.getParameter("huesped.NIF");
        List<String> errors = new LinkedList<>();
        if (name.length() < 3) {
            errors.add("Mínimo 3 caracteres para nombre.");
        }
        if (name.length() > 20) {
            errors.add("Máximo 20 caracteres para nombre.");
        }
        if (surname.length() < 5) {
            errors.add("Mínimo 5 caracteres para apellidos.");
        }
        if (surname.length() > 30) {
            errors.add("Máximo 30 caracteres para apellidos.");
        }
        if (NIF.length() != 9) {
            errors.add("NIF de 9 caracteres.");
        }
        if (!errors.isEmpty()) {
            Huesped h = new Huesped(NIF, name, surname);
            request.setAttribute("huesped", h);
            request.setAttribute("oldNIF", request.getParameter("huesped.id"));
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("WEB-INF/views/huespeds/add.jsp").forward(request, response);
        } else {
            Huesped h = new Huesped(NIF, name, surname);
            String addstr = GenericHttpServlet.sr.addHuesped(GenericHttpServlet.miStream.toXML(h));
            ClaseConOk ok = (ClaseConOk) GenericHttpServlet.miStream.fromXML(addstr);
            if (ok.isOk()) {
                response.sendRedirect("./VerHuesped?NIF=" + NIF);
            } else {
                request.getSession().setAttribute("error", true);
                request.getSession().setAttribute("msg", "No se ha podido añadir");
                response.sendRedirect("./Huespeds");
            }
        }
    }
}
