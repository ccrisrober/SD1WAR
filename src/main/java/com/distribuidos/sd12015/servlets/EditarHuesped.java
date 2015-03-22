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
import com.distribuidos.sd12015.models.Domicilio;
import com.distribuidos.sd12015.models.Huesped;
import com.distribuidos.sd12015.rest.ServicioREST;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            ClaseConNif nif = new ClaseConNif(request.getParameter("NIF"));
            String huespedstr = GenericHttpServlet.sr.getHuesped(GenericHttpServlet.miStream.toXML(nif));
            Huesped h = (Huesped) GenericHttpServlet.miStream.fromXML(huespedstr);
            if (h == null) {
                request.getSession().setAttribute("error", true);
                request.getSession().setAttribute("msg", "Huesped no encontrado");
                response.sendRedirect("./Huespeds");
                return;
            }
            request.setAttribute("huesped", h);
            request.setAttribute("oldNIF", h.getNIF());
            request.getRequestDispatcher("WEB-INF/views/huespeds/edit.jsp").forward(request, response);
        } catch (NotFoundException ex) {
            request.getSession().setAttribute("error", true);
            request.getSession().setAttribute("msg", "Huesped no encontrado");
            response.sendRedirect("./Huespeds");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String old = request.getParameter("huesped.id");
        String name = request.getParameter("huesped.nombre");
        String surname = request.getParameter("huesped.apellidos");
        String NIF = request.getParameter("huesped.NIF");
        String nacimiento = request.getParameter("huesped.fechaNacimiento");
        String direccion = request.getParameter("huesped.direccion");
        String localidad = request.getParameter("huesped.localidad");
        String cp = request.getParameter("huesped.codigoPostal");
        String provincia = request.getParameter("huesped.provincia");
        String email = request.getParameter("huesped.email");
        String fijo = request.getParameter("huesped.telefonoFijo");
        String movil = request.getParameter("huesped.telefonoMovil");
        int codigoPostal = 0;
        Date fecha = null;
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
        if (nacimiento.isEmpty()) {
            errors.add("Fecha nacimiento requerida");
        } else {
            try {
                fecha = ServicioREST.strToDate(nacimiento);
            } catch (ParseException ex) {
                errors.add("Fecha nacimiento incorrecta");
            }
        }
        if (direccion.length() < 3) {
            errors.add("Mínimo 3 caracteres para dirección.");
        }
        if (localidad.length() < 3) {
            errors.add("Mínimo 3 caracteres para localidad.");
        }
        if (provincia.length() < 3) {
            errors.add("Mínimo 3 caracteres para provincia.");
        }
        if (cp.length() != 5) {
            errors.add("Código Postal de 5 digitos");
        } else {
            try {
                codigoPostal = Integer.parseInt(cp);
            } catch (NumberFormatException e) {
                errors.add("Código postal incorrecto");
            }
        }
        Huesped h = new Huesped(NIF, name, surname, fecha, new Domicilio(direccion, localidad, codigoPostal, provincia));
        if(!email.isEmpty() && email.length() > 0) {
            h.setEmail(email);
        }
        if(!fijo.isEmpty() && fijo.length() > 0) {
            h.setTelefonoFijo(fijo);
        }
        if(!movil.isEmpty() && movil.length() > 0) {
            h.setTelefonoMovil(movil);
        }
        if (!errors.isEmpty()) {
            request.setAttribute("huesped", h);
            request.setAttribute("oldNIF", request.getParameter("huesped.id"));
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("WEB-INF/views/huespeds/edit.jsp").forward(request, response);
        } else {
            ClaseConNifYHuesped nh = new ClaseConNifYHuesped(request.getParameter("huesped.id"), h);

            String editstr = GenericHttpServlet.sr.setHuesped(GenericHttpServlet.miStream.toXML(nh));
            ClaseConOk edit = (ClaseConOk) GenericHttpServlet.miStream.fromXML(editstr);
            if (edit.isOk()) {
                response.sendRedirect("./VerHuesped?NIF=" + NIF);
            } else {
                request.getSession().setAttribute("error", true);
                request.getSession().setAttribute("msg", "No se ha podido editar.");
                response.sendRedirect("./Huespeds");
            }
        }
    }
}
