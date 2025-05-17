package demo;

import helper.StatisticsHelper;
import java.util.Scanner;

public class StatisticsDemo {
    private final StatisticsHelper statisticsHelper;
    private final Scanner scanner;

    public StatisticsDemo(StatisticsHelper statisticsHelper) {
        this.statisticsHelper = statisticsHelper;
        this.scanner = new Scanner(System.in);
    }

    public void showReportsMenu() {
        System.out.println("Отчеты");
        System.out.println("1. Товары на складах");
        System.out.println("2. Продажи магазинов");
        System.out.print("Выберите отчет: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> System.out.println(statisticsHelper.generateInventoryReport());
            case 2 -> System.out.println(statisticsHelper.generateShopPerformanceReport());
            default -> System.out.println("Неверный выбор.");
        }
    }
}