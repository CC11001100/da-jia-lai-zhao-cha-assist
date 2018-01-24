package zhaocha;

import javaslang.Tuple2;
import org.cc11001100.game.zhaocha.util.ImageUtil;
import org.cc11001100.game.zhaocha.entity.Board;
import org.cc11001100.game.zhaocha.entity.Location;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author CC11001100
 */
public class ImageUtilTest {

	private static BufferedImage bufferedImage;

	@BeforeClass
	public static void beforeClass() {
		try {
			bufferedImage = ImageIO.read(new File("D:/test/ZhaoCha/2018-01-12_020235.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testFindBaseLocation() {
		Location p = ImageUtil.findBaseLocation(bufferedImage);
		System.out.println(p);
	}

	@Test
	public void testSplit() throws IOException {
		Location p = ImageUtil.findBaseLocation(bufferedImage);
		Tuple2<Board, Board> board = ImageUtil.mattingImageBoard(bufferedImage, p);

		ImageIO.write(board._1.getImage() , "jpg", new File("D:/test/ZhaoCha/left_001.png"));
		ImageIO.write(board._2.getImage() , "jpg", new File("D:/test/ZhaoCha/left_002.png"));

	}

	@Test
	public void testDiff() throws IOException {

		File[] imageFile = new File("D:\\test\\ZhaoCha").listFiles((dir, name) -> name.endsWith("png"));
		assert imageFile != null;
		for(File file : imageFile){
			BufferedImage image = ImageIO.read(file);
			Location p = ImageUtil.findBaseLocation(image);
			Tuple2<Board, Board> board = ImageUtil.mattingImageBoard(image, p);

			List<Location> locationList = ImageUtil.diffPointList(board._1.getImage(), board._2.getImage());

			Graphics g1 = board._1.getImage().getGraphics();
			Graphics g2 = board._2.getImage().getGraphics();
			g1.setColor(Color.GREEN);
			g2.setColor(Color.GREEN);

			locationList.forEach(x->{
				g1.drawRect(x.getX(), x.getY(), 1, 1);
				g2.drawRect(x.getX(), x.getY(), 1, 1);
			});

			ImageIO.write(board._1.getImage() , "jpg", new File("D:/test/ZhaoCha/result/" + file.getName()+ "_left.png"));
			ImageIO.write(board._2.getImage() , "jpg", new File("D:/test/ZhaoCha/result/" + file.getName()+ "_right.png"));
		}

	}

	@Test
	public void testDrawAttention() throws IOException, InterruptedException {

//		BufferedImage image1 = ImageIO.read(new File("D:/test/ZhaoCha/result/2018-01-12_020349.png_left.png"));
//		BufferedImage image2 = ImageIO.read(new File("D:/test/ZhaoCha/result/2018-01-12_020349.png_right.png"));
//
//		List<Location> locationList = ImageUtil.diff(image1, image2);
//
//		DrawAttention t = new DrawAttention(locationList);
//		new Thread(t).start();
//
//		AWTUtilities.setWindowOpaque(t, false);
////		AWTUtilities.setWindowOpacity(t, 0f);
//
//		while(true){
//			TimeUnit.SECONDS.sleep(999);
//		}


	}

}
