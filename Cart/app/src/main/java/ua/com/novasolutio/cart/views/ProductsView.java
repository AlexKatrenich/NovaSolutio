package ua.com.novasolutio.cart.views;

import java.util.List;

import ua.com.novasolutio.cart.data.Product;

/* Інтерфейс, що відображає віджет списку продуктів(ProductList)*/
public interface ProductsView {
    void showEmpty();
    void showProducts(List<Product> products);
}
