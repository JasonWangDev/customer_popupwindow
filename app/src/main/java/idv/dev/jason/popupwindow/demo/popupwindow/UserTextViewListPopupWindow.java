package idv.dev.jason.popupwindow.demo.popupwindow;

import android.content.Context;

import idv.dev.jason.popupwindow.demo.model.User;
import idv.dev.jason.popupwindow.textview_list.TextViewListPopupWindow;

/**
 * Created by Jason on 2017/11/13.
 */

public class UserTextViewListPopupWindow extends TextViewListPopupWindow<User> {

    public UserTextViewListPopupWindow(Context context) {
        super(context);
    }


    @Override
    protected String getLabelName(User obj) {
        return obj.getName();
    }

}
