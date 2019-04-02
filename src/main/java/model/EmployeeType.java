package model;

public enum EmployeeType {
	EMPLOYEE {
		public String toString() {
            return "Pracovník";
        }
	}
	,MANAGER
	{
		public String toString() {
            return "Vedoucí";
        }
	}
	, ADMIN {
		public String toString() {
            return "Administrátor";
        }
	},OWNER
	{
		public String toString() {
            return "Majitel";
        }
	};	
}
