package GUI.custom;

import GUI.CustomSimulation;
import GUI.custom.add.AddHost;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class CustomCenter {
    private JPanel panel;
    private JPanel titlePanel;
    private JPanel hostPanel;
    private JPanel datacenterCharacteristicsPanel;
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

    private JFrame frame = null;
    private CustomSimulation customSimulation;
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
    private int hostId = 0;

    public CustomCenter(CustomSimulation customSimulation) {
        this.customSimulation = customSimulation;
        hostTableHead = new Object[]{"host id","Pe Number","Pe MIPS","Ram","Storage","BW"};
        hostData = new Object[][]{};
        hostTableModel = new DefaultTableModel(hostData,hostTableHead);
        hostListTable.setModel(hostTableModel);
        //add button listener
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddHost.show(CustomCenter.this);
            }
        });
        //delete button listener
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int length = hostListTable.getSelectedRowCount();
                for(int i = 0; i<length;i++){
                    hostTableModel.removeRow(hostListTable.getSelectedRow());
                }
                Object[] array = hostTableModel.getDataVector().toArray();
                //重置hostId
                int hostId = 0;
                for (Object o : array) {
                    Vector v = (Vector) o;
                    v.set(0,hostId);
                    hostId++;
                }
                //修改最后hostId
                CustomCenter.this.hostId = hostId;
            }
        });
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //数据效验
                frame.setVisible(false);
            }
        });
    }

    public void show() {
        if (frame == null) {
            frame = new JFrame("CustomCenter");
            frame.setContentPane(this.panel);
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            frame.pack();
        }
        if (!frame.isVisible()){
            frame.setVisible(true);
        }
    }


    public void addHostData(int PeNum,long storage,int bw,int mips,int ram){
        hostTableModel.addRow(new Object[]{hostId,PeNum,mips,ram,storage,bw});
        hostId++;
    }

    public Object[] getHostData() {
        return hostTableModel.getDataVector().toArray();
    }

    public double[] getCenterCharacteristicsData() {
        return new double[]{Double.valueOf(costPerBWText.getText()),Double.valueOf(costPerMemText.getText()),
                Double.valueOf(costPerSecText.getText()),Double.valueOf(costPerStorageText.getText())};
    }
}
