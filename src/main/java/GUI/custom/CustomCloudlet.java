package GUI.custom;

import GUI.CustomSimulation;
import GUI.custom.add.AddCloudlet;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.regex.Pattern;

public class CustomCloudlet {
    private JTable tableCloudletList;
    private JButton buttonDelete;
    private JButton buttonAdd;
    private JButton buttonOk;
    private JPanel panelTitle;
    private JPanel panelMain;
    private JPanel panelBottom;
    private JLabel labelTitle;
    private JLabel labelCloudletList;
    private JPanel jPanel;

    private JFrame frame;
    private CustomSimulation customSimulation;

    private DefaultTableModel cloudletTableModel;
    private Object[] header;
    private Object[][] cloudletData;

    private Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");

    /**
     * 最后一个cloudlet的ID
     */
    private int lastCloudletId = 0;

    public CustomCloudlet(CustomSimulation customSimulation) {
        this.customSimulation = customSimulation;
        header = new Object[]{"id","PENum","length","File Size","OUTPUT Size"};
        cloudletData = new Object[][]{};
        cloudletTableModel = new DefaultTableModel(cloudletData, header);
        cloudletTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                String s = (String) cloudletTableModel.getValueAt(row,column);

            }
        });
        tableCloudletList.setModel(cloudletTableModel);
        //delete button listener
        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int length = tableCloudletList.getSelectedRowCount();
                for(int i = 0; i<length;i++){
                    cloudletTableModel.removeRow(tableCloudletList.getSelectedRow());
                }
                Object[] array = cloudletTableModel.getDataVector().toArray();
                //重置Cloudlet ID
                int cloudletId = 0;
                for (Object o : array) {
                    Vector v = (Vector) o;
                    v.set(0,cloudletId);
                    cloudletId++;
                }
                //修改最后CloudletId
                lastCloudletId = cloudletId;
            }
        });
        //add button listener
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddCloudlet.show(CustomCloudlet.this);
            }
        });
        //ok button listener
        buttonOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Test
                System.out.println("Test Ok");
                Object[] data = cloudletTableModel.getDataVector().toArray();
                for (Object o : data) {
                    System.out.println(o);
                }
                /*for (Object[] data : cloudletData) {
                    for(Object s : data){
                        System.out.print(s + "\t");
                    }
                    System.out.println();
                }*/
            }
        });
        buttonOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customSimulation.setCloudletNum(lastCloudletId + 1);
                frame.setVisible(false);
            }
        });
    }

    public void addCloudlet(long length,long fileSize,long outputSize,int peNum) {
        cloudletTableModel.addRow(new Object[]{lastCloudletId,peNum,length,fileSize,outputSize});
        lastCloudletId++;
    }

    public void show() {
        if (frame == null) {
            frame = new JFrame("CustomCloudlet");
            frame.setContentPane(this.jPanel);
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            frame.pack();
        }
        if (!frame.isVisible()){
            frame.setVisible(true);
        }
    }



    public Object[] getCloudletData() {
        return cloudletTableModel.getDataVector().toArray();
    }

    private boolean isNumber(String s) {
        return pattern.matcher(s).matches();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("CustomCloudlet");
        frame.setContentPane(new CustomCloudlet(new CustomSimulation()).jPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
