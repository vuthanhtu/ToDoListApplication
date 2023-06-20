package com.example.todolistapplication;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class CongViecAdapter extends BaseAdapter{
    private MainActivity context;
    private int layout;
    private List<CongViec> listCongViec;

    public CongViecAdapter(MainActivity context, int layout, List<CongViec> listCongViec) {
        this.context = context;
        this.layout = layout;
        this.listCongViec = listCongViec;
    }

    @Override
    public int getCount() {
        return listCongViec.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView txtTen;
        ImageView imgDelete,imgEdit;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.txtTen = (TextView) view.findViewById(R.id.idTVCongViec);
            holder.imgEdit = (ImageView) view.findViewById(R.id.IVEdit);
            holder.imgDelete = (ImageView) view.findViewById(R.id.IVDelete);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        CongViec cv = listCongViec.get(i);
        holder.txtTen.setText(cv.getTenCV());
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogSua(cv.getId(),cv.getTenCV());
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogXoaCV(cv.getId(),cv.getTenCV());
            }
        });
        return view;
    }
}
