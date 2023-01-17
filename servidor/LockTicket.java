package servidor;

import java.util.concurrent.atomic.AtomicInteger;

public class LockTicket {
	
	private volatile int nowTicket;
	private volatile AtomicInteger  nextTicket;
	
	public LockTicket()
	{
		this.nowTicket = 0;
		this.nextTicket = new AtomicInteger(0);
	}

	void takeLock(int id) {
		int myTicket = this.nextTicket.getAndAdd(1);
		while(this.nowTicket != myTicket);
	}

	void releaseLock(int id) {
		this.nowTicket++;
	}

}