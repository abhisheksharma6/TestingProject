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

public class AllProductAdapter extends RecyclerView.Adapter<AllProductAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<AllProductsModel> allproductModels;
    AsynResult<AllProductsModel> asynResultListener;
    AsynResult<AllProductsModel> asynResultListenerModel;
    AsynResult<AllProductsModel> asynResultListenerMinus;
    AsynResult<AllProductsModel> asynResultDetailView;

    double total;
    double totalCount = 0;
    int multi, particularItemCount = 0;
    private int[] a = new int[50];

    public AllProductAdapter(Context mContext, ArrayList<AllProductsModel> allproductsModels, AsynResult<AllProductsModel> asynResultListener,
                              AsynResult<AllProductsModel> asynResultListenerMinus,
                             AsynResult<AllProductsModel> asynResultDetailView) {
        this.mContext = mContext;
        this.allproductModels = allproductsModels;
        this.asynResultListener = asynResultListener;
    //    this.asynResultListenerModel = asynResultListenerModel;
        this.asynResultListenerMinus = asynResultListenerMinus;
        this.asynResultDetailView = asynResultDetailView;
    }


    @Override
    public AllProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_product_list, parent, false);

        return new AllProductAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AllProductAdapter.MyViewHolder holder, final int position) {
        final AllProductsModel allproductModel = allproductModels.get(position);
        holder.title.setText(allproductModel.getProductName());
        holder.description.setText(allproductModel.getProductDescription());
        holder.newRate.setText(allproductModel.getProductPrice());
        Glide.with(mContext).load(AppUrl.IMAGE_URL + allproductModels.get(position).getProductImage()).into(holder.titleImage);
        allproductModels.get(position).setItemCount(0);

        holder.productLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asynResultDetailView.success(allproductModels.get(position));
            }
        });

    /*   *//* holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sum = allproductModels.get(position).getItemCount();

                if(sum == 0){
                    Toast.makeText(v.getContext(), "You have to select atleast one item", Toast.LENGTH_SHORT).show();
                } else {
                    total = Double.parseDouble(allproductModels.get(position).getProductPrice());
                    int multi = allproductModels.get(position).getItemCount();
                    total = total * multi;
                    // a = new int[multi];
                    totalCount = totalCount + total;
                    allproductModels.get(position).setTotalCount(totalCount);
                    allproductModels.get(position).setSubTotal(total);
                    asynResultListener.passItemValue(allproductModels.get(position).getItemCount());
                    asynResultListener.success(allproductModels.get(position));
                    asynResultListenerModel.success(allproductModels.get(position));
                    Toast.makeText(v.getContext(), "Your item is added to shopping cart", Toast.LENGTH_SHORT).show();
                    // a[position] = sum;
                }*//*
              *//*  if (sum == 25) {
                    total = Double.parseDouble(allproductModels.get(position).getProductPrice());
                    int multi = allproductModels.get(position).getItemCount();
                    total = total * multi;
                    // a = new int[multi];
                    totalCount = totalCount + total;
                    allproductModels.get(position).setTotalCount(totalCount);
                    allproductModels.get(position).setSubTotal(total);
                    asynResultListener.passItemValue(allproductModels.get(position).getItemCount());
                    asynResultListener.success(allproductModels.get(position));
                    asynResultListenerModel.success(allproductModels.get(position));
                    Toast.makeText(v.getContext(), "Your item is added to shopping cart", Toast.LENGTH_SHORT).show();
                    a[position] = sum;
                } else if (a[position] == 0 && a[position] < sum) {
                    total = Double.parseDouble(allproductModels.get(position).getProductPrice());
                    int multi = allproductModels.get(position).getItemCount();
                    total = total * multi;
                    // a = new int[multi];
                    totalCount = totalCount + total;
                    allproductModels.get(position).setTotalCount(totalCount);
                    allproductModels.get(position).setSubTotal(total);
                    asynResultListener.passItemValue(allproductModels.get(position).getItemCount());
                    asynResultListener.success(allproductModels.get(position));
                    asynResultListenerModel.success(allproductModels.get(position));
                    Toast.makeText(v.getContext(), "Your item is added to shopping cart", Toast.LENGTH_SHORT).show();
                    a[position] = sum;
                } else if (a[position] != 0 && a[position] < sum) {
                    total = Double.parseDouble(allproductModels.get(position).getProductPrice());
                    int multi = allproductModels.get(position).getItemCount();
                    *//**//*if (multi >= 1) {
                        particularItemCount = multi - 1;
                        multi = multi - particularItemCount;
                    }*//**//*
                    double subTotal = total * multi;
                    sum = sum - a[position];
                    total = total * sum;
                    totalCount = totalCount + total;
                    allproductModels.get(position).setTotalCount(totalCount);
                    allproductModels.get(position).setSubTotal(subTotal);

                    asynResultListener.passItemValue(sum);
                    asynResultListener.success(allproductModels.get(position));
                    asynResultListenerModel.success(allproductModels.get(position));
                    Toast.makeText(v.getContext(), "Your item is added to shopping cart", Toast.LENGTH_SHORT).show();
                    a[position] = allproductModels.get(position).getItemCount();
                }*//*

                // holder.addButton.setClickable(false);
            }
        });*/

        // holder.itemCount.setText(String.valueOf(allproductModel.getItemCount()));

     /*   holder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // holder.addButton.setClickable(true);
                String sum = holder.itemCount.getText().toString();
                int sum1 = Integer.parseInt(sum) + 1;
                allproductModels.get(position).setItemCount(sum1);
                holder.itemCount.setText(String.valueOf(allproductModels.get(position).getItemCount()));

                // multiply();
                // subTotal();
            }

            private void multiply() {
                total = Double.parseDouble(allproductModels.get(position).getProductPrice());
                multi = allproductModels.get(position).getItemCount();
                if (multi >= 1) {
                    particularItemCount = multi - 1;
                    multi = multi - particularItemCount;
                }
                *//*if(particularItemCount > 1){
                   multi = discountModel.getItemCount() - particularItemCount;
                }else {
                    multi = discountModel.getItemCount();
                }*//*
                total = total * multi;
                allproductModels.get(position).setSubTotal(total);
                // totalCount = totalCount + allproductModels.get(position).getSubTotal();
                totalCount = totalCount + Double.parseDouble(allproductModels.get(position).getProductPrice());
                allproductModels.get(position).setTotalCount(totalCount);
                // holder.subTotal.setText(String.valueOf(discountModel.getSubTotal()));
            }

            private void subTotal() {
                total = Double.parseDouble(allproductModels.get(position).getProductPrice());
                int multi = allproductModels.get(position).getItemCount();
                total = total * multi;
                allproductModels.get(position).setSubTotal(total);
            }
        });*/

     /*   holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allproductModels.get(position).getItemCount() > 0) {
                    String sum1 = holder.itemCount.getText().toString();
                    int sum = Integer.parseInt(sum1) - 1;
                    allproductModels.get(position).setItemCount(sum);
                    holder.itemCount.setText(String.valueOf(sum));

                   *//* double totalCount = allproductModels.get(position).getTotalCount();
                    String productPrice = allproductModels.get(position).getProductPrice();
                    double totalCountMinusValue = totalCount - Double.parseDouble(productPrice);
                    double subTotal = Double.parseDouble(productPrice) * sum;*//*
                    *//* allproductModels.get(position).setTotalCount(totalCountMinusValue);
                    allproductModels.get(position).setSubTotal(subTotal);*//*
                    //asynResultListener.passItemValue(sum);
                    // asynResultListenerMinus.success(allproductModels.get(position));
                    // asynResultListener.success(discountModels.get(position));
                    // asynResultListenerModel.success(allproductModels.get(position));
                    //  Toast.makeText(v.getContext(), "Your item is subtracted from cart", Toast.LENGTH_SHORT).show();
                    //divide();
                    // subTotal();

                }

            }

            private void divide() {
                if (allproductModels.get(position).getTotalCount() > 0) {
                    double totalCount = allproductModels.get(position).getTotalCount();
                    double mini = Double.parseDouble(allproductModels.get(position).getProductPrice());
                    totalCount = totalCount - mini;
                    allproductModels.get(position).setTotalCount(totalCount);
                }
            }

            private void subTotal() {
                if (allproductModels.get(position).getItemCount() > 0) {
                    double subtotal3 = allproductModels.get(position).getSubTotal();
                    double mini = Double.parseDouble(allproductModels.get(position).getProductPrice());
                    subtotal3 = subtotal3 - mini;
                    allproductModels.get(position).setSubTotal(subtotal3);
                    totalCount = totalCount + allproductModels.get(position).getSubTotal();
                    allproductModels.get(position).setTotalCount(totalCount);
                    //holder.subTotal.setText(String.valueOf(discountModel.getSubTotal()));
                }
            }
        });*/
    }

    public void filterList(ArrayList<AllProductsModel> filterdNames) {
        allproductModels.clear();
        allproductModels = filterdNames;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return allproductModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description, newRate, itemCount, oldRate;
        ImageView titleImage, defaultImage;
        Button addButton;
        ImageButton minusButton, plusButton;
        RelativeLayout productLayout;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.allProductTitle);
            description = (TextView) view.findViewById(R.id.allProductDescription);
            newRate = (TextView) view.findViewById(R.id.allProductNewRate);
            titleImage = (ImageView) view.findViewById(R.id.allProductTitleImage);
           // addButton = (Button) view.findViewById(R.id.addButton);
            itemCount = (TextView) view.findViewById(R.id.itemCount);
            plusButton = (ImageButton) view.findViewById(R.id.plusButton);
            minusButton = (ImageButton) view.findViewById(R.id.minusButton);
            productLayout = (RelativeLayout) view.findViewById(R.id.relativeLayoutProduct);

        }

    }
}
