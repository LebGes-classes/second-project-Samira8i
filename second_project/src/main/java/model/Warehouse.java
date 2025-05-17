package model;

import java.util.ArrayList;
import java.util.List;
public class Warehouse {
    private int id;
    private String location;
    private List<WarehouseCell> cells;
    private boolean closed;

    public Warehouse(int id, String location) {
        this.id = id;
        this.location = location;
        this.cells = new ArrayList<>();
        this.closed = false;
    }

    public boolean addProduct(int productId, int quantity) {
        if (isClosed()) {
            System.err.println("Ошибка: склад закрыт");
            return false;
        }
        if (quantity <= 0) {
            System.err.println("Ошибка: количество должно быть положительным");
            return false;
        }

        int remaining = quantity;
        while (remaining > 0) {
            WarehouseCell cell = findAvailableCell(productId);
            if (cell == null) {
                System.err.println("Ошибка: нет доступных ячеек для товара " + productId);
                return false;
            }

            int space = cell.getRemainingSpace();
            int toAdd = Math.min(space, remaining);

            if (cell.getProductId() == 0) {
                cell.manageProduct(productId, toAdd);
            } else {
                cell.manageProductQuantity(toAdd);
            }

            remaining -= toAdd;
            System.out.println("Добавлено " + toAdd + " единиц товара " + productId + " в ячейку " + cell.getId());
        }
        return true;
    }

    public boolean removeProduct(int productId, int quantity) {
        if (isClosed()) {
            System.err.println("Ошибка: склад закрыт");
            return false;
        }
        if (quantity <= 0) {
            System.err.println("Ошибка: количество должно быть положительным");
            return false;
        }

        System.out.println("Попытка удаления " + quantity + " единиц товара " + productId);

        int remaining = quantity;
        for (WarehouseCell cell : cells) {
            if (cell.getProductId() == productId) {
                int available = cell.getProductQuantity();
                int toRemove = Math.min(available, remaining);

                cell.manageProductQuantity(-toRemove);
                remaining -= toRemove;

                System.out.println("Удалено " + toRemove + " единиц из ячейки " + cell.getId());

                if (remaining == 0) break;
            }
        }

        if (remaining > 0) {
            System.err.println("Ошибка: не удалось удалить все количество (осталось: " + remaining + ")");
            return false;
        }
        return true;
    }
    private WarehouseCell findAvailableCell(int productId) {
        for (WarehouseCell cell : cells) {
            if (cell.getProductId() == productId && cell.getRemainingSpace() > 0) {
                return cell;
            }
        }
        for (WarehouseCell cell : cells) {
            if (cell.getProductId() == 0) {
                return cell;
            }
        }

        return null;
    }

    //не использую так как в тз не надо,но можно реализовать
    public void addCell(WarehouseCell cell) {
        if (cell.getStorageId() != this.id) {
            throw new IllegalArgumentException("Ячейка принадлежит другому складу");
        }
        this.cells.add(cell);
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public int getProductQuantity(int productId) {
        int total = 0;
        for (WarehouseCell cell : cells) {
            if (cell.getProductId() == productId) {
                total += cell.getProductQuantity();
            }
        }
        System.out.println("На складе " + id + " товара " + productId + ": " + total + " единиц");
        return total;
    }

    public int getTotalCapacity() {
        return cells.stream().mapToInt(WarehouseCell::getCapacity).sum();
    }

    public int getUsedCapacity() {
        return cells.stream().mapToInt(WarehouseCell::getProductQuantity).sum();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCells(List<WarehouseCell> cells) {
        this.cells = new ArrayList<>(cells);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return String.format("Склад ID:%d (%s), Ячеек: %d, Закрыт: %s",
                id, location, cells.size(), closed ? "да" : "нет");
    }

    public List<WarehouseCell> getCells() {
        return cells;
    }
}