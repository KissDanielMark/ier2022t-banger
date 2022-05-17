import jason.asSyntax.*;
import jason.environment.Environment;
import jason.environment.grid.Location;
import java.util.logging.Logger;

public class HouseEnv extends Environment {

    // common literals - itt van az atkapcsolas jason baszasbol
    public static final Literal openFridgeParancs  = Literal.parseLiteral("open(fridge)");
    public static final Literal closeFridgeParancs = Literal.parseLiteral("close(fridge)");
    public static final Literal getEtelParancs  = Literal.parseLiteral("get(etel)");
    public static final Literal handInEtelParancs  = Literal.parseLiteral("hand_in(etel)");
    public static final Literal sb  = Literal.parseLiteral("sip(beer)");
    public static final Literal hasSzakacsEtelParancs = Literal.parseLiteral("has(szakacs,etel)");
    public static final Literal cookEtelParancs = Literal.parseLiteral("cook(etel)");

    public static final Literal atFridgeParancs = Literal.parseLiteral("at(kukta,fridge)");
    public static final Literal ao = Literal.parseLiteral("at(kukta,szakacs)");

    static Logger logger = Logger.getLogger(HouseEnv.class.getName());

    HouseModel model; // the model openFridgeParancs the grid

    @Override
    public void init(String[] args) {
        //MAS consolban jelenik meg ez!!!
        //logger.info("Elindult a java");
        System.out.println("Itt indul a JAVA program....");
        model = new HouseModel();
        
        if (args.length == 1 && args[0].equals("gui")) {
            System.out.println("Kell GUI");
            HouseView view  = new HouseView(model);
            model.setView(view);
        }

        updatePercepts();
    }

    
    //creates the agents percepts(felfogas) based on the HouseModel 
    void updatePercepts() {
        // clear the percepts openFridgeParancs the agents
        clearPercepts("kukta");
        clearPercepts("szakacs");

        // get the robot location
        Location locationRobot = model.getAgPos(0);

        // add agent location to its percepts
        if (locationRobot.equals(model.locationFridge)) {
            addPercept("kukta", atFridgeParancs);
        }
        if (locationRobot.equals(model.locationOwner)) {
            addPercept("kukta", ao);
        }

        // add beer "status" the percepts
        if (model.fridgeOpen) {
            addPercept("kukta", Literal.parseLiteral("stock(etel,"+model.availableBeers+")"));
        }
        if (model.sipCount > -1) //(model.sipCount > 0)
        {
            addPercept("kukta", hasSzakacsEtelParancs);
            addPercept("szakacs", hasSzakacsEtelParancs);
        }
    }


    
    @Override
    public boolean executeAction(String ag, Structure action) {
        System.out.println("\n\n\n\n["+ag+"] doing: "+action);
        boolean result = false;
        if (action.equals(openFridgeParancs)) 
        { // openFridgeParancs = open(fridge)
            result = model.openFridge();

        } 
        else if (action.equals(closeFridgeParancs)) 
        { // closeFridgeParancs = close(fridge)
            result = model.closeFridge();

        } 
        else if (action.getFunctor().equals("move_towards")) 
        {
            String l = action.getTerm(0).toString();
            Location dest = null;
            if (l.equals("fridge")) {
                dest = model.locationFridge;
            } else if (l.equals("szakacs")) {
                dest = model.locationOwner;
            }

            try {
                result = model.moveTowards(dest);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } 
        else if (action.equals(getEtelParancs)) 
        {
            System.out.println("ETEL KELL PARANCS FUTAS");
            result = model.getBeer();

        } 
        else if (action.equals(handInEtelParancs)) 
        {
            result = model.handInBeer();

        } 
        else if (action.equals(sb)) 
        {
            result = model.sipBeer();

        } 

        else if (action.equals(cookEtelParancs)) 
        {
            System.out.println("Cook kaja parancs!!!");
            result = model.cookFood();

        } 

        else if (action.getFunctor().equals("deliver")) 
        {
            // wait 4 seconds to finish "deliver"
            try {
                Thread.sleep(4000);
                result = model.addBeer( (int)((NumberTerm)action.getTerm(1)).solve());
            } catch (Exception e) {
                logger.info("Failed to execute action deliver!"+e);
            }

        } 
        else 
        {
            System.out.println("HIBA VOLT!");
            logger.info("Failed to execute action "+action);
        }

        if (result) 
        {
            updatePercepts();
            try {
                Thread.sleep(1000);
            } catch (Exception e) {}
        }
        return result;
    }
}
