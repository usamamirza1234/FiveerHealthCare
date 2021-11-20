package com.uk.fiveerhealthcare.MainAuxillaries;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uk.fiveerhealthcare.R;
import com.uk.fiveerhealthcare.Utils.IAdapterCallback;

import java.util.ArrayList;


public class DoctorRCVAdapter extends RecyclerView.Adapter<DoctorRCVAdapter.ViewHolder> {

    private static View lastClicked = null;
    private ArrayList<DModelTreatment> mData;
    private final Context mContext;
    private final IAdapterCallback iAdapterCallback;


    public DoctorRCVAdapter(Context mContext, ArrayList<DModelTreatment> mData,
                            IAdapterCallback iAdapterCallback) {
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


//        holder.txvName.setText(mData.get(position).getName());
//        holder.txvDesc.setText(mData.get(position).getDesc());
//
//        if (mData.get(position).getName().equalsIgnoreCase("Cardiologist"))
//        {
//            holder.llBg.setBackground(mContext.getResources().getDrawable(R.drawable.shp_rect_rounded_light_blue2_small));
//            holder.imvResults.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_medical_heart));
////            Picasso.get()
////                    .load(mData.get(position).getStrImage())
////                    .placeholder(R.drawable.ic_user_placeholder)
////                    .into(  viewHolder.imvResults);
//
//        }
//        else if (mData.get(position).getName().equalsIgnoreCase("Pediatrician"))
//        {
//            holder.llBg.setBackground(mContext.getResources().getDrawable(R.drawable.shp_rect_rounded_green));
//            holder.imvResults.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_pill));
//        }
//        else if (mData.get(position).getName().equalsIgnoreCase("General"))
//        {
//            holder.llBg.setBackground(mContext.getResources().getDrawable(R.drawable.shp_rect_rounded_blue2_small));
//            holder.imvResults.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_skull_heart));
//        }
//        else if (mData.get(position).getName().equalsIgnoreCase("Homeopathy"))
//        {
//            holder.llBg.setBackground(mContext.getResources().getDrawable(R.drawable.shp_rect_rounded_light_blue_small));
//            holder.imvResults.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_mortar_pestle));
//        }
//

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
        ImageView imvResults;
        RelativeLayout rlBg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


//            txvName = itemView.findViewById(R.id.lay_treatment_item_txvName);
//            txvDesc = itemView.findViewById(R.id.lay_treatment_item_txvDesc);
//            imvResults = itemView.findViewById(R.id.lay_treatment_item_imv);
            rlBg = itemView.findViewById(R.id.lay_doctor_item_rlbg);


        }
    }



}
