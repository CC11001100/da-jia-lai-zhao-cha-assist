package org.cc11001100.game.zhaocha.gui;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author CC11001100
 */
public class MainFrame extends JFrame {

	public MainFrame(){
		JPanel jPanel = new JPanel();
		JButton controlBtn = new JButton("start");
		controlBtn.addActionListener(e->{
			this.setTitle("restart");
			if(MainController.stopFlag.get()){
				MainController.start();
			} else{
				MainController.restart();
			}
		});
		jPanel.add(controlBtn);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("QQ游戏大家来找茬辅助工具");
		setSize(150, 80);
		setLocation(100, 200);

		add(jPanel);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("窗口关闭，程序退出...");
				MainController.stop();
				System.exit(0);
			}
		});

		setVisible(true);
	}

	public static void main(String[] args) {
		new MainFrame();
	}

}
