package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}
