import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Station {
    String name;
    Map<Station, Road> roads;
    ArrayList<Train> trains;
    boolean alive;
    Thread logic;

    public Station(String name, Map<Station, Double> n) {
        this.name = name;
        alive = true;
        trains = new ArrayList<>();
        roads = new HashMap<>();
        for(Station station: n.keySet()){
            roads.put(station, new Road(this, station, n.get(station)));
        }
        logic = new Thread(new Runnable() {
            @Override
            public void run() {
                Station.this.run();
            }
        });
        logic.start();
    }

    public void run() {
        while(alive){
            try{
                wait();
            }catch (InterruptedException e){
                ArrayList<Train> left = new ArrayList<>(trains);
                for(Train train: left){
                    if(roads.get(train.nextStop).free){
                        train.next(roads.get(train.nextStop));
                        trains.remove(train);
                    }
                }
            }
        }
    }
}
