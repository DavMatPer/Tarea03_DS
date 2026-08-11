package com.ticketbeat.modelo;

/**
 * Antes solo exponía getNombre()/setNombre(); id, email y telefono se
 * heredaban pero ninguna subclase (Comprador, Organizador) podía usarlos
 * (code smell "Legado Rechazado"). Ahora los cuatro campos tienen accesores
 * públicos, disponibles para cualquier subclase.
 *
 * @author Rafael Cosmo
 */
public abstract class Usuario {
    protected String id;
    protected String nombre;
    protected String email;
    protected String telefono;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
