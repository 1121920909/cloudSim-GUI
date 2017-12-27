package GUI;

import GUI.CustomDataCenter;
import com.sun.javaws.util.JfxHelper;
import custom.CustomSimulation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomForm {
    private JPanel panel;
    private JPanel titlePanel;
    private JPanel HostPanel;
    private JPanel DatacenterCharacteristicsPanel;
    private JPanel okPanel;
    private JLabel titleLabel;
    private JButton okButton;
    private JTable hostListTable;
    private JButton addButton;
    private JButton deleteButton;
    private JTextField costPerSecText;
    private JTextField costPerStorageText;
    private JTextField costPerBWText;
    private JTextField costPerMemText;
    private JLabel hostListTableLabel;
    private JLabel costPerSecLabel;
    private JLabel costPerMemLabel;
    private JLabel costPerStorageLabel;
    private JLabel costPerBWLabel;
    private JTextField datacenterNumText;
    private JButton customDatacentButton;
    private JTextField vmPeNumText;
    private JTextField vmMipsText;
    private JTextField vmRamText;
    private JTextField vmSizeText;
    private JTextField vmBwText;
    private JTextField cloLengthText;
    private JTextField cloPeNumText;
    private JTextField cloFileSizeText;
    private JTextField cloOutputSizeText;
    private JTextField cloNumText;
    private JButton simulationButton;
    private JTextField vmNumText;

    private CustomSimulation simulation;
    private static JFrame frame = null;

    public CustomForm() {
        simulation = new CustomSimulation();
        customDatacentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomDataCenter.showCustomDatacenter(simulation);
            }
        });
        simulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int datacenterNum;
                int vmMips;
                int vmPeNum;
                int vmRam;
                int vmBw;
                long vmSize;
                int vmNum;
                long cloudletLength;
                int cloudletNum;
                int cloudletPeNum;
                int cloudletFileSize;
                int cloudletOutputSize;
                String bindCloudletToVmsPolicy;

                datacenterNum = Integer.valueOf(datacenterNumText.getText());
                vmMips = Integer.valueOf(vmMipsText.getText());
                vmPeNum = Integer.valueOf(vmPeNumText.getText());
                vmRam = Integer.valueOf(vmRamText.getText());
                vmBw = Integer.valueOf(vmBwText.getText());
                vmNum = Integer.valueOf(vmNumText.getText());
                vmSize = Long.valueOf(vmSizeText.getText());
                cloudletLength = Long.valueOf(cloLengthText.getText());
                cloudletNum = Integer.valueOf(cloNumText.getText());
                cloudletPeNum = Integer.valueOf(cloPeNumText.getText());
                cloudletFileSize = Integer.valueOf(cloFileSizeText.getText());
                cloudletOutputSize = Integer.valueOf(cloOutputSizeText.getText());

                simulation.setDatacenterList(datacenterNum);
                simulation.setVmList(vmNum,vmMips,vmPeNum,vmRam,vmBw,vmSize);
                simulation.setCloudletList(cloudletLength, cloudletFileSize, cloudletOutputSize, cloudletPeNum, cloudletNum);
                simulation.simulationStart();
                ResultForm.show(simulation.formatInfo());
            }
        });
    }

    public static void show() {
        if (frame == null) {
            frame = new JFrame("CustomForm");
            frame.setContentPane(new CustomForm().panel);
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }
        frame.pack();
        frame.setVisible(true);
    }
}
