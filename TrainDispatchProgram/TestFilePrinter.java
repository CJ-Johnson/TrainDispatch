import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.BitSet;
import java.util.Random;
/**
 * Prints test files for data collection
 * 
 * @author Charles Johnson
 * @author Joshua Jennings
 * @author Zachery Hibbard
 */
public class TestFilePrinter {
	int stationNum;
	int trackNum;
	int trainNum;
	int fileNum;
	/**
	 * Constructor that takes in test file parameters
	 * 
	 * @param stations
	 * @param tracks
	 * @param trains
	 * @param files
	 */
	public TestFilePrinter(int stations, int tracks, int trains, int files) {
		this.stationNum = stations;
		this.trackNum = tracks;
		this.trainNum = trains;
		this.fileNum = files;
	}
	/**
	 * Prints select number of train and track input files with given values
	 * 
	 * @param directory
	 * @throws IOException
	 */
	public void printFiles (String directory) throws IOException {
		Random rng = new Random(1);
		int newTrainNum = trainNum;
		for (int i = 0; i < fileNum; ++i) {
			String location = directory + "trainTester" + i + ".txt";
			BufferedWriter outWrite = new BufferedWriter (new FileWriter(location));
			outWrite.write(newTrainNum + "\n");
			for (int j = 0; j < newTrainNum; ++j) {
				int a = rng.nextInt(stationNum);
				int b = rng.nextInt(stationNum);
				while (a == b) {
					b = rng.nextInt(stationNum);
				}
				String line = a + " " + b + " 0 0";
				if (j != newTrainNum - 1) {
					line += "\n";
				}
				outWrite.write(line);
			}
			outWrite.close();
		}
		int newTrackNum = trackNum;
		for (int i = 0; i < fileNum; ++i) {
			String location = directory + "testTrack" + i + ".txt";
			BufferedWriter outWrite = new BufferedWriter (new FileWriter(location));
			outWrite.write(newTrackNum + "\n");

			BitSet connectedTracks = new BitSet(stationNum);
			int lastStationID = rng.nextInt(stationNum);
			connectedTracks.set(lastStationID);
			for(int j = 1; j < 25; ++j) {
				int nextStationID;
				do {
					nextStationID = rng.nextInt(stationNum);
				} while(connectedTracks.get(nextStationID));
				connectedTracks.set(nextStationID);
				outWrite.write(lastStationID + " " + nextStationID + " " + (rng.nextInt(30) + 1) + "\n");
				lastStationID = nextStationID;
			}
			for (int j = 24; j < newTrackNum; ++j) {
				int a = rng.nextInt(stationNum);			
				int b = rng.nextInt(stationNum);
				while (a == b) {
					b = rng.nextInt(stationNum);
				}				
				int weight = rng.nextInt(30) + 1;
				String line = a + " " + b + " " + weight;
				
				if (j != newTrackNum - 1) {
					line += "\n";
				}
				outWrite.write(line);
			}
			outWrite.close();
		}
	}
}
