package com.ticketbeat.estrategia_pago;

import java.util.Map;

/**
 * Estrategia de pago con tarjeta. Ahora extiende {@link AbstractEstrategiaPago},
 * que concentra la lógica común de procesarPago()/revertirPago() (corrección
 * del code smell "Código Duplicado"), y usa el parámetro "datos" para leer el
 * número de tarjeta (corrección del code smell "Generalización Especulativa").
 *
 * @author Rafael Cosmo
 */
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
