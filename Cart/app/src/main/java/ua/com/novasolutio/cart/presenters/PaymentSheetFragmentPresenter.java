package ua.com.novasolutio.cart.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import ua.com.novasolutio.cart.data.Payment;
import ua.com.novasolutio.cart.data.ProductListManager;
import ua.com.novasolutio.cart.views.fragments.PaymentSheetFragment;

public class PaymentSheetFragmentPresenter extends BasePresenter<Payment, PaymentSheetFragment> {
    public static final String TAG = "PaySheetFragPresenter";


    // Перерахування можливих купюр
    public enum Bills {
        TEN,
        TWENTY,
        FIFTY,
        ONE_HUNDRED,
        TWO_HUNDRED,
        FIVE_HUNDRED
    }

    public enum KeyboardButtons{
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        NULL,
        DOT,
        CANCEL
    }

    private long totalPrice = 0L; // змінна відображає загальну ціну продажі

    private long currentCash = 0L; // змінна відображає суму, що дав покупець


    @Override
    protected void updateView() {
        long currentChange = currentCash - totalPrice; // перерахунок решти для покупця

        view().updateUI(formatPriceOnText(currentCash),
                currentChange >= 0L ? formatPriceOnText(currentChange) : "0.00 ");

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

    // метод для прорахунку додавання купюр до загальної суми розрахунку
    public void onPaymentButtonBillClicked(Bills bills) {

        switch (bills) {
            case TEN:
                currentCash = currentCash + 1000;
                break;
            case TWENTY:
                currentCash = currentCash + 2000;
                break;
            case FIFTY:
                currentCash = currentCash + 5000;
                break;
            case ONE_HUNDRED:
                currentCash = currentCash + 10000;
                break;
            case TWO_HUNDRED:
                currentCash = currentCash + 20000;
                break;
            case FIVE_HUNDRED:
                currentCash = currentCash + 50000;
                break;
        }

        updateView();
    }

    // очищення поля введення суми
    public void onKeyboardButtonClicked(KeyboardButtons buttons) {
        switch (buttons) {
            case ONE:
                break;
            case TWO:
                break;
            case THREE:
                break;
            case FOUR:
                break;
            case FIVE:
                break;
            case SIX:
                break;
            case SEVEN:
                break;
            case EIGHT:
                break;
            case NINE:
                break;
            case DOT:
                break;
            case NULL:
                break;
            case CANCEL:
                currentCash = 0;
                break;
        }

        updateView();
    }


}
