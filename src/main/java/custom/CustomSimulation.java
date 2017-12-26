package custom;

import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;
import sun.misc.VM;
import sun.security.util.PendingException;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

public class CustomSimulation {
    private List<Vm> vmList;
    private List<Pe> peList;
    private List<Host> hostList;
    private DatacenterCharacteristics datacenterCharacteristics;
    private List<Datacenter> datacenterList;
    private List<Cloudlet> cloudletList;
    private List<Cloudlet> resultList;
    private DatacenterBroker broker;

    public CustomSimulation() {
        vmList = new ArrayList<Vm>();
        peList = new ArrayList<Pe>();
        hostList = new ArrayList<Host>();
        datacenterList = new ArrayList<Datacenter>();
        cloudletList = new ArrayList<Cloudlet>();
        CloudSim.init(1, Calendar.getInstance(),false);
        broker = createBroker();
    }

    private DatacenterBroker createBroker(){
        DatacenterBroker broker = null;
        if (broker == null) {
            try {
                broker = new DatacenterBroker("Broker");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return broker;
    }

    public void setPeList(int mips,int num){
        peList.clear();
        for(int n = 0;n < num;n ++){
            peList.add(new Pe(n + 1,new PeProvisionerSimple(mips)));
        }
    }

    public void setHostList(int ram,long storage,int bw,int num){
        hostList.clear();
        for(int i = 0;i < num;i++) {
            hostList.add(new Host(i+1,
                    new RamProvisionerSimple(ram),
                    new BwProvisionerSimple(bw),
                    storage, peList,
                    new VmSchedulerSpaceShared(peList)));
        }
    }

    public void createDatacenterCharactersitics(double costPerSec,double costPerMem,double costPerStorage,double costPerBW){
        datacenterCharacteristics = new DatacenterCharacteristics("x86","Linux","Xen",
                                        hostList,10.0,costPerSec,costPerMem,costPerStorage,costPerBW);
    }

    public void setDatacenterList(int num) {
        datacenterList.clear();
        String name = "Datacenter_";
        try {
            for (int i = 0; i < num; i++) {
                datacenterList.add(new Datacenter(name + i, datacenterCharacteristics,
                        new VmAllocationPolicySimple(hostList),
                        new ArrayList<Storage>(), 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setVmList(int vmNum,int mips,int peNum,int ram,int bw,long size){
        vmList.clear();
        for(int i = 0; i < vmNum; i++){
            vmList.add(new Vm(i,broker.getId(),mips,peNum,ram,bw,size,"Xen",new CloudletSchedulerSpaceShared()));
        }
    }

    public void setCloudletList(long length,long fileSize,long outputSize,int peNum,int num){
        cloudletList.clear();
        UtilizationModel utilizationModel = new UtilizationModelFull();
        for(int i = 0; i < num; i++) {
         Cloudlet cloudlet =  new Cloudlet(i,length,peNum,fileSize,outputSize,
                 utilizationModel,utilizationModel,utilizationModel);
         cloudlet.setUserId(broker.getId());
         cloudletList.add(cloudlet);
        }
    }

    public void simulationStart(){
        broker.submitVmList(vmList);
        broker.submitCloudletList(cloudletList);
        int vmId;
        for(int i = 0;i < cloudletList.size();i++) {
            vmId = (int)(Math.random() * vmList.size());
            broker.bindCloudletToVm(cloudletList.get(i).getCloudletId(),vmId);
        }
        CloudSim.startSimulation();
        resultList = broker.getCloudletReceivedList();
        CloudSim.stopSimulation();

    }

    public String formatInfo(){
        StringBuilder sb = new StringBuilder();
        Cloudlet cloudlet;
        String indent = "\t";
        sb.append("========== OUTPUT ==========\n");
        sb.append("Cloudlet ID\tSTATUS\tData center ID\tVM ID\tTime\tStart Time\tFinish Time\n");
        DecimalFormat dft = new DecimalFormat("###.##");
        for(int i = 0;i < resultList.size();i++){
            cloudlet = resultList.get(i);
            sb.append(cloudlet.getCloudletId() + indent);
            if(cloudlet.getStatus() == Cloudlet.SUCCESS){
                sb.append("SUCCESS" + indent + cloudlet.getResourceId()
                        + indent + cloudlet.getVmId()
                        + indent
                        + dft.format(cloudlet.getActualCPUTime()) + indent
                        + dft.format(cloudlet.getExecStartTime())
                        + indent
                        + dft.format(cloudlet.getFinishTime()) + "\n");
            }
        }
        return sb.toString();
    }

    public void bindCloudletToVmsTimeAwared(){
        double[][] times = new double[cloudletList.size()][vmList.size()] ;
        vmList.sort(new Comparator<Vm>() {
            @Override
            public int compare(Vm o1, Vm o2) {
                if(o1.getMips() > o2.getMips()){
                    return -1;
                }
                else{
                    return 1;
                }
            }
        });
        cloudletList.sort(new Comparator<Cloudlet>() {
            @Override
            public int compare(Cloudlet o1, Cloudlet o2) {
                if(o1.getCloudletLength() > o2.getCloudletLength()){
                    return 1;
                }
                else{
                    return -1;
                }
            }
        });
        for (int i = 0; i < cloudletList.size(); i++) {
            for (int j = 0; j < vmList.size(); j++) {
                times[i][j] = cloudletList.get(i).getCloudletLength()/vmList.get(j).getMips();
            }
        }
        for (int i = 0; i < cloudletList.size(); i++) {
            for (int j = 0; j < vmList.size(); j++) {
                System.out.print(times[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
