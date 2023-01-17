//Practica Final PC
//Pablo Lozano Martin y Pablo Magno Pezo Ortiz

package servidor;

import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.io.IOException;
import java.util.TreeMap;
import java.util.concurrent.Semaphore;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.FileReader;

public class Servidor {
	
	protected static MonitorUsuarios monitor = new MonitorUsuarios();
	private static Semaphore semaforo = new Semaphore(1);
	static LockTicket lock = new LockTicket();
	
	private static Map<String, Set<String>> fileMap = new TreeMap<>();
	
	private static int portClients = 100;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		int id = 0;
		String usuario;
		
		if(args.length < 1) {
			System.err.println("<Puerto no detectado>");	//Necesitas incluir el puerto por los argumentos
			System.exit(1);
		}
		
		int puerto = Integer.parseInt(args[0]);
		
		BufferedReader f = new BufferedReader(new FileReader("usuarios.txt"));
		
		while((usuario = f.readLine()) != null)
		{
			f.readLine().split(" ");
			
			for(String file: f.readLine().split(" "))
			{
				updateFile(file, usuario);
			}
		}
		
		ServerSocket sSocket = new ServerSocket(puerto);
		
		System.out.println("<Servidor iniciado en el puerto " + puerto + ">");
		System.out.println("Esperando clientes...");
		
		while(true) {	// Espera hasta que un cliente se une
			Socket socket = sSocket.accept();
			
			OyenteCliente listener = new OyenteCliente(socket, id, lock);
			listener.start();
			
			id++;
		}
	}
	
	protected static List<String> fileList() throws InterruptedException
	{		
		List<String> lista = new ArrayList<>();	
		
		semaforo.acquire();	//Uso del semaforo para proteger la seccion critica del fileMap
		
		for(String s : fileMap.keySet())
		{
			lista.add(s);
		}
		
		semaforo.release();
		
		return lista;	
	}
	
	protected static boolean hasFile(String f, String u) throws InterruptedException
	{		
		semaforo.acquire();	//Uso del semaforo para proteger la seccion critica del fileMap
		
		Set<String> m = fileMap.get(f);
		
		semaforo.release();
		
		return m.contains(u);
	}
	
	protected static void updateFile(String f, String u) throws InterruptedException
	{		
		semaforo.acquire();		//Uso del semaforo para proteger la seccion critica del fileMap
		
		if(!fileMap.containsKey(f))
		{
			Set<String> h = new HashSet<String>();
			
			h.add(u);
			fileMap.put(f, h);
		}
		else
		{
			fileMap.get(f).add(u);
		}
		
		semaforo.release();
	}
	
	protected static Set<String> clientList(String f) throws InterruptedException
	{		
		semaforo.acquire();		//Uso del semaforo para proteger la seccion critica del fileMap
		
		Set<String> file = fileMap.get(f);
		
		semaforo.release();
		
		return file;
	}
	
	protected static boolean fileInServer(String f) throws InterruptedException
	{		
		semaforo.acquire();		//Uso del semaforo para proteger la seccion critica del fileMap
		
		boolean contains = fileMap.containsKey(f);
		
		semaforo.release();
		
		return contains;
	}

	public static int getPuerto() {
		
		portClients++;
		
		return portClients;
	}

	public static void removeUser(String origen) {

		monitor.removeUser(origen);
		
	}

	public static ObjectOutputStream outf(String destino) {
		
		return monitor.outf(destino);
	}

	public static void addUser(String cliente, ObjectInputStream _reader, ObjectOutputStream _writer) {

		monitor.addUser(cliente, _reader, _writer);
		
	}

	public static List<String> userList() {
		
		return monitor.userList();
	}

	public static boolean availableClients(String aC) {
		
		return monitor.availableClients(aC);
	}
}