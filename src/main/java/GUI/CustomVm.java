package GUI;

import org.cloudbus.cloudsim.Vm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        tableVmList.setModel(tableModel);
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddVm.show(getThis());
            }
        });
        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int length = tableVmList.getSelectedRowCount();
                for(int i = 0; i<length;i++){
                    tableModel.removeRow(tableVmList.getSelectedRow());
                }
            }
        });
    }

    public void addVm(int peNum,int mips,int ram,int bw,int size){
        tableModel.addRow(new Object[]{vmid,peNum,mips,ram,bw,size});
        vmid++;
    }

    private CustomVm getThis(){
        return this;
    }
}
