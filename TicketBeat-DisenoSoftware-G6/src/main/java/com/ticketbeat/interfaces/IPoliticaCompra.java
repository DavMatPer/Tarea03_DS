package com.ticketbeat.interfaces;

import com.ticketbeat.modelo.Comprador;
import com.ticketbeat.modelo.Evento;
import java.util.List;

/**
 * Contrato único para toda política de compra/devolución de un evento.
 *
 * Se agregó {@link #aplicarPoliticaDevolucion(Evento)} para corregir el code
 * smell "Intimidad Inapropiada / Acoplamiento a Tipos Concretos": antes
 * existían dos jerarquías de "política" sin relación entre sí (esta interfaz,
 * usada para validar compras, y la clase suelta PoliticaEvento, usada solo
 * para calcular devoluciones). Ahora ambas responsabilidades viven en la
 * misma abstracción, permitiendo reutilizar los decoradores existentes
 * (límite de boletos, restricción de socio, verificación de edad) también en
 * el flujo de cancelación.
 *
 * @author Rafael Cosmo
 */
public interface IPoliticaCompra {
    boolean validarCompra(Comprador comprador, int cantidad);
    double calcularReembolso(double monto);
    boolean permiteCambioFecha();
    List<IBoleto> aplicarPoliticaDevolucion(Evento evento);
}
