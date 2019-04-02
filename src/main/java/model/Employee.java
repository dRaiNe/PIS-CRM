package model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(schema = "pisproject")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
	private long id;
	

	@Column(name = "LOGIN", nullable = false, unique=true)
	private String Login;

	
	private String Name;
	private String Surname;
	private String Degree;
	private String PaswordHash;
	
	@OneToMany(fetch=EAGER)
	@JoinColumn(name = "ASSOCIATEDEMPOYEE_id")
	private List<Customer> Customers;
	
	@Enumerated(EnumType.ORDINAL)
	private EmployeeType Type= EmployeeType.EMPLOYEE;
	

	@ManyToMany(fetch = EAGER)
	@JoinTable(name = "EMPLOYEE_CARBRAND", 
	        joinColumns = @JoinColumn(name = "CARBRAND_id"),
	        inverseJoinColumns = @JoinColumn(name = "SPECIALISTS_id"))
	List<CarBrand> SpecialistToBrands;
	
	public long getId() {
		return id;
	}
	
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getLogin() {
		return Login;
	}

	public void setLogin(String login) {
		Login = login;
	}

	public String getSurname() {
		return Surname;
	}
	public EmployeeType getType() {
		return Type;
	}
	
	public String getTypeString() {
		return Type.toString();
	}

	public void setType(EmployeeType type) {
		Type = type;
	}

	public List<CarBrand> getSpecialistToBrands() {
		return SpecialistToBrands;
	}
	
	public String getSpecialistToBrandsString() {
	    String ret="";
		
		for (CarBrand brand : SpecialistToBrands) 
		{ 
			if(ret!="") 
				ret+=", ";
			ret+=brand.getName();
		}
		
		return ret;
	}

	public void setSpecialistToBrands(List<CarBrand> specialistToBrands) {
		SpecialistToBrands = specialistToBrands;
	}

	public void setDegree(String degree) {
		Degree = degree;
	}

	public void setSurname(String surname) {
		Surname = surname;
	}
	public String getDegree() {
		return Degree;
	}
	public void setTitle(String degree) {
		Degree = degree;
	}
	public String getPaswordHash() {
		return PaswordHash;
	}
	
	public String setPaswordHash() {
		return PaswordHash;
	}
	
	public String getPassword() 
	{
		return "";
	}
	
	public String generatePassword(String password) {
		String generatedPassword = null;
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			
			md.update(password.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
		}
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
		return generatedPassword;
	}
	
	public void setPassword(String password) {
		System.out.println("setting password hash = " + generatePassword(password) + ", from password= " + password);
			PaswordHash = generatePassword(password);
	}
	
	public Boolean checkPassword(String password) {
		System.out.println("password hash = " + generatePassword(password) + ", hash in DB= " + PaswordHash+", res="+ (PaswordHash.contentEquals(generatePassword(password))?"TRUE":"FALSE") );
		return PaswordHash.equals(generatePassword(password));
    }

	public List<Customer> getCustomers() {
		return Customers;
	}

	public void setCustomers(List<Customer> customers) {
		Customers = customers;
	}
	
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee o2 = (Employee) o;
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
