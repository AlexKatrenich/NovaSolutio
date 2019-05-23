package ua.com.novasolutio.cart;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.facebook.stetho.Stetho;

import ua.com.novasolutio.cart.model.dao.CartDatabase;
import ua.com.novasolutio.cart.model.data.CurrencyManager;

public class CartApplication extends Application {
    private static CartApplication instance;

    private CartDatabase mDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        mDatabase = Room
                .databaseBuilder(this, CartDatabase.class, "CartDatabase")
                .build();
        Stetho.initializeWithDefaults(this);
    }

    public static CartApplication getInstance() {
        return instance;
    }

    public CartDatabase getDatabase(){
        return mDatabase;
    }
}
