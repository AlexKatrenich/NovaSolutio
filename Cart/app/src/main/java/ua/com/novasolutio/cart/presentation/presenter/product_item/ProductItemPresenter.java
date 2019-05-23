package ua.com.novasolutio.cart.presentation.presenter.product_item;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import ua.com.novasolutio.cart.CartApplication;
import ua.com.novasolutio.cart.model.data.Product;
import ua.com.novasolutio.cart.model.data.ProductListManager;
import ua.com.novasolutio.cart.presentation.presenter.BasePresenter;
import ua.com.novasolutio.cart.ui.view_holder.ProductViewHolder;
import ua.com.novasolutio.cart.ui.activity.product.AddChangeProductActivity;


public class ProductItemPresenter extends BasePresenter<Product, ProductViewHolder> {
    public static final String TAG = "ProductItemPresenter";

    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 99; // Максимальне значення для Product.count
    protected int position;

    public void setPosition(int pos){
        position = pos;
    }


    @Override
    protected void updateView() {
        view().setProductCaption(model.getCaption());
        int price = model.getPrice(); // отримання ціни товару в Int значенні
        String formattedPrice = formatPriceOnText((long)price); // форматування ціни товару в текстове значення, з додаванням роздільника
        view().setProductPrice(formattedPrice);
        view().setCounterProduct(model.getCount());

        if(model.getCount() > MIN_VALUE) {
            view().changeCancelButtonSize(ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        if (model.getCount() == MIN_VALUE) {
            view().changeCancelButtonSize(0);
        }
    }


    public void onContextMenuClicked(View v) {
        view().showPopupMenu(v);
    }


    public void onDeleteContextMenuItemClicked() {
        ProductListManager.getInstance().removeProduct(model);  // delete product from ProductListManager
        new DeleteProductTask().execute(model); // delete product from DB
    }

    public void onChangeContextMenuItemClicked(Context context) {
        Intent intent = new Intent(context, AddChangeProductActivity.class);
        intent.putExtra(AddChangeProductActivity.INTENT_CODE_FOR_GETTING_MODEL, model.getID());
        context.startActivity(intent);
    }

    // Клас для асинхронного видалення елементу з Бази даних
    private class DeleteProductTask extends AsyncTask<Product, Void, Product> {

        @Override
        protected Product doInBackground(Product... products) {
            Product product = products[0];
            product.setDeleted(true);
            CartApplication.getInstance().getDatabase().mProductDao().update(product); // видалення запису з БД
            Log.i(TAG, "doInBackground: Delete Product From DB " + product);
            return product;
        }
    }

    // метод опрацьовує логіку виконання після натиснення користувачем на загальний елемент RecyclerView
    public void onItemClick() {
        if(setupDone() && model.getCount() < MAX_VALUE) {
            model.setCount(model.getCount() + 1);
            ProductListManager.getInstance().setProductById(model, model.getID());
            updateView();
        }
    }

    // метод для обробки кліку по елементу ImageButton(кнопка скидання рахувальника натиснень по елементу Product в RecyclerView)
    public void onCanceledButtonClicked() {
        if(setupDone()) {
            if (model.getCount() > MIN_VALUE){
                model.setCount(model.getCount() - 1);
                ProductListManager.getInstance().setProductById(model, model.getID());
                updateView();
            }
        }
    }
}
