import demo.*;
import repository.*;
import helper.*;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        // Инициализация репозиториев и хелперов
        EmployeeRepository employeeRepo = new EmployeeRepository();
        ProductRepository productRepo = new ProductRepository();
        ShopRepository shopRepo = new ShopRepository(employeeRepo);
        WarehouseRepository warehouseRepo = new WarehouseRepository();

        EmployeeHelper employeeHelper = new EmployeeHelper(employeeRepo);
        ProductHelper productHelper = new ProductHelper(productRepo);
        ShopHelper shopHelper = new ShopHelper(shopRepo, employeeRepo, productHelper);
        WarehouseHelper warehouseHelper = new WarehouseHelper(warehouseRepo, productRepo);
        StatisticsHelper statisticsHelper = new StatisticsHelper(productRepo, shopRepo, warehouseRepo);

        // Инициализация демо-классов
        EmployeeDemo employeeDemo = new EmployeeDemo(employeeHelper, shopHelper);
        ProductDemo productDemo = new ProductDemo(productHelper, warehouseHelper, shopHelper);
        ShopDemo shopDemo = new ShopDemo(shopHelper);
        WarehouseDemo warehouseDemo = new WarehouseDemo(warehouseHelper);
        StatisticsDemo statisticsDemo = new StatisticsDemo(statisticsHelper);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nГЛАВНОЕ МЕНЮ");
            System.out.println("1. Управление сотрудниками");
            System.out.println("2. Управление товарами");
            System.out.println("3. Управление магазинами");
            System.out.println("4. Управление складами");
            System.out.println("5. Статистика");
            System.out.println("0. Выход");
            System.out.print("Выберите раздел: ");

            int mainChoice = Integer.parseInt(scanner.nextLine());

            switch (mainChoice) {
                case 1 -> showEmployeeMenu(employeeDemo, scanner);
                case 2 -> showProductMenu(productDemo, scanner);
                case 3 -> showShopMenu(shopDemo, scanner);
                case 4 -> showWarehouseMenu(warehouseDemo, scanner);
                case 5 -> statisticsDemo.showReportsMenu();
                case 0 -> {
                    System.out.println("Выход из программы.");
                    System.exit(0);
                }
                default -> System.out.println("Неверный выбор!");
            }
        }
    }

    private static void showEmployeeMenu(EmployeeDemo demo, Scanner scanner) {
        while (true) {
            System.out.println("\nУПРАВЛЕНИЕ СОТРУДНИКАМИ");
            System.out.println("1. Нанять сотрудника");
            System.out.println("2. Уволить сотрудника");
            System.out.println("3. Список активных сотрудников");
            System.out.println("4. Назначить менеджера магазина");
            System.out.println("0. Назад");
            System.out.print("Выберите действие: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> demo.hireEmployee();
                case 2 -> demo.dismissEmployee();
                case 3 -> demo.listActiveEmployees();
                case 4 -> demo.assignManager();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Неверный выбор!");
            }
        }
    }

    private static void showProductMenu(ProductDemo demo, Scanner scanner) {
        while (true) {
            System.out.println("\nУПРАВЛЕНИЕ ТОВАРАМИ");
            System.out.println("1. Добавить товар");
            System.out.println("2. Продать товар");
            System.out.println("3. Возврат товара");  // Новый пункт меню
            System.out.println("0. Назад");
            System.out.print("Выберите действие: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> demo.addProduct();
                case 2 -> demo.sellProduct();
                case 3 -> demo.returnProduct();  // Новый метод
                case 0 -> {
                    return;
                }
                default -> System.out.println("Неверный выбор!");
            }
        }
    }

    private static void showShopMenu(ShopDemo demo, Scanner scanner) {
        while (true) {
            System.out.println("\nУПРАВЛЕНИЕ МАГАЗИНАМИ");
            System.out.println("1. Открыть магазин");
            System.out.println("2. Закрыть магазин");
            System.out.println("0. Назад");
            System.out.print("Выберите действие: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> demo.openShop();
                case 2 -> demo.closeShop();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Неверный выбор!");
            }
        }
    }

    private static void showWarehouseMenu(WarehouseDemo demo, Scanner scanner) {
        while (true) {
            System.out.println("\nУПРАВЛЕНИЕ СКЛАДАМИ");
            System.out.println("1. Открыть склад");
            System.out.println("2. Закрыть склад");
            System.out.println("3. Переместить товар между складами");
            System.out.println("4. Список активных складов");
            System.out.println("0. Назад");
            System.out.print("Выберите действие: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> demo.openWarehouse();
                case 2 -> demo.closeWarehouse();
                case 3 -> demo.moveProductBetweenWarehouses();
                case 4 -> demo.listActiveWarehouses();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Неверный выбор!");
            }
        }
    }
}