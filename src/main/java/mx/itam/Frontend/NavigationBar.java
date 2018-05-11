package mx.itam.Frontend;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class NavigationBar extends HorizontalLayout {
    public NavigationBar() {
        Label lblTitle = new Label("BitStats");
        Label lblSlogan = new Label("Donde las Criptomonedas están");
        addComponents(lblTitle,lblSlogan);
        this.setComponentAlignment(lblTitle,Alignment.BOTTOM_LEFT);
        this.setComponentAlignment(lblSlogan, Alignment.BOTTOM_RIGHT);
        this.setStyleName("navbar-bitstats");
        this.setSizeFull();
    }
}
