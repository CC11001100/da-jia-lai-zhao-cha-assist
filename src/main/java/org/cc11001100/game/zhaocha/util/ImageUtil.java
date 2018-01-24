package org.cc11001100.game.zhaocha.util;

import javaslang.Tuple;
import javaslang.Tuple2;
import org.cc11001100.game.zhaocha.entity.Board;
import org.cc11001100.game.zhaocha.entity.Location;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *  图像工具类
 *
 * @author CC11001100
 */
public final class ImageUtil {

	/**
	 * 找到基准点的位置
	 *
	 * @param image
	 * @return
	 */
	public static Location findBaseLocation(BufferedImage image) {

		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {

				// (188,5)
				if (!rgbEquals(image, x, y, 0XE09838)) {
					continue;
				}

				int x1 = x - 6, y1 = y + 5;
				int x2 = x + 7, y2 = y + 5;
				int x3 = x + 4, y3 = y + 14;

				if (x1 < 0 || x1 >= image.getWidth() //
						|| x2 < 0 || x2 >= image.getWidth() //
						|| x3 < 0 || x3 >= image.getWidth() //
						|| y1 < 0 || y1 >= image.getHeight() //
						|| y2 < 0 || y2 >= image.getHeight() //
						|| y3 < 0 || y3 >= image.getHeight()) {
					continue;
				}

				if (rgbEquals(image, x1, y1, 0XF0D458) //
						&& rgbEquals(image, x2, y2, 0XF0C850) //
						&& rgbEquals(image, x3, y3, 0Xf0a430)) {
					return new Location(x, y);
				}
			}
		}

		return null;
	}

	/**
	 * 用于判断图片指定位置的像素是不是跟传入的RGB相等，此相等是完全相等
	 *
	 * @param image
	 * @param x
	 * @param y
	 * @param rgb
	 * @return
	 */
	public static boolean rgbEquals(BufferedImage image, int x, int y, int rgb) {
		int srcRgb = image.getRGB(x, y);
		return rgbEquals(srcRgb, rgb);
	}

	/**
	 * 比较 两个点的RGB值是否相等，此相等是完全相等
	 *
	 * @param rgb1
	 * @param rgb2
	 * @return
	 */
	public static boolean rgbEquals(int rgb1, int rgb2) {
		return (rgb1 & 0XFFFFFF) == (rgb2 & 0XFFFFFF);
	}

	/**
	 * 比较两个rgb像素是否近似相等，在一定距离内则认为是近似相等
	 *
	 * @param rgb1
	 * @param rgb2
	 * @param distance 三个通道的距离在3*distance以内认为是相等
	 * @return
	 */
	public static boolean rgbSimilar(int rgb1, int rgb2, int distance) {
		int r = Math.abs((rgb1 & 0XFF0000) - (rgb2 & 0XFF0000)) >> 16;
		int g = Math.abs((rgb1 & 0X00FF00) - (rgb2 & 0X00FF00)) >> 8;
		int b = Math.abs((rgb1 & 0X0000FF) - (rgb2 & 0X0000FF));
		return (r + g + b) <= (distance * 3);
	}

	/**
	 * 根据传入的BaseLocation将左右两张图抠出来
	 *
	 * @param image
	 * @param location
	 * @return Tuple2<leftBoard,rightBoard>
	 */
	public static Tuple2<Board, Board> mattingImageBoard(BufferedImage image, Location location) {
		Location leftLocation = new Location(location.getX() + 76, location.getY() + 307);
		BufferedImage leftImage = image.getSubimage(leftLocation.getX(), leftLocation.getY(), 380, 285);
		Board leftBoard = new Board(leftLocation, leftImage);

		Location rightLocation = new Location(location.getX() + 533, location.getY() + 307);
		BufferedImage rightImage = image.getSubimage(rightLocation.getX(), rightLocation.getY(), 380, 285);
		Board rightBoard = new Board(rightLocation, rightImage);

		return Tuple.of(leftBoard, rightBoard);
	}

	/**
	 * 转换图片模式，会使用处理后的图片替换处理前的图片，并不会改变传入进来的图片
	 *
	 * @return
	 */
	private static BufferedImage grayscaleProcessing(BufferedImage srcImage) {
		int w = srcImage.getWidth();
		int h = srcImage.getHeight();
		BufferedImage destImage = new BufferedImage(w, h, BufferedImage.TYPE_USHORT_GRAY);
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				int rgb = srcImage.getRGB(i, j);
				destImage.setRGB(i, j, rgb);
			}
		}
		return destImage;
	}

	//	/**
	//	 * RGB图像转为HSL矩阵
	//	 *
	//	 * @return
	//	 */
	//	public static Tuple3<Double, Double, Double>[][] covert(BufferedImage image) {
	//		Tuple3<Double, Double, Double>[][] res = new Tuple3[image.getWidth()][image.getHeight()];
	//
	//		for (int i = 0; i < image.getWidth(); i++) {
	//			for (int j = 0; j < image.getHeight(); j++) {
	//				res[i][j] = rgb2HslUseBuiltin(image.getRGB(i, j));
	//			}
	//		}
	//
	//		return res;
	//	}

	//	public static Tuple3<Double, Double, Double> rgb2HslUseBuiltin(int rgb) {
	//		int r = (rgb & 0XFF0000) >> 16;
	//		int g = (rgb & 0X00FF00) >> 8;
	//		int b = rgb & 0X0000FF;
	//		float[] hsl = Color.RGBtoHSB(r, g, b, null);
	//		return Tuple.of(hsl[0] * 1.0D, hsl[1] * 1.0D, hsl[2] * 1.0D);
	//	}
	//
	//	/**
	//	 * 将RGB点转换为HSL模式的点
	//	 * <p>
	//	 * refer: https://www.rapidtables.com/convert/color/rgb-to-hsl.html
	//	 *
	//	 * @param rgb
	//	 * @return
	//	 */
	//	public static Tuple3<Double, Double, Double> rgb2Hsl(int rgb) {
	//
	//		double r = ((rgb & 0XFF0000) >> 16) / 255.0;
	//		double g = ((rgb & 0X00FF00) >> 8) / 255.0;
	//		double b = (rgb & 0X0000FF) / 255.0;
	//
	//		double cmax = r;
	//		char cmaxFlag = 'r';
	//		if (g > cmax) {
	//			cmax = g;
	//			cmaxFlag = 'g';
	//		}
	//		if (b > cmax) {
	//			cmax = b;
	//			cmaxFlag = 'b';
	//		}
	//
	//		double cmin = Math.min(Math.min(r, g), b);
	//		double delta = cmax - cmin;
	//
	//		double h = 0;
	//		if (cmaxFlag == 'r') {
	//			h = ((g - b) / delta) * (Math.PI / 3);
	//		} else if (cmaxFlag == 'g') {
	//			h = ((b - r) / delta) * (Math.PI / 3) + 2 * Math.PI / 3;
	//		} else { // cmaxFlag == 'b'
	//			h = ((r - g) / delta) * (Math.PI / 3) + 4 * Math.PI / 3;
	//		}
	//
	//		if (h < 0) {
	//			h += Math.PI * 2;
	//		}
	//
	//		if (h < 0) {
	//			System.out.println("impossible");
	//		}
	//
	//		double l = (cmin + cmax) / 2;
	//
	//		double s = 0;
	//		if (Math.abs(delta - 0) < 1E-10) {
	//			s = delta;
	//		} else {
	//			//			s = delta / (1 - Math.abs(2 * l - 1));
	//			s = delta <= 0.5 ? delta / (cmax + cmin) : delta / (2 - cmax - cmin);
	//		}
	//
	//		return Tuple.of(h, s, l);
	//	}

	/**
	 * 捕获当前屏幕快照
	 *
	 * @return
	 */
	public static BufferedImage captureScreenSnapshot() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle screenRectangle = new Rectangle(screenSize);
		Robot robot = null;
		try {
			robot = new Robot();
			return robot.createScreenCapture(screenRectangle);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 用于比较两张图片是否完全一模一样，遇到第一个不同像素时立即返回
	 *
	 * @param img1
	 * @param img2
	 * @return
	 */
	public static boolean fastDiff(BufferedImage img1, BufferedImage img2) {
		if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
			return false;
		}

		for (int i = 0; i < img1.getWidth(); i++) {
			for (int j = 0; j < img1.getHeight(); j++) {
				if (!rgbEquals(img1.getRGB(i, j), img2.getRGB(i, j))) {
					return false;
				}
			}
		}

		return true;
	}

//	/**
//	 * 计算两张图片的差异化矩阵
//	 *
//	 * @param img1
//	 * @param img2
//	 * @return
//	 */
//	public static int[][] diffPointMatrix(BufferedImage img1, BufferedImage img2) {
//
//		if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
//			return new int[0][0];
//		}
//
//		int[][] diffMatrix = new int[img1.getWidth()][img1.getHeight()];
//		for (int i = 0; i < diffMatrix.length; i++) {
//			for (int j = 0; j < diffMatrix[i].length; j++) {
//				int rgb1 = img1.getRGB(i, j);
//				int rgb2 = img2.getRGB(i, j);
//				diffMatrix[i][j] = bitSub(rgb1, rgb2, 0XFF0000) //
//						+ bitSub(rgb1, rgb2, 0X00FF00) //
//						+ bitSub(rgb1, rgb2, 0X0000FF);
//			}
//		}
//
//		return diffMatrix;
//	}

	/**
	 * 比较两幅图片的不同（近似相等判断），将不同的点集返回
	 *
	 * @param img1
	 * @param img2
	 * @return
	 */
	public static List<Location> diffPointList(BufferedImage img1, BufferedImage img2) {

		if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
			return Collections.emptyList();
		}

		// 先做一下灰度处理
		img1 = grayscaleProcessing(img1);
		img2 = grayscaleProcessing(img2);

		List<Location> diffList = new ArrayList<>();
		for (int i = 0; i < img1.getWidth(); i++) {
			for (int j = 0; j < img1.getHeight(); j++) {
				if (!rgbSimilar(img1.getRGB(i, j), img2.getRGB(i, j), 25)) {
					diffList.add(new Location(i, j));
				}
			}
		}

		return diffList;
	}

//	/**
//	 * 对两个数指定的位数做减法
//	 *
//	 * @param rgb1
//	 * @param rgb2
//	 * @param bitSelector
//	 * @return
//	 */
//	private static int bitSub(int rgb1, int rgb2, int bitSelector) {
//		return ((rgb1 & bitSelector) - (rgb2 & bitSelector)) & bitSelector;
//	}

//	/**
//	 * 调整图片亮度，通过RGB通道调整
//	 *
//	 * @param image
//	 * @param adjust
//	 * @return
//	 */
//	public static BufferedImage adjustBrightness(BufferedImage image, int adjust) {
//		BufferedImage returnImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
//		for (int i = 0; i < image.getWidth(); i++) {
//			for (int j = 0; j < image.getHeight(); j++) {
//				int rgb = image.getRGB(i, j);
//				int alpha = rgb & 0XFF000000;
//				int r = rgb & 0X00FF0000 + adjust << 16;
//				int g = rgb & 0X0000FF00 + adjust << 8;
//				int b = rgb & 0X0000FF00 + adjust;
//				returnImage.setRGB(i, j, alpha + r + g + b);
//			}
//		}
//		return returnImage;
//	}

}
