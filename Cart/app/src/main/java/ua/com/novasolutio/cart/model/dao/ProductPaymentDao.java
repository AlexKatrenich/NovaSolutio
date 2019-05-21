package ua.com.novasolutio.cart.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import ua.com.novasolutio.cart.model.data.ProductPaymentJoin;

@Dao
public interface ProductPaymentDao {
    @Insert
    void insert(ProductPaymentJoin productPaymentJoin);

    @Query("SELECT * FROM product_payment WHERE payment_id=:paymentId")
    Flowable<List<ProductPaymentJoin>> getSoldProductsCount(final int paymentId);

}
