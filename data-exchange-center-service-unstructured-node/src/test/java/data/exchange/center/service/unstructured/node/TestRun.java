package data.exchange.center.service.unstructured.node;

public class TestRun implements Runnable{

    TestRun(String a){
        
    }
    @Override
    public void run() {
       for (int i = 0; i < 10000; i++) {
        System.out.println(1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
        
    }

}
