package idv.dev.jason.popupwindow.demo.popupwindow;

import android.content.Context;

import idv.dev.jason.popupwindow.checkbox_list.CheckBoxListPopupWindow;
import idv.dev.jason.popupwindow.demo.model.User;

/**
 * Created by Jason on 2017/11/14.
 */

public class UserCheckBoxListPopupWindow extends CheckBoxListPopupWindow<User> {

    public UserCheckBoxListPopupWindow(Context context) {
        super(context);
    }


    @Override
    protected String getLabelName(User obj) {
        return obj.getName();
    }

}
