package ua.com.novasolutio.cart.presenters;

import android.content.Intent;
import android.os.AsyncTask;
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

            // отримання ІД нового продукту з бази даних(якщо, об'єкт новий)
            MockDB mDB = MockDB.getInstance();
            if (model.getID() == -1){
                model.setID(mDB.getIdCounter());
            }

            if(!ProductListManager.getInstance().setProductById(model, model.getID())) ProductListManager.getInstance().addProduct(model);

            if(setupDone()) ((AddChangeProductActivity)view()).onBackPressed();
        }

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
