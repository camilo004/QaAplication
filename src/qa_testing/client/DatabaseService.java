package qa_testing.client;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import qa_testing.shared.modeloAplicativo;
import qa_testing.shared.modeloMetricas;
@RemoteServiceRelativePath("DatabaseService")
public interface  DatabaseService  extends RemoteService {
	boolean insertData(String aplicativo);
    boolean  insertarVersion(int  aplicativoId, String version);
	List<modeloAplicativo> getAplicativosConId();
	List<String> getVersionsByAplicativo(int aplicativoId);
	boolean insertVersionAndTestCases(int versionId, String cicloPrueba, int casosPrueba, float porcentajeExitosos,
			int errores, float tiempoPromedio, float tasaFallos);
	List<modeloMetricas> getMetricasByAplicativo(int aplicativoId);
	


}
