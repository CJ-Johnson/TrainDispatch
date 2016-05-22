/**
 * Manages a list of IntervalItems for each Track
 *
 * Each Track has a unique list of Intervals that can be called on to determine
 * when trains will be occupying that Track
 *
 * @author Charles Johnson
 * @author Joshua Jennings
 * @author Zachery Hibbard
 */
public class Interval {
   private int weight;
   private IntervalItem head;

   private boolean belowRange(IntervalItem one, IntervalItem two) {
      return one.end < two.start;
   }

   private boolean aboveRange(IntervalItem one, IntervalItem two) {
      return one.start > two.end;
   }

   private boolean withinRange(IntervalItem one, IntervalItem two) {
      return !(belowRange(one, two) || aboveRange(one, two));
   }

   private boolean isSuperSequence(IntervalItem one, IntervalItem two) {
      return one.start <= two.start && one.end >= two.end;
   }

   private boolean isSubSequence(IntervalItem one, IntervalItem two) {
      return one.start >= two.start && one.end <= two.end;
   }

   /**
    * Iterates through an Interval list to find the next available time interval
    * from arriveTime to the arriveTime + weight of Track
    * 
    * @param arriveTime
    * @return
    */
   public int getNextAvailableTime(int arriveTime) {
      IntervalItem immediate = new IntervalItem(arriveTime,
            arriveTime + weight, null);
      if (head == null) {
         return immediate.start;
      }
      if (head.next == null) {
         if (withinRange(immediate, head)) {
            return head.end;
         }
         return immediate.start;
      }
      if (belowRange(immediate, head)) {
         return immediate.start;
      }
      IntervalItem cur = head;
      while (cur.next != null) {
         if (withinRange(immediate, cur)) {
            return cur.end;
         }
         if (belowRange(immediate, cur.next)) {
            return immediate.start;
         }
         cur = cur.next;
      }
      if (withinRange(immediate, cur)) {
         return cur.end;
      }
      return immediate.start;
   }

   /**
    * Creates a new IntervalItem and adds it to Interval list Uses belowRange,
    * aboveRange, withinRange methods to find position
    * 
    * @param leaveTime
    */
   private void insertItem(int leaveTime) {
      IntervalItem input = new IntervalItem(leaveTime, leaveTime + weight, null);
      IntervalItem cur = head;
      if (cur == null || belowRange(input, cur)) {
         head = input;
         head.next = cur;
         return;
      }
      while (cur.next != null) {
         if (input.start <= cur.next.start) {
            input.next = cur.next;
            cur.next = input;
            return;
         }
         cur = cur.next;
      }
      cur.next = input;
   }

   /**
    * If the range between two consecutive IntervalItems is less than the weight
    * of the Track, the IntervalItems are merged
    */
   private void mergeAdjacent() {
      IntervalItem cur = head;
      if (cur == null) {
         return;
      }
      while (cur.next != null) {
         if ((cur.end - cur.next.start) < weight) {
            cur.end = cur.next.end;
            cur.next = cur.next.next;
         } else {
            cur = cur.next;
         }
      }
   }

   /**
    * Creates a new IntervalItem Calls both insertItem and mergeAdjacent methods
    * 
    * @param leaveTime
    */
   public void setOccupied(int leaveTime) {
      insertItem(leaveTime);
      mergeAdjacent();
   }

   /**
    * Creates a new IntervalItem and adds it to Interval list Uses belowRange,
    * isSuperSequence, isSubSequence methods to find position
    * 
    * The difference is that when the Base Case is active, it is only concerned
    * with each Train's final destination
    * 
    * If the IntervalItem to be added is not greater than the current combined
    * range of all list IntervalItems, no action is taken
    * 
    * @param startTime
    * @param endTime
    */
   private void insertItemBaseCase(int startTime, int endTime) {
      IntervalItem input = new IntervalItem(startTime, endTime, null);
      IntervalItem cur = head;
      if (cur == null || belowRange(input, cur)) {
         head = input;
         head.next = cur;
         return;
      }
      if (isSuperSequence(cur, input)) {
         return;
      }
      if (isSubSequence(cur, input)) {
         input.next = cur;
         head = input;
         return;
      }
      while (cur.next != null) {
         if (input.start <= cur.next.start) {
            input.next = cur.next;
            cur.next = input;
            return;
         }
      }
      cur.next = input;
   }

   /**
    * Since all the Base Case is concerned with is the final time, all
    * IntervalItems are merged into one with the final time being the largest
    * final time of any train passing through
    * 
    * Uses isSuperSequence method
    */
   private void mergeAdjacentBaseCase() {
      if (head == null) {
         return;
      }
      IntervalItem cur = head;
      while (cur.next != null) {
         if (isSuperSequence(cur, cur.next)) {
            cur.next = cur.next.next;
         } else if (withinRange(cur, cur.next)
               || (cur.next.start - cur.end) < weight) {
            cur.end = cur.next.end;
            cur.next = cur.next.next;
         } else {
            cur = cur.next;
         }
      }
   }

   /**
    * Adds a new IntervalItem for Base Case
    * 
    * Calls both insertItemBaseCase and mergeAdjacentBaseCase methods
    * 
    * @param startTime
    * @param endTime
    */
   public void setOccupiedBaseCase(int startTime, int endTime) {
      insertItemBaseCase(startTime, endTime);
      mergeAdjacentBaseCase();
   }

   /**
    * Constructor that assigns the Track weight
    * 
    * @param weight
    */
   public Interval(int weight) {
      this.weight = weight;
   }
}
