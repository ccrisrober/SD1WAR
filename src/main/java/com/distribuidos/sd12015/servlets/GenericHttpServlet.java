/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.distribuidos.sd12015.servlets;

import com.distribuidos.sd12015.rest.ServicioREST;
import com.thoughtworks.xstream.XStream;

/**
 *
 * @author Cristian
 */
public class GenericHttpServlet {
    public static ServicioREST sr = ServicioREST.instance();
    public static XStream miStream = new XStream();
}
