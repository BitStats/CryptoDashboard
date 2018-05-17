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

/**
 * Clase para realizar las gráficas o curvas de los datos de las monedas seleccionadas
 * @author BitStats
 */
public class Graphs extends VerticalLayout {
	//Se instancia el logger que llevará la publicaciòn de logs.
    private final static Logger logger =
            Logger.getLogger(Graphs.class.getName());
    
    /**
     * Dibuja la gráfica con los parámetros especificados
     * @param data Los precios de las monedas a graficar
     * @param axis Los nombres de los ejes de la gráfica
     * @param symbol Las monedas cuya comparación se va a graficar
     */
    public Graphs(Double[] data, String[] axis, Symbol symbol) {
	//Se instancian los objetos necesarios para el display de las gráficas.
	//Estos son los datos, los valores en los ejes y las especificaciones visuales.
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
	//Se asignan estas especificaciones al objeto visual.
        Options options = new Options()
                .setSeriesDefaults(seriesDefaults)
                .setAxes(axes)
                .setTitle(symbol.toString())
                .setHighlighter(highlighter);
	//Mensaje tipo log.
        logger.log(Level.INFO,"Cargando la gráfica.");
	//Se despliega el resultado visual de la gráfica precargada.
        DCharts chart = new DCharts()
                .setDataSeries(dataSeries)
                .setOptions(options)
                .show();

        logger.log(Level.INFO,"La gráfica termino de cargarse.");

        this.addComponent(chart);
    }
}
