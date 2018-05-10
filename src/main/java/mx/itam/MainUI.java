package mx.itam;

import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MainUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        DataServices data = new DataServices();
        Grid<DataHistorica> grid = new Grid<>();
        grid.setItems(data.getData_interval());
        grid.addColumn(DataHistorica::getDate).setCaption("Fecha");
        grid.addColumn(DataHistorica::getOpenPrice).setCaption("Apertura");
        grid.addColumn(DataHistorica::getClosePrice).setCaption("Cierre");
        grid.addColumn(DataHistorica::getMaxPrice).setCaption("Maximo");
        grid.addColumn(DataHistorica::getMinPrice).setCaption("Minimo");
        grid.setWidth("100%");
        layout.addComponents(grid);

        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
