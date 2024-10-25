import java.util.Random;

// This Class sole purpose is to simulate departure-in-progress for 2 to 5 seconds.
public class WaitingState {

    public static synchronized void waitingState() {

        Random random = new Random();

        try {

            Thread.sleep( random.nextInt(3000) + 2000);

        } catch(InterruptedException e) {

            System.out.println("Error at WaitingState sleep process.");
        }
    }
}
