package com.ticketbeat.gestores;

import com.ticketbeat.interfaces.EstrategiaPago;
import com.ticketbeat.interfaces.IBoleto;
import com.ticketbeat.modelo.Comprador;
import com.ticketbeat.modelo.EstadoBoleto;
import com.ticketbeat.modelo.Pago;
import com.ticketbeat.modelo.Reserva;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GestorReservas {

    private EstrategiaPago estrategiaPago;
    private Comprador comprador;
    private List<IBoleto> boletosSeleccionados = new ArrayList<>();
    private Reserva reservaActual;

    public void setEstrategiaPago(EstrategiaPago estrategia) {
        this.estrategiaPago = estrategia;
    }

    public void setComprador(Comprador comprador) {
        this.comprador = comprador;
    }

    public void setBoletosSeleccionados(List<IBoleto> boletos) {
        this.boletosSeleccionados = (boletos != null) ? boletos : new ArrayList<>();
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

    private boolean verificarDisponibilidad() {
        return !boletosSeleccionados.isEmpty()
                && boletosSeleccionados.stream().allMatch(b -> b.getEstado() == EstadoBoleto.DISPONIBLE);
    }

    private void reservarEntradas() {
        for (IBoleto boleto : boletosSeleccionados) {
            boleto.setEstado(EstadoBoleto.RESERVADO);
        }
        Date expiracion = new Date(System.currentTimeMillis() + 15 * 60 * 1000);
        reservaActual = new Reserva(UUID.randomUUID().toString(), comprador,
                new ArrayList<>(boletosSeleccionados), expiracion);
        System.out.println("Reserva " + reservaActual.getId() + " creada para "
                + boletosSeleccionados.size() + " boleto(s). Expira: " + expiracion);
    }

    private void iniciarTemporizadorDeReserva() {
        System.out.println("Temporizador de expiración de reserva iniciado (15 minutos).");
    }

    public void confirmarCompra(double monto, Map<String, String> datos) {
        if (this.estrategiaPago == null) {
            System.out.println("Error: No se ha seleccionado una estrategia de pago.");
            return;
        }

        Pago pago = this.estrategiaPago.procesarPago(monto, datos);

        if (pago != null && pago.estaCompletado()) {
            System.out.println("Confirmación de pago (ID: " + pago.getId() + ")");
            marcarEntradasComoVendidas();
            generarBoletosDigitales();
            System.out.println("Confirmación de compra y boletos");
        } else {
            System.out.println("Pago rechazado");
            System.out.println("Informar error y permitir reintento");
        }
    }

    private void marcarEntradasComoVendidas() {
        if (reservaActual == null) return;
        for (IBoleto boleto : reservaActual.getBoletosReservados()) {
            boleto.setEstado(EstadoBoleto.VENDIDO);
            boleto.setComprador(comprador);
        }
    }

    private void generarBoletosDigitales() {
        if (reservaActual == null) return;
        System.out.println(reservaActual.getBoletosReservados().size() + " boleto(s) digital(es) generado(s).");
    }

    public void tiempoDeReservaExpirado() {
        liberarEntradasReservadas();
        System.out.println("Informar expiración de reserva");
    }

    private void liberarEntradasReservadas() {
        if (reservaActual == null) return;
        for (IBoleto boleto : reservaActual.getBoletosReservados()) {
            if (boleto.getEstado() == EstadoBoleto.RESERVADO) {
                boleto.setEstado(EstadoBoleto.DISPONIBLE);
            }
        }
        reservaActual = null;
    }
}
