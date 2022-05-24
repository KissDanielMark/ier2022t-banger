import jason.environment.grid.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


/** class that implements the View of Domestic Robot application */
public class HouseView extends GridWorldView {

    HouseModel hmodel;

    public HouseView(HouseModel model) {
        super(model, "Banger - konyha", 700);
        hmodel = model;
        defaultFont = new Font("Arial", Font.BOLD, 16); // change default font
        setVisible(true);
        repaint();
    }

    /** draw application objects */
    @Override
    public void draw(Graphics g, int x, int y, int object) {
        Location lRobot = hmodel.getAgPos(0);
        super.drawAgent(g, x, y, Color.lightGray, -1);
        switch (object) {


        case HouseModel.FRIDGE:
            if (lRobot.equals(hmodel.locationFridge)) {
                super.drawAgent(g, x, y, Color.yellow, -1);
            }
            g.setColor(Color.black);
            drawString(g, x, y, defaultFont, "Fridge ("+hmodel.availableBeers+")");
            break;


        case HouseModel.OWNER:
            if (lRobot.equals(hmodel.locationOwner)) {
                super.drawAgent(g, x, y, Color.yellow, -1);
            }
            String o = "Szakacs";
            if (hmodel.sipCount > 0) {
                o +=  " ("+hmodel.sipCount+")";
            }
            g.setColor(Color.black);
            drawString(g, x, y, defaultFont, o);
            break;
            
        }
    }

    @Override
    public void drawAgent(Graphics g, int x, int y, Color c, int id) {
        if(id == 0){
            Location lRobot = hmodel.getAgPos(0);
            if (!lRobot.equals(hmodel.locationOwner) && !lRobot.equals(hmodel.locationFridge)) {
                c = Color.yellow;
                if (hmodel.carryingBeer) c = Color.orange;
                super.drawAgent(g, x, y, c, -1);
                g.setColor(Color.black);
                super.drawString(g, x, y, defaultFont, "Kukta");
            }
        } else if(id == 1){
            Location lRobot = hmodel.getAgPos(1);
            c = Color.green;
            super.drawAgent(g, x, y, c, -1);
            g.setColor(Color.black);
            super.drawString(g, x, y, defaultFont, "Pincer");
        }

    }
}
