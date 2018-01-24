package org.cc11001100.game.zhaocha.entity;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author CC11001100
 */
public class Board {

	/**
	 * 相对于屏幕的位置
	 */
	private Location location;

	/**
	 * 显示板内容
	 */
	private BufferedImage image;

	public Board() {
	}

	public Board(Location location, BufferedImage image) {
		this.location = location;
		this.image = image;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
}
