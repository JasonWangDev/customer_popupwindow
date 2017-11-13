package idv.dev.jason.popupwindow.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import idv.dev.jason.popupwindow.demo.model.User;
import idv.dev.jason.popupwindow.demo.popupwindow.UserTextViewListPopupWindow;
import idv.dev.jason.popupwindow.textview_list.OnTextViewListPopupWindowListener;

public class DemoActivity extends AppCompatActivity implements View.OnClickListener, OnTextViewListPopupWindowListener {

    private Button btn1;

    private TextView tv;

    private List<User> userList;
    private UserTextViewListPopupWindow popupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        btn1 = findViewById(R.id.btn_1);
        btn1.setOnClickListener(this);

        tv = findViewById(R.id.tv);
    }


    @Override
    public void onClick(View view) {
        if (R.id.btn_1 == view.getId())
        {
            if (null == popupWindow)
            {
                popupWindow = new UserTextViewListPopupWindow(this);
                popupWindow.setOnTextViewListPopupWindowListener(this);
                popupWindow.build();
            }
            popupWindow.showAsDropDown(view);

            if (null == userList)
            {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        DemoActivity.this.getWindow().getDecorView().post(new Runnable() {
                            @Override
                            public void run() {
                                userList = new ArrayList<>();
                                for (int i = 0 ; i < 5 ; i++)
                                    userList.add(new User(i, "Name " + i));

                                popupWindow.setDataLoaded(userList);
                            }
                        });
                    }
                }).start();
            }
        }
    }


    @Override
    public void onTextViewItemClick(int position) {
        tv.setText("Click " + userList.get(position).toString());
    }

}
