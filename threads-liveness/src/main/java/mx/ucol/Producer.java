package mx.ucol;

import java.util.Random;

public class Producer implements Runnable {
    private Drop drop;

    public Producer(Drop drop) {
        this.drop = drop;
    }

    public void run() {
        String entradas[] = {
                "Entrada 1",
                "Entrada 2",
                "Entrada 3",
                "Entrada 4",
                "Entrada 5",
                "Entrada 6",
                "Entrada 7",
                "Entrada 8",
                "Entrada 9",
                "Entrada 10"
        };

        String fuertes[] = {
                "Fuerte 1",
                "Fuerte 2",
                "Fuerte 3",
                "Fuerte 4",
                "Fuerte 5",
                "Fuerte 6",
                "Fuerte 7",
                "Fuerte 8",
                "Fuerte 9",
                "Fuerte 10"
        };

        String postres[] = {
                "Postre 1",
                "Postre 2",
                "Postre 3",
                "Postre 4",
                "Postre 5",
                "Postre 6",
                "Postre 7",
                "Postre 8",
                "Postre 9",
                "Postre 10"
        };

        String[][] platillos = new String[][] { entradas, fuertes, postres };
        Random random = new Random();

        for (String[] array: platillos) {
            drop.put(array);

            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException e) {}
        }

        drop.put(new String[]{"DONE"});
    }
}
