package ro.pricepage.file;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

public class FileUtils {
	public static final String ADMIN = "admin";
	public static final String ANONYMOUS = "anonymous";
	public static final String FILE_CONTENT_PROPERTY_NAME = "fileContent";
	public static final String HEAD_ID_PROPERTY_NAME = "headId";
	public static final String HEAD = "head";
	public static final String STORE_IMAGES_PATH = "images/stores";
	public static final String CHAIN_IMAGES_PATH = "images/chains";
	public static final String PRODUCT_IMAGES_PATH = "images/products";
	
	//DEFAULT IMAGE SIZES
	public static final int PRODUCT_IMAGE_WIDTH = 348;
	public static final int PRODUCT_IMAGE_HEIGHT = 255;
	public static final int STORE_IMAGE_WIDTH = 431;
	public static final int STORE_IMAGE_HEIGHT = 325;
	public static final int CHAIN_IMAGE_WIDTH = 45;
	public static final int CHAIN_IMAGE_HEIGHT = 29;
	
	public static InputStream resize(int maxWidth, int maxHeight, BufferedImage originalImage) throws IOException {
		if (originalImage != null) {
			int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
			int width = originalImage.getWidth();
			int height = originalImage.getHeight();
			float widthRatio = (float) width / maxWidth;
			float heightRatio = (float) height / maxHeight;
			float ratio = widthRatio > heightRatio ? widthRatio : heightRatio;
			width = (int) (width / ratio);
			height = (int) (height / ratio);
			BufferedImage resizedImage = new BufferedImage(width, height, type);
			Graphics2D g = resizedImage.createGraphics();
			g.drawImage(originalImage, 0, 0, width, height, null);
			g.dispose();
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			ImageOutputStream ios = ImageIO.createImageOutputStream(result);
			ImageIO.write(resizedImage, "jpg", ios);
			return new ByteArrayInputStream(result.toByteArray());
		} else {
			return null;
		}
	}
}
