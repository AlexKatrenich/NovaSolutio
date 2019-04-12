package ua.com.novasolutio.cart.presentation.view;

import java.util.List;

import ua.com.novasolutio.cart.model.data.Product;

/* Інтерфейс, що відображає віджет списку продуктів(ProductList)*/
public interface ProductsListView {
    void showEmpty();

    void showProducts(List<Product> products);

    void showMessage(String message);

}
