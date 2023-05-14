package qa_testing.shared;

import java.io.Serializable;

import qa_testing.shared.modeloAplicativo;

public class modeloVersion implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private modeloAplicativo aplicativoId;
	private String Version;

	public modeloVersion() {
	}

	public modeloVersion(int id, modeloAplicativo aplicativoId, String version) {
		super();
		this.id = id;
		this.aplicativoId = aplicativoId;
		Version = version;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public modeloAplicativo getAplicativoId() {
		return aplicativoId;
	}

	public void setAplicativoId(modeloAplicativo aplicativoId) {
		this.aplicativoId = aplicativoId;
	}

	public String getVersion() {
		return Version;
	}

	public void setVersion(String version) {
		Version = version;
	}

}
