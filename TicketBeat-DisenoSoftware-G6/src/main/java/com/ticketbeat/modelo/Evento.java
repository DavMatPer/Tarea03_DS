package com.ticketbeat.modelo;

/**
 *
 * @author Rafael Cosmo
 */
import com.ticketbeat.interfaces.IBoleto;
import com.ticketbeat.interfaces.IPoliticaCompra;
import java.util.List;
import java.util.ArrayList;

public class Evento {
    private String id;
    private String nombre;
    private EstadoEvento estado;
    private int boletosVendidos;
    private double montoRecaudado;
    private List<IBoleto> boletos;
    private IPoliticaCompra politica;

    public Evento() {
        this.boletos = new ArrayList<>();
        this.estado = EstadoEvento.ACTIVO;
    }

    public int getBoletosVendidos() { 
        return boletosVendidos; 
    }

    public void setBoletosVendidos(int boletosVendidos) {
        this.boletosVendidos = boletosVendidos;
    }
    
    public double getMontoRecaudado() { 
        return montoRecaudado; 
    }

    public void setMontoRecaudado(double montoRecaudado) {
        this.montoRecaudado = montoRecaudado;
    }
    
    public EstadoEvento getEstado() {
        return estado;
    }

    public void setEstado(EstadoEvento estado) { 
        this.estado = estado; 
        System.out.println("El estado del evento ha cambiado a: " + estado);
    }

    public List<IBoleto> getBoletos() { return boletos; }
    public void agregarBoleto(IBoleto boleto) { this.boletos.add(boleto); }
    public void setBoletos(List<IBoleto> boletos) { this.boletos = boletos; }

    public IPoliticaCompra getPolitica() { return politica; }
    public void setPolitica(IPoliticaCompra politica) { this.politica = politica; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Determina qué boletos de este evento son elegibles para devolución
     * (vendidos o reservados). Movido aquí desde la extinta clase
     * PoliticaEvento: toda la lógica opera sobre los boletos propios del
     * evento, así que es este objeto quien debe resolverla.
     */
    public List<IBoleto> obtenerBoletosParaDevolucion() {
        List<IBoleto> boletosAfectados = new ArrayList<>();
        if (boletos == null) {
            return boletosAfectados;
        }
        for (IBoleto boleto : boletos) {
            if (boleto == null) continue;
            EstadoBoleto estadoBoleto = boleto.getEstado();
            if (estadoBoleto == EstadoBoleto.VENDIDO || estadoBoleto == EstadoBoleto.RESERVADO) {
                boletosAfectados.add(boleto);
            }
        }
        return boletosAfectados;
    }
}
