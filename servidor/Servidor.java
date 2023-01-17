package servidor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Semaphore;

public class Servidor {
	
	// referencia al monitor donde est� la lista de usuarios conectados y los flujos 
	// para acceder a ellos
	protected static Monitor_usuarios monitor = new Monitor_usuarios();
	
	private static Semaphore semInfoFicheros = new Semaphore(1);
	
	static LockTicket lock = new LockTicket();
	
	// tabla ficheros-usuarios propietarios
	private static Map<String, Set<String>> infoFicheros = new TreeMap<>();
	
	private static int numPuerto = 100;
	
	// representa la m�quina servidor
	public static void main(String[] args) throws IOException, InterruptedException {
		
		int id = 0;
		
		// si no hay puerto error
		if(args.length < 1) {
			System.err.println("No hay puerto");
			System.exit(1);
		}
		
		// se convierte el string a entero
		int puerto = Integer.parseInt(args[0]);
		
		@SuppressWarnings("resource")
		// se abre el fichero users.txt donde se encuentra la informaci�n inicial de usuarios 
		// registrados y sus ficheros propios
		BufferedReader leeFich = new BufferedReader(new FileReader("users.txt"));
		
		String nombreUser;
		// se lee el nombre de usuario
		
		while((nombreUser = leeFich.readLine()) != null) {
			// se lee la l�nea que contiene los ficheros del usuario
			System.out.println(leeFich.readLine().split(" "));
			for(String fichero: leeFich.readLine().split(" ")) {
				// se actualiza la tabla de ficheros-usuarios propietarios
				actualizaFichero(fichero, nombreUser);
			}
		}
		
		@SuppressWarnings("resource")
		ServerSocket serversocket = new ServerSocket(puerto);
		
		while(true) {
			// el servidor espera a que llegue un cliente para crear el canal
			Socket socket = serversocket.accept();
			
			// lanza un proceso oyente cliente para que gestione las peticiones del cliente
			OyenteCliente oyentecliente = new OyenteCliente(socket, id, lock);
			
			// se inicia la eecuci�n del hilo
			oyentecliente.start();
			
			id++;
		}
	}
	
	// se actualiza la tabla fichero-usuarios propietarios
	protected static void actualizaFichero(String fichero, String usuario) throws InterruptedException {
		
		semInfoFicheros.acquire();
		
		// si el fichero ya estaba en el sistema
		if(infoFicheros.containsKey(fichero)) {
			// se a�ade este usuario como propietario suyo
			infoFicheros.get(fichero).add(usuario);
		}
		
		// si el fichero no estaba en el sistema
		else {
			// se crea un nuevo conjunto
			Set<String> conjunto = new HashSet<String>();
			
			// se a�ade el usuario a ese conjunto
			conjunto.add(usuario);
			
			// se a�ade el fichero-conjunto a nuestra tabla
			infoFicheros.put(fichero, conjunto);
		}
		
		semInfoFicheros.release();
	}
	
	// devuelve la lista de ficheros de nuestro sistema
	protected static List<String> listaFicheros() throws InterruptedException {
		
		semInfoFicheros.acquire();
		
		List<String> lista = new ArrayList<>();
		
		// se a�ade cada fichero de la tabla a una lista
		for(String s : infoFicheros.keySet()) {
			lista.add(s);
		}
		
		semInfoFicheros.release();
		
		return lista;	
	}
	
	// comprueba si un usuario es propietario de un determinado fichero
	protected static boolean esPropietario(String usuario, String fichero) throws InterruptedException {
		
		semInfoFicheros.acquire();
		
		// se obtiene el conjunto de usuarios propietarios del fichero
		Set<String> conjunto = infoFicheros.get(fichero);
		
		semInfoFicheros.release();
		
		// se comprueba si el usuario por el que nos preguntaban se encuentra en dicho conjunto
		return conjunto.contains(usuario);
	}
	
	// devuelve la lista de propietarios de un fichero
	protected static Set<String> listaPropietarios(String fichero) throws InterruptedException{
		
		semInfoFicheros.acquire();
		
		// se obtiene el conjunto de usuarios propietarios del fichero
		Set<String> conjunto = infoFicheros.get(fichero);
		
		semInfoFicheros.release();
		
		return conjunto;
	}
	
	// comprueba si un determinado fichero est� en el sistema
	protected static boolean existeFichero(String fichero) throws InterruptedException {
		
		semInfoFicheros.acquire();
		
		// comprueba si el fichero se encuentra en la tabla
		boolean existe = infoFicheros.containsKey(fichero);
		
		semInfoFicheros.release();
		
		return existe;
	}

	public static int obtenerPuerto() {
		
		numPuerto++;
		
		return numPuerto;
	}
	
//	protected static int obtenerPuerto() throws InterruptedException {
//		
//		try {
//			semPuertos.acquire();
//
//			numPuerto++;
//			
//			semPuertos.release();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return numPuerto;
//	}
}