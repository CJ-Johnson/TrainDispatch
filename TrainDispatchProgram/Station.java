import java.util.ArrayList;
/**
 * Stores data on each node (Station) in the graph
 * 
 * @author Charles Johnson
 * @author Joshua Jennings
 * @author Zachery Hibbard
 */
public class Station {
   public int ID;
   public ArrayList<Integer> trackIDList;
   /**
    * Constructor that assigns a unique Station Id
    * 
    * Also creates a new list to store neighboring Stations
    * 
    * @param ID
    */
   public Station(int ID) {
      this.ID = ID;
      this.trackIDList = new ArrayList<Integer>();
   }
}
