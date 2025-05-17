package demo;

import helper.*;
import model.Product;
import model.Shop;
import util.IdGenerator;

import java.util.Optional;
import java.util.Scanner;

public class ProductDemo {
    private final ProductHelper productHelper;
    private final Scanner scanner;
    private final WarehouseHelper warehouseHelper;
    private final ShopHelper shopHelper;

    public ProductDemo(ProductHelper productHelper, WarehouseHelper warehouseHelper, ShopHelper shopHelper) {
        this.productHelper = productHelper;
        this.scanner = new Scanner(System.in);
        this.warehouseHelper = warehouseHelper;
        this.shopHelper = shopHelper;
    }

    public void addProduct() {
        System.out.println("=== Добавление товара ===");
        System.out.print("Название: ");
        String name = scanner.nextLine();
        System.out.print("Цена продажи: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Цена закупки: ");
        double purchasePrice = Double.parseDouble(scanner.nextLine());
        System.out.print("Количество: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        System.out.print("Производитель: ");
        String manufacturer = scanner.nextLine();

        Product product = new Product(
                IdGenerator.generateId(),
                name,
                price,
                purchasePrice,
                quantity,
                manufacturer
        );
        productHelper.addProduct(product);
        System.out.println("Товар добавлен: " + product);
    }

    public void sellProduct() {
        System.out.println("=== Продажа товара ===");

        System.out.println("Доступные магазины:");
        shopHelper.getActiveShops().forEach(s ->
                System.out.println("ID: " + s.getId() + " - " + s.getLocation()));

        System.out.print("Выберите ID магазина: ");
        int shopId = Integer.parseInt(scanner.nextLine());

        Optional<Shop> shopOpt = shopHelper.getShop(shopId);
        if (shopOpt.isEmpty()) {
            System.out.println("Магазин не найден!");
            return;
        }

        Shop shop = shopOpt.get();
        System.out.println("Товары в магазине:");
        shop.getProducts().forEach((id, product) ->
                System.out.println(product.toString()));

        System.out.print("Введите ID товара для продажи: ");
        int productId = Integer.parseInt(scanner.nextLine());
        System.out.print("Количество: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        if (shopHelper.processSale(shopId, productId, quantity)) {
            System.out.println("Товар успешно продан!");
        } else {
            System.out.println("Ошибка при продаже товара");
        }
    }

    public void returnProduct() {
        System.out.println("\n=== Возврат товара ===");
        System.out.println("Доступные магазины:");
        shopHelper.getActiveShops().forEach(s ->
                System.out.println("ID: " + s.getId() + " - " + s.getLocation()));

        System.out.print("Выберите ID магазина: ");
        int shopId = Integer.parseInt(scanner.nextLine());

        Optional<Shop> shopOpt = shopHelper.getShop(shopId);
        if (shopOpt.isEmpty()) {
            System.out.println("Магазин не найден!");
            return;
        }

        Shop shop = shopOpt.get();
        System.out.println("Товары в магазине:");
        shop.getProducts().forEach((id, product) ->
                System.out.println(product.toString()));

        System.out.print("Введите ID возвращаемого товара: ");
        int productId = Integer.parseInt(scanner.nextLine());
        System.out.print("Количество: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        if (shopHelper.processReturn(shopId, productId, quantity)) {
            System.out.println("Товар успешно возвращен!");
        } else {
            System.out.println("Ошибка при возврате товара");
        }
    }
}