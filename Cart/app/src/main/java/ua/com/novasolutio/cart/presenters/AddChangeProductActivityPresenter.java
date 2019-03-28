package ua.com.novasolutio.cart.presenters;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;



import ua.com.novasolutio.cart.data.Product;
import ua.com.novasolutio.cart.data.ProductListManager;
import ua.com.novasolutio.cart.mock.MockDB;
import ua.com.novasolutio.cart.views.ProductView;
import ua.com.novasolutio.cart.views.activities.AddChangeProductActivity;

public class AddChangeProductActivityPresenter extends BasePresenter<Product, ProductView>{
    public static final String TAG = "AddChProdActivPresent";
    private boolean isLoadingData = false;

    @Override
    protected void updateView() {
        if(model != null){
            view().setProductCaption(model.getCaption());
            view().setProductPrice(formatPriceOnText((long) model.getPrice()));
        }
    }

    @Override
    public void bindView(@NonNull ProductView view) {
        super.bindView(view);

        // не потрібно повторно завантажувати дані повторно, якщо вони вже завантажені або в процесі завантаження
        if(model == null && !isLoadingData){
            loadModel(-1);
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

        if(productId == -1){
            Product product = new Product();
            product.setCaption("");
            product.setPrice(0);
            setModel(product);
            isLoadingData = false;
        } else {
            ProductListManager.getInstance().getProductById(productId);
        }
    }

    public void changeProductCaption(String caption){
        model.setCaption(caption);
    }

    public void changeProductPrice(int price){
        model.setPrice(price);
    }

    public void OnSaveButtonClicked() {
        new WriteDataTask().execute(model);

        // отримання ІД нового продукту з бази даних(якщо, об'єкт новий)
        MockDB mDB = MockDB.getInstance();
        if (model.getID() == -1){
            model.setID(mDB.getIdCounter());
        }

        if(!ProductListManager.getInstance().setProductById(model, model.getID())) ProductListManager.getInstance().addProduct(model);

        if(setupDone()) ((AddChangeProductActivity)view()).onBackPressed();
    }


    // внутрішній клас для емуляції запису додавання нового продукту до бази даних
    private class WriteDataTask extends AsyncTask<Product, Void, Void> {

        @Override
        protected Void doInBackground(Product... products) {
            // емуляція запису в БД
            Product product = products[0];
            Log.i(TAG, "doInBackground: Product: " + product);
            MockDB.getInstance().setProduct(product);
            return null;
        }

    }

}
