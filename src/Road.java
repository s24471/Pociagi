import java.util.ArrayList;

public class Road implements Runnable{
    Station s1;
    Station s2;
    int length;
    Train train;
    boolean free;
    Thread logic;
    boolean alive;

    ArrayList<Train> trains;

    public Road(Station s1, Station s2, int length) {
        this.s1 = s1;
        this.s2 = s2;
        this.length = length;
        free = true;
        alive = true;
        trains = new ArrayList<>();
        logic = new Thread(this);
        logic.start();
    }
    public void run() {
        while (alive) {
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    if (free && trains.size() > 0) {
                        train = trains.get(0);
                        trains.remove(0);
                        train.next(this);
                        free = false;
                    }
                }
            }
        }
    }




    public void finished(){
        this.train = null;
        free = true;
        logic.interrupt();
    }
    public void arrive(Train train){
        trains.add(train);
        logic.interrupt();
    }
}
