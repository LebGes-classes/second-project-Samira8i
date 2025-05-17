package demo;

import model.Shop;
import helper.ShopHelper;
import util.IdGenerator;

import java.util.Scanner;

public class ShopDemo {
    private final ShopHelper shopHelper;
    private final Scanner scanner;

    public ShopDemo(ShopHelper shopHelper) {
        this.shopHelper = shopHelper;
        this.scanner = new Scanner(System.in);
    }
    public void closeShop() {
        System.out.print("Введите ID магазина для закрытия: ");
        int shopId = Integer.parseInt(scanner.nextLine());

        boolean success = shopHelper.closeShop(shopId);
        if (success) {
            System.out.println("Магазин успешно закрыт.");
        } else {
            System.out.println("Ошибка: магазин не найден или уже закрыт.");
        }
    }
    public void openShop() {
        System.out.println("Открытие магазина");
        System.out.print("Адрес: ");
        String location = scanner.nextLine();

        Shop shop = new Shop(IdGenerator.generateId(), location);
        shopHelper.openShop(shop);
        System.out.println("Магазин открыт: " + shop.getLocation());
    }
}