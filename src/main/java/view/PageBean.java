package view;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class PageBean {
	private int activeTab=1;

	public int getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(int activeTab) {
		this.activeTab = activeTab;
	}
	
	
}
