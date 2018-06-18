import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

/**
 * @author Jesus Chilca
 *
 */
public class Lineal {
	TimeSeries serie1;
	TimeSeries serie2;
	TimeSeries serie3;

	public Lineal() {
		super();
	}

	public TimeSeries getSerie1() {
		return serie1;
	}

	public void setSerie1(Double serie1) {
		this.serie1.addOrUpdate(new Second(), serie1);
	}

	public TimeSeries getSerie2() {
		return serie2;
	}

	public void setSerie2(Double serie2) {
		this.serie2.addOrUpdate(new Second(), serie2);
	}

	public TimeSeries getSerie3() {
		return serie3;
	}

	public void setSerie3(Double serie3) {
		this.serie3.addOrUpdate(new Second(), serie3);
	}

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

		grafica.addSubtitle(new TextTitle("Source: http://www.google.com", new Font("Dialog", Font.ITALIC, 10)));
		grafica.setBackgroundPaint(Color.LIGHT_GRAY);

		return grafica;
	}

}