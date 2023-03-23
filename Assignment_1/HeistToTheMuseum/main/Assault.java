package main;

import entities.*;
import sharedRegions.*;

/**
 * Simulation of the Museum Heist
 */
public class Assault {
    public static void main(String[] args){
        MasterThief master;
        OrdinaryThief [] thief = new OrdinaryThief[SimulationParameters.M];
        // AssaultParty [] ap = new AssaultParty[SimulationParameters.G];
        AssaultParty aParty;
        ConcentrationSite conSite;
        ControlAndCollectionSite ccSite;
        Museum museum;
        GeneralRepos repos;
        char opt;                                            // selected option
        boolean success;                                     // end of operation flag
        int nIter;
        String fileName;
        
//        /* problem initialization */
//        GenericIO.writelnString ("\n" + "      Problem of the Sleeping Barbers\n");
//        GenericIO.writeString ("Number of iterations of the customer life cycle? ");
//        nIter = GenericIO.readlnInt ();
//        do
//        { GenericIO.writeString ("Logging file name? ");
//          fileName = GenericIO.readlnString ();
//          if (FileOp.exists (".", fileName))
//             { do
//               { GenericIO.writeString ("There is already a file with this name. Delete it (y - yes; n - no)? ");
//                 opt = GenericIO.readlnChar ();
//               } while ((opt != 'y') && (opt != 'n'));
//               if (opt == 'y')
//                  success = true;
//                  else success = false;
//             }
//             else success = true;
//        } while (!success);
//        repos = new GeneralRepos (fileName, nIter);
//        bShop = new BarberShop (repos);
        conSite = new ConcentrationSite ();
        ccSite = new ControlAndCollectionSite ();
        aParty = new AssaultParty ();
        museum = new Museum ();
        
        //initialisation of master
        master = new MasterThief ("Master", 1, ccSite, aParty, conSite);
        System.out.println("Master Created");
        
        //initialisation of thieves
        for (int i = 0; i < SimulationParameters.M; i++){
        	thief[i] = new OrdinaryThief ("Thief_" + (i+1),i,conSite, aParty, museum ,ccSite);
        	System.out.println("Thief_" + i + "Created");
        }

        
        System.out.println("Start of simulation.");
        
        master.start();
        System.out.println("Master Started");
        for (int i = 0; i < SimulationParameters.M;i++) {
        	thief[i].start();
        	System.out.println("Thief"+ i +"Started");
        }
        
        /* waiting for the end of the simulation */
        
        //GenericIO.writelnString ();
        for (int i = 0; i < SimulationParameters.M; i++){ 
        	try{
        		thief[i].join ();
        		System.out.println("Thief_" + i + " terminated");
        	}
        	catch (InterruptedException e) {}
          //GenericIO.writelnString ("The customer " + (i+1) + " has terminated.");
        }
        
        //GenericIO.writelnString ();
        while (master.isAlive ()){
        	master.interrupt ();
            Thread.yield ();
        }
        try{
        	master.join ();
        	System.out.println("Master has been terminated");
        }
        catch (InterruptedException e) {}
        //GenericIO.writelnString ("The barber " + (i+1) + " has terminated.");#
        System.out.println("Master has been terminated");
        }
        //GenericIO.writelnString ();
}
    