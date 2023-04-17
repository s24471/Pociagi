public class Road {
    Station start;
    Station end;
    double length;
    Train train;
    boolean free;

    public Road(Station start, Station end, double length) {
        this.start = start;
        this.end = end;
        this.length = length;
        free = true;
    }

    public double change(Train train){
        this.train = train;
        free = false;
        return length;
    }
}
