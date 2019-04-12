package ua.com.novasolutio.cart.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ua.com.novasolutio.cart.data.Payment;

@Dao
public interface PaymentDao {
    @Query("SELECT * FROM payments")
    List<Payment> getAll();

    @Query("SELECT * FROM payments WHERE payment_id = :id")
    Payment getById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Payment payment);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(Payment payment);

    @Delete
    void delete(Payment payment);
}
