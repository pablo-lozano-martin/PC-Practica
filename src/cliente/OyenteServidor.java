package cliente;

import mensaje.Mensaje;
import mensaje.MensajeConfirmarUsuarios;
import mensaje.MensajeEmision;
import mensaje.MensajeListo;
import java.util.List;
import java.util.Map;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class OyenteServidor extends Thread{
	
	private ObjectOutputStream _writer;
	private ObjectInputStream _reader;
	
	private Cliente _cliente;
	
	private String origen = "Oyente servidor";
	
	public OyenteServidor(ObjectOutputStream escritor, ObjectInputStream lector, Cliente cliente) {
		super();
		_writer = escritor;
		_reader = lector;
		_cliente = cliente;
	}

	public void run()
	{
		try {
			
			while(true)	//Espera las instrucciones recibidas por el mensaje
			{
				Mensaje m = (Mensaje) _reader.readObject();
				
				switch(m.getTipo())
				{
				
					case "sesion":	//Inicia sesion un nuevo cliente
						System.out.println("<" + _cliente.getNombre() + " conectado>");
						
						break;
						
					case "solicitud_lista":	//Se solicita la lista de usuarios con su info
						
						MensajeConfirmarUsuarios mLista = (MensajeConfirmarUsuarios) m;
						
						for(Map.Entry<String, List<String>> a : mLista.getLista())
						{
							System.out.println("> Ficheros de " + a.getKey() + ": ");
							
							for(String fichero : a.getValue())
							{
								System.out.println("         > " + fichero);
							}
						}
						
						break;
						
					case "solicitud_fichero_listo":	//Creas un nuevo receptor para el mensaje a recibir
		
						MensajeListo mFicheroListo = (MensajeListo) m;
						
						Receptor r = new Receptor(mFicheroListo.getIP(), mFicheroListo.getPuerto());
						
						r.start();
						
						break;	
					
					case "solicitud_fichero":	//Solicitan el envio de un fichero de un cliente a otro
						
						MensajeEmision mFichero = (MensajeEmision) m;
						
						Emisor e = new Emisor(mFichero.getPuerto(), mFichero.getFichero());
						
						e.start();
						
						_writer.writeObject(new MensajeListo(origen, mFichero.getOrigen(), _cliente.getIP(), mFichero.getPuerto()));
								
						System.out.println("<" + mFichero.getFichero() + " ha sido enviado>");
						
						break;
						
					case "error":	//El fichero introducido no existe en la lista de ficheros de los clientes conectados
		
						System.out.println("<Fichero no existente>");
						
						break;
						
					case "fin_sesion":	//El cliente se desconecta del servidor, haciendo que sus ficheros dejen de ser publicos
						
						System.out.println("<Cliente desconectado>");
						
						System.exit(1);
						
						break;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}