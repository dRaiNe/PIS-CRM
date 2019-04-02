package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Customer;

@Stateless
public class CustomerManager {
	@PersistenceContext
    private EntityManager em;

    public Customer find(Long  id)
    {
    	return em.find(Customer.class, id);
    }
    
    public void save(Customer p)
    {
    		em.merge(p);
    }
	
    public void remove(Customer p)
    {
    	em.remove(em.merge(p));
    }
    
	public List <Customer> findAll()
    {
    	return em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
    }
}
