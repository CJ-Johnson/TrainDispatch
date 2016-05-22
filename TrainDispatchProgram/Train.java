/**
 * Stores all data pertaining to each train
 * 
 * Uses Path and PathItem classes
 * 
 * @author Charles Johnson
 * @author Joshua Jennings
 * @author Zachery Hibbard
 */
public class Train implements Comparable<Train> {
   public int priority;
   public int ID;
   public int originID;
   public int destinationID;
   public Path bestPath;
   public int scheduleTime;
   public int finalArriveTime;

   /**
    * Constructor for each train
    * 
    * @param ID
    *           - Unique Train ID
    * @param originID
    *           - Source Station
    * @param destinationID
    *           - End Station
    * @param scheduleTime
    *           - Time Train departs source
    * @param priority
    *           - Determines which Trains get calculated first
    * @param bestPath
    *           - List of PathItems to store shortest path
    */
   public Train(int ID, int originID, int destinationID, int scheduleTime,
         int priority, Path bestPath) {
      this.ID = ID;
      this.originID = originID;
      this.destinationID = destinationID;
      this.priority = priority;
      this.scheduleTime = scheduleTime;
      this.bestPath = bestPath;
   }

   /**
    * Compares Trains based first on priority, then departure time
    */
   @Override
   public int compareTo(Train t) {
      if (this.priority > t.priority) {
         return -1;
      }
      if (this.priority < t.priority) {
         return 1;
      }
      if (this.scheduleTime < t.scheduleTime) {
         return -1;
      }
      if (this.scheduleTime > t.scheduleTime) {
         return 1;
      }
      return 0;
   }

   /**
    * Returns String representation of departure time
    */
   @Override
   public String toString() {
      return "" + scheduleTime;
   }
}
