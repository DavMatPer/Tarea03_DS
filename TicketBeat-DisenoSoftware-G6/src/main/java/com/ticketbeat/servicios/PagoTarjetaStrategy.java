package com.ticketbeat.servicios;

import com.ticketbeat.interfaces.EstrategiaPago;
import com.ticketbeat.modelo.Pago;
import java.util.Map;
import java.util.UUID;

public class PagoTarjetaStrategy implements EstrategiaPago {
    private String pasarela;

    public PagoTarjetaStrategy(String pasarela) {
        this.pasarela = pasarela;
    }

    @Override
    public Pago procesarPago(double monto, Map<String, String> datos) {
        System.out.println("[" + pasarela + "] Procesando cobro con tarjeta por $" + monto);
        // Lógica de procesamiento de pago con tarjeta
        String pagoId = UUID.randomUUID().toString();
        return new Pago(pagoId, monto, "COMPLETADO");
    }

    @Override
    public boolean revertirPago(String pagoId) {
        System.out.println("[" + pasarela + "] Reembolsando el pago " + pagoId + " a la tarjeta.");
        return true;
    }
}
