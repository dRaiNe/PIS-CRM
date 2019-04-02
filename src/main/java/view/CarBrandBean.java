package view;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.CellEditEvent;

import model.CarBrand;
import model.Employee;
import service.CarBrandManager;

@ManagedBean(name="carBrandBean")
public class CarBrandBean {
	@Inject
	private CarBrandManager carBrandMgr;
	
	private List<CarBrand> tableDataList;
	
	public List <CarBrand> getCarBrands()
    {
    	//kvůli řazení musím načíst jen 1x
   	 if (tableDataList == null)
   	        tableDataList = carBrandMgr.findAll();
       return tableDataList;
    }
	
	
	public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
        	FacesContext context = FacesContext.getCurrentInstance();
        	CarBrand e =context.getApplication().evaluateExpressionGet(context, "#{item}", CarBrand.class);
        	 
        	carBrandMgr.save(e);
        	
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Změny uloženy", "");//pokud bytoho bylo potřeba vypsat víc Původní hodnota: " + oldValue + ", Nová hodnota:" + newValue
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
	
	
    public void onAddNew() {
        // Add one new car to the table:
        CarBrand e = new CarBrand();
        e.setName("Nová");
        carBrandMgr.save(e);
        tableDataList.add(e);
        
        FacesMessage msg = new FacesMessage("Nová značka přidána", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void delete(CarBrand e) {
    	tableDataList.remove(e);
    	carBrandMgr.remove(e);
        FacesMessage msg = new FacesMessage("Smazáno", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
