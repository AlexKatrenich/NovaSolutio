package ua.com.novasolutio.cart.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

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

        ((TextView) findViewById(R.id.tv_search_on_list_products)).setText(" SEARCH ON APP ");
        ((TextView) findViewById(R.id.tv_total_caption_product_list_activity)).setText(" TOTAL:");
        ((TextView) findViewById(R.id.tv_total_price_product_list_activity)).setText("200,00 UAH");


    }


}
