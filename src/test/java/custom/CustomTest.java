package custom;

import org.junit.Test;

public class CustomTest {
    @Test
    public void customSimulationTest(){
        CustomSimulation simulation = new CustomSimulation();
        simulation.setPeList(10000,1);
        simulation.setHostList(1024,100000,1000,1);
        simulation.createDatacenterCharactersitics(2,20,120,120);
        simulation.setDatacenterList(1);
        simulation.setVmList(2,4000,1,256,300,10000);
        simulation.setCloudletList(10000,1000,1000,1,2);
        simulation.bindCloudletToVmsTimeAwared();
        //simulation.simulationStart();
        //System.out.println(simulation.formatInfo());
    }
}
