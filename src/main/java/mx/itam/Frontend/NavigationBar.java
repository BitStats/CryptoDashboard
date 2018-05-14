package mx.itam.Frontend;

import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;

import java.io.File;

public class NavigationBar extends HorizontalLayout {
    public NavigationBar() {
        Label lblTitle = new Label("BitStats");
        Label lblSlogan = new Label("Donde las Criptomonedas están");
        ThemeResource resource = new ThemeResource("img/BitStats.png");
        Image image = new Image("",resource);
        image.setWidth(130, Unit.PIXELS);
        image.setHeight(30, Unit.PIXELS);
        addComponents(image,lblSlogan);
        this.setComponentAlignment(image,Alignment.BOTTOM_LEFT);
        this.setComponentAlignment(lblSlogan, Alignment.BOTTOM_RIGHT);
        this.setStyleName("navbar-bitstats");
        this.setSizeFull();
    }
}
