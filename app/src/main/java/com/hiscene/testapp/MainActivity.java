package com.hiscene.testapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.hiscene.testapp.recycler.MyAdapter;

public class MainActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "MainActivityTAG";
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        GridLayoutManager manager = new GridLayoutManager(this, 6);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                int span = 3;
                int size = myAdapter.getSize();
                if (size<=4){
                    switch (i){
                        case 0:{
                            if (size==1) {
                                span = 6;
                            }else {
                                span = 3;
                            }
                        }break;
                        case 1:span = 3;break;
                        case 2:{
                            if (size==4) {
                                span = 3;
                            }else if (size==3){
                                span = 6;
                            }

                        }break;
                        case 3:span = 3;break;
                        default:break;
                    }
                }else if (size==6){
                    span = 2;
                }else if (size==5){
                    switch (i){
                        case 0:{
                                span = 2;
                        }break;
                        case 1:span = 2;break;
                        case 2:span = 2;break;
                        case 3:span = 3;break;
                        case 4:span = 3;break;
                        default:break;
                    }
                }

                Log.d(TAG, "getSpanSize: i="+i+",span="+span);
                return span;
            }
        });
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(manager);

        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_del).setOnClickListener(this);
        findViewById(R.id.btn_resize).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add: {
                add();
            }
            break;
            case R.id.btn_del: {
                delete();
            }
            break;
            case R.id.btn_resize: {
                resize();
            }
            break;
            default:
                break;
        }
    }

    private void resize(){
        myAdapter.resize();
    }

    private void add() {
        Log.d(TAG, "add: ");
        myAdapter.add("hujun" + myAdapter.getSize());

    }

    private void delete() {
        Log.d(TAG, "delete: ");
        myAdapter.delete();
    }

}
