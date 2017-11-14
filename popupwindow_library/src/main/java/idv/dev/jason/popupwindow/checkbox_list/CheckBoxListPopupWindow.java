package idv.dev.jason.popupwindow.checkbox_list;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
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

public abstract class CheckBoxListPopupWindow<T> {

    protected abstract String getLabelName(T obj);


    private boolean isSupportAllSelect = false;
    private boolean isAllRowSingle = true;
    private int popupWindowWidth = ViewGroup.LayoutParams.MATCH_PARENT;
    private int popupWindowHeight = ViewGroup.LayoutParams.MATCH_PARENT;
    private int popupWindowBackgroundColorRes = android.R.color.holo_blue_light;
    private int itemBackgroundRes = 0;
    private RecyclerView.ItemDecoration itemDivider = null;
    private PopupWindow.OnDismissListener onDismissListener;

    private Context context;

    private List<T> objectList;
    private List<T> checkedList;
    private CheckBoxListAdapter<T> adapter;

    private PopupWindow popupWindow;

    private ViewSwitcher viewSwitcher;
    private RecyclerView recyclerView;


    public CheckBoxListPopupWindow(Context context) {
        this.context = context;
    }


    public void setSupportAllSelect(boolean supportAllSelect) {
        isSupportAllSelect = supportAllSelect;
    }

    public void setAllRowSingle(boolean allRowSingle) {
        isAllRowSingle = allRowSingle;
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


    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }


    public void build() {
        if (null == popupWindow)
        {
            objectList = new ArrayList<>();
            checkedList = new ArrayList<>();
            adapter = new CheckBoxListAdapter<T>(objectList, checkedList, isSupportAllSelect, isAllRowSingle) {
                @Override
                protected String getItemName(T obj) {
                    return getLabelName(obj);
                }

                @Override
                protected int getItemViewBackgroundResource(int position) {
                    return itemBackgroundRes;
                }
            };

            popupWindow = new PopupWindow(context);
            popupWindow.setContentView(createPopupWindowView());
            popupWindow.setWidth(popupWindowWidth);
            popupWindow.setHeight(popupWindowHeight);
            popupWindow.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(context, popupWindowBackgroundColorRes)));
            popupWindow.setTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setOnDismissListener(onDismissListener);
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


    public void setDataLoaded(List<T> objectList, List<T> checkedList) {
        int oldSize = objectList.size();
        this.objectList.clear();
        adapter.notifyItemRangeRemoved(0, oldSize);

        this.objectList.addAll(objectList);
        adapter.notifyItemRangeInserted(0, objectList.size());

        this.checkedList.clear();
        this.checkedList.addAll(checkedList);
        adapter.notifyItemChanged(0, objectList.size());

        viewSwitcher.setDisplayedChild(1);
    }


    public List<T> getCheckedList() {
        return checkedList;
    }


    private View createPopupWindowView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View parent = inflater.inflate(R.layout.popupwindow_checkbox_list,null);

        viewSwitcher = parent.findViewById(R.id.view_switcher);
        recyclerView = parent.findViewById(R.id.recycler_view);

        GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(layoutManager);

        if (null != itemDivider)
            recyclerView.addItemDecoration(itemDivider);

        recyclerView.setAdapter(adapter);

        return parent;
    }

}
