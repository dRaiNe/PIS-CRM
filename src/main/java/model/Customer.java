package model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

import java.util.Objects;

import javax.persistence.Column;

@Entity
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
	private long id;
	
	private String Name;
	private String Surname;
	private String Degree;
	
	@ManyToOne(fetch=EAGER)
	private CarBrand CarBrand;
	
	@Column(columnDefinition="text")
	private String Info;
	
	@ManyToOne(fetch=EAGER)
	private Employee AssociatedEmpoyee;
	
	public long getId() {
		return id;
	}

	
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
	public String getSurname() {
		return Surname;
	}
	public void setSurname(String surname) {
		Surname = surname;
	}
	public String getDegree() {
		return Degree;
	}
	public void setDegree(String degree) {
		Degree = degree;
	}


	public CarBrand getCarBrand() {
		return CarBrand;
	}


	public void setCarBrand(CarBrand carBrand) {
		CarBrand = carBrand;
	}


	public String getInfo() {
		return Info;
	}


	public void setInfo(String info) {
		Info = info;
	}


	public Employee getAssociatedEmpoyee() {
		return AssociatedEmpoyee;
	}


	public void setAssociatedEmpoyee(Employee associatedEmpoyee) {
		AssociatedEmpoyee = associatedEmpoyee;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer o2 = (Customer) o;
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
