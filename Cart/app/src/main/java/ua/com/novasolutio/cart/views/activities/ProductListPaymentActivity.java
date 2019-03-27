package ua.com.novasolutio.cart.views.activities;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuItem;
import ua.com.novasolutio.cart.R;
import ua.com.novasolutio.cart.mock.MockDB;
import ua.com.novasolutio.cart.presenters.PresenterManager;
import ua.com.novasolutio.cart.presenters.ProductListPaymentActivityPresenter;
import ua.com.novasolutio.cart.views.fragments.ProductListFragment;

/* Activity для відображення користувачу списку товарів, які можна додати до корзини покупок,
 * також в цій активності можна додавати нові товари*/
public class ProductListPaymentActivity extends AppCompatActivity {
    private static final String TAG = "ProdListPaymentActivity";
    private Toolbar mToolbar;
    private BottomNavigationView mNavigationView;
    private ProductListPaymentActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        init(savedInstanceState);

        // відображення першого фрагменту при відкритті екрану
        bindFragment(new ProductListFragment());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.bindView(this);
    }

    /* Ініціалізація елементів Активності*/
    private void init(Bundle savedInstanceState) {

        if(savedInstanceState == null){
            mPresenter = new ProductListPaymentActivityPresenter();
        } else {
            mPresenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }

        mToolbar = findViewById(R.id.toolbar_product_list_activity);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        getSupportActionBar().setTitle(getResources().getString(
                        R.string.product_list_activity_caption));

        /*Ініціація нижнього меню навігації між фрагментами*/
        mNavigationView = findViewById(R.id.bnv_list_products_activity);

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
        mNavigationView.setItemIconTintList(null);
        setSupportActionBar(null);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item_settings_menu :
                Log.i(TAG, "onOptionsItemSelected: Settings");
                return true;
            case R.id.item_sorting_menu :
                Log.i(TAG, "onOptionsItemSelected: Sorting");
                return true;
            case R.id.item_add_new_product_menu:
                /*Презентер оброблює подію згідно логіки додатку*/
                mPresenter.addNewProductMenuClicked();
                Log.i(TAG, "onOptionsItemSelected: Add new product");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(mPresenter, outState);
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


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks whether a hardware keyboard is available
        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
            Log.i(TAG, "onConfigurationChanged: KEYBOARD VISIBLE");
        } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
            Log.i(TAG, "onConfigurationChanged: KEYBOARD INVISIBLE");
        }
    }
}
