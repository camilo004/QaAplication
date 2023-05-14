package qa_testing.client;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import qa_testing.shared.modeloAplicativo;
import qa_testing.shared.modeloMetricas;
import qa_testing.shared.modeloVersion;

public class MetricasAplicativo extends Window {
	private final DatabaseServiceAsync databaseService = GWT.create(DatabaseService.class);

    private ListGrid metricasGrid;
    private ToolStripButton refreshButton;
    private String selectedAplicativoId;

    public MetricasAplicativo() {
        setTitle("Métricas de Aplicativo");
        setWidth(800);
        setHeight(400);
        setCanDragResize(true);
        centerInPage();
        show();

        VLayout layout = new VLayout();
        layout.setWidth100();
        layout.setHeight100();

        createToolbar();
        createMetricasGrid();

        layout.addMember(refreshButton);
        layout.addMember(metricasGrid);

        addItem(layout);
    }

    private void createToolbar() {

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
		        aplicativoItem.addChangedHandler(event -> {
		            selectedAplicativoId = aplicativoItem.getValueAsString();
		        });
		    }

		    @Override
		    public void onFailure(Throwable caught) {
		        // Manejar el error en caso de que ocurra
		        caught.printStackTrace(); // Imprimir el error en la consola
		        // Puedes mostrar un mensaje de error en la interfaz de usuario si lo deseas
		    }
		});
        ToolStrip toolbar = new ToolStrip();
        toolbar.setWidth100();
        toolbar.setHeight(30);

        refreshButton = new ToolStripButton();
        refreshButton.setTitle("Actualizar");
        refreshButton.addClickHandler(event -> {
            refreshMetricas();
        });

        toolbar.addButton(refreshButton);
        toolbar.addFormItem(aplicativoItem);
        addMember(toolbar);
    }

    private void createMetricasGrid() {
        metricasGrid = new ListGrid();
        metricasGrid.setWidth100();
        metricasGrid.setHeight100();
        metricasGrid.setShowAllRecords(true);
        metricasGrid.setCanEdit(false);

        ListGridField versionField = new ListGridField("version", "Versión");
        ListGridField cicloPruebaField = new ListGridField("cicloPrueba", "Ciclo de Prueba");
        ListGridField casosPruebaField = new ListGridField("casosPrueba", "Casos de Prueba");
        ListGridField porcentajeExitososField = new ListGridField("porcentajeExitosos", "Porcentaje de Casos Exitosos");
        ListGridField erroresField = new ListGridField("errores", "Errores Encontrados");
        ListGridField tiempoPromedioField = new ListGridField("tiempoPromedio", "Tiempo Promedio de Ejecución");
        ListGridField tasaFallosField = new ListGridField("tasaFallos", "Tasa de Fallos");

        metricasGrid.setFields(versionField, cicloPruebaField, casosPruebaField, porcentajeExitososField, erroresField,
                tiempoPromedioField, tasaFallosField);

        addItem(metricasGrid);
    }

    private void refreshMetricas() {
    	 String selectedValue =	selectedAplicativoId;
    	 String[] parts = selectedValue.split(":");
         int aplicativoId = Integer.parseInt(parts[0].trim());
        
        
        // Llamar al servicio para obtener las métricas del aplicativo
        DatabaseServiceAsync metricasService = GWT.create(DatabaseService.class);
        metricasService.getMetricasByAplicativo(aplicativoId, new AsyncCallback<List<modeloMetricas>>() {
            @Override
            public void onSuccess(List<modeloMetricas> result) {
            	metricasGrid.setData(convertToRecordArray(result));
            }

            @Override
            public void onFailure(Throwable caught) {
                SC.warn("Error al obtener las métricas del aplicativo: " + caught.getMessage());
            }
        });
    }

    private ListGridRecord[] convertToRecordArray(List<modeloMetricas> metricas) {
        ListGridRecord[] records = new ListGridRecord[metricas.size()];

        for (int i = 0; i < metricas.size(); i++) {
            modeloMetricas metrica = metricas.get(i);
            ListGridRecord record = new ListGridRecord();
            modeloVersion version = metrica.getVersionId();
            int versionId = version.getId();
          
            record.setAttribute("version", versionId);
            record.setAttribute("cicloPrueba", metrica.getCicloPrueba());
            record.setAttribute("casosPrueba", metrica.getCasosPruebaEjecutados());
            record.setAttribute("porcentajeExitosos", metrica.getPorcentajeCasosExitosos());
            record.setAttribute("errores", metrica.getErroresEncontrados());
            record.setAttribute("tiempoPromedio", metrica.getTiempoPromedioEjecucion());
            record.setAttribute("tasaFallos", metrica.getTasaFallos());

            records[i] = record;
        }

        return records;
    }
}
            
