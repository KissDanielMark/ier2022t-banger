import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/** class that implements the Model of0 Domestic Robot application */
public class HouseModel extends GridWorldModel {

    // constants for the grid objects
    public static final int FRIDGE = 16;
    public static final int OWNER  = 32;
    public static final int TABLE  = 8;
    
    // the grid size
    public static final int palyameret = 7;
    
    boolean map[][] = new boolean[palyameret][palyameret];

    int[][] mapKereso = new int[palyameret][palyameret];
        

    boolean foodReady    = false;
    boolean fridgeOpen   = false; // whether the fridge is open
    boolean carryingBeer = false; // whether the robot is carrying beer
    int sipCount        = 0; // how many sip the owner did
    int availableBeers  = 10; // how many beers are available

    Location locationFridge = new Location(0,0);//felso sarok
    Location locationOwner  = new Location(palyameret-1,palyameret-1);//jobb also sarok
    Location locationAsztal = new Location(0, palyameret-1);//bal also

    public HouseModel() {
        
        // create a 7x7 grid with one mobile agent
        super(palyameret, palyameret, 2);

        // initial location of robot (column 3, line 3)
        // ag code 0 means the robot
        setAgPos(0, palyameret-1, palyameret/2);
        setAgPos(1, palyameret-1, 0);

        // initial location of fridge and owner
        add(FRIDGE, locationFridge);
        add(OWNER, locationOwner);
        add(TABLE, locationAsztal);
        
        for(int y = 0; y <= 6; y++){
            if(y == 4) continue;
            add(OBSTACLE, 4, y);
            map[4][y] = true;
            mapKereso[y][4] = 1;
        }

        System.out.println("HouseModel inicializálás");
    }

    boolean openFridge() {
        if (!fridgeOpen) {
            fridgeOpen = true;
            return true;
        } else {
            return false;
        }
    }

    boolean closeFridge() {
        if (fridgeOpen) {
            fridgeOpen = false;
            return true;
        } else {
            return false;
        }
    }

    boolean moveTowards(Location dest, int id) {
        Finder f = new Finder();
        Location r1 = getAgPos(id);

        Point start = new Point(r1.x, r1.y, null);
        Point end = new Point(dest.x,dest.y, null);

        List<Point> path = f.FindPath(mapKereso,start,end);
        if (path != null) 
        {
            r1.x = path.get(0).x;
            r1.y = path.get(0).y;
        }
        else{
            System.out.println("No path found");
        }
        setAgPos(id, r1); // move the robot in the grid

        // repaint the fridge and owner locations
        if (view != null) {
            view.update(locationFridge.x,locationFridge.y);
            view.update(locationOwner.x,locationOwner.y);
            view.update(locationAsztal.x, locationAsztal.y);
        }
        return true;
    }

    boolean getBeer() {
        if (fridgeOpen && availableBeers > 0 && !carryingBeer) 
        {
            availableBeers--;
            carryingBeer = true;
            if (view != null)
                view.update(locationFridge.x,locationFridge.y);
            return true;
        } else {
            return false;
        }
    }

    boolean addBeer(int n) {
        availableBeers += n;
        if (view != null)
            view.update(locationFridge.x,locationFridge.y);
        return true;
    }

    boolean handInBeer() {
        if (carryingBeer) {
            sipCount = 5;
            carryingBeer = false;
            if (view != null)
                view.update(locationOwner.x,locationOwner.y);
            return true;
        } else {
            return false;
        }
    }

    boolean sipBeer() {
        if (sipCount > 0) {
            sipCount--;
            if (view != null)
                view.update(locationOwner.x,locationOwner.y);
            return true;
        } else {
            return false;
        }
    }

    boolean cookFood()
    {
        return true;
    }

    boolean prepareFood()
    {
        return true;
    }
}
