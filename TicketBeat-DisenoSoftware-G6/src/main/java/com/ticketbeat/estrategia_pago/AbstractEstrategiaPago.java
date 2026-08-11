package com.ticketbeat.estrategia_pago;

import com.ticketbeat.interfaces.EstrategiaPago;
import com.ticketbeat.modelo.EstadoPago;
import com.ticketbeat.modelo.Pago;
import java.util.Map;
import java.util.UUID;

/**
 * Superclase que concentra el flujo común a todas las estrategias de pago
 * (Template Method): construir el {@link Pago} resultante y registrar el
 * mensaje de reversión. Cada subclase concreta solo aporta el mensaje
 * específico de su medio de pago.
 *
 * Introducida para corregir dos code smells:
 * <ul>
 *   <li>"Código Duplicado": las tres estrategias (tarjeta, móvil,
 *       transferencia) repetían la misma construcción de {@link Pago} con
 *       {@code UUID.randomUUID()} y el mismo esqueleto de revertirPago().</li>
 *   <li>"Generalización Especulativa": el parámetro {@code datos} de
 *       procesarPago() no se usaba en ninguna estrategia. Aquí se aprovecha
 *       mediante {@link #obtenerDato(Map, String)} para leer un dato relevante
 *       (número de tarjeta, teléfono, cuenta) en cada subclase.</li>
 * </ul>
 *
 * @author Rafael Cosmo
 */
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
