package data.exchange.center.service.unstructured.node;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import data.exchange.center.common.constant.Constant;
import data.exchange.center.service.unstructured.node.util.DrawPanel;
import data.exchange.center.service.unstructured.node.util.PropertyUtil;

/**
 * 
 * Description:
 * <p>
 * Company: xinYa
 * </p>
 * ,
 * <p>
 * Date:2017年10月13日 下午3:05:44
 * </p>
 * 
 * @author Wen.Yuguang
 * @version 1.0
 *
 */

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = { "data.exchange.center.service.unstructured.node" })
public class NodeApplication {
    public static void main(String[] args) {
        //String applicationKey = args[0].substring(args[0].indexOf("="));
        String applicationKey= "=DY";
        System.setProperty("applicationKey", applicationKey.substring(1,applicationKey.length()));
    	System.out.println("application_"+System.getProperty("applicationKey")+".properties");
        // 创建 JFrame 实例
        JFrame frame = new JFrame("数据中心-资源目录客户端-"+PropertyUtil.getProperty("client.name"));
        // Setting the width and height of frame
        frame.setBounds(100, 100, 800, 600);
       

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*
         * 创建面板，这个类似于 HTML 的 div 标签 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
        JPanel panel = new DrawPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        // 添加面板
        frame.add(panel);
        panel.setLayout(null);
        Font titlefont=new Font("微软雅黑",Font.BOLD,20); 
        JLabel titleLabel = new JLabel("四川省法院基于电子卷宗非结构化数据");
        titleLabel.setBounds(230, 40, 350, 35);
        titleLabel.setFont(titlefont);
        
        JLabel titleLabels = new JLabel("资源目录客户端");
        titleLabels.setBounds(320, 70, 240, 35);
        titleLabels.setFont(titlefont);
        panel.add(titleLabels);
        panel.add(titleLabel);
        
        
        // 创建MQ信息
        Font font=new Font("微软雅黑",Font.BOLD,15); 
        JLabel MqLabel = new JLabel("Mq集群");
        MqLabel.setBounds(70, 120, 100, 25);
        MqLabel.setFont(font);
        panel.add(MqLabel);

        JLabel mqIpLabel = new JLabel("集群");
        mqIpLabel.setBounds(15, 150, 240, 35);
        mqIpLabel.setText("集群地址: " + PropertyUtil.getProperty("spring.rabbitmq.addresses"));
        panel.add(mqIpLabel);
        
        JLabel mqZTLabel = new JLabel("集群状态");
        mqZTLabel.setBounds(500, 150, 240, 35);
        mqZTLabel.setText("状态:  "+Constant.C_NO_CONNECT);
        panel.add(mqZTLabel);
        // 创建redis信息
        JLabel rdisLabel = new JLabel("Redis");
        rdisLabel.setBounds(70, 200, 100, 25);
        rdisLabel.setFont(font);
        panel.add(rdisLabel);

        JLabel redisIpLabel = new JLabel("Redis");
        redisIpLabel.setBounds(15, 230, 300, 35);
        redisIpLabel.setText("Rdis服务 : " + PropertyUtil.getProperty("spring.redis.host")+":"+PropertyUtil.getProperty("spring.redis.port"));
        panel.add(redisIpLabel);
        
        JLabel redisZTLabel = new JLabel("redis状态");
        redisZTLabel.setBounds(500, 230, 240, 35);
        redisZTLabel.setText("状态:  "+Constant.C_NO_CONNECT);
        panel.add(redisZTLabel);
        
        // 中心库
        JLabel ZxLabel = new JLabel("中心库");
        ZxLabel.setBounds(70, 280, 80, 35);
        ZxLabel.setFont(font);
        panel.add(ZxLabel);

        JLabel zxdLabel = new JLabel("中心端");
        zxdLabel.setBounds(15, 310, 300, 35);
        zxdLabel.setText("中心端   :  " + PropertyUtil.getProperty("spring.datasource.url"));
        panel.add(zxdLabel);
        
        JLabel zxZTLabel = new JLabel("中心端状态");
        zxZTLabel.setBounds(500, 310, 240, 35);
        zxZTLabel.setText("状态:  "+Constant.C_NO_CONNECT);
        panel.add(zxZTLabel);
        
        // 中心库
        JLabel ywLabel = new JLabel("业务库");
        ywLabel.setBounds(70, 360, 80, 25);
        ywLabel.setFont(font);
        panel.add(ywLabel);

        JLabel ywdLabel = new JLabel("业务链接: "+PropertyUtil.getProperty("tdh.datasource.url"));
        ywdLabel.setBounds(15, 390, 400, 35);
        panel.add(ywdLabel);


        
        JLabel ywZTLabel = new JLabel("业务端状态");
        ywZTLabel.setBounds(500, 390, 240, 35);
        ywZTLabel.setText("状态:  "+Constant.C_NO_CONNECT);
        panel.add(ywZTLabel);
        
        JLabel zcLabel = new JLabel("技术支持电话：67024");
        zcLabel.setBounds(450, 530, 200, 25);
        panel.add(zcLabel);

        JLabel jjdLabel = new JLabel("公司简介： www.XinYa.com");
        jjdLabel.setBounds(600, 530, 200, 25);
        panel.add(jjdLabel);
        
        // 创建启动按钮
        JButton startupButton = new JButton("启动");
        startupButton.setBounds(520, 470, 80, 25);
        startupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                ((JButton) ae.getSource()).setText("启动完成");
                ((JButton) ae.getSource()).setEnabled(false);
//                PropertyUtil.setProper("tdh.datasource.username",accountText.getText());
//                PropertyUtil.setProper("tdh.datasource.password",String.valueOf(passwordText.getPassword()));
                mqZTLabel.setText("状态:  "+Constant.C_IN_CONNECT);
                zxZTLabel.setText("状态:  "+Constant.C_IN_CONNECT);
                ywZTLabel.setText("状态:  "+Constant.C_IN_CONNECT);
                redisZTLabel.setText("状态:  "+Constant.C_IN_CONNECT);
                Thread t = new Thread() {
                    public void run() {
                        //启动类
                        SpringApplication.run(NodeApplication.class, args);
                        while(true){
                            if(System.getProperty(Constant.C_MQSTATE) != null){
                                mqZTLabel.setText("状态:  "+System.getProperty(Constant.C_MQSTATE));
                            }
                            if(System.getProperty(Constant.C_ZXDSTATE) != null){
                                zxZTLabel.setText("状态:  "+System.getProperty(Constant.C_ZXDSTATE)); 
                            }
                            if(System.getProperty(Constant.C_YWDSTATE) != null){
                                ywZTLabel.setText("状态:  "+System.getProperty(Constant.C_YWDSTATE)); 
                            }
                            if(System.getProperty(Constant.C_REDISTATE) != null){
                                redisZTLabel.setText("状态:  "+System.getProperty(Constant.C_REDISTATE)); 
                            }
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                t.start();
            }

        });
        panel.add(startupButton);
        // 创建关闭按钮
        JButton pauseButton = new JButton("关闭");
        pauseButton.setBounds(650, 470, 80, 25);
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        panel.add(pauseButton);

        // placeComponents(panel,args);
        // 设置界面可见
        frame.setVisible(true);
    }
    // private static void placeComponents(JPanel panel,String[] args) {
    //
    //
    // }

    // public static void main(String[] args) {
    // SpringApplication.run(DataExchangeCenterServiceUnstructuredNodeApplication.class,
    // args);
    // }

}
