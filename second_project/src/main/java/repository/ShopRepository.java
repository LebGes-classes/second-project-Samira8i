package repository;

import model.Product;
import model.Shop;
import model.Employee;
import java.io.IOException;
import java.util.*;

public class ShopRepository {
    private static final String FILE_NAME = "shops.txt";
    private final Map<Integer, Shop> shopsMap = new HashMap<>();
    private final EmployeeRepository employeeRepo;

    public ShopRepository(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
        loadShops().forEach(shop -> shopsMap.put(shop.getId(), shop));
    }

    private List<Shop> loadShops() {
        List<Shop> shops = new ArrayList<>();
        try {
            String data = FileDataManager.load(FILE_NAME);
            if (data == null || data.isEmpty()) return shops;

            Scanner scanner = new Scanner(data);
            while (scanner.hasNextLine()) {
                Shop shop = parseShop(scanner.nextLine());
                if (shop != null) shops.add(shop);
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println("Ошибка загрузки магазинов: " + e.getMessage());
        }
        return shops;
    }

    private Shop parseShop(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length < 3) return null;

            Shop shop = new Shop(
                    Integer.parseInt(parts[0].trim()),
                    parts[1].trim()
            );

            if (parts[2].trim().equals("0")) {
                shop.setActive(false);
            }

            if (parts.length > 3 && !parts[3].trim().isEmpty()) {
                int managerId = Integer.parseInt(parts[3].trim());
                employeeRepo.getEmployee(managerId).ifPresent(shop::setManager);
            }

            for (int i = 4; i < parts.length; i++) {
                String[] productData = parts[i].split(",");
                if (productData.length >= 6) {
                    Product product = new Product(
                            Integer.parseInt(productData[0]),
                            productData[1],
                            Double.parseDouble(productData[2]),
                            Double.parseDouble(productData[3]),
                            Integer.parseInt(productData[4]),
                            productData[5]
                    );
                    shop.getProducts().put(product.getId(), product);
                }
            }

            return shop;
        } catch (Exception e) {
            System.err.println("Ошибка парсинга магазина: " + line);
            return null;
        }
    }

    public void saveData() {
        try {
            StringBuilder sb = new StringBuilder();
            for (Shop shop : shopsMap.values()) {
                sb.append(shopToString(shop)).append("\n");
            }
            FileDataManager.save(FILE_NAME, sb.toString());
        } catch (IOException e) {
            System.err.println("Ошибка сохранения магазинов: " + e.getMessage());
        }
    }

    private String shopToString(Shop shop) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.join("|",
                String.valueOf(shop.getId()),
                shop.getLocation(),
                shop.isActive() ? "1" : "0",
                shop.getManager() != null ? String.valueOf(shop.getManager().getId()) : ""));

        for (Product product : shop.getProducts().values()) {
            sb.append("|").append(String.format("%d,%s,%.2f,%.2f,%d,%s",
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getPurchasePrice(),
                    product.getQuantity(),
                    product.getManufacturer()));
        }

        return sb.toString();
    }

    public void addShop(Shop shop) {
        if (shopsMap.containsKey(shop.getId())) {
            throw new IllegalArgumentException("ID магазина уже существует");
        }
        shopsMap.put(shop.getId(), shop);
    }

    public Optional<Shop> getShop(int id) {
        return Optional.ofNullable(shopsMap.get(id));
    }

    public List<Shop> getActiveShops() {
        List<Shop> active = new ArrayList<>();
        for (Shop shop : shopsMap.values()) {
            if (shop.isActive()) active.add(shop);
        }
        return active;
    }

    public boolean closeShop(int id) {
        Shop shop = shopsMap.get(id);
        if (shop != null) {
            shop.setActive(false);
            return true;
        }
        return false;
    }
    //назначение сотудника
    public boolean assignManager(int shopId, int employeeId) {
        Optional<Shop> shopOpt = getShop(shopId);
        Optional<Employee> employeeOpt = employeeRepo.getEmployee(employeeId);

        if (shopOpt.isPresent() && employeeOpt.isPresent()) {
            Shop shop = shopOpt.get();
            Employee employee = employeeOpt.get();
            shop.setManager(employee);
            saveData();
            return true;
        }
        return false;
    }
}