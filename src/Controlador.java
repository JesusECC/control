import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import javaclient3.PlayerClient;
import javaclient3.Position2DInterface;
import javaclient3.RangerInterface;
import javaclient3.structures.PlayerConstants;
import javaclient3.structures.ranger.PlayerRangerData;

public class Controlador extends Thread {

	PlayerClient robot = null;
	Position2DInterface posi = null;
	RangerInterface ranger = null;
	int[] lados = new int[3];
	int indexRadar = 0, puerto = 6665;
	String servidor = "localhost", accion, ruta;
	Lineal Glineal;
	Fichero f = new Fichero();
	TimeSeries serie1;
	TimeSeries serie2;
	TimeSeries serie3;

	// los array list para el speed y la rotacion

	ArrayList<String> d1 = new ArrayList<>();
	ArrayList<String> d2 = new ArrayList<>();

	public Controlador() {
		super();
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public void setIndexRadar(int indexRadar) {
		this.indexRadar = indexRadar;
	}

	public Controlador(int puerto, String servidor) {
		this.puerto = puerto;
		this.servidor = servidor;
	}

	// metodos
	public JFreeChart Lineal() {
		serie1 = new TimeSeries("sensor 0", "Fecha", "0");
		serie2 = new TimeSeries("sensor 3", "Fecha", "3");
		serie3 = new TimeSeries("sensor 7", "Fecha", "7");
		TimeSeriesCollection datos = new TimeSeriesCollection();
		datos.addSeries(serie1);
		datos.addSeries(serie2);
		datos.addSeries(serie3);

		serie1.setMaximumItemCount(20);
		JFreeChart grafica = ChartFactory.createTimeSeriesChart("lectura del sensor", "Tiempo", "Valor", datos, true,
				true, true);

		grafica.addSubtitle(new TextTitle("Jesus Chilca", new Font("Dialog", Font.ITALIC, 10)));
		grafica.setBackgroundPaint(Color.LIGHT_GRAY);

		return grafica;
	}

	public void apagar() {
		if (ranger.isDataReady()) {
			posi.setMotorPower(0);
			ranger.setRangerPower(0);
			ranger = robot.requestInterfaceRanger(indexRadar, PlayerConstants.PLAYER_CLOSE_MODE);
			ranger = null;
			ranger = null;
			robot.close();
		}
		System.out.println("APAGADO");

	}

	public void encender() {
		try {
			robot = new PlayerClient(servidor, puerto);
			posi = robot.requestInterfacePosition2D(0, PlayerConstants.PLAYER_OPEN_MODE);
			ranger = robot.requestInterfaceRanger(indexRadar, PlayerConstants.PLAYER_OPEN_MODE);
			ranger.setRangerPower(1);
			posi.setMotorPower(1);

			// generacion de motor
			switch (ranger.getDeviceAddress().getIndex()) {
			case 0:
				lados[0] = 5;
				lados[1] = 10;
				lados[2] = 15;
				break;
			case 1:
				lados[0] = 120;
				lados[1] = 240;
				lados[2] = 360;
				break;
			}

		} catch (Exception e) {
			System.err.println(e.toString());
			System.exit(1);
		}
		robot.runThreaded(-1, -1);

	}

	public void obstaculo(int inicio, int fin) {
		boolean obstaculo = false;
		for (int i = inicio; i < fin; i++) {
			if (ranger.isDataIntnsReady()) {
				if (ranger.getData().getRanges()[i] < 1.5)
					obstaculo = true;
			}
		}
	}

	public void DataSensor() {
		// sonar
		if (indexRadar == 0) {
			serie1.addOrUpdate(new Second(), ranger.getData().getRanges()[0]);
			serie2.addOrUpdate(new Second(), ranger.getData().getRanges()[3]);
			serie3.addOrUpdate(new Second(), ranger.getData().getRanges()[7]);
		} else if (indexRadar == 1) {
			serie1.addOrUpdate(new Second(), ranger.getData().getRanges()[0]);
			serie2.addOrUpdate(new Second(), ranger.getData().getRanges()[89]);
			serie3.addOrUpdate(new Second(), ranger.getData().getRanges()[179]);
		}
		/*
		 * if (indexRadar == 0) { Glineal.setSerie1(ranger.getData().getRanges()[0]);
		 * Glineal.setSerie2(ranger.getData().getRanges()[3]);
		 * Glineal.setSerie3(ranger.getData().getRanges()[7]); } else if (indexRadar ==
		 * 1) { Glineal.setSerie1(ranger.getData().getRanges()[0]); Glineal.setSerie2(
		 * ranger.getData().getRanges()[89]);
		 * Glineal.setSerie3(ranger.getData().getRanges()[179]); }
		 */
	}

	public boolean LecturaData() {
		String ruta = getRuta();
		File fichero = new File(ruta);
		Scanner s = null;
		if (fichero.exists()) {
			try {
				// Leemos el contenido del fichero
				s = new Scanner(fichero);
				// Leemos linea a linea el fichero
				while (s.hasNextLine()) {
					String linea = s.nextLine(); // Guardamos la linea en un String
					String[] parts = linea.split(",");
					String part1 = parts[0];
					String part2 = parts[1];
					d1.add(part1);
					d2.add(part2);
				}
				return true;
			} catch (Exception ex) {
				System.out.println("Mensaje: " + ex.getMessage());
			} finally {
				// Cerramos el fichero tanto si la lectura ha sido correcta o no
				try {
					if (s != null)
						s.close();
				} catch (Exception ex2) {
					System.out.println("Mensaje 2: " + ex2.getMessage());
				}
			}
		}
		return false;
	}

	public void replicaRecorrido(String speed, String turnrate) {
		// double speed = 0, turnrate = 0;

		// System.out.println("ACCION: "+accion);

		posi.setSpeed(Double.parseDouble(speed), Double.parseDouble(turnrate));
		DataSensor();
		try {
			Thread.sleep(500);
		} catch (Exception e) {
		}
		posi.setSpeed(0f, 0);
	}

	public void movimiento(String accion) {

		// control normal
		double speed = 0, turnrate = 0;
		if ("IZQUIERDA".equals(accion)) {
			DataSensor();
			speed = 0f;
			turnrate = 1;
			obstaculo(lados[1] + 1, lados[2]);
		} else if ("AVANZAR".equals(accion)) {
			DataSensor();
			speed = 0.5;
			turnrate = 0;
			obstaculo(lados[0] + 1, lados[1]);

		} else if ("RETROCEDER".equals(accion)) {
			DataSensor();
			speed = -0.5f;
			turnrate = 0;
		} else if ("DERECHA".equals(accion)) {
			DataSensor();
			speed = 0;
			turnrate = -1;
			obstaculo(0, lados[0]);
		}
		d1.add(String.valueOf(speed));
		d2.add(String.valueOf(turnrate));
		posi.setSpeed(speed, turnrate);
		try {
			Thread.sleep(500);
		} catch (Exception e) {
			System.out.println(e);
		}
		posi.setSpeed(0f, 0);
	}

	public void GuardarData() {
		if (1 < d1.size() && 1 < d2.size()) {
			f.DataSensor(getRuta(), d1, d2);
			d1.clear();
			d2.clear();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//
		movimiento(getAccion());
		if ("Guardar".equals(getAccion())) {
			GuardarData();
		} else if ("Lectura".equals(getAccion())) {
			boolean val = LecturaData();
			if (true == val) {
				for (int i = 0; i < d1.size(); i++) {
					replicaRecorrido(d1.get(i), d2.get(i));
				}
				// d1.clear();
				// d2.clear();
			}
		}

	}

}
