package view;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import model.Customer;
import model.Employee;
import service.EmployeeManager;
 
@ManagedBean @SessionScoped
public class LoginBean {
	@Inject
	private EmployeeManager employeeMgr;
	
    private String Login;
    private String password;
 
    private Customer loggedAs;
    
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
 
    public String validateUserLogin() {
        String navResult = "";
        System.out.println("Entered Login is= " + Login + ", password is= " + password);
        
        Employee emp=employeeMgr.findByLogin(Login);
        
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
}