package ua.com.novasolutio.cart.views.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ua.com.novasolutio.cart.R;

/** Екран для редагування/додавання нових об'єктів "Product" */
public class AddChangeProductActivity extends AppCompatActivity {
    private Toolbar mToolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_change_product);

        init();
    }


    private void init() {

    }

}
