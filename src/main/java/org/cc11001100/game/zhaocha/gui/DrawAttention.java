package org.cc11001100.game.zhaocha.gui;

import org.cc11001100.game.zhaocha.entity.Location;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 糊在屏幕上的一坨东西，用来吸引注意力，醒目
 *
 * @author CC11001100
 */
public class DrawAttention extends JFrame implements Runnable {

	private DrawAttentionPanel panel;

	/**
	 * 要不断在屏幕上绘制的点
	 */
	private List<Location> drawPoint;

	AtomicBoolean stopFlag = new AtomicBoolean(false);

	public DrawAttention(List<Location> drawPoint, Location baseLocation) throws HeadlessException {
		this.drawPoint = drawPoint;

		panel = new DrawAttentionPanel();
		panel.setBackground(null);
		panel.setOpaque(false);

		setAlwaysOnTop(true);

		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));
		//		setOpacity(1);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setContentPane(panel);

		setSize(380, 285);
		setLocation(baseLocation.getX() + 76, baseLocation.getY() + 307);
		setVisible(true);
	}

	@Override
	public void run() {

		// 每隔100毫秒重绘一次
		while (!stopFlag.get()) {
			panel.repaint();
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 杀掉这块糊糊的东西
	 */
	void killSelf(){
		stopFlag.set(false);
		dispose();
	}

	public class DrawAttentionPanel extends JPanel {

		/**
		 * 用于控制不断的闪烁
		 */
		private Boolean flag = true;

		@Override
		public void paint(Graphics g) {
			if (flag) {
				g.setColor(Color.GREEN);
				flag = false;
			} else {
				g.setColor(new Color(0, 0, 0, 0));
				flag = true;
			}

			drawPoint.forEach(x -> g.fillRect(x.getX(), x.getY(), 1, 1));
		}
	}
}
