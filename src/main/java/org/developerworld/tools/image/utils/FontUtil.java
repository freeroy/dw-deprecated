/*********************************************************************
 *
 * 版本：1.0
 * 制作人：黄若儒 Roy.Huang
 * 创建日期：2008年05月06日
 * 功能描述：文字工具类
 * 
 ***************************************************************************/
package org.developerworld.tools.image.utils;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * 
 * @author Roy
 *
 *@deprecated
 *@see org.developerworld.commons.image project
 */
public class FontUtil {

	/**
	 * 自适应字体大小
	 * 
	 * @param font
	 * @param str
	 * @param maxWidth
	 * @param maxHeight
	 * @return
	 */
	public static Font fitFontSize(Font font, String str, int maxWidth,
			int maxHeight) {
		Font oFont = font;
		FontMetrics fm = getFontMetrics(font, str);
		while ((fm.stringWidth(str) > maxWidth || fm.getHeight() > maxHeight)
				&& font.getSize() > 0) {
			font = new Font(font.getFamily(), font.getStyle(),
					font.getSize() - 1);
			fm = getFontMetrics(font, str);
		}
		oFont = font;
		return oFont;
	}

	/**
	 * 获取字体FontMetrics对象
	 * 
	 * @param font
	 * @param str
	 * @return
	 */
	public static FontMetrics getFontMetrics(Font font, String str) {
		FontMetrics fm = null;
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		g.drawString(str, 0, 0);
		fm = g.getFontMetrics(font);
		g.dispose();
		return fm;
	}
}