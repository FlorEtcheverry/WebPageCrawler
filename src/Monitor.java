import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor implements Runnable {

	LinkedBlockingQueue<String[]> colaStatus;
	private Lock lock;
	private HashMap<String, Integer> sharedStatus;

	public Monitor(LinkedBlockingQueue<String[]> cola) {
		colaStatus = cola;
		lock = new ReentrantLock();
		sharedStatus = new HashMap<String, Integer>();
	}

	@Override
	public void run() {

		ScheduledExecutorService scheduledExSer = Executors
			.newScheduledThreadPool(1);

		scheduledExSer.scheduleAtFixedRate(
			new StatsSaver(lock, sharedStatus),
			0, 
			1, 
			TimeUnit.SECONDS
			);

		while (true) {

			try {
				// leer data de la cola
				String[] msg = colaStatus.take();
				
				// lockear
				lock.lock();

				// guardar en map
				if (!sharedStatus.containsKey(msg[0])) {
					sharedStatus.put(msg[0], 0);
				}
				
				sharedStatus.put(
					msg[0],
					sharedStatus.get(msg[0]) + Integer.parseInt(msg[1])
					);

				// unlockear
				lock.unlock();

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
