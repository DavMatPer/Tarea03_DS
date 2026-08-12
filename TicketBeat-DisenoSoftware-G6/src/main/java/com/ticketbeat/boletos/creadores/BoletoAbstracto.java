/*
 * Producto Abstracto - Patrón Factory Method
 */
package com.ticketbeat.boletos.creadores;

import com.ticketbeat.interfaces.IBoleto;
import com.ticketbeat.modelo.Comprador;
import com.ticketbeat.modelo.EstadoBoleto;

/**
 * Superclase que concentra los campos y el comportamiento comunes a todos los
 * tipos de boleto (id, estado, precio, comprador).
 *
 * Introducida para corregir el code smell "Código Duplicado": BoletoGeneral,
 * BoletoReservado y BoletoVIP repetían exactamente los mismos tres campos y
 * las mismas implementaciones de getEstado()/getPrecio()/setEstado(). Cada
 * subclase concreta ahora solo aporta sus atributos propios y su
 * mostrarDetalles().
 *
 * El campo "comprador" se agregó después para vincular cada boleto con quien
 * lo adquirió (ver IBoleto), resolviendo la falta de vínculo Boleto-Comprador
 * señalada en las notas de diseño del plan de pruebas.
 *
 * @author Rafael Cosmo
 */
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
