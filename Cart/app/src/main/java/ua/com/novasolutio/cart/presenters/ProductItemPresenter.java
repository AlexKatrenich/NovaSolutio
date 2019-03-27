package ua.com.novasolutio.cart.presenters;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import ua.com.novasolutio.cart.data.Product;
import ua.com.novasolutio.cart.mock.MockDB;
import ua.com.novasolutio.cart.views.ProductViewHolder;
import ua.com.novasolutio.cart.views.activities.AddChangeProductActivity;


public class ProductItemPresenter extends BasePresenter<Product, ProductViewHolder>{
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
        // зміна видимості кнопки, що відповідає за скидання рахувальника кількості товарів
        if (model.getCount() > MIN_VALUE){
            view().changeCancelButtonSize(ViewGroup.LayoutParams.WRAP_CONTENT);
            MockDB.getInstance().observeOnDbProductChange();
        } else {
            view().changeCancelButtonSize(0);
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

    // метод опрацьовує логіку виконання після натиснення користувачем на загальний елемент RecyclerView
    public void onItemClick() {
        if(setupDone() && model.getCount() < MAX_VALUE) {
            model.setCount(model.getCount() + 1);
            MockDB.getInstance().setProduct(model); //Запис до бази даних, TODO зробити асинхронним
            updateView();
        }
    }

    // метод для обробки кліку по елементу ImageButton(кнопка скидання рахувальника натиснень по елементу Product в RecyclerView)
    public void onCanceledButtonClicked() {
        if(setupDone()) {
            model.setCount(MIN_VALUE);
            MockDB.getInstance().setProduct(model); //Запис до бази даних, TODO зробити асинхронним
            MockDB.getInstance().observeOnDbProductChange();
            updateView();
        }
    }
}
