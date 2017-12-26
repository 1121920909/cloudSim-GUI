package example.basic;

import org.junit.Test;

import java.awt.peer.SystemTrayPeer;

public class CloudSimExampleTest {
    @Test
    public void cloudSimExample1Test(){
        CloudSimExample1 cloudSimExample1 = new CloudSimExample1();
    }
    @Test
    public void printTest(){
        System.out.println((new CloudSimExample1()).formatInfo());
    }
    @Test
    public void cloudSimExample3Test(){
        System.out.println(new CloudSimExample3().formatInfo());
    }
    @Test
    public void cloudSimExample4Test(){
        System.out.println(new CloudSimExample4().formatInfo());
    }
    @Test
    public void cloudSimExample5Test(){
        System.out.println(new CloudSimExample5().formatInfo());
    }
}
