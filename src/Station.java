import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Station {
    String name;
    Map<Station, Road> roads;
    public Station(String name, Map<Station, Integer> n) {
        this.name = name;
        roads = new HashMap<>();
        for(Station station: n.keySet()){
            if(station.roads.containsKey(this)){
                roads.put(station, station.roads.get(this));
            }
            else{
                roads.put(station, new Road(this, station, n.get(station)));
                station.roads.put(this, roads.get(station));
            }
        }

    }


}
