package baq;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Semaphore {

	public final int totalPermits;
	public int permitsIssued;
	
	public Semaphore(int totalPermits) {
		this.totalPermits = totalPermits;
		this.permitsIssued = 0;
	}
	
	public synchronized void getPermit() throws InterruptedException {
		if (permitsIssued == totalPermits) {
			System.out.println("Maximum permits acquired");
			wait();
		} else {
			permitsIssued++;
			System.out.println("Num permits available: " + permitsIssued);
		}
	}
	
	public synchronized void returnPermit() {
		if (permitsIssued == 0) {
			return;
		}
		if (permitsIssued == totalPermits) {
			notify();
		}
		permitsIssued--;
	}
	
	
	private final static ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
	public static void main(String[] args) {
		final Semaphore semaphore = new Semaphore(1);
		service.scheduleAtFixedRate(new PermitTaker(10L, semaphore), 0, 5, TimeUnit.MILLISECONDS);
		service.scheduleAtFixedRate(new PermitTaker(10L, semaphore), 0, 10, TimeUnit.MILLISECONDS);
	}
	
	private final static class PermitTaker implements Runnable {
		
		private final Semaphore semaphore;
		private final long millisSleep;
		
		public PermitTaker(long millisSleep, Semaphore semaphore) {
			this.millisSleep = millisSleep;
			this.semaphore = semaphore;
		}
		
		public void run() {
			try {
				semaphore.getPermit();
				Thread.sleep(millisSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				semaphore.returnPermit();
			}
		}
	}
}
