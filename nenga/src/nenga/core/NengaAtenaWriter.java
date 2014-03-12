/*
 *  $Id: NengaAtenaWriter.java 2014/02/16 15:39:40 masamitsu $
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

package nenga.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pw.core.pdf.PWPdfField;
import pw.core.pdf.PWPdfFile;

/**
 * @author masamitsu
 *
 */
public class NengaAtenaWriter {
	private static final boolean EMBEDDED = true;
	private static final PWPdfFile.Font FONT = PWPdfFile.Font.HEISEI_MIN_W3;
	private static final PWPdfFile.Encoding ENCODING = PWPdfFile.Encoding.UNIJIS_UCS2_V;
	
	private static final int SENDER_ZIPCODE_FONT_SIZE = 11;
	private static final float SENDER_ZIPCODE_TOP_BASE = 67;
	private static final float[] SENDER_ZIPCODE_LEFT_BASES = { 21, 32, 44, 57, 69, 81, 92 };
	private static final float SENDER_NAME_BOTTOM_BASE = 80;
	private static final float SENDER_NAME_LEFT_BASE = 10;
	private static final float SENDER_NAME_HEIGHT = 160;
	private static final float SENDER_NAME_FONT_SIZE = 12;
	private static final float SENDER_ADDRESS_BOTTOM_BASE = 80;
	private static final float SENDER_ADDRESS_HEIGHT = 170;
	private static final int SENDER_ADDRESS_FONT_SIZE = 12;
	
	private static final int RECEIVER_ZIPCODE_FONT_SIZE = 14;
	private static final float RECEIVER_ZIPCODE_TOP_BASE = 380;
	private static final float[] RECEIVER_ZIPCODE_LEFT_BASES = { 133, 153, 173, 194, 213, 233, 253 };
	private static final float RECEIVER_NAME_TOP_BASE = 330;
	private static final float RECEIVER_NAME_CENTER_BASE = 145;
	private static final float RECEIVER_NAME_HEIGHT = 280;
	private static final float RECEIVER_NAME_FONT_SIZE = 22;
	private static final float RECEIVER_ADDRESS_TOP_BASE = 340;
	private static final float RECEIVER_ADDRESS_LEFT_BASE = 260;
	private static final float RECEIVER_ADDRESS_HEIGHT = 280;
	private static final int RECEIVER_ADDRESS_FONT_SIZE = 14;
	
	private class Item {
		private String zipcode;
		private String address;
		private String familyName;
		private String[] firstNames;
		private String honorific;
	}
	
	private Item sender;
	private List<Item> receivers;
	
	public NengaAtenaWriter() {
		sender = new Item();
		receivers = new ArrayList<Item>();
	}
	
	public void setSenderAddress(String zipcode, String address, String familyName, String... firstNames) {
		sender.zipcode = zipcode;
		sender.address = address;
		sender.familyName = familyName;
		sender.firstNames = firstNames;
	}
	
	public void addReceiverAddress(String zipcode, String address, String honorific, String familyName, String... firstNames) {
		Item receiver = new Item();
		receiver.zipcode = zipcode;
		receiver.address = address;
		receiver.honorific = honorific;
		receiver.familyName = familyName;
		receiver.firstNames = firstNames;
		receivers.add(receiver);
	}
	
	public void save(String path) {
		File file = new File(path);
		save(file);
	}
	
	public void save(File file) {
		PWPdfFile pdf = PWPdfFile.create(file, PWPdfFile.Size.POSTCARD);
		pdf.open();
		try {
			for (Item receiver : receivers) {
				List<PWPdfField> fields = getFields(receiver);
				for (PWPdfField field : fields) {
					if (field.text == null) {
						continue;
					}
					pdf.addField(field);
				}
				pdf.insertNewPage();
			}
		} finally {
			pdf.close();
		}
	}
	
	private List<PWPdfField> getFields(Item receiver) {
		List<PWPdfField> fields = new ArrayList<PWPdfField>();
		addSenderInfo(fields);
		addReceiverInfo(fields, receiver);
		return fields;
	}
	
	/**
	 * @param fields
	 */
	private void addSenderInfo(List<PWPdfField> fields) {
		addZipCode(fields, sender.zipcode, SENDER_ZIPCODE_TOP_BASE, SENDER_ZIPCODE_LEFT_BASES, SENDER_ZIPCODE_FONT_SIZE);
		addAddress(fields, sender.address, new PositionCalculator() {
			@Override
			public float getTopBase(float fontSize, int stringLength) {
				return SENDER_ADDRESS_BOTTOM_BASE + (fontSize * stringLength);
			}
			
			@Override
			public float getLeftBase(float fontSize, int count) {
				int maxFirstNameLength = getMaxLength(sender.firstNames);
				int familyNameLength = sender.familyName.length();
				int totalLength = familyNameLength + 1 + maxFirstNameLength;
				float nameFont = getPreferredFontSize((float)SENDER_NAME_FONT_SIZE, SENDER_NAME_HEIGHT, totalLength);
				float offset = nameFont * sender.firstNames.length;
				return fontSize * (2 + count) + offset;
			}

			@Override
			public float getFontSize(int length) {
				return getPreferredFontSize((float)SENDER_ADDRESS_FONT_SIZE, SENDER_ADDRESS_HEIGHT, length);
			}
		});
		addNameFields(fields, sender, new PositionCalculator() {
			@Override
			public float getTopBase(float fontSize, int totalNameLength) {
				return SENDER_NAME_BOTTOM_BASE + (fontSize * totalNameLength);
			}
			
			@Override
			public float getLeftBase(float fontSize, int firstNamesCount) {
				return SENDER_NAME_LEFT_BASE + (fontSize * firstNamesCount);
			}

			@Override
			public float getFontSize(int length) {
				return getPreferredFontSize((float)SENDER_NAME_FONT_SIZE, SENDER_NAME_HEIGHT, length);
			}
		});
	}
	
	private void addReceiverInfo(List<PWPdfField> fields, Item receiver) {
		addZipCode(fields, receiver.zipcode, RECEIVER_ZIPCODE_TOP_BASE, RECEIVER_ZIPCODE_LEFT_BASES, RECEIVER_ZIPCODE_FONT_SIZE);
		addAddress(fields, receiver.address, new PositionCalculator() {
			@Override
			public float getTopBase(float preferredFontSize, int totalNameLength) {
				return RECEIVER_ADDRESS_TOP_BASE;
			}
			
			@Override
			public float getLeftBase(float fontSize, int firstNamesCount) {
				return RECEIVER_ADDRESS_LEFT_BASE;
			}

			@Override
			public float getFontSize(int length) {
				return getPreferredFontSize((float)RECEIVER_ADDRESS_FONT_SIZE, RECEIVER_ADDRESS_HEIGHT, length);
			}
		});
		addNameFields(fields, receiver, new PositionCalculator() {
			@Override
			public float getTopBase(float fontSize, int totalNameLength) {
				return RECEIVER_NAME_TOP_BASE;
			}
			
			@Override
			public float getLeftBase(float fontSize, int firstNamesCount) {
				return RECEIVER_NAME_CENTER_BASE + (fontSize / 2.0f) * (firstNamesCount - 1);
			}

			@Override
			public float getFontSize(int length) {
				return getPreferredFontSize((float)RECEIVER_NAME_FONT_SIZE, RECEIVER_NAME_HEIGHT, length);
			}
		});
	}
	
	private void addZipCode(List<PWPdfField> fields, String zipcode, float topBase, float[] leftBases, int fontSize) {
		if (zipcode == null) {
			return;
		}
		
		char[] chars = zipcode.toCharArray();
		int max = chars.length <= leftBases.length ? chars.length : leftBases.length;
		for (int i = 0; i < max; i++) {
			PWPdfField field = new PWPdfField(leftBases[i], topBase, String.format("%c", chars[i]), fontSize, FONT, ENCODING, EMBEDDED);
			fields.add(field);
		}
	}
	
	private void addAddress(List<PWPdfField> fields, String address, PositionCalculator calculator) {
		String space = "";
		int maxLength = 0;
		String[] addressArray = address.split(" ");
		for (int i = 0; i < addressArray.length; i++) {
			addressArray[i] = space + addressArray[i];
			space += "　";
			
			if (maxLength < addressArray[i].length()) {
				maxLength = addressArray[i].length();
			}
		}
		
		float fsize = calculator.getFontSize(maxLength);
		
		float topBase = calculator.getTopBase(fsize, maxLength);
		
		float leftBase = calculator.getLeftBase(fsize, addressArray.length);
		float[] leftBases = new float[addressArray.length];
		for (int i = 0; i < addressArray.length; i++) {
			leftBases[i] = leftBase - (i * fsize);
		}
		
		for (int i = 0; i < addressArray.length; i++) {
			PWPdfField field = new PWPdfField(leftBases[i], topBase, addressArray[i], fsize, FONT, ENCODING, true);
			fields.add(field);
		}
	}
	
	private static float getPreferredFontSize(float defaultFontSize, float height, int length) {
		if (length == 0) {
			return defaultFontSize;
		}
		
		float size = height / length;
		if (defaultFontSize < size) {
			return defaultFontSize;
		} else {
			return size;
		}
	}
	
	interface PositionCalculator {
		float getTopBase(float fontSize, int length);
		float getLeftBase(float fontSize, int count);
		float getFontSize(int length);
	}
	
	private static int getMaxLength(String[] values) {
		int maxLength = 0;
		for (String value : values) {
			if (maxLength < value.length()) {
				maxLength = value.length();
			}
		}
		return maxLength;
	}
	
	private void addNameFields(List<PWPdfField> fields, Item item, PositionCalculator calculator) {
		int maxFirstNameLength = getMaxLength(item.firstNames);
		int familyNameLength = item.familyName.length();
		int honorificLength = item.honorific == null ? 0 : item.honorific.length();
		int totalLength = familyNameLength + 1 + maxFirstNameLength + (honorificLength == 0 ? 0 : 1) + honorificLength;
		
		float preferredFontSize = calculator.getFontSize(totalLength);
		
		float topBase = calculator.getTopBase(preferredFontSize, totalLength);
		
		float firstNamesTop = topBase - (preferredFontSize * (familyNameLength + (familyNameLength == 0 ? 0 : 1)));
		
		float honerTop = firstNamesTop - (preferredFontSize * (maxFirstNameLength + 1));
		
		float leftBase = calculator.getLeftBase(preferredFontSize, item.firstNames.length);
		
		float[] leftBases = new float[item.firstNames.length];
		float[] fontSizes = new float[item.firstNames.length];
        leftBases[0] = leftBase;
        fontSizes[0] = preferredFontSize;
		for (int i = 1; i < item.firstNames.length; i++) {
			leftBases[i] = leftBases[i - 1] - preferredFontSize;
			fontSizes[i] = preferredFontSize;
		}
		
		PWPdfField field;
		field = new PWPdfField(leftBase, topBase, item.familyName, preferredFontSize, FONT, ENCODING, EMBEDDED);
		fields.add(field);
		for (int i = 0; i < item.firstNames.length; i++) {
			field = new PWPdfField(leftBases[i], firstNamesTop, item.firstNames[i], preferredFontSize, FONT, ENCODING, EMBEDDED);
			fields.add(field);
			
			field = new PWPdfField(leftBases[i], honerTop, item.honorific, preferredFontSize, FONT, ENCODING, EMBEDDED);
			fields.add(field);
		}
	}

}
