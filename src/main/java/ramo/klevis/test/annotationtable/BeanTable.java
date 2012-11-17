package ramo.klevis.test.annotationtable;

import ramo.klevis.test.bindingform.Bean;
import ramo.klevis.vaadinaddon.annotations.ToTable;

public class BeanTable extends Bean {

	@ToTable(columnName = "Father name")
	private String father;

	@ToTable(columnName = "Document Id", nestedvalue = "document.id")
	private Document document;

	public String getFather() {
		return father;
	}

	public void setFather(String father) {
		this.father = father;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
}
