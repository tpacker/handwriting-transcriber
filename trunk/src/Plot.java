import java.awt.Font;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * A demonstration application showing how to create a vertical combined chart.
 *
 */
public class Plot extends ApplicationFrame {

    /**
     * Constructs a new demonstration application.
     *
     * @param title  the frame title.
     */
	final CombinedDomainXYPlot plot;
	String title = "";
    public Plot(String title) {

        super(title);
        this.title = title;
        plot = new CombinedDomainXYPlot(new NumberAxis("Domain"));
        

    }

    /**
     * Creates a combined chart.
     *
     * @return The combined chart.
     */
    public void setSubplot(float[]profile,String name){
    	XYDataset data1= createDataset(profile,name);
    	 final XYItemRenderer renderer1 = new StandardXYItemRenderer();
         final NumberAxis rangeAxis1 = new NumberAxis(name);
    	final XYPlot subplot1 = new XYPlot(data1, null, rangeAxis1, renderer1);
    	 subplot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
    	 // add the subplots...
         plot.add(subplot1, 1);
    }
    void draw( ) {

        plot.setGap(10.0);
        plot.setOrientation(PlotOrientation.VERTICAL);
//        return 
        
        
        final JFreeChart chart = new JFreeChart(title,JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        final ChartPanel panel = new ChartPanel(chart, true, true, true, false, true);
        panel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(panel);
        
        this.pack();
		RefineryUtilities.centerFrameOnScreen(this);
		this.setVisible(true);

    }

    /**
     * Creates a sample dataset.
     *
     * @return Series 1.
     */
    private XYDataset createDataset1() {

        // create dataset 1...
        final XYSeries series1 = new XYSeries("Series 1a");
        series1.add(10.0, 12353.3);
        series1.add(20.0, 13734.4);
        series1.add(30.0, 14525.3);
        series1.add(40.0, 13984.3);
        series1.add(50.0, 12999.4);
        series1.add(60.0, 14274.3);
        series1.add(70.0, 15943.5);
        series1.add(80.0, 14845.3);
        series1.add(90.0, 14645.4);
        series1.add(100.0, 16234.6);
        series1.add(110.0, 17232.3);
        series1.add(120.0, 14232.2);
        series1.add(130.0, 13102.2);
        series1.add(140.0, 14230.2);
        series1.add(150.0, 11235.2);

//        final XYSeries series1b = new XYSeries("Series 1b");
//        series1b.add(10.0, 15000.3);
//        series1b.add(20.0, 11000.4);
//        series1b.add(30.0, 17000.3);
//        series1b.add(40.0, 15000.3);
//        series1b.add(50.0, 14000.4);
//        series1b.add(60.0, 12000.3);
//        series1b.add(70.0, 11000.5);
//        series1b.add(80.0, 12000.3);
//        series1b.add(90.0, 13000.4);
//        series1b.add(100.0, 12000.6);
//        series1b.add(110.0, 13000.3);
//        series1b.add(120.0, 17000.2);
//        series1b.add(130.0, 18000.2);
//        series1b.add(140.0, 16000.2);
//        series1b.add(150.0, 17000.2);

        final XYSeriesCollection collection = new XYSeriesCollection();
        collection.addSeries(series1);
//        collection.addSeries(series1b);
        return collection;

    }
    private XYDataset createDataset(float[]values,String name) {

        // create dataset 1...
        final XYSeries series = new XYSeries(name);
        
        for(int i=0; i<values.length;i++){
        	series.add(i,values[i]);
        }
        
        final XYSeriesCollection collection = new XYSeriesCollection();
        collection.addSeries(series);
        return collection;

    }
}