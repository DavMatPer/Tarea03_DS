package com.ticketbeat.servicios;

import com.ticketbeat.interfaces.EstrategiaPago;
import com.ticketbeat.modelo.Pago;
import java.util.Map;
import java.util.UUID;

public class PagoTransferenciaStrategy implements EstrategiaPago {
    private String banco;

    public PagoTransferenciaStrategy(String banco) {
        this.banco = banco;
    }

    @Override
    public Pago procesarPago(double monto, Map<String, String> datos) {
        System.out.println("[" + banco + "] Validando transferencia bancaria por $" + monto);
        // Lógica de procesamiento de pago con transferencia
        String pagoId = UUID.randomUUID().toString();
        return new Pago(pagoId, monto, "COMPLETADO");
    }

    @Override
    public boolean revertirPago(String pagoId) {
        System.out.println("[" + banco + "] Extornando el pago " + pagoId + " a la cuenta de origen.");
        return true;
    }
}
