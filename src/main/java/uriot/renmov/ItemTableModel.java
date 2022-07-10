package uriot.renmov;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.AbstractTableModel;

public class ItemTableModel extends AbstractTableModel {

	private final ArrayList<Item> items;
	public int dir1Length;
	public int dir2Length;

	public ItemTableModel(ArrayList<Item> items) {
		this.items = items;
	}

	@Override
	public int getRowCount() {
		return items.size();
	}

	@Override
	public int getColumnCount() {
		return Item.columnNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return Item.columnNames[column];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return Item.columnClasses[columnIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex==0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		var item = items.get(rowIndex);
		return switch (columnIndex) {
			case 0 -> item.select;
			case 1 -> item.path1.substring(dir1Length);
			case 2 -> item.path2.substring(dir2Length);
			case 3 -> formatter.format(Date.from(item.criteria.date));
			case 4 -> item.criteria.size;
			case 5 -> item.action;
			default -> null;
		};
	}
	private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy/dd/MM HH:mm:ss");  

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		var item = items.get(rowIndex);
		switch (columnIndex) {
			case 0 -> item.select = (Boolean) value;
		}
	}

}
