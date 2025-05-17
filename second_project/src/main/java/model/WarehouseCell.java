package model;
public class WarehouseCell {
    private int id;
    private int capacity;
    private int productId; // 0 - ячейка пуста
    private int productQuantity;
    private int storageId; // ID склада, которому принадлежит ячейка

    public WarehouseCell(int id, int capacity, int storageId) {
        this(id, capacity, storageId, 0, 0);
    }

    public WarehouseCell(int id, int capacity, int storageId,
                         int productId, int productQuantity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Емкость ячейки должна быть положительной");
        }
        this.id = id;
        this.capacity = capacity;
        this.storageId = storageId;
        this.productId = productId;
        this.productQuantity = productQuantity;
    }


    public void manageProduct(int productId, int productQuantity) {
        if (productId <= 0) {
            throw new IllegalArgumentException("ID товара должен быть положительным");
        }
        if (productQuantity < 0 || productQuantity > capacity) {
            throw new IllegalArgumentException("Некорректное количество товара");
        }
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public void manageProductQuantity(int quantityChange) {
        int newQuantity = productQuantity + quantityChange;
        if (newQuantity < 0 || newQuantity > capacity) {
            throw new IllegalArgumentException("Невозможно изменить количество");
        }
        this.productQuantity = newQuantity;

        // Если товара не осталось, очищаем ячейку
        if (this.productQuantity == 0) {
            this.productId = 0;
        }
    }


    public boolean isEmpty() {
        return productId == 0;
    }

    public void clear() {
        this.productId = 0;
        this.productQuantity = 0;
    }


    public int getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getProductId() {
        return productId;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public int getStorageId() {
        return storageId;
    }

    public int getRemainingSpace() {
        return capacity - productQuantity;
    }

    @Override
    public String toString() {
        return String.format("Ячейка ID:%d (Склад:%d) %s - %d/%d",
                id, storageId,
                productId == 0 ? "Пусто" : "Товар:" + productId,
                productQuantity, capacity);
    }
}