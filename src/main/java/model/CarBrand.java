package model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.JoinTable;

@Entity
public class CarBrand {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
	private long id;
	
	private String Name;
	

	@ManyToMany(fetch = EAGER)
	@JoinTable(name = "EMPLOYEE_CARBRAND")
    Set<Employee> Specialists;


	public String getName() {
		return Name;
	}


	public void setName(String name) {
		Name = name;
	}


	public Set<Employee> getSpecialists() {
		return Specialists;
	}


	public void setSpecialists(Set<Employee> specialists) {
		Specialists = specialists;
	}


	public long getId() {
		return id;
	}
	
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarBrand)) return false;
        CarBrand o2 = (CarBrand) o;
        return Objects.equals(getId(), o2.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
    
    @Override
    public String toString() {
        return String.format("%s[id=%d]", getClass().getSimpleName(), getId());
    }

}
