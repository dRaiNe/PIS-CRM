package view;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Customer;
import model.Employee;
import model.EmployeeType;
import service.EmployeeManager;
 
@ManagedBean @SessionScoped
public class LoginBean {
	
	@Inject
	private EmployeeManager employeeMgr;
	
	@PersistenceContext
    private EntityManager em;
	
	private Employee emp;
    
    private EmployeeType type;
	
    private String Login;
    private String password;
 
    private Customer loggedAs;
    
    
    private String fullName;
    
    private boolean managment;
    
    public boolean getManagment() {
		return managment;
	}

	public void setManagment(boolean isManagment) {
		this.managment = isManagment;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
    
	public String getFullName() {
		return emp.getName() + " " + emp.getSurname();
	}

    
    public EmployeeType getType() {
		return type;
	}

	public void setType(EmployeeType type) {
		this.type = type;
	}

	public String getLogin() {
        return Login;
    }
 
    public void setLogin(String Login) {
        this.Login = Login;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
	public Employee getEmp() {
		return emp;
	}

	public void setEmp(Employee emp) {
		this.emp = emp;
	}
 
    public String validateUserLogin() {
        String navResult = "";
        System.out.println("Entered Login is= " + Login + ", password is= " + password);
        
        emp = employeeMgr.findByLogin(Login);
        EmployeeManager.setEmpl(emp);
        fullName = getFullName();
        
        if( emp.getType() == EmployeeType.ADMIN || emp.getType() == EmployeeType.OWNER ) {
        	setManagment(true);
        } else {
        	setManagment(false);
        }
        
        if (emp!=null && emp.checkPassword(password)) {
        	 loggedAs = new Customer();
             navResult = "success";          
        } else {
    		FacesContext fContext = FacesContext.getCurrentInstance();
    		fContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Špatné uživatelské jméno nebo heslo",null));
        }
        
        return navResult;
    }
    
    public String logout() {
    	loggedAs = null;
    	return "logout";   
    }

	public Customer getLoggedAs() {
		return loggedAs;
	}
	
    public void userDetail() {
    	FacesContext fContext = FacesContext.getCurrentInstance();
    	ExternalContext extContext = fContext.getExternalContext();
    	try {
			extContext.redirect("EmployeeDetail.xhtml?id="+emp.getId());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
	
}