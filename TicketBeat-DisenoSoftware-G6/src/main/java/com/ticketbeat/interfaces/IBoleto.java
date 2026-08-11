package com.ticketbeat.interfaces;

import com.ticketbeat.modelo.Comprador;
import com.ticketbeat.modelo.EstadoBoleto;

/**
 * Interfaz que define el contrato para todos los tipos de boletos.
 * Parte del patrón Factory Method.
 *
 * Se agregó getComprador()/setComprador() para vincular cada boleto con
 * quien lo compró. Antes IBoleto no tenía ninguna referencia a Comprador,
 * por lo que era imposible saber, a partir de un boleto, quién lo había
 * adquirido — lo que dejaba sin implementar la idea (descrita en el
 * enunciado funcional del sistema) de notificar solo a los compradores de
 * boletos vendidos.
 *
 * @author Rafael Cosmo
 */
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
