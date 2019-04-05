package ua.com.novasolutio.cart.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.com.novasolutio.cart.R;

// клас для відображення фрагменту проведення оплати в корзині
public class PaymentSheetFragment extends BottomSheetDialogFragment {
    public PaymentSheetFragment() {
        // потрібен пустий конструктор
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_payment_calculator, container, false);
    }
}
