import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;


public class StatsSaver implements Runnable {
	
	private Lock lock;
	private HashMap<String, Integer> statusMap;
	private String fileName;

	public StatsSaver(Lock monitorLock,HashMap<String, Integer> status) {
		ConfigLoader conf = new ConfigLoader();
		fileName = conf.getStatsPath();
		lock = monitorLock;
		statusMap = status;
		
	}

	@Override
	public void run() {		
		//toma el lock, guarda el map, suelta el lock y termina
		
		BufferedWriter bw;
		try {
			File arch = new File(fileName);
			if (!arch.exists()) {
				arch.createNewFile();
			}
			bw = new BufferedWriter(new FileWriter(arch,true));
			
			//escribir timestamp
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
			String formattedDate = sdf.format(date);
			bw.write(formattedDate);
			bw.newLine();
			
			//escribir datos
			lock.lock();
			
			for (String key : statusMap.keySet()) {
				bw.write(key + ": "+statusMap.get(key));
				bw.newLine();
			}
			
		    lock.unlock();
		    
		    bw.newLine();
		    bw.close();
		    
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
