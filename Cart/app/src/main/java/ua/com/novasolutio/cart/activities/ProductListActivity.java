package ua.com.novasolutio.cart.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ua.com.novasolutio.cart.R;
import ua.com.novasolutio.cart.presenters.ProductListPresenter;

/* Activity для відображення користувачу списку товарів, які можна додати до корзини покупок,
 * також в цій активності можна додавати нові товари*/
public class ProductListActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ProductListPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        init();
    }

    private void init() {
        mToolbar = findViewById(R.id.toolbar_product_list_activity);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        getSupportActionBar().setTitle(getResources().getString(
                        R.string.product_list_activity_caption));

        mPresenter = new ProductListPresenter();

    }


}
