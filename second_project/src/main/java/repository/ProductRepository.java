package repository;

import model.Product;
import java.io.IOException;
import java.util.*;

public class ProductRepository {
    private static final String FILE_NAME = "products.txt";
    private final Map<Integer, Product> productsMap = new HashMap<>();

    public ProductRepository() {
        loadProducts().forEach(prod -> productsMap.put(prod.getId(), prod));
    }

    private List<Product> loadProducts() {
        List<Product> products = new ArrayList<>();
        try {
            String data = FileDataManager.load(FILE_NAME);
            if (data == null || data.isEmpty()) return products;

            Scanner scanner = new Scanner(data);
            while (scanner.hasNextLine()) {
                Product prod = parseProduct(scanner.nextLine());
                if (prod != null) products.add(prod);
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println("Ошибка загрузки продуктов: " + e.getMessage());
        }
        return products;
    }

    private Product parseProduct(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length != 7) return null;

            return new Product(
                    Integer.parseInt(parts[0].trim()),
                    parts[1].trim(),
                    Double.parseDouble(parts[2].trim()),
                    Double.parseDouble(parts[3].trim()),
                    Integer.parseInt(parts[4].trim()),
                    parts[5].trim()
            );
        } catch (Exception e) {
            System.err.println("Ошибка парсинга продукта: " + line);
            return null;
        }
    }

    public void saveData() {
        try {
            StringBuilder sb = new StringBuilder();
            for (Product prod : productsMap.values()) {
                sb.append(productToString(prod)).append("\n");
            }
            FileDataManager.save(FILE_NAME, sb.toString());
        } catch (IOException e) {
            System.err.println("Ошибка сохранения продуктов: " + e.getMessage());
        }
    }

    private String productToString(Product prod) {
        return String.join("|",
                String.valueOf(prod.getId()),
                prod.getName(),
                String.valueOf(prod.getPrice()),
                String.valueOf(prod.getPurchasePrice()),
                String.valueOf(prod.getQuantity()),
                prod.getManufacturer(),
                prod.getArrivalDate().toString()
        );
    }

    public void addProduct(Product product) {
        if (productsMap.containsKey(product.getId())) {
            throw new IllegalArgumentException("ID продукта уже существует");
        }
        productsMap.put(product.getId(), product);
    }

    public Product getProduct(int id) {
        return productsMap.get(id);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(productsMap.values());
    }

    public boolean updateProductQuantity(int id, int quantityChange) {
        Product product = productsMap.get(id);
        if (product != null) {
            int newQuantity = product.getQuantity() + quantityChange;
            if (newQuantity >= 0) {
                product.setQuantity(newQuantity);
                return true;
            }
        }
        return false;
    }

    public boolean returnProduct(int productId, int quantity) {
        Product product = productsMap.get(productId);
        if (product != null && quantity > 0) {
            product.increaseQuantity(quantity);
            saveData();
            return true;
        }
        return false;
    }
}