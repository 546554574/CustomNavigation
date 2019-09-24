package com.dong.eleven.customnavigation;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;

import java.util.ArrayList;
import java.util.List;

public class CustomNavigation extends RelativeLayout {
    private static final String BLUE = "blue";

    public CustomNavigation(Context context) {
        super(context);
        initView(context);
    }

    public CustomNavigation(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CustomNavigation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomNavigation(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    List<NavigationItem> allItems, showItems;
    RecyclerView allItemRlv, showItemRlv;
    RelativeLayout editLv;
    LinearLayout showItemLv;

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_custom_navigation_view, this, true);
        allItemRlv = view.findViewById(R.id.allItemRlv);
        showItemRlv = view.findViewById(R.id.showItemRlv);
        editLv = view.findViewById(R.id.editLv);
        showItemLv = view.findViewById(R.id.showItemLv);
        allItems = new ArrayList<>();
        NavigationItem navigationItem = new NavigationItem();
        navigationItem.setImgSrc(R.mipmap.shoueye_s);
        navigationItem.setName("首页");
        allItems.add(navigationItem);
        navigationItem = new NavigationItem();
        navigationItem.setImgSrc(R.mipmap.dongtai_s);
        navigationItem.setName("动态");
        allItems.add(navigationItem);
        navigationItem = new NavigationItem();
        navigationItem.setImgSrc(R.mipmap.tongji_s);
        navigationItem.setName("统计");
        allItems.add(navigationItem);
        navigationItem = new NavigationItem();
        navigationItem.setImgSrc(R.mipmap.wode_s);
        navigationItem.setName("我的");
        allItems.add(navigationItem);

        showItems = new ArrayList<>();
        navigationItem = new NavigationItem();
        navigationItem.setImgSrc(R.mipmap.shoueye_s);
        navigationItem.setName("首页111");
        showItems.add(navigationItem);
        navigationItem = new NavigationItem();
        navigationItem.setImgSrc(R.mipmap.dongtai_s);
        navigationItem.setName("动态111");
        showItems.add(navigationItem);
        navigationItem = new NavigationItem();
        navigationItem.setImgSrc(R.mipmap.tongji_s);
        navigationItem.setName("统计111");
        showItems.add(navigationItem);
        navigationItem = new NavigationItem();
        navigationItem.setImgSrc(R.mipmap.wode_s);
        navigationItem.setName("我的111");
        showItems.add(navigationItem);
        notifyAllAdapter();
        notifyShowAdapter();

        initEvent();
    }

    int tag = 0;  //1show  2all

    private void initEvent() {
        showItemRlv.setOnDragListener(new OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                //获取事件
                int action = event.getAction();
                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
//                        showToast(BLUE, "开始拖拽");
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
//                        showToast(BLUE, "结束拖拽");
                        showItemLv.setBackgroundResource(R.drawable.default_lv_bg);
                        if (tag == 1) {
                            if (showItems.size() >= 5) {
                                showToast(BLUE, "最多只能选择5个模块");
                                return true;
                            }
                            NavigationItem data = (NavigationItem) event.getLocalState();
                            showItems.add(data);
                            notifyShowAdapter();
                        }
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        tag = 1;
                        showItemLv.setBackgroundResource(R.drawable.select_lv_bg);
//                        showToast(BLUE, "拖拽的view进入监听的showItemRlv时");
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        tag = 0;
                        showItemLv.setBackgroundResource(R.drawable.default_lv_bg);
//                        showToast(BLUE, "拖拽的view离开监听的view时");
                        break;
                    case DragEvent.ACTION_DRAG_LOCATION:
//                        float x = event.getX();
//                        float y = event.getY();
//                        long l = SystemClock.currentThreadTimeMillis();
//                        showToast(BLUE, "拖拽的view在监听view中的位置:x =" + x + ",y=" + y);
                        break;
                    case DragEvent.ACTION_DROP:
//                        showToast(BLUE, "释放拖拽的view");

                        break;
                }
                //是否响应拖拽事件，true响应，返回false只能接受到ACTION_DRAG_STARTED事件，后续事件不会收到
                return true;
            }
        });

        allItemRlv.setOnDragListener(new OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                //v 永远是设置该监听的view，这里即fl_blue
//                String simpleName = v.getClass().getSimpleName();
//                showToast(BLUE, "view name:" + simpleName);

                //获取事件
                int action = event.getAction();
                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
//                        showToast(BLUE, "开始拖拽");
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
//                        showToast(BLUE, "结束拖拽");
                        if (tag == 2) {
                            NavigationItem data = (NavigationItem) event.getLocalState();
                            showItems.remove(data);
                            notifyShowAdapter();
                        }
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        tag = 2;
//                        showToast(BLUE, tag+"");
//                        showToast(BLUE, "拖拽的view进入监听的showItemRlv时");
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        tag = 0;
//                        showToast(BLUE, tag+"");
//                        showToast(BLUE, "拖拽的view离开监听的view时");
                        break;
                    case DragEvent.ACTION_DROP:
//                        showToast(BLUE, "释放拖拽的view");

                        break;
                }
                //是否响应拖拽事件，true响应，返回false只能接受到ACTION_DRAG_STARTED事件，后续事件不会收到
                return true;
            }
        });
    }

    private void showToast(String blue, String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        Log.e(blue, s);
    }

    ItemAdapter showItemAdapter;
    private OnItemDragListener onItemDragListener = new OnItemDragListener() {
        @Override
        public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {

        }

        @Override
        public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {

        }

        @Override
        public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {

        }
    };

    private void notifyShowAdapter() {
        if (showItemAdapter == null) {
            showItemAdapter = new ItemAdapter(R.layout.layout_my_navigation, showItems);
            showItemRlv.setLayoutManager(new GridLayoutManager(getContext(), showItems.size()));
            ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(showItemAdapter);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
            itemTouchHelper.attachToRecyclerView(showItemRlv);
            // 开启拖拽
            showItemAdapter.enableDragItem(itemTouchHelper, R.id.itemLv, true);
            showItemAdapter.setOnItemDragListener(onItemDragListener);
            showItemRlv.setAdapter(showItemAdapter);
            showItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent();
                    intent.putExtra("data", showItems.get(position));
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(null, shadowBuilder, showItems.get(position), 0);
                    //震动反馈
                    view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                }
            });
        } else {
            showItemRlv.setLayoutManager(new GridLayoutManager(getContext(), showItems.size()));
            showItemAdapter.notifyDataSetChanged();
        }
    }

    ItemAdapter allItemAdapter;

    private void notifyAllAdapter() {
        if (allItemAdapter == null) {
            allItemAdapter = new ItemAdapter(R.layout.layout_my_navigation, allItems);
            allItemRlv.setLayoutManager(new GridLayoutManager(getContext(), 5));
            allItemRlv.setAdapter(allItemAdapter);
            allItemAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent();
                    intent.putExtra("data", allItems.get(position));
                    ClipData clipData = ClipData.newIntent("label", intent);
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(null, shadowBuilder, allItems.get(position), 0);
                    //震动反馈
                    view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                    return true;
                }
            });
        } else {
            allItemAdapter.notifyDataSetChanged();
        }
    }

}
