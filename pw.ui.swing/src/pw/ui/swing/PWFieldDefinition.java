/*
 *  $Id: PWFieldDefinition.java 2014/01/20 22:37:53 masamitsu $
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

package pw.ui.swing;

import pw.core.PWUtilities;
import pw.core.annotation.PWFieldBasicInfo;
import pw.core.annotation.PWItemBasicInfo;
import pw.core.item.PWItem;

/**
 * @author masamitsu
 *
 */
@PWItemBasicInfo(caption = "Field Definitions", tableName = "FieldDefinitions")
public class PWFieldDefinition extends PWItem {
	
	@PWFieldBasicInfo(caption = "Table Name", type = "varchar(100)", primary = true)
	private String tableName;
	
	@PWFieldBasicInfo(caption = "Field Name", type = "varchar(100)", primary = true)
	private String fieldName;
	
	@PWFieldBasicInfo(caption = "Description", type = "text")
	private String description;
	
	@PWFieldBasicInfo(caption = "Data Type", type = "varchar(100)")
	private String dataType;
	
	@PWFieldBasicInfo(caption = "Rederer Class Path", type = "varchar(100)")
	private String rendererClassPath;
	
	@PWFieldBasicInfo(caption = "Editor Class Path", type = "varchar(100)")
	private String editorClassPath;
	
	@PWFieldBasicInfo(caption = "Is Used", type = "bool")
	private boolean isUsed;
	
	@PWFieldBasicInfo(caption = "Primary Key Index", type = "int")
	private int primaryKeyIndex;
	
	@PWFieldBasicInfo(caption = "Unique Key Index", type = "int")
	private int uniqueKeyIndex;
	
	@PWFieldBasicInfo(caption = "Is Generated Automatically", type = "bool")
	private boolean isGeneratedAutomatically;
	
	@PWFieldBasicInfo(caption = "Index", type = "int")
	private int index;
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDataType() {
		return dataType;
	}
	
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	public String getRendererClassPath() {
		return rendererClassPath;
	}
	
	public void setRendererClassPath(String renderer) {
		this.rendererClassPath = renderer;
	}
	
	public String getEditorClassPath() {
		return editorClassPath;
	}
	
	public void setEditorClassPath(String editor) {
		this.editorClassPath = editor;
	}
	
	public boolean isUsed() {
		return isUsed;
	}
	
	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}
	
	public int getPrimaryKeyIndex() {
		return primaryKeyIndex;
	}
	
	public void setPrimaryKeyIndex(int primaryKeyIndex) {
		this.primaryKeyIndex = primaryKeyIndex;
	}
	
	public int getUniqueKeyIndex() {
		return uniqueKeyIndex;
	}
	
	public void setUniqueKeyIndex(int uniqueKeyIndex) {
		this.uniqueKeyIndex = uniqueKeyIndex;
	}
	
	public boolean isGeneratedAutomatically() {
		return isGeneratedAutomatically;
	}
	
	public void setGeneratedAutomatically(boolean isGeneratedAutomatically) {
		this.isGeneratedAutomatically = isGeneratedAutomatically;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}

	static {
		PWUtilities.prepareTable(PWFieldDefinition.class);
	}
}
