package demo;

import model.Employee;
import helper.EmployeeHelper;
import helper.ShopHelper;
import util.IdGenerator;

import java.util.List;
import java.util.Scanner;

public class EmployeeDemo {
    private final EmployeeHelper employeeHelper;
    private final Scanner scanner;
    private final ShopHelper shopHelper;

    public EmployeeDemo(EmployeeHelper employeeHelper, ShopHelper shopHelper) {
        this.employeeHelper = employeeHelper;
        this.shopHelper = shopHelper;
        this.scanner = new Scanner(System.in);
    }

    public void hireEmployee() {
        System.out.println("Наем сотрудника");
        System.out.print("Имя: ");
        String firstName = scanner.nextLine();
        System.out.print("Фамилия: ");
        String lastName = scanner.nextLine();
        System.out.print("Телефон: ");
        String phone = scanner.nextLine();
        System.out.print("Должность: ");
        String position = scanner.nextLine();
        System.out.print("ID рабочего места: ");
        int workplaceId = Integer.parseInt(scanner.nextLine());

        Employee employee = new Employee(
                IdGenerator.generateId(), // Генерация ID
                firstName,
                lastName,
                phone,
                position,
                workplaceId
        );
        employeeHelper.hireEmployee(employee);
        System.out.println("Сотрудник нанят: " + employee);
    }

    public void dismissEmployee() {
        System.out.print("Введите ID сотрудника для увольнения: ");
        int id = Integer.parseInt(scanner.nextLine());
        if (employeeHelper.dismissEmployee(id)) {
            System.out.println("Сотрудник уволен.");
        } else {
            System.out.println("Ошибка: сотрудник не найден.");
        }
    }

    public void listActiveEmployees() {
        List<Employee> employees = employeeHelper.getActiveEmployees();
        System.out.println("Активные сотрудники");
        employees.forEach(System.out::println);
    }

    public void assignManager() {
        System.out.println("\nНазначение менеджера магазина");
        System.out.print("Введите ID сотрудника: ");
        int employeeId = Integer.parseInt(scanner.nextLine());
        System.out.print("Введите ID магазина: ");
        int shopId = Integer.parseInt(scanner.nextLine());

        boolean success = shopHelper.assignManager(shopId, employeeId);
        if (success) {
            System.out.println("Менеджер успешно назначен!");
        } else {
            System.out.println("Ошибка назначения менеджера. Проверьте ID.");
        }
    }
}