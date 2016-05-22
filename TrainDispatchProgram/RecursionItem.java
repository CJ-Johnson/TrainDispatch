/**
 * Recursion class's equivalent to a call stack's stack pointer
 * 
 * @author Charles Johnson
 * @author Joshua Jennings
 * @author Zachery Hibbard
 */
public class RecursionItem {
   public int stationID;
   public int trackID;
   public int arriveTime;
   public int leaveTime;
   public int index;

   /**
    * Constructor that stores all relevant traversal information
    * 
    * @param stationID
    * @param trackID
    * @param arriveTime
    * @param leaveTime
    * @param index
    */
   public RecursionItem(int stationID, int trackID, int arriveTime,
         int leaveTime, int index) {
      this.stationID = stationID;
      this.trackID = trackID;
      this.arriveTime = arriveTime;
      this.leaveTime = leaveTime;
      this.index = index;
   }
}
