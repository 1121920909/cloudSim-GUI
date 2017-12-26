package example.basic;

import ch.qos.logback.core.pattern.FormatInfo;
import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CloudSimExample5 extends BasicExample {
    private List<Cloudlet> cloudletList1;
    private List<Cloudlet> cloudletList2;
    private List<Vm> vmList1;
    private List<Vm> vmList2;
    private List<Cloudlet> resultList1;
    private List<Cloudlet> resultList2;
    public CloudSimExample5() {
        int userNum = 2;
        Calendar calendar = Calendar.getInstance();
        CloudSim.init(userNum,calendar,false);
        Datacenter datacenter0 = createDatacenter("DataCenter_0");
        Datacenter datacenter1 = createDatacenter("DataCenter_1");
        DatacenterBroker broker1 = createBroker(1);
        int brokerId1 = broker1.getId();
        DatacenterBroker broker2 = createBroker(2);
        int brokerId2 = broker2.getId();
        vmList1 = new ArrayList<Vm>();
        vmList2 = new ArrayList<Vm>();
        int vmid = 0;
        int mips = 250;
        long size = 10000;
        int ram = 512;
        long bw = 1000;
        int pesNumber = 1;
        String vmm = "Xen";
        Vm vm1 = new Vm(vmid, brokerId1, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
        Vm vm2 = new Vm(vmid, brokerId2, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
        vmList1.add(vm1);
        vmList2.add(vm2);
        broker1.submitVmList(vmList1);
        broker2.submitVmList(vmList2);
        int id = 0;
        long length = 40000;
        long fileSize = 300;
        long outputSize = 300;
        UtilizationModel utilizationModel = new UtilizationModelFull();

        Cloudlet cloudlet1 = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
        cloudlet1.setUserId(brokerId1);
        Cloudlet cloudlet2 = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
        cloudlet2.setUserId(brokerId2);
        cloudletList1 = new ArrayList<Cloudlet>();
        cloudletList2 = new ArrayList<Cloudlet>();
        cloudletList1.add(cloudlet1);
        cloudletList2.add(cloudlet2);

        broker1.submitCloudletList(cloudletList1);
        broker2.submitCloudletList(cloudletList2);

        CloudSim.startSimulation();

        resultList1 = broker1.getCloudletReceivedList();
        resultList2 = broker2.getCloudletReceivedList();
        CloudSim.stopSimulation();
    }
    public DatacenterBroker createBroker(int id){
        DatacenterBroker broker = null;
        try{
            broker = new DatacenterBroker("Broker"+id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return broker;
    }
    @Override
    public String formatInfo() {
        StringBuilder sb = new StringBuilder();
        DecimalFormat dft = new DecimalFormat("###.##");
        String indent = "\t";
        sb.append("============> User 4\n");
        sb.append("========== OUTPUT ==========\n");
        sb.append("Cloudlet ID\tSTATUS\tData center ID\tVM ID\tTime\tStart Time\tFinish Time\n");
        for(Cloudlet cloudlet : resultList1){
            sb.append(cloudlet.getCloudletId() + indent);
            if(cloudlet.getStatus() == Cloudlet.SUCCESS){
                sb.append("SUCCESS"+indent + cloudlet.getResourceId()
                        + indent + cloudlet.getVmId()
                        + indent
                        + dft.format(cloudlet.getActualCPUTime()) + indent
                        + dft.format(cloudlet.getExecStartTime())
                        + indent
                        + dft.format(cloudlet.getFinishTime()) + "\n");
            }
        }
        sb.append("============> User 5\n");
        sb.append("========== OUTPUT ==========\n");
        sb.append("Cloudlet ID\tSTATUS\tData center ID\tVM ID\tTime\tStart Time\tFinish Time\n");
        for(Cloudlet cloudlet : resultList2){
            sb.append(cloudlet.getCloudletId() + indent);
            if(cloudlet.getStatus() == Cloudlet.SUCCESS){
                sb.append("SUCCESS"+indent + cloudlet.getResourceId()
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

}
