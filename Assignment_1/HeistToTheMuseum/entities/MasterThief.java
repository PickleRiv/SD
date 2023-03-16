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

        ccSite.startOperations();

        while(true){
            int i = 0;
            if (i == 1){ //  se os ladroes tiverem todos no ConSite e salas todas vazias
                conSite.sumUpResults();
                break;
            }
            else if (i == 20000){ // se arrays ou fifo cheios???
                conSite.prepareAssaultParty();
                aParty.sendAssaultParty();
            }
            else{
                ccSite.takeARest();
                ccSite.collectACanvas();
            }

        }
    }
}