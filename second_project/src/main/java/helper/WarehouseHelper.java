package helper;

import model.Product;
import model.Warehouse;
import repository.ProductRepository;
import repository.WarehouseRepository;
import java.util.List;

public class WarehouseHelper {
    private final WarehouseRepository warehouseRepo;
    private final ProductRepository productRepo;

    public WarehouseHelper(WarehouseRepository warehouseRepo, ProductRepository productRepo) {
        this.warehouseRepo = warehouseRepo;
        this.productRepo = productRepo;
    }

    public void addWarehouse(Warehouse warehouse) {
        warehouseRepo.addWarehouse(warehouse);
        warehouseRepo.saveData();
    }

    public boolean closeWarehouse(int warehouseId) {
        boolean result = warehouseRepo.closeWarehouse(warehouseId);
        if (result) {
            warehouseRepo.saveData();
        }
        return result;
    }

    public Warehouse getWarehouse(int id) {
        return warehouseRepo.getWarehouse(id);
    }

    public List<Warehouse> getActiveWarehouses() {
        return warehouseRepo.getAllWarehouses().stream()
                .filter(warehouse -> !warehouse.isClosed())
                .toList();
    }

    public boolean moveProduct(int productId, int fromWarehouseId, int toWarehouseId, int quantity) {
        if (quantity <= 0) {
            System.err.println("Ошибка: количество должно быть положительным");
            return false;
        }

        Product product = productRepo.getProduct(productId);
        Warehouse fromWarehouse = warehouseRepo.getWarehouse(fromWarehouseId);
        Warehouse toWarehouse = warehouseRepo.getWarehouse(toWarehouseId);

        if (product == null) {
            System.err.println("Ошибка: товар с ID " + productId + " не найден");
            return false;
        }
        if (fromWarehouse == null) {
            System.err.println("Ошибка: исходный склад с ID " + fromWarehouseId + " не найден");
            return false;
        }
        if (toWarehouse == null) {
            System.err.println("Ошибка: целевой склад с ID " + toWarehouseId + " не найден");
            return false;
        }

        // Проверки статуса складов
        if (fromWarehouse.isClosed()) {
            System.err.println("Ошибка: исходный склад закрыт");
            return false;
        }
        if (toWarehouse.isClosed()) {
            System.err.println("Ошибка: целевой склад закрыт");
            return false;
        }

        // Проверка наличия товара
        int availableQuantity = fromWarehouse.getProductQuantity(productId);
        if (availableQuantity < quantity) {
            System.err.println("Ошибка: недостаточно товара на исходном складе (доступно: " + availableQuantity + ", требуется: " + quantity + ")");
            return false;
        }

        // Проверка места на целевом складе
        int availableSpace = toWarehouse.getTotalCapacity() - toWarehouse.getUsedCapacity();
        if (availableSpace < quantity) {
            System.err.println("Ошибка: недостаточно места на целевом складе (доступно: " + availableSpace + ", требуется: " + quantity + ")");
            return false;
        }

        System.out.println("Попытка перемещения: " + quantity + " единиц товара " + productId
                + " со склада " + fromWarehouseId + " на склад " + toWarehouseId);

        // Удаляем с исходного склада
        if (!fromWarehouse.removeProduct(productId, quantity)) {
            System.err.println("Ошибка: не удалось удалить товар с исходного склада");
            return false;
        }

        // Добавляем на целевой склад
        if (!toWarehouse.addProduct(productId, quantity)) {
            System.err.println("Ошибка: не удалось добавить товар на целевой склад. Откат изменений...");
            fromWarehouse.addProduct(productId, quantity); // Откат
            return false;
        }

        // Сохраняем изменения
        warehouseRepo.saveData();
        productRepo.saveData();

        System.out.println("Успешно перемещено " + quantity + " единиц товара " + productId
                + " со склада " + fromWarehouseId + " на склад " + toWarehouseId);
        return true;
    }

    public boolean addProductToWarehouse(int warehouseId, int productId, int quantity) {
        Warehouse warehouse = warehouseRepo.getWarehouse(warehouseId);
        if (warehouse != null && !warehouse.isClosed()) {
            return warehouse.addProduct(productId, quantity);
        }
        return false;
    }
}