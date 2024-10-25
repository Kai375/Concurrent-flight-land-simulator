import java.util.Random;

public class Main {

    private static final int NUMBER_OF_FLIGHTS = 10;

    public static void main(String[] args) {

        Random random = new Random();

        Airport TLV = new Airport("TLV");
        Airport LAX = new Airport("LAX");

        Flight[] flights = new Flight[NUMBER_OF_FLIGHTS];

        for(int i = 0; i < NUMBER_OF_FLIGHTS; i++) {

            // Choose randomly a flight's point of origin and point of destination.
            if(random.nextBoolean()) {

                flights[i] = new Flight(i+1, TLV, LAX);
            }
            else {

                flights[i] = new Flight(i+1, LAX, TLV);
            }
        }

        for(int i = 0; i < NUMBER_OF_FLIGHTS; i++) {

            flights[i].start();
        }
    }
}
