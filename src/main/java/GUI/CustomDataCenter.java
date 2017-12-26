package GUI;

import custom.CustomSimulation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomDataCenter {
    private JLabel titleLabel;
    private JTextField peMipsText;
    private JPanel pePanel;
    private JLabel peLabel;
    private JTextField ramText;
    private JTextField storageText;
    private JTextField bwText;
    private JTextField peNumText;
    private JLabel ramLabel;
    private JLabel storageLabel;
    private JLabel bwLabel;
    private JLabel peNumLabel;
    private JTextField costMemText;
    private JTextField costStorageText;
    private JTextField costBwText;
    private JTextField costSecText;
    private JPanel DatacenterCharactersiticsPanel;
    private JLabel costPerSecLabel;
    private JLabel costPerMemLabel;
    private JLabel costPerStorageLabel;
    private JLabel costPerBwLabel;
    private JButton confirmButton;
    private JTextField hostNumText;
    private JLabel hostNumLabel;
    private JPanel hostPanel;
    private JPanel titlePanel;
    private JPanel mainPanel;
    private JPanel confirmPanel;


    private static CustomSimulation simulation = null;
    private static JFrame frame = null;

    public CustomDataCenter() {
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int peNum;
                int peMIPS;
                int hostRam;
                int hostBw;
                long hostStorage;
                int hostNum;
                double dcPerCostSec;
                double dcPerCostMem;
                double dcPerCostBw;
                double dcPerCostStorage;

                peNum = Integer.valueOf(peNumText.getText());
                peMIPS = Integer.valueOf(peMipsText.getText());
                hostRam = Integer.valueOf(ramText.getText());
                hostBw = Integer.valueOf(bwText.getText());
                hostStorage = Long.valueOf(storageText.getText());
                hostNum = Integer.valueOf(hostNumText.getText());
                dcPerCostSec = Double.valueOf(costSecText.getText());
                dcPerCostMem = Double.valueOf(costMemText.getText());
                dcPerCostBw = Double.valueOf(costBwText.getText());
                dcPerCostStorage = Double.valueOf(costMemText.getText());

                simulation.setPeList(peMIPS,peNum);
                simulation.setHostList(hostRam,hostStorage,hostBw,hostNum);
                simulation.createDatacenterCharactersitics(dcPerCostSec,dcPerCostMem,dcPerCostStorage,dcPerCostBw);
                frame.setVisible(false);
            }
        });
    }

    public static void showCustomDatacenter(CustomSimulation inSimulation){
            simulation = inSimulation;

        if (frame == null) {
            frame = new JFrame("CusomDatacenter");
            frame.setContentPane(new CustomDataCenter().mainPanel);
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }
        frame.pack();
        frame.setVisible(true);
    }
}
