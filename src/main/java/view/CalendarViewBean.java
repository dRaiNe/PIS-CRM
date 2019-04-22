package view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.apache.commons.fileupload.RequestContext;
//import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

@ManagedBean
public class CalendarViewBean {
 
    private Date date;
 
    @PostConstruct
    public void init() {
    }
 
//    public void onDateSelect(SelectEvent event) {
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
//    }
// 
//    public void click() {
//    	RequestContext.current().ajax().update("form:display");
//        PrimeFaces.current().executeScript("PF('dlg').show()");
//    }
 
    public Date getDate() {
        return date;
    }
 
    public void setDate(Date date) {
        this.date = date;
    }
   
}