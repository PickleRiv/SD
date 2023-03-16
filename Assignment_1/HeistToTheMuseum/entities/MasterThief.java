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
        do{

        }while(true)
    }
}
  /**
   *   Life cycle of the barber.
   */

   @Override
   public void run ()
   {
      int customerId;                                      // customer id
      boolean endOp;                                       // flag signaling end of operations

      while (true)
      { endOp = bShop.goToSleep ();                        // the barber sleeps while waiting for a customer to service
        if (endOp) break;                                  // check for end of operations
        customerId = bShop.callACustomer ();               // the barber has waken up and calls next customer
        cutHair ();                                        // the barber cuts the customer hair
        bShop.receivePayment (customerId);                 // the barber finishes his service and receives payment for it
      }
   }

  /**
   *  Cutting the customer hair.
   *
   *  Internal operation.
   */

   private void cutHair ()
   {
      try
      { sleep ((long) (1 + 100 * Math.random ()));
      }
      catch (InterruptedException e) {}
   }
}
