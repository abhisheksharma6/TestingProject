package com.denimhouse.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.denimhouse.Activity.AsynResult1;
import com.denimhouse.Models.AllProductsModel;
import com.denimhouse.Network.AppUrl;
import com.denimhouse.R;

import java.util.ArrayList;

/**
 * Created by Vs1 on 9/4/2017.
 */

public class CheckOutAdapter extends RecyclerView.Adapter<CheckOutAdapter.MyViewHolder> {

    ArrayList<AllProductsModel> checkOutModels;
    private Context mContext;
    AsynResult1 asynResult1;

    public CheckOutAdapter(Context mContext, ArrayList<AllProductsModel> checkOutModels) {
        this.mContext = mContext;
        this.checkOutModels = checkOutModels;
        this.asynResult1 = asynResult1;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.check_out_list, parent, false);
        return new CheckOutAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final AllProductsModel checkOutModel = checkOutModels.get(position);
        holder.title.setText(checkOutModel.getProductName());
        holder.type.setText(checkOutModel.getCategoryName());
        holder.productCode.setText(checkOutModel.getDiscountvalue());
        holder.price.setText(checkOutModel.getProductPrice());
        Glide.with(mContext).load(AppUrl.IMAGE_URL + checkOutModel.getProductImage()).into(holder.titleImage);
        holder.subtotal.setText(String.valueOf(checkOutModels.get(position).getSubTotal()));
        holder.size.setText(checkOutModel.getSize());
    }

    @Override
    public int getItemCount() {
        return checkOutModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title, type, productCode, price, subtotal, size;
        ImageView titleImage;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.checkOutTitle);
            type = (TextView) itemView.findViewById(R.id.typeRemote);
            productCode = (TextView) itemView.findViewById(R.id.productCodeRemote);
            price = (TextView) itemView.findViewById(R.id.priceRemote);
            subtotal = (TextView) itemView.findViewById(R.id.subtotalremote);
            titleImage = (ImageView) itemView.findViewById(R.id.checkOutImageView);
            size = (TextView) itemView.findViewById(R.id.shoppingCartSize);
        }
    }
}
