package com.denimhouse.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.denimhouse.Activity.AsynResult;
import com.denimhouse.Models.AllProductsModel;
import com.denimhouse.Network.AppUrl;
import com.denimhouse.R;

import java.util.ArrayList;

/**
 * Created by Android-Dev2 on 8/14/2017.
 */

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.MyViewHolder> {


    private Context mContext;
    private ArrayList<AllProductsModel> discountModels;
    AsynResult<AllProductsModel> asynResultListener;
    AsynResult<AllProductsModel> asynResultListenerModel;
    AsynResult<AllProductsModel> asynResultListenerMinus;
    AsynResult<AllProductsModel> asynResultDetailView;
    double total;
    double totalCount = 0, totalCount1 = 0;
    int multi, particularItemCount = 0;
    static int sumTotlal = 0;
    private int count = 0;
    private int count1 = 0;
    int sum = 0;
    //  private int[] a = new int[50];


    public DiscountAdapter(Context mContext, ArrayList<AllProductsModel> discountModels, AsynResult<AllProductsModel> asynResultListener,
                           AsynResult<AllProductsModel> asynResultListenerModel, AsynResult<AllProductsModel> asynResultListenerMinus,
                           AsynResult<AllProductsModel> asynResultDetailView) {
        this.mContext = mContext;
        this.discountModels = discountModels;
        this.asynResultListener = asynResultListener;
        this.asynResultListenerModel = asynResultListenerModel;
        this.asynResultListenerMinus = asynResultListenerMinus;
        this.asynResultDetailView = asynResultDetailView;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.discount_list, parent, false);

        return new DiscountAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final AllProductsModel discountModel = discountModels.get(position);
        holder.title.setText(discountModel.getProductName());
        holder.description.setText(discountModel.getProductDescription());
        holder.newRate.setText(discountModel.getProductPrice());
        Glide.with(mContext).load(AppUrl.IMAGE_URL + discountModel.getProductImage()).into(holder.titleImage);
        discountModels.get(position).setItemCount(0);

        holder.discountLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              asynResultDetailView.success(discountModels.get(position));
            }
        });

       /* holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sum = discountModels.get(position).getItemCount();

               *//* if (sum == 25) {
                    total = Double.parseDouble(discountModels.get(position).getProductPrice());
                    int multi = discountModels.get(position).getItemCount();
                    total = total * multi;
                    // a = new int[multi];
                    totalCount = totalCount + total;
                    discountModels.get(position).setTotalCount(totalCount);
                    discountModels.get(position).setSubTotal(total);
                    asynResultListener.passItemValue(discountModels.get(position).getItemCount());
                    asynResultListener.success(discountModels.get(position));
                    asynResultListenerModel.success(discountModels.get(position));
                    Toast.makeText(v.getContext(), "Your item is added to shopping cart", Toast.LENGTH_SHORT).show();
                    //  a[position] = sum;
                }*//*
                if(sum == 0){
                  Toast.makeText(v.getContext(), "You have to select atleast one item", Toast.LENGTH_SHORT).show();
                } else {
                    total = Double.parseDouble(discountModels.get(position).getProductPrice());
                    int multi = discountModels.get(position).getItemCount();
                    total = total * multi;
                    // a = new int[multi];
                    totalCount = totalCount + total;
                    discountModels.get(position).setTotalCount(totalCount);
                    discountModels.get(position).setSubTotal(total);
                    asynResultListener.passItemValue(discountModels.get(position).getItemCount());
                    asynResultListener.success(discountModels.get(position));
                    asynResultListenerModel.success(discountModels.get(position));
                    Toast.makeText(v.getContext(), "Your item is added to shopping cart", Toast.LENGTH_SHORT).show();
                    // a[position] = sum;
                }

                   *//* total = Double.parseDouble(discountModels.get(position).getProductPrice());

                    int multi = discountModels.get(position).getItemCount();
                    *//**//*if (multi >= 1) {
                        particularItemCount = multi - 1;
                        multi = multi - particularItemCount;
                    }*//**//*
                    double subTotal = total * multi;
                    sum = sum - a[position];
                    total = total * sum;

                    totalCount = totalCount + total;
                    discountModels.get(position).setTotalCount(totalCount);
                    discountModels.get(position).setSubTotal(subTotal);

                    asynResultListener.passItemValue(sum);
                    asynResultListener.success(discountModels.get(position));
                    asynResultListenerModel.success(discountModels.get(position));
                    Toast.makeText(v.getContext(), "Your item is added to shopping cart", Toast.LENGTH_SHORT).show();
                    a[position] = discountModels.get(position).getItemCount();*//*

                // holder.addButton.setClickable(false);
            }
        });*/

        // holder.itemCount.setText(String.valueOf(discountModel.getItemCount()));

      /*  holder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // asynResultListener.success(discountModels.get(position));
                //   holder.addButton.setClickable(true);
                String sum1 = holder.itemCount.getText().toString();

                int sum = Integer.parseInt(sum1) + 1;
                discountModels.get(position).setItemCount(sum);
                holder.itemCount.setText(String.valueOf(discountModels.get(position).getItemCount()));
                // subTotal();
                // multiply();

            }

            private void subTotal() {
                total = Double.parseDouble(discountModels.get(position).getProductPrice());
                int multi = discountModels.get(position).getItemCount();
                total = total * multi;
                discountModels.get(position).setSubTotal(total);
            }


            private void multiply() {
                total = Double.parseDouble(discountModels.get(position).getProductPrice());
                multi = discountModels.get(position).getItemCount();
                if (multi >= 25) {
                    particularItemCount = multi - 1;
                    multi = multi - particularItemCount;
                }

                total = total * multi;
                discountModels.get(position).setSubTotal(total);
                double subTotalDiscount = discountModels.get(position).getSubTotal();
                // totalCount = totalCount + subTotalDiscount;
                totalCount = totalCount + Double.parseDouble(discountModels.get(position).getProductPrice());
                discountModels.get(position).setTotalCount(totalCount);
                // holder.subTotal.setText(String.valueOf(discountModel.getSubTotal()));
            }

        });*/

        /*holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (discountModels.get(position).getItemCount() > 0) {
                    String sum1 = holder.itemCount.getText().toString();
                    int sum = Integer.parseInt(sum1) - 1;
                    discountModels.get(position).setItemCount(sum);
                    holder.itemCount.setText(String.valueOf(sum));
                    *//*double totalCount = discountModels.get(position).getTotalCount();
                    String productPrice = discountModels.get(position).getProductPrice();
                    double totalCountMinusValue = totalCount - Double.parseDouble(productPrice);
                    double subTotal = Double.parseDouble(productPrice) * sum;*//*
                  *//*  discountModels.get(position).setTotalCount(totalCountMinusValue);
                    discountModels.get(position).setSubTotal(subTotal);*//*
                    //   asynResultListenerMinus.success(discountModels.get(position));
                    //   asynResultListenerModel.success(discountModels.get(position));
                    //asynResultListener.passItemValue(sum);
                    // asynResultListener.success(discountModels.get(position));
                    // divide();
                    // subTotal();
                }

            }

            private void divide() {
                if (discountModels.get(position).getTotalCount() > 0) {
                    *//*total = Double.parseDouble(discountModels.get(position).getProductPrice());
                    multi = discountModels.get(position).getItemCount();
                    total = total * multi;
                    discountModels.get(position).setSubTotal(total);
                    totalCount = totalCount - Double.parseDouble(discountModels.get(position).getProductPrice());
                    discountModels.get(position).setTotalCount(totalCount);*//*
                    double totalCount = discountModels.get(position).getTotalCount();
                    double mini = Double.parseDouble(discountModels.get(position).getProductPrice());
                    totalCount = totalCount - mini;
                    discountModels.get(position).setTotalCount(totalCount);

                }
                *//* if (discountModel.getItemCount() > 0) {
                    double subtotal3 = discountModel.getSubTotal();
                    double mini = Double.parseDouble(discountModel.getProductPrice());
                    subtotal3 = subtotal3 - mini;
                    discountModel.setSubTotal(subtotal3);
                    totalCount = totalCount + discountModel.getSubTotal();
                    discountModel.setTotalCount(totalCount);
                    //holder.subTotal.setText(String.valueOf(discountModel.getSubTotal()));
                }*//*
            }

            private void subTotal() {
                if (discountModels.get(position).getItemCount() > 0) {
                    double subtotal3 = discountModels.get(position).getSubTotal();
                    double mini = Double.parseDouble(discountModels.get(position).getProductPrice());
                    subtotal3 = subtotal3 - mini;
                    discountModels.get(position).setSubTotal(subtotal3);
                    totalCount = totalCount + discountModels.get(position).getSubTotal();
                    discountModels.get(position).setTotalCount(totalCount);
                    //holder.subTotal.setText(String.valueOf(discountModel.getSubTotal()));
                }
            }

        });*/

    }


    @Override
    public int getItemCount() {
        return discountModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description, newRate, itemCount, oldRate;
        ImageView titleImage, defaultImage;
        Button addButton;
        ImageButton minusButton, plusButton;
        RelativeLayout discountLayout;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.discountTitle);
            description = (TextView) view.findViewById(R.id.discountDescription);
            newRate = (TextView) view.findViewById(R.id.discountNewRate);
            titleImage = (ImageView) view.findViewById(R.id.discountTitleImage);
           // addButton = (Button) view.findViewById(R.id.addButton);
            itemCount = (TextView) view.findViewById(R.id.itemCount);
            plusButton = (ImageButton) view.findViewById(R.id.plusButton);
            minusButton = (ImageButton) view.findViewById(R.id.minusButton);
            discountLayout = (RelativeLayout) view.findViewById(R.id.discountLayout);
        }

    }

}
