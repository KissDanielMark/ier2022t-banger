import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RendeloKiosk extends JFrame {
    JButton rendelBtn = new JButton("Rendelés");
    JComboBox<String> kajak = new JComboBox(new String[]{"Burger", "Pizza", "Spagetti"});
    JComboBox<String> asztalok = new JComboBox(new String[]{"asztal1", "asztal2", "asztal3"});

    public RendeloKiosk(){
        setLayout(new GridLayout());
        setSize(400, 400);
        rendelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HouseEnv.currentKaja = kajak.getSelectedIndex()+1;
                HouseEnv.currentAsztal = (String) asztalok.getSelectedItem();
                System.out.println("Választott kaja: " + kajak.getSelectedItem());
                System.out.println("Választott asztal: "+HouseEnv.currentAsztal);
            }
        });
        add(kajak);
        add(asztalok);
        add(rendelBtn);
        setVisible(true);
    }
}
