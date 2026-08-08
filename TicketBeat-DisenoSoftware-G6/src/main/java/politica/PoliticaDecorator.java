package politica;

import com.ticketbeat.interfaces.IPoliticaCompra;
import com.ticketbeat.modelo.Comprador;

public abstract class PoliticaDecorator implements IPoliticaCompra {
    protected IPoliticaCompra componente;

    public PoliticaDecorator(IPoliticaCompra componente) {
        this.componente = componente;
    }

    @Override
    public boolean validarCompra(Comprador comprador, int cantidad) {
        return componente.validarCompra(comprador, cantidad);
    }

    @Override
    public double calcularReembolso(double monto) {
        return componente.calcularReembolso(monto);
    }

    @Override
    public boolean permiteCambioFecha() {
        return componente.permiteCambioFecha();
    }
}

