package nerio.seminario.imc.rmi.cliente;

import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;
import nerio.seminario.imc.rmi.lib.DatosCuenta;
import nerio.seminario.imc.rmi.lib.IRemotaCuenta;

import javax.swing.*;
import java.awt.*;

public class VentanaCliente extends JFrame {
    private JTextField txtIp, txtPuerto, txtTotal, txtPersonas;
    private JLabel lblResultado, lblMensaje;
    private JButton btnConectar, btnCalcular;
    private Client clienteRmi;
    private IRemotaCuenta servicioRemoto;

    public VentanaCliente() {
        setTitle("Divisor de Cuentas - LipeRMI");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 10, 10));

        add(new JLabel(" IP del Servidor:"));
        txtIp = new JTextField("localhost");
        add(txtIp);

        add(new JLabel(" Puerto:"));
        txtPuerto = new JTextField("9007");
        add(txtPuerto);

        btnConectar = new JButton("Conectar");
        add(btnConectar);
        add(new JLabel(""));

        add(new JLabel(" Valor Total ($):"));
        txtTotal = new JTextField();
        add(txtTotal);

        add(new JLabel(" Cantidad Personas:"));
        txtPersonas = new JTextField();
        add(txtPersonas);

        btnCalcular = new JButton("Dividir Cuenta");
        btnCalcular.setEnabled(false);
        add(btnCalcular);
        add(new JLabel(""));

        add(new JLabel(" Pago por persona:"));
        lblResultado = new JLabel("$0.00");
        lblResultado.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblResultado);

        add(new JLabel(" Estado:"));
        lblMensaje = new JLabel("---");
        add(lblMensaje);

        btnConectar.addActionListener(e -> conectarAlServidor());
        btnCalcular.addActionListener(e -> calcularDivision());
    }

    private void conectarAlServidor() {
        try {
            String ip = txtIp.getText().trim();
            int puerto = Integer.parseInt(txtPuerto.getText().trim());
            CallHandler callHandler = new CallHandler();
            clienteRmi = new Client(ip, puerto, callHandler);

            // Conectamos con la interfaz de la cuenta
            servicioRemoto = (IRemotaCuenta) clienteRmi.getGlobal(IRemotaCuenta.class);

            JOptionPane.showMessageDialog(this, "¡Conectado al servidor!");
            btnConectar.setEnabled(false);
            btnCalcular.setEnabled(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al conectar: " + ex.getMessage());
        }
    }

    private void calcularDivision() {
        try {
            float total = Float.parseFloat(txtTotal.getText());
            int personas = Integer.parseInt(txtPersonas.getText());

            DatosCuenta datos = new DatosCuenta(total, personas);
            datos = servicioRemoto.dividirCuenta(datos); // Llamada remota al servidor

            lblResultado.setText("$" + String.format("%.2f", datos.getPagoPorPersona()));
            lblMensaje.setText(datos.getMensaje());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ingrese números válidos.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaCliente().setVisible(true));
    }
}