import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;

/** class that implements the Model of0 Domestic Robot application */
public class HouseModel extends GridWorldModel {

    // constants for the grid objects
    public static final int FRIDGE = 16;
    public static final int OWNER  = 32;
    public static final int TABLE  = 8;
    
    // the grid size
    public static final int palyameret = 8;
    
    boolean map[][] = new boolean[palyameret][palyameret];

    boolean foodReady    = false;
    boolean fridgeOpen   = false; // whether the fridge is open
    boolean carryingBeer = false; // whether the robot is carrying beer
    int sipCount        = 0; // how many sip the owner did
    int availableBeers  = 10; // how many beers are available

    Location locationFridge = new Location(1,1);//felso sarok
    Location locationOwner  = new Location(palyameret-2,palyameret-2);//jobb also sarok
    Location locationAsztal = new Location(1, palyameret-2);//bal also

    public HouseModel() {
        
        // create a 7x7 grid with one mobile agent
        super(palyameret, palyameret, 2);

        // initial location of robot (column 3, line 3)
        // ag code 0 means the robot
        setAgPos(0, palyameret/2, palyameret/2);
        setAgPos(1, palyameret-2, 0);

        // initial location of fridge and owner
        add(FRIDGE, locationFridge);
        add(OWNER, locationOwner);
        add(TABLE, locationAsztal);
        for(int x = 0; x <= 7; x++){
            add(OBSTACLE, x, 0);
            add(OBSTACLE, x, 7);
            map[x][0] = true;
            map[x][7] = true;
        }
        for(int y = 1; y <= 6; y++){
            add(OBSTACLE, 0, y);
            add(OBSTACLE, 7, y);
            map[0][y] = true;
            map[7][y] = true;
        }
        for(int y = 0; y <= 6; y++){
            if(y == 4) continue;
            add(OBSTACLE, 4, y);
            map[4][y] = true;
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
        Location r1 = getAgPos(id);
            if (r1.x < dest.x)        r1.x++;
            else if (r1.x > dest.x)   r1.x--;
            if (r1.y < dest.y)        r1.y++;
            else if (r1.y > dest.y)   r1.y--;
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
