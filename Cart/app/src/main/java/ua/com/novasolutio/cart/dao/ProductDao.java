package ua.com.novasolutio.cart.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
import ua.com.novasolutio.cart.data.Product;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM products")
    List<Product> getAll();

    @Query("SELECT * FROM products WHERE product_id LIKE :id")
    Product getById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Product product);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(Product product);

    @Delete
    void delete(Product product);
}
