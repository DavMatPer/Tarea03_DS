package com.ticketbeat.estrategia_pago;

import com.ticketbeat.interfaces.EstrategiaPago;
import com.ticketbeat.modelo.EstadoPago;
import com.ticketbeat.modelo.Pago;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractEstrategiaPago implements EstrategiaPago {

    @Override
    public Pago procesarPago(double monto, Map<String, String> datos) {
        System.out.println(mensajeProcesamiento(monto, datos));
        String pagoId = UUID.randomUUID().toString();
        return new Pago(pagoId, monto, EstadoPago.COMPLETADO);
    }

    @Override
    public boolean revertirPago(String pagoId) {
        System.out.println(mensajeReversion(pagoId));
        return true;
    }

    /** Mensaje de log específico de cada medio de pago; puede leer {@code datos}. */
    protected abstract String mensajeProcesamiento(double monto, Map<String, String> datos);

    /** Mensaje de log específico de cada medio de pago para la reversión. */
    protected abstract String mensajeReversion(String pagoId);

    /** Lee una clave del mapa de datos del pago, con un valor por defecto seguro. */
    protected String obtenerDato(Map<String, String> datos, String clave) {
        return (datos != null && datos.containsKey(clave)) ? datos.get(clave) : "N/D";
    }
}
