package cliente;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.io.ObjectOutputStream;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;
import java.io.ObjectInputStream;
import java.net.InetAddress;

import mensaje.MensajeDesconectar;
import mensaje.MensajeSolicitud;
import mensaje.MensajeConectar;
import mensaje.MensajeUsuarios;

public class Cliente {

	private String destino = "listenerClient";
	
	private int _puerto;
	private String _nombre;
	private InetAddress _IPAdress;
	
	ObjectOutputStream _writer;
	ObjectInputStream _reader;
	Socket _socket;
	
	public Cliente(int puerto, String nombre, InetAddress IPAdress) throws UnknownHostException, IOException {
		super();
		_nombre = nombre;
		_IPAdress = IPAdress;
		_puerto = puerto;

		_socket = new Socket(InetAddress.getLocalHost(), _puerto);	//Crea el canal
		_writer = new ObjectOutputStream(_socket.getOutputStream());
		_reader = new ObjectInputStream(_socket.getInputStream());

		OyenteServidor listener = new OyenteServidor(_writer, _reader, this);
		listener.start();
	}
	
	@SuppressWarnings("resource")
	public void init() throws UnknownHostException, IOException {
		
		System.out.println("Escriba los ficheros que le pertenecen:");

		Scanner scanner = new Scanner(System.in);
		
		List<String> files = Arrays.asList(scanner.nextLine().split(" "));
		
		_writer.writeObject(new MensajeConectar(_nombre, destino, files));
		
		System.out.println("Usuario conectandose: " + _nombre);
		
		System.out.println("------MENU------");
		System.out.println("1. Pedir fichero a otro usuario conectado");
		System.out.println("2. Pedir lista de usuarios conectados");
		System.out.println("0. Salir del sistema");
		
		while(true)	//Espera hasta que se escribe una instruccion
		{
			String l = scanner.nextLine();
			
			if(!l.equals("0") && !l.equals("1") && !l.equals("2"))
			{
				System.err.println("ERROR - caracter no valido");
			}
			else 
			{
				int op = Integer.parseInt(l);
				
				switch(op) {
				
					case 0:	//Salir del servidor
						
						System.out.println("¿Confirmar? S/N");
						
						String l2 = scanner.nextLine();
						
						if(l2.equals("S"))
						{
							_writer.writeObject(new MensajeDesconectar(_nombre, destino));
						}
						else if (!l2.equals("N"))
						{
							System.err.println("ERROR - caracter no valido");
						}
						
						break;
						
					case 1:	//Descargar un fichero
						
						System.out.println("Fichero: ");
						String f = scanner.nextLine();
						
						_writer.writeObject(new MensajeSolicitud(_nombre, destino, f));
						
						break;
						
					case 2:	//Mostrar la lista de usuarios con su info
						
						System.out.println("Mostrando lista de usuarios: ");
						_writer.writeObject(new MensajeUsuarios(_nombre, destino));
						
						break;

				}
			}
		}
	}
	
	public int getPuerto() {
		return _puerto;
	}

	public String getNombre() {
		return _nombre;
	}

	public InetAddress getIP() {
		return _IPAdress;
	}
}