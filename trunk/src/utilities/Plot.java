package utilities;
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

	
	public Plot() {

//        super(title);
//        this.title = title;
		super("");
        plot = new CombinedDomainXYPlot(new NumberAxis("Domain"));
        

    }


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
    public void setSubplot(double[]profile,String name){
    	XYDataset data1= createDataset(profile,name);
    	 final XYItemRenderer renderer1 = new StandardXYItemRenderer();
         final NumberAxis rangeAxis1 = new NumberAxis(name);
    	final XYPlot subplot1 = new XYPlot(data1, null, rangeAxis1, renderer1);
    	 subplot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
    	 // add the subplots...
         plot.add(subplot1, 1);
    }
    public void draw( ) {

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
    private XYDataset createDataset(double[]values,String name) {

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