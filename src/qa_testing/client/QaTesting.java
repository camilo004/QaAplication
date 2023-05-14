package qa_testing.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class QaTesting implements EntryPoint {


	public void onModuleLoad() {
		final Window mainWindow = new Window();
		mainWindow.setTitle("Quality Assurance App");
		mainWindow.setWidth(400);
		mainWindow.setHeight(400);
		mainWindow.setShowMinimizeButton(false);
		mainWindow.setIsModal(true);
		mainWindow.setShowModalMask(true);
		mainWindow.setCanDragReposition(false);
		mainWindow.setCanDragResize(false);
		mainWindow.setOverflow(Overflow.HIDDEN);
		mainWindow.centerInPage(); // Centra la ventana en la página
		// Create a label for the welcome message
		Label welcomeLabel = new Label();
		welcomeLabel.setContents("<b>Welcome to the Quality Assurance App!</b>");
		welcomeLabel.setAlign(Alignment.CENTER);
		welcomeLabel.setWidth100();
		welcomeLabel.setStyleName("welcomeLabel");
		welcomeLabel.setMargin(10);

		// Create a start button with an icon
		Button registrarButton = new Button("Registrar Métricas");
		registrarButton.setWidth(200);
		registrarButton.setHeight(40);
		registrarButton.setMargin(10);
		registrarButton.setAlign(Alignment.CENTER);
		registrarButton.setShowRollOver(true);
		registrarButton.setShowHover(true);
		registrarButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				mainWindow.hide();
				// Crear una instancia de la clase RegistroPruebasView
				RegistroPruebasView registroPruebasView = new RegistroPruebasView();
				RootPanel.get().add(registroPruebasView);
				 registroPruebasView.show();


			}
		});

		Button resumenButton = new Button("Resumen de Métricas");
		resumenButton.setWidth(200);
		resumenButton.setHeight(40);
		resumenButton.setMargin(10);
		resumenButton.setAlign(Alignment.CENTER);
		resumenButton.setShowRollOver(true);
		resumenButton.setShowHover(true);
		resumenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				mainWindow.hide();
				MetricasAplicativo metricas = new MetricasAplicativo();
				RootPanel.get().add(metricas);
				metricas.show();

			}
		});
		// Create a layout to hold the welcome label and buttons
		VLayout contentLayout = new VLayout();
		contentLayout.setWidth100();
		contentLayout.setHeight100();
		contentLayout.setAlign(Alignment.CENTER);
		contentLayout.setDefaultLayoutAlign(Alignment.CENTER);
		contentLayout.setMembersMargin(20);
		contentLayout.addMembers(welcomeLabel, registrarButton, resumenButton);

		// Set the layout as the main layout of the window
		mainWindow.addItem(contentLayout);

		// Show the main window
		mainWindow.show();
	}

}