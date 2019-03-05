package ua.com.novasolutio.cart.mock;

import android.util.ArrayMap;

import ua.com.novasolutio.cart.data.Product;

/*Клас для підміни отримання даних від БД*/
public class MockDB {

    public static ArrayMap<Integer, Product> getProductListForDB(){
        ArrayMap<Integer, Product> productMap = new ArrayMap<>();

        productMap.put(1, new Product(1, "Coffee", 200, 1));
        productMap.put(2, new Product(2, "Bread", 300, 1));
        productMap.put(3, new Product(3, "Tea", 400, 1));
        productMap.put(4, new Product(4, "Cookies", 500, 1));
        productMap.put(5, new Product(5, "Juice", 1200, 1));
        productMap.put(6, new Product(6, "Cocoa", 75, 1));
        productMap.put(7, new Product(7, "Chocolate", 230, 1));
        productMap.put(8, new Product(8, "Candy", 42, 1));
        productMap.put(9, new Product(9, "Cheese", 32, 1));
        productMap.put(10, new Product(10, "Peanut", 18, 1));
        productMap.put(11, new Product(11, "Sausage", 74, 1));
        productMap.put(12, new Product(12, "Coconut", 32, 1));
        productMap.put(13, new Product(13, "Pudding", 23, 1));
        productMap.put(14, new Product(14, "Latte", 17, 1));
        productMap.put(15, new Product(15, "Raisins", 15, 1));
        productMap.put(16, new Product(16, "ice cream", 32, 1));
        productMap.put(17, new Product(17, "Dumplings", 61, 1));
        productMap.put(18, new Product(18, "boiled dough", 43, 1));
        productMap.put(19, new Product(19, "Paste", 41, 1));
        productMap.put(20, new Product(20, "Ham", 32, 1));
        productMap.put(21, new Product(21, "Snickers", 23, 1));
        productMap.put(22, new Product(22, "Chewing gum", 41, 1));
        productMap.put(23, new Product(23, "Tangerine", 11, 1));
        productMap.put(24, new Product(24, "Orange", 22, 1));
        productMap.put(25, new Product(25, "Banana", 14, 1));
        productMap.put(26, new Product(26, "Lemon", 10, 1));
        productMap.put(27, new Product(27, "Grapefruit", 5, 1));
        productMap.put(28, new Product(28, "Pineapple", 9, 1));
        productMap.put(29, new Product(29, "Apple", 5, 1));
        productMap.put(30, new Product(30, "Soap", 16, 1));
        productMap.put(31, new Product(31, "Liquid soap", 13, 1));
        productMap.put(32, new Product(32, "Nuts", 12, 1));
        productMap.put(33, new Product(33, "Oreo", 3, 1));

        return productMap;
    }

}
