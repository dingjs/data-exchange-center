package data.exchange.center.service.unstructured.node;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;

public class StartupButton implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent ae) {
        String buttonText=((JButton)ae.getSource()).getText();
    
        if(buttonText.equals("启动")){
            ((JButton)ae.getSource()).setText("暂停");
            try {
                System.in.read();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else if(buttonText.equals("暂停")){
            Thread thread = new Thread(new TestRun(""));
            thread.start();
            ((JButton)ae.getSource()).setText("启动"); 
        }else if (buttonText.equals("关闭")){
            System.exit(0);
        }
    }




}
