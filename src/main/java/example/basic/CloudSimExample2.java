package example.basic;

import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;

import java.util.ArrayList;
import java.util.Calendar;

public class CloudSimExample2 extends BasicExample {
    public CloudSimExample2(){
        int userNum = 1;
        Calendar calendar = Calendar.getInstance();
        boolean traceFlag = false;
        CloudSim.init(userNum,calendar,traceFlag);
        Datacenter datacenter0 = createDatacenter("DataCenter_0");
        DatacenterBroker datacenterBroker = createBroker("Broker");
        int brokerId = datacenterBroker.getId();
        vmList = new ArrayList<Vm>();
        //VM description
        int vmId = 0;
        int mips = 500;
        int size = 10000;
        int ram = 512;
        long bw = 1000;
        int pesNumber = 1;
        String vmm = "Xen";
        Vm vm1 = new Vm(vmId,brokerId,mips,pesNumber,ram,bw,size,vmm,new CloudletSchedulerTimeShared());
        vmList.add(vm1);
        vmId++;
        Vm vm2 = new Vm(vmId,brokerId,mips,pesNumber,ram,bw,size,vmm,new CloudletSchedulerTimeShared());
        vmList.add(vm2);
        datacenterBroker.submitVmList(vmList);
        cloudletList = new ArrayList<Cloudlet>();
        int id = 0;
        long length = 400000;
        long fileSize = 300;
        long outputSize = 300;
        UtilizationModel utilizationModel = new UtilizationModelFull();
        Cloudlet cloudlet1 = new Cloudlet(id,length,pesNumber,fileSize,outputSize,utilizationModel,utilizationModel,utilizationModel);
        cloudlet1.setUserId(brokerId);
        id++;
        Cloudlet cloudlet2 = new Cloudlet(id,length,pesNumber,fileSize,outputSize,utilizationModel,utilizationModel,utilizationModel);
        cloudlet2.setUserId(brokerId);
        cloudletList.add(cloudlet1);
        cloudletList.add(cloudlet2);
        datacenterBroker.submitCloudletList(cloudletList);
        datacenterBroker.bindCloudletToVm(cloudlet1.getCloudletId(),vm1.getId());
        datacenterBroker.bindCloudletToVm(cloudlet2.getCloudletId(),vm2.getId());

        //start simulation
        CloudSim.startSimulation();
        resultCloudletList = datacenterBroker.getCloudletReceivedList();
        //stop simulation
        CloudSim.stopSimulation();
    }
}

