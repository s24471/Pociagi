import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Train {
    public static int ID =0;
    public int id;
    ArrayList<Station> path;
    Station nextStop;
    boolean ready;
    Thread logic;
    double toDrive;
    double left;
    boolean alive;
    String name;
    int maxCart;
    int maxPull;
    int maxElectric;
    int progress;
    int speed;
    Road curr;
    public Train(Station start, Station fin, int next, int speed, String name, int maxCart, int maxPull, int maxElectric) {
        id=++ID;
        alive = true;
        this.speed = speed;
        this.name = name;
        this.maxCart = maxCart;
        this.maxPull = maxPull;
        this.maxElectric = maxElectric;
        ready = false;
        path = Main.findRoute(start, fin);
        progress = next;
        nextStop = path.get(next+1);
        logic = new Thread(new Runnable() {
            @Override
            public void run() {
                Train.this.run();
            }
        });
        logic.start();
        System.out.println(name +" wystartowal z " + path.get(progress).name);
        path.get(progress).roads.get(nextStop).arrive(this);
    }

    public void next(Road road){
        curr = road;
        ready = true;
        toDrive = road.length;
        left = toDrive;
        logic.interrupt();
    }
    public void finished(){
        System.out.println(name + " dojechano do " + nextStop.name);
        ready = false;
        curr.finished();
        left = 0;
        progress++;
        if(progress == path.size()-1){
            Collections.reverse(path);
            progress = 0;
            sleep(28);
        }
        nextStop = path.get(progress+1);
        sleep(2);
        path.get(progress).roads.get(nextStop).arrive(this);
    }
    public void run(){
        while(alive){
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    if (ready) {
                        while (left > 0) {
                            System.out.println(name + " jedzie po " + curr.s1.name + "-" + curr.s2.name + " " + left/toDrive*100+"%");
                            drive();
                            sleep(1);
                        }
                        finished();
                    }
                }
            }
        }
    }
    public void sleep(int n){
        try {
            TimeUnit.SECONDS.sleep(n);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void drive(){
        left -= speed;
        double factor = new Random().nextBoolean() ? 1.03 : 0.97;
        speed = (int) Math.round(speed * factor);
    }
}
