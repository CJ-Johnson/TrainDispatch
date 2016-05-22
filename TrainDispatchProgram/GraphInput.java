import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

/**
 * Reads in all information from input text files and instantiates all needed
 * objects for calculation.
 * 
 * @author Charles Johnson
 * @author Joshua Jennings
 * @author Zachery Hibbard
 */
public class GraphInput {
   private static final int NULL_TRAIN_ID = -1;
   private static int numOfTracks;
   private static int numOfTrains;

   /**
    * Returns number of Tracks
    * 
    * @return
    */
   public static int getNumOfTracks() {
      return numOfTracks;
   }

   /**
    * Returns number of Trains
    * 
    * @return
    */
   public static int getNumOfTrains() {
      return numOfTrains;
   }

   /**
    * Reads number of stations from text file and returns that value
    * 
    * @param path
    *           to StationInput.txt
    * @return the first line (total number of Stations in file)
    */
   public static int getNumOfStations(String graphFilePath)
         throws NumberFormatException, IOException {
      final BufferedReader stationReader = new BufferedReader(new FileReader(
            graphFilePath));
      int numOfStations = Integer.parseInt(stationReader.readLine());
      stationReader.close();
      return numOfStations;
   }

   /**
    * Reads Station input file and stores data in corresponding structures
    * 
    * @param stations
    *           array will be filled with data
    * @param filePath
    * @return
    */
   public static Track[] populateTracks(Station[] stations, String filePath, boolean isBaseCase)
         throws NumberFormatException, IOException {
	   numOfTracks = numOfTrains = 0;
      final BufferedReader stationReader = new BufferedReader(new FileReader(
            filePath));
      stationReader.readLine();
      for (int i = 0; i < stations.length; ++i) {
         stations[i] = new Station(i);
      }
      Stack<String> stack = new Stack<String>();
      for (;;) {
         String line = stationReader.readLine();
         if (line == null)
            break;
         stack.push(line);
         ++numOfTracks;
      }
      Track[] tracks = new Track[numOfTracks];
      for (int i = numOfTracks - 1; i >= 0; --i) {
         Scanner scan = new Scanner(stack.pop());
         int stationID1 = scan.nextInt();
         int stationID2 = scan.nextInt();
         int weight = scan.nextInt();

         scan.close();
         tracks[i] = new Track(i, weight, stationID1, stationID2);
         stations[stationID1].trackIDList.add(i);
         stations[stationID2].trackIDList.add(i);
      }
      stationReader.close();
      if (!isBaseCase){
    	  for(int i = 0; i < stations.length; ++i) {
    		  int tempLen = stations[i].trackIDList.size();
    		  Track[] tempTracks = new Track[tempLen];
    		  for(int j = 0; j < tempLen; ++j) {
    			  int trackID = stations[i].trackIDList.get(j);
    			  tempTracks[j] = tracks[trackID];
    		  }
    		  Arrays.sort(tempTracks);
    		  for(int j = 0; j < tempLen; ++j) {
    			  stations[i].trackIDList.set(j, tempTracks[j].ID);
    		  }
    	  }
      }
      return tracks;
   }

   /**
    * Reads Train input file and stores data in corresponding structures
    * 
    * @param filePath
    * @return
    * @throws NumberFormatException
    * @throws IOException
    */
   public static Train[] populateTrains(String filePath, boolean isBaseCase)
         throws NumberFormatException, IOException {
      final BufferedReader scheduleReader = new BufferedReader(new FileReader(
            filePath));
      numOfTrains = Integer.parseInt(scheduleReader.readLine());
      Train[] trains = new Train[numOfTrains];
      for (int i = 0; i < numOfTrains; ++i) {
         Scanner scan = new Scanner(scheduleReader.readLine());
         int originID = scan.nextInt();
         int destinationID = scan.nextInt();
         int scheduleTime = scan.nextInt();
         int priority = scan.nextInt();
         trains[i] = new Train(NULL_TRAIN_ID, originID, destinationID, scheduleTime,
               priority, new Path());
         scan.close();
      }
      scheduleReader.close();
      if(!isBaseCase) {
    	  Arrays.sort(trains);
      }
      for(int i = 0; i < trains.length; ++i) {
    	  trains[i].ID = i;
      }
      return trains;
   }
}
