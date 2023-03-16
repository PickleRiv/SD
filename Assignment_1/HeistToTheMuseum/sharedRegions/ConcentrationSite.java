package sharedRegions;

import entities.*;

public class ConcentrationSite {
    
<<<<<<< HEAD
    public synchronized boolean amINeeded(){
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CONCENTRATION_SITE);
        return true;
    }

    public synchronized void prepareAssaultParty(){
    }

    public synchronized void prepareExcursion(){
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CRAWLING_INWARDS);
=======
    public synchronized int appraiseSit(){
        return 0;
    }

    public synchronized boolean amINeeded(){
        return true;
    }

    public synchronized int prepareAssaultParty(){
        return 0;
    }

    public synchronized int prepareExcursion(){
        return 0;
>>>>>>> ff1122621875153113e35aab030ec45b969110d3
    }

    public synchronized void sumUpResults(){
        
    }
}
