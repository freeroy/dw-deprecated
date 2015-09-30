package org.developerworld.tools.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * @version 20090422
 * @author Roy.Huang
 *
 *@deprecated
 *@see org.developerworld.commons.image project
 */
public interface ImageHandler {

	/**
	 * 加载图片
	 * @param source
	 * @return
	 * @throws IOException
	 */
	public BufferedImage loadImage(String source) throws IOException;

	/**
	 *  加载图片
	 * @param source
	 * @return
	 * @throws IOException
	 */
	public BufferedImage loadImage(File source) throws IOException;
	
	/**
	 *  加载图片
	 * @param source
	 * @return
	 * @throws IOException
	 */
	public BufferedImage loadImage(InputStream source) throws IOException;
	
	/**
	 *  加载图片
	 * @param source
	 * @return
	 * @throws IOException
	 */
	public BufferedImage loadImage(URL source) throws IOException;
	
	/**
	 * 输出图片
	 * @param source
	 * @param destination
	 * @throws Exception
	 */
	public void saveImage(BufferedImage source,String destination) throws Exception;
	
	/**
	 * 输出图片
	 * @param source
	 * @param destination
	 * @throws Exception
	 */
	public void saveImage(BufferedImage source,OutputStream destination) throws Exception;
}