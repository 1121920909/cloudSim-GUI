package example.basic;

import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CloudSimExample1 extends BasicExample{

    public CloudSimExample1(){
        //用户数量
        int userNum = 1;
        Calendar calendar = Calendar.getInstance();
        boolean traceFlag = false;
        //初始化CloudSim
        CloudSim.init(userNum,calendar,traceFlag);
        Datacenter datacenter0 = createDatacenter("DataCenter_0");
        DatacenterBroker datacenterBroker = createBroker("Broker");
        int brokerId = datacenterBroker.getId();
        //System.out.println(borkerId);
        vmList = new ArrayList<Vm>();
        //VM description
        int vmId = 0;
        int mips = 1000;
        int size = 10000;
        int ram = 512;
        long bw = 1000;
        int pesNumber = 1;
        String vmm = "Xen";
        //create VM
        Vm vm = new Vm(vmId,brokerId,mips,pesNumber,ram,bw,size,vmm,new CloudletSchedulerTimeShared());
        vmList.add(vm);
        //submit VM List
        datacenterBroker.submitVmList(vmList);
        //Cloudlet properties
        cloudletList = new ArrayList<Cloudlet>();
        int id = 0;
        long length = 400000;
        long fileSize = 300;
        long outputSize = 300;
        UtilizationModel utilizationModel = new UtilizationModelFull();
        Cloudlet cloudlet = new Cloudlet(id,length,pesNumber,fileSize,outputSize,utilizationModel,utilizationModel,utilizationModel);
        cloudlet.setUserId(brokerId);
        //cloudlet.setVmId(vmId);
        cloudletList.add(cloudlet);
        //submit Cloudlet List
        datacenterBroker.submitCloudletList(cloudletList);
        datacenterBroker.bindCloudletToVm(cloudlet.getCloudletId(),vm.getId());
        //start simulation
        CloudSim.startSimulation();
        //stop simulation
        CloudSim.stopSimulation();
        //get Result List
        resultCloudletList = datacenterBroker.getCloudletReceivedList();
    }

}
