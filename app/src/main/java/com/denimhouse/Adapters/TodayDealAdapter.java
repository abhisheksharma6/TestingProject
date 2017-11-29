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
 * Created by vishal mahajan on 8/12/2017.
 */
public class TodayDealAdapter extends RecyclerView.Adapter<TodayDealAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<AllProductsModel> todayDealModels;
    AsynResult<AllProductsModel> asynResultListener;
    AsynResult<AllProductsModel> asynResultListenerModel;
    AsynResult<AllProductsModel> asynResultListenerMinus;
    AsynResult<AllProductsModel> asynResultDetailView;
    double total;
    double totalCount = 0;
    int multi, particularItemCount = 0;
    private int[] a = new int[50];

    public TodayDealAdapter(Context mContext, ArrayList<AllProductsModel> todayDealModels, AsynResult<AllProductsModel> asynResultListener,
                            AsynResult<AllProductsModel> asynResultListenerModel, AsynResult<AllProductsModel> asynResultListenerMinus,
                            AsynResult<AllProductsModel> asynResultDetailView) {
        this.mContext = mContext;
        this.todayDealModels = todayDealModels;
        this.asynResultListener = asynResultListener;
        this.asynResultListenerModel = asynResultListenerModel;
        this.asynResultListenerMinus = asynResultListenerMinus;
        this.asynResultDetailView = asynResultDetailView;
    }


    @Override
    public TodayDealAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.today_deal_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TodayDealAdapter.MyViewHolder holder, final int position) {
        final AllProductsModel todayDealModel = todayDealModels.get(position);
        holder.title.setText(todayDealModel.getProductName());
        holder.description.setText(todayDealModel.getProductDescription());
        holder.newRate.setText(todayDealModel.getProductPrice());
        Glide.with(mContext).load(AppUrl.IMAGE_URL + todayDealModel.getProductImage()).into(holder.titleImage);
        todayDealModels.get(position).setItemCount(0);

        holder.todayDealLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             asynResultDetailView.success(todayDealModels.get(position));
            }
        });

/*
        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sum = todayDealModels.get(position).getItemCount();

                if(sum == 0){
                    Toast.makeText(v.getContext(), "You have to select atleast one item", Toast.LENGTH_SHORT).show();
                } else {
                    total = Double.parseDouble(todayDealModels.get(position).getProductPrice());
                    int multi = todayDealModels.get(position).getItemCount();
                    total = total * multi;
                    // a = new int[multi];
                    totalCount = totalCount + total;
                    todayDealModels.get(position).setTotalCount(totalCount);
                    todayDealModels.get(position).setSubTotal(total);
                    asynResultListener.passItemValue(todayDealModels.get(position).getItemCount());
                    asynResultListener.success(todayDealModels.get(position));
                    asynResultListenerModel.success(todayDealModels.get(position));
                    Toast.makeText(v.getContext(), "Your item is added to shopping cart", Toast.LENGTH_SHORT).show();
                    // a[position] = sum;
                }

               */
/* if (sum == 25) {
                    total = Double.parseDouble(todayDealModels.get(position).getProductPrice());
                    int multi = todayDealModels.get(position).getItemCount();
                    total = total * multi;
                    // a = new int[multi];
                    totalCount = totalCount + total;
                    todayDealModels.get(position).setTotalCount(totalCount);
                    todayDealModels.get(position).setSubTotal(total);
                    asynResultListener.passItemValue(todayDealModels.get(position).getItemCount());
                    asynResultListener.success(todayDealModels.get(position));
                    asynResultListenerModel.success(todayDealModels.get(position));
                    Toast.makeText(v.getContext(), "Your item is added to shopping cart", Toast.LENGTH_SHORT).show();
                    a[position] = sum;
                } else if (a[position] == 0 && a[position] < sum) {
                    total = Double.parseDouble(todayDealModels.get(position).getProductPrice());
                    int multi = todayDealModels.get(position).getItemCount();
                    total = total * multi;
                    // a = new int[multi];
                    totalCount = totalCount + total;
                    todayDealModels.get(position).setTotalCount(totalCount);
                    todayDealModels.get(position).setSubTotal(total);
                    asynResultListener.passItemValue(todayDealModels.get(position).getItemCount());
                    asynResultListener.success(todayDealModels.get(position));
                    asynResultListenerModel.success(todayDealModels.get(position));
                    Toast.makeText(v.getContext(), "Your item is added to shopping cart", Toast.LENGTH_SHORT).show();
                    a[position] = sum;
                } else if (a[position] != 0 && a[position] < sum) {
                    total = Double.parseDouble(todayDealModels.get(position).getProductPrice());
                    int multi = todayDealModels.get(position).getItemCount();
                    *//*
*/
/*if (multi >= 1) {
                        particularItemCount = multi - 1;
                        multi = multi - particularItemCount;
                    }*//*
*/
/*
                    double subTotal = total * multi;
                    sum = sum - a[position];
                    total = total * sum;
                    totalCount = totalCount + total;
                    todayDealModels.get(position).setTotalCount(totalCount);
                    todayDealModels.get(position).setSubTotal(subTotal);

                    asynResultListener.passItemValue(sum);
                    asynResultListener.success(todayDealModels.get(position));
                    asynResultListenerModel.success(todayDealModels.get(position));
                    Toast.makeText(v.getContext(), "Your item is added to shopping cart", Toast.LENGTH_SHORT).show();
                    a[position] = todayDealModels.get(position).getItemCount();
                }
*//*

                // holder.addButton.setClickable(false);

            }
        });
*/

        // holder.itemCount.setText(String.valueOf(todayDealModel.getItemCount()));

      /*  holder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // holder.addButton.setClickable(true);
                String sum = holder.itemCount.getText().toString();
                int sum1 = Integer.parseInt(sum) + 1;
                todayDealModels.get(position).setItemCount(sum1);
                holder.itemCount.setText(String.valueOf(todayDealModels.get(position).getItemCount()));

                // subTotal();
                //  multiply();

            }*/

           /* private void subTotal() {
                total = Double.parseDouble(todayDealModels.get(position).getProductPrice());
                int multi = todayDealModels.get(position).getItemCount();
                total = total * multi;
                todayDealModels.get(position).setSubTotal(total);
            }

            private void multiply() {
                total = Double.parseDouble(todayDealModels.get(position).getProductPrice());
                multi = todayDealModels.get(position).getItemCount();
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
                todayDealModels.get(position).setSubTotal(total);
                // totalCount = totalCount + todayDealModels.get(position).getSubTotal();
                totalCount = totalCount + Double.parseDouble(todayDealModels.get(position).getProductPrice());
                todayDealModels.get(position).setTotalCount(totalCount);
                // holder.subTotal.setText(String.valueOf(discountModel.getSubTotal()));
            }


        });
*/
       /* holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (todayDealModels.get(position).getItemCount() > 0) {
                    String sum1 = holder.itemCount.getText().toString();
                    int sum = Integer.parseInt(sum1) - 1;
                    todayDealModels.get(position).setItemCount(sum);
                    holder.itemCount.setText(String.valueOf(sum));

                    *//* double totalCount = todayDealModels.get(position).getTotalCount();
                    String productPrice = todayDealModels.get(position).getProductPrice();
                    double totalCountMinusValue = totalCount - Double.parseDouble(productPrice);
                    double subTotal = Double.parseDouble(productPrice) * sum;
                    todayDealModels.get(position).setItemCount(sum);
                    holder.itemCount.setText(String.valueOf(sum));
                    todayDealModels.get(position).setTotalCount(totalCountMinusValue);
                    todayDealModels.get(position).setSubTotal(subTotal);*//*
                    //asynResultListener.passItemValue(sum);
                    //asynResultListenerMinus.success(todayDealModels.get(position));
                    // asynResultListener.success(discountModels.get(position));
                    // asynResultListenerModel.success(todayDealModels.get(position));
                    // Toast.makeText(v.getContext(), "Your item is subtracted from cart", Toast.LENGTH_SHORT).show();
                    //divide();
                    // subTotal();

                }


            }

            private void divide() {
                if (todayDealModel.getTotalCount() > 0) {
                    double totalCount = todayDealModels.get(position).getTotalCount();
                    double mini = Double.parseDouble(todayDealModels.get(position).getProductPrice());
                    totalCount = totalCount - mini;
                    todayDealModels.get(position).setTotalCount(totalCount);

                }
            }

            private void subTotal() {
                if (todayDealModel.getItemCount() > 0) {
                    double subtotal3 = todayDealModels.get(position).getSubTotal();
                    double mini = Double.parseDouble(todayDealModels.get(position).getProductPrice());
                    subtotal3 = subtotal3 - mini;
                    todayDealModels.get(position).setSubTotal(subtotal3);
                    totalCount = totalCount + todayDealModels.get(position).getSubTotal();
                    todayDealModels.get(position).setTotalCount(totalCount);
                    //holder.subTotal.setText(String.valueOf(discountModel.getSubTotal()));
                }
            }

        });*/
    }

    @Override
    public int getItemCount() {
       /* return todayDealModels.size();*/
        return todayDealModels.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description, newRate, itemCount, oldRate;
        ImageView titleImage, defaultImage;
        Button addButton;
        ImageButton minusButton, plusButton;
        RelativeLayout todayDealLayout;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.todayDealTitle);
            description = (TextView) view.findViewById(R.id.todayDealDescription);
            newRate = (TextView) view.findViewById(R.id.todayDealNewRate);
            titleImage = (ImageView) view.findViewById(R.id.todayDealTitleImage);
           // addButton = (Button) view.findViewById(R.id.addButton);
            itemCount = (TextView) view.findViewById(R.id.itemCount);
            plusButton = (ImageButton) view.findViewById(R.id.plusButton);
            minusButton = (ImageButton) view.findViewById(R.id.minusButton);
            todayDealLayout = (RelativeLayout) view.findViewById(R.id.todayDealLayout);
        }

    }
}
