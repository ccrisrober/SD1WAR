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
    
    /**
     * Retorna una lista con todos los huéspeds. Vacía si no hay huéspeds
     * @return Lista de huéspeds
     */
    public String getHuespeds() {
        List<Huesped> l = new LinkedList<>();
        for (Huesped h : Hotel.huespeds.values()) {
            l.add(h);
        }
        String str = miStream.toXML(l);
        return str;
    }
    
    /**
     * Busca huéspeds que contienen como nombre el dado.
     * @param xml ClaseConNombre serializado
     * @return Lista de huéspeds
     */
    public String findByName(String xml) {
        ClaseConNombre o = (ClaseConNombre) miStream.fromXML(xml);
        List<Huesped> l = new LinkedList<>();
        for (Huesped h : Hotel.huespeds.values()) {
            if (h.getNombre().contains(o.getNombre())) {
                l.add(h);
            }
        }
        String str = miStream.toXML(l);
        return str;
    }
    
    /**
     * Busca huéspeds cuyo apellido contiene el dado.
     * @param xml ClaseConApellido serializado
     * @return Lista de huéspeds
     */
    public String findByApellidos(String xml) {
        ClaseConApellido o = (ClaseConApellido) miStream.fromXML(xml);
        List<Huesped> l = new LinkedList<>();
        for (Huesped h : Hotel.huespeds.values()) {
            if (h.getApellidos().contains(o.getApellido())) {
                l.add(h);
            }
        }
        String str = miStream.toXML(l);
        return str;
    }
    
    /**
     * Añade un huésped a la "base de datos"
     * @param xml Huésped serializado
     * @return ClaseConOk. Retorna true si se ha guardado. False en caso 
     *      contrario.
     */
    public String addHuesped(String xml) {
        Huesped h = (Huesped) miStream.fromXML(xml);
        ClaseConOk ok = new ClaseConOk(false);
        if (!Hotel.huespeds.containsKey(h.getNIF()) && h.getNIF() != null) {
            ok.setOk(Hotel.huespeds.put(h.getNIF(), h) == null);
        }        
        String str = miStream.toXML(ok);
        return str;
    }
    
    /**
     * Obtiene un huésped a la "base de datos"
     * @param xml ClaseConNif con el nif a buscar serializada
     * @return 
     *      - Si el huésped no existe, se lanza una excepción NotFoundException
     *      - Si el huésped existe, se devuelve un objeto Huésped
     * @throws com.distribuidos.sd12015.exceptions.NotFoundException
     */
    public String getHuesped(String xml) throws NotFoundException {
        ClaseConNif nif = (ClaseConNif) miStream.fromXML(xml);
        Huesped h = Hotel.huespeds.get(nif.getNif());
        if (h == null) {
            throw new NotFoundException("Huesped not found");
        }
        String str = miStream.toXML(h);
        return str;
    }
    
    /**
     * Edita un huésped en la "base de datos"
     * @param xml ClaseConNifYHuesped que contiene el viejo nif y el nuevo 
     *      huésped editado serializado
     * @return ClaseConOk. El interior de esta clase contiene si se ha editado 
     *      con éxito o no.
     */
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
    
    /**
     * Remueve un huésped de la "base de datos"
     * @param xml ClaseConNif con el NIF del huésped serializado
     * @return ClaseConOk. En su interior se encuentra un booleano que 
     *      determina si se ha borrado o no
     */
    public String removeHuesped(String xml) {
        ClaseConOk ok = new ClaseConOk(false);
        ClaseConNif nif = (ClaseConNif) miStream.fromXML(xml);
        Huesped remove = Hotel.huespeds.remove(nif.getNif());
        ok.setOk(remove != null);
        String str = miStream.toXML(ok);
        return str;
    }
    
    /**
     * Retorna una lista con todas las reservas. Vacía si no hay reservas.
     * @return Lista de reservas
     */
    public String getReservas() {
        List<Reserva> l = new LinkedList<>();
        for (Reserva r : Hotel.reservas.values()) {
            l.add(r);
        }
        String str = miStream.toXML(l);
        return str;
    }
    
    /**
     * Busca reservas que que concuerden con la fecha de entrada pasada.
     * @param xml ClaseConFecha serializado
     * @return Lista de reservas
     */
    public String findByFechaEntrada(String xml) {
        ClaseConFecha fecha = (ClaseConFecha) miStream.fromXML(xml);
        List<Reserva> l = new LinkedList<>();
        for (Reserva r : Hotel.reservas.values()) {
            if (r.getFechaEntrada().compareTo(fecha.getDate()) == 0) {
                l.add(r);
            }
        }
        String str = miStream.toXML(l);
        return str;
    }
    
   /**
     * Obtiene una reserva a la "base de datos"
     * @param xml ClaseConFechaYNif con el nif y fecha de entrada a buscar serializada
     * @return 
     *      - Si la reserva no existe, se lanza una excepción NotFoundException
     *      - Si la reserva existe, se devuelve un objeto Reserva
     * @throws com.distribuidos.sd12015.exceptions.NotFoundException
     */
    public String getReserva(String xml) throws NotFoundException {
        ClaseConFechaYNif fn = (ClaseConFechaYNif) miStream.fromXML(xml);
        Duple d = new Duple(fn.getDate(), fn.getNif());
        Reserva r = Hotel.reservas.get(d);
        if (r == null) {
            throw new NotFoundException("Reserva not found");
        }
        String str = miStream.toXML(r);
        return str;
    }
    
    /**
     * Edita una reserva en la "base de datos"
     * @param xml ClaseConReservaYNif que contiene el viejo nif y la nueva 
     *      reserva editada serializado
     * @return ClaseConOk. El interior de esta clase contiene si se ha editado 
     *      con éxito o no.
     * @throws com.distribuidos.sd12015.exceptions.NotFoundException
     */
    public String setReserva(String xml) throws NotFoundException {
        ClaseConOkYDuple ok = new ClaseConOkYDuple(false, null);
        ClaseConReservaYNif rn = (ClaseConReservaYNif) miStream.fromXML(xml);
        Duple d = new Duple(rn.getReserva().getFechaEntrada(), rn.getNif());
        ok.setDuple(d);
        Reserva r = Hotel.reservas.remove(d);
        if (r == null) {
            throw new NotFoundException("Reserva not found");
        }
        d.setF(rn.getReserva().getNIF());
        Reserva put = Hotel.reservas.put(d, rn.getReserva());        
        if (put == null) {
            ok.setOk(true);
        }
        String str = miStream.toXML(ok);
        return str;
    }
    
    /**
     * Remueve una reserva de la "base de datos"
     * @param xml ClaseConFechaYNif con el NIF y fecha de entrada de la reserva 
     *      serializado
     * @return ClaseConOk. En su interior se encuentra un booleano que 
     *      determina si se ha borrado o no
     */
    public String deleteReserva(String xml) {
        ClaseConOk ok = new ClaseConOk(false);
        ClaseConFechaYNif fn = (ClaseConFechaYNif) miStream.fromXML(xml);
        Reserva remove = Hotel.reservas.remove(new Duple(fn.getDate(), fn.getNif()));
        ok.setOk(remove != null);
        String str = miStream.toXML(ok);
        return str;
    }
    
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    
    public static Date strToDate(String str) throws ParseException {
        return format.parse(str);
    }
    
    public static String dateToStr(Date date) throws ParseException {
        return format.format(date);
    }
    
    /**
     * Añade una reserva a la "base de datos"
     * @param xml Reserva serializada
     * @return ClaseConOk. Retorna true si se ha guardado. False en caso 
     *      contrario.
     */
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
        Reserva reserva = new Reserva(fesn.getNif(), hab, fesn.getFechaEntrada(), fesn.getFechaSalida());
        Duple d = new Duple(fesn.getFechaEntrada(), fesn.getNif());
        Hotel.reservas.put(d, reserva);
        dateOk.setDuple(d);
        String str = miStream.toXML(dateOk);
        return str;
    }
}
