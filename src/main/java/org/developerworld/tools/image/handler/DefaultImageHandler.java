package org.developerworld.tools.image.handler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import org.developerworld.tools.image.ImageHandler;

import com.sun.jimi.core.Jimi;
import com.sun.jimi.core.JimiException;

/**
 * @version 20090422
 * @author Roy.Huang
 *
 *@deprecated
 *@see org.developerworld.commons.image project
 */
public class DefaultImageHandler implements ImageHandler {
	
	private String mimeType; 
	
	public DefaultImageHandler(String mimeType){
		this.mimeType=mimeType;
	}
	
	public BufferedImage loadImage(String source)
			throws IOException {
		return loadImage(new File(source));
	}

	public BufferedImage loadImage(File source) throws IOException {
		return ImageIO.read(source);
	}
	
	public BufferedImage loadImage(InputStream source) throws IOException{
		return ImageIO.read(source);
	}
	
	public BufferedImage loadImage(ImageInputStream source) throws IOException{
		return ImageIO.read(source);
	}
	
	public BufferedImage loadImage(URL source) throws IOException{
		return ImageIO.read(source);
	}
	public void saveImage(BufferedImage source,String destination) throws JimiException{
		Jimi.putImage(this.mimeType, source, destination);
	}
	
	public void saveImage(BufferedImage source,OutputStream destination) throws JimiException{
		Jimi.putImage(this.mimeType, source,destination);
	}
}
