import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    static ArrayList<Station> stations;
    public static void main(String[] args) {
        stations = new ArrayList<>();
        stations.add(new Station("s1", new HashMap<>()));

        stations.add(new Station("s3", new HashMap<>()));
        HashMap<Station, Integer> map = new HashMap<>();

        map.put(stations.get(0), 10);
        map.put(stations.get(1), 20);
        stations.add(new Station("s2", map));

        for (int i = 0; i < stations.size(); i++) {
            System.out.println(stations.get(i).name);
        }

        Train train = new Train(stations.get(0), stations.get(1), 0, 8, "t1", 0, 0 ,0);
        for (Station s: findRoute(stations.get(0), stations.get(1)))
        {
            System.out.println(s.name);
        }
    }


    public static ArrayList<Station> findRoute(Station start, Station fin){
        HashMap<Station, Integer> cost = new HashMap<>();
        HashMap<Station, Station> prev = new HashMap<>();
        for (Station station : stations) {
            cost.put(station, Integer.MAX_VALUE);
            prev.put(station, null);
        }
        cost.put(start, 0);
        ArrayList<Station> unvisited = new ArrayList<>(stations);

        while (!unvisited.isEmpty()) {
            Station current = null;
            int minCost = Integer.MAX_VALUE;
            for (Station station : unvisited) {
                if (cost.get(station) < minCost) {
                    current = station;
                    minCost = cost.get(station);
                }
            }

            if (current == fin) {
                break;
            }

            unvisited.remove(current);

            for (Road road : current.roads.values()) {
                Station neighbor = (road.s1 == current) ? road.s2 : road.s1;
                int newCost = cost.get(current) + road.length;
                if (newCost < cost.get(neighbor)) {
                    cost.put(neighbor, newCost);
                    prev.put(neighbor, current);
                }
            }
        }

        ArrayList<Station> route = new ArrayList<>();
        Station current = fin;
        while (current != null) {
            route.add(0, current);
            current = prev.get(current);
        }
        return route;
    }
}