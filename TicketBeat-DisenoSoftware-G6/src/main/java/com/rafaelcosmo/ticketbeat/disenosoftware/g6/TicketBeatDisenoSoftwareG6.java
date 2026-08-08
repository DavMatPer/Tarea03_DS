/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.rafaelcosmo.ticketbeat.disenosoftware.g6;

/**
 *
 * @author Rafael Cosmo
 */
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

        // Datos globales
        Comprador comprador = new Comprador();
        comprador.setNombre("Juan Perez");
        comprador.setCanalPreferido(new CanalEmail());
        comprador.setEdad(25);
        comprador.setEsSocio(true);

        GestorNotificaciones gestorNotif = new GestorNotificaciones();
        Evento concierto = new Evento();


        // =================================================================
        // PRUEBA PATRON 1 - FACTORY METHOD: Creación de Boletos
        // =================================================================
        System.out.println("=== PRUEBA FACTORY METHOD: CREACIÓN DE BOLETOS ===\n");

        // Se crean los creadores concretos (sin depender de las clases concretas de boleto)
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

        // Verificación: crear boletos via Factory y confirmar tipos
        System.out.println("\n>> Verificación de instancias creadas via Factory Method:");
        IBoleto boletoVip = creadorVIP.crearBoleto();
        IBoleto boletoGen = creadorGeneral.crearBoleto();
        IBoleto boletoRes = creadorReservado.crearBoleto();

        System.out.println("Boleto VIP -> Precio: $" + boletoVip.getPrecio() + " | Estado: " + boletoVip.getEstado());
        System.out.println("Boleto General -> Precio: $" + boletoGen.getPrecio() + " | Estado: " + boletoGen.getEstado());
        System.out.println("Boleto Reservado -> Precio: $" + boletoRes.getPrecio() + " | Estado: " + boletoRes.getEstado());

        System.out.println("\n=================================================\n");


        // =================================================================
        // PRUEBA PATRON 2 - STRATEGY: Procesamiento de Pagos
        // =================================================================
        System.out.println("=== PRUEBA STRATEGY: PROCESAMIENTO DE PAGOS ===\n");
        GestorReservas gestorReservas = new GestorReservas();

        gestorReservas.buscarEventos();
        gestorReservas.seleccionarEvento();
        gestorReservas.elegirCantidadYTipoDeEntrada();

        // Pago con Tarjeta
        System.out.println("\n>> Comprador selecciona: Tarjeta de Crédito (VISA)");
        EstrategiaPago tarjetaStrategy = new PagoTarjetaStrategy("VISA");
        gestorReservas.setEstrategiaPago(tarjetaStrategy);

        Map<String, String> datosTarjeta = new HashMap<>();
        datosTarjeta.put("numeroTarjeta", "1234-5678-9012-3456");
        datosTarjeta.put("cvv", "123");
        gestorReservas.confirmarCompra(150.50, datosTarjeta);

        // Cambio dinámico de estrategia a Pago Móvil
        System.out.println("\n>> Comprador cambia estrategia en tiempo de ejecución: Pago Móvil");
        EstrategiaPago movilStrategy = new PagoMovilStrategy("PagoFlash");
        gestorReservas.setEstrategiaPago(movilStrategy);

        Map<String, String> datosMovil = new HashMap<>();
        datosMovil.put("numeroTelefono", "0412-1234567");
        gestorReservas.confirmarCompra(200.00, datosMovil);

        // Cambio dinámico a Transferencia
        System.out.println("\n>> Comprador cambia estrategia otra vez: Transferencia Bancaria");
        EstrategiaPago transferenciaStrategy = new PagoTransferenciaStrategy("Banco Nacional");
        gestorReservas.setEstrategiaPago(transferenciaStrategy);

        Map<String, String> datosTransferencia = new HashMap<>();
        datosTransferencia.put("cuentaOrigen", "0102-1234-5678");
        gestorReservas.confirmarCompra(300.00, datosTransferencia);

        System.out.println("\n=================================================\n");


        // =================================================================
        // PRUEBA PATRON 3 - DECORATOR: Políticas y Restricciones
        // =================================================================
        System.out.println("=== PRUEBA DECORATOR: POLÍTICAS Y RESTRICCIONES ===\n");

        // Construir política con múltiples decoradores envueltos
        IPoliticaCompra politicaDecorada = new VerificacionEdadDecorator(
            new LimiteBoletosDecorator(
                new PoliticaEventoBase(), 4
            ), 18
        );

        // Asignar la política decorada al evento
        concierto.setPolitica(politicaDecorada);

        // Prueba 1: Compra VÁLIDA (edad 25, cantidad 3)
        System.out.println(">> Prueba 1: Comprador de 25 años quiere 3 boletos (límite: 4, edad mín: 18)");
        boolean resultado1 = concierto.getPolitica().validarCompra(comprador, 3);
        System.out.println("Resultado: " + (resultado1 ? "APROBADA" : "RECHAZADA") + "\n");

        // Prueba 2: Compra INVÁLIDA por cantidad (edad 25, cantidad 6)
        System.out.println(">> Prueba 2: Comprador de 25 años quiere 6 boletos (límite: 4)");
        boolean resultado2 = concierto.getPolitica().validarCompra(comprador, 6);
        System.out.println("Resultado: " + (resultado2 ? "APROBADA" : "RECHAZADA") + "\n");

        // Prueba 3: Compra INVÁLIDA por edad (menor de 18)
        Comprador compradorMenor = new Comprador();
        compradorMenor.setNombre("Pedro Menor");
        compradorMenor.setEdad(15);
        System.out.println(">> Prueba 3: Comprador de 15 años quiere 2 boletos (edad mín: 18)");
        boolean resultado3 = concierto.getPolitica().validarCompra(compradorMenor, 2);
        System.out.println("Resultado: " + (resultado3 ? "APROBADA" : "RECHAZADA") + "\n");

        // Prueba 4: Agregar decorador de membresía
        System.out.println(">> Prueba 4: Agregar restricción de membresía");
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
        System.out.println("Comprador NO socio, 30 años, 2 boletos:");
        boolean resultado4 = politicaConMembresia.validarCompra(compradorNoSocio, 2);
        System.out.println("Resultado: " + (resultado4 ? "APROBADA" : "RECHAZADA") + "\n");

        System.out.println("Comprador SÍ socio, 25 años, 2 boletos:");
        boolean resultado5 = politicaConMembresia.validarCompra(comprador, 2);
        System.out.println("Resultado: " + (resultado5 ? "APROBADA" : "RECHAZADA"));

        System.out.println("\n=================================================\n");


        // =================================================================
        // PRUEBA PATRON 4 - CHAIN OF RESPONSIBILITY: Gestión de Incidentes
        // =================================================================
        System.out.println("=== PRUEBA CHAIN OF RESPONSIBILITY: GESTIÓN DE INCIDENTES ===\n");
        GestorIncidentes gestorIncidentes = new GestorIncidentes();

        // Incidente simple: resuelto en primer nivel por AgenteSoporte
        System.out.println(">> Incidente 1 (Simple): Resuelto en primer nivel");
        gestorIncidentes.registrarIncidente("El código QR del boleto no carga en la app");

        System.out.println();

        // Incidente complejo: escala automáticamente a DepartamentoAdministracion
        System.out.println(">> Incidente 2 (Complejo): Escala automáticamente al siguiente nivel");
        gestorIncidentes.registrarIncidente("Problema complejo con doble cobro y reembolso pendiente");


        System.out.println("\n=================================================");
        System.out.println("          FIN DE LA SIMULACIÓN - GRUPO 6");
        System.out.println("=================================================");
    }
}