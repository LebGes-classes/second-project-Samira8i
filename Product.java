public class Product {
    private String name;
    private double price;
    private double purchasePrice;
    private String dimensions;
    private Manufacturer manufacturer;
    private String status;
    private int quantity;
    private LocalDate purchaseDate;
    public Product(String id, String name, double price, double purchasePrice,
                   String dimensions, Manufacturer manufacturer, String status,
                   int quantity, LocalDate purchaseDate) {
        super(id);
        this.name = name;
        this.price = price;
        this.purchasePrice = purchasePrice;
        this.dimensions = dimensions;
        this.manufacturer = manufacturer;
        this.status = status;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
    }

    // Геттеры и сеттеры
    public String getName() { return name; }
    public double getPrice() { return price; }
    public double getPurchasePrice() { return purchasePrice; }
    public String getDimensions() { return dimensions; }
    public Manufacturer getManufacturer() { return manufacturer; }
    public String getStatus() { return status; }
    public int getQuantity() { return quantity; }
    public LocalDate getPurchaseDate() { return purchaseDate; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setStatus(String status) { this.status = status; }

}
