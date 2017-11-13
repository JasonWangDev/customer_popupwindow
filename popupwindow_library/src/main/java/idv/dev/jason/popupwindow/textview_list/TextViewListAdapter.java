package idv.dev.jason.popupwindow.textview_list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import idv.dev.jason.popupwindow.R;

/**
 * Created by Jason on 2017/10/14.
 */

abstract class TextViewListAdapter<T> extends RecyclerView.Adapter<TextViewListAdapter.ViewHolder>
                                      implements View.OnClickListener {

    //************************************** 抽象方法宣告 ****************************************//

    protected abstract String getItemName(T obj);
    protected abstract int getItemViewBackgroundResource(int position);


    //***************************************** 變數宣告 *****************************************//

    private static final int ITEM_LAYOUT = R.layout.item_textview_list;

    private OnTextViewListAdapterListener listener;

    private List<T> objectList;


    //****************************************** 建構子 ******************************************//

    TextViewListAdapter(List<T> objectList) {
        this.objectList = objectList;
    }


    //************************************* Adapter 覆寫方法 *************************************//

    @Override
    public TextViewListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(ITEM_LAYOUT, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TextViewListAdapter.ViewHolder holder, int position) {
        holder.itemView.setBackgroundResource(getItemViewBackgroundResource(position));
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(this);

        T obj = objectList.get(position);
        holder.tv.setText(getItemName(obj));
    }

    @Override
    public int getItemCount() {
        return null != objectList ? objectList.size() : 0;
    }


    //*********************************** OnClickListener 介面 ***********************************//

    @Override
    public void onClick(View view) {
        if (null != listener)
            listener.onTextViewItemClick(view, (int) view.getTag());
    }


    //****************************************** 公用類 ******************************************//

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;


        ViewHolder(View itemView) {
            super(itemView);

            tv = itemView.findViewById(R.id.text);
        }
    }


    //***************************************** 公用方法 *****************************************//

    void setOnTextViewListAdapterListener(OnTextViewListAdapterListener listener) {
        this.listener = listener;
    }

}
