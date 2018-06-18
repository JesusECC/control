import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartPanel;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

public class PanelDeControl extends JFrame implements ActionListener {

	JButton btnAvanzar, btnIzquierda, btnRetroceder, btnDerecha, btnEncender, btnApagar;
	JComboBox cmbRadar;
	Controlador control = new Controlador();
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblIp;
	private JTextField txtPuerto;
	private JTextField txtIP;
	private JTextField txtRuta;
	private JButton btnGuardar;
	private JButton btnLectura;
	Lineal gLineal;

	/**
	 * Launch the application.
	 */

	public void GrafiLinial() {
		// gLineal = new Lineal();
		try {
			JPanel paneIP = new ChartPanel(control.Lineal());
			paneIP.setBounds(12, 223, 801, 384);
			paneIP.repaint();
			contentPane.add(paneIP);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PanelDeControl frame = new PanelDeControl();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the frame.
	 */
	public PanelDeControl() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 847, 679);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		btnAvanzar = new JButton("AVANZAR");
		btnAvanzar.setBounds(526, 37, 117, 25);
		btnAvanzar.addActionListener(this);
		contentPane.setLayout(null);
		contentPane.add(btnAvanzar);

		btnIzquierda = new JButton("IZQUIERDA");
		btnIzquierda.setBounds(404, 90, 117, 25);
		btnIzquierda.addActionListener(this);
		contentPane.add(btnIzquierda);

		btnRetroceder = new JButton("RETROCEDER");
		btnRetroceder.setBounds(526, 143, 117, 25);
		btnRetroceder.addActionListener(this);
		contentPane.add(btnRetroceder);

		btnDerecha = new JButton("DERECHA");
		btnDerecha.setBounds(646, 90, 117, 25);
		btnDerecha.addActionListener(this);
		contentPane.add(btnDerecha);

		cmbRadar = new JComboBox();
		cmbRadar.setBounds(548, 90, 86, 24);
		cmbRadar.setModel(new DefaultComboBoxModel(new String[] { "sonar", "laser" }));
		// cmbRadar.addActionListener(this);
		contentPane.add(cmbRadar);

		btnEncender = new JButton("ENCENDER");
		btnEncender.setBounds(402, 8, 117, 25);
		btnEncender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int puerto = Integer.parseInt(txtPuerto.getText());
				String ip = txtIP.getText();
				control = new Controlador(puerto, ip);
				control.setIndexRadar(cmbRadar.getSelectedIndex());
				control.setRuta(txtRuta.getText());
				control.encender();
				GrafiLinial();
				activarBotones();
			}
		});
		contentPane.add(btnEncender);

		btnApagar = new JButton("APAGAR");
		btnApagar.setBounds(646, 8, 117, 25);
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				control.apagar();
				desactivarBotones();
			}
		});
		contentPane.add(btnApagar);

		lblIp = new JLabel("Puerto:");
		lblIp.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblIp.setBounds(12, 13, 56, 16);
		contentPane.add(lblIp);

		txtPuerto = new JTextField();
		txtPuerto.setText("6665");
		txtPuerto.setBounds(80, 11, 116, 22);
		contentPane.add(txtPuerto);
		txtPuerto.setColumns(10);

		JLabel lblPuerto = new JLabel("IP:");
		lblPuerto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPuerto.setBounds(12, 54, 56, 16);
		contentPane.add(lblPuerto);

		txtIP = new JTextField();
		txtIP.setText("192.168.0.12");
		txtIP.setBounds(80, 51, 116, 22);
		contentPane.add(txtIP);
		txtIP.setColumns(10);

		JLabel lblRuta = new JLabel("Ruta:");
		lblRuta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRuta.setBounds(12, 94, 56, 16);
		contentPane.add(lblRuta);

		txtRuta = new JTextField();
		txtRuta.setText("D:/java/Ficheros/DatosDelSensor.txt");
		txtRuta.setBounds(80, 91, 295, 22);
		contentPane.add(txtRuta);
		txtRuta.setColumns(10);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(12, 143, 97, 25);
		btnGuardar.addActionListener(this);
		contentPane.add(btnGuardar);

		btnLectura = new JButton("Lectura");
		btnLectura.setBounds(131, 143, 97, 25);
		btnLectura.addActionListener(this);
		contentPane.add(btnLectura);

		desactivarBotones();
		GrafiLinial();
	}

	void activarBotones() {
		btnAvanzar.setEnabled(true);
		btnIzquierda.setEnabled(true);
		btnRetroceder.setEnabled(true);
		btnDerecha.setEnabled(true);
		btnEncender.setEnabled(false);
		btnApagar.setEnabled(true);
	}

	void desactivarBotones() {
		btnAvanzar.setEnabled(false);
		btnIzquierda.setEnabled(false);
		btnRetroceder.setEnabled(false);
		btnDerecha.setEnabled(false);
		btnEncender.setEnabled(true);
		btnApagar.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// control.movimiento(e.getActionCommand());
		// System.out.println(e.getSource());
		control.setAccion(e.getActionCommand());
		// System.out.println(e.getActionCommand());
		if ("Guardar".equals(e.getActionCommand())) {
			control.setRuta(txtRuta.getText());
			control.run();
		} else if ("Lectura".equals(e.getActionCommand())) {
			control.start();
		} else {
			control.run();
		}
		// System.out.println(e.getSource());

		// control.start();

	}
}
