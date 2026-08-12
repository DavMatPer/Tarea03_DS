package com.ticketbeat.estrategia_pago;

import java.util.Map;

public class PagoTransferenciaStrategy extends AbstractEstrategiaPago {
    private String banco;

    public PagoTransferenciaStrategy(String banco) {
        this.banco = banco;
    }

    @Override
    protected String mensajeProcesamiento(double monto, Map<String, String> datos) {
        String cuenta = obtenerDato(datos, "cuenta");
        return "[" + banco + "] Validando transferencia bancaria (cuenta " + cuenta + ") por $" + monto;
    }

    @Override
    protected String mensajeReversion(String pagoId) {
        return "[" + banco + "] Extornando el pago " + pagoId + " a la cuenta de origen.";
    }
}
