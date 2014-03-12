/*
 *  $Id: PWPdfFile.java 2014/02/09 8:36:29 masamitsu $
 *
 *  ===============================================================================
 *
 *   Copyright (C) 2013  Masamitsu Oikawa  <oicawa@gmail.com>
 *   
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *   
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *   
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 *
 *  ===============================================================================
 */

package pw.core.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import pw.core.PWError;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author masamitsu
 *
 */
public class PWPdfFile {
	// Page Size
	public enum Size {
		POSTCARD,
		A4,
	}
	private static Rectangle[] rectangles = { PageSize.POSTCARD, PageSize.A4, PageSize.A4};
	
	// Font
	public enum Font {
		KOZMINPRO_REGULAR,
		HEISEI_MIN_W3,
		HEISEI_KAKUGO_W5,
	}
	private static final String[] FONT_NAMES = { "KozMinPro-Regular", "HeiseiMin-W3", "HeiseiKakuGo-W5", "HeiseiKakuGo-W5" };
	
	// Encoding
	public enum Encoding {
		UNIJIS_UCS2_H,
		UNIJIS_UCS2_V,
		UNIJIS_UCS2_HW_H,
		UNIJIS_UCS2_HW_V,
	}
	private static final String[] ENCODING_NAMES = { "UniJIS-UCS2-H", "UniJIS-UCS2-V", "UniJIS-UCS2-HW-H", "UniJIS-UCS2-HW-V", "UniJIS-UCS2-H" };
	
	private Document document;
	private PdfWriter writer;
	
	public static PWPdfFile create(File file, Size size) {
		return create(file, size, 0.0f, 0.0f, 0.0f, 0.0f);
	}
	
	public static PWPdfFile create(File file, Size size, float marginTop, float marginLeft, float marginBottom, float marginRight) {
		return new PWPdfFile(file, size, marginTop, marginLeft, marginBottom, marginRight);
	}
	
	protected PWPdfFile(File file, Size size, float marginTop, float marginLeft, float marginBottom, float marginRight) {
		Rectangle rect = size.ordinal() < rectangles.length ? rectangles[size.ordinal()] : rectangles[rectangles.length - 1];
		document = new Document(rect, marginLeft, marginRight, marginTop, marginBottom);
		try {
			writer = PdfWriter.getInstance(document, new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			throw new PWError(e, "Failed to create PDF Writer");
		} catch (DocumentException e) {
			throw new PWError(e, "Failed to create PDF Writer");
		}
	}
	
	public void open() {
		document.open();
	}
	
	public void open(String athor, String subject) {
		document.addAuthor(athor);
		document.addSubject(subject);
		document.open();
	}
	
	public void close() {
		document.close();
	}
	
	protected BaseFont getFont(Font font, Encoding encoding, boolean isEmbedded) {
		String fontName = font.ordinal() < FONT_NAMES.length ? FONT_NAMES[font.ordinal()] : FONT_NAMES[FONT_NAMES.length - 1];
		String encodingName = encoding.ordinal() < ENCODING_NAMES.length ? ENCODING_NAMES[encoding.ordinal()] : ENCODING_NAMES[ENCODING_NAMES.length - 1];
		try {
			return BaseFont.createFont(fontName, encodingName, isEmbedded ? BaseFont.EMBEDDED : BaseFont.NOT_EMBEDDED);
		} catch (DocumentException e) {
			throw new PWError(e, "Failed to get font.");
		} catch (IOException e) {
			throw new PWError(e, "Failed to get font.");
		}
	}
	
	public void addField(PWPdfField field) {
		PdfContentByte contentByte = writer.getDirectContent();
		contentByte.beginText();
		BaseFont baseFont = getFont(field.font, field.encoding, field.isEmbedded);
		contentByte.setFontAndSize(baseFont, field.fontSize);
		contentByte.setTextMatrix(field.x, field.y);
		contentByte.showText(field.text);
		contentByte.endText();
	}
	
	public void insertNewPage() {
		document.newPage();
	}
}
