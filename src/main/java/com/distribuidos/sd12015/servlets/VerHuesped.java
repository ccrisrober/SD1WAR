/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.distribuidos.sd12015.servlets;

import com.distribuidos.sd12015.data.ClaseConNif;
import com.distribuidos.sd12015.exceptions.NotFoundException;
import com.distribuidos.sd12015.models.Huesped;
import static com.distribuidos.sd12015.servlets.GenericHttpServlet.miStream;
import static com.distribuidos.sd12015.servlets.GenericHttpServlet.sr;
import java.io.IOException;
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
public class VerHuesped extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ClaseConNif nif = new ClaseConNif(request.getParameter("NIF"));
            String huespedStr = sr.getHuesped(miStream.toXML(nif));
            Huesped h = (Huesped) miStream.fromXML(huespedStr);
            response.setContentType("text/html;charset=UTF-8");
            if (h == null) {
                request.getSession().setAttribute("error", true);
                request.getSession().setAttribute("msg", "No se ha encontrado huesped");
                response.sendRedirect("./Huespeds");
            }
            request.setAttribute("huesped", h);
            request.getRequestDispatcher("WEB-INF/views/huespeds/view.jsp").forward(request, response);
        } catch (NotFoundException ex) {
            request.getSession().setAttribute("error", true);
            request.getSession().setAttribute("msg", "No se ha encontrado huesped");
            response.sendRedirect("./Huespeds");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
