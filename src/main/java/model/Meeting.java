package model;

import java.util.Date;
import java.util.Objects;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Basic;
import static javax.persistence.FetchType.EAGER;

@Entity
public class Meeting {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
	private long id;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Basic(fetch = EAGER)
	private Date Date;
	
	@Column(columnDefinition="text")
    private String Report;
	

	@ManyToOne(fetch=EAGER)
	private Customer Customer;
	
	@ManyToOne(fetch=EAGER)
	private Employee Employee;

	
	public long getId() {
		return id;
	}

	public Date getDate() {
		return Date;
	}
	public void setDate(Date date) {
		Date = date;
	}
	public String getReport() {
		return Report;
	}
	public void setReport(String report) {
		Report = report;
	}


	public Customer getCustomer() {
		return Customer;
	}

	public void setCustomer(Customer customer) {
		Customer = customer;
	}

	public Employee getEmployee() {
		return Employee;
	}

	public void setEmployee(Employee employee) {
		Employee = employee;
	}

	
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meeting)) return false;
        Meeting o2 = (Meeting) o;
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
