package idv.dev.jason.popupwindow.textview_list;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.List;

import idv.dev.jason.popupwindow.R;

/**
 * Created by jason on 2017/11/9.
 */

public abstract class TextViewListPopupWindow<T> implements OnTextViewListAdapterListener {

    protected abstract String getLabelName(T obj);


    private int popupWindowWidth = ViewGroup.LayoutParams.MATCH_PARENT;
    private int popupWindowHeight = ViewGroup.LayoutParams.MATCH_PARENT;
    private int popupWindowBackgroundColorRes = android.R.color.holo_blue_light;
    private int itemBackgroundRes = 0;
    private RecyclerView.ItemDecoration itemDivider = null;

    private Context context;

    private OnTextViewListPopupWindowListener listener;

    private List<T> objectList;
    private TextViewListAdapter<T> adapter;

    private PopupWindow popupWindow;

    private ViewSwitcher viewSwitcher;
    private RecyclerView recyclerView;


    public TextViewListPopupWindow(Context context) {
        this.context = context;
    }


    @Override
    public void onTextViewItemClick(View view, int position) {
        if (null != listener)
            listener.onTextViewItemClick(position);

        if (null != popupWindow && popupWindow.isShowing())
            popupWindow.dismiss();
    }


    public void setPopupWindowWidth(int width) {
        popupWindowWidth = width;
    }

    public void setPopupWindowHeight(int height) {
        popupWindowHeight = height;
    }

    public void setPopupWindowBackgroundColorRes(int res) {
        popupWindowBackgroundColorRes = res;
    }

    public void setItemBackgroundRes(int res) {
        itemBackgroundRes = res;
    }

    public void setItemDivider(RecyclerView.ItemDecoration divider) {
        itemDivider = divider;
    }


    public void build() {
        if (null == popupWindow)
        {
            objectList = new ArrayList<>();

            adapter = new TextViewListAdapter<T>(objectList) {
                @Override
                protected String getItemName(T obj) {
                    return getLabelName(obj);
                }

                @Override
                protected int getItemViewBackgroundResource(int position) {
                    return itemBackgroundRes;
                }
            };
            adapter.setOnTextViewListAdapterListener(this);

            popupWindow = new PopupWindow(context);
            popupWindow.setContentView(createPopupWindowView());
            popupWindow.setWidth(popupWindowWidth);
            popupWindow.setHeight(popupWindowHeight);
            popupWindow.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(context, popupWindowBackgroundColorRes)));
            popupWindow.setTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
        }
    }


    public void showAsDropDown(View anchor) {
        if (null != popupWindow && !popupWindow.isShowing())
            popupWindow.showAsDropDown(anchor);
    }

    public void showAsDropDown(View anchor, int xOffset, int yOffset) {
        if (null != popupWindow && !popupWindow.isShowing())
            popupWindow.showAsDropDown(anchor, xOffset, yOffset);
    }


    public void setOnTextViewListPopupWindowListener(OnTextViewListPopupWindowListener listener) {
        this.listener = listener;
    }


    public void setDataLoaded(List<T> objectList) {
        int oldSize = objectList.size();
        this.objectList.clear();
        adapter.notifyItemRangeRemoved(0, oldSize);

        this.objectList.addAll(objectList);
        adapter.notifyItemRangeInserted(0, objectList.size());

        viewSwitcher.setDisplayedChild(1);
    }


    private View createPopupWindowView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View parent = inflater.inflate(R.layout.popupwindow_textview_list,null);

        viewSwitcher = parent.findViewById(R.id.view_switcher);
        recyclerView = parent.findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        if (null != itemDivider)
            recyclerView.addItemDecoration(itemDivider);

        recyclerView.setAdapter(adapter);

        return parent;
    }

}
