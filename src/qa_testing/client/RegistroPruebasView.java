package qa_testing.client;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Encoding;
import com.smartgwt.client.types.FormMethod;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;

import qa_testing.shared.modeloAplicativo;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

public class RegistroPruebasView extends Window {
	private final DatabaseServiceAsync databaseService = GWT.create(DatabaseService.class);


	Window aplicativoWindow;
	private Window versionWindow;
	private Window metricasWindow;
	private Button versionButton;
	private Button metricasButton;
	private Button guardarButton;

	public RegistroPruebasView() {

		createAplicativoWindow();
		createVersionWindow();
		createMetricasWindow();
		addItemsToWindows();

	}

	public void createAplicativoWindow() {
		aplicativoWindow = new Window();
		aplicativoWindow.setTitle("Registro de Aplicativo");
		aplicativoWindow.setWidth(400);
		aplicativoWindow.setHeight(200);
		aplicativoWindow.setIsModal(true);
		aplicativoWindow.setShowModalMask(true);
		aplicativoWindow.centerInPage();
		aplicativoWindow.setCanDragReposition(false);
		aplicativoWindow.setCanDragResize(false);
		aplicativoWindow.show();


		DynamicForm aplicativoForm = new DynamicForm();
		aplicativoForm.setWidth100();
		aplicativoForm.setHeight100();
		aplicativoForm.setPadding(10);
		aplicativoForm.setNumCols(2);
		aplicativoForm.setEncoding(Encoding.MULTIPART);
		aplicativoForm.setMethod(FormMethod.POST);
		

		TextItem aplicativoItem = new TextItem("aplicativo", "Aplicativo");
		aplicativoItem.setRequired(true);
		Button saveButton = new Button("Guardar");
		
		saveButton.addClickHandler(new ClickHandler() {
		    public void onClick(ClickEvent event) {
		        String aplicativo = aplicativoItem.getValueAsString();
		        databaseService.insertData(aplicativo, new AsyncCallback<Boolean>() {
		            @Override
		            public void onSuccess(Boolean result) {
		                aplicativoItem.clearValue();
		            }

		            @Override
		            public void onFailure(Throwable caught) {
		                // Manejar el error aquí
		            }
		        });
		    }
		});
			
		VLayout container = new VLayout();
		container.addMember(aplicativoForm);
		container.addMember(saveButton);
		aplicativoForm.setFields(aplicativoItem);
		aplicativoWindow.addItem(aplicativoForm);
		aplicativoWindow.addItem(container);

	}

	private void createVersionWindow() {
		versionWindow = new Window();
		versionWindow.setTitle("Registro de Versión");
		versionWindow.setWidth(400);
		versionWindow.setHeight(200);
		versionWindow.setIsModal(true);
		versionWindow.setShowModalMask(true);
		versionWindow.setCanDragReposition(false);
		versionWindow.setCanDragResize(false);
		versionWindow.centerInPage();
		versionWindow.hide();

		DynamicForm versionForm = new DynamicForm();
		versionForm.setWidth100();
		versionForm.setHeight100();
		versionForm.setPadding(10);
		versionForm.setNumCols(2);

		ComboBoxItem aplicativoItem = new ComboBoxItem("aplicativo", "Aplicativo");
		aplicativoItem.setRequired(true);
		databaseService.getAplicativosConId(new AsyncCallback<List<modeloAplicativo>>() {
		    @Override
		    public void onSuccess(List<modeloAplicativo> aplicativos) {
		        // Crear una lista de opciones para el ComboBoxItem
		        List<String> opciones = new ArrayList<>();
		        
		        // Iterar sobre los aplicativos y agregarlos a la lista de opciones
		        for (modeloAplicativo aplicativo : aplicativos) {
		            int id = aplicativo.getId();
		            String nombre = aplicativo.getNombre();
		            String opcion = id + ": " + nombre; // Personaliza el formato de la opción según tus necesidades
		            opciones.add(opcion);
		        }

		        // Establecer las opciones del ComboBoxItem
		        aplicativoItem.setValueMap(opciones.toArray(new String[0]));
		    }

		    @Override
		    public void onFailure(Throwable caught) {
		        // Manejar el error en caso de que ocurra
		        caught.printStackTrace(); // Imprimir el error en la consola
		        // Puedes mostrar un mensaje de error en la interfaz de usuario si lo deseas
		    }
		});
		TextItem versionItem = new TextItem("version", "Versión");
		versionItem.setRequired(true);
		Button enviarButton = new Button("Guardar");
		enviarButton.addClickHandler(new ClickHandler() {
		    @Override
		    public void onClick(ClickEvent event) {
		        // Obtener el valor seleccionado del ComboBox
		        String selectedValue = aplicativoItem.getValueAsString();

		        // Validar que se haya seleccionado un valor
		        if (selectedValue != null && !selectedValue.isEmpty()) {
		            // Separar el ID del valor seleccionado
		            String[] parts = selectedValue.split(":");
		            int aplicativoId = Integer.parseInt(parts[0].trim());

		            com.google.gwt.core.client.GWT.log("ID del aplicativo: " + aplicativoId);

		            // Obtener la versión ingresada
		            String version = versionItem.getValueAsString();

		            // Validar que la versión no esté vacía
		            if (version != null && !version.isEmpty()) {
		                // Llamada al método insertarVersion del servicio
		                databaseService.insertarVersion(aplicativoId, version, new AsyncCallback<Boolean>() {
		                    @Override
		                    public void onSuccess(Boolean result) {
		                        if (result) {
		                            // La inserción fue exitosa, realizar alguna acción adicional si es necesario
		                            SC.say("Versión insertada correctamente.");
		                            versionItem.clearValue();
		                            
		                        } else {
		                            // La inserción falló, mostrar un mensaje de error
		                            SC.warn("Error al insertar la versión.");
		                        }
		                    }

		                    @Override
		                    public void onFailure(Throwable caught) {
		                        caught.printStackTrace(); // Imprimir el error en la consola
		                        // Mostrar un mensaje de error en la interfaz de usuario
		                        SC.warn("Error al insertar la versión: " + caught.getMessage());
		                    }
		                });
		            } else {
		                // Mostrar un mensaje de error si la versión no está completa
		                SC.warn("Por favor, ingrese una versión válida.");
		            }
		        } else {
		            // Mostrar un mensaje de error si no se ha seleccionado ningún valor
		            SC.warn("Por favor, seleccione un aplicativo.");
		        }
		    }
		});
		
		VLayout layout = new VLayout();
		layout.addMember(versionForm);
		layout.addMember(enviarButton);


		versionForm.setFields(aplicativoItem, versionItem);
		versionWindow.addItem(versionForm);
		versionWindow.addItem(layout);
	}

	private void createMetricasWindow() {
		metricasWindow = new Window();
		metricasWindow.setTitle("Registro de Métricas");
		metricasWindow.setWidth(400);
		metricasWindow.setHeight(400);
		metricasWindow.setIsModal(true);
		metricasWindow.setShowModalMask(true);
		metricasWindow.setCanDragReposition(false);
		metricasWindow.setCanDragResize(false);
		metricasWindow.centerInPage();
		metricasWindow.hide();

		DynamicForm metricasForm = new DynamicForm();
		metricasForm.setWidth100();
		metricasForm.setHeight100();
		metricasForm.setPadding(10);
		metricasForm.setNumCols(3);

		// Crear el ComboBoxItem para el aplicativo
		ComboBoxItem aplicativoItem = new ComboBoxItem("aplicativo", "Aplicativo");
		aplicativoItem.setRequired(true);

		// Obtener los aplicativos desde la base de datos y cargarlos en el ComboBoxItem
		databaseService.getAplicativosConId(new AsyncCallback<List<modeloAplicativo>>() {
		    @Override
		    public void onSuccess(List<modeloAplicativo> aplicativos) {
		        // Crear una lista de opciones para el ComboBoxItem
		        List<String> opciones = new ArrayList<>();
		        
		        // Iterar sobre los aplicativos y agregarlos a la lista de opciones
		        for (modeloAplicativo aplicativo : aplicativos) {
		            int id = aplicativo.getId();
		            String nombre = aplicativo.getNombre();
		            String opcion = id + ": " + nombre; // Personaliza el formato de la opción según tus necesidades
		            opciones.add(opcion);
		        }

		        // Establecer las opciones del ComboBoxItem
		        aplicativoItem.setValueMap(opciones.toArray(new String[0]));
		    }

		    @Override
		    public void onFailure(Throwable caught) {
		        // Manejar el error en caso de que ocurra
		        caught.printStackTrace(); // Imprimir el error en la consola
		        // Puedes mostrar un mensaje de error en la interfaz de usuario si lo deseas
		    }
		});

		// Crear el ComboBoxItem para las versiones
		ComboBoxItem versionItem = new ComboBoxItem("version", "Versión");
		versionItem.setRequired(true);



		// Agregar el evento de cambio al ComboBoxItem del aplicativo
		aplicativoItem.addChangedHandler(new ChangedHandler() {
		    @Override
		    public void onChanged(ChangedEvent event) {
		        // Obtener el ID del aplicativo seleccionado
		        String selectedValue = aplicativoItem.getValueAsString();
		        
		        // Validar que se haya seleccionado un aplicativo
		        if (selectedValue != null && !selectedValue.isEmpty()) {
		        	 String[] parts = selectedValue.split(":");
		        	 int aplicativoId = Integer.parseInt(parts[0].trim());

		            // Obtener las versiones del aplicativo desde la base de datos
		            databaseService.getVersionsByAplicativo(aplicativoId, new AsyncCallback<List<String>>() {
		                @Override
		                public void onSuccess(List<String> versiones) {
		                    // Establecer las versiones del ComboBoxItem
		                    versionItem.setValueMap(versiones.toArray(new String[0]));
		                }

		                @Override
		                public void onFailure(Throwable caught) {
		                    // Manejar el error en caso de que ocurra
		                    caught.printStackTrace(); // Imprimir el error en la consola
		                    // Puedes mostrar un mensaje de error en la interfaz de usuario si lo deseas
		                }
		            });
		        } else {
		            // Limpiar las opciones del ComboBoxItem de versiones si no se ha seleccionado un aplicativo
		            versionItem.clearValue();
		        }
		    }
		});
		
		



		TextItem ciclosItem = new IntegerItem("Ciclos", "Ciclo");
		ciclosItem.setRequired(true);
		
		IntegerItem casosPruebaItem = new IntegerItem("casosPrueba", "Casos de Prueba");
		casosPruebaItem.setRequired(true);

		FloatItem porcentajeExitososItem = new FloatItem("porcentajeExitosos", "Porcentaje de Casos Exitosos");
		porcentajeExitososItem.setRequired(true);

		IntegerItem erroresItem = new IntegerItem("errores", "Errores Encontrados");
		erroresItem.setRequired(true);

		FloatItem tiempoPromedioItem = new FloatItem("tiempoPromedio", "Tiempo Promedio de Ejecución");
		tiempoPromedioItem.setRequired(true);

		FloatItem tasaFallosItem = new FloatItem("tasaFallos", "Tasa de Fallos");
		tasaFallosItem.setRequired(true);
		
		Button guardarButton = new Button("Guardar");
		guardarButton.addClickHandler(new ClickHandler() {
		    @Override
		    public void onClick(ClickEvent event) {
		        // Obtener los valores de los campos
		        int versionId = Integer.parseInt(versionItem.getValueAsString());
		        String cicloPrueba = ciclosItem.getValueAsString();
		        int casosPrueba = Integer.parseInt(casosPruebaItem.getValueAsString());
		        float porcentajeExitosos = Float.parseFloat(porcentajeExitososItem.getValueAsString());
		        int errores = Integer.parseInt(erroresItem.getValueAsString());
		        float tiempoPromedio = Float.parseFloat(tiempoPromedioItem.getValueAsString());
		        float tasaFallos = Float.parseFloat(tasaFallosItem.getValueAsString());

		        // Insertar los datos en la base de datos
		        databaseService.insertVersionAndTestCases(versionId, cicloPrueba, casosPrueba, porcentajeExitosos, errores, tiempoPromedio, tasaFallos, new AsyncCallback<Boolean>() {
		            @Override
		            public void onSuccess(Boolean result) {
		                // Manejar la respuesta exitosa aquí
		                // Por ejemplo, mostrar un mensaje de éxito
		            	SC.say("Datos Almacenados");
		                com.google.gwt.user.client.Window.Location.reload();

		            	
		            }

		            @Override
		            public void onFailure(Throwable caught) {
		                // Manejar el error aquí
		                // Por ejemplo, mostrar un mensaje de error
		            }
		        });
		    }
		});
		VLayout layout = new VLayout();
		layout.addMember(metricasForm);
		layout.addMember(guardarButton);

		metricasForm.setFields( aplicativoItem ,versionItem,ciclosItem ,casosPruebaItem, porcentajeExitososItem, erroresItem, tiempoPromedioItem,
				tasaFallosItem);

		metricasWindow.addItem(metricasForm);
		metricasWindow.addItem(layout);
	
	}



	private void addItemsToWindows() {
	
		versionButton = new Button("Siguiente");
		versionButton.setWidth(100);
		versionButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				aplicativoWindow.hide();
				versionWindow.show();
			}
		});
		aplicativoWindow.addItem(versionButton);

		metricasButton = new Button("Siguiente");
		metricasButton.setWidth(100);
		metricasButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				versionWindow.hide();
				metricasWindow.show();
			}
		});
		versionWindow.addItem(metricasButton);

		guardarButton.setWidth(100);

		VLayout mainLayout = new VLayout();
		mainLayout.setWidth100();
		mainLayout.setHeight100();
		mainLayout.setAlign(Alignment.CENTER);
		mainLayout.setDefaultLayoutAlign(Alignment.CENTER);
		mainLayout.setMembersMargin(10);
		mainLayout.addMember(aplicativoWindow);
		mainLayout.addMember(versionWindow);
		mainLayout.addMember(metricasWindow);

		mainLayout.draw();

	}


}
