package ua.com.novasolutio.cart.views.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ua.com.novasolutio.cart.R;

/** Екран для редагування/додавання нових об'єктів "Product" */
public class AddChangeProductActivity extends AppCompatActivity {
    private static final String TAG = "AddChangeProdActivity";
    private Toolbar mToolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_change_product);

        init();
    }

    private void init() {
        mToolbar = findViewById(R.id.toolbar_add_change_product_activity);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }



}
