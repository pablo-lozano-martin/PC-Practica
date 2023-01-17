package cliente;

import mensaje.Mensaje_conexion;
import mensaje.Mensaje_lista_usuarios;
import mensaje.Mensaje_cerrar_conexion;
import mensaje.Mensaje_pedir_fichero;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Cliente {
	// puerto por el que el cliente se va a conectar con el servidor
	private int puerto;
	
	// nombre de usuario del cliente
	private String nombreUser;
	
	// direcci�n IP del cliente
	private InetAddress dirIP;
	
	public int getPuerto() {
		return puerto;
	}

	public String getNombreUser() {
		return nombreUser;
	}

	public InetAddress getDirIP() {
		return dirIP;
	}
	
	public Cliente(int puerto, String nombreUser, InetAddress dirIP) {
		super();
		this.puerto = puerto;
		this.nombreUser = nombreUser;
		this.dirIP = dirIP;
	}
	
	// lleva a cabo el cometido del cliente
	public void iniciaCliente() throws UnknownHostException, IOException {
		
		System.out.println(puerto);
		System.out.println(InetAddress.getLocalHost());
		// crea el canal para conectarse con el oyente cliente
		Socket socket = new Socket(InetAddress.getLocalHost(), puerto);
		
		// flujos para comunicarse con el oyente del cliente
		ObjectOutputStream escritor = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream lector = new ObjectInputStream(socket.getInputStream());
		
		// proceso que se encarga de escuchar al oyente cliente mientras 
		// la clase cliente interactua con el usuario
		OyenteServidor oyente = new OyenteServidor(escritor, lector, this);
		oyente.start();
		
		// pide al usuario los ficheros de los que es propietario
		System.out.println("Indique los ficheros de los que es propietario");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
		// los los ficheros que introduce el usuario por consola
		List<String> ficheros = Arrays.asList(scanner.nextLine().split(" "));
		
		// indica el inicio de la conexi�n
		System.out.println("Yo, usuario " + nombreUser + ", estoy estableciendo la conexi�n");
		
		// avisa al oyente cliente de que el cliente quiere conectarse con el servidor
		escritor.writeObject(new Mensaje_conexion(nombreUser, "Oyente cliente", ficheros));
		
		// imprime el men�
		System.out.println("Opciones:");
		System.out.println("1 - Pedir lista de usuarios conectados");
		System.out.println("2 - Pedir fichero a otro usuario conectado");
		System.out.println("3 - Salir del sistema");
		
		// ejecuta la elecci�n del usuario
		while(true) {
			// lee la opci�n
			String linea = scanner.nextLine();
			
			// si la opci�n elegida es v�lida
			if(linea.equals("1") || linea.equals("2") || linea.equals("3")) {
			
				// convierte el string a entero
				int opcion = Integer.parseInt(linea);
				
				// casos
				switch(opcion) {
				
				// el usuario quiere la lista de usuarios conectados
				case 1:
					
					// avisamos al oyente cliente
					escritor.writeObject(new Mensaje_lista_usuarios(nombreUser, "Oyente cliente"));
					
					break;
					
				// el usuario quiere pedir un fichero
				case 2:
					
					// pide el fichero al usuario
					System.out.println("Introduzca el nombre del fichero deseado");
					String fichero = scanner.nextLine();
					
					// avisa al oyente cliente del fichero que quiere el usuario
					escritor.writeObject(new Mensaje_pedir_fichero(nombreUser, "Oyente cliente", fichero));
					
					break;
					
				// el usuario quiere desconectarse
				case 3:
					
					// avisa al oyente cliente de que el usuario quiere salir
					escritor.writeObject(new Mensaje_cerrar_conexion(nombreUser, "Oyente cliente"));
					
					break;
				}
			}
			
			// si la opci�n introducida no es v�lida, avisamos por consola al usuario
			else {
				System.err.println("Introduzca una opci�n v�lida");
			}
		}
	}
}