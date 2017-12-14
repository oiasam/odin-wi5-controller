package net.floodlightcontroller.odin.applications;


import net.floodlightcontroller.odin.master.OdinApplication;
import net.floodlightcontroller.odin.master.OdinClient;
import net.floodlightcontroller.util.MACAddress;
import net.floodlightcontroller.odin.master.IOdinAgent;
import net.floodlightcontroller.odin.master.Lvap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class VerticalHandoverManual extends OdinApplication {
	
	protected static Logger log = LoggerFactory.getLogger(VerticalHandoverManual.class);

	HashSet<OdinClient> clients;
	
	private final int START_WAIT = 60000; // ms time before running the application. This leaves you some time for starting the agents
	private final int INTERVAL = 120000; // ms periodic interval to remove all clients
	private final long BLACKLIST = 360000; // ms time to blacklist client before it may reconnect
	private final long SLEEP = 500; // do not hog the CPU while there aren't any clients

	/* (non-Javadoc)
	 * @see net.floodlightcontroller.odin.master.OdinApplication#run()
	 */
	@Override
	public void run() {
		OdinClient currentClient;
		Map<MACAddress, Long> clientBlacklist = getClientBlackList();
		//Lvap currentLvap;
		//IOdinAgent currentAgent;
		Scanner sc = new Scanner(System.in);
				
		/* When the application runs, you need some time to start the agents */
		log.info("Wait " + START_WAIT + " ms to initialize.");
		try {
			Thread.sleep(START_WAIT);
		} catch (InterruptedException e){
        		e.printStackTrace();
		}
		log.info("Manually block / unblock clients");
		while(1==1){
			/*all the clients Odin has heard (even non-connected) */				
			clients = new HashSet<OdinClient>(getClients());
			
			Iterator<OdinClient> it = clients.iterator();
			int index = 0;
		
			while(it.hasNext()){
				currentClient = (OdinClient) it.next();
				index++;

				System.out.println("["+index+"] " + currentClient.getMacAddress().toString());
								
			}
			
			if (index > 0){
				System.out.println("Select (by index) a MAC address to blacklist");
				
				int clientIdx = 0;
				try {
					clientIdx = sc.nextInt();
				} catch (Exception e) {
					e.getMessage();
				}
				
				System.out.println("User selected to block MAC address with index: "+ clientIdx);
				
				Iterator<OdinClient> ix = clients.iterator();
				index = 1;
				
				while (ix.hasNext()){
					currentClient = (OdinClient) ix.next();
					if (index++ == clientIdx){
						
						long _now = System.nanoTime();
						long _blacklist = _now + BLACKLIST*1000000;
						log.debug("Removing client " + currentClient.getMacAddress().toString()+", blacklisting from "+_now+" ns until "+_blacklist+" ns");
						
						// Add selected MAC to blacklist
						clientBlacklist.put(currentClient.getMacAddress(), _blacklist);
				
						deauthClient(currentClient);
						break;
					}
				}
			}
				
			
			Iterator<MACAddress> itBL = clientBlacklist.keySet().iterator();
			int indexBL = 0;
		
			while(itBL.hasNext()){
				MACAddress currentMac = itBL.next();
				indexBL++;

				System.out.println("["+indexBL+"] " + currentMac.toString() );
								
			}
			
			if (indexBL > 0){
				System.out.println("Select a MAC address to unblock");
				
				int clientIdx = 0;
				try {
					clientIdx = sc.nextInt();
				} catch (Exception e) {
					e.getMessage();
				}
			
				System.out.println("User selected to unblock MAC address with index: "+ clientIdx);
				
				Iterator<MACAddress> ixBL = clientBlacklist.keySet().iterator();
				indexBL = 1;
			
				while (ixBL.hasNext()){
					MACAddress currentMac = ixBL.next();
					if (indexBL++ == clientIdx){
						clientBlacklist.remove(currentMac);
						break;
					}
				}
			}
			
			try {
				Thread.sleep(SLEEP);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//Erase expired blacklist entries
			//Iterator<MACAddress> itBL = clientBlacklist.keySet().iterator();
			//while (itBL.hasNext()){
			//	MACAddress currentMac = itBL.next();
			//	long blackListTime = clientBlacklist.get(currentMac);
			//	if(blackListTime <= System.nanoTime()){
			//		clientBlacklist.remove(currentMac);
			//	}
			//}

			//log.info("Wait " + INTERVAL + " ms to repeat removal of clients.");
			//try {
			//	Thread.sleep(INTERVAL);
			//} catch (InterruptedException e){
	        //		e.printStackTrace();
			//}
		}
	}
}
