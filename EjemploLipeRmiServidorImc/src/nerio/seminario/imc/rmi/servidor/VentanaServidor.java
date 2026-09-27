package nerio.seminario.imc.rmi.servidor;

import nerio.seminario.imc.rmi.lib.IRemotaCuenta;
import net.sf.lipermi.exception.LipeRMIException;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Server;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class VentanaServidor extends JFrame {
    private JTextField txtPuerto;
    private JButton btnIniciar;
    private JLabel lblEstado;
    private JTextArea txtLog;
    private Server servidor;
    private CallHandler callHandler;

    public VentanaServidor() {
        setTitle("Servidor IMC - LipeRMI");
        setSize(420, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelSuperior = new JPanel(new FlowLayout());
        panelSuperior.add(new JLabel("Puerto:"));
        txtPuerto = new JTextField("9007", 6);
        panelSuperior.add(txtPuerto);

        btnIniciar = new JButton("INICIAR");
        btnIniciar.setBackground(Color.GREEN);
        panelSuperior.add(btnIniciar);

        lblEstado = new JLabel("Estado: OFFLINE");
        lblEstado.setForeground(Color.RED);
        panelSuperior.add(lblEstado);

        txtLog = new JTextArea();
        txtLog.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtLog);

        add(panelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        btnIniciar.addActionListener(e -> alternarServidor());
    }

    private void alternarServidor() {
        if (servidor == null) {
            try {
                int puerto = Integer.parseInt(txtPuerto.getText().trim());
                callHandler = new CallHandler();
                CalculoCuentaImplem implem = new CalculoCuentaImplem();
                callHandler.registerGlobal(IRemotaCuenta.class, implem);

                servidor = new Server();
                servidor.bind(puerto, callHandler);

                lblEstado.setText("Estado: ONLINE");
                lblEstado.setForeground(new Color(0, 150, 0));
                btnIniciar.setText("DETENER");
                btnIniciar.setBackground(Color.RED);
                txtLog.append("Servidor iniciado en el puerto " + puerto + "\n");
            } catch (IOException | LipeRMIException ex) {
                JOptionPane.showMessageDialog(this, "Error iniciando servidor: " + ex.getMessage());
            }
        } else {
            servidor.close();
            servidor = null;

            lblEstado.setText("Estado: OFFLINE");
            lblEstado.setForeground(Color.RED);
            btnIniciar.setText("INICIAR");
            btnIniciar.setBackground(Color.GREEN);
            txtLog.append("Servidor detenido.\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaServidor().setVisible(true));
    }
}