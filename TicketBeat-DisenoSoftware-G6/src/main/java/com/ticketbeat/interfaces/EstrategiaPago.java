package com.ticketbeat.interfaces;

import com.ticketbeat.modelo.Pago;
import java.util.Map;

public interface EstrategiaPago {
    Pago procesarPago(double monto, Map<String, String> datos);
    boolean revertirPago(String pagoId);
}
