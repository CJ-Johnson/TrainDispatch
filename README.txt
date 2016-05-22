README

Place all classes inside the same Eclipse project.

There are two "Driver" classes, TrainDispatch and TrainDispatchTest.

TrainDispatchTest utilizes the TestFilePrinter class which
TrainDispatch does not.

As a jumping off point, use the TrainDispatch class and the two text files
in the FirstRun folder.

Follow the prompts in the console to provide the file path to these files.

WARNING: Must include the "/" at the end of the file path.

This file path also includes the required style sheet for the gui.

In order to render the graph on the screen, you must use the
GraphStream libraries.

There are 6 .jar files included in the package. Import all of them into
Eclipse via build path.

After your first run, you can further explore the program, manually creating
your own input files or using TrainDispatchTest to randomly generate 100
test cases.

When using TrainDispatchTest, the user must select the number of stations,
tracks and trains.

The program will also prompt for a file path to store all created
test case input files.

STATION FILE FORMAT:
First Line => Number of Stations
Other Lines => First Station, Second Station, Weight

3
2 1 4
1 3 6
2 1 4
3 1 5
3 2 1


TRAIN FILE FORMAT:
First Line => Number of Stations
Origin, Destination, Departure Time, Train Priority

4
2 5 3 6
3 7 8 0
2 3 6 2
1 5 1 5

