package ua.com.novasolutio.cart.presentation.presenter.product;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;


import ua.com.novasolutio.cart.CartApplication;
import ua.com.novasolutio.cart.model.dao.CartDatabase;
import ua.com.novasolutio.cart.model.data.Product;
import ua.com.novasolutio.cart.model.data.ProductListManager;
import ua.com.novasolutio.cart.presentation.presenter.BasePresenter;
import ua.com.novasolutio.cart.presentation.view.ProductView;
import ua.com.novasolutio.cart.ui.activity.product.AddChangeProductActivity;

public class AddChangeProductActivityPresenter extends BasePresenter<Product, ProductView> {
    public static final String TAG = "AddChProdActivPresent";
    private boolean isLoadingData = false;

    @Override
    protected void updateView() {
        if(model != null){
            view().setProductCaption(model.getCaption());
            if(model.getPrice() != 0) {
                view().setProductPrice(formatPriceOnText((long) model.getPrice()));
            }
        }
    }

    @Override
    public void bindView(@NonNull ProductView view) {
        super.bindView(view);

        // зчитування Intent та відображення даних в залежності від того чи було щось передано чере Інтент
        Intent intent = ((AddChangeProductActivity)view).getIntent();
        int productId = intent.getIntExtra(AddChangeProductActivity.INTENT_CODE_FOR_GETTING_MODEL, -1);
        Log.i(TAG, "init: PRODUCT ID FORM INTENT: " + productId);
        loadModel(productId);

        // не потрібно повторно завантажувати дані повторно, якщо вони вже завантажені або в процесі завантаження
        if(model == null && !isLoadingData){
            loadModel(productId);
        }
    }

    @Override
    public String formatPriceOnText(long price) {
        StringBuffer priceString = new StringBuffer(String.valueOf(price));
        switch (priceString.length()){
            case 0 :
                priceString.append("0.00");
                break;
            case 1:
                priceString.insert(0, "0.0");
                break;
            case 2:
                priceString.insert(0, "0.");
                break;
            default:
                priceString.insert(priceString.length() - 2, '.');
        }

        return priceString.toString();
    }

    public void loadModel(int productId) {
        isLoadingData = true;
        Product product;
        if(productId == -1){
            product = new Product();
            product.setCaption("");
            product.setPrice(0);
            product.setDeleted(false);
        } else {
           product = ProductListManager.getInstance().getProductById(productId);
        }

        setModel(product);
        isLoadingData = false;
    }

    public boolean changeProductCaption(String caption){
        if (caption != null && !caption.isEmpty()) {
            model.setCaption(caption);
            return true;
        }
        return false;
    }

    public boolean changeProductPrice(String s){
        if(s!= null && !s.isEmpty()){
            double formattedPrice = Double.valueOf(s);
            int price = (int)Math.round(formattedPrice * new Double(100));
            model.setPrice(price);
            return true;
        } else {
            return false;
        }

    }

    public void OnSaveButtonClicked() {
        if (model.getCaption() != null && !model.getCaption().isEmpty()){
            new WriteDataTask().execute(model);

            if(setupDone()) ((AddChangeProductActivity)view()).onBackPressed();
        }

    }


    // внутрішній клас для емуляції запису додавання нового продукту до бази даних
    private class WriteDataTask extends AsyncTask<Product, Void, Product> {

        @Override
        protected Product doInBackground(Product... products) {
            // емуляція запису в БД
            Product product = products[0];
            Log.i(TAG, "doInBackground: Product: " + product);
            CartDatabase db = CartApplication.getInstance().getDatabase();

            if (db.mProductDao().update(product) == 0){
                long productID = db.mProductDao().insert(product);
                product.setID((int) productID);
            }

            return product;
        }

        @Override
        protected void onPostExecute(Product product) {
            Log.i(TAG, "onPostExecute PRODUCT " + product);
            // заміна або додавання нового продукту в залежності від того чи елемент з таким Ід вже є в списку
            if (!ProductListManager.getInstance().setProductById(product, product.getID())) {
                ProductListManager.getInstance().addProduct(product);
            }
            super.onPostExecute(product);
        }
    }

}
