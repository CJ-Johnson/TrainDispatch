/**
 * Node storing an interval of time that a Track is occupied
 * 
 * @author Charles Johnson
 * @author Joshua Jennings
 * @author Zachery Hibbard
 */
public class IntervalItem {
   public int start;
   public int end;
   public IntervalItem next;

   /**
    * Constructor that assigns start/end time and the next list IntervalItem
    * 
    * @param start
    * @param end
    * @param next
    */
   public IntervalItem(int start, int end, IntervalItem next) {
      this.start = start;
      this.end = end;
      this.next = next;
   }

   /**
    * Returns a String representation of each IntervalItem
    */
   @Override
   public String toString() {
      return "[start:" + start + ", end:" + end + ", next:" + (next != null)
            + "]";
   }
}
