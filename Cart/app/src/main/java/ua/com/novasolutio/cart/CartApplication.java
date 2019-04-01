package ua.com.novasolutio.cart;

import android.app.Application;
import android.arch.persistence.room.Room;

import ua.com.novasolutio.cart.dao.CartDatabase;

public class CartApplication extends Application {
    private static CartApplication instance;

    private CartDatabase mDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        mDatabase = Room.databaseBuilder(this, CartDatabase.class, "CartDatabase")
                .build();

    }

    public static CartApplication getInstance() {
        return instance;
    }

    public CartDatabase getDatabase(){
        return mDatabase;
    }
}
