import java.util.Arrays;

/**
 * Path class that stores final route taken by each Train Uses the PathItem
 * class
 * 
 * @author Charles Johnson
 * @author Joshua Jennings
 * @author Zachery Hibbard
 */
public class Path {
   public int totalTime;
   public int length;
   public PathItem[] list;

   /**
    * Constructor that takes in a list of PathItems Stores the list, its length,
    * and the final arrival time
    * 
    * @param list
    */
   public Path(PathItem[] list) {
      this.list = list;
      this.length = list.length;
      this.totalTime = list[list.length - 1].arriveTime;
   }

   /**
    * Constructor that takes time and length as parameters Stores both variables
    * and assigns a new PathItem array
    * 
    * @param totalTime
    * @param length
    */
   public Path(int totalTime, int length) {
      this.totalTime = totalTime;
      this.length = length;
      this.list = new PathItem[0];
   }

   /**
    * Default empty constructor
    * 
    * This defaults to the highest possible value so that all identified Paths
    * will be less than the default and used as new shortest path.
    */
   public Path() {
      this(Integer.MAX_VALUE, Integer.MAX_VALUE);
   }

   /**
    * Returns String representation of the Path This will be the shortest path
    * taken by the train String also contains arrival/departure time values from
    * PathItems
    */
   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(Arrays.toString(list));
      sb.append("\n");
      sb.append("Final Arrival Time: " + list[list.length - 1].arriveTime);
      sb.append("\n");
      return sb.toString();
   }
}
