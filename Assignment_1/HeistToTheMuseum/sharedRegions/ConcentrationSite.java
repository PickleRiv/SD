package sharedRegions;

import entities.*;

public class ConcentrationSite {
    
    public synchronized boolean amINeeded(){
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CONCENTRATION_SITE);
        return true;
    }

    public synchronized void prepareExcursion(){
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CRAWLING_INWARDS);
    }

    public synchronized void sumUpResults(){
        
    }
}
