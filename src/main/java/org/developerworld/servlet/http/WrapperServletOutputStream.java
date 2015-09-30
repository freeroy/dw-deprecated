package org.developerworld.servlet.http;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

/**
 * 包装过的ServletOutputStream
 * 
 * @author Roy Huang
 * @version 20121225
 * 
 * @deprecated
 * @see org.developerworld.commons.servlet project
 */
public class WrapperServletOutputStream extends ServletOutputStream {

	private OutputStream outputStream;

	public WrapperServletOutputStream(OutputStream outputStream) {
		super();
		this.outputStream = outputStream;
	}

	@Override
	public void write(int b) throws IOException {
		outputStream.write(b);
	}

}
