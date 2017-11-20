### android无埋点方案总结
1. 使用BaseActivity，BaseFragment
> BaseActivity中复写覆写dispatchTouchEvent()方法和onWindowsFocusChanged()方法
dispatchTouchEvent()方法中负责捕捉当前Action_UP点的坐标位置。
```
if(event.getAction == ACTION_UP){
    int x = (int) event.getRawX();
    int y = (int) event.getRawY();
    Rect rect = new Rect();
    //遍历views，找到hold住点击位置的view
    for(int i = 0; i < views.size(); i++){
        v.getGlobalVisibleRect(rect);
        if(v.contains(x,y)){
            //如果该view能够hold住该点击位置，则说明该view是被点击的控件。
            //因此可以在这里做数据上报工作
        }
    }
   // 如果没有找到view能hold住该点击位置，则说明该view很有可能出现在Fragment里面
    //又因为Fragment是没有dispatchTouchEnvent()方法的，所以此处可以使用接口给Fragment传输point位置(x,y)
    dispatchTouchEnventAble.point(x,y);
}


 //给fragment的接口 是BaseActivity内部定义的一个接口，可以通过setter来设置该接口
    interface DispatchTouchEventable {
        /**
         * 因为fragment没有onIntercept方法，所以要
         * 通過view向fragment传输坐标
         *
         * @param x
         * @param y
         */
        void point(int x, int y);
    }


    //在Fragment中，可以使用(Activity)getActivity()获得当前的Activity，然后通过Activity调用接口
    activity.SetDispatchEventable(new DispatchTouchEventable(
        public void point(int x, int y){
            //然后和activity里面操作的一样，进行view的遍历，看看哪一个view可以hold住该click事件，再做相应地数据上报

        }
    ));

//在BaseActivity和BaseFragment中都需要遍历收集里面所有的view，拿到所有的view保存在list中，采用的方法是递归调用
//此外还需要拿到所有view的path，此path同样采用的是递归调用
//在BaseActivity中在onWindowChanged()方法中递归拿到所有view，递归拿到所有的viewpath
//在BaseFragment中在onCreateView()方法中递归拿到所有的view和viewpath
```