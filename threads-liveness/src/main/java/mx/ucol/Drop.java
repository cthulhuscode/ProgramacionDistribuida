package mx.ucol;

public class Drop {
    private String [] array;
    public boolean empty = true;

    public synchronized String[] take() {
        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Someone interrupted this thread." + e);
            }
        }

        empty = true;
        notifyAll();

        return array;
    }

    public synchronized void put(String[] array) {
        while (!empty) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }

        empty = false;
        this.array = array;
        notifyAll();
    }
}
