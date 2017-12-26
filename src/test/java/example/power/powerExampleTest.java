package example.power;

import example.power.planetlab.Dvfs;
import example.power.planetlab.PlanetLabRunner;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class powerExampleTest {
    @Test
    public void toStirngTest(){
        boolean enableOutput = true;
        boolean outputToFile = false;
        String inputFolder = powerExampleTest.class.getClassLoader().getResource("planetlab").getPath();
        String outputFolder = "output";
        String workload = "20110303"; // PlanetLab workload
        String vmAllocationPolicy = "dvfs"; // DVFS policy without VM migrations
        String vmSelectionPolicy = "";
        String parameter = "";

        PlanetLabRunner test =  new PlanetLabRunner(
                enableOutput,
                outputToFile,
                inputFolder,
                outputFolder,
                workload,
                vmAllocationPolicy,
                vmSelectionPolicy,
                parameter);
        System.out.println("test input:" + test.result);
    }
    @Test
    public void beanTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("example/power/beans.xml");
        PowerExampleFactory dvfs = (PowerExampleFactory) context.getBean("dvfs");
        System.out.println(dvfs.getResutl());
    }
}
