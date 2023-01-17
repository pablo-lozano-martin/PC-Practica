package servidor;

import java.io.ObjectInputStream;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MonitorUsuarios {
	
	private Map<String, Usuario> users = new TreeMap<>();
	
	synchronized void addUser(String usuario, ObjectInputStream reader, ObjectOutputStream writer)	//Añade un usuario a la lista
	{
		users.put(usuario, new Usuario(usuario, reader, writer));
	}
	
	synchronized void removeUser(String usuario)	//Elimina un usuario de la lista
	{
		users.remove(usuario);
	}
	
	synchronized boolean availableClients(String cliente)	//Devuelve los clientes disponibles
	{
		return users.containsKey(cliente);
	}
	
	synchronized ObjectOutputStream outf(String cliente)	//Devuelve el writer del cliente en concreto
	{
		return users.get(cliente).getWriter();				
	}
	
	synchronized List<String> userList()	//Devuelve los nombres de los usuarios conectados
	{
		List<String> l = new ArrayList<>();
		
		for(String s : users.keySet())
		{
			l.add(s);
		}
		
		return l;
	}

}