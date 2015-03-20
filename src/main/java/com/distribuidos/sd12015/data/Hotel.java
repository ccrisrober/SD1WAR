/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.distribuidos.sd12015.data;

import com.distribuidos.sd12015.models.Duple;
import com.distribuidos.sd12015.models.Huesped;
import com.distribuidos.sd12015.models.Reserva;
import com.distribuidos.sd12015.rest.ServicioREST;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cristian
 */
public class Hotel {
    public static Map<String, Huesped> huespeds = new HashMap<String, Huesped>();
    public static Map<Duple<Date, String>, Reserva> reservas = new HashMap<Duple<Date, String>, Reserva>();
    public static List<Integer> habitacionesDisponibles = new LinkedList<Integer>();
    private static Random random = new Random();
    static {
        try {huespeds.put("12345678A", new Huesped("12345678A", "José", "Pérez Pérez"));
            huespeds.put("12345678B", new Huesped("12345678B", "Andrés", "Pérez Pérez"));
            for(int i = 100; i < 600; i++) {
                habitacionesDisponibles.add(i);
            }
            habitacionesDisponibles.remove(101);
            Date di = ServicioREST.strToDate("2015-03-01");
            Date df = ServicioREST.strToDate("2015-03-02");
            reservas.put(new Duple<Date, String>(di, "12345678A"), new Reserva("12345678A", 101, di, df));

        } catch (ParseException ex) {
            Logger.getLogger(Hotel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static int getRandomHabitacion() {
        if (habitacionesDisponibles.isEmpty()) {
            return -1;
        }
        int r = habitacionesDisponibles.size() + 1;
        while(r > habitacionesDisponibles.size()) {
            r = random.nextInt(habitacionesDisponibles.size());
        }
        return habitacionesDisponibles.remove(r);
    }
}
