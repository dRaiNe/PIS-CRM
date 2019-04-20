package view;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.omnifaces.cdi.Param;

import model.Employee;
import service.EmployeeManager;

@ManagedBean(name="employeeDetailBean")
@RequestScoped
public class EmployeeDetailBean {

	@Inject
	private EmployeeManager employeeMgr;
	
	private Long id;
	private Employee employee;
	
	
	private void send404()
	{
	     FacesContext facesContext = FacesContext.getCurrentInstance();
	     try {
			facesContext.getExternalContext().responseSendError(404, "Not Found");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}                
	     
	     facesContext.responseComplete();		
	}
	
	@PostConstruct
	public void init() {
	     Map<String, String> paramMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	     Long id = Long.valueOf(paramMap.get("id"));
		
	     if(id>0)
	     {
	 		employee = employeeMgr.find(id);
	   	 	if(employee == null) send404();
	     } else send404();
	}
	

	
	public void save() {
		employeeMgr.save(employee);
		
		FacesContext fContext = FacesContext.getCurrentInstance();
		
		fContext.addMessage(null, new FacesMessage("Uloženo "));
	}
	
	
	public Employee getEmployee() {	
		return employee;
	}

	public void setEmployee(Employee Employee) {
		this.employee = Employee;
	}	
}
