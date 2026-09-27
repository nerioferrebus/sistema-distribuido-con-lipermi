package nerio.seminario.imc.rmi.lib;

import java.io.Serializable;

public class DatosCuenta implements Serializable {
    private float valorTotal;
    private int cantidadPersonas;
    private float pagoPorPersona;
    private String mensaje;

    public DatosCuenta(float valorTotal, int cantidadPersonas) {
        this.valorTotal = valorTotal;
        this.cantidadPersonas = cantidadPersonas;
    }

    public float getValorTotal() { return valorTotal; }
    public int getCantidadPersonas() { return cantidadPersonas; }
    public float getPagoPorPersona() { return pagoPorPersona; }
    public void setPagoPorPersona(float pagoPorPersona) { this.pagoPorPersona = pagoPorPersona; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}