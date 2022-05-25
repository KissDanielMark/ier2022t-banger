import jason.asSyntax.*;
import jason.environment.Environment;
import jason.environment.grid.Location;
import java.util.logging.Logger;
import java.util.Random;

public class HouseEnv extends Environment {

    static String currentAsztal = "ures";
    static int currentKaja = 0;

    // common literals - itt van az atkapcsolas jason baszasbol
    public static final Literal openFridgeParancs  = Literal.parseLiteral("open(fridge)");
    public static final Literal closeFridgeParancs = Literal.parseLiteral("close(fridge)");
    public static final Literal getAlapAgParancs  = Literal.parseLiteral("get(alapanyag)");
    public static final Literal handInAlapAgParancs  = Literal.parseLiteral("hand_in(alapanyag)");
    public static final Literal kavarEtelParancs  = Literal.parseLiteral("kavar(etel)");
    public static final Literal hasSzakacsEtelParancs = Literal.parseLiteral("has(szakacs,alapanyag)");

    public static final Literal cookEtelParancs = Literal.parseLiteral("cook(etel)");
    public static final Literal doneParancs = Literal.parseLiteral("elkeszult(keszkaja)");

    public static final Literal atFridgeParancs = Literal.parseLiteral("at(kukta,fridge)");
    public static final Literal ao = Literal.parseLiteral("at(kukta,szakacs)");

    public static final Literal felveszParancs = Literal.parseLiteral("felvesz(keszkaja)");
    public static final Literal felszolgalParancs = Literal.parseLiteral("felszolgal(keszkaja)");
    public static final Literal serveParancs = Literal.parseLiteral("serve(keszkaja)");
    public static final Literal hasSzakacsKeszkajaParancs = Literal.parseLiteral("has(szakacs,keszkaja)");
    public static final Literal pincerAtSzakacs = Literal.parseLiteral("at(pincer,szakacs)");
    public static final Literal pincerAtAsztal1 = Literal.parseLiteral("at(pincer,asztal1)");
    public static final Literal pincerAtAsztal2 = Literal.parseLiteral("at(pincer,asztal2)");
    public static final Literal pincerAtAsztal3 = Literal.parseLiteral("at(pincer,asztal3)");

    static Logger logger = Logger.getLogger(HouseEnv.class.getName());

    HouseModel model; // the model of the grid

    @Override
    public void init(String[] args) {
        //MAS consolban jelenik meg ez!!!
        //logger.info("Elindult a java");
        System.out.println("Itt indul a JAVA program....");
        model = new HouseModel();

        RendeloKiosk k = new RendeloKiosk();
        
        if (args.length == 1 && args[0].equals("gui")) {
            System.out.println("Kell GUI");
            HouseView view  = new HouseView(model);
            model.setView(view);
        }

        updatePercepts();
    }

    
    //creates the agents percepts(felfogas) based on the HouseModel 
    void updatePercepts() {
        // clear the percepts of the agents
        clearPercepts("kukta");
        clearPercepts("szakacs");
        clearPercepts("pincer");

        addPercept("pincer", Literal.parseLiteral("rendeles("+ currentAsztal +")"));

        // get the robot location
        Location locationRobot = model.getAgPos(0);

        // add agent location to its percepts
        if (locationRobot.equals(model.locationFridge)) {
            addPercept("kukta", atFridgeParancs);
        }
        if (locationRobot.equals(model.locationChef)) {
            addPercept("kukta", ao);
        }

        Location locationPincer = model.getAgPos(1);
        if(locationPincer.equals(model.locationChef)){
            addPercept("pincer", pincerAtSzakacs);
        }
        if(locationPincer.equals(model.locationAsztal1)){
            addPercept("pincer", pincerAtAsztal1);
        } else if(locationPincer.equals(model.locationAsztal2)){
            addPercept("pincer", pincerAtAsztal2);
        } else if(locationPincer.equals(model.locationAsztal3)){
            addPercept("pincer", pincerAtAsztal3);
        }

        if (model.fridgeOpen) {
            addPercept("kukta", Literal.parseLiteral("stock(alapanyag,"+(model.availableIngredient)+")"));
        }
        if (model.kavarCount > 0)
        {
            addPercept("kukta", hasSzakacsEtelParancs);
            addPercept("szakacs", hasSzakacsEtelParancs);
        }
        if(model.foodReady){
            addPercept("pincer", hasSzakacsKeszkajaParancs);
            addPercept("szakacs", hasSzakacsKeszkajaParancs);
        }
    }


    
    @Override
    public boolean executeAction(String ag, Structure action) {
        //System.out.println("\n["+ag+"] doing: "+action+"\n");
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
                dest = model.locationChef;
            } else if (l.equals("asztal1")){
                dest = model.locationAsztal1;
            } else if (l.equals("asztal2")){
                dest = model.locationAsztal2;
            } else if (l.equals("asztal3")){
                dest = model.locationAsztal3;
            }

            try {
                if(ag.equals("pincer")){
                    if(!l.equals("ures")) {
                        result = model.moveTowards(dest, 1);
                    }
                } else {
                    result = model.moveTowards(dest, 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else if (action.getFunctor().equals("rendeles")){
            String a = action.getTerm(0).toString();
            if(!a.equals("ures")) {
                result = true;
            }
        }
        else if (action.equals(getAlapAgParancs))
        {
            result = model.getAlapAg(currentKaja);
        } 
        else if (action.equals(handInAlapAgParancs))
        {
            result = model.handInAlapAg();
        } 
        else if (action.equals(kavarEtelParancs))
        {
            result = model.kavarEtel();
        }
        else if (action.equals(cookEtelParancs)) 
        {
            result = model.cookFood();
        }

        else if (action.getFunctor().equals("deliver")) 
        {
            // wait 3 seconds to finish "deliver"
            try {
                Thread.sleep(3000);
                result = model.addAlapAg( (int)((NumberTerm)action.getTerm(1)).solve());
            } catch (Exception e) {
                logger.info("Failed to execute action deliver!"+e);
            }

        } else if(action.equals(felveszParancs)){
            //todo
            result = true;
        } else if(action.equals(felszolgalParancs)){
            model.foodReady = false;
            currentAsztal = "ures";
            result = true;
        } else if(action.equals(serveParancs)){
            result = true;
        } else if(action.equals(doneParancs)){
            System.out.println("Kész a kaja");
            model.foodReady = true;
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
                Thread.sleep(250);
            } catch (Exception e) {}
        }
        return result;
    }
}
