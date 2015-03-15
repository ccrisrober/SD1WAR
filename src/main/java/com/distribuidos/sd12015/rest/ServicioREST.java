/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.distribuidos.sd12015.rest;

import com.distribuidos.sd12015.data.ClaseConApellido;
import com.distribuidos.sd12015.models.Reserva;
import com.distribuidos.sd12015.models.Huesped;
import com.distribuidos.sd12015.models.Duple;
import com.distribuidos.sd12015.data.ClaseConFechaEntradaYSalidaYNif;
import com.distribuidos.sd12015.data.ClaseConNombre;
import com.distribuidos.sd12015.data.ClaseConOkYDuple;
import com.distribuidos.sd12015.data.ClaseConFecha;
import com.distribuidos.sd12015.data.ClaseConOk;
import com.distribuidos.sd12015.data.Hotel;
import com.distribuidos.sd12015.data.ClaseConFechaYNif;
import com.distribuidos.sd12015.data.ClaseConNifYHuesped;
import com.distribuidos.sd12015.data.ClaseConNif;
import com.distribuidos.sd12015.data.ClaseConReservaYNif;
import com.distribuidos.sd12015.exceptions.NotFoundException;
import com.thoughtworks.xstream.XStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Cristian
 */
public class ServicioREST {
    
    protected static ServicioREST instance;
    protected XStream miStream = new XStream();
    
    public static ServicioREST instance() {
        if (instance == null) {
            instance = new ServicioREST();
        }
        return instance;
    }
    
    public String getHuespeds() {
        List<Huesped> l = new LinkedList<Huesped>();
        for (Huesped h : Hotel.huespeds.values()) {
            l.add(h);
        }
        String str = miStream.toXML(l);
        /*return miStream.toXML(l);*/
        return str;
    }
    
    public String findByName(String xml) {
        ClaseConNombre o = (ClaseConNombre) miStream.fromXML(xml);
        List<Huesped> l = new LinkedList<Huesped>();
        for (Huesped h : Hotel.huespeds.values()) {
            if (h.getNombre().contains(o.getNombre())) {
                l.add(h);
            }
        }
        String str = miStream.toXML(l);
        return str;
    }
    
    public String findByApellidos(String xml) {
        ClaseConApellido o = (ClaseConApellido) miStream.fromXML(xml);
        List<Huesped> l = new LinkedList<Huesped>();
        for (Huesped h : Hotel.huespeds.values()) {
            if (h.getApellidos().contains(o.getApellido())) {
                l.add(h);
            }
        }
        String str = miStream.toXML(l);
        return str;
    }
    
    public String addHuesped(String xml) {
        Huesped h = (Huesped) miStream.fromXML(xml);
        ClaseConOk ok = new ClaseConOk(false);
        if (!Hotel.huespeds.containsKey(h.getNIF()) && h.getNIF() != null) {
            ok.setOk(Hotel.huespeds.put(h.getNIF(), h) == null);
        }        
        String str = miStream.toXML(ok);
        return str;
    }
    
    public String getHuesped(String xml) throws NotFoundException {
        ClaseConNif nif = (ClaseConNif) miStream.fromXML(xml);
        Huesped h = Hotel.huespeds.get(nif.getNif());
        if (h == null) {
            throw new NotFoundException("Huesped not found");
        }
        String str = miStream.toXML(h);
        return str;
    }
    
    public String setHuesped(String xml) {
        ClaseConOk ok = new ClaseConOk(false);
        ClaseConNifYHuesped nh = (ClaseConNifYHuesped) miStream.fromXML(xml);
        Huesped hh = Hotel.huespeds.get(nh.getNif());
        if (hh != null) {
            ok.setOk(Hotel.huespeds.put(nh.getNif(), nh.getHuesped()) == hh);
        }
        String str = miStream.toXML(ok);
        return str;
    }
    
    public String removeHuesped(String xml) {
        ClaseConOk ok = new ClaseConOk(false);
        ClaseConNif nif = (ClaseConNif) miStream.fromXML(xml);
        Huesped remove = Hotel.huespeds.remove(nif.getNif());
        ok.setOk(remove != null);
        String str = miStream.toXML(ok);
        return str;
    }
    
    public String getReservas() {
        List<Reserva> l = new LinkedList<Reserva>();
        for (Reserva r : Hotel.reservas.values()) {
            l.add(r);
        }
        String str = miStream.toXML(l);
        return str;
    }
    
    public String findByFechaEntrada(String xml) {
        ClaseConFecha fecha = (ClaseConFecha) miStream.fromXML(xml);
        List<Reserva> l = new LinkedList<Reserva>();
        for (Reserva r : Hotel.reservas.values()) {
            if (r.getFechaEntrada().compareTo(fecha.getDate()) == 0) {
                l.add(r);
            }
        }
        String str = miStream.toXML(l);
        return str;
    }
    
    public String getReserva(String xml) throws NotFoundException {
        ClaseConFechaYNif fn = (ClaseConFechaYNif) miStream.fromXML(xml);
        Reserva r = Hotel.reservas.get(new Duple<Date, String>(fn.getDate(), fn.getNif()));
        if (r == null) {
            throw new NotFoundException("Reserva not found");
        }
        String str = miStream.toXML(r);
        return str;
    }
    
    public String setReserva(String xml) throws NotFoundException {
        ClaseConOk ok = new ClaseConOk(false);
        ClaseConReservaYNif rn = (ClaseConReservaYNif) miStream.fromXML(xml);
        Duple d = new Duple<Date, String>(rn.getReserva().getFechaEntrada(), rn.getNif());
        Reserva r = Hotel.reservas.get(d);
        if (r == null) {
            throw new NotFoundException("Reserva not found");
        }
        Reserva put = Hotel.reservas.put(d, rn.getReserva());        
        if (put != null) {
            ok.setOk(true);
        }
        String str = miStream.toXML(ok);
        return str;
    }
    
    public String deleteReserva(String xml) {
        ClaseConOk ok = new ClaseConOk(false);
        ClaseConFechaYNif fn = (ClaseConFechaYNif) miStream.fromXML(xml);
        Reserva remove = Hotel.reservas.remove(new Duple<Date, String>(fn.getDate(), fn.getNif()));
        ok.setOk(remove != null);
        String str = miStream.toXML(ok);
        return str;
    }
    
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    
    public static Date strToDate(String str) throws ParseException {
        return format.parse(str);
    }
    
    public static String dateToStr(Date date) throws ParseException {
        return format.format(date);
    }
    
    public String addReserva(String xml) {
        ClaseConOkYDuple dateOk = new ClaseConOkYDuple(true, null);
        ClaseConFechaEntradaYSalidaYNif fesn = (ClaseConFechaEntradaYSalidaYNif) miStream.fromXML(xml);
        
        if (!Hotel.huespeds.containsKey(fesn.getNif())) {
            dateOk.setOk(false);
        }
        int hab = Hotel.getRandomHabitacion();
        if (hab < 0) {
            dateOk.setOk(false);
        }
        // Si queremos ser más rigurosos, hace falta comprobar más cosas como que no pisa algo anterior o tal!
        Reserva reserva = new Reserva(fesn.getNif(), hab, fesn.getFechaEntrada(), fesn.getFechaSalida());
        Duple<Date, String> d = new Duple<Date, String>(fesn.getFechaEntrada(), fesn.getNif());
        Hotel.reservas.put(d, reserva);
        dateOk.setDuple(d);
        String str = miStream.toXML(dateOk);
        return str;
    }
}
