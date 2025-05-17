package helper;

import model.Product;
import repository.ProductRepository;
import java.util.List;

public class ProductHelper {
    private final ProductRepository productRepo;

    public ProductHelper(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    public void addProduct(Product product) {
        productRepo.addProduct(product);
        productRepo.saveData();
    }

    public Product getProduct(int id) {
        return productRepo.getProduct(id);
    }

    public List<Product> getAllProducts() {
        return productRepo.getAllProducts();
    }

    public boolean updateProductQuantity(int productId, int quantityChange) {
        boolean result = productRepo.updateProductQuantity(productId, quantityChange);
        if (result) {
            productRepo.saveData();
        }
        return result;
    }

    public List<Product> getAvailableProducts() {
        return productRepo.getAllProducts().stream()
                .filter(p -> p.getQuantity() > 0)
                .toList();
    }

    public boolean returnProduct(int productId, int quantity) {
        boolean result = productRepo.returnProduct(productId, quantity);
        if (result) {
            System.out.println("Товар успешно возвращен на склад");
        } else {
            System.out.println("Ошибка возврата товара");
        }
        return result;
    }
}