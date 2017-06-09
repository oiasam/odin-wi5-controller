package net.floodlightcontroller.odin.master;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.projectfloodlight.openflow.protocol.OFFactory;
import org.projectfloodlight.openflow.protocol.OFFlowAdd;
//import org.projectfloodlight.openflow.protocol.OFFlowMod;
import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.action.OFAction;
import org.projectfloodlight.openflow.protocol.action.OFActionOutput;
import org.projectfloodlight.openflow.protocol.action.OFActions;
import org.projectfloodlight.openflow.protocol.instruction.OFInstructionApplyActions;
import org.projectfloodlight.openflow.protocol.instruction.OFInstructions;
import org.projectfloodlight.openflow.protocol.instruction.OFInstruction;
import org.projectfloodlight.openflow.protocol.match.Match;
import org.projectfloodlight.openflow.protocol.match.MatchField;
import org.projectfloodlight.openflow.types.EthType;
import org.projectfloodlight.openflow.types.IPv4Address;
import org.projectfloodlight.openflow.types.OFPort;

import net.floodlightcontroller.core.IOFSwitch;

/*import org.openflow.protocol.OFFlowMod;
import org.openflow.protocol.OFMatch;
import org.openflow.protocol.OFMessage;
import org.openflow.protocol.action.OFAction;
import org.openflow.protocol.action.OFActionDataLayerDestination;
import org.openflow.protocol.action.OFActionOutput;
import org.openflow.util.U16;*/

//import net.floodlightcontroller.util.MACAddress;

public class LvapManager {
			
	/**
	 * Get the default flow table entries that Odin associates
	 * with each LVAP
	 * @param lvap 
	 * 
	 * @param inetAddr IP address to use for the flow
	 * @return a list of flow mods
	 */
	public List<OFMessage> getDefaultOFModList(Lvap lvap, InetAddress inetAddr) {
		IOdinAgent agent = lvap.getAgent();
		IOFSwitch sw = agent.getSwitch();
		OFFactory myFactory = sw.getOFFactory();	
		
		//OFFactory myFactory = lvap.getAgent().getSwitch().getOFFactory();
		OFInstructions instructions = myFactory.instructions();
		OFActions actions = myFactory.actions();
		
		Match match1  = myFactory.buildMatch()
				.setExact(MatchField.IN_PORT, OFPort.of(2))
				.setExact(MatchField.ETH_TYPE, EthType.IPv4)
				.setExact(MatchField.IPV4_SRC, IPv4Address.of(inetAddr.getHostAddress()))
				.build();
		
		
		ArrayList<OFAction> actionList1 = new ArrayList<OFAction>();
		
		OFActionOutput output1 = actions.buildOutput()
				.setPort(OFPort.of(1))
				.build();
		actionList1.add(output1);
		
		ArrayList<OFInstruction> instructionList1 = new ArrayList<OFInstruction>();
		
		OFInstructionApplyActions applyActions1 = instructions.buildApplyActions()
				.setActions(actionList1)
				.build();
		instructionList1.add(applyActions1);
		
		
		OFFlowAdd flow1 = myFactory.buildFlowAdd()
				.setMatch(match1)
				.setInstructions(instructionList1)
				.build();
		
		
		
		/*OFFlowMod flow1 = new OFFlowMod;
		{
			OFMatch match = new OFMatch();
			match.fromString("in_port=2,dl_type=0x0800,nw_src=" + inetAddr.getHostAddress());
			
			OFActionOutput actionOutput = new OFActionOutput ();
			actionOutput.setPort((short) 1);
			actionOutput.setLength((short) OFActionOutput.MINIMUM_LENGTH);
			
			List<OFAction> actionList = new ArrayList<OFAction>();
			actionList.add(actionOutput);
			
		
			flow1.setCookie(12345);
			flow1.setPriority((short) 200); 
			flow1.setMatch(match);
			flow1.setIdleTimeout((short) 0);
			flow1.setActions(actionList);
	        flow1.setLength(U16.t(OFFlowMod.MINIMUM_LENGTH
	        		+ OFActionOutput.MINIMUM_LENGTH));	
		}*/
		
		Match match2  = myFactory.buildMatch()
				.setExact(MatchField.IN_PORT, OFPort.of(1))
				.setExact(MatchField.ETH_TYPE, EthType.IPv4)
				.setExact(MatchField.IPV4_DST, IPv4Address.of(inetAddr.getHostAddress()))
				.build();
		
		ArrayList<OFAction> actionList2 = new ArrayList<OFAction>();
		
		OFActionOutput output2 = actions.buildOutput()
				.setPort(OFPort.of(2))
				.build();
		actionList2.add(output2);
		
		ArrayList<OFInstruction> instructionList2 = new ArrayList<OFInstruction>();
		
		OFInstructionApplyActions applyActions2 = instructions.buildApplyActions()
				.setActions(actionList2)
				.build();
		instructionList2.add(applyActions2);
		
		
		OFFlowAdd flow2 = myFactory.buildFlowAdd()
				.setMatch(match2)
				.setInstructions(instructionList2)
				.build();

		/*OFFlowMod flow2 = new OFFlowMod();
		{
			OFMatch match = new OFMatch();
			match.fromString("in_port=1,dl_type=0x0800,nw_dst=" + inetAddr.getHostAddress());
			
			OFActionOutput actionOutput = new OFActionOutput ();
			actionOutput.setPort((short) 2);
			actionOutput.setLength((short) OFActionOutput.MINIMUM_LENGTH);
			
			List<OFAction> actionList = new ArrayList<OFAction>();
			actionList.add(actionOutput);
			
		
			flow2.setCookie(12345);
			flow2.setPriority((short) 200);
			flow2.setMatch(match);
			flow2.setIdleTimeout((short) 0);
			flow2.setActions(actionList);
	        flow2.setLength(U16.t(OFFlowMod.MINIMUM_LENGTH + OFActionOutput.MINIMUM_LENGTH));
		}*/
	
		ArrayList<OFMessage> list = new ArrayList<OFMessage>();
		
		list.add(flow1);
		list.add(flow2);
		
		return list;
	}

}
