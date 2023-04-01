package sharedRegions;

import entities.*;

public class ConcentrationSite {
	
	private int [] totalPartyMembers;
	private int [] partyAssignedRoom;
	private int [] availableRooms;
	private boolean [] sentParty;
	private int membersPerParty;
	private int totalThieves;
	 /**
     * Concentration  instantiation
     * 
     * @param repos reference to general repository
     */
	public ConcentrationSite(int totalThieves, int membersPerParty,int [] availableRooms)
	{
		this.membersPerParty = membersPerParty;
		this.totalThieves = totalThieves;
		this.availableRooms = availableRooms;
		sentParty = new boolean [totalThieves / membersPerParty];
		totalPartyMembers = new int [totalThieves / membersPerParty];
		partyAssignedRoom = new int [totalThieves / membersPerParty];
		for (int i = 0;i < partyAssignedRoom.length;i++ ) {
			totalPartyMembers[i]= 0;
			partyAssignedRoom[i]= -1;
			sentParty[i] = false;
		}
	}	

	
    public synchronized int amINeeded(int thiefID){
    	((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CONCENTRATION_SITE);
    	totalPartyMembers[(thiefID/membersPerParty)]++;
    	notifyAll();
		while(partyAssignedRoom[(thiefID/membersPerParty)] == -1){
			try{
		    	System.out.println("Thief_" + thiefID + "am ready to work!");
	            wait();
	        } catch(InterruptedException e){
	            e.printStackTrace();
	        }
		}
		return partyAssignedRoom[(thiefID/membersPerParty)];
    }
    
    public int partyFullIndex() {
        for (int i = 0; i < totalPartyMembers.length; i++) {
            if (totalPartyMembers[i] == membersPerParty)
                return i;
        }
        return -1;
    }
    
    public synchronized void appraiseSit() {
        // Set the state of the MasterThief to "deciding what to do"
    	((MasterThief) Thread.currentThread()).setMasterState(MasterThiefStates.DECIDING_WHAT_TO_DO);
        // Check if there are any thieves in the assault parties
        while (partyFullIndex()==-1) {
        	try{
                wait();
        	} catch(InterruptedException e){
	            e.printStackTrace();
	        }
        }
        notifyAll();
    }
    
    public synchronized void prepareAssaultParty() {
        int currentParty = partyFullIndex();     
        ((MasterThief) Thread.currentThread()).setMasterState(MasterThiefStates.ASSEMBLING_A_GROUP);
        
    	for (int i = 0;i < availableRooms.length;i++ ) {
    		if(availableRooms[i] == 1 && partyAssignedRoom[currentParty] == -1) {
    			availableRooms[i] = 0;
    			partyAssignedRoom[currentParty] = currentParty;
    			System.out.println("Party_"+ currentParty+" going to Room_" + i );
    			break;
    		}
		}
        notifyAll();
    }
    
    public synchronized boolean isLooted() {
    	int lootedRooms = 0;
    	for (int i = 0;i < availableRooms.length;i++ ) {
    		if(availableRooms[i] == -1) {
    			lootedRooms ++;
    		}
		}
    	if (lootedRooms==availableRooms.length) {
    		return true;
    	}
    	return false;
    }
    
    public synchronized boolean isSent() {
    	for (int i = 0;i < sentParty.length;i++ ) {
    		if(sentParty[i] == true) {
    			return true;
    		}
		}
    	return false;
    }
    
    public synchronized void sendAssaultParty() {
    	int currentParty = partyFullIndex();
    	if (currentParty != -1 && partyAssignedRoom[currentParty] != -1 && sentParty[currentParty] == false) {
    		sentParty[currentParty] = true;
    	}
    	((MasterThief) Thread.currentThread()).setMasterState(MasterThiefStates.DECIDING_WHAT_TO_DO);
    	notifyAll();
    	
    }
    
    public synchronized void isLast(int count) {
    	int currentParty = partyFullIndex();
    	if (count == totalPartyMembers[currentParty]) {
    		totalPartyMembers[currentParty] = 0;
    	}
    }
    
    public synchronized void prepareExcursion(){
    	int currentParty = partyFullIndex();
    	int preppedMembers = 0;
    	while(sentParty[currentParty] == false) {
    		try{
                wait();
        	} catch(InterruptedException e){
	            e.printStackTrace();
	        }
    	}
    	preppedMembers++;
    	isLast(preppedMembers);
    	notifyAll();
    	System.out.println("crawling");
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CRAWLING_INWARDS);
    }
}
