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

import model.Customer;
import service.CustomerManager;

@ManagedBean(name="customerDetailBean")
@RequestScoped
public class CustomerDetailBean {
	private Long id;
	private Customer customer;

	@Inject
	private CustomerManager customerMgr;
	
	
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
	 		customer = customerMgr.find(id);
	   	 	if(customer == null) send404();
	     } else send404();
	}

	
	public void save() {
		customerMgr.save(customer);
		
		FacesContext fContext = FacesContext.getCurrentInstance();
		
		fContext.addMessage(null, new FacesMessage("Uloženo "));
	}
	
	
	public Customer getCustomer() {	
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}	
}
