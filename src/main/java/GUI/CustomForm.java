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
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JPanel datacenterPanel;
    private JPanel vmPanel;
    private JPanel cloudletPanel;
    private JTextField datacenterNumText;
    private JButton customDatacentButton;
    private JLabel datacenterNumLabel;
    private JTextField vmPeNumText;
    private JTextField vmMipsText;
    private JTextField vmRamText;
    private JTextField vmSizeText;
    private JTextField vmBwText;
    private JLabel mipsLabel;
    private JLabel peNumLabel;
    private JLabel ramLabel;
    private JLabel bwLabel;
    private JLabel sizeLabel;
    private JLabel vmNumLabel;
    private JTextField cloLengthText;
    private JTextField cloPeNumText;
    private JTextField cloFileSizeText;
    private JTextField cloOutputSizeText;
    private JTextField cloNumText;
    private JLabel lengthLabel;
    private JLabel cluPeNumLabel;
    private JLabel fileSizeLabel;
    private JLabel outputSizeLabel;
    private JLabel cloNumLabel;
    private JButton simulationButton;
    private JPanel buttonPanel;
    private JTextField vmNumText;
    private JLabel policyLabel;
    private JComboBox cloPolicyCombo;

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
