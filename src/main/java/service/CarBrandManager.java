package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.CarBrand;

@Stateless
public class CarBrandManager {
	@PersistenceContext
    private EntityManager em;

    public CarBrand find(Long id)
    {
    	return em.find(CarBrand.class, id);
    }
    
    public void save(CarBrand p)
    {
    		em.merge(p);
    }
	
    public void remove(CarBrand p)
    {
    	em.remove(em.merge(p));
    }
    
	public List <CarBrand> findAll()
    {
    	return em.createQuery("SELECT c FROM CarBrand c", CarBrand.class).getResultList();
    }
}
