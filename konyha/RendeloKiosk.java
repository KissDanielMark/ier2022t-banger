import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RendeloKiosk extends JFrame {
    JPanel panel = new JPanel();
    static JLabel dellaLabel = new JLabel("Jövedelem: $0");
    JButton rendelBtn = new JButton("Rendelés");
    JComboBox<String> kajak = new JComboBox(new String[]{"Burger(1)", "Pizza(2)", "Spagetti(3)"});
    JComboBox<String> asztalok = new JComboBox(new String[]{"Asztal1", "Asztal2", "Asztal3"});

    public RendeloKiosk(){
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        setSize(400, 400);
        rendelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HouseEnv.rendelesek.add(new Rendeles((String) asztalok.getSelectedItem(), kajak.getSelectedIndex()+1));
                System.out.println("Beérkezett rendelés: " + (kajak.getSelectedIndex()+1)+" a(z) "+asztalok.getSelectedItem()+"-nál/nél!");
        
//                HouseEnv.currentKaja = kajak.getSelectedIndex()+1;
//                HouseEnv.currentAsztal = (String) asztalok.getSelectedItem();
            }
        });
        panel.add(dellaLabel);
        panel.add(kajak);
        panel.add(asztalok);
        panel.add(rendelBtn);
        add(panel);
        setVisible(true);
    }

    static void updateDella(int della){
        dellaLabel.setText("Jövedelem $" + della);
    }
}
