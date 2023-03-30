package entities;

import sharedRegions.*;

public class MasterThief extends Thread {
    private int masterID;							//	Master Thief Identification
    private int masterState;						//	Master Thief State 
    private final ControlAndCollectionSite ccSite;	//	Reference to the Collection Site
    private final AssaultParty aParty;				//	Reference to the Assault Party
    private final ConcentrationSite conSite;		//	Reference to the Concentration Site
    
    /**
     *   Instantiation of a barber thread.
     *
     *     @param name thread name
     *     @param masterID master thief id
     *     @param ccSite Reference to the Collection Site
     */
    public MasterThief(String name, int masterID, ControlAndCollectionSite ccSite, AssaultParty aParty,ConcentrationSite conSite){
        super(name);
    	this.masterID = masterID;
        masterState = MasterThiefStates.PLANNING_THE_HEIST;
        this.ccSite = ccSite;
        this.aParty = aParty;
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
    	while(true) {
            ccSite.startOperations();
            ccSite.appraiseSit();
            //conSite.
    	}
        	
    }
}


////switch(masterState) {
////case MasterThiefStates.DECIDING_WHAT_TO_DO:
////System.out.println("Master is deciding what to do");
////appraiseSit();
////break;
////case MasterThiefStates.ASSEMBLING_A_GROUP:
////}
//int i = 0;
//if (i == 1){ //  se os ladroes tiverem todos no ConSite e salas todas vazias
//
//System.out.println("oi");
//conSite.sumUpResults();
//i++;
//break;
//
//}
//else if (i == 100){ // se arrays ou fifo cheios???
//System.out.println("hello");
//
//aParty.prepareAssaultParty();
//aParty.sendAssaultParty();
//i++;
//}
//else{
//ccSite.takeARest();
//ccSite.collectACanvas();
//