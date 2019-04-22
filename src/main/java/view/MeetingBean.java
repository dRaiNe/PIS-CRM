package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;

import model.CarBrand;
import model.Employee;
import model.Meeting;
import model.EmployeeType;
import service.CarBrandManager;
import service.EmployeeManager;
import service.MeetingManager;


@ManagedBean
public class MeetingBean {	
	@PersistenceContext
    private EntityManager em;
	
	@Inject
	private MeetingManager meetingMgr;
	
	@Inject
	private EmployeeManager employeeManager;
	
	private Meeting meeting=new Meeting();
	
	private List<Meeting> tableDataList;

	private Meeting newMeeting=new Meeting();

	public Meeting getNewMeeting() {
		return newMeeting;
	}
	
	public Meeting getMeeting() {
		return meeting;
	}
	
	public Date date;
    
	public void setDate(Date d) {
		this.date = d;
	}
 
	
	public List<Meeting> getMeetings(CalendarViewBean calendar) {	
		tableDataList = meetingMgr.findById(employeeManager.getEmpl(), calendar.getDate());

		return tableDataList;
	}
	
	
	/// DB storage part
	public String saveNew() {
		meetingMgr.save(newMeeting);
		
		FacesContext fContext = FacesContext.getCurrentInstance();
		
		fContext.addMessage(null, new FacesMessage("Uloženo "));

    	ExternalContext extContext = fContext.getExternalContext();
    	extContext.getFlash().setKeepMessages(true);

		return "success";
	}
	
	public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
        	FacesContext context = FacesContext.getCurrentInstance();
        	Meeting e =context.getApplication().evaluateExpressionGet(context, "#{item}", Meeting.class);
        	 
        	meetingMgr.save(e);
        	
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Zmìny uloženy", "");//pokud bytoho bylo potøeba vypsat víc Pùvodní hodnota: " + oldValue + ", Nová hodnota:" + newValue
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
	
	public void save() {
		meetingMgr.save(meeting);
		
		FacesContext.getCurrentInstance().addMessage(null,
              new FacesMessage("Uloženo "));
	}
	
    public void delete(Meeting e) {
    	tableDataList.remove(e);
    	meetingMgr.remove(e);
        FacesMessage msg = new FacesMessage("Smazáno", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}