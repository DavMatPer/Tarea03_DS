package com.ticketbeat.modelo;

import com.ticketbeat.interfaces.IPoliticaCompra;

public class RestriccionSocioDecorator extends PoliticaDecorator {
    private boolean requiereMembresia;

    public RestriccionSocioDecorator(IPoliticaCompra componente, boolean requiereMembresia) {
        super(componente);
        this.requiereMembresia = requiereMembresia;
    }

    @Override
    public boolean validarCompra(Comprador comprador, int cantidad) {
        if (requiereMembresia && !comprador.isEsSocio()) {
            System.out.println("[RestriccionSocio] RECHAZADO: Se requiere membresía y el comprador no es socio.");
            return false;
        }
        System.out.println("[RestriccionSocio] OK: El comprador cumple con el requisito de membresía.");
        return componente.validarCompra(comprador, cantidad);
    }
}
