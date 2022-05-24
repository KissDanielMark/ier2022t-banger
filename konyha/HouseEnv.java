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
    public static final Literal sipEtelParancs  = Literal.parseLiteral("sip(etel)");
    public static final Literal hasSzakacsEtelParancs = Literal.parseLiteral("has(szakacs,etel)");

    public static final Literal cookEtelParancs = Literal.parseLiteral("cook(etel)");
    public static final Literal doneParancs = Literal.parseLiteral("elkeszult(keszetel)");
    public static final Literal prepareEtelParancs = Literal.parseLiteral("prepare(etel)");

    public static final Literal atFridgeParancs = Literal.parseLiteral("at(kukta,fridge)");
    public static final Literal ao = Literal.parseLiteral("at(kukta,szakacs)");

    public static final Literal felveszParancs = Literal.parseLiteral("felvesz(keszkaja)");
    public static final Literal felszolgalParancs = Literal.parseLiteral("felszolgal(keszkaja)");
    public static final Literal serveParancs = Literal.parseLiteral("serve(keszkaja)");
    public static final Literal hasSzakacsKeszkajaParancs = Literal.parseLiteral("has(szakacs,keszkaja)");
    public static final Literal pincerAtSzakacs = Literal.parseLiteral("at(pincer,szakacs)");
    public static final Literal pincerAtAsztal = Literal.parseLiteral("at(pincer,asztal)");

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
        clearPercepts("pincer");

        // get the robot location
        Location locationRobot = model.getAgPos(0);

        // add agent location to its percepts
        if (locationRobot.equals(model.locationFridge)) {
            addPercept("kukta", atFridgeParancs);
        }
        if (locationRobot.equals(model.locationOwner)) {
            addPercept("kukta", ao);
        }

        Location locationPincer = model.getAgPos(1);
        if(locationPincer.equals(model.locationOwner)){
            addPercept("pincer", pincerAtSzakacs);
        }
        if(locationPincer.equals(model.locationAsztal)){
            addPercept("pincer", pincerAtAsztal);
        }

        // add beer "status" the percepts
        if (model.fridgeOpen) {
            addPercept("kukta", Literal.parseLiteral("stock(etel,"+model.availableBeers+")"));
        }
        if (model.sipCount > 0) //(model.sipCount > 0)
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
                dest = model.locationOwner;
            } else if (l.equals("asztal")){
                dest = model.locationAsztal;
            }

            try {
                if(ag.equals("pincer")){
                    result = model.moveTowards(dest, 1);
                } else {
                    result = model.moveTowards(dest, 0);
                }
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
            System.out.println("KAJAATADAS PARANCS FUT");
            result = model.handInBeer();

        } 
        else if (action.equals(sipEtelParancs)) 
        {
            System.out.println("CSINALJ VELE VALAMIT BUZI SZAKACS");
            result = model.sipBeer();

        } 

        else if (action.equals(cookEtelParancs)) 
        {
            System.out.println("Cook kaja parancs!!!");
            result = model.cookFood();

        } 

        else if (action.equals(prepareEtelParancs)) 
        {
            System.out.println("Prepare kaja parancs!!!");
            result = model.prepareFood();
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

        } else if(action.equals(felveszParancs)){
            //todo
            System.out.println("Felvettem yeeee");
            result = true;
        } else if(action.equals(felszolgalParancs)){
            //todo
            model.foodReady = false;
            System.out.println("FELSZOLGALTAM AAAAAAAAAAAAAAAAAA");
            result = true;
        } else if(action.equals(serveParancs)){
            System.out.println("SERVEEEEEEEEEEEEEEEEEE");
            result = true;
        } else if(action.equals(doneParancs)){
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
                Thread.sleep(500);
            } catch (Exception e) {}
        }
        return result;
    }
}
