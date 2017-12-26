package example.network;

import org.apache.commons.math3.ml.neuralnet.Network;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class NetworkTest {

    ApplicationContext context = new ClassPathXmlApplicationContext("example/network/beans.xml");
    @Test
    public void example1Test(){
        NetworkExample1 example1 = (NetworkExample1) context.getBean("networkExample1");
        System.out.println(example1.formatInfo());
    }
    @Test
    public void example2Test(){
        NetworkExample2 example2 = (NetworkExample2) context.getBean("networkExample2");
        System.out.println(example2.formatInfo());
    }
    @Test
    public void example3Test(){
        NetworkExample3 example3 = (NetworkExample3)context.getBean("networkExample3");
        System.out.println(example3.formatInfo());
    }
    @Test
    public void example4Test(){
        NetworkExample4 example4 = (NetworkExample4)context.getBean("networkExample4");
        System.out.println(example4.formatInfo());
    }
}
