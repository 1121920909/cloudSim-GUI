package GUI;

import GUI.custom.CustomCenter;
import GUI.custom.CustomCloudlet;
import GUI.custom.CustomVm;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class CustomSimulation {
    private JPanel titlePanel;
    private JPanel mainPanel;
    private JPanel runPanel;
    private JLabel titleLabel;
    private JButton customDatacenterButton;
    private JButton customVmButton;
    private JButton customCloudletButton;
    private JButton runButton;
    private JLabel customDatacenterLabel;
    private JLabel customVmLabel;
    private JLabel customCloudletLabel;
    private JLabel vmNumLabel;
    private JLabel cloudletNumLabel;
  private JPanel panel;

  /**
     * custom包中的CustomSimulation。。。不小心命名重了
     */
    private custom.CustomSimulation simulation;

    /**
     * CustomCenter 窗体对象
     */
    private CustomCenter customCenter;
    /**
     * CustomCloudlet窗体对象
     */
    private CustomCloudlet customCloudlet;
    /**
     * CustomVm窗体对象
     */
    private CustomVm customVm;

    public CustomSimulation() {
        simulation = new custom.CustomSimulation();
        customCenter = new CustomCenter(this);
        customCloudlet = new CustomCloudlet(this);
        customVm = new CustomVm(this);

        //customDatacenterButton listener
        customDatacenterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customCenter.show();
            }
        });

        //customVmButton listener
        customVmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customVm.show();
            }
        });

        //customCloudletButton listener
      customCloudletButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          customCloudlet.show();
        }
      });
      //runButton listener
      runButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          GUI.CustomSimulation.this.runSimulation();
        }
      });
    }

  private void runSimulation() {
    //create Host
    Object[] hostData = customCenter.getHostData();
    List<Pe> peList;
    int peId = 0;
    int hostPeNum;
    int hostPeMips;
    int hostId;
    int hostRam;
    long hostStorage;
    int hostBw;
    for (Object o : hostData) {
      peList = new ArrayList<>();
      Vector data = (Vector) o;
      hostId = (int)data.get(0);
      hostPeNum = (int) data.get(1);
      hostPeMips = (int) data.get(2);
      hostRam = (int) data.get(3);
      hostStorage = (long) data.get(4);
      hostBw = (int)data.get(5);
      for (int i = 0; i < hostPeNum; i ++) {
        peList.add(new Pe(peId, new PeProvisionerSimple(hostPeMips)));
        peId++;
      }
      simulation.addHost(hostId,hostRam,hostStorage,hostBw,peList);
    }
    //create Datacenter
    double[] dataceterChars = customCenter.getCenterCharacteristicsData();
    double costPerSec = dataceterChars[2];
    double costPerMem = dataceterChars[1];
    double costPerBw = dataceterChars[0];
    double costPerStorage = dataceterChars[3];
    simulation.createDatacenterCharacteristics(costPerSec,costPerMem,costPerStorage,costPerBw);
    //addVM
    Object[] VmData = customVm.getVmData();
    //int vmId,int peNum,int mips,int ram,int bw,long size
    int vmId;
    int vmPeNum;
    int vmPeMips;
    int vmRam;
    int vmBw;
    long vmSize;
    for (Object o : VmData) {
      Vector v = (Vector) o;
      vmId = (int)v.get(0);
      vmPeNum = (int) v.get(1);
      vmPeMips = (int) v.get(2);
      vmRam = (int) v.get(3);
      vmBw = (int) v.get(4);
      vmSize = (long) v.get(5);
      simulation.addVm(vmId,vmPeNum,vmPeMips,vmRam,vmBw,vmSize);
    }
    //addCloudlet
    Object[] cloudletData = customCloudlet.getCloudletData();
    //int cloudletId,long length,long fileSize,long outputSize,int peNum
    int cloudletId;
    long cloudletLength;
    long cloudletFileSize;
    long cloudletOutputSize;
    int cloudletPeNum;
    for (Object o : cloudletData) {
      Vector v = (Vector)o;
      cloudletId = (int) v.get(0);
      cloudletPeNum = (int) v.get(1);
      cloudletLength = (long) v.get(2);
      cloudletFileSize = (long) v.get(3);
      cloudletOutputSize = (long) v.get(4);
      simulation.addCloudlet(cloudletId,cloudletLength,cloudletFileSize,cloudletOutputSize,cloudletPeNum);
    }
    //simulation start
    simulation.startSimulation();
    System.out.println(simulation.formatInfo());
  }

  public static void show() {
    JFrame frame = new JFrame("CustomSimulation");
    frame.setContentPane(new CustomSimulation().panel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("CustomSimulation");
    frame.setContentPane(new CustomSimulation().panel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }
}
