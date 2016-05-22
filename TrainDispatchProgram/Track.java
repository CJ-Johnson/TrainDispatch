
/**
 * Stores data for each edge (Track) in the graph
 * 
 * @author Charles Johnson
 * @author Joshua Jennings
 * @author Zachery Hibbard
 */
public class Track implements Comparable<Track>{
   public int ID;
   public int weight;
   public Interval interval;
   private int stationIDs;

   /**
    * Returns connecting Station from Station parameter
    * 
    * @param curID
    * @return
    */
   public int getOtherStationID(int curID) {
      return stationIDs ^ curID;
   }

   /**
    * Constructor that assigns two Stations, a weight and a unique Station ID
    * 
    * @param ID
    * @param weight
    * @param stationID1
    * @param stationID2
    */
   public Track(int ID, int weight, int stationID1, int stationID2) {
      this.ID = ID;
      this.weight = weight;
      this.interval = new Interval(weight);
      this.stationIDs = stationID1 ^ stationID2;
   }

   @Override
   public int compareTo(Track t) {
	   return this.weight - t.weight;
   }
}
