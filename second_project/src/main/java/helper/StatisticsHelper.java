package helper;

import model.Product;
import model.Shop;
import repository.ProductRepository;
import repository.ShopRepository;
import repository.WarehouseRepository;

public class StatisticsHelper{
    private final ProductRepository productRepo;
    private final ShopRepository shopRepo;
    private final WarehouseRepository warehouseRepo;

    public StatisticsHelper(ProductRepository productRepo, ShopRepository shopRepo,
                         WarehouseRepository warehouseRepo) {
        this.productRepo = productRepo;
        this.shopRepo = shopRepo;
        this.warehouseRepo = warehouseRepo;
    }

    public String generateInventoryReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("Отчет по товарам\n");

        for (Product product : productRepo.getAllProducts()) {
            sb.append(String.format("%s - Доступно: %d\n",
                    product.getName(), product.getQuantity()));
        }

        return sb.toString();
    }

    public String generateShopPerformanceReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("Отчет по магазинам\n");

        for (Shop shop : shopRepo.getActiveShops()) {
            sb.append(String.format("%s - Доход: %.2f\n",
                    shop.getLocation(), shop.getIncome()));
        }

        return sb.toString();
    }
}