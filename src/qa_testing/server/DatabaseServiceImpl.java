package qa_testing.server;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import qa_testing.client.DatabaseService;
import qa_testing.shared.modeloAplicativo;
import qa_testing.shared.modeloMetricas;
import qa_testing.shared.modeloVersion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;


public class DatabaseServiceImpl extends RemoteServiceServlet implements DatabaseService  {
    private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private static final String DB_URL = "jdbc:sqlserver://ELBICHOSIU\\SQLEXPRESS:1433;databaseName=qatest;integratedSecurity=true;TrustServerCertificate=True";
	public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
	
	
	
	@Override
    public boolean insertData(String aplicativo) {
        try {
            Connection conn = DatabaseServiceImpl.getConnection();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO Aplicativo (Nombre) VALUES (?)");
            statement.setString(1, aplicativo);
            int rowsInserted = statement.executeUpdate();
            conn.close();
            return rowsInserted > 0;
        } catch (SQLException e) {
            return false;
        }
    }
	
	

	// En tu servicio
	@Override
	public List<modeloAplicativo> getAplicativosConId() {
	    List<modeloAplicativo> aplicativos = new ArrayList<>();

	    // Obtener los nombres y IDs de aplicativos desde la base de datos
	    try {
	        Connection conn = getConnection();
	        Statement statement = conn.createStatement();
	        ResultSet resultSet = statement.executeQuery("SELECT Id, Nombre FROM Aplicativo");

	        while (resultSet.next()) {
	            int idAplicativo = resultSet.getInt("Id");
	            String nombreAplicativo = resultSet.getString("Nombre");

	            modeloAplicativo aplicativo = new modeloAplicativo(idAplicativo, nombreAplicativo);
	            aplicativos.add(aplicativo);
	        }

	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return aplicativos;
	}

	
	
	@Override
	public boolean insertarVersion(int  aplicativoId, String version) {
	    try {
	        Connection conn = getConnection();
	        PreparedStatement statement = conn.prepareStatement("INSERT INTO Version (AplicativoId, NumeroVersion) VALUES (?, ?)");
	        statement.setInt(1, aplicativoId);
	        statement.setString(2, version);
	        int rowsAffected = statement.executeUpdate();
	        conn.close();

	        // Si se insertó al menos una fila, se considera que la inserción fue exitosa
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	

	@Override
	public List<String> getVersionsByAplicativo(int aplicativoId) {
	    List<String> versions = new ArrayList<>();

	    // Obtener las versiones del aplicativo desde la base de datos
	    try {
	        Connection conn = getConnection();
	        PreparedStatement statement = conn.prepareStatement("SELECT id FROM Version WHERE aplicativoId = ?");
	        statement.setInt(1, aplicativoId);
	        ResultSet resultSet = statement.executeQuery();

	        while (resultSet.next()) {
	            String version = resultSet.getString("id");
	            versions.add(version);
	        }

	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return versions;
	}
	
	public boolean insertVersionAndTestCases(int versionId, String cicloPrueba, int casosPrueba, float porcentajeExitosos, int errores, float tiempoPromedio, float tasaFallos) {
	    try {
	        Connection conn = getConnection();
	        PreparedStatement statement = conn.prepareStatement("INSERT INTO ResultadosPrueba (VersionId, CicloPrueba, CasosPruebaEjecutados, PorcentajeCasosExitosos, TiempoPromedioEjecucion, ErroresEncontrados, TasaFallos) VALUES (?, ?, ?, ?, ?, ?, ?)");
	        statement.setInt(1, versionId);
	        statement.setString(2, cicloPrueba);
	        statement.setInt(3, casosPrueba);
	        statement.setFloat(4, porcentajeExitosos);
	        statement.setInt(5, errores);
	        statement.setFloat(6, tiempoPromedio);
	        statement.setFloat(7, tasaFallos);
	        int rowsAffected = statement.executeUpdate();
	        conn.close();
	        return rowsAffected > 0;
	       
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	@Override
	public List<modeloMetricas> getMetricasByAplicativo(int aplicativoId) {
	    List<modeloMetricas> metricas = new ArrayList<>();

	    try {
	        Connection conn = getConnection();
	        PreparedStatement statement = conn.prepareStatement("SELECT * FROM ResultadosPrueba rp INNER JOIN Version v ON rp.versionId = v.id WHERE v.aplicativoId = ?");
	        statement.setInt(1, aplicativoId);
	        ResultSet resultSet = statement.executeQuery();

	        while (resultSet.next()) {
	            int id = resultSet.getInt("id");
	            int versionId = resultSet.getInt("versionId");
	            String cicloPrueba = resultSet.getString("CicloPrueba");
	            int casosPrueba = resultSet.getInt("CasosPruebaEjecutados");
	            float porcentajeExitosos = resultSet.getFloat("PorcentajeCasosExitosos");
	            float tiempoPromedio = resultSet.getFloat("TiempoPromedioEjecucion");
	            int errores = resultSet.getInt("ErroresEncontrados");
	            float tasaFallos = resultSet.getFloat("TasaFallos");

	            modeloVersion version = new modeloVersion();
	            version.setId(versionId);

	            modeloMetricas metrica = new modeloMetricas();
	            metrica.setId(id);
	            metrica.setVersionId(version);
	            metrica.setCicloPrueba(cicloPrueba);
	            metrica.setCasosPruebaEjecutados(casosPrueba);
	            metrica.setPorcentajeCasosExitosos(porcentajeExitosos);
	            metrica.setTiempoPromedioEjecucion(tiempoPromedio);
	            metrica.setErroresEncontrados(errores);
	            metrica.setTasaFallos(tasaFallos);

	            metricas.add(metrica);
	        }

	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return metricas;
	}
	public static void main(String[] args) {
	    // Crea una instancia de tu clase de base de datos
	    DatabaseServiceImpl databaseService = new DatabaseServiceImpl();

	    // Define el ID del aplicativo para el cual deseas obtener las métricas
	    int aplicativoId = 2; // Reemplaza con el ID de tu aplicativo

	    // Llama al método getMetricasByAplicativo para obtener la lista de métricas
	    List<modeloMetricas> metricas = databaseService.getMetricasByAplicativo(aplicativoId);

	    // Imprime las métricas obtenidas
	    for (modeloMetricas metrica : metricas) {
	        System.out.println("ID: " + metrica.getId());
	        System.out.println("Version ID: " + metrica.getVersionId().getId());
	        System.out.println("Ciclo de Prueba: " + metrica.getCicloPrueba());
	        System.out.println("Casos de Prueba Ejecutados: " + metrica.getCasosPruebaEjecutados());
	        System.out.println("Porcentaje de Casos Exitosos: " + metrica.getPorcentajeCasosExitosos());
	        System.out.println("Tiempo Promedio de Ejecución: " + metrica.getTiempoPromedioEjecucion());
	        System.out.println("Errores Encontrados: " + metrica.getErroresEncontrados());
	        System.out.println("Tasa de Fallos: " + metrica.getTasaFallos());
	        System.out.println("----------------------");
	    }
	}



}
