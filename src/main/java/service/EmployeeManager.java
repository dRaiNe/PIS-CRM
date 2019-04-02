package service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.Employee;
import model.EmployeeType;

@Stateless
public class EmployeeManager {
	 @PersistenceContext
	    private EntityManager em;

	    public Employee find(Long id)
	    {
	    	return em.find(Employee.class, id);
	    }
	    
	    public Employee findByLogin(String login) //v případě neúspěchu vrací null
	    {
	    	 TypedQuery<Employee> query = em.createQuery("SELECT c FROM Employee c WHERE c.Login = :login", Employee.class).setParameter("login", login).setMaxResults(1);
	    	 
	    	 List<Employee> list = query.getResultList();
	    	 if (list == null || list.isEmpty()) {
	    	    return null;
	    	 }

	    	 return list.get(0);	    	    
	    }
	    
	    public void save(Employee p)
	    {
	    		em.merge(p);
	    }
		
	    public void remove(Employee p)
	    {
	    	em.remove(em.merge(p));
	    }
	    
		public List <Employee> findAll()
	    {
	    	return em.createQuery("SELECT c FROM Employee c", Employee.class).getResultList();
	    }
		
		public List <Employee> findSpecialists(int CarBrandId)
	    {
	    	return em.createQuery("SELECT c FROM Employee c JOIN c.SpecialistToBrands b WHERE b.id="+CarBrandId, Employee.class).getResultList();
	    }
}
