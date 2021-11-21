package com.uk.fiveerhealthcare.MainAuxillaries;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uk.fiveerhealthcare.R;
import com.uk.fiveerhealthcare.Utils.IAdapterCallback;

import java.util.ArrayList;


public class CustomerProfileTreatmentRCVAdapter extends RecyclerView.Adapter<CustomerProfileTreatmentRCVAdapter.ViewHolder> {

    private static View lastClicked = null;
    private ArrayList<DModelTreatment> mData;
    private final Context mContext;
    private final IAdapterCallback iAdapterCallback;


    public CustomerProfileTreatmentRCVAdapter(Context mContext, ArrayList<DModelTreatment> mData,
                                              IAdapterCallback iAdapterCallback) {
        this.mContext = mContext;
        this.mData = mData;

        this.iAdapterCallback = iAdapterCallback;


    }


    @NonNull
    @Override
    public CustomerProfileTreatmentRCVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_customer_treatment_item, null);


        return new CustomerProfileTreatmentRCVAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final CustomerProfileTreatmentRCVAdapter.ViewHolder holder, final int position) {

        holder.txvName.setText(mData.get(position).getName());
//        holder.txvDesc.setText(mData.get(position).getDesc());
//
        if (mData.get(position).getName().equalsIgnoreCase("Cardiologist"))
        {
//            holder.llBg.setBackground(mContext.getResources().getDrawable(R.drawable.shp_rect_rounded_light_blue2_small));
            holder.imvResults.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_medical_heart));
//            Picasso.get()
//                    .load(mData.get(position).getStrImage())
//                    .placeholder(R.drawable.ic_user_placeholder)
//                    .into(  viewHolder.imvResults);

        }
        else if (mData.get(position).getName().equalsIgnoreCase("Diabetes"))
        {
//            holder.llBg.setBackground(mContext.getResources().getDrawable(R.drawable.shp_rect_rounded_green));
            holder.imvResults.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_pill));
        }
        else if (mData.get(position).getName().equalsIgnoreCase("Blood pressure"))
        {
//            holder.llBg.setBackground(mContext.getResources().getDrawable(R.drawable.shp_rect_rounded_blue2_small));
            holder.imvResults.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_skull_heart));
        }
        else if (mData.get(position).getName().equalsIgnoreCase("Asthma"))
        {
//            holder.llBg.setBackground(mContext.getResources().getDrawable(R.drawable.shp_rect_rounded_light_blue_small));
            holder.imvResults.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_asthma));
        }
//
//        holder.llBg.setOnClickListener(v -> {
//            iAdapterCallback.onAdapterEventFired(IAdapterCallback.EVENT_A, position);
//        });
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

        ImageView imvResults;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            txvName = itemView.findViewById(R.id.lay_treatment_item_txvName);

            imvResults = itemView.findViewById(R.id.imv);
//            llBg = itemView.findViewById(R.id.lay_treatment_item_llbg);


        }
    }



}
