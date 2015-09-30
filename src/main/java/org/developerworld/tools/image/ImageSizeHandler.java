package org.developerworld.tools.image;

import java.io.File;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.apache.commons.io.FilenameUtils;

/**
 * 图片进行大小规格压缩
 * 
 * @version 20111226
 * @author Roy.Huang
 * 
 *@deprecated
 *@see org.developerworld.commons.image project
 */
public class ImageSizeHandler {

	private int maxHeight, maxWidth;
	private String imageType, filePath, fileName, mimeType;

	public ImageSizeHandler(int maxWidth, int maxHeight) {
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
	}

	public void setImageType(String temp) {
		imageType = temp;
	}

	public void setFilePath(String temp) {
		filePath = temp;
	}

	public void setFileName(String temp) {
		fileName = temp;
	}

	public void setMimeType(String temp) {
		mimeType = temp;
	}

	/**
	 * 按比例生成图片
	 * 
	 * @param image
	 * @throws Exception
	 */
	public void fitImageSize(String image) throws Exception {
		fitImageSize(new File(image));
	}

	/**
	 * 按比例生成图片
	 * 
	 * @param imageFile
	 * @throws Exception
	 */
	public void fitImageSize(File imageFile) throws Exception {
		fitSetting(imageFile);
		fitImageSize(ImageHandlerFactory.getImageHandler(mimeType).loadImage(
				imageFile));
	}

	/**
	 * 按比例生成图片
	 * 
	 * @param bi
	 * @throws Exception
	 */
	public void fitImageSize(BufferedImage bi) throws Exception {
		ImageHandlerFactory.getImageHandler(mimeType).saveImage(
				fitImageSize(bi, maxWidth, maxHeight, false),
				filePath + File.separator + fileName);
	}

	/**
	 * 若传入图片对象的大小超过执行大小，则按比例缩小并返回新的图片对象
	 * 
	 * @param file
	 * @param maxWidth
	 * @param maxHeight
	 * @param isAbsfit
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage fitImageSize(String file, int maxWidth,
			int maxHeight, boolean isAbsfit) throws IOException {
		return fitImageSize(
				ImageHandlerFactory.getImageHandler(
						ImageHandlerFactory.getMimeType(file)).loadImage(file),
				maxWidth, maxHeight, isAbsfit);
	}

	/**
	 * 若传入图片对象的大小超过执行大小，则按比例缩小并返回新的图片对象
	 * 
	 * @param file
	 * @param maxWidth
	 * @param maxHeight
	 * @param isAbsfit
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage fitImageSize(File file, int maxWidth,
			int maxHeight, boolean isAbsfit) throws IOException {
		return fitImageSize(
				ImageHandlerFactory.getImageHandler(
						ImageHandlerFactory.getMimeType(file)).loadImage(file),
				maxWidth, maxHeight, isAbsfit);
	}

	/**
	 * 若传入图片对象的大小超过执行大小，则按比例缩小并返回新的图片对象
	 * 
	 * @param img
	 * @param maxWidth
	 * @param maxHeight
	 * @param isAbsFix
	 *            设置是否也按比例自动放大图片
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage fitImageSize(BufferedImage img, int maxWidth,
			int maxHeight, boolean isAbsfit) {
		int width = img.getWidth();
		int height = img.getHeight();
		if (isAbsfit || width > maxWidth || height > maxHeight) {
			float scale = 1;
			scale = Math.min((float) maxWidth / width, (float) maxHeight
					/ height);
			width = Math.max(1, (int) ((float) width * scale));
			height = Math.max(1, (int) ((float) height * scale));
			BufferedImage oImg = new BufferedImage(
					width,
					height,
					img.getType() == BufferedImage.TYPE_CUSTOM ? BufferedImage.TYPE_INT_ARGB
							: img.getType());
			Graphics g = oImg.getGraphics();
			g.drawImage(img, 0, 0, width, height, null);
			g.dispose();
			img = oImg;
		}
		return img;
	}

	/**
	 * 设置参数
	 * 
	 * @param imageFile
	 */
	private void fitSetting(File imageFile) {
		if (filePath == null || filePath.trim().length() <= 0)
			filePath = imageFile.getParent();
		else if (filePath.endsWith("\\") && filePath.endsWith("/"))
			filePath = FilenameUtils.getFullPathNoEndSeparator(filePath);
		if (fileName == null || fileName.trim().length() <= 0)
			fileName = imageFile.getName();
		if (imageType == null || imageType.trim().length() <= 0)
			imageType = FilenameUtils.getExtension(fileName);
		if (mimeType == null || mimeType.trim().length() <= 0)
			mimeType = ImageHandlerFactory.getMimeType(fileName);
	}

	public final static void main(String args[]) throws Exception {
		String file = "e:\\test\\Chrysanthemum.jpg";
		ImageSizeHandler wm = new ImageSizeHandler(100, 100);
		wm.fitImageSize(file);
	}
}
