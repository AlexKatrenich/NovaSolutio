package ua.com.novasolutio.cart.data;

import android.support.annotation.Nullable;

import java.util.List;
import java.util.Objects;

// клас для відображення об'єкту
public class Payment {

    private int id; // Ід об'єкту в БД

    private long paymentDate; // дата проведення платежу

    @Nullable
    private List<Product> mProducts; // список проданих продуктів

    private long totalPrice; // загальна вартість платежу(в копійках 3000 = 30,00 грн)

    private long change;


    /* Constructor */
    public Payment() {
    }

    public Payment(int id, long paymentDate, int totalPrice) {
        this.id = id;
        this.paymentDate = paymentDate;
        this.totalPrice = totalPrice;
    }

    public Payment(int id, long paymentDate, @Nullable List<Product> products, int totalPrice) {
        this.id = id;
        this.paymentDate = paymentDate;
        this.mProducts = products;
        this.totalPrice = totalPrice;
    }

    /* Getter and Setter */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(long paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Nullable
    public List<Product> getProducts() {
        return mProducts;
    }

    public void setProducts(@Nullable List<Product> products) {
        this.mProducts = products;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getChange() {
        return change;
    }

    public void setChange(long change) {
        this.change = change;
    }

    /* Override methods*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return id == payment.id ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", paymentDate=" + paymentDate +
                ", totalPrice=" + totalPrice +
                ", change=" + change +
                '}';
    }
}
