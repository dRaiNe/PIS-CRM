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
	
//	@Inject
//	private CalendarViewBean calendarViewBean;
	
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
		// kvùli øazení musím naèíst jen 1x
		if (tableDataList == null) {
			tableDataList = meetingMgr.findById(employeeManager.getEmpl(), calendar.getDate());
//			if (MeetingManager.getEmpl().getType() == EmployeeType.OWNER) {
//				tableDataList = employeeMgr.findAll();
//
//			} else {
//				tableDataList = employeeMgr.findAll().stream()
//						.filter(e -> e.getType() == EmployeeType.MANAGER || e.getType() == EmployeeType.EMPLOYEE )
//						.collect(Collectors.toList());
//			}
		}
		return tableDataList;

	}
	
}