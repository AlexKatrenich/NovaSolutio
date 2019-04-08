package ua.com.novasolutio.cart.data;

import android.support.annotation.Nullable;

import java.util.List;
import java.util.Objects;

// клас для відображення об'єкту
public class Payment {

    private int id; // Ід об'єкту в БД

    private long paymentDate; // дата проведення платежу

    @Nullable
    private List<Product> product; // список проданих продуктів

    private int totalPrice; // загальна вартість платежу(в копійках 3000 = 30,00 грн)


    /* Constructor */
    public Payment() {
    }

    public Payment(int id, long paymentDate, int totalPrice) {
        this.id = id;
        this.paymentDate = paymentDate;
        this.totalPrice = totalPrice;
    }

    public Payment(int id, long paymentDate, @Nullable List<Product> product, int totalPrice) {
        this.id = id;
        this.paymentDate = paymentDate;
        this.product = product;
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
    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(@Nullable List<Product> product) {
        this.product = product;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
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
                '}';
    }
}
