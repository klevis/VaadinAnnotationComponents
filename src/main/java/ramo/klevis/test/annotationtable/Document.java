package ramo.klevis.test.annotationtable;

import ramo.klevis.vaadinaddon.annotations.ATable;

public class Document {

	private String id;
	private String content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	@Override
	public String toString() {
		return "Document [id=" + id + ", content=" + content + "]";
	}

	public void setContent(String content) {
		this.content = content;
	}

}
