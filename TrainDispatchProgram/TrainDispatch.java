import java.io.IOException;

/**
 * Contains the main method for entire program
 * 
 * @author Charles Johnson
 * @author Joshua Jennings
 * @author Zachery Hibbard
 */
public class TrainDispatch {
	public static void main(String[] args) throws NumberFormatException, IOException {
		java.util.Scanner scan = new java.util.Scanner(System.in);

		System.out.println("Please enter the file location for input files:");
		String directory = scan.next();
		String graphFilePath = directory + "StationInput.txt";
		String trainsFilePath = directory + "Trains.txt";
		String cssFilePath = "url('file:///" + directory + "nodeBuilder.css')";
		System.out.println("Would you like to run the Base case? (Y/N)");
		boolean isBaseCase = scan.next().toLowerCase().equals("y");

		if (isBaseCase) {
			System.out.println("Base Case: ");
		}
		scan.close();

		Station[] stations = new Station[GraphInput.getNumOfStations(graphFilePath)];
		Track[] tracks = GraphInput.populateTracks(stations, graphFilePath, isBaseCase);

		Train[] trains = GraphInput.populateTrains(trainsFilePath, isBaseCase);

		GraphSearch.fullSearch(trains, tracks, stations, isBaseCase);
		GraphDisplay.generateView(stations, tracks, trains, cssFilePath);
	}
}
