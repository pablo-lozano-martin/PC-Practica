package mensaje;

import java.io.Serializable;

// se codifican los mensajes que se van a enviar
@SuppressWarnings("serial")
public abstract class Mensaje implements Serializable{
	
	// de d�nde viene el mensaje
	private String origen;
	
	// a d�nde se dirige
	private String destino;
	
	public abstract String getTipo();

	public String getOrigen() {
		return origen;
	}

	public String getDestino() {
		return destino;
	}

	public Mensaje(String origen, String destino) {
		super();
		this.origen = origen;
		this.destino = destino;
	}
}