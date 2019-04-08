package ua.com.novasolutio.cart.views.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.com.novasolutio.cart.R;
import ua.com.novasolutio.cart.presenters.PaymentSheetFragmentPresenter;
import ua.com.novasolutio.cart.presenters.PresenterManager;

// клас для відображення фрагменту проведення оплати в корзині
public class PaymentSheetFragment extends BottomSheetDialogFragment {
    private static final String TAG = "PaymentSheetFragment";
    @BindView(R.id.tv_payment_sheet_customer_cash)
    protected TextView mCash; // текстове поле для відображення готівки, яку дав покупець

    @BindView(R.id.tv_payment_sheet_customer_change)
    protected TextView mChange; // текстове поле для відображення готівки(здачі), яку повинен віддати касир покупцю

    private PaymentSheetFragmentPresenter mPresenter;

    public PaymentSheetFragment() {
        // потрібен пустий конструктор
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // задаємо розміри відкриття діалогу(щоб відкривався в повному розмірі)
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog d = (BottomSheetDialog) dialog;

                FrameLayout bottomSheet = (FrameLayout) d.findViewById(android.support.design.R.id.design_bottom_sheet);
                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        return dialog;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        if(savedInstanceState == null){
            mPresenter = new PaymentSheetFragmentPresenter();
        } else {
            mPresenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }

    }

    @Override
    public void onStart() {
        mPresenter.bindView(this);
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    public void onStop() {
        mPresenter.unbindView();
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    // метод для відображення на екрані готівки та решти по платежу
    public void updateUI(String currentCash, String currentChange){

        Log.i(TAG, String.format("updateUI CASH: %s, CHANGE: %s ", currentCash, currentChange));

        if (currentCash != null && currentCash != ""){
            mCash.setText(currentCash);
        }

        if(currentChange != null && currentChange != ""){
            mChange.setText(currentChange);
        }


    }

    @OnClick(R.id.ib_payment_sheet_button_close)
    public void onClosePaymentFragmentButtonClicked(){
        mPresenter.onCloseButtonClicked();
        Log.i(TAG, "onClosePaymentFragmentButtonClicked: ");
    }
}
