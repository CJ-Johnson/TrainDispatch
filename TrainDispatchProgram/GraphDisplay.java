import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerPipe;
import java.util.BitSet;
import java.util.Random;

/**
 * Creates a visual representation of graph data
 * 
 * @author Charles Johnson
 * @author Joshua Jennings
 * @author Zachery Hibbard
 */
public class GraphDisplay {
   /**
    * Print average total travel time per train
    * 
    * @param trains
    */
   public static void printTravelTime(Train[] trains) {
      int longestOverallTime = 0;
      for (int i = 0; i < trains.length; ++i) {
         longestOverallTime = Math.max(longestOverallTime,
               trains[i].finalArriveTime);
      }
      int averageTravelTime = 0;
      for (int i = 0; i < trains.length; ++i) {
         int last = trains[i].bestPath.list.length - 1;
         System.out.println("Train " + trains[i].ID + " Path Traveled:");
         for (int j = 0; j <= last; j++) {
            System.out.print("Station " + trains[i].bestPath.list[j].stationID);
            if (j < last) {
               System.out.print(" : ");
            }
         }
         System.out.println();
         System.out.println(trains[i].bestPath);
         averageTravelTime += trains[i].finalArriveTime
               - trains[i].scheduleTime;
      }
      System.out.println("Average Total Travel Time Per Train: "
            + (averageTravelTime / trains.length));
   }

   /**
    * Add Stations and Tracks to new graph
    * 
    * @param stations
    * @param tracks
    */
   public static void addElements(Station[] stations, Track[] tracks,
         Graph printGraph, String station, String track) {
      for (int i = 0; i < stations.length; ++i) {
         printGraph.addNode(station + stations[i].ID);
         Node n = printGraph.getNode(station + stations[i].ID);
         n.addAttribute("ui.label", station + i);
      }
      BitSet createdTracks = new BitSet(tracks.length);
      for (int i = 0; i < stations.length; ++i) {
         for (int j = 0, len = stations[i].trackIDList.size(); j < len; ++j) {
            int trackID = stations[i].trackIDList.get(j);
            if (!createdTracks.get(trackID)) {
               createdTracks.set(trackID);
               int otherStationID = tracks[trackID]
                     .getOtherStationID(stations[i].ID);
               printGraph.addEdge(track + trackID, station + stations[i].ID,
                     station + otherStationID);
               printGraph.getEdge(track + trackID).addAttribute("ui.label",
                     tracks[trackID].weight);
            }
         }
      }
   }

   /**
    * Create a unique Sprite for each Train
    * 
    * @param trains
    * @param station
    * @param sman
    * @return
    */
   public static String[] setTrainColors(Train[] trains, String station,
         SpriteManager sman) {
      Random rng = new Random(1);
      String[] colors = new String[trains.length];
      for (int i = 0; i < trains.length; ++i) {
         int r1 = rng.nextInt((255 - 1) + 1) + 1;
         int r2 = rng.nextInt((255 - 1) + 1) + 1;
         int r3 = rng.nextInt((255 - 1) + 1) + 1;
         String temp = ("rgb(" + r1 + ", " + r2 + ", " + r3 + ");");
         Sprite s = sman.addSprite("T" + i);
         s.addAttribute("ui.label", "T" + i);
         s.addAttribute("ui.style", "text-background-color: " + temp);
         s.attachToNode(station + trains[i].originID);
         colors[i] = temp;
      }
      return colors;
   }

   /**
    * Display a visual representation of the dispatch system
    * 
    * @param stations
    *           array containing all station data
    * @param tracks
    *           array containing all track data
    * @param trains
    *           array containing all train data
    * @param cssFilePath
    *           used to display graph features
    */
   public static void generateView(Station[] stations, Track[] tracks,
         Train[] trains, String cssFilePath) {
      printTravelTime(trains);
      System.setProperty("org.graphstream.ui.renderer",
            "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
      Graph printGraph = new MultiGraph("MyGraph");
      String station = "Station ";
      String track = "Track ";
      addElements(stations, tracks, printGraph, station, track);
      printGraph.addAttribute("ui.stylesheet", cssFilePath);

      Viewer viewer = printGraph.display();
      ViewerPipe vPipe = viewer.newViewerPipe();
      SpriteManager sman = new SpriteManager(printGraph);

      String[] colors = setTrainColors(trains, station, sman);

      int maxTime = 0;
      for (int i = 0; i < trains.length; ++i) {
         maxTime = Math.max(maxTime, trains[i].finalArriveTime);
      }
      System.out.println();
      System.out.println("Train Traversal:");

      for (int m = 0; m <= maxTime; ++m) {
         try {
            Thread.sleep(450);
         } catch (InterruptedException e1) {
            e1.printStackTrace();
         }
         for (int i = 0; i < trains.length; ++i) {
            int last = trains[i].bestPath.list.length - 1;
            int prev = 0;
            for (int j = 0; j <= last; ++j) {
               int t = 0;
               int x = trains[i].bestPath.list[j].stationID; // j not j + 1
               if (j < last) {
                  t = trains[i].bestPath.list[j + 1].trackID;
               }
               prev = trains[i].bestPath.list[j].trackID;
               int a = trains[i].bestPath.list[j].arriveTime;
               int d = trains[i].bestPath.list[j].leaveTime;

               if (j == 0 && m == a) {
                  if (a != d) {
                     System.out.println("Time: " + m
                           + "  DELAYED START: Train T" + trains[i].ID
                           + " delayed for " + (d - a));
                  }
               }
               if (j != 0 && d == 0 && m == a) {
                  sman.removeSprite("T" + i);
                  Sprite s = sman.addSprite("T" + i);
                  s.addAttribute("ui.label", "T" + i);
                  s.addAttribute("ui.style", "text-background-color: "
                        + colors[i]);
                  s.attachToNode(station + x);
                  System.out.println("Time: " + m + "  Train T" + trains[i].ID
                        + " finally arrived at " + station + x);
                  printGraph.getEdge(track + prev).addAttribute("ui.style",
                        "fill-color: black;");
                  break;
               }
               if (m == a && j != 0 && t >= 0) {
                  if (a != d) {
                     sman.removeSprite("T" + i);
                     Sprite s = sman.addSprite("T" + i);
                     s.addAttribute("ui.label", "T" + i);
                     s.addAttribute("ui.style", "text-background-color: "
                           + colors[i]);
                     s.attachToNode(station + x);
                     System.out.println("Time: " + m + "  Train T"
                           + trains[i].ID + " arrived at " + station + x);
                     System.out.println("Time: " + m + "  DELAY: Train T"
                           + trains[i].ID + " delayed for " + (d - a));
                  }
                  printGraph.getEdge(track + prev).addAttribute("ui.style",
                        "fill-color: black;");
               }
               if (m == d && j != last && t >= 0) {
                  System.out.println("Time: " + m + "  Train T" + trains[i].ID
                        + " left " + station + x + " on " + track + t);
                  sman.getSprite("T" + i).attachToEdge(track + t);
                  sman.getSprite("T" + i).setPosition(0.6);
                  printGraph.getEdge(track + t).addAttribute("ui.style",
                        "fill-color: " + colors[i]);
               }
               prev = trains[i].bestPath.list[j].trackID;
            }
         }
         vPipe.pump();
      }
      System.out.println("All Trains have arrived.");
   }
}
