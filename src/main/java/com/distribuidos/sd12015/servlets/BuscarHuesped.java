/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.distribuidos.sd12015.servlets;

import com.distribuidos.sd12015.data.ClaseConApellido;
import com.distribuidos.sd12015.data.ClaseConNombre;
import com.distribuidos.sd12015.models.Huesped;
import static com.distribuidos.sd12015.servlets.GenericHttpServlet.miStream;
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
public class BuscarHuesped extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String type = request.getParameter("type");
        String value = request.getParameter("value");
        
        System.out.println(value);
        
        List<Huesped> huespeds = null;
        if (type.compareToIgnoreCase("apellidos") == 0) {
            ClaseConApellido ap = new ClaseConApellido(value);
            String res = GenericHttpServlet.sr.findByApellidos(miStream.toXML(ap));
            huespeds = (List<Huesped>) miStream.fromXML(res);
        } else if (type.compareToIgnoreCase("nombre") == 0) {
            ClaseConNombre nm = new ClaseConNombre(value);
            String res = GenericHttpServlet.sr.findByName(miStream.toXML(nm));
            huespeds = (List<Huesped>) miStream.fromXML(res);
        } else {
            huespeds = new LinkedList<Huesped>();
        }
        
        request.setAttribute("huespeds", huespeds);
        request.getRequestDispatcher("WEB-INF/views/huespeds/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

}
