package com.ticketbeat.modelo;

import com.ticketbeat.interfaces.ICanal;

public class Comprador extends Usuario {
    private ICanal canalPreferido;
    private int limiteCompra;
    private int edad;
    private boolean esSocio;

    public ICanal getCanalPreferido() { return canalPreferido; }
    public void setCanalPreferido(ICanal canalPreferido) { this.canalPreferido = canalPreferido; }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    public boolean isEsSocio() { return esSocio; }
    public void setEsSocio(boolean esSocio) { this.esSocio = esSocio; }
}

