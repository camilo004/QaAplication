package qa_testing.shared;


import java.io.Serializable;

import qa_testing.shared.modeloVersion;

public class modeloMetricas implements Serializable   {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private modeloVersion versionId;
    private String cicloPrueba;
    private int casosPruebaEjecutados;
    private float porcentajeCasosExitosos;
    private float tiempoPromedioEjecucion;
    private int erroresEncontrados;
    private float tasaFallos;

	public modeloMetricas() {
	}

	public modeloMetricas(int id, modeloVersion versionId, String cicloPrueba, int casosPruebaEjecutados,
			float porcentajeCasosExitosos, float tiempoPromedioEjecucion, int erroresEncontrados, float tasaFallos) {
		super();
		this.id = id;
		this.versionId = versionId;
		this.cicloPrueba = cicloPrueba;
		this.casosPruebaEjecutados = casosPruebaEjecutados;
		this.porcentajeCasosExitosos = porcentajeCasosExitosos;
		this.tiempoPromedioEjecucion = tiempoPromedioEjecucion;
		this.erroresEncontrados = erroresEncontrados;
		this.tasaFallos = tasaFallos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public modeloVersion getVersionId() {
		return versionId;
	}

	public void setVersionId(modeloVersion versionId) {
		this.versionId = versionId;
	}

	public String getCicloPrueba() {
		return cicloPrueba;
	}

	public void setCicloPrueba(String cicloPrueba) {
		this.cicloPrueba = cicloPrueba;
	}

	public int getCasosPruebaEjecutados() {
		return casosPruebaEjecutados;
	}

	public void setCasosPruebaEjecutados(int casosPruebaEjecutados) {
		this.casosPruebaEjecutados = casosPruebaEjecutados;
	}

	public float getPorcentajeCasosExitosos() {
		return porcentajeCasosExitosos;
	}

	public void setPorcentajeCasosExitosos(float porcentajeCasosExitosos) {
		this.porcentajeCasosExitosos = porcentajeCasosExitosos;
	}

	public float getTiempoPromedioEjecucion() {
		return tiempoPromedioEjecucion;
	}

	public void setTiempoPromedioEjecucion(float tiempoPromedioEjecucion) {
		this.tiempoPromedioEjecucion = tiempoPromedioEjecucion;
	}

	public int getErroresEncontrados() {
		return erroresEncontrados;
	}

	public void setErroresEncontrados(int erroresEncontrados) {
		this.erroresEncontrados = erroresEncontrados;
	}

	public float getTasaFallos() {
		return tasaFallos;
	}

	public void setTasaFallos(float tasaFallos) {
		this.tasaFallos = tasaFallos;
	}
	
	

	

}
