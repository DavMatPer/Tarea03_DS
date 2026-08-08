package com.ticketbeat.servicios;

import com.ticketbeat.interfaces.EstrategiaPago;
import com.ticketbeat.modelo.Pago;
import java.util.Map;
import java.util.UUID;

public class PagoMovilStrategy implements EstrategiaPago {
    private String servicioMovil;

    public PagoMovilStrategy(String servicioMovil) {
        this.servicioMovil = servicioMovil;
    }

    @Override
    public Pago procesarPago(double monto, Map<String, String> datos) {
        System.out.println("[" + servicioMovil + "] Cobro rápido procesado por $" + monto);
        // Lógica de procesamiento de pago móvil
        String pagoId = UUID.randomUUID().toString();
        return new Pago(pagoId, monto, "COMPLETADO");
    }

    @Override
    public boolean revertirPago(String pagoId) {
        System.out.println("[" + servicioMovil + "] Reembolso para el pago " + pagoId + " enviado a la billetera digital.");
        return true;
    }
}
