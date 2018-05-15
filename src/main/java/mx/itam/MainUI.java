package mx.itam;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import mx.itam.Backend.*;
import mx.itam.Frontend.Graphs;
import mx.itam.Frontend.NavigationBar;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
/**
 * Clase que despliega la interfaz para el usuario
 */
public class MainUI extends UI {
    private int graphId;
    private int infoGridId;
    private int actualPriceGridId;
    private Label lblActualPrice;
    private VerticalLayout layout;
    private HorizontalLayout gridsLayout;
    
    private final static Logger logger = Logger.getLogger(MainUI.class.getName());
    
    /**
     * Inicializador de dependencias y todo lo necesario para comenzar
     * @param vaadinRequest 
     */
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        layout = new VerticalLayout(new NavigationBar());
        layout.setStyleName("body-bitstats");
        gridsLayout = new HorizontalLayout();
        DataServices data = new DataServices();
        ComboBox<Interval> cmbInterval = new ComboBox<>("Periodo:");
        ComboBox<Symbol> cmbSymbol = new ComboBox<>("Monedas:");
        Button btnUpdatePrices = new Button("Actualizar precios");
        btnUpdatePrices.addClickListener(e->{
           updatePrices(data);
        });
        lblActualPrice = new Label("Precio Actual: ");
        try {
            lblActualPrice.setValue("Precio Actual: "+data.getActualData().getMaxPrice());
        } catch (IOException e) {
            //Error de fetch precio.
            logger.log(Level.SEVERE, "Ocurrió un error crítico inicializando la página", e);
        }
        Graphs graph;
        graphId = 0;
        actualPriceGridId = 0;
        infoGridId = 0;
        graph = new Graphs(data.generateChartData(),data.generateChartAxis(),data.getSelectedSymbol());
        cmbSymbol.setItems(data.getSymbols());
        cmbInterval.setItems(data.getIntervals());
        cmbSymbol.setEmptySelectionAllowed(false);
        cmbInterval.setEmptySelectionAllowed(false);
        cmbSymbol.setSelectedItem(data.getSelectedSymbol());
        cmbInterval.setSelectedItem(data.getSelectedInterval());
        cmbSymbol.addValueChangeListener(e->{
           data.setSelectedSymbol(cmbSymbol.getSelectedItem().get());
           repaint(data);
        });
        cmbInterval.addValueChangeListener(e->{
           data.setSelectedInterval(cmbInterval.getSelectedItem().get());
           repaint(data);
        });
        Grid<DataHistorica> infoGrid = infoGrid(data);
        Grid<ActualPrice> actualPriceGrid = actualPriceGrid(data);
        HorizontalLayout optionBar = new HorizontalLayout(cmbInterval,cmbSymbol);
        optionBar.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        layout.addComponents(optionBar);
        gridsLayout.addComponents(infoGrid,actualPriceGrid);
        gridsLayout.setSizeFull();
        gridsLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        HorizontalLayout priceButtons = new HorizontalLayout(lblActualPrice,btnUpdatePrices);
        layout.addComponents(graph,priceButtons,gridsLayout);
        priceButtons.setExpandRatio(lblActualPrice,.7f);
        priceButtons.setExpandRatio(btnUpdatePrices,.3f);

        priceButtons.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        graphId = layout.getComponentIndex(graph);
        infoGridId = gridsLayout.getComponentIndex(infoGrid);
        actualPriceGridId = gridsLayout.getComponentIndex(actualPriceGrid);
        gridsLayout.setExpandRatio(infoGrid,.7f);
        gridsLayout.setExpandRatio(actualPriceGrid,.3f);

        setContent(layout);
        logger.log(Level.INFO, "Se inicializó correctamente la página");
    }

    @WebServlet(urlPatterns = "/*", name = "BitStats", asyncSupported = true)
    @VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

    /**
     * Redespliega los precios actualizados en la gráfica
     * @param data Los nuevos datos a graficar
     */
    private void repaint(DataServices data){
        logger.log(Level.INFO, "Recargando los precios para la gráfica");
        try {
            data.regenerateHistoricData();
            Graphs regraph = new Graphs(data.generateChartData(),data.generateChartAxis(),data.getSelectedSymbol());
            Grid<DataHistorica> grid = infoGrid(data);
            gridsLayout.replaceComponent(gridsLayout.getComponent(infoGridId),grid);
            layout.replaceComponent(layout.getComponent(graphId),regraph);
            graphId = layout.getComponentIndex(regraph);
            infoGridId = gridsLayout.getComponentIndex(grid);
            lblActualPrice.setValue("Precio Actual: "+data.getActualData().getMaxPrice());
            logger.log(Level.INFO, "Se recargó correctamente");
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Ocurrió un error crítico con la API", ex);
            ex.printStackTrace();
        }
    }
    
    /**
     * Rellena la tabla de precios con precios actualizados
     * @param data Los nuevos precios
     */
    private void updatePrices(DataServices data){
        logger.log(Level.INFO, "Recargando los precios para la tabla");
        try {
            data.regenerateActualPrices();
            lblActualPrice.setValue("Precio Actual: "+data.getActualData().getMaxPrice());
            Grid<ActualPrice> grid = actualPriceGrid(data);
            gridsLayout.replaceComponent(gridsLayout.getComponent(actualPriceGridId),grid);
            actualPriceGridId = gridsLayout.getComponentIndex(grid);
            logger.log(Level.INFO, "Se recargó correctamente");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Hubo un error crítico con la API", e);
            e.printStackTrace();
        }

    }
    
    /**
     * 
     * @param data Los datos a desplegar
     * @return Tabla donde se despliegan los datos históricos
     */
    private Grid<DataHistorica> infoGrid(DataServices data){
        logger.log(Level.INFO, "Cargando datos históricos");
        Grid<DataHistorica> grid = new Grid<>();
        grid.setItems(data.getHistoricData());
        grid.addColumn(DataHistorica::beautyDate).setCaption("Fecha").setExpandRatio(20);
        grid.addColumn(DataHistorica::getOpenPriceBeauty).setCaption("Apertura").setExpandRatio(20);
        grid.addColumn(DataHistorica::getClosePriceBeauty).setCaption("Cierre").setExpandRatio(20);
        grid.addColumn(DataHistorica::getMaxPriceBeauty).setCaption("Maximo").setExpandRatio(20);
        grid.addColumn(DataHistorica::getMinPriceBeauty).setCaption("Minimo").setExpandRatio(20);
        grid.setSizeFull();
        grid.setCaption("Información de "+data.getSelectedSymbol().toString());
        return grid;
    }
    
    /**
     * 
     * @param data Los datos a desplegar
     * @return Tabla donde se despliegan los datos actuales
     */
    private Grid<ActualPrice> actualPriceGrid(DataServices data){
        logger.log(Level.INFO, "Cargando datos actuales");
        Grid<ActualPrice> grid = new Grid<>();
        grid.setItems(data.getActualPrices());
        grid.addColumn(ActualPrice::getSymbol).setSortProperty("Moneda").setCaption("Moneda").setExpandRatio(4);
        grid.addColumn(ActualPrice::currencyFormat).setCaption("Precio").setExpandRatio(6);
        grid.setSizeFull();
        grid.setCaption("Tipos de cambio actuales");
        return grid;
    }

}
