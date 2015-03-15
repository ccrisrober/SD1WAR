/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.distribuidos.sd12015.servlets;

import com.distribuidos.sd12015.data.ClaseConNif;
import com.distribuidos.sd12015.data.ClaseConOk;
import com.distribuidos.sd12015.models.Huesped;
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
public class EliminarHuesped extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String NIF = request.getParameter("NIF");
        ClaseConNif nif = new ClaseConNif(NIF);
        String str = GenericHttpServlet.miStream.toXML(nif);
        String okstr = GenericHttpServlet.sr.removeHuesped(str);
        ClaseConOk removeHuesped = (ClaseConOk) GenericHttpServlet.miStream.fromXML(okstr);
        
        String message = "";
        boolean isOk = removeHuesped.isOk();
        if(isOk) {
            message = "Se ha borrado con éxito";
        } else {
            message = "No se ha podido borrar";
        }
        request.getSession().setAttribute("ok", isOk);
        request.getSession().setAttribute("msg", message);
        response.sendRedirect("./Huespeds");
    }
}
