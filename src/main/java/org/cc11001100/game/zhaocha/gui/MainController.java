package org.cc11001100.game.zhaocha.gui;

import javaslang.Tuple2;
import org.cc11001100.game.zhaocha.entity.Board;
import org.cc11001100.game.zhaocha.entity.Location;
import org.cc11001100.game.zhaocha.util.ImageUtil;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author CC11001100
 */
public class MainController {

	static AtomicBoolean stopFlag = new AtomicBoolean(true);

	private static DrawAttention drawAttention;

	public static void start() {

		stopFlag.set(false);

		if (!startOne()) {
			stop();
		}

		// 不在游戏中时自动隐藏指示画板
		startDrawAttentionAutoHide();

	}

	public static void restart() {
		stop();
		start();
	}

	private static boolean startOne() {
		BufferedImage screenImage = ImageUtil.captureScreenSnapshot();
		Location baseLocation = ImageUtil.findBaseLocation(screenImage);

		if (baseLocation == null) {
			JOptionPane.showMessageDialog(null, "未找到游戏界面");
			return false;
		}

		Tuple2<Board, Board> boardTuple = ImageUtil.mattingImageBoard(screenImage, baseLocation);
		List<Location> diffPointList = ImageUtil.diffPointList(boardTuple._1.getImage(), boardTuple._2.getImage());
		drawAttention = new DrawAttention(diffPointList, baseLocation);
		new Thread(drawAttention).start();
		return true;
	}

	/**
	 * 启动一个线程监控，当不在游戏时自动隐藏掉糊在屏幕上的这一坨
	 */
	private static void startDrawAttentionAutoHide() {
		new Thread(() -> {
			while (!stopFlag.get()) {
				System.out.println(Thread.currentThread().getId() + " detect in game ...");
				if (drawAttention != null) {
					drawAttention.setVisible(isInGame());
				}
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private static BufferedImage lastStageNumber;

	/**
	 * 是否切换了场景
	 *
	 * @return
	 */
	private static boolean isChangeStage() {
		BufferedImage screenImage = ImageUtil.captureScreenSnapshot();
		Location baseLocation = ImageUtil.findBaseLocation(screenImage);

		if (baseLocation == null) {
			return false;
		}

		BufferedImage stageNumberImage = screenImage
				.getSubimage(baseLocation.getX() + 471, baseLocation.getY() + 221, 46, 18);

		if (lastStageNumber != null && ImageUtil.fastDiff(lastStageNumber, stageNumberImage)) {
			return true;
		} else {
			lastStageNumber = stageNumberImage;
			return false;
		}
	}

	/**
	 * 当前是否在游戏中，用于自动隐藏糊在屏幕上的一坨用来醒目的东西
	 *
	 * @return
	 */
	private static boolean isInGame() {
		BufferedImage screenImage = ImageUtil.captureScreenSnapshot();
		Location baseLocation = ImageUtil.findBaseLocation(screenImage);
		return baseLocation != null;
	}

	/**
	 * 停止并清理资源
	 */
	public static void stop() {

		// 先释放持有的资源
		if (drawAttention != null) {
			drawAttention.killSelf();
		}

		// 再释放自己
		stopFlag.set(true);

		// 这里有个bug，会释放不及资源，可能是哪里协调出了问题，先暂时这样凑活着
		try {
			TimeUnit.MILLISECONDS.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
