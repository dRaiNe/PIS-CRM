package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

import model.Employee;
import model.Meeting;

@Stateless
public class MeetingManager {
	@PersistenceContext
    private EntityManager em;

    public Meeting find(Long id)
    {
    	return em.find(Meeting.class, id);
    }
    
    public void save(Meeting p)
    {
    		em.merge(p);
    }
	
    public void remove(Meeting p)
    {
    	em.remove(em.merge(p));
    }
    
	public List <Meeting> findAll()
    {
    	return em.createQuery("SELECT c FROM Meeting c", Meeting.class).getResultList();
    }
	
	public List <Meeting> findById(Employee emp, Date date)
    {
		if (date == null)
			date = new Date();
		
		Date start = DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
		Date end = DateUtils.addDays(start, 1);
		 
    	 TypedQuery<Meeting> query = em.createQuery("SELECT c FROM Meeting c WHERE c.Employee = :id AND c.Date >= :start "
    	 		+ "AND c.Date <= :end", Meeting.class)
    			 .setParameter("id", emp).setParameter("start", start).setParameter("end", end);
    	 
    	 List<Meeting> list = query.getResultList();
    	 if (list == null || list.isEmpty()) {
    	    return null;
    	 }

    	 return list;    	    
    }
}
