package org.cc11001100.game.zhaocha.entity;

/**
 * @author CC11001100
 */
public class Location {

	private Integer x;

	private Integer y;

	private Location base;

	public Location() {
	}

	public Location(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}

	public Location(Integer x, Integer y, Location base) {
		this.x = x;
		this.y = y;
		this.base = base;
	}

	@Override
	public String toString() {
		return "Location{" + "x=" + x + ", y=" + y + ", base=" + base + '}';
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Location getBase() {
		return base;
	}

	public void setBase(Location base) {
		this.base = base;
	}
}
