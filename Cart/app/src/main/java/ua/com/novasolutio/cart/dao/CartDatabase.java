package ua.com.novasolutio.cart.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import ua.com.novasolutio.cart.data.Product;

@Database(entities = {Product.class}, version = 1)
public abstract class CartDatabase extends RoomDatabase {
    public abstract ProductDao mProductDao();
}
