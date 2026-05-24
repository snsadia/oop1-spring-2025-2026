class Position {
    private String positionName;

    public Position(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionName() {
        return positionName;
    }
}

class Employee {
    private String EmployeeName;
    private double salary;
    private Position position;

    public Employee(String EmployeeName, double salary, Position position) {
        this.EmployeeName = EmployeeName;
        this.salary = salary;
        this.position = position;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public double getSalary() {
        return salary;
    }

    public Position getPosition() {
        return position;
    }
}

class Company {
    private String companyName;
    private Employee[] employees;

    private int count = 0;

    public Company(String CompanyName, int numberOfEmployees) {
        this.companyName = CompanyName;
        this.employees = new Employee[numberOfEmployees];
    }

    public void addEmployee(Employee employee) {
        if (count < employees.length) {
            employees[count] = employee;
            count++;
        } else {
            System.out.println("Cannot add more employees. Maximum limit reached.");
        }
    }

    public void displayEmployeeDetails(Employee Employee) {
        System.out.println("Employee Name: " + Employee.getEmployeeName());
        System.out.println("Position: " + Employee.getPosition().getPositionName());
        System.out.println("Salary: " + Employee.getSalary());
    }

    public void displayCompanyDetails() {
        System.out.println("Company Name: " + companyName);
        System.out.println("Employees:");
        for (int i = 0; i < count; i++) {
            displayEmployeeDetails(employees[i]);
            System.out.println();
        }

    }
}

public class AssociationTask {
    public static void main(String[] args) {
        Position manager = new Position("Manager");
        Position developer = new Position("Developer");

        Employee emp1 = new Employee("Sadia", 500000, manager);
        Employee emp2 = new Employee("Maria", 400000, developer);

        Company company = new Company("Atlassian", 5);
        company.addEmployee(emp1);
        company.addEmployee(emp2);

        company.displayCompanyDetails();
    }
}