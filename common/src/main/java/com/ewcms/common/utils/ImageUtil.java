package com.ewcms.common.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图象工具
 * 
 * @author 吴智俊
 */
public class ImageUtil {

	private static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    private static final String[] IMAGES_SUFFIXES = {
        "bmp", "jpg", "jpeg", "gif", "png", "tiff"
};

	/**
	 * 是否是图片附件
	 *
	 * @param filename
	 * @return
	 */
	public static boolean isImage(String filename) {
	    if (filename == null || filename.trim().length() == 0) return false;
	    return ArrayUtils.contains(IMAGES_SUFFIXES, FilenameUtils.getExtension(filename).toLowerCase());
	}
	
	/**
	 * 图象压缩
	 * 
	 * @param sourceFile 源文件
	 * @param targetPath 目标文件名
	 * @param width 压缩宽度
	 * @param hight 压缩高度
	 * @return 压缩是否成功
	 */
	public static Boolean compression(MultipartFile sourceFile, String targetPath, int width, int hight) {
		try {
			File targetFile = new File(targetPath);

			String format = getFormatName(sourceFile);
			logger.info("Image Format is {}", format);

			BufferedImage srcImage = ImageIO.read(sourceFile.getInputStream());
			if (width > 0 || hight > 0) {
				srcImage = resize(srcImage, width, hight);
			}
			ImageIO.write(srcImage, format, targetFile);
		} catch (IIOException e) {
			logger.error("Image IIOException:{}", e);
			return false;
		} catch (IOException e) {
			logger.error("Image file IOException:{}", e);
			return false;
		}
		return true;
	}

	/**
	 * 图象按比率压缩
	 * 
	 * @param source 源图象
	 * @param targetW 目标宽度
	 * @param targetH 目标长度
	 * @return BufferedImage
	 */
	private static BufferedImage resize(BufferedImage source, int targetW, int targetH) {
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();
		if (sx > sy) {
			sx = sy;
			targetW = (int) (sx * source.getWidth());
		} else {
			sy = sx;
			targetH = (int) (sy * source.getHeight());
		}
		if (type == BufferedImage.TYPE_CUSTOM) {
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else
			target = new BufferedImage(targetW, targetH, type);
		Graphics2D g = target.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}

	/**
	 * 读图片格式
	 * 
	 * @param file
	 *            图片文件
	 * @return
	 * @throws IOException
	 */
	public static String getFormatName(MultipartFile sourcePath) throws IOException {
		ImageInputStream iis = ImageIO.createImageInputStream(sourcePath.getInputStream());
		Iterator<ImageReader> iterator = ImageIO.getImageReaders(iis);
		while (iterator.hasNext()) {
			ImageReader reader = (ImageReader) iterator.next();
			return reader.getFormatName();
		}

		throw new IOException("It is not image");
	}
	
//	public static Boolean compression(String sourcePath, String targetPath, int width, int hight) {
//
//		try {
//			File sourceFile = new File(sourcePath);
//			File targetFile = new File(targetPath);
//
//			String format = getFormatName(new File(sourcePath));
//			logger.info("Image Format is {}", format);
//
//			BufferedImage srcImage = ImageIO.read(sourceFile);
//			if (width > 0 || hight > 0) {
//				srcImage = resize(srcImage, width, hight);
//			}
//			ImageIO.write(srcImage, format, targetFile);
//		} catch (IIOException e) {
//			logger.error("Image IIOException:{}", e);
//			return false;
//		} catch (IOException e) {
//			logger.error("Image file IOException:{}", e);
//			return false;
//		}
//		return true;
//	}

//	public static String getFormatName(File file) throws IOException {
//	    ImageInputStream iis = ImageIO.createImageInputStream(file);
//	    Iterator<ImageReader> iterator = ImageIO.getImageReaders(iis);
//	    while (iterator.hasNext()) {
//	       ImageReader reader = (ImageReader)iterator.next();
//	       return reader.getFormatName();
//	    }
//	    
//	    throw new IOException("It is not image");
//    }
	
	/**
	 * 图片水印
	 * 
	 * @param pressImg 水印图片
	 * @param targetImg 目标图片
	 * @param x 修正值 默认在中间
	 * @param y 修正值 默认在中间
	 * @param alpha 透明度
	 */
//	public final static void pressImage(byte[] pressImg, String formatName, String targetImg, int x, int y, float alpha) {
	public final static void pressImage(String pressImg, String targetImg, String formatName, int x, int y, float alpha) {
		try {
			File img = new File(targetImg);
			Image src = ImageIO.read(img);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);
			//水印文件

			Image src_biao = ImageIO.read(new File(pressImg));
//			Image src_biao = ImageIO.read(new ByteArrayInputStream(pressImg));
			
			int wideth_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
			g.drawImage(src_biao, (wideth - wideth_biao) / 2, (height - height_biao) / 2, wideth_biao, height_biao, null);
			//水印文件结束

			g.dispose();
			ImageIO.write((BufferedImage) image, formatName, img);
			
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
	}

	/**
	 * 文字水印
	 * 
	 * @param pressText 水印文字
	 * @param targetImg 目标图片
	 * @param fontName 字体名称
	 * @param fontStyle 字体样式
	 * @param color 字体颜色
	 * @param fontSize 字体大小
	 * @param x 修正值
	 * @param y 修正值
	 * @param alpha 透明度
	 */
	public static void pressText(String pressText, String targetImg, String fontName, int fontStyle, Color color, int fontSize, int x, int y, float alpha) {
		try {
			File img = new File(targetImg);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(color);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
			g.drawString(pressText, (width - (getLength(pressText) * fontSize)) / 2 + x, (height - fontSize) / 2 + y);
			g.dispose();
			ImageIO.write((BufferedImage) image, "jpg", img);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
	}
	
	public static int getLength(String text) {
		int length = 0;
		for (int i = 0; i < text.length(); i++) {
			if (new String(text.charAt(i) + "").getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length / 2;
	}
	
	public static void main(String[] args) {
        pressImage("F:/logo.png", "F:/source.jpg", "jpg", 100, 100, 0.9f);
        pressText("yoyoyo小屋", "F:/source1.jpg", "华文楷体", 5, Color.BLACK, 20, 500, 500, 0.9f);
    }
}
