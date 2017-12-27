package custom;

import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

import java.text.DecimalFormat;
import java.util.*;

/**
 * @author ZYP
 */

public class CustomSimulation2 {

    /**
     * VmList
     * 虚拟机
     */
    private List<Vm> vmList = null;
    /**
     * CloudletList
     * 云应用
     */
    private List<Cloudlet> cloudletList = null;
    /**
     * resultCloudletList
     * 模拟结束后返回的Cloudlet
     */
    private List<Cloudlet> resultCloudletList = null;
    /**
     * HostList
     */
    private List<Host> hostList = null;
    /**
     * key:Host ID , value : PeList
     */
    private Hashtable<Integer,List<Pe>> peTable = null;

    private DatacenterBroker broker = null;

    private Datacenter datacenter;

    private DatacenterCharacteristics characteristics = null;

    /**
     * 构造方法
     */
    public CustomSimulation2(){
        CloudSim.init(1, Calendar.getInstance(),false);
        broker = createBroker();
    }

    /**
     *
     */
    public void startSimulation(){
        createDatacenter();
        broker.submitVmList(vmList);
        broker.submitCloudletList(cloudletList);
        //bindCloudletsToVmsSimple();
        bindCLoudletsToVmsTimeAwared();
        CloudSim.startSimulation();
        resultCloudletList = broker.getCloudletReceivedList();
        CloudSim.stopSimulation();
    }

    /**
     * 顺序分配策略
     */
    private void bindCloudletsToVmsSimple(){
        int cloudletNum = cloudletList.size();
        int vmNum = vmList.size();
        for(int i = 0; i < cloudletNum; i++) {
            broker.bindCloudletToVm(cloudletList.get(i).getCloudletId(),vmList.get(i%vmNum).getId());
        }
    }

    /**
     * 贪心策略
     */
    private void bindCLoudletsToVmsTimeAwared(){
        //任务i在虚拟机j上执行所需时间
        double[][] times;
        //虚拟机i执行所分配任务所需时间
        double[] vmTimes;
        //虚拟机i上的Cloudlet数目
        int[] cloudletOfVmsNum;
        //cloudlet总数
        int cloudleletNum = cloudletList.size();
        //vm总数
        int vmNum = vmList.size();

        times = new double[cloudleletNum][vmNum];

        //初始化vmTime和CloudletOfVmsNum
        vmTimes = new double[vmNum];
        cloudletOfVmsNum = new int[vmNum];
        for(int i = 0; i < vmTimes.length; i++) {
            vmTimes[i] = 0;
            cloudletOfVmsNum[i] = 0;
        }

        //对cloudlet按length降序排序
        cloudletList.sort(new Comparator<Cloudlet>() {
            @Override
            public int compare(Cloudlet o1, Cloudlet o2) {
                if (o1.getCloudletLength() > o2.getCloudletLength()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        //对VM按升序排序
        vmList.sort(new Comparator<Vm>() {
            @Override
            public int compare(Vm o1, Vm o2) {
                if (o1.getMips() > o2.getMips()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });

        //设置任务在不同虚拟机上执行时间
        for(int i = 0; i < cloudleletNum; i++) {
            for(int j = 0; j < vmNum; j++) {
                times[i][j] = cloudletList.get(i).getCloudletLength()/vmList.get(j).getMips();
            }
        }

        Cloudlet cloudlet;
        Vm vm;
        for(int i = 0; i < cloudleletNum; i++) {
            cloudlet = cloudletList.get(i);
            for(int j = vmNum -1 ; j >= 0; j--) {
                if (j == 0) {
                    int n = j;
                    do {
                        vm = vmList.get(n);
                        broker.bindCloudletToVm(cloudlet.getCloudletId(), vm.getId());
                        n++;
                    } while (cloudlet.getVmId() == -1 && n < vmNum);
                } else {
                    double time1 = vmTimes[j] + times[i][j];
                    double time2 = vmTimes[j-1] + times[i][j-1];
                    if (time1 < time2) {
                        vm = vmList.get(j);
                    } else if (time1 == time2) {
                        if (cloudletOfVmsNum[j] > cloudletOfVmsNum[j - 1]) {
                            vm = vmList.get(j - 1);
                        } else {
                            vm = vmList.get(j);
                        }
                    } else {
                        continue;
                    }
                    broker.bindCloudletToVm(cloudlet.getCloudletId(),vm.getId());
                    if (cloudlet.getVmId() != -1) {
                        vmTimes[vmList.indexOf(vm)] += times[i][vmList.indexOf(vm)];
                        break;
                    }
                }
            }
        }
    }

    /**
     * 初始化VmList
     */
    public void initVmList(){
        if (vmList == null) {
            vmList = new ArrayList<>();
        } else {
            vmList.clear();
        }
    }

    /**
     * 初始化CloudletList
     */
    public void initCloudletList() {
        if (cloudletList == null) {
            cloudletList = new ArrayList<>();
        } else {
            cloudletList.clear();
        }
    }

    /**
     * 初始化HostList
     */
    public void initHostList() {
        if (hostList == null) {
            hostList = new ArrayList<>();
        } else {
            hostList.clear();
        }
    }

    /**
     * 初始化PEList
     */
    public void initPeTable() {
        if (peTable == null) {
            peTable = new Hashtable<>();
        } else {
            peTable.clear();
        }
    }

    /**
     * 创建DatacenterCharacteristic
     * @param costPerSec
     * @param costPerMem
     * @param costPerStorage
     * @param costPerBW
     */
    public void createDatacenterCharacteristics(double costPerSec,double costPerMem,double costPerStorage,double costPerBW){
        characteristics = new DatacenterCharacteristics("x86","Linux","Xen",
                hostList,10.0,costPerSec,costPerMem,costPerStorage,costPerBW);
    }

    private void createDatacenter(){
        try {
            datacenter = new Datacenter("Datacenter", characteristics,
                    new VmAllocationPolicySimple(hostList),
                    new ArrayList<Storage>(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加Pe
     * @param hostId pe 所属 HostID
     * @param mips pe mips
     * @param peId  pe ID
     */
    public void addPeList(int hostId,int mips,int peId){
        List<Pe> peList;
        if (peTable == null) {
            initPeTable();
        }
        if (peTable.containsKey(hostId)) {
            peList = peTable.get(hostId);
        } else {
            peList = new ArrayList<>();
            peTable.put(hostId,peList);
        }
        peList.add(new Pe(peId,new PeProvisionerSimple(mips)));
    }

    /**
     * 添加HOST
     * @param hostId host ID
     * @param ram    host RAM
     * @param storage host storage
     * @param bw      host bw
     * @param peList  Pe List
     */
    public void addHost(int hostId,int ram,long storage,int bw,List<Pe> peList){
        if (hostList == null) {
            initHostList();
        }
        hostList.add(new Host(hostId,
                new RamProvisionerSimple(ram),
                new BwProvisionerSimple(bw),
                storage, peTable.get(hostId),
                new VmSchedulerSpaceShared(peList)));
    }

    /**
     * 添加Vm
     * @param vmId vm ID
     * @param mips  vm MIPS
     * @param ram   vm RAM
     * @param peNum vm pe number
     * @param bw    vm bw
     * @param size  vm size
     */
    public void addVm(int vmId,int peNum,int mips,int ram,int bw,long size){
        if (vmList == null) {
            initVmList();
        }
        vmList.add(new Vm(vmId,broker.getId(),mips,peNum,ram,bw,size,"Xen",new CloudletSchedulerSpaceShared()));
    }

    /**
     * 添加Cloudlet
     * @param cloudletId Cloudlet ID
     * @param length     Cloudlet length
     * @param fileSize   Cloudlet file size
     * @param outputSize Cloudlet out put size
     * @param peNum      Cloudlet PE number
     */
    public void addCloudlet(int cloudletId,long length,long fileSize,long outputSize,int peNum) {
        if (cloudletList == null) {
            initCloudletList();
        }
        UtilizationModel utilizationModel = new UtilizationModelFull();
        Cloudlet cloudlet = new Cloudlet(cloudletId,length,peNum,fileSize,outputSize,
                utilizationModel,utilizationModel,utilizationModel);
        cloudlet.setUserId(broker.getId());
        cloudletList.add(cloudlet);
    }

    /**
     * 创建DatacenterBroker
     * @return DatacenterBroker
     */
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

    public String formatInfo(){
        StringBuilder sb = new StringBuilder();
        Cloudlet cloudlet;
        String indent = "\t";
        sb.append("========== OUTPUT ==========\n");
        sb.append("Cloudlet ID\tSTATUS\tData center ID\tVM ID\tTime\tStart Time\tFinish Time\n");
        DecimalFormat dft = new DecimalFormat("###.##");
        for(int i = 0;i < resultCloudletList.size();i++){
            cloudlet = resultCloudletList.get(i);
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

}
