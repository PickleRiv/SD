package sharedRegions;

import entities.*;
import main.*;

public class ControlAndCollectionSite {
    //private final GeneralRepos repos;
    
    public synchronized void startOperations(){
        ((MasterThief) Thread.currentThread()).setMasterState(MasterThiefStates.DECIDING_WHAT_TO_DO);
        //repos.setMasterState(((MasterThief) Thread.currentThread()).getMasterState());

        try{
            wait();
        } catch(InterruptedException e){
            e.printStackTrace();
        }

    }

    public static int takeARest(int handACanvas){
        return 0;
    }    

    public static int handACanvas(int collectACanvas){
        return 0;
    }    

    public static int collectACanvas(){
        return 0;
    }

}
