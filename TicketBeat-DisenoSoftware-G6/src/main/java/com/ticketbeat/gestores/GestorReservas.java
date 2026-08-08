package com.ticketbeat.gestores;

import com.ticketbeat.interfaces.EstrategiaPago;
import com.ticketbeat.modelo.Pago;
import java.util.Map;

public class GestorReservas {

    private EstrategiaPago estrategiaPago;

    public void setEstrategiaPago(EstrategiaPago estrategia) {
        this.estrategiaPago = estrategia;
    }

    public void buscarEventos() {
        System.out.println("Mostrando eventos disponibles...");
    }

    public void seleccionarEvento() {
        System.out.println("Mostrando tipos de entradas...");
    }

    public void elegirCantidadYTipoDeEntrada() {
        if (verificarDisponibilidad()) {
            reservarEntradas();
            iniciarTemporizadorDeReserva();
        } else {
            System.out.println("Informar entradas insuficientes");
        }
    }

    private boolean verificarDisponibilidad() { return true; }
    private void reservarEntradas() { }
    private void iniciarTemporizadorDeReserva() { }

    public void confirmarCompra(double monto, Map<String, String> datos) {
        if (this.estrategiaPago == null) {
            System.out.println("Error: No se ha seleccionado una estrategia de pago.");
            return;
        }

        Pago pago = this.estrategiaPago.procesarPago(monto, datos);

        if (pago != null && "COMPLETADO".equals(pago.getEstado())) {
            System.out.println("Confirmación de pago (ID: " + pago.getId() + ")");
            marcarEntradasComoVendidas();
            generarBoletosDigitales();
            System.out.println("Confirmación de compra y boletos");
        } else {
            System.out.println("Pago rechazado");
            System.out.println("Informar error y permitir reintento");
        }
    }

    private void marcarEntradasComoVendidas() { }
    private void generarBoletosDigitales() { }

    public void tiempoDeReservaExpirado() {
        liberarEntradasReservadas();
        System.out.println("Informar expiración de reserva");
    }

    private void liberarEntradasReservadas() { }
}