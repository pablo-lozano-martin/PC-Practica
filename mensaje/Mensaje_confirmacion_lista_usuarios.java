package mensaje;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

// mensaje en el que se env�a la lista de usuarios y se lleva para cada usuario
// la lista de ficheros de los que es propietario
@SuppressWarnings("serial")
public class Mensaje_confirmacion_lista_usuarios extends Mensaje{
	
	// lista de pares usuario-ficheros propios
	private List<Map.Entry<String, List<String>>> lista;

	public List<Map.Entry<String, List<String>>> getLista() {
		return lista;
	}

	public Mensaje_confirmacion_lista_usuarios(String origen, String destino, List<Map.Entry<String, List<String>>> lista) {
		super(origen, destino);
		this.lista = lista;
	}

	public String getTipo() {
		return "solicitud_lista";
	}
}