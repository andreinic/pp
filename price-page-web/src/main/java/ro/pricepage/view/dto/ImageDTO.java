package ro.pricepage.view.dto;

import java.io.Serializable;

public class ImageDTO implements Serializable {
	private static final long serialVersionUID = -4953016766963301989L;
	
	private String path;
	private Boolean head;
	
	public ImageDTO(String path, Boolean head) {
		this.path = path;
		this.head = head;
	}
	
	public Boolean getHead() {
		return head;
	}
	public void setHead(Boolean head) {
		this.head = head;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
