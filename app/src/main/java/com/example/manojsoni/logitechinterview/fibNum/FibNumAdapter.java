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

    private List<String> fibNumberList = new ArrayList<>();
    private int startPosition = 0;

    @Override
    public FibNumAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_fibnum_item, parent, false);
        return new ViewHolder(view);
    }

    public void setData(int startIndex, List<String> fibNum) {
        fibNumberList.clear();
        fibNumberList.addAll(fibNum);
        startPosition = startIndex;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(FibNumAdapter.ViewHolder holder, int position) {
        holder.fibNumIndex.setText(String.valueOf(startPosition + position));
        holder.fibNumVal.setText(String.valueOf(fibNumberList.get(position)));
    }

    @Override
    public int getItemCount() {
        return fibNumberList.size();
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
