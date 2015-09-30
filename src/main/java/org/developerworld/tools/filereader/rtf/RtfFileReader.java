package org.developerworld.tools.filereader.rtf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

import org.developerworld.tools.filereader.FileReader;

/**
 * rtf文件内容读取器
 * 
 * @author Roy Huang
 * @version 20130417
 * 
 *@deprecated
 *@see org.developerworld.commons.filereader project
 */
public class RtfFileReader implements FileReader {

	public String readFileToString(File file) throws IOException,
			BadLocationException {
		String rst = null;
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			DefaultStyledDocument styledDoc = new DefaultStyledDocument();
			new RTFEditorKit().read(inputStream, styledDoc, 0);
			// 提取文本，读取中文需要使用ISO8859_1编码，否则会出现乱码
			rst = new String(styledDoc.getText(0, styledDoc.getLength())
					.getBytes("ISO8859_1"));
		} finally {
			if (inputStream != null)
				inputStream.close();
		}
		return rst;
	}

}
