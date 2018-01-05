package GUI.custom.add;

import GUI.custom.CustomCloudlet;

import javax.swing.*;
import java.awt.event.*;

public class AddCloudlet extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textCloudletNum;
    private JTextField textPeNum;
    private JTextField textFileSize;
    private JTextField textOutputSize;
    private JTextField textLength;
    private JPanel panelTitle;
    private JPanel panelMain;
    private JLabel labelTitle;
    private JLabel labelPeNum;
    private JLabel labelLength;
    private JLabel labelFileSize;
    private JLabel labelOutPutSize;
    private JLabel labelCloudletNum;

    private CustomCloudlet customCloudlet;

    public AddCloudlet(CustomCloudlet customCloudlet) {
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

        this.customCloudlet = customCloudlet;
    }

    private void onOK() {
        // add your code here

        int cloudletNum = Integer.valueOf(textCloudletNum.getText());
        int peNum = Integer.valueOf(textPeNum.getText());
        long length = Long.valueOf(textLength.getText());
        long fileSize = Long.valueOf(textFileSize.getText());
        long outputSize = Long.valueOf(textOutputSize.getText());

        for (int i = 0; i < cloudletNum; i++) {
            customCloudlet.addCloudlet(length,fileSize,outputSize,peNum);
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void show(CustomCloudlet customCloudlet) {
        AddCloudlet dialog = new AddCloudlet(customCloudlet);
        dialog.pack();
        dialog.setVisible(true);
    }

}
