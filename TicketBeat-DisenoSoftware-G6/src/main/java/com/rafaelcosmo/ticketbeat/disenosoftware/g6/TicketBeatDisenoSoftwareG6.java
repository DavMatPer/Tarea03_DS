package com.rafaelcosmo.ticketbeat.disenosoftware.g6;

/**
 *
 * @author Rafael Cosmo
 */
import com.ticketbeat.politica.VerificacionEdadDecorator;
import com.ticketbeat.politica.RestriccionSocioDecorator;
import com.ticketbeat.politica.LimiteBoletosDecorator;
import com.ticketbeat.politica.PoliticaEventoBase;
import com.ticketbeat.boletos.creadores.CreadorBoletoGeneral;
import com.ticketbeat.boletos.creadores.CreadorBoletoReservado;
import com.ticketbeat.boletos.creadores.CreadorBoletoVIP;
import com.ticketbeat.boletos.creadores.CreadorBoleto;
import com.ticketbeat.estrategia_pago.PagoMovilStrategy;
import com.ticketbeat.estrategia_pago.PagoTarjetaStrategy;
import com.ticketbeat.estrategia_pago.PagoTransferenciaStrategy;
import com.ticketbeat.interfaces.*;
import com.ticketbeat.gestores.*;
import com.ticketbeat.modelo.*;
import com.ticketbeat.servicios.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketBeatDisenoSoftwareG6 {

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("       INICIANDO SISTEMA TICKETBEAT - GRUPO 6");
        System.out.println("=================================================\n");

        Comprador comprador = crearCompradorDemo();
        GestorNotificaciones gestorNotif = new GestorNotificaciones();

        List<IBoleto> boletosEmitidos = demostrarFactoryMethod();
        demostrarStrategy(comprador, boletosEmitidos);
        Evento concierto = demostrarDecorator(comprador);
        demostrarCancelacionEvento(concierto, boletosEmitidos, gestorNotif);
        demostrarChainOfResponsibility();

        System.out.println("\n=================================================");
        System.out.println("          FIN DE LA SIMULACIoN - GRUPO 6");
        System.out.println("=================================================");
    }

    private static Comprador crearCompradorDemo() {
        Comprador comprador = new Comprador();
        comprador.setNombre("Juan Perez");
        comprador.setEmail("juan.perez@example.com");
        comprador.setCanalPreferido(new CanalEmail());
        comprador.setEdad(25);
        comprador.setEsSocio(true);
        return comprador;
    }

    // =================================================================
    // PRUEBA PATRON 1 - FACTORY METHOD: Creacion de Boletos
    // =================================================================
    private static List<IBoleto> demostrarFactoryMethod() {
        System.out.println("=== PRUEBA FACTORY METHOD: CREACIoN DE BOLETOS ===\n");

        // Se crean los creadores concretos 
        CreadorBoleto creadorVIP = new CreadorBoletoVIP();
        CreadorBoleto creadorGeneral = new CreadorBoletoGeneral();
        CreadorBoleto creadorReservado = new CreadorBoletoReservado();

        // Cada creador emite su boleto y muestra detalles usando procesarEmision()
        System.out.println(">> Emitiendo Boleto VIP:");
        creadorVIP.procesarEmision();

        System.out.println("\n>> Emitiendo Boleto General:");
        creadorGeneral.procesarEmision();

        System.out.println("\n>> Emitiendo Boleto Reservado:");
        creadorReservado.procesarEmision();

        // Verificacion: crear boletos via Factory y confirmar tipos
        System.out.println("\n>> Verificacion de instancias creadas via Factory Method:");
        IBoleto boletoVip = creadorVIP.crearBoleto();
        IBoleto boletoGen = creadorGeneral.crearBoleto();
        IBoleto boletoRes = creadorReservado.crearBoleto();

        System.out.println("Boleto VIP -> Precio: $" + boletoVip.getPrecio() + " | Estado: " + boletoVip.getEstado());
        System.out.println("Boleto General -> Precio: $" + boletoGen.getPrecio() + " | Estado: " + boletoGen.getEstado());
        System.out.println("Boleto Reservado -> Precio: $" + boletoRes.getPrecio() + " | Estado: " + boletoRes.getEstado());

        System.out.println("\n=================================================\n");

        List<IBoleto> boletos = new ArrayList<>();
        boletos.add(boletoVip);
        boletos.add(boletoGen);
        boletos.add(boletoRes);
        return boletos;
    }

    // =================================================================
    // PRUEBA PATRON 2 - STRATEGY: Procesamiento de Pagos
    // =================================================================
    private static void demostrarStrategy(Comprador comprador, List<IBoleto> boletosSeleccionados) {
        System.out.println("=== PRUEBA STRATEGY: PROCESAMIENTO DE PAGOS ===\n");
        GestorReservas gestorReservas = new GestorReservas();

        gestorReservas.buscarEventos();
        gestorReservas.seleccionarEvento();
        gestorReservas.setComprador(comprador);
        gestorReservas.setBoletosSeleccionados(boletosSeleccionados);
        gestorReservas.elegirCantidadYTipoDeEntrada();

        // Pago con Tarjeta
        System.out.println("\n>> Comprador selecciona: Tarjeta de Credito (VISA)");
        EstrategiaPago tarjetaStrategy = new PagoTarjetaStrategy("VISA");
        gestorReservas.setEstrategiaPago(tarjetaStrategy);

        Map<String, String> datosTarjeta = new HashMap<>();
        datosTarjeta.put("numero", "1234-5678-9012-3456");
        datosTarjeta.put("cvv", "123");
        gestorReservas.confirmarCompra(150.50, datosTarjeta);

        // Cambio dinamico de estrategia a Pago Movil
        System.out.println("\n>> Comprador cambia estrategia en tiempo de ejecucion: Pago Movil");
        EstrategiaPago movilStrategy = new PagoMovilStrategy("PagoFlash");
        gestorReservas.setEstrategiaPago(movilStrategy);

        Map<String, String> datosMovil = new HashMap<>();
        datosMovil.put("telefono", "0412-1234567");
        gestorReservas.confirmarCompra(200.00, datosMovil);

        // Cambio dinamico a Transferencia
        System.out.println("\n>> Comprador cambia estrategia otra vez: Transferencia Bancaria");
        EstrategiaPago transferenciaStrategy = new PagoTransferenciaStrategy("Banco Nacional");
        gestorReservas.setEstrategiaPago(transferenciaStrategy);

        Map<String, String> datosTransferencia = new HashMap<>();
        datosTransferencia.put("cuenta", "0102-1234-5678");
        gestorReservas.confirmarCompra(300.00, datosTransferencia);

        System.out.println("\n=================================================\n");
    }

    // =================================================================
    // PRUEBA PATRON 3 - DECORATOR: Politicas y Restricciones
    // =================================================================
    private static Evento demostrarDecorator(Comprador comprador) {
        System.out.println("=== PRUEBA DECORATOR: POLiTICAS Y RESTRICCIONES ===\n");

        Evento concierto = new Evento();
        concierto.setNombre("Concierto de Rock");

        // Construir politica con múltiples decoradores envueltos
        IPoliticaCompra politicaDecorada = new VerificacionEdadDecorator(
            new LimiteBoletosDecorator(
                new PoliticaEventoBase(), 4
            ), 18
        );

        // Asignar la politica decorada al evento
        concierto.setPolitica(politicaDecorada);

        // Prueba 1: Compra VaLIDA (edad 25, cantidad 3)
        System.out.println(">> Prueba 1: Comprador de 25 anios quiere 3 boletos (limite: 4, edad min: 18)");
        boolean resultado1 = concierto.getPolitica().validarCompra(comprador, 3);
        System.out.println("Resultado: " + (resultado1 ? "APROBADA" : "RECHAZADA") + "\n");

        // Prueba 2: Compra INVaLIDA por cantidad (edad 25, cantidad 6)
        System.out.println(">> Prueba 2: Comprador de 25 anios quiere 6 boletos (limite: 4)");
        boolean resultado2 = concierto.getPolitica().validarCompra(comprador, 6);
        System.out.println("Resultado: " + (resultado2 ? "APROBADA" : "RECHAZADA") + "\n");

        // Prueba 3: Compra INVaLIDA por edad (menor de 18)
        Comprador compradorMenor = new Comprador();
        compradorMenor.setNombre("Pedro Menor");
        compradorMenor.setEdad(15);
        System.out.println(">> Prueba 3: Comprador de 15 anios quiere 2 boletos (edad min: 18)");
        boolean resultado3 = concierto.getPolitica().validarCompra(compradorMenor, 2);
        System.out.println("Resultado: " + (resultado3 ? "APROBADA" : "RECHAZADA") + "\n");

        // Prueba 4: Agregar decorador de membresia
        System.out.println(">> Prueba 4: Agregar restriccion de membresia");
        IPoliticaCompra politicaConMembresia = new RestriccionSocioDecorator(
            new VerificacionEdadDecorator(
                new LimiteBoletosDecorator(
                    new PoliticaEventoBase(), 4
                ), 18
            ), true
        );

        Comprador compradorNoSocio = new Comprador();
        compradorNoSocio.setNombre("Carlos NoSocio");
        compradorNoSocio.setEdad(30);
        compradorNoSocio.setEsSocio(false);
        System.out.println("Comprador NO socio, 30 anios, 2 boletos:");
        boolean resultado4 = politicaConMembresia.validarCompra(compradorNoSocio, 2);
        System.out.println("Resultado: " + (resultado4 ? "APROBADA" : "RECHAZADA") + "\n");

        System.out.println("Comprador Si socio, 25 anios, 2 boletos:");
        boolean resultado5 = politicaConMembresia.validarCompra(comprador, 2);
        System.out.println("Resultado: " + (resultado5 ? "APROBADA" : "RECHAZADA"));

        System.out.println("\n=================================================\n");
        return concierto;
    }

    // =================================================================
    // PRUEBA GESTOR DE EVENTOS: Cancelacion y devolucion
    // =================================================================
    private static void demostrarCancelacionEvento(Evento concierto, List<IBoleto> boletosEmitidos, GestorNotificaciones gestorNotif) {
        System.out.println("=== PRUEBA GESTOR DE EVENTOS: CANCELACIoN Y DEVOLUCIoN ===\n");

        for (IBoleto boleto : boletosEmitidos) {
            concierto.agregarBoleto(boleto);
        }
        boletosEmitidos.get(0).setEstado(EstadoBoleto.VENDIDO);
        boletosEmitidos.get(1).setEstado(EstadoBoleto.RESERVADO);
        boletosEmitidos.get(2).setEstado(EstadoBoleto.DISPONIBLE);

        GestorEventos gestorEventos = new GestorEventos(gestorNotif);
        gestorEventos.confirmarCancelacion("Fuerza mayor", concierto);

        System.out.println("\n=================================================\n");
    }

    // =================================================================
    // PRUEBA PATRON 4 - CHAIN OF RESPONSIBILITY: Gestion de Incidentes
    // =================================================================
    private static void demostrarChainOfResponsibility() {
        System.out.println("=== PRUEBA CHAIN OF RESPONSIBILITY: GESTIoN DE INCIDENTES ===\n");
        GestorIncidentes gestorIncidentes = new GestorIncidentes();

        // Incidente simple: resuelto en primer nivel por AgenteSoporte
        System.out.println(">> Incidente 1 (Simple): Resuelto en primer nivel");
        gestorIncidentes.registrarIncidente("El codigo QR del boleto no carga en la app");

        System.out.println();

        // Incidente complejo: escala automaticamente a DepartamentoAdministracion
        System.out.println(">> Incidente 2 (Complejo): Escala automaticamente al siguiente nivel");
        gestorIncidentes.registrarIncidente("Problema complejo con doble cobro y reembolso pendiente");
    }
}
