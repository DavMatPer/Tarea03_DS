/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ticketbeat.modelo;

/**
 *
 * @author Rafael Cosmo
 */
import com.ticketbeat.interfaces.IBoleto;
import java.util.Date;
import java.util.List;

public class Reserva {
    private String id;
    private Comprador comprador;
    private List<IBoleto> boletosReservados;
    private Date fechaExpiracion;

    public Comprador getComprador() { return comprador; }
}
