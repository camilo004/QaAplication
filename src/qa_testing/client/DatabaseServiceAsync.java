package qa_testing.client;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import qa_testing.shared.modeloAplicativo;
import qa_testing.shared.modeloMetricas;
public interface DatabaseServiceAsync {
    void insertData(String aplicativo, AsyncCallback<Boolean> callback);
    void insertarVersion(int  aplicativoId, String version, AsyncCallback<Boolean> callback);
    void getAplicativosConId(AsyncCallback<List<modeloAplicativo>> callback);
    void getVersionsByAplicativo(int aplicativoId, AsyncCallback<List<String>> callback);
    void insertVersionAndTestCases(int versionId, String cicloPrueba, int casosPrueba, float porcentajeExitosos, int errores, float tiempoPromedio, float tasaFallos, AsyncCallback<Boolean> callback);
    void getMetricasByAplicativo(int aplicativoId, AsyncCallback<List<modeloMetricas>> callback);




}
