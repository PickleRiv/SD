package sharedRegions;

import entities.*;

public class ConcentrationSite {
    
    public synchronized boolean amINeeded(){
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CONCENTRATION_SITE);
        return true;
    }

    public synchronized void prepareAssaultParty(){
    }

    public synchronized void prepareExcursion(){
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CRAWLING_INWARDS);
    }

    public static int sumUpRecords(){
        return 0;
    }
}
