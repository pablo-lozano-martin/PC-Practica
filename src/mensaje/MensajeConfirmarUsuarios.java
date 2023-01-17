package mensaje;

import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class MensajeConfirmarUsuarios extends Mensaje{
	
	private List<Map.Entry<String, List<String>>> _l;

	public MensajeConfirmarUsuarios(String origen, String destino, List<Map.Entry<String, List<String>>> l)
	{
		super(origen, destino);
		_l = l;
	}

	public String getTipo()
	{
		return "solicitud_lista";
	}
	
	public List<Map.Entry<String, List<String>>> getLista()	//Envia la lista pedida
	{
		return _l;
	}
}