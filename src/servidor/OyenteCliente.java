package servidor;

import mensaje.Mensaje;
import mensaje.MensajeErrorFichero;
import mensaje.MensajeConfirmarConexion;
import mensaje.MensajeConectar;
import mensaje.MensajeConfirmarUsuarios;
import mensaje.MensajeSolicitud;
import mensaje.MensajeListo;
import mensaje.MensajeEmision;
import mensaje.MensajeDesconectar;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OyenteCliente extends Thread {

	private String origen = "listenerClient";
	
	private Socket _socket;
	private String nombre;
	private int _id;
	private LockTicket _lock;
	private ObjectInputStream _reader;
	private ObjectOutputStream _writer;

	public OyenteCliente(Socket socket, int id, LockTicket lock) throws IOException {
		super();
		_id = id;
		_lock = lock;
		_socket = socket;
		
		_reader = new ObjectInputStream(_socket.getInputStream());
		_writer = new ObjectOutputStream(_socket.getOutputStream());
	}
	
	public void run() {
		try {
			
			while(true) {	//Espera hasta leer una orden

				Mensaje m = (Mensaje) _reader.readObject();
				
				int i = 0;
				
				switch(m.getTipo()) {
				
				case "sesion":
					
					MensajeConectar mSesion = (MensajeConectar) m;
					nombre = mSesion.getOrigen();
					Servidor.addUser(nombre, _reader, _writer);
					
					i = 0;
					
					while (i < mSesion.getFiles().size())
					{
						Servidor.updateFile(mSesion.getFiles().get(i), nombre);
						i++;
					}
					_writer.writeObject(new MensajeConfirmarConexion(origen, nombre));
					break;
					
				case "solicitud_lista":

					List<Map.Entry<String, List<String>>> l = new ArrayList<>();
					
					int j = 0;
					
					while (j < Servidor.userList().size())
					{
						String u = Servidor.userList().get(j);
						
						l.add(Pair.of(u, new ArrayList<>()));
						
						i = 0;
						
						while (i < Servidor.fileList().size())
						{
							String f = Servidor.fileList().get(i);
							
							if(Servidor.hasFile(f, u)) l.get(l.size() - 1).getValue().add(f);
							
							i++;
						}
						
						j++;
					}
					
					_writer.writeObject(new MensajeConfirmarUsuarios(origen, nombre, l));
					break;
					
				case "solicitud_fichero_listo":
					
					MensajeListo mReadyFile = (MensajeListo) m;
					
					ObjectOutputStream write = Servidor.outf(mReadyFile.getDestino());
		
					write.writeObject(new MensajeListo(origen, mReadyFile.getDestino(), mReadyFile.getIP(), mReadyFile.getPuerto()));
					
					break;
					
				case "solicitud_fichero":
					
					MensajeSolicitud mFichero = (MensajeSolicitud) m;
					
					if(Servidor.fileInServer(mFichero.getFichero())) {
						
						String a = null;
						
						for(String aC : Servidor.clientList(mFichero.getFichero()))
						{
							if(Servidor.availableClients(aC))
							{
								a = aC;
								break;
							}
						}
						
						if(a != null)
						{							
							_lock.takeLock(_id);	//Lock para proteger la variable de puertos disponibles
							
							int idPuerto = Servidor.getPuerto();
							
							_lock.releaseLock(_id);
							
							Servidor.outf(a).writeObject(new MensajeEmision(nombre, a, mFichero.getFichero(), idPuerto));
						}
						else
						{
							_writer.writeObject(new MensajeErrorFichero(origen, nombre));
						}
					}
					else
					{
						_writer.writeObject(new MensajeErrorFichero(origen, nombre));
					}
					
					break;
					
				case "fin_sesion":

					MensajeDesconectar mExit = (MensajeDesconectar) m;
					
					Servidor.removeUser(mExit.getOrigen());
					
					_writer.writeObject(new MensajeDesconectar(origen, mExit.getOrigen()));
					
					break;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}