//package ru.ifmo.enf.melnikovd.matan;
//
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.XYPlot;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;
//import org.jfree.experimental.chart.renderer.xy.XYSmoothLineAndShapeRenderer;
//import org.jfree.ui.ApplicationFrame;
//import org.jfree.ui.RefineryUtilities;
//
//import java.awt.*;
//
//import static java.lang.Math.*;
//
//public class Fourier extends ApplicationFrame {
//
//    private static final long serialVersionUID = 5804050150116959400L;
//
//    private XYPlot plot;
//    private int datasetIndex = 0;
//
//    public Fourier(final String title) {
//        super(title);
//        XYSeriesCollection dataset1 = createDataset(1);
//        JFreeChart chart = ChartFactory.createXYLineChart("Fourier Series", "t", "f(t)", dataset1);
//        ChartPanel chartPanel = new ChartPanel(chart);
//        plot = chart.getXYPlot();
//        add(chartPanel);
//        chartPanel.setPreferredSize(new Dimension(800, 600));
//
//        for (int n : new int[] { 3, 50, 100 }) { // n values
//            datasetIndex++;
//            plot.setDataset(datasetIndex, createDataset(n));
//            XYSmoothLineAndShapeRenderer renderer = new XYSmoothLineAndShapeRenderer();
//            plot.setRenderer(datasetIndex, renderer);
//        }
//
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        pack();
//    }
//
//    private XYSeriesCollection createDataset(int n) {
//        final XYSeries series = new XYSeries("n = " + n);
//        for (double t = -8; t < 8; t += 0.01) {
//            series.add(t, function(t, n));
//        }
//        return new XYSeriesCollection(series);
//    }
//
//    private double function(double t, int nValue) {
//        int T = 5;
//        double a0 = 4 / 5;
//        double sum = a0 / 2;
//
//        for (int n = 1; n <= nValue; n++) {
//            double an = 2 / (PI * n) * sin(2 * PI * n) + 5 / (2 * PI * PI * n * n)
//                    * (cos(2 * PI * n) - cos(6 * PI * n / 5));
//            double bn = -2 / (PI * n) * cos(2 * PI * n) + 5 / (2 * PI * PI * n * n)
//                    * (sin(2 * PI * n) - sin(6 * PI * n / 5));
//
//            sum += (an * cos(n * 2 * PI / T * (t)) + bn * sin(n * 2 * PI / T * (t)));
//        }
//
//        return sum;
//    }
//
//    public static void main(final String[] args) {
//        Fourier demo = new Fourier("Fourier");
//        RefineryUtilities.centerFrameOnScreen(demo);
//        demo.setVisible(true);
//    }
//
//}