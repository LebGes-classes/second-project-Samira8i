package helper;

import model.Product;
import model.Shop;
import repository.EmployeeRepository;
import repository.ShopRepository;
import java.util.List;
import java.util.Optional;

public class ShopHelper {
    private final ShopRepository shopRepo;
    private final EmployeeRepository employeeRepo;
    private final ProductHelper productHelper;


    public ShopHelper(ShopRepository shopRepo, EmployeeRepository employeeRepo, ProductHelper productHelper) {
        this.shopRepo = shopRepo;
        this.employeeRepo = employeeRepo;
        this.productHelper = productHelper;
    }

    public void openShop(Shop shop) {
        shopRepo.addShop(shop);
        shopRepo.saveData();
    }

    public boolean closeShop(int shopId) {
        boolean result = shopRepo.closeShop(shopId);
        if (result) {
            shopRepo.saveData();
        }
        return result;
    }

    public Optional<Shop> getShop(int id) {
        return shopRepo.getShop(id);
    }

    public List<Shop> getActiveShops() {
        return shopRepo.getActiveShops();
    }


    public boolean assignManager(int shopId, int employeeId) {
        return shopRepo.assignManager(shopId, employeeId);
    }
    public boolean processSale(int shopId, int productId, int quantity) {
        Optional<Shop> shopOpt = shopRepo.getShop(shopId);
        if (shopOpt.isPresent()) {
            Shop shop = shopOpt.get();
            boolean result = shop.sellProduct(productId, quantity);
            if (result) {
                shopRepo.saveData();
            }
            return result;
        }
        return false;
    }

    public boolean processReturn(int shopId, int productId, int quantity) {
        Optional<Shop> shopOpt = shopRepo.getShop(shopId);
        if (shopOpt.isPresent()) {
            Shop shop = shopOpt.get();
            boolean result = shop.returnProduct(productId, quantity);
            if (result) {
                shopRepo.saveData();
            }
            return result;
        }
        return false;
    }
    public boolean addProductToShop(int shopId, int productId, int quantity) {
        Optional<Shop> shopOpt = shopRepo.getShop(shopId);
        Product product = productHelper.getProduct(productId);

        if (shopOpt.isPresent() && product != null) {
            Shop shop = shopOpt.get();
            Product shopProduct = new Product(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getPurchasePrice(),
                    quantity,
                    product.getManufacturer()
            );
            shop.getProducts().put(productId, shopProduct);
            shopRepo.saveData();
            return true;
        }
        return false;
    }
}