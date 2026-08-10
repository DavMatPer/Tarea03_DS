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
    private String estado;
    private int boletosVendidos;
    private double montoRecaudado;
    private List<IBoleto> boletos;
    private IPoliticaCompra politica;

    public Evento() {
        this.boletos = new ArrayList<>();
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
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) { 
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
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */