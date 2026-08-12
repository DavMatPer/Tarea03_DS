package com.ticketbeat.interfaces;

import com.ticketbeat.modelo.Comprador;
import com.ticketbeat.modelo.EstadoBoleto;

public interface IBoleto {
    void mostrarDetalles();
    EstadoBoleto getEstado();
    void setEstado(EstadoBoleto estado);
    double getPrecio();
    Comprador getComprador();
    void setComprador(Comprador comprador);
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
