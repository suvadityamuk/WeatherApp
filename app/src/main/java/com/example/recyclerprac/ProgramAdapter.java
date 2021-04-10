package com.example.recyclerprac;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerprac.R;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder>{
    Context context;
    String[] itemList;
    String[] valueList;
    int[] imageIdList;
    public static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView tv1;
//        TextView tv2;
        TextView tvalt;
        ImageView imvw;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            tv1 = itemView.findViewById(R.id.tv1);
//            tv2 = itemView.findViewById(R.id.tv2);
            imvw = itemView.findViewById(R.id.imvw);
            tvalt = itemView.findViewById(R.id.tvalt);
        }
    }
    public ProgramAdapter(Context context, String[] itemList, String[] valueList, int[] imageIdList)
    {
        this.context = context;
        this.imageIdList = imageIdList;
        this.itemList = itemList;
        this.valueList = valueList;
    }


    @NonNull
    @Override
    public ProgramAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProgramAdapter.ViewHolder holder, int position) {
//        holder.tv1.setText(itemList[position]);
//        holder.tv2.setText(valueList[position]);
        holder.tvalt.setText(itemList[position] + " => "+ valueList[position]);
        holder.imvw.setImageResource(imageIdList[position]);

    }

    @Override
    public int getItemCount() {
        return itemList.length;
    }
}
