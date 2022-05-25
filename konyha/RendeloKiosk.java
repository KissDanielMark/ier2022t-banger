import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RendeloKiosk extends JFrame {
    JButton rendelBtn = new JButton("Rendelés");
    JComboBox<String> kajak = new JComboBox(new String[]{"Burger(1)", "Pizza(2)", "Spagetti(3)"});
    JComboBox<String> asztalok = new JComboBox(new String[]{"Asztal1", "Asztal2", "Asztal3"});

    public RendeloKiosk(){
        setLayout(new GridLayout());
        setSize(400, 400);
        rendelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HouseEnv.rendelesek.add(new Rendeles((String) asztalok.getSelectedItem(), kajak.getSelectedIndex()+1));
//                HouseEnv.currentKaja = kajak.getSelectedIndex()+1;
//                HouseEnv.currentAsztal = (String) asztalok.getSelectedItem();
            }
        });
        add(kajak);
        add(asztalok);
        add(rendelBtn);
        setVisible(true);
    }
}
