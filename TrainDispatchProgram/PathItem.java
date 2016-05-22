/**
 * PathItem class stores data on each traversed track
 * 
 * @author Charles Johnson
 * @author Joshua Jennings
 * @author Zachery Hibbard
 */
public class PathItem {
   public int stationID;
   public int trackID;
   public int arriveTime;
   public int leaveTime;

   /**
    * Constructor creates a new PathItem
    * 
    * Each PathItem consists of arrival/departure time, as well as both a
    * Station and Track ID.
    * 
    * @param stationID
    * @param trackID
    * @param arriveTime
    * @param leaveTime
    */
   public PathItem(int stationID, int trackID, int arriveTime, int leaveTime) {
      this.stationID = stationID;
      this.trackID = trackID;
      this.arriveTime = arriveTime;
      this.leaveTime = leaveTime;
   }

   /**
    * Returns a String representation of a PathItem
    * 
    * If any schedule conflicts were found, an alert with a wait time is also
    * returned.
    */
   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("[Station ");
      sb.append(stationID);
      sb.append(", Arrived: ");
      sb.append(arriveTime);
      sb.append(", Departed: ");
      sb.append(leaveTime == -1 ? "NULL" : leaveTime);
      if (leaveTime - arriveTime > 0) {
         sb.append(", CONFLICT! Wait Time: ");
         sb.append(leaveTime - arriveTime);
      }
      sb.append("]");
      return sb.toString();
   }
}
