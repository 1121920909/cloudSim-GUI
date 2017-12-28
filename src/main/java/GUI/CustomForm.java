package GUI;

import GUI.CustomDataCenter;
import com.sun.javaws.util.JfxHelper;
import custom.CustomSimulation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
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

    /**
     * 表头
     */
    private Object[] hostTableHead;

    /**
     * host 数据
     */
    private Object[][] hostData;

    /**
     * tableModel
     */
    private DefaultTableModel hostTableModel = null;

    /**
     * host last id
     */
    private int HostId = 0;

    public CustomForm() {
        hostTableHead = new Object[]{"host id","Pe Number","Pe MIPS","Ram","Storage","BW"};
        hostData = new Object[][]{};
        hostTableModel = new DefaultTableModel(hostData,hostTableHead);
        hostListTable.setModel(hostTableModel);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddHost.show(getThis());
            }
        });
        //删除选中行
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int length = hostListTable.getSelectedRowCount();
                for(int i = 0; i<length;i++){
                    hostTableModel.removeRow(hostListTable.getSelectedRow());
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("CustomForm");
        frame.setContentPane(new CustomForm().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void addHostData(int PeNum,long storage,int bw,int mips,int ram){
        hostTableModel.addRow(new Object[]{HostId,PeNum,mips,ram,storage,bw});
        HostId++;
    }

    public CustomForm getThis(){
        return this;
    }
}
