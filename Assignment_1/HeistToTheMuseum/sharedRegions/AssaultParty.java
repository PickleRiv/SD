package sharedRegions;

import entities.*;

public class AssaultParty {
    
<<<<<<< HEAD
    public synchronized void crawlIn(){
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CRAWLING_INWARDS);
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.AT_A_ROOM);

=======
    public synchronized int crawlIn(){
        return 0;
>>>>>>> ff1122621875153113e35aab030ec45b969110d3
    }    

    public synchronized int sendAssaultParty(){
        return 0;
    }    

<<<<<<< HEAD
    public synchronized void CrawlOut(){
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CRAWLING_OUTWARDS);
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.COLLECTION_SITE);
    }    

    public synchronized void reverseDirection(){
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CRAWLING_OUTWARDS);
=======
    public synchronized int CrawlOut(){
        return 0;
    }    

    public synchronized int reverseDirection(){
        return 0;
>>>>>>> ff1122621875153113e35aab030ec45b969110d3
    } 
}
