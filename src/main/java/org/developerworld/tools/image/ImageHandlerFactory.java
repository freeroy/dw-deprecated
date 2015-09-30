package org.developerworld.tools.image;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.developerworld.tools.image.handler.DefaultImageHandler;
import org.developerworld.tools.image.handler.GifImageHandler;
import org.developerworld.tools.image.handler.TiffImageHandler;

/**
 * @version 20110111
 * @author Roy Huang
 * 
 *@deprecated
 *@see org.developerworld.commons.image project
 */
public class ImageHandlerFactory {

	/**
	 * 根据传入图片的mimeType类型,返回图片处理类
	 * 
	 * @param mimeType
	 * @return
	 */
	public static ImageHandler getImageHandler(String mimeType) {
		mimeType = mimeType.toLowerCase();
		if (mimeType.equals("image/gif"))
			return new GifImageHandler();
		else if (mimeType.equals("image/tif") || mimeType.equals("image/tiff"))
			return new TiffImageHandler();
		else
			return new DefaultImageHandler(mimeType);
	}

	/**
	 * 获取文件mime类型
	 * 
	 * @param file
	 * @return
	 */
	public static String getMimeType(File file) {
		return getMimeType(file.getName());
	}

	/**
	 * 获取文件mime类型
	 * 
	 * @param img
	 * @return
	 */
	public static String getMimeType(String img) {
		return "image/" + FilenameUtils.getExtension(img);
	}
}
