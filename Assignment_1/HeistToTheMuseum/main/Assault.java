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
        AssaultParty aParty1;
        AssaultParty aParty2;
        ConcentrationSite conSite;
        ControlAndCollectionSite ccSite;
        Museum museum;
        int [] roomsDist = new int [SimulationParameters.N];
        int [] roomsPaints = new int [SimulationParameters.N];
    	int [] availableRooms = new int [SimulationParameters.N];
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
        for (int i = 0; i < SimulationParameters.N; i++) {
        	roomsDist[i] = (int)(Math.random() * 30) + 15;
        	roomsPaints[i] = (int)(Math.random() * 16) + 8;
        	availableRooms[i] = 1;

        }
        
        conSite = new ConcentrationSite (SimulationParameters.M,SimulationParameters.K,availableRooms);
        ccSite = new ControlAndCollectionSite ();
        aParty1 = new AssaultParty (0,roomsDist);
        aParty2 = new AssaultParty (1,roomsDist);
        museum = new Museum (roomsPaints);
        
        //initialisation of master
        master = new MasterThief ("Master", 1, ccSite, conSite);
        System.out.println("Master Created");
        
        //initialisation of thieves
        for (int i = 0; i < SimulationParameters.M; i++){
        	if (i<3){
        		thief[i] = new OrdinaryThief ("Thief_" + (i+1),i,conSite, aParty1 , museum ,ccSite);
            	System.out.println("Thief_" + i + "Created");
        	}else {
        		thief[i] = new OrdinaryThief ("Thief_" + (i+1),i,conSite, aParty2 , museum ,ccSite);
        		System.out.println("Thief_" + i + "Created");
        	}
        }
        
        System.out.println("Start of simulation.");
        
        master.start();
        System.out.println("Master Started");
        for (int i = 0; i < SimulationParameters.M;i++) {
        	thief[i].start();
        	System.out.println("Thief_"+ i +" Started");
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
        }
        catch (InterruptedException e) {}
        //GenericIO.writelnString ("The barber " + (i+1) + " has terminated.");#
        System.out.println("Master has been terminated");
        }
        //GenericIO.writelnString ();
}
    