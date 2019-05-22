package ua.com.novasolutio.cart.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import ua.com.novasolutio.cart.model.data.Product;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM products")
    List<Product> getAll();

    @Query("SELECT * FROM products WHERE deleted = 0")
    List<Product> getAllActive();

    @Query("SELECT * FROM products WHERE id LIKE :id")
    Product getById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Product product);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(Product product);

    @Delete
    void delete(Product product);
}
