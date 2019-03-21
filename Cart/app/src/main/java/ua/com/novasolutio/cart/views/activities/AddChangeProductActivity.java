package ua.com.novasolutio.cart.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ua.com.novasolutio.cart.R;
import ua.com.novasolutio.cart.presenters.AddChangeProductActivityPresenter;
import ua.com.novasolutio.cart.presenters.PresenterManager;
import ua.com.novasolutio.cart.views.ProductView;

/** Екран для редагування/додавання нових об'єктів "Product" */
public class AddChangeProductActivity extends AppCompatActivity implements ProductView {
    private static final String TAG = "AddChangeProdActivity";
    private Toolbar mToolbar;
    private AddChangeProductActivityPresenter mPresenter;
    private AppCompatEditText mProductCaption, mProductPrice;
    public static final String INTENT_CODE = "INTENT_CODE_FOR_GETTING_MODEL";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_change_product);

        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        // ініціалізація/відновлення презентера
        if(savedInstanceState == null) {
            mPresenter = new AddChangeProductActivityPresenter();
        } else {
            mPresenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }

        // ініціалізація тулбара
        mToolbar = findViewById(R.id.toolbar_add_change_product_activity);
        setSupportActionBar(mToolbar);

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

        mProductCaption = findViewById(R.id.tiet_product_caption);
        // обробка тексту при зміні фокусу з View та передача результату в презентер
        mProductCaption.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    Log.i(TAG, "onFocusChange: CAPTION - " + mProductCaption.getText());
                    mPresenter.changeProductCaption(String.valueOf(mProductCaption.getText()));
                }
            }
        });
        mProductPrice = findViewById(R.id.tiet_product_price);
        // обробка тексту при зміні фокусу з View та передача результату в презентер
        mProductPrice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    double formattedPrice = Double.valueOf(mProductPrice.getText().toString());
                    int price = (int) (formattedPrice*100);
                    mPresenter.changeProductPrice(price);
                }
            }
        });

        // зчитування Intent та передача даних в презентер для відповідного завантаження даних
        Intent intent = getIntent();
        int productId = intent.getIntExtra(AddChangeProductActivity.INTENT_CODE, -1);
        mPresenter.loadModel(productId);
    }

    @Override
    protected void onResume() {
        mPresenter.bindView(this);
        super.onResume();
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
    protected void onPause() {
        super.onPause();
        mPresenter.unbindView();
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
