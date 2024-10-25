import java.util.ArrayList;

// This Class defines the Airport characteristics and Producer/Consumer(s) relations.
public class Airport {

    // Declarations.
    private static final int NUMBER_OF_RUNWAYS = 3;
    private final String airportName;
    private final ArrayList<Integer> flightList;
    private int totalRunways;
    private final boolean[] availableRunways; // Declaration of  the array that tracks which runway is occupied (true), and which is not (false).


    // Constructor.
    public Airport(String name) {

        this.airportName = name;
        flightList = new ArrayList<>();
        totalRunways = 0;
        availableRunways = new boolean[NUMBER_OF_RUNWAYS];  // Initialises the array that tracks which runway is occupied (true), and which is not (false).
    }

    // Describes the departure-consumer process of any flight.
    public void depart(int flightNumber) {

        int runwayNumber = flightMonitor(flightNumber); // Send the flightNumber to the flight flightMonitor for attempt runway resource assignment.

        System.out.println("Flight number " + flightNumber + " started its departure process from " + airportName + " airport" + ", on runway number " + (runwayNumber + 1));

        WaitingState.waitingState(); // Simulate departure-in-progress for 2 to 5 seconds.

        System.out.println("Flight number " + flightNumber + " completed its departure process from " + airportName + " airport" + ", on runway number " + (runwayNumber + 1));

        releaseRunway(runwayNumber); // Release runway resource after use.
    }

    // Describes the landing-consumer process of any flight.
    public void land(int flightNumber) {

        System.out.println("Flight number " + flightNumber + " completed its flight and is waiting for landing at " + airportName + " airport.");

        int runwayNumber = flightMonitor(flightNumber); // Send the flightNumber to the flight flightMonitor for attempt runway resource assignment.

        System.out.println("Flight number " + flightNumber + " started its landing process at " + airportName + " airport" + ", on runway number " + (runwayNumber + 1));

        WaitingState.waitingState(); // Simulate landing-in-progress for 2 to 5 seconds.

        System.out.println("Flight number " + flightNumber + " completed its landing process at " + airportName + " airport" + ", on runway number " + (runwayNumber + 1));

        releaseRunway(runwayNumber); // Release runway resource after use.
    }

    // This method routes departure/landing consumer processes to the runway resources, if they are available.
    // Otherwise, it pauses them until one of the runway resources becomes available.
    private synchronized int flightMonitor(int flightNumber) {

        flightList.add(flightNumber);

        // The loop runs until the capacity of the runways is reached, or the flightList is exhausted.
        while(totalRunways == NUMBER_OF_RUNWAYS || flightList.get(0) != flightNumber) {

            System.out.println("Flight number " + flightNumber + " does not have an available runway, and is waiting for one to clear.");

            try {

                wait();

            } catch(InterruptedException e) {

                System.out.println("Error at flightMonitor waiting process.");
            }
        }

        flightList.remove(0); // Removes a departure/landing process from flightList after it has completed its process.

        totalRunways++;

        return availableRunway();
    }

    // This method releases the runway resources after use by the consumer methods depart/land processes.
    private synchronized void releaseRunway(int runwayNumber) {

        totalRunways--;

        availableRunways[runwayNumber] = false;

        notifyAll();  // Notifies the awaiting users.
    }

    // This method searches for an available runway resource and returns it to the flightMonitor.
    // If no runway resource is available, then it will return -1.
    private synchronized int availableRunway() {

        for(int i = 0; i < availableRunways.length; i++) {

            if(!availableRunways[i]) {

                availableRunways[i] = true;

                return i;
            }
        }

        return -1;
    }
}
