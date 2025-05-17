package repository;

import model.Employee;
import java.io.IOException;
import java.util.*;

public class EmployeeRepository {
    private static final String FILE_NAME = "employees.txt";
    private final Map<Integer, Employee> employeesMap = new HashMap<>();

    //создаем map id+employee
    public EmployeeRepository() {
        loadEmployees().forEach(emp -> employeesMap.put(emp.getId(), emp));
    }
    //загрузка
    private List<Employee> loadEmployees() {
        List<Employee> employees = new ArrayList<>();
        try {
            String data = FileDataManager.load(FILE_NAME);
            if (data == null || data.isEmpty()) return employees;

            Scanner scanner = new Scanner(data);
            while (scanner.hasNextLine()) {
                Employee emp = parseEmployee(scanner.nextLine());
                if (emp != null) employees.add(emp);
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println("Ошибка загрузки: " + e.getMessage());
        }
        return employees;
    }

    private Employee parseEmployee(String line) {
        try {
            String[] parts = line.split("\\|"); // Используем | как разделитель
            if (parts.length != 7) return null;

            Employee emp = new Employee(
                    Integer.parseInt(parts[0].trim()),
                    parts[1].trim(),
                    parts[2].trim(),
                    parts[3].trim(),
                    parts[4].trim(),
                    Integer.parseInt(parts[5].trim())
            );
            if (parts[6].trim().equals("0")) emp.dismiss();
            return emp;
        } catch (Exception e) {
            System.err.println("Ошибка парсинга: " + line);
            return null;
        }
    }

    public void saveData() {
        try {
            StringBuilder sb = new StringBuilder();
            for (Employee emp : employeesMap.values()) {
                sb.append(employeeToString(emp)).append("\n");
            }
            FileDataManager.save(FILE_NAME, sb.toString());
        } catch (IOException e) {
            System.err.println("Ошибка сохранения: " + e.getMessage());
        }
    }

    private String employeeToString(Employee emp) {
        return String.join("|",
                String.valueOf(emp.getId()),
                emp.getFirstName(),
                emp.getLastName(),
                emp.getPhoneNumber(),
                emp.getPosition(),
                String.valueOf(emp.getWorkPlaceId()),
                emp.isActive() ? "1" : "0" // 1 - активен, 0 - уволен
        );
    }

    public void addEmployee(Employee employee) {
        employeesMap.put(employee.getId(), employee);
    }

    public Optional<Employee> getEmployee(int id) {
        return Optional.ofNullable(employeesMap.get(id));
    }

    public List<Employee> getActiveEmployees() {
        List<Employee> active = new ArrayList<>();
        for (Employee emp : employeesMap.values()) {
            if (emp.isActive()) active.add(emp);
        }
        return active;
    }

    public boolean dismissEmployee(int id) {
        Employee emp = employeesMap.get(id);
        if (emp != null) {
            emp.dismiss();
            return true;
        }
        return false;
    }
}