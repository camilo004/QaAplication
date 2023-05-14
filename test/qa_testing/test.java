package qa_testing;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import qa_testing.server.DatabaseServiceImpl;
import qa_testing.shared.modeloAplicativo;
import qa_testing.shared.modeloMetricas;

public class test {

	@Test
	public void testGetAplicativosConId() {
	    // Arrange
	    DatabaseServiceImpl databaseService = new DatabaseServiceImpl();
	    
	    // Act
	    List<modeloAplicativo> aplicativos = databaseService.getAplicativosConId();
	    
	    // Assert
	    assertNotNull(aplicativos);
	    assertTrue(aplicativos.size() > 0); // Verificar que la lista no esté vacía
	    
	    // Verificar que cada aplicativo tenga un ID y un nombre no nulo
	    for (modeloAplicativo aplicativo : aplicativos) {
	        assertNotNull(aplicativo.getId());
	        assertNotNull(aplicativo.getNombre());
	    }
	}
	 
	@Test
	public void testGetVersionsByAplicativo() {
	    // Arrange
	    int aplicativoId = 1; // Reemplaza con el ID de aplicativo válido en tu base de datos
	    DatabaseServiceImpl databaseService = new DatabaseServiceImpl();
	    
	    // Act
	    List<String> versions = databaseService.getVersionsByAplicativo(aplicativoId);
	    
	    // Assert
	    assertNotNull(versions);
	    assertTrue(versions.size() > 0); // Verificar que la lista no esté vacía
	    
	    // Verificar que cada versión no sea nula
	    for (String version : versions) {
	        assertNotNull(version);
	    }
	}
	 
	 @Test
	    public void testGetMetricasByAplicativo() {
	        // Arrange
	        int aplicativoId = 1; // Reemplaza con el ID de aplicativo válido en tu base de datos
	        DatabaseServiceImpl databaseService = new DatabaseServiceImpl();
	        
	        // Act
	        List<modeloMetricas> metricas = databaseService.getMetricasByAplicativo(aplicativoId);
	        
	        // Assert
	        assertNotNull(metricas);
	        
	        // Verificar que cada métrica tenga los datos esperados
	        for (modeloMetricas metrica : metricas) {
	            assertNotNull(metrica);
	            assertNotNull(metrica.getId());
	            assertNotNull(metrica.getVersionId());
	            assertNotNull(metrica.getCicloPrueba());
	            assertNotNull(metrica.getCasosPruebaEjecutados());
	            assertNotNull(metrica.getPorcentajeCasosExitosos());
	            assertNotNull(metrica.getTiempoPromedioEjecucion());
	            assertNotNull(metrica.getErroresEncontrados());
	            assertNotNull(metrica.getTasaFallos());
	        }
	    }

}
