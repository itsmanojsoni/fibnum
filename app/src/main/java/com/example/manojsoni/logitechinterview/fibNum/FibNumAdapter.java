package com.example.manojsoni.logitechinterview.fibNum;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.manojsoni.logitechinterview.R;

import java.util.ArrayList;
import java.util.List;


public class FibNumAdapter extends RecyclerView.Adapter<FibNumAdapter.ViewHolder> {

    private List<String> fibNumerList = new ArrayList<>();
    private static final String TAG = FibNumAdapter.class.getSimpleName();


    @Override
    public FibNumAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.activity_fibnum_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    public void setData(List<String> fibNum) {
        fibNumerList.clear();
        fibNumerList.addAll(fibNum);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(FibNumAdapter.ViewHolder holder, int position) {
        holder.fibNumIndex.setText(String.valueOf(position));
        holder.fibNumVal.setText(String.valueOf(fibNumerList.get(position)));
    }

    @Override
    public int getItemCount() {
        return fibNumerList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fibNumIndex;
        TextView fibNumVal;

        public ViewHolder(View itemView) {
            super(itemView);
            fibNumIndex = itemView.findViewById(R.id.fibNumberIndex);
            fibNumVal = itemView.findViewById(R.id.fibNumber);
        }
    }
}
