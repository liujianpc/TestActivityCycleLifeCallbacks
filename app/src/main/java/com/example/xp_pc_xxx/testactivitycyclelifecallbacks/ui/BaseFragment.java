package com.example.xp_pc_xxx.testactivitycyclelifecallbacks.ui;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.xp_pc_xxx.testactivitycyclelifecallbacks.R;
import com.example.xp_pc_xxx.testactivitycyclelifecallbacks.utils.DataCollector;
import com.example.xp_pc_xxx.testactivitycyclelifecallbacks.utils.ToastUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author XP-PC-XXX
 * @date 2017/11/15
 */

public class BaseFragment extends Fragment {
    List<String> viewPath = new ArrayList<>();
    List<View> views;
    private android.widget.TextView textcontent;
    private android.widget.TextView usrtext;
    private android.widget.EditText usrname;
    private android.widget.TextView passtext;
    private android.widget.EditText password;
    private android.widget.Button loginin;
    private android.widget.Button logoutout;
    private Button otherbtn;
    private TextView showtext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout, null);
        this.showtext = (TextView) rootView.findViewById(R.id.show_text);
        this.otherbtn = (Button) rootView.findViewById(R.id.other_btn);
        this.logoutout = (Button) rootView.findViewById(R.id.logout_out);
        this.loginin = (Button) rootView.findViewById(R.id.login_in);
        this.password = (EditText) rootView.findViewById(R.id.pass_word);
        this.passtext = (TextView) rootView.findViewById(R.id.pass_text);
        this.usrname = (EditText) rootView.findViewById(R.id.usr_name);
        this.usrtext = (TextView) rootView.findViewById(R.id.usr_text);
        this.textcontent = (TextView) rootView.findViewById(R.id.text_content);
        loginin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Main4Activity.class);
                startActivity(intent);
            }
        });
        logoutout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(v, v.getTag().toString(), Snackbar.LENGTH_LONG).setAction("action", null).show();
                ToastUtil.showToast(getContext(), v.getTag().toString());
            }
        });
        otherbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCollector.onViewClick(v);
                ToastUtil.showToast(getContext(), v.getTag().toString());
                // Snackbar.make(v, v.getTag().toString(), Snackbar.LENGTH_LONG).setAction("action", null).show();
            }
        });
        LinkedList<String> linkedList = new LinkedList<>();
        getViewPathWithTree(rootView, linkedList);
        getAllViews(rootView);
        ((BaseActivity) getActivity()).setDispatchTouchEventable(new BaseActivity.DispatchTouchEventable() {
            @Override
            public void point(int x, int y) {
                for (int i = 0; i < views.size(); i++
                        ) {
                    Rect rect = new Rect();
                    views.get(i).getGlobalVisibleRect(rect);
                    if (rect.contains(x, y)) {
                        Log.d("命中", viewPath.get(i) + "这个是我们需要的数据");
                        return;
                    }

                }
            }
        });
        usrname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCollector.onViewClick(v);

            }
        });
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCollector.onViewClick(v);

            }
        });

        usrtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCollector.onViewClick(v);
            }
        });
        passtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCollector.onViewClick(v);
            }
        });
        rootView.setTag("baseFragment");
        return rootView;


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 获取子view的路径：
     * ----从rootView到子view的路径
     *
     * @param rootView   根view 由getWindow().getDecoreView.getRootView()获得
     * @param nodeVector 一个从根节点到叶子节点的路径
     */
    public void getViewPathWithTree(View rootView, LinkedList<String> nodeVector) {

        if (rootView != null) {
            nodeVector.add(rootView.getClass().getSimpleName());
        }
        if (!(rootView instanceof ViewGroup)) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String str :
                    nodeVector) {
                stringBuilder.append(str).append("_");

            }
            viewPath.add(stringBuilder.toString());
            nodeVector.removeLast();
            return;
        } else {
            int childCount = ((ViewGroup) rootView).getChildCount();
            for (int i = 0; i < childCount; i++) {
                getViewPathWithTree(((ViewGroup) rootView).getChildAt(i), nodeVector);
            }
            nodeVector.removeLast();

        }

    }

    /**
     * 获得ViewTree中的View
     *
     * @param viewGroup 根节点
     * @return viewTree中的view
     */

    public void getAllViews(View viewGroup) {
        if (views == null) {
            views = new ArrayList<>();
        }
        if (viewGroup == null) {
            return;
        }
        if (!(viewGroup instanceof ViewGroup)) {
            views.add(viewGroup);

        } else {
            int childCount = ((ViewGroup) viewGroup).getChildCount();
            for (int i = 0; i < childCount; i++) {
                getAllViews(((ViewGroup) viewGroup).getChildAt(i));
            }
        }
    }
}
