/**
 * Primary path-finding algorithm class
 * 
 * @author Charles Johnson
 * @author Joshua Jennings
 * @author Zachery Hibbard
 */
public class GraphSearch {
   public static final int START_INDEX = 0;
   public static final int NULL_TRACK_ID = -1;
   public static final int NULL_LEAVE_TIME = 0;

   /**
    * Pre-Order traversal via an iterative Depth-First Search
    * 
    * It utilizes the Recursion class which, while this algorithm is iterative,
    * behaves like it would if using recursion by substituting an abstract stack
    * for the call stack.
    * 
    * @param train
    * @param tracks
    * @param stations
    * @param isBaseCase
    */
   public static void singleSearch(Train train, Track[] tracks,
         Station[] stations, boolean isBaseCase) {
      Recursion recursion = new Recursion(stations.length);
      RecursionItem cur;
      recursion._call(train.originID, NULL_TRACK_ID, train.scheduleTime,
            train.scheduleTime, START_INDEX);
      while (recursion._notDone()) {
         cur = recursion._current();
         if (cur.stationID == train.destinationID) {
            if ((cur.arriveTime < train.bestPath.totalTime)
                  || ((cur.arriveTime == train.bestPath.totalTime) && (recursion.stackSize < train.bestPath.length))) {
               train.bestPath = recursion._toPath();
            }
            recursion._return();
         } else if ((cur.index == stations[cur.stationID].trackIDList.size())
               || (cur.arriveTime >= train.bestPath.totalTime)) {
            recursion._return();
         } else {
            int nextTrackID = stations[cur.stationID].trackIDList
                  .get(cur.index++);
            int nextStationID = tracks[nextTrackID]
                  .getOtherStationID(cur.stationID);
            if (!recursion._contains(nextStationID)) {
               int nextAvailableTime = tracks[nextTrackID].interval
                     .getNextAvailableTime(cur.arriveTime);
               cur.leaveTime = nextAvailableTime;
               int nextArriveTime = nextAvailableTime
                     + tracks[nextTrackID].weight;
               recursion._call(nextStationID, nextTrackID, nextArriveTime,
                     NULL_LEAVE_TIME, START_INDEX);
            }
         }
      }
      train.finalArriveTime = train.bestPath.list[train.bestPath.list.length - 1].arriveTime;
   }

   /**
    * Finds the shortest path in graph for each Train
    * 
    * @param trains
    * @param tracks
    * @param stations
    * @param isBaseCase
    */
   public static void fullSearch(Train[] trains, Track[] tracks,
         Station[] stations, boolean isBaseCase) {
      for (Train train : trains) {
         GraphSearch.singleSearch(train, tracks, stations, isBaseCase);

         // start at index 1 because first station has no leading trackID (you
         // don't originate from anywhere)
         for (int i = 1, len = train.bestPath.list.length; i < len; ++i) {
            PathItem pi = train.bestPath.list[i];
            if (isBaseCase) {
               tracks[pi.trackID].interval.setOccupiedBaseCase(
                     train.scheduleTime, train.finalArriveTime);
            } else {
               int previousLeaveTime = pi.arriveTime
                     - tracks[pi.trackID].weight;
               tracks[pi.trackID].interval.setOccupied(previousLeaveTime);
            }
         }
      }
   }
}
