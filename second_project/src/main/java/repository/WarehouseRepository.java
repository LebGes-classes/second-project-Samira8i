package repository;


import model.Warehouse;

import java.io.IOException;
import java.util.*;

public class WarehouseRepository {
    private static final String FILE_NAME = "warehouses.txt";
    private final Map<Integer, Warehouse> warehousesMap = new HashMap<>();

    public WarehouseRepository() {
        loadWarehouses().forEach(warehouse -> warehousesMap.put(warehouse.getId(), warehouse));
    }

    private List<Warehouse> loadWarehouses() {
        List<Warehouse> warehouses = new ArrayList<>();
        try {
            String data = FileDataManager.load(FILE_NAME);
            if (data == null || data.isEmpty()) return warehouses;

            Scanner scanner = new Scanner(data);
            while (scanner.hasNextLine()) {
                Warehouse warehouse = parseWarehouse(scanner.nextLine());
                if (warehouse != null) warehouses.add(warehouse);
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println("Ошибка загрузки складов: " + e.getMessage());
        }
        return warehouses;
    }

    private Warehouse parseWarehouse(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length < 3) return null;

            Warehouse warehouse = new Warehouse(
                    Integer.parseInt(parts[0].trim()),
                    parts[1].trim()
            );
            if (parts.length > 2 && parts[2].trim().equals("0")) {
                warehouse.setClosed(true);
            }

            return warehouse;
        } catch (Exception e) {
            System.err.println("Ошибка парсинга склада: " + line);
            return null;
        }
    }
    public void saveData() {
        try {
            StringBuilder sb = new StringBuilder();
            for (Warehouse warehouse : warehousesMap.values()) {
                sb.append(warehouseToString(warehouse)).append("\n");
            }
            FileDataManager.save(FILE_NAME, sb.toString());
        } catch (IOException e) {
            System.err.println("Ошибка сохранения магазинов: " + e.getMessage());
        }
    }

    private String warehouseToString(Warehouse warehouse) {
        return String.join("|",
                String.valueOf(warehouse.getId()),
                warehouse.getLocation(),
                warehouse.isClosed() ? "0" : "1"  // 1 - активен, 0 - закрыт
        );
    }

    public void addWarehouse(Warehouse warehouse) {
        if (warehousesMap.containsKey(warehouse.getId())) {
            throw new IllegalArgumentException("ID склада уже существует");
        }
        warehousesMap.put(warehouse.getId(), warehouse);
    }

    public boolean closeWarehouse(int warehouseId) {
        Warehouse warehouse = warehousesMap.get(warehouseId);
        if (warehouse != null && !warehouse.isClosed()) {
            warehouse.setClosed(true);
            return true;
        }
        return false;
    }

    public Warehouse getWarehouse(int id) {
        return warehousesMap.get(id);
    }

    public List<Warehouse> getAllWarehouses() {
        return new ArrayList<>(warehousesMap.values());
    }
}