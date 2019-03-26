package ua.com.novasolutio.cart.presenters;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import ua.com.novasolutio.cart.data.Product;
import ua.com.novasolutio.cart.mock.MockDB;
import ua.com.novasolutio.cart.views.ProductViewHolder;
import ua.com.novasolutio.cart.views.activities.AddChangeProductActivity;


public class ProductItemPresenter extends BasePresenter<Product, ProductViewHolder>{
    public static final String TAG = "ProductItemPresenter";

    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 99;
    protected int position;

    public void setPosition(int pos){
        position = pos;
    }


    @Override
    protected void updateView() {
        view().setProductCaption(model.getCaption());
        int price = model.getPrice(); // отримання ціни товару в Int значенні
        String formattedPrice = formatPriceOnText(price); // форматування ціни товару в текстове значення, з додаванням роздільника
        view().setProductPrice(formattedPrice);
        view().setCounterProduct(model.getCount());
    }

    public void onLeftSwipeMinusProduct(){
        if(setupDone() && model.getCount() > MIN_VALUE){
            model.setCount(model.getCount() - 1);
            updateView();
        }
    }

    public void onRightSwipePlusProduct(){
        if(setupDone() && model.getCount() < MAX_VALUE){
            model.setCount(model.getCount() + 1);
            updateView();
        }
    }

    public void onContextMenuClicked(View v) {
        Log.i(TAG, "onContextMenuClicked: ");
        view().showPopupMenu(v);
    }


    public void onDeleteContextMenuItemClicked() {
        new DeleteProductTask().execute(model);
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
            SystemClock.sleep(500); // емуляція запису в БД
            Product product = products[0];
            MockDB mDB = MockDB.getInstance();
            mDB.deleteProductById(product.getID());
            Log.i(TAG, "doInBackground: Delete Product From DB " + product);
            return product;
        }

        @Override
        protected void onPostExecute(Product product) {
            Log.i(TAG, "onPostExecute: REMOVE" + product);
            MockDB.getInstance().observeOnDbProductRemove(product);
            super.onPostExecute(product);
        }
    }

    public void onItemClick() {
        model.setCount(model.getCount() + 1);
        MockDB.getInstance().setProduct(model); //Запис до бази даних, TODO зробити асинхронним
        updateView();
    }

    public Product getModel(){
        return model;
    }
}
