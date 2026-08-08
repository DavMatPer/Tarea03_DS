/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
    
    public double getMontoRecaudado() { 
        return montoRecaudado; 
    }
    
    public void setEstado(String estado) { 
        this.estado = estado; 
        System.out.println("El estado del evento ha cambiado a: " + estado);
    }

    public List<IBoleto> getBoletos() { return boletos; }
    public void agregarBoleto(IBoleto boleto) { this.boletos.add(boleto); }

    public IPoliticaCompra getPolitica() { return politica; }
    public void setPolitica(IPoliticaCompra politica) { this.politica = politica; }
}
