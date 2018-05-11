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

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MainUI extends UI {
    private int graphId;
    private int infoGridId;
    private int actualPriceGridId;
    private Label lblActualPrice;
    private VerticalLayout layout;
    private HorizontalLayout gridsLayout;
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
        layout.addComponents(graph,new HorizontalLayout(lblActualPrice,btnUpdatePrices),gridsLayout);
        graphId = layout.getComponentIndex(graph);
        infoGridId = gridsLayout.getComponentIndex(infoGrid);
        actualPriceGridId = gridsLayout.getComponentIndex(actualPriceGrid);
        gridsLayout.setExpandRatio(infoGrid,.7f);
        gridsLayout.setExpandRatio(actualPriceGrid,.3f);

        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

    private void repaint(DataServices data){
        try {
            data.regenerateHistoricData();
            Graphs regraph = new Graphs(data.generateChartData(),data.generateChartAxis(),data.getSelectedSymbol());
            Grid<DataHistorica> grid = infoGrid(data);
            gridsLayout.replaceComponent(gridsLayout.getComponent(infoGridId),grid);
            layout.replaceComponent(layout.getComponent(graphId),regraph);
            graphId = layout.getComponentIndex(regraph);
            infoGridId = gridsLayout.getComponentIndex(grid);
        } catch (IOException ex) {
            //Log de error aqui
            ex.printStackTrace();
        }
    }
    private void updatePrices(DataServices data){
        try {
            data.regenerateActualPrices();
            lblActualPrice.setValue("Precio Actual: "+data.getActualData().getMaxPrice());
            Grid<ActualPrice> grid = actualPriceGrid(data);
            gridsLayout.replaceComponent(gridsLayout.getComponent(actualPriceGridId),grid);
            actualPriceGridId = gridsLayout.getComponentIndex(grid);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private Grid<DataHistorica> infoGrid(DataServices data){
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
    private Grid<ActualPrice> actualPriceGrid(DataServices data){
        Grid<ActualPrice> grid = new Grid<>();
        grid.setItems(data.getActualPrices());
        grid.addColumn(ActualPrice::getSymbol).setSortProperty("Moneda").setCaption("Moneda").setExpandRatio(4);
        grid.addColumn(ActualPrice::currencyFormat).setCaption("Precio").setExpandRatio(6);
        grid.setSizeFull();
        grid.setCaption("Tipos de cambio actuales");
        return grid;
    }

}
