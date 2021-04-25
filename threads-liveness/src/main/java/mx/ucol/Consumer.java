package mx.ucol;

import java.util.Random;

public class Consumer implements Runnable {
    private Drop drop;
    private int threadNumber;

    public Consumer(Drop drop, int threadNumber) {
        this.drop = drop;
        this.threadNumber = threadNumber;
    }

    public void run() {
        Random random = new Random();

        for(String [] platillos = drop.take(); platillos[0] != "DONE"; platillos = drop.take()){
            for (String platillo: platillos) {
                System.out.format("Thread %d: Comiendo %s%n",this.threadNumber, platillo);

                try {
                    Thread.sleep(random.nextInt(5000));
                } catch (InterruptedException e) {}
            }
        }
    }
}
