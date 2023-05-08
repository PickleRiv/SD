package clientSide.main;

import serverSide.main.*;
import clientSide.stubs.*;
import clientSide.entities.*;
import genclass.GenericIO;

/**
 * Client side of the Heist to the Museum problem (ordinary thief).
 *
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class ClientThief {
	/**
	 *  Main method.
	 *
	 *    @param args runtime arguments
	 *        args[0] - name of the platform where is located the Collection Site server
	 *        args[1] - port number for listening to service requests
     *		  args[2] - name of the platform where is located the Concentration Site server
	 *        args[3] - port number for listening to service requests
	 *        args[4] - name of the platform where is located the Museum server
	 *        args[5] - port number for listening to service requests
	 *        args[6] - name of the platform where is located the Assault Party server
	 *        args[7] - port number for listening to service requests
	 *        args[8] - name of the platform where is located the General Repository server
	 *        args[9] - port number for listening to service requests
	 */
	
	//String name, int id, CocentrationStub conSite, CollectionStub ccSite, AssaultPartyStub[] parties, MuseumStub museum, GeneralReposStub repos
	public static void main() {
		OrdinaryThief[] thief = new OrdinaryThief[SimulPar.nThieves]; 	// Thief threads
		CollectionSiteStub ccSiteStub; 									// remote reference to the collection site stub
		ConcentrationSiteStub conSiteStub; 								// remote reference to the concentration stub
		MuseumStub museumStub; 											// remote reference to the museum stub
		GeneralReposStub genReposStub; 									// remote reference to the general repository stub
		AssaultPartyStub[] aPartyStub; 									// remote reference to the assault party stub
		int maxThiefDisp;
		
		// Name of the platforms where collection and concentration sites, museum 
		// and assault parties servers are located
		String ccServerHostName, conServerHostName, museumServerHostName, apServerHostName, genRepoServerHostName;
		// Port numbers for listening to service requests
		int ccServerPortNumb = -1, conServerPortNumb = -1, museumServerPortNumb = -1, apServerPortNumb = -1,
				genRepoServerPortNumb = -1;

		/* Getting problem runtime parameters */
		if (args.length != 10) {
			GenericIO.writelnString("Wrong number of parameters!");
			System.exit(1);
		}
		// Get collection parameters
		ccServerHostName = args[0];
		try {
			ccServerPortNumb = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			GenericIO.writelnString("args[1] is not a number!");
			System.exit(1);
		}
		if ((ccServerPortNumb < 22410) || (ccServerPortNumb > 22419)) {
			GenericIO.writelnString("args[1] is not a valid port number!");
			System.exit(1);
		}

		// Get concentration parameters
		conServerHostName = args[2];
		try {
			conServerPortNumb = Integer.parseInt(args[3]);
		} catch (NumberFormatException e) {
			GenericIO.writelnString("args[3] is not a number!");
			System.exit(1);
		}
		if ((conServerPortNumb < 22410) || (conServerPortNumb > 22419)) {
			GenericIO.writelnString("args[3] is not a valid port number!");
			System.exit(1);
		}

		// Get museum parameters
		museumServerHostName = args[4];
		try {
			museumServerPortNumb = Integer.parseInt(args[5]);
		} catch (NumberFormatException e) {
			GenericIO.writelnString("args[5] is not a number!");
			System.exit(1);
		}
		if ((museumServerPortNumb < 22410) || (museumServerPortNumb > 22419)) {
			GenericIO.writelnString("args[5] is not a valid port number!");
			System.exit(1);
		}

		// Get assault party parameters
		apServerHostName = args[6];
		try {
			apServerPortNumb = Integer.parseInt(args[7]);
		} catch (NumberFormatException e) {
			GenericIO.writelnString("args[7] is not a number!");
			System.exit(1);
		}
		if ((apServerPortNumb < 22410) || (apServerPortNumb > 22419)) {
			GenericIO.writelnString("args[7] is not a valid port number!");
			System.exit(1);
		}

		// Get general repo parameters
		genRepoServerHostName = args[8];
		try {
			genRepoServerPortNumb = Integer.parseInt(args[9]);
		} catch (NumberFormatException e) {
			GenericIO.writelnString("args[9] is not a number!");
			System.exit(1);
		}
		if ((genRepoServerPortNumb < 22410) || (genRepoServerPortNumb > 22419)) {
			GenericIO.writelnString("args[9] is not a valid port number!");
			System.exit(1);
		}

		/* problem initialisation */
		ccSiteStub = new ColectionStub(ccServerHostName, ccServerPortNumb);
		conSiteStub = new ConcentrationStub(conServerHostName, conServerPortNumb);
		museumStub = new MuseumStub(museumServerHostName, museumServerPortNumb);
		aPartyStub = new AssaultPartyStub(apServerHostName, apServerPortNumb);
		genReposStub = new GeneralReposStub(genRepoServerHostName, genRepoServerPortNumb);
		for (int i = 0; i < SimulPar.nThieves; i++) {
			maxThiefDisp = (int) (Math.random() * (SimulPar.maxDisp - SimulPar.minDisp)) + SimulPar.minDisp;
			thief[i] = new OrdinaryThief("Thief_", i , ccSiteStub, conSiteStub, museumStub, aPartyStub, genReposStub, maxThiefDisp);
		}

		/* start simulation */
		GenericIO.writelnString("Launching Master Thread ");
		master.start();

		/* waiting for the end of the simulation */
		try {
			master.join();
		} catch (InterruptedException e) {
		}
		GenericIO.writelnString("The master thread has terminated.");
		ccSiteStub.shutdown();
		conSiteStub.shutdown();
		museumStub.shutdown();
		aPartyStub.shutdown();
		genReposStub.shutdown();
	}
}