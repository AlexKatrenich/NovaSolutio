package ua.com.novasolutio.cart.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ua.com.novasolutio.cart.R;
import ua.com.novasolutio.cart.model.data.CurrencyManager;
import ua.com.novasolutio.cart.model.data.Product;

public class PaymentReportProductListAdapter extends BaseAdapter {
    public static final String TAG = "PaymentListAdapter";
    private List<Product> data;
    private static LayoutInflater sInflater = null;

    public PaymentReportProductListAdapter(Context context, List<Product> data) {
        this.data = data;
        sInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    public void setData(List<Product> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public Product getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = sInflater.inflate(R.layout.item_list_products_payment_report, null);
            viewHolder = new ViewHolder();
            viewHolder.productCaption = convertView.findViewById(R.id.tv_payment_report_products_caption);
            viewHolder.productCount = convertView.findViewById(R.id.tv_payment_report_products_count);
            viewHolder.totalPrice = convertView.findViewById(R.id.tv_payment_report_products_price);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Product product = data.get(position);
        viewHolder.productCaption.setText(product.getCaption());
        viewHolder.productCount.setText(String.valueOf(product.getCount()));

        long price = product.getCount() * product.getPrice();
        viewHolder.totalPrice.setText(formatPriceOnText(price));

        return convertView;
    }

    static class ViewHolder{
        TextView productCaption;
        TextView productCount;
        TextView totalPrice;
    }

    public String formatPriceOnText(long price) {
        StringBuffer priceString = new StringBuffer(String.valueOf(price));
        switch (priceString.length()){
            case 0 :
                priceString.append("0.00");
                break;
            case 1:
                priceString.insert(0, "0.0");
                break;
            case 2:
                priceString.insert(0, "0.");
                break;
            default:
                priceString.insert(priceString.length() - 2, '.');
        }

        // додавання назви грошових одиниць до відображення ціни на екрані
        String currency = CurrencyManager.getInstance().getCurrencyName();
        priceString.append(' ').append(currency).append(' ');

        return priceString.toString();
    }
}
