package ua.com.novasolutio.cart.presentation.view;

import java.util.List;

import ua.com.novasolutio.cart.model.data.Payment;

public interface PaymentsListReport {
    void showPaymentsList(List<Payment> payments);
}
