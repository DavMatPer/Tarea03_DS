/*
 * Producto Abstracto - Patrón Factory Method
 */
package com.ticketbeat.boletos.creadores;

import com.ticketbeat.interfaces.IBoleto;
import com.ticketbeat.modelo.Comprador;
import com.ticketbeat.modelo.EstadoBoleto;

public abstract class BoletoAbstracto implements IBoleto {

    protected String id;
    protected EstadoBoleto estado;
    protected double precio;
    protected Comprador comprador;

    protected BoletoAbstracto(double precio) {
        this.estado = EstadoBoleto.DISPONIBLE;
        this.precio = precio;
    }

    @Override
    public EstadoBoleto getEstado() {
        return estado;
    }

    @Override
    public void setEstado(EstadoBoleto estado) {
        this.estado = estado;
    }

    @Override
    public double getPrecio() {
        return precio;
    }

    @Override
    public Comprador getComprador() {
        return comprador;
    }

    @Override
    public void setComprador(Comprador comprador) {
        this.comprador = comprador;
    }
}
