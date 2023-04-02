package entities;

import main.SimulationParameters;
import sharedRegions.*;

public class MasterThief extends Thread {
    private int masterID;							//	Master Thief Identification
    private int masterState;						//	Master Thief State 
    private final ControlAndCollectionSite ccSite;	//	Reference to the Collection Site
    private final ConcentrationSite conSite;		//	Reference to the Concentration Site
    private final AssaultParty [] aParty;			//	Reference to the Concentration Site
    private int [] partiesSent;
    private final Museum museum;

    
    /**
     *   Instantiation of a barber thread.
     *
     *     @param name thread name
     *     @param masterID master thief id
     *     @param ccSite Reference to the Collection Site
     */
    public MasterThief(String name, int masterID, ControlAndCollectionSite ccSite,ConcentrationSite conSite, AssaultParty [] aParty, Museum museum){
        super(name);
    	this.masterID = masterID;
        masterState = MasterThiefStates.PLANNING_THE_HEIST;
        this.ccSite = ccSite;
        this.conSite = conSite;
        this.aParty = aParty;
        this.museum = museum;
        partiesSent = new int [SimulationParameters.G];
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
		int currentParty=-1;
		ccSite.sumUpResults();
		/*
    	while(true) {
            switch(masterState) {
            case 1:                                         				// Deciding what to do
            	if(museum.getAvailableRoom()==-2) {
        			ccSite.sumUpResults();
        			break;
        		}
            	if(conSite.isBusy() || museum.numAvail()<SimulationParameters.G) {
        			ccSite.takeARest();
        			break;
        		}
            	currentParty = conSite.appraiseSit();										// state changes to deciding what to do
            	conSite.prepareAssaultParty();								// state changes to Assembling a group
            	break;
            case 2:                                         				// Assembling a group
            	System.out.println(currentParty);
            	int availableRoom = museum.getAvailableRoom();
            	if (availableRoom != -2 || availableRoom != -1) {
                	conSite.sendAssaultParty(museum.getAvailableRoom());
                	partiesSent[currentParty]++;
                	currentParty=-1;
            	}
            	break;
            case 3:                                         				// waitting for arrival
            	//if party == arrived:
            	ccSite.collectACanvas();
            	break;
            case 4:															// Presenting the report
            	//print out the end of the loop
            	break;
          }
    	}*/
        	
    }
}
