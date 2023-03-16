package entities;

import sharedRegions.*;

public class MasterThief extends Thread {
    private int masterID;
    private int masterState;
    private final ControlAndCollectionSite ccSite;
    private final AssaultParty aParty;
    private final ConcentrationSite conSite;

    public MasterThief(int masterID, ControlAndCollectionSite ccSite){
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
        while(true){
            if (true)
                break;
        }
    }
}
