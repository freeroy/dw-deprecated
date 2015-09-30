package org.developerworld.tools.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.developerworld.tools.image.utils.FontUtil;
import org.developerworld.tools.image.utils.PositionLayout;

/**
 * 图片水印处理类
 * @version 20111226
 * @author Roy.Huang
 * 
 *@deprecated
 *@see org.developerworld.commons.image project
 */
public class WaterMark {
	private int markPosition = PositionLayout.BOTTOM_RIGHT;
	private float markAlpha = 0.4f;
	private String imageType, mimeType, filePath, fileName;
	private Font font = new Font(null, Font.BOLD, 20);
	private Color fontColor = Color.WHITE;

	public WaterMark() {
	}

	public void setAlpha(float temp) {
		markAlpha = temp;
	}

	public void setImageType(String temp) {
		imageType = temp;
	}

	public void setMimeType(String temp) {
		mimeType = temp;
	}

	public void setFilePath(String temp) {
		filePath = temp;
	}

	public void setFileName(String temp) {
		fileName = temp;
	}

	public void setFontColor(Color temp) {
		fontColor = temp;
	}

	public void setFont(Font temp) {
		font = temp;
	}

	public void setMarkPostion(int temp) {
		markPosition = temp;
	}

	public void doFontMark(String sImg, String sFont) throws Exception {
		doFontMark(new File(sImg), sFont);
	}

	public void doFontMark(File fImg, String sFont) throws Exception {
		fitSetting(fImg);
		doFontMark(ImageHandlerFactory.getImageHandler(mimeType)
				.loadImage(fImg), sFont);
	}

	public void doFontMark(BufferedImage img, String sFont) throws Exception {
		font = FontUtil.fitFontSize(font, sFont, img.getWidth(),
				img.getHeight());
		FontMetrics fm = FontUtil.getFontMetrics(font, sFont);
		int p[] = getPosition(img.getWidth(), img.getHeight(),
				fm.stringWidth(sFont), fm.getHeight());
		p[1] += fm.getAscent();
		Graphics2D g = img.createGraphics();
		g.setColor(fontColor);
		g.setFont(font);
		g.drawString(sFont, p[0], p[1]);
		g.dispose();
		saveImage(img);
	}

	public void doImageMark(String sImg, String sIcon) throws Exception {
		doImageMark(new File(sImg), new File(sIcon));
	}

	public void doImageMark(File fImg, File fIcon) throws Exception {
		fitSetting(fImg);
		doImageMark(
				ImageHandlerFactory.getImageHandler(mimeType).loadImage(fImg),
				ImageHandlerFactory.getImageHandler(mimeType).loadImage(fIcon));
	}

	public void doImageMark(BufferedImage img, BufferedImage icon)
			throws Exception {
		icon = ImageSizeHandler.fitImageSize(icon, img.getWidth(),
				img.getHeight(), false);
		Graphics2D g = img.createGraphics();
		int p[] = getPosition(img.getWidth(), img.getHeight(), icon.getWidth(),
				icon.getHeight());
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				markAlpha));
		g.drawImage(icon, p[0], p[1], p[2], p[3], null);
		g.dispose();
		saveImage(img);
	}

	/**
	 * 设置参数
	 * 
	 * @param imageFile
	 */
	private void fitSetting(File imageFile) {
		if (filePath == null || filePath.trim().length() <= 0)
			filePath = imageFile.getParent();
		else if(filePath.endsWith("\\") && filePath.endsWith("/"))
			filePath=FilenameUtils.getFullPathNoEndSeparator(filePath);
		if (fileName == null || fileName.trim().length() <= 0)
			fileName = imageFile.getName();
		if (imageType == null || imageType.trim().length() <= 0)
			imageType = FilenameUtils.getExtension(fileName);
		if (mimeType == null || mimeType.trim().length() <= 0)
			mimeType = ImageHandlerFactory.getMimeType(fileName);
	}

	/**
	 * 返回水印定位
	 * 
	 * @param aW
	 * @param aH
	 * @param bW
	 * @param bH
	 * @return
	 */
	private int[] getPosition(int aW, int aH, int bW, int bH) {
		int p[] = { 0, 0, bW, bH };
		switch (markPosition) {
		case PositionLayout.TOP_LEFT:
			break;
		case PositionLayout.TOP_CENTER:
			p[0] = (aW - bW) / 2;
			break;
		case PositionLayout.TOP_RIGHT:
			p[0] = aW - bW;
			break;
		case PositionLayout.MIDDLE_LEFT:
			p[1] = (aH - bH) / 2;
			break;
		case PositionLayout.MIDDLE_CENTER:
			p[0] = (aW - bW) / 2;
			p[1] = (aH - bH) / 2;
			break;
		case PositionLayout.MIDDLE_RIGHT:
			p[0] = aW - bW;
			p[1] = (aH - bH) / 2;
			break;
		case PositionLayout.BOTTOM_LEFT:
			p[1] = aH - bH;
			break;
		case PositionLayout.BOTTOM_CENTER:
			p[0] = (aW - bW) / 2;
			p[1] = aH - bH;
			break;
		case PositionLayout.BOTTOM_RIGHT:
			p[0] = aW - bW;
			p[1] = aH - bH;
			break;
		}
		return p;
	}

	/**
	 * 保存图片
	 * 
	 * @param img
	 * @throws Exception
	 */
	private void saveImage(BufferedImage img) throws Exception {
		ImageHandler ih = ImageHandlerFactory.getImageHandler(mimeType);
		ih.saveImage(img, filePath+File.separator + fileName);
	}
	
	public final static void main(String args[]) throws Exception{
		String file="e:\\test\\Chrysanthemum.jpg";
		WaterMark wm=new WaterMark();
		wm.doFontMark(file, "test");
	}
}
