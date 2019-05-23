package ua.com.novasolutio.cart.ui.view_holder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.com.novasolutio.cart.R;
import ua.com.novasolutio.cart.model.data.CurrencyManager;
import ua.com.novasolutio.cart.model.data.Payment;
import ua.com.novasolutio.cart.presentation.presenter.payments_report.PaymentReportViewHolderPresenter;
import ua.com.novasolutio.cart.presentation.view.PaymentsListItem;
import ua.com.novasolutio.cart.ui.activity.payment.PaymentReportActivity;

public class PaymentReportViewHolder extends MvpViewHolder<PaymentReportViewHolderPresenter>
        implements PaymentsListItem {

    public static final String TAG = "PaymentReportVH";

    @BindView(R.id.tv_payment_list_item_number)
    protected TextView paymentNumber;

    @BindView(R.id.tv_payment_list_item_date)
    protected TextView paymentDate;

    @BindView(R.id.tv_payment_list_item_total_price)
    protected TextView paymentPrice;

    private Context mContext;

    public PaymentReportViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(v -> presenter.clickOnItem());
        mContext = itemView.getContext();
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
        String currency = CurrencyManager.getInstance().getCurrencyName();
        priceString.append(' ').append(currency).append(' ');

        return priceString.toString();
    }


    @Override
    public void showPayment(Payment payment) {
        paymentNumber.setText(String.valueOf(payment.getId()));

        Date time = new Date(payment.getPaymentDate());
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault());
        format.setTimeZone(TimeZone.getDefault());
        paymentDate.setText(format.format(time));

        paymentPrice.setText(formatPrice(payment.getTotalPrice()));
    }

    @Override
    public void startPaymentReportActivity(Class<PaymentReportActivity> paymentReportActivityClass, int id) {
        Intent intent = new Intent(mContext, paymentReportActivityClass);
        intent.putExtra(PaymentReportActivity.PAYMENT_ID_TAG, id);
        mContext.startActivity(intent);
    }
}
