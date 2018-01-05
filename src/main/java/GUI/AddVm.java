package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class AddVm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textVmNum;
    private JTextField textMips;
    private JTextField textBw;
    private JTextField textPeNum;
    private JTextField textRam;
    private JTextField textSize;
    private JLabel labelTitle;
    private JPanel panelTitle;
    private JPanel panelCustom;
    private JPanel panelBotton;
    private JLabel labelVmNum;
    private JLabel labelPeNum;
    private JLabel labelMips;
    private JLabel labelRam;
    private JLabel labelBw;
    private JLabel labelSize;

    private CustomVm customVm;

    public AddVm(CustomVm customVm) {
        this.customVm = customVm;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        int vmNum = Integer.valueOf(textVmNum.getText());
        int peNum = Integer.valueOf(textPeNum.getText());
        int mips = Integer.valueOf(textMips.getText());
        int size = Integer.valueOf(textSize.getText());
        int ram = Integer.valueOf(textRam.getText());
        int bw = Integer.valueOf(textBw.getText());

        for (int i = 0; i < vmNum; i ++) {
            customVm.addVm(peNum,mips,ram,bw,size);
        }
            dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void show(CustomVm customVm) {
        AddVm dialog = new AddVm(customVm);
        dialog.pack();
        dialog.setVisible(true);
       // System.exit(0);
    }
}
