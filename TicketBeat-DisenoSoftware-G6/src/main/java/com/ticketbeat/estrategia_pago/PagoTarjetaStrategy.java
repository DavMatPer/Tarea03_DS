package com.ticketbeat.estrategia_pago;

import java.util.Map;

public class PagoTarjetaStrategy extends AbstractEstrategiaPago {
    private String pasarela;

    public PagoTarjetaStrategy(String pasarela) {
        this.pasarela = pasarela;
    }

    @Override
    protected String mensajeProcesamiento(double monto, Map<String, String> datos) {
        String numero = obtenerDato(datos, "numero");
        return "[" + pasarela + "] Procesando cobro con tarjeta (" + numero + ") por $" + monto;
    }

    @Override
    protected String mensajeReversion(String pagoId) {
        return "[" + pasarela + "] Reembolsando el pago " + pagoId + " a la tarjeta.";
    }
}
