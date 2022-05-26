import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Utkereso
{
    public static boolean IsWalkable(int[][] map, Pont point) {
        if (point.y < 0 || point.y > map.length - 1) return false;
        if (point.x < 0 || point.x > map[0].length - 1) return false;
        return map[point.y][point.x] == 0;
    }

    public static List<Pont> FindNeighbors(int[][] map, Pont point) {
        List<Pont> neighbors = new ArrayList<>();
        Pont up = point.offset(0,  1);
        Pont down = point.offset(0,  -1);
        Pont left = point.offset(-1, 0);
        Pont right = point.offset(1, 0);
        if (IsWalkable(map, up)) neighbors.add(up);
        if (IsWalkable(map, down)) neighbors.add(down);
        if (IsWalkable(map, left)) neighbors.add(left);
        if (IsWalkable(map, right)) neighbors.add(right);
        return neighbors;
    }

    public static List<Pont> FindPath(int[][] map, Pont start, Pont end)
    {
        boolean finished = false;
        List<Pont> used = new ArrayList<>();
        used.add(start);
        while (!finished) {
            List<Pont> newOpen = new ArrayList<>();
            for(int i = 0; i < used.size(); ++i){
                Pont point = used.get(i);
                for (Pont neighbor : FindNeighbors(map, point)) {
                    if (!used.contains(neighbor) && !newOpen.contains(neighbor)) {
                        newOpen.add(neighbor);
                    }
                }
            }

            for(Pont point : newOpen) {
                used.add(point);
                if (end.equals(point)) {
                    finished = true;
                    break;
                }
            }

            if (!finished && newOpen.isEmpty())
                return null;
        }

        List<Pont> path = new ArrayList<>();
        Pont point = used.get(used.size() - 1);
        while(point.previous != null) {
            path.add(0, point);
            point = point.previous;
        }
        return path;
    }
}