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
    private Date date2;
 
    @PostConstruct
    public void init() {
    }
 
 
    public Date getDate() {
        return date;
    }
 
    public void setDate(Date date) {
        this.date = date;
    }
   
    public Date getDate2() {
        return date2;
    }
 
    public void setDate2(Date date) {
        this.date2 = date;
    }
}