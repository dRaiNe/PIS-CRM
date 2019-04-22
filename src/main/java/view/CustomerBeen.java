package view;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.primefaces.event.CellEditEvent;

import model.CarBrand;
import model.Customer;
import model.Employee;
import model.EmployeeType;
import service.CarBrandManager;
import service.CustomerManager;
import service.EmployeeManager;

@ManagedBean(name = "customerBean")
public class CustomerBeen {
	@PersistenceContext
	private EntityManager em;

	@Inject
	private CustomerManager customerMgr;

	@Inject
	private CarBrandManager carBrandMgr;

	@Inject
	private EmployeeManager employeeMgr;

	private List<Customer> tableDataList;
	private List<CarBrand> carBrandList;

	private boolean enableAdd;

	public boolean getEnableAdd() {
		return enableAdd;
	}

	public void setEnableAdd(boolean enableAdd) {
		this.enableAdd = enableAdd;
	}

	public CustomerManager getCustomerMgr() {
		return this.customerMgr;
	}
	
	
	public List<Customer> getCustomers() {
		// kvůli řazení musím načíst jen 1x
		if (EmployeeManager.getEmpl().getType() == EmployeeType.EMPLOYEE) {
			tableDataList = customerMgr.findAll().stream().filter(
					e -> e.getAssociatedEmpoyee() != null && e.getAssociatedEmpoyee().equals(EmployeeManager.getEmpl()))
					.collect(Collectors.toList());

		} else if (EmployeeManager.getEmpl().getType() == EmployeeType.MANAGER
				|| EmployeeManager.getEmpl().getType() == EmployeeType.ADMIN) {
			tableDataList = customerMgr.findAll().stream()
					.filter(e -> e.getAssociatedEmpoyee() == null
							|| e.getAssociatedEmpoyee().equals(EmployeeManager.getEmpl())
							|| EmployeeManager.getEmpl().getSpecialistToBrands().contains(e.getCarBrand()))
					.collect(Collectors.toList());
		} else {

			tableDataList = customerMgr.findAll();

		}

		if (EmployeeManager.getEmpl().getType() != EmployeeType.EMPLOYEE) {
			enableAdd = true;
		} else {
			enableAdd = false;
		}

		return tableDataList;
	}

	public List<CarBrand> getCarBrands() {
		// kvůli řazení musím načíst jen 1x
		if (carBrandList == null)
			carBrandList = carBrandMgr.findAll();
		return carBrandList;
	}

	public List<Employee> getEmployees(int CarBrandId) {
		return employeeMgr.findSpecialists(CarBrandId);
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		FacesContext context = FacesContext.getCurrentInstance();
		Customer e = context.getApplication().evaluateExpressionGet(context, "#{item}", Customer.class);

		customerMgr.save(e);

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Změny uloženy", "");// pokud bytoho bylo
																								// potřeba vypsat víc
																								// Původní hodnota: " +
																								// oldValue + ", Nová
																								// hodnota:" + newValue
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onAddNew() {
		// Add one new car to the table:
		Customer e = new Customer();
		e.setName("Nový zákazník");
		customerMgr.save(e);
		tableDataList.add(e);

		FacesMessage msg = new FacesMessage("Nová značka přidána", "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void delete(Customer e) {
		tableDataList.remove(e);
		customerMgr.remove(e);
		FacesMessage msg = new FacesMessage("Smazáno", "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void detail(Customer e) {
		FacesContext fContext = FacesContext.getCurrentInstance();
		ExternalContext extContext = fContext.getExternalContext();
		try {
			extContext.redirect("CustomerDetail.xhtml?id=" + e.getId());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
