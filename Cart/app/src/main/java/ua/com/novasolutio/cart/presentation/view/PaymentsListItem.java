package ua.com.novasolutio.cart.presentation.view;

import ua.com.novasolutio.cart.model.data.Payment;
import ua.com.novasolutio.cart.ui.activity.payment.PaymentReportActivity;

public interface PaymentsListItem {

    void showPayment(Payment model);

    void startPaymentReportActivity(Class<PaymentReportActivity> paymentReportActivityClass, int id);
}
