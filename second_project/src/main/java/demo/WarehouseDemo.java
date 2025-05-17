package demo;

import helper.WarehouseHelper;
import model.Warehouse;
import util.IdGenerator;

import java.util.Scanner;

public class WarehouseDemo {
    private final WarehouseHelper warehouseHelper;
    private final Scanner scanner;

    public WarehouseDemo(WarehouseHelper warehouseHelper) {
        this.warehouseHelper = warehouseHelper;
        this.scanner = new Scanner(System.in);
    }

    public void openWarehouse() {
        System.out.println("Открытие склада");
        System.out.print("Введите адрес склада: ");
        String location = scanner.nextLine();

        Warehouse warehouse = new Warehouse(IdGenerator.generateId(), location);
        warehouseHelper.addWarehouse(warehouse);
        System.out.println("Склад успешно открыт. ID: " + warehouse.getId());
    }

    public void closeWarehouse() {
        System.out.println("Закрытие склада");
        System.out.print("Введите ID склада для закрытия: ");
        int warehouseId = Integer.parseInt(scanner.nextLine());

        if (warehouseHelper.closeWarehouse(warehouseId)) {
            System.out.println("Склад успешно закрыт.");
        } else {
            System.out.println("Ошибка: склад не найден или уже закрыт.");
        }
    }

    public void moveProductBetweenWarehouses() {
        System.out.println("=== Перемещение товара между складами ===");
        System.out.print("Введите ID товара: ");
        int productId = Integer.parseInt(scanner.nextLine());

        System.out.print("Введите ID исходного склада: ");
        int fromWarehouseId = Integer.parseInt(scanner.nextLine());

        System.out.print("Введите ID целевого склада: ");
        int toWarehouseId = Integer.parseInt(scanner.nextLine());

        System.out.print("Введите количество: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        if (warehouseHelper.moveProduct(productId, fromWarehouseId, toWarehouseId, quantity)) {
            System.out.println("Товар успешно перемещен.");
        } else {
            System.out.println("Ошибка перемещения товара. Проверьте данные.");
        }
    }

    public void listActiveWarehouses() {
        System.out.println("Активные склады");
        warehouseHelper.getActiveWarehouses().forEach(warehouse ->
                System.out.println("ID: " + warehouse.getId() + ", Адрес: " + warehouse.getLocation()));
    }


}