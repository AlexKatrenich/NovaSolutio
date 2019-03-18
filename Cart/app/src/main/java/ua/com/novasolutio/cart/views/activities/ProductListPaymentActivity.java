package ua.com.novasolutio.cart.views.activities;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuItem;
import ua.com.novasolutio.cart.R;
import ua.com.novasolutio.cart.views.fragments.CartFragment;
import ua.com.novasolutio.cart.views.fragments.ProductListFragment;

/* Activity для відображення користувачу списку товарів, які можна додати до корзини покупок,
 * також в цій активності можна додавати нові товари*/
public class ProductListPaymentActivity extends AppCompatActivity {
    private static final String TAG = "ProdListPaymentActivity";
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
            case R.id.settings_item_menu :
                Log.i(TAG, "onOptionsItemSelected: Settings");
                return true;
            case R.id.sorting_item_menu :
                Log.i(TAG, "onOptionsItemSelected: Sorting");
                return true;
            case R.id.add_new_product_item_menu :
                Log.i(TAG, "onOptionsItemSelected: Add new product");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
