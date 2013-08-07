package kermor.visual;

import kermor.ReducedModel;

import org.apache.commons.math.linear.RealMatrix;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * Basic methods for plotting results of dynamical sytem simulations.
 * 
 * This class uses the JFreeChart classes for plot generation.
 * 
 * @author Daniel Wirtz @date 2013-08-07
 * 
 */
public class Plotter extends ApplicationFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5905547086247935278L;

	/**
	 * A demonstration application showing how to create a simple time series chart.
	 * 
	 * @param title
	 * the frame title.
	 */
	public Plotter(final String title) {
		super(title);
		setVisible(false);
	}

	public void plotResult(double[] times, RealMatrix y, ReducedModel rm) {
		plot(times, y, rm.name);
	}

	public void plotResult(double[] times, double[][] y, ReducedModel rm) {
		plot(times, y, rm.name);
	}

	public void plot(double[] times, RealMatrix y, String chartname) {
		final XYDataset dataset = createDataset(times, y);
		plot(times, dataset, chartname);
	}

	public void plot(double[] times, double[][] y, String chartname) {
		final XYDataset dataset = createDataset(times, y);
		plot(times, dataset, chartname);
	}

	public void plot(double[] x, double[] fx, String chartname) {
		final XYDataset dataset = createDataset(x, fx);
		plot(x, dataset, chartname);
	}

	private void plot(double[] xdata, XYDataset y, String chartname) {
		final JFreeChart chart = ChartFactory.createXYLineChart(chartname, "time t", "outputs y_i", y,
				PlotOrientation.VERTICAL, false, true, false);
		final ChartPanel pnl = new ChartPanel(chart);
		pnl.setPreferredSize(new java.awt.Dimension(500, 270));
		pnl.setMouseZoomable(true, false);
		setContentPane(pnl);
		pack();
		RefineryUtilities.centerFrameOnScreen(this);
		setVisible(true);
	}

	private XYDataset createDataset(double[] times, RealMatrix y) {
		XYSeriesCollection dataset = new XYSeriesCollection();
		for (int i = 0; i < y.getRowDimension(); i++) {
			XYSeries series = new XYSeries("Dim " + (i + 1));
			for (int j = 0; j < y.getColumnDimension(); j++) {
				series.add(times[j], y.getEntry(i, j));
			}
			dataset.addSeries(series);
		}
		return dataset;
	}

	private XYDataset createDataset(double[] times, double[][] y) {
		XYSeriesCollection dataset = new XYSeriesCollection();
		for (int i = 0; i < y.length; i++) {
			XYSeries series = new XYSeries("Dim " + (i + 1));
			for (int j = 0; j < y[i].length; j++) {
				series.add(times[j], y[i][j]);
			}
			dataset.addSeries(series);
		}
		return dataset;
	}

	private XYDataset createDataset(double[] x, double[] fx) {
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series = new XYSeries("f(x)");
		for (int i = 0; i < x.length; i++) {
			series.add(x[i], fx[i]);
		}
		dataset.addSeries(series);
		return dataset;
	}
}
