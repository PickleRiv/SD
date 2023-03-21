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

    public void setMasterID (int id){
        masterID = id;
    }

    public int getMasterID(){
        return masterID;
    }

    public void setMasterState(int state){
        masterState = state;
    }

    public int getMasterState(){
        return masterState;
    }
    
    @Override
    public void run(){

        ccSite.startOperations();

        while(true){
            int i = 0;
            if (i == 1){ //  se os ladroes tiverem todos no ConSite e salas todas vazias
                conSite.sumUpResults();
                break;
            }
            else if (i == 20000){ // se arrays ou fifo cheios???
                aParty.prepareAssaultParty();
                aParty.sendAssaultParty();
            }
            else{
                ccSite.takeARest();
                ccSite.collectACanvas();
            }

        }
    }
}