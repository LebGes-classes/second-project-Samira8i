package helper;

import model.Employee;
import repository.EmployeeRepository;
import java.util.List;
import java.util.Optional;

public class EmployeeHelper {
    private final EmployeeRepository employeeRepo;

    public EmployeeHelper(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public void hireEmployee(Employee employee) {
        employeeRepo.addEmployee(employee);
        employeeRepo.saveData();
    }

    public boolean dismissEmployee(int employeeId) {
        boolean result = employeeRepo.dismissEmployee(employeeId);
        if (result) {
            employeeRepo.saveData();
        }
        return result;
    }

    public Optional<Employee> getEmployee(int id) {
        return employeeRepo.getEmployee(id);
    }

    public List<Employee> getActiveEmployees() {
        return employeeRepo.getActiveEmployees();
    }

    public List<Employee> getEmployeesByWorkplace(int workPlaceId) {
        return employeeRepo.getActiveEmployees().stream()
                .filter(emp -> emp.getWorkPlaceId() == workPlaceId)
                .toList();
    }
}