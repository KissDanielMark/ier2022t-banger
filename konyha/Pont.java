import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pont {
    public int x;
    public int y;
    public Pont previous;

    public Pont(int x, int y, Pont previous) {
        this.x = x;
        this.y = y;
        this.previous = previous;
    }

    @Override
    public String toString() { return String.format("(%d, %d)", x, y); }

    @Override
    public boolean equals(Object o) {
        Pont Pont = (Pont) o;
        return x == Pont.x && y == Pont.y;
    }

    @Override
    public int hashCode() { return Objects.hash(x, y); }

    public Pont offset(int ox, int oy) { return new Pont(x + ox, y + oy, this);  }

}