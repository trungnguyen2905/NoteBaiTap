package com.example.notebaitap;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class NoiDungAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<NoiDung> noiDungList;

    public NoiDungAdapter(MainActivity context, int layout, List<NoiDung> noiDungList) {
        this.context = context;
        this.layout = layout;
        this.noiDungList = noiDungList;
    }

    @Override
    public int getCount() {
        return noiDungList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
        TextView txtTen;
        TextView txtDate;
        ImageView imgDelete, imgEdit;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
//            holder.txtDate = (TextView) convertView.findViewById(R.id.textviewDate);
            holder.txtTen = (TextView) convertView.findViewById(R.id.textviewTen);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.imageviewDelete);
            holder.imgEdit = (ImageView) convertView.findViewById(R.id.imageviewEdit);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        final NoiDung noiDung = noiDungList.get(position);

        holder.txtTen.setText(noiDung.getTenTD());
//        holder.txtTen.setText(noiDung.getCurrentDate());

        //bat su kien xoa va sua
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogSuaNoiDung(noiDung.getTenTD(),noiDung.getIdND());
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogXoaNoiDung(noiDung.getTenTD(),noiDung.getIdND());
            }
        });



        return convertView;
    }


}
