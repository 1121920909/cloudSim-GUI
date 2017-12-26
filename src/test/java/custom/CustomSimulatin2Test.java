package custom;

import com.sun.javafx.collections.NonIterableChange;
import org.cloudbus.cloudsim.core.CloudSim;
import org.junit.Test;

public class CustomSimulatin2Test {
    @Test
    public void testSimulation(){
        int peId = 0;
        int vmId = 0;
        int cloudletId = 0;
        CustomSimulation2 simulation2 = new CustomSimulation2();
        for (int i = 0; i < 8; i++){
            simulation2.addVm(vmId,1,512,256,100,4000);vmId++;
        }
        for (int i = 0; i < 31; i++){
            simulation2.addPeList(1,1024,peId);
            simulation2.addCloudlet(cloudletId,1000,100,100,1);
            peId++;
            cloudletId++;
        }
        simulation2.addHost(1,20480,100000,10000);
        simulation2.createDatacenterCharacteristics(10,10,10,10);
        simulation2.startSimulation();
        System.out.println(simulation2.formatInfo());
    }

    @Test
    public void test(){
        do {
            System.out.println(111);
        }while (false);
    }
}
