package mx.itam.Frontend;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;

/**
 * Clase para armar la Navigation Bar de la página web
 * @author BitStats
 */
public class NavigationBar extends HorizontalLayout {
    
    /**
     * Método constructor. Crea la navigation bar de la página con el nombre, slogan y logo
     */
    public NavigationBar() {
	//Se instancian los compenentes visuales de la página asi como los textos que contienen.
        Label lblTitle = new Label("BitStats");
        Label lblSlogan = new Label("Donde las Criptomonedas están");
        ThemeResource resource = new ThemeResource("img/BitStats.png");
	//Se asignan las especificaciones del tamaño de las imagenes.
        Image image = new Image("",resource);
        image.setWidth(130, Unit.PIXELS);
        image.setHeight(30, Unit.PIXELS);
	//Se añaden los componentes para ser visualizados en el resultado final.
        addComponents(image,lblSlogan);
        this.setComponentAlignment(image,Alignment.BOTTOM_LEFT);
        this.setComponentAlignment(lblSlogan, Alignment.BOTTOM_RIGHT);
        this.setStyleName("navbar-bitstats");
        this.setSizeFull();
    }
}
