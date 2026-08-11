package com.ticketbeat.estrategia_pago;

import java.util.Map;

/**
 * Estrategia de pago móvil. Ahora extiende {@link AbstractEstrategiaPago} y
 * usa el parámetro "datos" para leer el número de teléfono (corrección de
 * "Código Duplicado" y "Generalización Especulativa").
 *
 * @author Rafael Cosmo
 */
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
