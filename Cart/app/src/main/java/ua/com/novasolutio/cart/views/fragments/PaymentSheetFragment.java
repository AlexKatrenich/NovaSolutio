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
import android.widget.Toast;

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
    public void updateUiCash(String currentCash){
        Log.i(TAG, String.format("updateUI CASH: %s", currentCash));
        if (currentCash != null && currentCash != ""){
            mCash.setText(currentCash);
        }

    }

    public void updateUiChange(String currentChange){
        if(currentChange != null && currentChange != ""){
            mChange.setText(currentChange);
        }
    }

    @OnClick(R.id.ib_payment_sheet_button_close)
    public void onClosePaymentFragmentButtonClicked(){
        mPresenter.onCloseButtonClicked();
        Log.i(TAG, "onClosePaymentFragmentButtonClicked: ");
    }

    /* Обробка кліків по кнопкам додавання покупюрного додавання до суми сплати*/
    @OnClick(R.id.btn_payment_bill_first)
    public void onPaymentButtonBillClicked(){
        mPresenter.onPaymentButtonBillClicked(PaymentSheetFragmentPresenter.Bills.TEN);
    }

    @OnClick(R.id.btn_payment_bill_second)
    public void onPaymentButtonBillSecondClicked(){
        mPresenter.onPaymentButtonBillClicked(PaymentSheetFragmentPresenter.Bills.TWENTY);
    }

    @OnClick(R.id.btn_payment_bill_third)
    public void onPaymentButtonBillThirdClicked(){
        mPresenter.onPaymentButtonBillClicked(PaymentSheetFragmentPresenter.Bills.FIFTY);
    }

    @OnClick(R.id.btn_payment_bill_fourth)
    public void onPaymentButtonBillFourthClicked(){
        mPresenter.onPaymentButtonBillClicked(PaymentSheetFragmentPresenter.Bills.ONE_HUNDRED);
    }

    @OnClick(R.id.btn_payment_bill_fifth)
    public void onPaymentButtonBillFifthClicked(){
        mPresenter.onPaymentButtonBillClicked(PaymentSheetFragmentPresenter.Bills.TWO_HUNDRED);
    }

    @OnClick(R.id.btn_payment_bill_sixth)
    public void onPaymentButtonBillSixthClicked(){
        mPresenter.onPaymentButtonBillClicked(PaymentSheetFragmentPresenter.Bills.FIVE_HUNDRED);
    }
    /* -------------------------------------------------------------------------------------------- */

    /* Обробка кліків по кнопкам почислового додавання до суми сплати*/
    @OnClick(R.id.btn_payment_numb_one)
    public void onButtonkeyboardOneClicked(){
        mPresenter.onKeyboardButtonClicked(PaymentSheetFragmentPresenter.KeyboardButtons.ONE, String.valueOf(mCash.getText()));
    }

    @OnClick(R.id.btn_payment_numb_two)
    public void onButtonkeyboardTwoClicked(){
        mPresenter.onKeyboardButtonClicked(PaymentSheetFragmentPresenter.KeyboardButtons.TWO, String.valueOf(mCash.getText()));
    }

    @OnClick(R.id.btn_payment_numb_three)
    public void onButtonkeyboardThreeClicked(){
        mPresenter.onKeyboardButtonClicked(PaymentSheetFragmentPresenter.KeyboardButtons.THREE, String.valueOf(mCash.getText()));
    }

    @OnClick(R.id.btn_payment_numb_four)
    public void onButtonkeyboardFourClicked(){
        mPresenter.onKeyboardButtonClicked(PaymentSheetFragmentPresenter.KeyboardButtons.FOUR, String.valueOf(mCash.getText()));
    }

    @OnClick(R.id.btn_payment_numb_five)
    public void onButtonkeyboardFiveClicked(){
        mPresenter.onKeyboardButtonClicked(PaymentSheetFragmentPresenter.KeyboardButtons.FIVE, String.valueOf(mCash.getText()));
    }

    @OnClick(R.id.btn_payment_numb_six)
    public void onButtonkeyboardSixClicked(){
        mPresenter.onKeyboardButtonClicked(PaymentSheetFragmentPresenter.KeyboardButtons.SIX, String.valueOf(mCash.getText()));
    }

    @OnClick(R.id.btn_payment_numb_seven)
    public void onButtonkeyboardSevenClicked(){
        mPresenter.onKeyboardButtonClicked(PaymentSheetFragmentPresenter.KeyboardButtons.SEVEN, String.valueOf(mCash.getText()));
    }

    @OnClick(R.id.btn_payment_numb_eight)
    public void onButtonkeyboardEightClicked(){
        mPresenter.onKeyboardButtonClicked(PaymentSheetFragmentPresenter.KeyboardButtons.EIGHT, String.valueOf(mCash.getText()));
    }

    @OnClick(R.id.btn_payment_numb_nine)
    public void onButtonkeyboardNineClicked(){
        mPresenter.onKeyboardButtonClicked(PaymentSheetFragmentPresenter.KeyboardButtons.NINE, String.valueOf(mCash.getText()));
    }

    @OnClick(R.id.btn_payment_numb_null)
    public void onButtonkeyboardNullClicked(){
        mPresenter.onKeyboardButtonClicked(PaymentSheetFragmentPresenter.KeyboardButtons.NULL, String.valueOf(mCash.getText()));
    }

    @OnClick(R.id.btn_payment_numb_dot)
    public void onButtonkeyboardDotClicked(){
        mPresenter.onKeyboardButtonClicked(PaymentSheetFragmentPresenter.KeyboardButtons.DOT, String.valueOf(mCash.getText()));
    }

    @OnClick(R.id.btn_payment_numb_cancel)
    public void onPaymentClearButtonClicked(){
        mPresenter.onKeyboardButtonClicked(PaymentSheetFragmentPresenter.KeyboardButtons.CANCEL, String.valueOf(mCash.getText()));
    }
    /* ------------------------------------------------------------------------------------------ */

    @OnClick(R.id.btn_main_make_payment)
    public void onPaymentButtonClicked(){
        mPresenter.onPaymentButtonClicked(String.valueOf(mCash.getText()));
    }

    public void showMessage(String message){
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }
}
