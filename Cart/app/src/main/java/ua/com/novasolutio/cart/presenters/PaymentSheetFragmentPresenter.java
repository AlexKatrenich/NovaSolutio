package ua.com.novasolutio.cart.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ua.com.novasolutio.cart.R;
import ua.com.novasolutio.cart.data.Payment;
import ua.com.novasolutio.cart.data.Product;
import ua.com.novasolutio.cart.data.ProductListManager;
import ua.com.novasolutio.cart.views.fragments.PaymentSheetFragment;

public class PaymentSheetFragmentPresenter extends BasePresenter<Payment, PaymentSheetFragment> {
    public static final String TAG = "PaySheetFragPresenter";
    private boolean userInput = false;

    // Перерахування можливих купюр
    public enum Bills {
        TEN,
        TWENTY,
        FIFTY,
        ONE_HUNDRED,
        TWO_HUNDRED,
        FIVE_HUNDRED
    }

    // перерахування кнопок власної клавіатури
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

        view().updateUiChange(currentChange >= 0L ? formatPriceOnText(currentChange) : "0.00");
        view().updateUiCash(formatPriceOnText(currentCash));
        Log.i(TAG, "updateView Cash: " + currentCash + " CHANGE: " + currentChange);
    }

    @Override
    public void bindView(@NonNull PaymentSheetFragment view) {
        totalPrice = ProductListManager.getInstance().getTotalPriceSelectedProducts();
        currentCash = totalPrice;
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

    // закриття фрагменту, коли користувач натискає на кнопку відміни
    public void onCloseButtonClicked() {
        view().dismiss();
    }

    // метод для прорахунку додавання купюр до загальної суми розрахунку
    public void onPaymentButtonBillClicked(Bills bills) {
        if(!userInput) {
            currentCash = 0;
            userInput = true;
        }

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
        Log.i(TAG, "onPaymentButtonBillClicked: ");
    }

    // очищення поля введення суми
    public void onKeyboardButtonClicked(KeyboardButtons buttons, String stringValue) {
        if(!userInput) {
            currentCash = 0;
            userInput = true;
            stringValue = "";
        }

        switch (buttons) {
            case ONE:
                addNumberToCash("1", stringValue);
                break;
            case TWO:
                addNumberToCash("2", stringValue);
                break;
            case THREE:
                addNumberToCash("3", stringValue);
                break;
            case FOUR:
                addNumberToCash("4", stringValue);
                break;
            case FIVE:
                addNumberToCash("5", stringValue);
                break;
            case SIX:
                addNumberToCash("6", stringValue);
                break;
            case SEVEN:
                addNumberToCash("7", stringValue);
                break;
            case EIGHT:
                addNumberToCash("8", stringValue);
                break;
            case NINE:
                addNumberToCash("9", stringValue);
                break;
            case DOT:
                view().updateUiCash(stringValue + ".");
                break;
            case NULL:
                addNumberToCash("0", stringValue);
                break;
            case CANCEL:
                currentCash = 0;
                updateView();
                break;
        }
        Log.i(TAG, "onKeyboardButtonClicked: ");
    }

    // метод для форматування вхідних даних зі штучної клавіатури
    private void addNumberToCash(String numb, String stringValue) {
        stringValue = stringValue + numb;
        double priceDouble;

        try {
            priceDouble = Double.valueOf(stringValue);
            view().updateUiCash(stringValue);
        } catch (NumberFormatException e){
            priceDouble = Double.valueOf(numb);
            view().updateUiCash(numb);
        }

        currentCash = Math.round(priceDouble * new Double(100));
        long currentChange = currentCash - totalPrice;
        view().updateUiChange(currentChange >= 0L ? formatPriceOnText(currentChange) : "0.00");

        Log.i(TAG, "addNumberToCash CASH: " + currentCash + " CURRENT_CHANGE: " + currentChange);
    }

    public void onPaymentButtonClicked(String cash) {
        double priceDouble = Double.valueOf(cash);
        currentCash = Math.round(priceDouble * new Double(100));
        long currentChange = currentCash - totalPrice;

        // перевірка на достатню величину введеної суми платежу
        if (currentCash >= totalPrice){
            // створення нового об'єкту платежу
            Payment payment = new Payment();
            payment.setTotalPrice(currentCash);
            payment.setChange(currentChange);

            // внесення до платежу переліку продуктів
            List<Product> products = ProductListManager.getInstance().getProductsList();
            List<Product> selectedProducts = new ArrayList<>();

            for (Product p : products) {
                if(p.getCount() > 0){
                    selectedProducts.add(p);
                    p.setCount(0);
                    ProductListManager.getInstance().setProductById(p, p.getID()); // обнулення переліку обраних продуктів в БД
                }
            }

            payment.setProducts(selectedProducts);
            Date currentTime = Calendar.getInstance().getTime();
            payment.setPaymentDate(currentTime.getTime());

            // TODO запис до БД платежу

            Log.i(TAG, "onPaymentButtonClicked: " + payment);
            view().dismiss();
        } else {
            String message = view().getResources().getString(R.string.incorrect_cash_value);
            view().showMessage(message);
        }
    }
}
