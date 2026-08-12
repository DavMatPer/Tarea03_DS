package com.ticketbeat.estrategia_pago;

import java.util.Map;

public class PagoMovilStrategy extends AbstractEstrategiaPago {
    private String servicioMovil;

    public PagoMovilStrategy(String servicioMovil) {
        this.servicioMovil = servicioMovil;
    }

    @Override
    protected String mensajeProcesamiento(double monto, Map<String, String> datos) {
        String telefono = obtenerDato(datos, "telefono");
        return "[" + servicioMovil + "] Cobro rápido (" + telefono + ") procesado por $" + monto;
    }

    @Override
    protected String mensajeReversion(String pagoId) {
        return "[" + servicioMovil + "] Reembolso para el pago " + pagoId + " enviado a la billetera digital.";
    }
}
