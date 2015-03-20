/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.distribuidos.sd12015.servlets;

import com.distribuidos.sd12015.data.ClaseConApellido;
import com.distribuidos.sd12015.data.ClaseConFecha;
import com.distribuidos.sd12015.data.ClaseConNombre;
import com.distribuidos.sd12015.models.Huesped;
import com.distribuidos.sd12015.models.Reserva;
import com.distribuidos.sd12015.rest.ServicioREST;
import static com.distribuidos.sd12015.servlets.GenericHttpServlet.miStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
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
public class BuscarReserva extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            String value = request.getParameter("value");
            
            System.out.println(value);
            
            List<Reserva> reservas = null;
            
            ClaseConFecha f = new ClaseConFecha(ServicioREST.strToDate(value));
            String s = GenericHttpServlet.sr.findByFechaEntrada(miStream.toXML(f));
            reservas = (List<Reserva>) miStream.fromXML(s);
            
            request.setAttribute("reservas", reservas);
            request.getRequestDispatcher("WEB-INF/views/reservas/index.jsp").forward(request, response);
        } catch (ParseException ex) {
            request.getSession().setAttribute("ok", false);
            request.getSession().setAttribute("msg", "Fecha de búsqueda incorrecta");
        
            request.setAttribute("huespeds", new LinkedList<Huesped>());
            request.getRequestDispatcher("WEB-INF/views/reservas/index.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

}
