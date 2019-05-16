package ua.com.novasolutio.cart.model.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(tableName = "product_payment",
    primaryKeys = {"product_id", "payment_id"},
    foreignKeys = {
        @ForeignKey(entity = Product.class,
                    parentColumns = "id",
                    childColumns = "product_id"),
        @ForeignKey(entity = Payment.class,
                    parentColumns = "id",
                    childColumns = "payment_id")
    })
public class ProductPaymentJoin {
    @ColumnInfo(name = "product_id")
    public final int productID;

    @ColumnInfo(name = "payment_id")
    public final int paymentID;

    @ColumnInfo(name = "count")
    public final int productCount;

    public ProductPaymentJoin(final int productID, final int paymentID, final int productCount) {
        this.productID = productID;
        this.paymentID = paymentID;
        this.productCount = productCount;
    }


    @Override
    public String toString() {
        return "ProductPaymentJoin{" +
                "productID=" + productID +
                ", paymentID=" + paymentID +
                ", productCount=" + productCount +
                '}';
    }
}
