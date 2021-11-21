package com.uk.fiveerhealthcare.MainAuxillaries;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uk.fiveerhealthcare.R;
import com.uk.fiveerhealthcare.Utils.IAdapterCallback;

import java.util.ArrayList;


public class DoctorRCVAdapter extends RecyclerView.Adapter<DoctorRCVAdapter.ViewHolder> {
    private static final View lastClicked = null;
    private final Context mContext;
    private final IAdapterCallback iAdapterCallback;
    private final ArrayList<DModelDoctor> mData;
    public DoctorRCVAdapter(Context mContext, ArrayList<DModelDoctor> mData, IAdapterCallback iAdapterCallback) {
        this.mContext = mContext;
        this.mData = mData;
        this.iAdapterCallback = iAdapterCallback;
    }
    @NonNull
    @Override
    public DoctorRCVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_doctor_item, null);
        return new DoctorRCVAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final DoctorRCVAdapter.ViewHolder holder, final int position) {
        holder.rlBg.setOnClickListener(v -> {
            iAdapterCallback.onAdapterEventFired(IAdapterCallback.EVENT_A, position);
        });
        holder.txvName.setText(mData.get(position).getName());
        holder.txvDesc.setText(mData.get(position).getDesc());
        holder.txvType.setText(mData.get(position).getType());
        holder.imvResults.setImageDrawable(mData.get(position).getImage());
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txvName;
        TextView txvDesc;
        TextView txvType;
        ImageView imvResults;
        RelativeLayout rlBg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txvName = itemView.findViewById(R.id.lay_doctor_item_txvName);
            txvDesc = itemView.findViewById(R.id.lay_doctor_item_txvDesc);
            txvType = itemView.findViewById(R.id.lay_doctor_item_txvType);
            imvResults = itemView.findViewById(R.id.lay_doctor_item_imvDr);
            rlBg = itemView.findViewById(R.id.lay_doctor_item_rlbg);
        }
    }
}
