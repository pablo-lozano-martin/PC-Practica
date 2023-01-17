package mensaje;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class Mensaje implements Serializable{
	
	private String origen;
	private String destino;
	
	public abstract String getTipo();

	public Mensaje(String origen, String destino)	//Clase padre de los mensajes. Contiene el origen y el destino del mensaje.
	{
		super();
		this.origen = origen;
		this.destino = destino;
	}
	
	public String getOrigen() {
		return origen;
	}

	public String getDestino() {
		return destino;
	}
}