package pw.ui.swing.table.editor;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import javax.swing.table.TableCellEditor;

import pw.ui.swing.table.PWTableViewPanel;
import pw.ui.swing.table.PWTableViewRowState;

public class PWTextFieldCellEditor extends AbstractCellEditor implements
		TableCellEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1792508804476914282L;
	
	protected int rowIndex;
	protected JTextField textField;
	
	private void changeState(final PWTableViewPanel tableView) {
		if (rowIndex < 0) {
			return;
		}
		PWTableViewRowState state = tableView.getRowState(rowIndex);
		if (state != PWTableViewRowState.Added)
			tableView.setRowState(rowIndex, PWTableViewRowState.Modified);
	}

	public PWTextFieldCellEditor(final PWTableViewPanel tableView) {
		rowIndex = -1;
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
		// TODO Auto-generated method stub
		return textField.getText();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		rowIndex = row;
		textField.setText((String)value);
		return textField;
	}

}
