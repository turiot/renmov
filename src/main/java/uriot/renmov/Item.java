package uriot.renmov;

import java.time.Instant;

public class Item {
	Criteria criteria;
	boolean  select;
	String   path1;
	String   path2;
	String   action;

	public Item(Criteria criteria) {
		this.criteria = criteria;
		select = true;
	}
	
	public static final String[] columnNames = {
		"Sél", "Fichier 1", "Fichier 2", "Date", "Taille", "Action" // i18n override
	};
	
	public static final Class[] columnClasses = {
		Boolean.class, String.class, String.class, Instant.class, Long.class, String.class
	};

	@Override
	public String toString() {
		return "Item{" + "criteria=" + criteria + ", select=" + select + ", path1=" + path1 + ", path2=" + path2 + ", action=" + action + '}';
	}
}
