package ua.com.novasolutio.cart.activities;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import ua.com.novasolutio.cart.R;
import ua.com.novasolutio.cart.fragments.CartFragment;
import ua.com.novasolutio.cart.fragments.ProductListFragment;
import ua.com.novasolutio.cart.presenters.ProductListFragmentPresenter;

/* Activity для відображення користувачу списку товарів, які можна додати до корзини покупок,
 * також в цій активності можна додавати нові товари*/
public class ProductListPaymentActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private BottomNavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        init();

        attachFragments(savedInstanceState);
    }

    /* Ініціалізація елементів Активності*/
    private void init() {
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
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container_products_activity, new ProductListFragment())
                                .addToBackStack(null)
                                .commit();
                        return true;

                    case R.id.item_cart :
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container_products_activity, new CartFragment())
                                .addToBackStack(null)
                                .commit();
                        return true;
                }
                return false;
            }
        });

    }

    /* Закріплення фрагментів до активності */
    private void attachFragments(Bundle savedInstanceState) {
        if (savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container_products_activity, new ProductListFragment())
                    .commit();
        }

    }


    @Override
    protected void onDestroy() {
        mNavigationView.setItemIconTintList(null);
        setSupportActionBar(null);

        super.onDestroy();
    }
}
