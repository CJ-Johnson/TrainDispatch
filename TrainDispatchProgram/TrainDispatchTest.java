import java.io.IOException;
/**
 * Test Driver
 * 
 * @author Charles Johnson
 * @author Joshua Jennings
 * @author Zachery Hibbard
 */
public class TrainDispatchTest {
    /**
     * Prints total and average times for each Track test case
     * 
     * @param isBaseCase
     * @throws NumberFormatException
     * @throws IOException
     */
    public static void trackTest(boolean isBaseCase, String directory)
            throws NumberFormatException, IOException {
    	System.out.println("Connect");
        for (int index = 0; index < 100; ++index) {
            String graphFilePath = directory + "testTrack" + index + ".txt";
            String trainsFilePath = directory + "trainTester0.txt";
            Station[] stations = new Station[GraphInput.getNumOfStations(graphFilePath)];
            Track[] tracks = GraphInput.populateTracks(stations, graphFilePath, isBaseCase);
            Train[] trains = GraphInput.populateTrains(trainsFilePath, isBaseCase);
            GraphSearch.fullSearch(trains, tracks, stations, isBaseCase);
            int averageTravelTime = 0;
            for (int i = 0; i < trains.length; ++i) {
                int last = trains[i].bestPath.list.length - 1;
                averageTravelTime += trains[i].bestPath.list[last].arriveTime;
            }
            int longestOverallTime = 0;
            for (int i = 0; i < trains.length; ++i) {
                longestOverallTime = Math.max(longestOverallTime,
                        trains[i].finalArriveTime);
            }
            System.out.println(GraphInput.getNumOfTracks()
                    + "\t" + longestOverallTime + "\t"
                    + (averageTravelTime / trains.length));
        }
    }

    /**
     * Prints total and average times for each Train test case
     * 
     * @param isBaseCase
     * @throws NumberFormatException
     * @throws IOException
     */
    public static void trainTest(boolean isBaseCase, String directory)
            throws NumberFormatException, IOException {
    	System.out.println("Train");
        int index = 0;
        while (index < 100) {
            String graphFilePath = directory + "testTrack0.txt";
            String trainsFilePath = directory + "trainTester" + index + ".txt";
            index++;
            Station[] stations = new Station[GraphInput
                    .getNumOfStations(graphFilePath)];
            Track[] tracks = GraphInput.populateTracks(stations, graphFilePath, isBaseCase);
            Train[] trains = GraphInput.populateTrains(trainsFilePath, isBaseCase);

            GraphSearch.fullSearch(trains, tracks, stations, isBaseCase);
            int averageTravelTime = 0;
            for (int i = 0; i < trains.length; ++i) {
                int last = trains[i].bestPath.list.length - 1;
                averageTravelTime += trains[i].bestPath.list[last].arriveTime;
            }
            int longestOverallTime = 0;
            for (int i = 0; i < trains.length; ++i) {
                longestOverallTime = Math.max(longestOverallTime,
                        trains[i].finalArriveTime);
            }
            System.out.println(GraphInput.getNumOfTrains()
                    + "\t" + longestOverallTime + "\t"
                    + (averageTravelTime / trains.length));
        }
    }
    
    /**
     * Prints randomized cases for standard deviation testing
     * 
     * @param isBaseCase
     * @throws NumberFormatException
     * @throws IOException
     */
    public static void stdDevTest(boolean isBaseCase, String directory) throws NumberFormatException, IOException {
    	for(int i = 0; i < 100; ++i) {
    			String graphFilePath = directory + "testTrack" + i + ".txt";
                String trainsFilePath = directory + "trainTester" + i + ".txt";
                Station[] stations = new Station[GraphInput
                        .getNumOfStations(graphFilePath)];
                Track[] tracks = GraphInput.populateTracks(stations, graphFilePath, isBaseCase);
                Train[] trains = GraphInput.populateTrains(trainsFilePath, isBaseCase);

                GraphSearch.fullSearch(trains, tracks, stations, isBaseCase);
                int averageTravelTime = 0;
                for (int k = 0; k < trains.length; ++k) {
                    int last = trains[k].bestPath.list.length - 1;
                    averageTravelTime += trains[k].bestPath.list[last].arriveTime;
                }
                int longestOverallTime = 0;
                for (int k = 0; k < trains.length; ++k) {
                    longestOverallTime = Math.max(longestOverallTime,
                            trains[k].finalArriveTime);
                }
                System.out.println(GraphInput.getNumOfTrains()
                        + "\t" + longestOverallTime + "\t"
                        + (averageTravelTime / trains.length));
    	}
    }

    /**
     * Alternate Main method for test purposes
     * 
     * @param args
     * @throws NumberFormatException
     * @throws IOException
     */
    public static void main(String[] args) throws NumberFormatException,
            IOException {
    	java.util.Scanner scan = new java.util.Scanner(System.in);
    	System.out.println("Enter number of stations:");
    	int stations = scan.nextInt();
    	System.out.println("Enter number of tracks:");
    	int tracks = scan.nextInt();
    	System.out.println("Enter number of trains:");
    	int trains = scan.nextInt();
    	int files = 100;
    	System.out.println("Enter file path for files to be stored:");
    	String directory = scan.next();
    	TestFilePrinter test = new TestFilePrinter(stations, tracks, trains, files);
    	test.printFiles(directory);
    	scan.close();    	
    	
        System.out.println("Track Test Main:");
        trackTest(false, directory);
        System.out.println();
        System.out.println("Track Test Base:");
        trackTest(true, directory);
        System.out.println();
        System.out.println("Train Test Main:");
        trainTest(false, directory);
        System.out.println();
        System.out.println("Train Test Base:");
        trainTest(true, directory);
        System.out.println("Std Test Main");
        stdDevTest(false, directory);
        System.out.println("Std Test base");
        stdDevTest(true, directory);
        System.out.println("Done");
    }
}
