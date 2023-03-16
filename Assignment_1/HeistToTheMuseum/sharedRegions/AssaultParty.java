package sharedRegions;

import entities.*;

public class AssaultParty {
    
    public synchronized void crawlIn(){
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CRAWLING_INWARDS);
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.AT_A_ROOM);

    }    

    public static int sendAssaultParty(){
        return 0;
    }    

    public synchronized void CrawlOut(){
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CRAWLING_OUTWARDS);
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.COLLECTION_SITE);
    }    

    public synchronized void reverseDirection(){
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CRAWLING_OUTWARDS);
    } 
}
