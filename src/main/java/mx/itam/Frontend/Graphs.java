package mx.itam.Frontend;
import com.vaadin.ui.VerticalLayout;
import mx.itam.Backend.Symbol;
import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.base.elements.XYaxis;
import org.dussan.vaadin.dcharts.data.DataSeries;
import org.dussan.vaadin.dcharts.data.Ticks;
import org.dussan.vaadin.dcharts.metadata.renderers.AxisRenderers;
import org.dussan.vaadin.dcharts.metadata.renderers.SeriesRenderers;
import org.dussan.vaadin.dcharts.options.Axes;
import org.dussan.vaadin.dcharts.options.Highlighter;
import org.dussan.vaadin.dcharts.options.Options;
import org.dussan.vaadin.dcharts.options.SeriesDefaults;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Graphs extends VerticalLayout {
    private final static Logger logger =
            Logger.getLogger(Graphs.class.getName());
    public Graphs(Double[] data, String[] axis, Symbol symbol) {
        DataSeries dataSeries = new DataSeries()
                .add(data);
        SeriesDefaults seriesDefaults = new SeriesDefaults()
                .setRenderer(SeriesRenderers.LINE);
        Axes axes = new Axes()
                .addAxis(
                        new XYaxis()
                                .setLabel("Periodos")
                                .setRenderer(AxisRenderers.CATEGORY)
                                .setTicks(
                                        new Ticks()
                                                .add(axis)));
        Highlighter highlighter = new Highlighter()
                .setShow(true);
        Options options = new Options()
                .setSeriesDefaults(seriesDefaults)
                .setAxes(axes)
                .setTitle(symbol.toString())
                .setHighlighter(highlighter);
        logger.log(Level.INFO,"Cargando la gráfica.");
        DCharts chart = new DCharts()
                .setDataSeries(dataSeries)
                .setOptions(options)
                .show();

        logger.log(Level.INFO,"La gráfica termino de cargarse.");

        this.addComponent(chart);
    }
}
