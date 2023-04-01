package entities;

import sharedRegions.*;

public class MasterThief extends Thread {
    private int masterID;							//	Master Thief Identification
    private int masterState;						//	Master Thief State 
    private final ControlAndCollectionSite ccSite;	//	Reference to the Collection Site
    private final ConcentrationSite conSite;		//	Reference to the Concentration Site
    
    /**
     *   Instantiation of a barber thread.
     *
     *     @param name thread name
     *     @param masterID master thief id
     *     @param ccSite Reference to the Collection Site
     */
    public MasterThief(String name, int masterID, ControlAndCollectionSite ccSite,ConcentrationSite conSite){
        super(name);
    	this.masterID = masterID;
        masterState = MasterThiefStates.PLANNING_THE_HEIST;
        this.ccSite = ccSite;
        this.conSite = conSite;
    }
    
    public void setMasterID(int ID){
        masterID = ID;
    }
    
    public int getMasterID(){
        return masterID;
    }
    
    /**
	 * 
	 * @param masterState new state to be set
	 */
    public void setMasterState(int state){
        masterState = state;
    }

    /**
	 * 	@return masterState
	 */
    public int getMasterState(){
        return masterState;
    }
    
    @Override
    public void run(){
		ccSite.startOperations();                       					// state changes from planning the heist

    	while(true) {
            switch(masterState) {
            case 1:                                         				// Deciding what to do
        		if (!conSite.isSent()) {
                	conSite.appraiseSit();									// state changes to deciding what to do
        		}
            	if (conSite.partyFullIndex()!=-1) {
            		conSite.prepareAssaultParty();							// state changes to Assembling a group
            	} 
            	// if parties sent >2 && rooms != empty:
            	if(!conSite.isLooted() && conSite.isSent() && conSite.partyFullIndex()==-1) 
            		ccSite.takeARest();
            	if(conSite.isLooted()) 
            		ccSite.sumUpResults();
            	break;
            case 2:                                         				// Assembling a group
            	conSite.sendAssaultParty();
            	break;
            case 3:                                         				// waitting for arrival
            	//if party == arrived:
            	ccSite.collectACanvas();
            	break;
            case 4:															// Presenting the report
            	//print out the end of the loop
            	break;
          }
    	}
        	
    }
}
