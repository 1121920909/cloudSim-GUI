package example.power;

import example.power.planetlab.PlanetLabRunner;

/**
 * @author ZYP
 */
public class PowerExampleFactory {
    private boolean enableOutput;
    private boolean outputToFile;
    private String inputFolder = PowerExampleFactory.class.getClassLoader().getResource("planetlab").getPath();
    private String outputFolder;
    private String workload;
    private String vmAllocationPolicy;
    private String vmSelectionPolicy;
    private String parameter;
    private PlanetLabRunner planetLabRunner;

    public PowerExampleFactory(boolean enableOutput, boolean outputToFile, String outputFolder, String workload, String vmAllocationPolicy, String vmSelectionPolicy, String parameter) {
        this.enableOutput = enableOutput;
        this.outputToFile = outputToFile;
        this.outputFolder = outputFolder;
        this.workload = workload;
        this.vmAllocationPolicy = vmAllocationPolicy;
        this.vmSelectionPolicy = vmSelectionPolicy;
        this.parameter = parameter;
        planetLabRunner = new PlanetLabRunner(
                                 enableOutput,
                                 outputToFile,
                                 inputFolder,
                                 outputFolder,
                                 workload,
                                 vmAllocationPolicy,
                                 vmSelectionPolicy,
                                 parameter);
    }
    public String getResutl(){
        return planetLabRunner.getResult();
    }
}
