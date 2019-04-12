package ua.com.novasolutio.cart.model.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.Objects;

// клас для відображення об'єкту
@Entity(tableName = "payments")
public class Payment {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "payment_id")
    private int mId; // Ід об'єкту в БД

    @ColumnInfo(name = "date")
    private long mPaymentDate; // дата проведення платежу

    @Nullable
    @Ignore
    private List<Product> mProducts; // список проданих продуктів

    @ColumnInfo(name = "price")
    private long mTotalPrice; // загальна вартість платежу(в копійках 3000 = 30,00 грн)

    @ColumnInfo(name = "change")
    private long mChange; // загальна решта клієнту по платежу(в копійках 3000 = 30,00 грн)


    /* Constructor */
    public Payment() {
    }

    public Payment(int id, long paymentDate, int totalPrice) {
        this.mId = id;
        this.mPaymentDate = paymentDate;
        this.mTotalPrice = totalPrice;
    }

    public Payment(int id, long paymentDate, @Nullable List<Product> products, int totalPrice) {
        this.mId = id;
        this.mPaymentDate = paymentDate;
        this.mProducts = products;
        this.mTotalPrice = totalPrice;
    }

    /* Getter and Setter */
    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public long getPaymentDate() {
        return mPaymentDate;
    }

    public void setPaymentDate(long paymentDate) {
        this.mPaymentDate = paymentDate;
    }

    @Nullable
    public List<Product> getProducts() {
        return mProducts;
    }

    public void setProducts(@Nullable List<Product> products) {
        this.mProducts = products;
    }

    public long getTotalPrice() {
        return mTotalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.mTotalPrice = totalPrice;
    }

    public long getChange() {
        return mChange;
    }

    public void setChange(long change) {
        this.mChange = change;
    }

    /* Override methods*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return mId == payment.mId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "mId=" + mId +
                ", mPaymentDate=" + mPaymentDate +
                ", mTotalPrice=" + mTotalPrice +
                ", mChange=" + mChange +
                '}';
    }
}
