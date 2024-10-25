// This Class defines the flight thread characteristics.
public class Flight extends Thread {

    private final int flightNumber;

    private final Airport origin, destination;

    // Constructor.
    public Flight(int flightNumber, Airport origin, Airport destination) {

        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
    }

    @Override // Start the flight process from origin airport to destination airport.
    public void run() {

        origin.depart(flightNumber);

        WaitingState.waitingState();

        destination.land(flightNumber);
    }
}
