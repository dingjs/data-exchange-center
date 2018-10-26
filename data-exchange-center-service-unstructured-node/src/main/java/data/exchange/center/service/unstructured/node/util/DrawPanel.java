package data.exchange.center.service.unstructured.node.util;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public  class DrawPanel extends JPanel {//画线的JPanel
    /**
	 * 2018年5月18日上午11:41:19
	 * Tony
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public void paint(Graphics g) {
        super.paint(g);
        //两点确定一条直线.  三个点(不在同一直线)两两连接就可以成为三角形
        g.setColor(Color.GRAY);//设置第一条线的颜色
        g.drawLine(0, 190, 800, 190);//画第一条线 点(50,50) 到点  (100,100)
        g.setColor(Color.GRAY);
        g.drawLine(0, 270, 800, 270);//画第二条线 点(50,50) 到点  (50,150)
        g.setColor(Color.GRAY);
        g.drawLine(0, 350, 800, 350);//画第三条线 点(50,150) 到点  (100,100)
        g.setColor(Color.GRAY);
        g.drawLine(0, 430, 800, 430);//画第三条线 点(50,150) 到点  (100,100)
        
    }
}
