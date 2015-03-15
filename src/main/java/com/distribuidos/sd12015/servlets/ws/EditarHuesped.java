/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.distribuidos.sd12015.servlets.ws;

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
public class EditarHuesped extends HttpServlet {

    protected static List<String> params;
    protected static final String NIF = "huesped.NIF";
    protected static final String NOMBRE = "huesped.nombre";
    protected static final String APELLIDOS = "huesped.apellidos";
    protected static final String OLD_NIF = "huesped.oldNIF";
    
    static {
        params = new LinkedList<>();
        params.add(NIF);
        params.add(NOMBRE);
        params.add(APELLIDOS);
        params.add(OLD_NIF);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO: error acceso
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO
    }
    
}
