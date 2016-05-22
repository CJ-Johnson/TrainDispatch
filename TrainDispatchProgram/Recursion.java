import java.util.BitSet;

/**
 * An abstract substitute for a native call stack allowing for direct access to
 * all "stack pointers" (RecursionItem).
 * 
 * @author Charles Johnson
 * @author Joshua Jennings
 * @author Zachery Hibbard
 */
public class Recursion {
   private BitSet set;
   private RecursionItem[] list;
   public int stackSize;

   /**
    * Instantiates a RecursionItem and inserts it at the top of the Recursion
    * stack (push)
    * 
    * @param stationID
    * @param trackID
    * @param arriveTime
    * @param leaveTime
    * @param index
    */
   public void _call(int stationID, int trackID, int arriveTime, int leaveTime,
         int index) {
      set.set((list[stackSize++] = new RecursionItem(stationID, trackID,
            arriveTime, leaveTime, index)).stationID);
   }

   /**
    * Removes the top value of the stack (pop without return value)
    */
   public void _return() {
      set.clear((list[--stackSize]).stationID);
   }

   /**
    * Returns last element in list (peek)
    * 
    * @return
    */
   public RecursionItem _current() {
      return list[stackSize - 1];
   }

   /**
    * Check for termination condition
    * 
    * @return
    */
   public boolean _notDone() {
      return stackSize > 0;
   }

   /**
    * Returns true if Station is present
    * 
    * @param stationID
    * @return
    */
   public boolean _contains(int stationID) {
      return set.get(stationID);
   }

   /**
    * Converts a Recursion stack into an array of PathItems
    * 
    * @return
    */
   Path _toPath() {
      PathItem[] path = new PathItem[stackSize];
      for (int i = 0; i < stackSize; ++i) {
         path[i] = new PathItem(list[i].stationID, list[i].trackID,
               list[i].arriveTime, list[i].leaveTime);
      }
      return new Path(path);
   }

   /**
    * Constructor that instantiates a BitSet and Stack buffer to max size equal
    * to number of Station in the graph
    * 
    * @param maxSize
    */
   public Recursion(int maxSize) {
      set = new BitSet(maxSize);
      list = new RecursionItem[maxSize];
      stackSize = 0;
   }
}
