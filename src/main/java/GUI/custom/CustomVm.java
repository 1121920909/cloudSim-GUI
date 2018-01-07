package GUI.custom;

import GUI.CustomSimulation;
import GUI.custom.add.AddVm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class CustomVm {
    private JPanel jPanel;
    private JTable tableVmList;
    private JButton buttonDelete;
    private JButton buttonAdd;
    private JButton buttonOk;
    private JPanel panelTitle;
    private JScrollPane panelMain;
    private JPanel panelButton;
    private JLabel labelTitle;
    private JLabel labelVmList;

    private JFrame frame;
    private CustomSimulation customSimulation;

    /**
     * 表头
     */
    private Object[] tableHead;

    /**
     * VM data
     */
    private Object[][] VmData;

    /**
     * table model
     */
    private DefaultTableModel tableModel;



    /**
     * vm last id
     */
    private int vmid = 0;

    public CustomVm(CustomSimulation customSimulation) {
        this.customSimulation = customSimulation;
        tableHead = new Object[]{"vmid","PeNum","Mips","Ram","BW","Size"};
        VmData = new Object[][]{};
        tableModel = new DefaultTableModel(VmData, tableHead);
        tableVmList.setModel(tableModel);
        //add button listener
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddVm.show(CustomVm.this);
            }
        });
        //delete button listener
        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int length = tableVmList.getSelectedRowCount();
                for(int i = 0; i<length;i++){
                    tableModel.removeRow(tableVmList.getSelectedRow());
                }
                Object[] array = tableModel.getDataVector().toArray();
                //重置VM ID
                int vmId = 0;
                for (Object o : array) {
                    Vector v = (Vector) o;
                    v.set(0,vmId);
                    vmId++;
                }
                //修改最后VM ID
                CustomVm.this.vmid = vmId;
            }
        });
        buttonOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customSimulation.setVmNum(vmid + 1);
                frame.setVisible(false);
            }
        });
    }

    public void show() {
        if (frame == null) {
            frame = new JFrame("CustomVm");
            frame.setContentPane(this.jPanel);
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            frame.pack();
        }
        if (!frame.isVisible()){
            frame.setVisible(true);
        }
    }

    /**
     * 往table中添加新的一行
     * @param peNum pe number
     * @param mips mips
     * @param ram ram
     * @param bw bw
     * @param size size
     */
    public void addVm(int peNum,int mips,int ram,int bw,long size){
        tableModel.addRow(new Object[]{vmid,peNum,mips,ram,bw,size});
        vmid++;
    }

    public Object[] getVmData() {
        return tableModel.getDataVector().toArray();
    }

}
