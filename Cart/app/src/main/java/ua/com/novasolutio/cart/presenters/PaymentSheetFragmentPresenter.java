package ua.com.novasolutio.cart.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import ua.com.novasolutio.cart.data.Payment;
import ua.com.novasolutio.cart.data.ProductListManager;
import ua.com.novasolutio.cart.views.fragments.PaymentSheetFragment;

public class PaymentSheetFragmentPresenter extends BasePresenter<Payment, PaymentSheetFragment> {
    public static final String TAG = "PaySheetFragPresenter";

    private long totalPrice = 0; // змінна відображає загальну ціну продажі

    private long currentCash = 0; // змінна відображає суму, що дав покупець

    private long currentChange = 0; // змінна відображає решту, що повинен віддати покупцеві касир


    @Override
    protected void updateView() {
        currentChange = currentCash - totalPrice;

        view().updateUI(formatPriceOnText(currentCash),
                currentCash >= 0 ? formatPriceOnText(currentCash) : "0.00 ");

        Log.i(TAG, "updateView Cash: " + currentCash + " CHANGE: " + currentChange);
    }

    @Override
    public void bindView(@NonNull PaymentSheetFragment view) {
        totalPrice = ProductListManager.getInstance().getTotalPriceSelectedProducts();
        super.bindView(view);
        Log.i(TAG, "bindView: TOTAL PRICE: " + String.valueOf(totalPrice));
        updateView();
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

        return priceString.append(' ').toString();
    }


    public void onCloseButtonClicked() {
        view().dismiss();
    }
}
