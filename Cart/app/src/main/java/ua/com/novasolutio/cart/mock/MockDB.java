package ua.com.novasolutio.cart.mock;

import android.util.ArrayMap;

import ua.com.novasolutio.cart.data.Product;

/*Клас для підміни отримання даних від БД*/
public class MockDB {
    ArrayMap<Integer, Product> productMap;
    public static MockDB instance;


    private MockDB(){
        productMap = new ArrayMap<>();
        setProductListForDB();
    }

    private void setProductListForDB(){
        productMap.put(1, new Product(1, "Coffee", 20, 1));
        productMap.put(2, new Product(2, "Bread", 30000, 1));
        productMap.put(3, new Product(3, "Tea", 40000, 1));
        productMap.put(4, new Product(4, "Cookies", 50000, 1));
        productMap.put(5, new Product(5, "Juice", 120000, 1));
        productMap.put(6, new Product(6, "Cocoa", 7500, 1));
        productMap.put(7, new Product(7, "Chocolate", 23000, 1));
        productMap.put(8, new Product(8, "Candy", 4200, 1));
        productMap.put(9, new Product(9, "Cheese", 3200, 1));
        productMap.put(10, new Product(10, "Peanut", 1800, 1));
        productMap.put(11, new Product(11, "Sausage", 7400, 1));
        productMap.put(12, new Product(12, "Coconut", 3200, 1));
        productMap.put(13, new Product(13, "Pudding", 2300, 1));
        productMap.put(14, new Product(14, "Latte", 1700, 1));
        productMap.put(15, new Product(15, "Raisins", 1500, 1));
        productMap.put(16, new Product(16, "ice cream", 3200, 1));
        productMap.put(17, new Product(17, "Dumplings", 6100, 1));
        productMap.put(18, new Product(18, "boiled dough", 4300, 1));
        productMap.put(19, new Product(19, "Paste", 4100, 1));
        productMap.put(20, new Product(20, "Ham", 3200, 1));
        productMap.put(21, new Product(21, "Snickers", 2300, 1));
        productMap.put(22, new Product(22, "Chewing gum", 4100, 1));
        productMap.put(23, new Product(23, "Tangerine", 1100, 1));
        productMap.put(24, new Product(24, "Orange", 2200, 1));
        productMap.put(25, new Product(25, "Banana", 1400, 1));
        productMap.put(26, new Product(26, "Lemon", 1000, 1));
        productMap.put(27, new Product(27, "Grapefruit", 500, 1));
        productMap.put(28, new Product(28, "Pineapple", 900, 1));
        productMap.put(29, new Product(29, "Apple", 500, 1));
        productMap.put(30, new Product(30, "Soap", 1600, 1));
        productMap.put(31, new Product(31, "Liquid soap", 1300, 1));
        productMap.put(32, new Product(32, "Nuts", 1200, 1));
        productMap.put(33, new Product(33, "Oreo", 300, 1));
    }

    public ArrayMap<Integer, Product> getProductMap(){
        return productMap;
    }

    public void addProduct(Product product){
        productMap.put(product.getID(), product);
    }

    public Product getProductById(Integer id){
        return productMap.get(id);
    }

    public static MockDB getInstance() {
        if(instance == null){
            instance = new MockDB();
        }
        return instance;
    }


}
