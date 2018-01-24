package zhaocha;

import javaslang.Tuple3;
import org.cc11001100.game.zhaocha.util.ImageUtil;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author CC11001100
 */
public class ImageAlgTest {

	private static BufferedImage image;

	@BeforeClass
	public static void beforeClass() throws IOException {
//		image = ImageIO.read(new File("D:/test/image_alg/001.jpg"));
//		image = ImageIO.read(new File("D:/test/ZhaoCha/left_001.png"));
		image = ImageIO.read(new File("D:/test/ZhaoCha/left_002.png"));
	}

}
