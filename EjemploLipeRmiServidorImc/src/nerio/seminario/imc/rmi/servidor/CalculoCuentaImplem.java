package nerio.seminario.imc.rmi.servidor;

import nerio.seminario.imc.rmi.lib.DatosCuenta;
import nerio.seminario.imc.rmi.lib.IRemotaCuenta;

public class CalculoCuentaImplem implements IRemotaCuenta {
    @Override
    public DatosCuenta dividirCuenta(DatosCuenta datos) {
        if (datos.getCantidadPersonas() > 0) {
            float division = datos.getValorTotal() / datos.getCantidadPersonas();
            datos.setPagoPorPersona(division);
            datos.setMensaje("División exitosa");
        } else {
            datos.setPagoPorPersona(0);
            datos.setMensaje("Error: Mínimo 1 persona");
        }
        return datos;
    }
}
