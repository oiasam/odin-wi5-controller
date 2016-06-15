package net.floodlightcontroller.odin.applications;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.floodlightcontroller.odin.master.OdinApplication;
import net.floodlightcontroller.odin.master.OdinClient;
import net.floodlightcontroller.util.MACAddress;

public class ShowStatistics extends OdinApplication {

	private final int INTERVAL = 60000;
	//private final int SIGNAL_THRESHOLD = 160;

	HashSet<OdinClient> clients;
	//Map<MACAddress, Set<InetAddress>> hearingMap = new HashMap<MACAddress, Set<InetAddress>> ();
	//Map<InetAddress, Integer> newMapping = new HashMap<InetAddress, Integer> ();
	
	
	@Override
	public void run() {
		
		
		while (true) {
			try {
				Thread.sleep(INTERVAL);
								
				clients = new HashSet<OdinClient>(getClients());
				
				//hearingMap.clear();
				//newMapping.clear();
				
				/*
				 * Probe each AP to get the list of MAC addresses that it can "hear".
				 * We define "able to hear" as "signal strength > SIGNAL_THRESHOLD".
				 * 
				 *  We then build the hearing table.
				 */
				 
				// for each Agent
				for (InetAddress agentAddr: getAgents()) {
					Map<MACAddress, Map<String, String>> vals = getRxStatsFromAgent(agentAddr);
					System.out.println("IP Agent " + agentAddr + "\n");

          // for each STA associated to the Agent
					for (Entry<MACAddress, Map<String, String>> vals_entry: vals.entrySet()) {
						MACAddress staHwAddr = vals_entry.getKey();
						System.out.println("  MAC station " + staHwAddr + "\n");
						System.out.println("    packets: " + vals_entry.getValue().get("packets") + "\n");
						System.out.println("    avg rate: " + vals_entry.getValue().get("avg_rate") + "\n");
						System.out.println("    avg signal: " + vals_entry.getValue().get("avg_signal") + "\n");
						System.out.println("    avg length: " + vals_entry.getValue().get("avg_len_pkt") + "\n");
						System.out.println("    air time: " + vals_entry.getValue().get("air_time") + "\n");						
						System.out.println("    init time: " + vals_entry.getValue().get("first_received") + "\n");
						System.out.println("    end time: " + vals_entry.getValue().get("last_received") + "\n");
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
