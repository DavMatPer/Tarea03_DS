/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ticketbeat.servicios;

import com.ticketbeat.interfaces.ICanal;

/**
 *
 * @author Rafael Cosmo
 */
public class CanalEmail implements ICanal{
    @Override
    public boolean enviar(String mensaje) {
        System.out.println("[EMAIL] Enviando correo: " + mensaje);
        return true;
    }

    @Override
    public boolean verificarDisponibilidad() {
        return true; 
    }

}
