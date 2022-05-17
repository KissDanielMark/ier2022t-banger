import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;

/** class that implements the Model of Domestic Robot application */
public class HouseModel extends GridWorldModel {

    // constants for the grid objects
    public static final int FRIDGE = 16;
    public static final int OWNER  = 32;

    // the grid size
    public static final int palyameret = 7;

    boolean fridgeOpen   = false; // whether the fridge is open
    boolean carryingBeer = false; // whether the robot is carrying beer
    int sipCount        = 0; // how many sip the owner did
    int availableBeers  = 10; // how many beers are available

    Location locationFridge = new Location(0,0);//felso sarok
    Location locationOwner  = new Location(palyameret-1,palyameret-1);//also sarok

    public HouseModel() {
        
        // create a 7x7 grid with one mobile agent
        super(palyameret, palyameret, 1);

        // initial location of robot (column 3, line 3)
        // ag code 0 means the robot
        setAgPos(0, palyameret/2, palyameret/2);

        // initial location of fridge and owner
        add(FRIDGE, locationFridge);
        add(OWNER, locationOwner);

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

    boolean moveTowards(Location dest) {
        Location r1 = getAgPos(0);
        if (r1.x < dest.x)        r1.x++;
        else if (r1.x > dest.x)   r1.x--;
        if (r1.y < dest.y)        r1.y++;
        else if (r1.y > dest.y)   r1.y--;
        setAgPos(0, r1); // move the robot in the grid

        // repaint the fridge and owner locations
        if (view != null) {
            view.update(locationFridge.x,locationFridge.y);
            view.update(locationOwner.x,locationOwner.y);
        }
        return true;
    }

    boolean getBeer() {
        if (fridgeOpen && availableBeers > 0 && !carryingBeer) {
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
            sipCount = 10;
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
        System.out.println("Szopj ki buzi");
        return true;
    }
}
