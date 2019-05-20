package ua.com.novasolutio.cart.ui.view_holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.com.novasolutio.cart.R;
import ua.com.novasolutio.cart.model.data.Payment;

public class PaymentReportViewHolder extends RecyclerView.ViewHolder {
    public static final String TAG = "PaymentReportVH";

    @BindView(R.id.tv_payment_list_item_number)
    protected TextView paymentNumber;

    @BindView(R.id.tv_payment_list_item_date)
    protected TextView paymentDate;

    @BindView(R.id.tv_payment_list_item_total_price)
    protected TextView paymentPrice;

    public PaymentReportViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(v -> {
            Toast.makeText(itemView.getContext(), "Click on element: ", Toast.LENGTH_SHORT).show();
        });
    }

    public void bind(Payment payment){
        Log.i(TAG, "bind: Payment:" + payment);
        paymentNumber.setText(String.valueOf(payment.getId()));

        Date time = new Date(payment.getPaymentDate());
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault());
        format.setTimeZone(TimeZone.getDefault());
        paymentDate.setText(format.format(time));

        paymentPrice.setText(formatPrice(payment.getTotalPrice()));
    }

    private String formatPrice(long l){
        StringBuffer priceString = new StringBuffer(String.valueOf(l));
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

        // додавання назви грошових одиниць до відображення ціни на екрані
        //TODO додати можливість задавати валюту через екран, зберігати через SharedPreference.
        String currency = "UAH";
        priceString.append(' ').append(currency).append(' ');

        return priceString.toString();
    }


}
