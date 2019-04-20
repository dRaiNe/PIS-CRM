package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import model.EmployeeType;
import service.CarBrandManager;
import service.EmployeeManager;

@ManagedBean
public class EmployeesBean {	
	@PersistenceContext
    private EntityManager em;
	
	@Inject
	private EmployeeManager employeeMgr;
	
	@Inject
	private CarBrandManager carBrandMgr;
	
	private Employee employee=new Employee();
	private List<Employee> tableDataList;

	private Employee newEmployee=new Employee();

	public Employee getNewEmployee() {
		return newEmployee;
	}
	
	
	public String saveNew() {
		
		if(employeeMgr.findByLogin(newEmployee.getLogin())!=null) //zkontroluju login
		{
    		FacesContext fContext = FacesContext.getCurrentInstance();
    		
    		fContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Uživatel s tímto přihlašovacím jménem již existuje",null));
			return "";
		}
		else
		{
		employeeMgr.save(newEmployee);
		
		FacesContext fContext = FacesContext.getCurrentInstance();
		
		fContext.addMessage(null, new FacesMessage("Uloženo "));

    	ExternalContext extContext = fContext.getExternalContext();
    	extContext.getFlash().setKeepMessages(true);
    	/*
    	try {
			extContext.redirect("Employees.xhtml");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		*/
		return "success";
		}
	}
	
	
    public List<Employee> getEmployees() {
    	//kvůli řazení musím načíst jen 1x
    	 if (tableDataList == null)
    	        tableDataList = employeeMgr.findAll();
        return tableDataList;
    }
	
	public Employee getEmployee() {
		return employee;
	}
	
	public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
        	FacesContext context = FacesContext.getCurrentInstance();
        	Employee e =context.getApplication().evaluateExpressionGet(context, "#{item}", Employee.class);
        	 
        	employeeMgr.save(e);
        	
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Změny uloženy", "");//pokud bytoho bylo potřeba vypsat víc Původní hodnota: " + oldValue + ", Nová hodnota:" + newValue
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
	
	public void save() {
		employeeMgr.save(employee);
		
		FacesContext.getCurrentInstance().addMessage(null,
              new FacesMessage("Uloženo "));
		
	}
	
	public List <EmployeeType> getTypes()
    {
		ArrayList<EmployeeType> types = new ArrayList<EmployeeType>();
		types.add(EmployeeType.EMPLOYEE);
		types.add(EmployeeType.MANAGER);
		types.add(EmployeeType.ADMIN);
		types.add(EmployeeType.OWNER);
		return types;
    }
	
	public List <CarBrand> getCarBrands()
    {
		return carBrandMgr.findAll();
    }
	
    public void delete(Employee e) {
    	tableDataList.remove(e);
    	employeeMgr.remove(e);
        FacesMessage msg = new FacesMessage("Smazáno", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
