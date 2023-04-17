import java.util.ArrayList;

public class Train {
    ArrayList<Station> path;
    Station nextStop;
    boolean ready;
    Thread logic;
    double toDrive;
    double left;

    public Train(Station start, Station fin) {
        path = Main.findRoute(start, fin);
        nextStop = path.get(1);
        logic = new Thread(new Runnable() {
            @Override
            public void run() {
                Train.this.run();
            }
        });
        logic.start();
    }

    public void next(Road road){
        ready = true;
        toDrive = road.change(this);
        left = toDrive;
    }
    public void run(){

    }
}
