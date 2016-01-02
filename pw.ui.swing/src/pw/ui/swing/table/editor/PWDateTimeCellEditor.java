package pw.ui.swing.table.editor;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import javax.swing.table.TableCellEditor;

import pw.ui.swing.table.PWTableViewPanel;
import pw.ui.swing.table.PWTableViewRowState;

public class PWDateTimeCellEditor extends AbstractCellEditor implements
		TableCellEditor {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4115139993664181989L;
	protected int rowIndex;
	protected String format;
	protected JTextField textField;
	
	private void changeState(final PWTableViewPanel tableView) {
		if (rowIndex < 0) {
			return;
		}
		PWTableViewRowState state = tableView.getRowState(rowIndex);
		if (state != PWTableViewRowState.Added)
			tableView.setRowState(rowIndex, PWTableViewRowState.Modified);
	}

	public PWDateTimeCellEditor(final PWTableViewPanel tableView, String format) {
		rowIndex = -1;
		this.format = format;
		textField = new JTextField();
		textField.getDocument().addDocumentListener(new DocumentListener() {
		     public void changedUpdate(DocumentEvent e) { changeState(tableView); }
		     public void insertUpdate(DocumentEvent e) { changeState(tableView);}
		     public void removeUpdate(DocumentEvent e) { changeState(tableView); }
		});
	}
	
	public boolean isCellEditable(EventObject event) {
		if (!(event instanceof MouseEvent)) {
			return false;
		}
		if (((MouseEvent)event).getClickCount() == 1) {
			return false;
		}
		return true;
	}

	@Override
	public Object getCellEditorValue() {
		Date dateTime = null;
		try {
			dateTime = DateFormat.getDateInstance().parse(textField.getText());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateTime;
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		rowIndex = row;
		String date = "";
		if (value != null)
			date = new SimpleDateFormat(format).format((Date)value);
		textField.setText(date);
		return textField;
	}

}
