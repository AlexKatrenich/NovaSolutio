package ua.com.novasolutio.cart.views.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.com.novasolutio.cart.R;
import ua.com.novasolutio.cart.presenters.AddChangeProductActivityPresenter;
import ua.com.novasolutio.cart.presenters.PresenterManager;
import ua.com.novasolutio.cart.views.ProductView;

/** Екран для редагування/додавання нових об'єктів "Product" */
public class AddChangeProductActivity extends AppCompatActivity implements ProductView {
    private static final String TAG = "AddChangeProdActivity";

    @BindView(R.id.til_product_caption_add_activity) protected TextInputLayout tilProductCaption;
    @BindView(R.id.til_product_price_add_activity) protected TextInputLayout tilProductPrice;
    @BindView(R.id.toolbar_add_change_product_activity) protected Toolbar mToolbar;
    @BindView(R.id.tiet_product_caption) protected AppCompatEditText mProductCaption;
    @BindView(R.id.tiet_product_price) protected AppCompatAutoCompleteTextView mProductPrice;
    private AddChangeProductActivityPresenter mPresenter;


    public static final String INTENT_CODE_FOR_GETTING_MODEL = "INTENT_CODE_FOR_GETTING_MODEL";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_change_product);

        ButterKnife.bind(this); // Байндинг елементів
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        // ініціалізація/відновлення презентера
        if(savedInstanceState == null) {
            mPresenter = new AddChangeProductActivityPresenter();
        } else {
            mPresenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }

        setSupportActionBar(mToolbar); //встановлення власного Тулбару за замовчуванням

        //встановлення кнопки "Back та відмова від Титульної назви екрану"
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // встановлення слухача по натисненню на конпку тулбара "back"
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // обробка тексту при зміні фокусу з View та передача результату в презентер
        mProductCaption.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    Log.i(TAG, "onFocusChange: CAPTION - " + mProductCaption.getText());
                    if (!mPresenter.changeProductCaption(String.valueOf(mProductCaption.getText()))){
                        tilProductCaption.setErrorEnabled(true);
                        tilProductCaption.setError(getResources().getText(R.string.incorrect_text_input));
                    } else {
                        tilProductCaption.setError(null);
                        tilProductCaption.setErrorEnabled(false);
                    }
                }
            }
        });

        // обробка тексту при зміні фокусу з View та передача результату в презентер
        mProductPrice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(!mPresenter.changeProductPrice(mProductPrice.getText().toString())){
                        tilProductPrice.setErrorEnabled(true);
                        tilProductPrice.setError(getResources().getText(R.string.incorrect_text_input));
                    } else {
                        tilProductPrice.setError(null);
                        tilProductPrice.setErrorEnabled(false);
                    }
                }
            }
        });

    }

    @Override
    protected void onStart() {
        mPresenter.bindView(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.unbindView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            getMenuInflater().inflate(R.menu.product_add_change_activity_menu, menu);
            Log.i(TAG, "onCreateOptionsMenu: Menu created");
        } catch (InflateException e){
            Log.e(TAG, "onCreateOptionsMenu: " + e.getMessage());
            return false;
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.item_save_add_change_product:
                Log.i(TAG, "onOptionsItemSelected: SAVE CHANGES ON PRODUCT");
                // зміна фокусу по всим EditText для передачі даних в Presenter
                mProductPrice.clearFocus();
                mProductCaption.clearFocus();
                mPresenter.OnSaveButtonClicked();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        if (mToolbar != null) mToolbar.setNavigationOnClickListener(null);
        setSupportActionBar(null);

        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(mPresenter, outState);
    }

    @Override
    public void setProductCaption(String caption) {
        mProductCaption.setText(caption);
    }

    @Override
    public void setProductPrice(String price) {
        mProductPrice.setText(price);
    }
}
