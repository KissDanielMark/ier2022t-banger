import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;

/** class that implements the Model of0 Domestic Robot application */
public class HouseModel extends GridWorldModel {

    // constants for the grid objects
    public static final int FRIDGE = 16;
    public static final int CHEF  = 32;
    public static final int TABLE1  = 8;
    public static final int TABLE2  = 64;
    public static final int TABLE3  = 128;

    // the grid size
    public static final int palyameret = 7;

    boolean foodReady    = false;
    boolean fridgeOpen   = false; // whether the fridge is open
    boolean carrying     = false; // whether the robot is carrying beer
    int kavarCount       = 0; // how many sip the owner did
    int availableIngredient  = 5; // how many beers are available

    Location locationFridge = new Location(0,0);//felso sarok
    Location locationChef  = new Location(palyameret-1,palyameret-1);//jobb also sarok
    Location locationAsztal1 = new Location(0, palyameret-1);//bal also
    Location locationAsztal2 = new Location(0, palyameret-3);//bal also
    Location locationAsztal3 = new Location(0, palyameret-5);//bal also

    public HouseModel() {
        
        // create a 7x7 grid with one mobile agent
        super(palyameret, palyameret, 2);

        // initial location of robot (column 3, line 3)
        // ag code 0 means the robot
        setAgPos(0, palyameret/2, palyameret/2);
        setAgPos(1, palyameret-1, 0);

        // initial location of fridge and owner
        add(FRIDGE, locationFridge);
        add(CHEF, locationChef);
        add(TABLE1, locationAsztal1);
        add(TABLE2, locationAsztal2);
        add(TABLE3, locationAsztal3);

        System.out.println("HouseModel inicializálás");
    }

    boolean openFridge() {
        if(HouseEnv.currentKaja == 0) return false;
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
            view.update(locationChef.x,locationChef.y);
            view.update(locationAsztal1.x, locationAsztal1.y);
            view.update(locationAsztal2.x, locationAsztal2.y);
            view.update(locationAsztal3.x, locationAsztal3.y);
        }
        return true;
    }

    boolean getAlapAg(int ft) {
        if (fridgeOpen && availableIngredient > 0 && !carrying)
        {
            availableIngredient -= ft;
            carrying = true;
            if (view != null)
                view.update(locationFridge.x,locationFridge.y);
            return true;
        } else {
            return false;
        }
    }

    boolean addAlapAg(int n) {
        availableIngredient += n;
        if (view != null)
            view.update(locationFridge.x,locationFridge.y);
        return true;
    }

    boolean handInAlapAg() {
        if (carrying) {
            kavarCount = HouseEnv.currentKaja * 3;
            carrying = false;
            HouseEnv.currentKaja = 0;
            if (view != null)
                view.update(locationChef.x,locationChef.y);
            return true;
        } else {
            return false;
        }
    }

    boolean kavarEtel() {
        if (kavarCount > 0) {
            kavarCount--;
            if (view != null)
                view.update(locationChef.x,locationChef.y);
            return true;
        } else {
            return false;
        }
    }

    boolean cookFood()
    {
        return true;
    }
}
