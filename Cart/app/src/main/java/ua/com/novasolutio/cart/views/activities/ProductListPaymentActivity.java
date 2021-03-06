package ua.com.novasolutio.cart.views.activities;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.com.novasolutio.cart.R;
import ua.com.novasolutio.cart.presenters.PresenterManager;
import ua.com.novasolutio.cart.presenters.ProductListPaymentActivityPresenter;
import ua.com.novasolutio.cart.views.fragments.PaymentSheetFragment;
import ua.com.novasolutio.cart.views.fragments.ProductListFragment;

/* Activity для відображення користувачу списку товарів, які можна додати до корзини покупок,
 * також в цій активності можна додавати нові товари*/
public class ProductListPaymentActivity extends AppCompatActivity {
    private static final String TAG = "ProdListPaymentActivity";
    private ProductListPaymentActivityPresenter mPresenter;

    @BindView(R.id.toolbar_product_list_activity)
    protected Toolbar mToolbar;

    @BindView(R.id.bnv_list_products_activity)
    protected BottomNavigationView mNavigationView;

    @BindView(R.id.btn_payment_button)
    protected Button btnPayment;

    private int bnvSelectedItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        ButterKnife.bind(this);
        init(savedInstanceState);

        if (savedInstanceState == null) bindFragment(new ProductListFragment());
    }

    @Override
    protected void onStart() {
        mPresenter.bindView(this);
        super.onStart();
    }

    @Override
    protected void onResume() {
        if(bnvSelectedItemId != 0 ){
            mNavigationView.setSelectedItemId(bnvSelectedItemId);
        }
        super.onResume();
    }

    /* Ініціалізація елементів Активності*/
    private void init(Bundle savedInstanceState) {

        if(savedInstanceState == null){
            mPresenter = new ProductListPaymentActivityPresenter();
        } else {
            mPresenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
            bnvSelectedItemId = savedInstanceState.getInt(TAG_SAVE_INSTANCE_STATE_BOTTOM_NAVIGATION);
            Log.i(TAG, "onCreate: ID = " + bnvSelectedItemId);
        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        getSupportActionBar().setTitle(getResources().getString(
                        R.string.product_list_activity_caption));

        /*Ініціація нижнього меню навігації між фрагментами*/
        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.item_list :
                        // передача презентеру обробки натистення на елемент ItemList
                        mPresenter.onProductListFragmentClicked();
                        return true;

                    case R.id.item_cart :
                        // передача презентеру обробки натистення на елемент ItemCart
                        mPresenter.onCartFragmentClicked();
                        return true;
                }
                return false;
            }
        });
    }

    /* Закріплення та відображення фрагментів в активності */
    public void bindFragment(Fragment fragment) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_products_activity, fragment)
                    .commit();
    }

    @Override
    protected void onStop() {
        mPresenter.unbindView();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        setSupportActionBar(null);
        mNavigationView.setOnNavigationItemSelectedListener(null);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            getMenuInflater().inflate(R.menu.product_list_activity_main_menu, menu);
        } catch (InflateException e){
            Log.e(TAG, "onCreateOptionsMenu: " + e.getMessage());
            return false;
        }

        return true;
    }

    private MenuItem sortMenuItem;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item_settings_menu :
                Log.i(TAG, "onOptionsItemSelected: Settings");
                return true;
            case R.id.item_sorting_menu :
                sortMenuItem = item;
                Log.i(TAG, "onOptionsItemSelected: Sorting");
                return true;
            case R.id.item_add_new_product_menu:
                /*Презентер оброблює подію згідно логіки додатку*/
                mPresenter.addNewProductMenuClicked();
                Log.i(TAG, "onOptionsItemSelected: Add new product");
                return true;

            case R.id.item_sort_ascending_caption :
                mPresenter.onSortItemClicked(ProductListPaymentActivityPresenter.SortingState.CAPTION_ASCENDING);
                Log.i(TAG, "onOptionsItemSelected SORTING BY ASCENDING CAPTION");
                return true;

            case R.id.item_sort_descending_caption :
                mPresenter.onSortItemClicked(ProductListPaymentActivityPresenter.SortingState.CAPTION_DESCENDING);
                Log.i(TAG, "onOptionsItemSelected: SORTING BY DESCENDING CAPTION");
                return true;

            case R.id.item_sort_ascending_price :
                mPresenter.onSortItemClicked(ProductListPaymentActivityPresenter.SortingState.PRICE_ASCENDING);
                Log.i(TAG, "onOptionsItemSelected: SORTING BY ASCENDING PRICE");
                return true;

            case R.id.item_sort_descending_price :
                mPresenter.onSortItemClicked(ProductListPaymentActivityPresenter.SortingState.PRICE_DESCENDING);
                Log.i(TAG, "onOptionsItemSelected: SORTING BY DESCENDING PRICE");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private final String TAG_SAVE_INSTANCE_STATE_BOTTOM_NAVIGATION = "TAG_SAVE_INSTANCE_STATE_BOTTOM_NAVIGATION";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(mPresenter, outState);
        int id = mNavigationView.getSelectedItemId();
        Log.i(TAG, "onSaveInstanceState: ID = " + id);
        outState.putInt(TAG_SAVE_INSTANCE_STATE_BOTTOM_NAVIGATION, id);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.item_sorting_menu);
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container_products_activity);
        try {
            ProductListFragment plFragment = (ProductListFragment)currentFragment;
            item.setVisible(false);
            Log.i(TAG, "onPrepareOptionsMenu: CurrentFragment IS " + plFragment.getClass().getSimpleName());

        } catch (ClassCastException e){
            Log.i(TAG, "onPrepareOptionsMenu: CurrentFragment IS " + currentFragment.getClass().getSimpleName());
            item.setVisible(true);
            return super.onPrepareOptionsMenu(menu);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @OnClick(R.id.btn_payment_button)
    public void onPaymentClick(){
        Log.i(TAG, "onPaymentClick: ");
        mPresenter.onPaymentClicked();
    }

    // метод для зміни розміру кнопки виклику фрагменту/діалогу оплати
    public void changeSizePaymentButton(boolean visible) {
        Log.i(TAG, "changeSizePaymentButton FULLSIZE: " + String.valueOf(visible));
        if (visible){
            btnPayment.setVisibility(View.VISIBLE);
        } else {
            btnPayment.setVisibility(View.GONE);
        }
    }

    // метод викликає діалог оплати товарів в корзині
    public void showPaymentDialog() {
        PaymentSheetFragment paymentDialog = new PaymentSheetFragment();
        paymentDialog.show(getSupportFragmentManager(), paymentDialog.getTag());
        Log.i(TAG, "showPaymentDialog: ");
    }

    // метод для зміни в тулбарі іконки сортування
    public void changeSortIcon(boolean ascending){
        if (sortMenuItem != null) {
            sortMenuItem.setIcon(ResourcesCompat.getDrawable(
                    getResources(),
                    ascending == true ?
                            R.drawable.ic_sort_menu_item_ascending : R.drawable.ic_sort_menu_item_descending,
                    null));
        }
    }

    public ProductListPaymentActivityPresenter getPresenter() {
        return mPresenter;
    }
}
