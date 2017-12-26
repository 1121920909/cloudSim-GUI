package example.basic;

import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CloudSimExample3 extends BasicExample {
    public CloudSimExample3() {
        int num_user = 1;   // number of cloud users
        Calendar calendar = Calendar.getInstance();
        boolean trace_flag = false;  // mean trace events

        // Initialize the CloudSim library
        CloudSim.init(num_user, calendar, trace_flag);
        Datacenter datacenter0 = createDatacenter("Datacenter_0");
        DatacenterBroker broker = createBroker("Broker");
        int brokerId = broker.getId();
        vmList = new ArrayList<Vm>();

        //VM description
        int vmid = 0;
        int mips = 250;
        long size = 10000; //image size (MB)
        int ram = 512; //vm memory (MB)
        long bw = 1000;
        int pesNumber = 1; //number of cpus
        String vmm = "Xen"; //VMM name

        //create two VMs
        Vm vm1 = new Vm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());

        //the second VM will have twice the priority of VM1 and so will receive twice CPU time
        vmid++;
        Vm vm2 = new Vm(vmid, brokerId, mips * 2, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());

        //add the VMs to the vmList
        vmList.add(vm1);
        vmList.add(vm2);

        //submit vm list to the broker
        broker.submitVmList(vmList);


        //Fifth step: Create two Cloudlets
        cloudletList = new ArrayList<Cloudlet>();

        //Cloudlet properties
        int id = 0;
        long length = 40000;
        long fileSize = 300;
        long outputSize = 300;
        UtilizationModel utilizationModel = new UtilizationModelFull();

        Cloudlet cloudlet1 = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
        cloudlet1.setUserId(brokerId);

        id++;
        Cloudlet cloudlet2 = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
        cloudlet2.setUserId(brokerId);

        //add the cloudlets to the list
        cloudletList.add(cloudlet1);
        cloudletList.add(cloudlet2);

        //submit cloudlet list to the broker
        broker.submitCloudletList(cloudletList);


        //bind the cloudlets to the vms. This way, the broker
        // will submit the bound cloudlets only to the specific VM
        broker.bindCloudletToVm(cloudlet1.getCloudletId(),vm1.getId());
        broker.bindCloudletToVm(cloudlet2.getCloudletId(),vm2.getId());
        // Sixth step: Starts the simulation
        CloudSim.startSimulation();
        // Final step: Print results when simulation is over
        resultCloudletList = broker.getCloudletReceivedList();
        CloudSim.stopSimulation();
    }
}
