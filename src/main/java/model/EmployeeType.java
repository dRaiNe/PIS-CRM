package model;

public enum EmployeeType {
	EMPLOYEE {
		public String toString() {
            return "Pracovn�k";
        }
	}
	,MANAGER
	{
		public String toString() {
            return "Vedouc�";
        }
	}
	, ADMIN {
		public String toString() {
            return "Administr�tor";
        }
	},OWNER
	{
		public String toString() {
            return "Majitel";
        }
	};	
}
