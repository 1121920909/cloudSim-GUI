package GUI;

import org.cloudbus.cloudsim.Vm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CustomVm {
    private JTable tableVmList;
    private JButton buttonDelete;
    private JButton buttonAdd;
    private JButton buttonOk;
    private JPanel panelTitle;
    private JScrollPane panelMain;
    private JPanel panelButton;
    private JLabel labelTitle;
    private JLabel labelVmList;

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

    public CustomVm() {
        tableHead = new Object[]{"vmid","PeNum","Mips","Ram","BW","Size"};
        VmData = new Object[][]{};
        tableModel = new DefaultTableModel(VmData, tableHead);
    }

    public void addVm(int peNum,int mips,int ram,int bw,int size){
        tableModel.addRow(new Object[]{vmid,peNum,mips,ram,bw,size});
        vmid++;
    }
}
