/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.distribuidos.sd12015.data;

import com.distribuidos.sd12015.models.Reserva;

/**
 *
 * @author Cristian
 */
public class ClaseConReservaYNif {
    
    private Reserva reserva;
    private String nif;

    public ClaseConReservaYNif(Reserva reserva, String nif) {
        this.reserva = reserva;
        this.nif = nif;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }
    
}
