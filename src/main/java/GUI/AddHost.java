package GUI;

import javax.swing.*;
import java.awt.event.*;

public class AddHost extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textHostNum;
    private JTextField textPeNum;
    private JTextField textRam;
    private JTextField textPeMips;
    private JTextField textStorage;
    private JTextField textBw;
    private JPanel titlePanel;
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JLabel titleLabel;
    private JLabel hostNumLabel;
    private JLabel peNumLabel;
    private JLabel peMipsLabel;
    private JLabel ramLabel;
    private JLabel storageLabel;
    private JLabel bwLabel;

    /**
     * CustomForm
     */
    private CustomForm customForm;
    public AddHost() {
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
    }

    private void onOK() {
        // add your code here
        int hostNum = Integer.valueOf(textHostNum.getText());
        int peMips = Integer.valueOf(textPeMips.getText());
        int peNum = Integer.valueOf(textPeNum.getText());
        long storage = Long.valueOf(textStorage.getText());
        int bw = Integer.valueOf(textBw.getText());
        int ram = Integer.valueOf(textRam.getText());

        for(int i = 0; i < hostNum; i++) {
            customForm.addHostData(peNum,storage,bw,peMips,ram);
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void show(CustomForm customForm){
        AddHost dialog = new AddHost();
        dialog.customForm = customForm;
        dialog.pack();
        dialog.setVisible(true);
        //System.exit(0);
    }
}
