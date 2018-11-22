package com.hiscene.testapp.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hiscene.testapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hujun on 2018/11/21.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {
    private static final String TAG = "MyAdapterTAG";
    private List<String> names = new ArrayList<>();
    private Context mContext;
    private int screenHeight;

    public void setNames(List<String> names) {
        this.names = names;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_1, viewGroup, false);
        mContext = viewGroup.getContext();
        screenHeight = viewGroup.getHeight();
        return new VH(view);
    }

    @Override
    public void onViewRecycled(@NonNull VH holder) {
        super.onViewRecycled(holder);
        Log.d(TAG, "onViewRecycled: " + holder.textView.getText().toString());
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        vh.textView.setText(names.get(i));
        ViewGroup.LayoutParams params = vh.itemView.getLayoutParams();
        if (i == 0 && names.size() == 1) {
            params.height = screenHeight;
        }else if (names.size()==2){
            params.height = screenHeight;
        }
        else {
            params.height = screenHeight / 2;
        }
        vh.itemView.setLayoutParams(params);
        Log.d(TAG, "onBindViewHolder: i=" + i + ",height=" + vh.itemView.getLayoutParams().height);

    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    class VH extends RecyclerView.ViewHolder {
        TextView textView;
        SurfaceView surfaceView;

        public VH(@NonNull View itemView) {

            super(itemView);
            textView = itemView.findViewById(R.id.txt_name);
            surfaceView = itemView.findViewById(R.id.surface);
            surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {
                    Log.d(TAG, "surfaceCreated: " + textView.getText().toString());
                }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int w, int h) {
                    Log.d(TAG, "surfaceChanged: " + textView.getText().toString() + ",w=" + w + ",h=" + h);
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                    Log.d(TAG, "surfaceDestroyed: " + textView.getText().toString());
                }
            });
        }
    }

    public void resize() {
        notifyDataSetChanged();
    }

    public void add(String name) {
        names.add(name);
        if (names.size() == 2) {
            notifyItemChanged(0);
        }
        notifyItemInserted(names.size());
    }

    public void delete() {
        if (names.size() == 0) {
            return;
        }
        names.remove(names.size() - 1);
        notifyItemRemoved(names.size() - 1);
        if (names.size()==1){
            notifyItemChanged(0);
        }
    }

    public int getSize() {
        return names.size();
    }
}
