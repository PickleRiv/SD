package sharedRegions;

import main.*;
import entities.*;
import genclass.GenericIO;
import genclass.TextFile;
import java.util.Objects;

// /**
//  *  General Repository.
//  * 
//  *    It is responsible to keep the visible internal state of the problem and to provide means for it
//  *    to be printed in the logging file.
//  *    It is implemented as an implicit monitor.
//  *    All public methods are executed in mutual exclusion.
//  *    There are no internal synchronization points.
//  */

public class GeneralRepos
{
  /**
   *  Name of the logging file.
   */

   private final String logFileName;

  /**
   *  State of the master thief
   */

   private int masterState;

  /**
   *  State of the thieves.
   */

   private final int [] thiefState;
   
   /**
    *  'W' (waiting to join a party) or 'P' (in party)
    */
   
   private char thiefSituation;
   
   
  /**
   *   Instantiation of a general repository object.
   *
   *     @param logFileName name of the logging file
   *     @param nIter number of iterations of the customer life cycle
   */

   public GeneralRepos (String logFileName)
   {
      if ((logFileName == null) || Objects.equals (logFileName, ""))
         this.logFileName = "logger";
         else this.logFileName = logFileName;
    
      masterState = MasterThiefStates.PLANNING_THE_HEIST;
      
      thiefState = new int [SimulationParameters.M];
      for (int i = 0; i < SimulationParameters.M; i++)
        thiefState[i] = OrdinaryThiefStates.CONCENTRATION_SITE;
      reportInitialStatus ();
      
   }
   
   public synchronized void setThiefSituation(boolean Situation) {
	   if (Situation == true) thiefSituation = 'P';
	   else thiefSituation = 'W';
	   reportStatus();
   }
   
   

  /**
   *   Set master state.
   *
   *     @param id master id
   *     @param state master state
   */

   public synchronized void setMasterState (int state)
   {
      masterState = state;
      reportStatus ();
   }

  /**
   *   Set customer state.
   *
   *     @param id ordinary thief id
   *     @param state ordinary thief state
   */

   public synchronized void setThiefState (int id, int state)
   {
	  thiefState[id] = state;
      reportStatus ();
   }

  /**
   *   Set Master and OrdinaryThief state.
   *customerState
   *     @param masterState master state
   *     @param cId customer id
   *     @param thiefState ordinary thief state
   */

   public synchronized void setMasterOrdinaryState (int masterState, int thiefID, int thiefState)
   {
      this.masterState = masterState;
      this.thiefState[thiefID] = thiefState;
      reportStatus ();
   }

  /**
   *  Write the header to the logging file.
   */

   private void reportInitialStatus ()
   {
      TextFile log = new TextFile ();                      // instantiation of a text file handler

      if (!log.openForWriting (".", logFileName))
         { GenericIO.writelnString ("The operation of creating the file " + logFileName + " failed!");
           System.exit (1);
         }
      log.writelnString("Heist to the Museum - Description of the internal state\n");
      log.writelnString("MstT         Thief 1         Thief 2         Thief 3         Thief 4         Thief 5         Thief 6\n");
      log.writelnString("Stat        Stat S MD       Stat S MD       Stat S MD       Stat S MD       Stat S MD        Stat S MD\n");
      log.writelnString("                               Assault party 1                                 Assault party 2                                 Museum\n");
      log.writelnString("                 Elem 1           Elem 2           Elem 3           Elem 1           Elem 2           Elem 3        Room 1      Room 2      Room 3      Room 4      Room 5");
      log.writelnString("  RId			Id Pos Cv         Id Pos Cv 	   Id Pos Cv        Id Pos Cv        Id Pos Cv        Id Pos Cv      NP DT       NP DT      NP DT      NP DT  ");
           
      if (!log.close ())
         { GenericIO.writelnString ("The operation of closing the file " + logFileName + " failed!");
           System.exit (1);
         }
      reportStatus ();
   }

  /**
   *  Write a state line at the end of the logging file.
   *
   *  The current state of the barbers and the customers is organized in a line to be printed.
   *  Internal operation.
   */

   private void reportStatus ()
   {
      TextFile log = new TextFile ();                      // instantiation of a text file handler

      String lineStatus = "";                              // state line to be printed

      if (!log.openForAppending (".", logFileName))
         { GenericIO.writelnString ("The operation of opening for appending the file " + logFileName + " failed!");
           System.exit (1);
         }
     
        switch (masterState)
        { case MasterThiefStates.PLANNING_THE_HEIST:   lineStatus += " PLANNING_THE_HEIST ";
                                        break;
          case MasterThiefStates.DECIDING_WHAT_TO_DO: lineStatus += " DECIDING_WHAT_TO_DO ";
                                        break;
                                        
          case MasterThiefStates.ASSEMBLING_A_GROUP: lineStatus += " ASSEMBLING_A_GROUP ";
          								break;
          
          case MasterThiefStates.WAITING_FOR_ARRIVAL: lineStatus += " WAITING_FOR_ARRIVAL ";
          								break;
          
          case MasterThiefStates.PRESENTING_THE_REPORT: lineStatus += " PRESENTING_THE_REPORT ";
          								break;
        }
        
      for (int i = 0; i < SimulationParameters.M; i++)
        switch (thiefState[i])
        { case OrdinaryThiefStates.CONCENTRATION_SITE:  lineStatus += " CONCENTRATION_SITE ";
                                             break;
          case OrdinaryThiefStates.CRAWLING_INWARDS: lineStatus += " CRAWLING_INWARDS ";
                                             break;
          case OrdinaryThiefStates.AT_A_ROOM:      lineStatus += " AT_A_ROOM ";
                                             break;
          case OrdinaryThiefStates.CRAWLING_OUTWARDS:    lineStatus += " CRAWLING_OUTWARDS ";
                                             break;
          case OrdinaryThiefStates.COLLECTION_SITE:    lineStatus += " COLLECTION_SITE ";
          									break;
        }
      
      log.writelnString (lineStatus);
      if (!log.close ())
         { GenericIO.writelnString ("The operation of closing the file " + logFileName + " failed!");
           System.exit (1);
         }
   }
   
   public void reportLegend()
	{
		TextFile log = new TextFile ();                  	// instantiation of a text file handler
		String line = "";                              		// state line to be printed
		if (!log.openForAppending (".", logFileName))
		{ 
			GenericIO.writelnString ("The operation of opening for appending the file " + logFileName + " failed!");
			System.exit (1);		
		}
		log.writelnString("\nLegend:\nMstT Stat – state of the master thief\nThief # Stat - state of the ordinary thief # (# - 1 .. 6)\n");
		log.writelnString("Thief # S – situation of the ordinary thief # (# - 1 .. 6) either 'W' (waiting to join a party) or 'P' (in party)\n");
		log.writelnString("Thief # MD – maximum displacement of the ordinary thief # (# - 1 .. 6) a random number between 2 and 6");
		log.writelnString("Assault party # RId – assault party # (# - 1,2) elem # (# - 1 .. 3) room identification (1 .. 5)\n");
		log.writelnString("Assault party # Elem # Id – assault party # (# - 1,2) elem # (# - 1 .. 3) member identification (1 .. 6)\n");
		log.writelnString("ssault party # Elem # Pos – assault party # (# - 1,2) elem # (# - 1 .. 3) present position (0 .. DT RId)\n");
		log.writelnString("Assault party # Elem # Cv – assault party # (# - 1,2) elem # (# - 1 .. 3) carrying a canvas (0,1)");
		log.writelnString("Museum Room # NP - room identification (1 .. 5) number of paintings presently hanging on the walls\n");
		log.writelnString("Museum Room # DT - room identification (1 .. 5) distance from outside gathering site, a random number between 15 and 30");
		
		if (!log.close ())
		{ 
			GenericIO.writelnString ("The operation of closing the file " + logFileName + " failed!");
			System.exit (1);
		}
	}

   
}