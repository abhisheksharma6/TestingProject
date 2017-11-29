package com.denimhouse.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.denimhouse.Activity.AsynResult;
import com.denimhouse.Activity.AsynResult1;
import com.denimhouse.Activity.CheckOutActivity;
import com.denimhouse.Activity.MainDashboardActivity;
import com.denimhouse.Adapters.ShoppingCartAdapter;
import com.denimhouse.DataBaseHelpers.DatabaseHelper;
import com.denimhouse.Models.AllProductsModel;
import com.denimhouse.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android-Dev2 on 8/16/2017.
 */

public class ShoppingCartFragment extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private ShoppingCartAdapter madapter;
    private ArrayList<AllProductsModel> shoppingCartList;
    // String title,newRate,price,id,itemTitle;
    int count = 0;
    double totalItemCount, priceCount = 0, subTotal;
    TextView itemsCount, totalCount;
    Button checkOut;
    Context context;
    Boolean isItemExist = false;
    List<AllProductsModel> discountModels;
    List<AllProductsModel> todayDealModels;
    List<AllProductsModel> allProductsModels;
    ArrayList<AllProductsModel> viewDetailsModels;
    ArrayList<AllProductsModel> combinedList;
    ArrayList<AllProductsModel> checkOutList = new ArrayList<>();
    AsynResult1 asynResult;

    String id, title, newRate, itemTitle;
    DatabaseHelper databaseHelper;


    public ShoppingCartFragment(List<AllProductsModel> discountModels, List<AllProductsModel> todayDealModels,
                                List<AllProductsModel> allProductsModels)
    {
        this.discountModels = discountModels;
        this.todayDealModels = todayDealModels;
        this.allProductsModels = allProductsModels;
      //  this.viewDetailsModels = viewDetailsModels;
    }

    public ShoppingCartFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        databaseHelper = new DatabaseHelper(context);
    }


    //  @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shopping_cart_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        itemsCount = (TextView) view.findViewById(R.id.itemsCount);
        totalCount = (TextView) view.findViewById(R.id.totalCount);
        checkOut = (Button) view.findViewById(R.id.checkOutButton);
        shoppingCartList = new ArrayList<>();
        combinedList = new ArrayList<>();

        for(int i=0; i<combinedList.size(); i++){
            if(viewDetailsModels.get(i).getId().equals(combinedList.get(i).getId())){
                int old_value = combinedList.get(i).getItemCount();
                int new_value = 1;
                int total = old_value + new_value;
                combinedList.get(i).setItemCount(total);
                isItemExist = true;
                break;
            }else {

            }

        }

      /*  ArrayList<AllProductsModel> model;
        model = new ArrayList<>(databaseHelper.getData());

        if(model != null && model.size() > 0) {
            for (int i = 0; i < model.size(); i++) {

                t = model.get(i).getTotalCount();
                total = total + t;

            }
            totalCount.setText(String.valueOf(total));
        }*/
        ImageView textView = (ImageView) view.findViewById(R.id.back_button);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainDashboardActivity.class);
                intent.putExtra("Key_Data", "0");
                intent.putExtra("Key_Data1", "1");
                startActivity(intent);
                getActivity().finish();
            }
        });


        MainDashboardActivity mainDashboardActivity = (MainDashboardActivity) getActivity();
        String totalCountValue = mainDashboardActivity.passCountValue();
        double itemTotalValue = mainDashboardActivity.passTotalCountValue();
        itemsCount.setText(totalCountValue);
        totalCount.setText(String.valueOf(itemTotalValue));
        checkOut.setOnClickListener(this);
        setAdapter();
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return true;
                    }
                }
                return false;
            }
        });

        return view;

    }


    // @RequiresApi(api = Build.VERSION_CODES.M)
    public void setAdapter() {
        //madapter = new ShoppingCartAdapter(getContext(),discountModels,asynResultListener);
        combinedList = new ArrayList<>(databaseHelper.getData());


       /* if (discountModels != null) {
            combinedList.addAll(discountModels);
        }
        if (todayDealModels != null) {
            combinedList.addAll(todayDealModels);
        }
        if (allProductsModels != null) {
            combinedList.addAll(allProductsModels);
        }

        combinedList = new ArrayList<>(new LinkedHashSet<>(combinedList));*/

        madapter = new ShoppingCartAdapter(getContext(), combinedList, asynResultListenerPlus, asynResultListenerMinus, asynResultListenerRemove);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(madapter);

    }


    AsynResult<AllProductsModel> asynResultListenerPlus = new AsynResult<AllProductsModel>() {
        @Override
        public void success(AllProductsModel discountModel) {
            String totalCount1 = itemsCount.getText().toString();
            count = Integer.parseInt(totalCount1) + 1;
            itemsCount.setText(String.valueOf(count));


            String itemsCount = totalCount.getText().toString();
            totalItemCount = Double.parseDouble(itemsCount) + Double.parseDouble(discountModel.getProductPrice());
            totalCount.setText(String.valueOf(totalItemCount));
            /*subTotal = discountModel.getSubTotal();
            priceCount = priceCount + subTotal;
            totalCount.setText(String.valueOf(priceCount));*/

        }

        @Override
        public void failure(String error) {

        }

        @Override
        public void passItemValue(int value) {

        }
    };

    AsynResult<AllProductsModel> asynResultListenerMinus = new AsynResult<AllProductsModel>() {
        @Override
        public void success(AllProductsModel discountModel) {

            String totalCount1 = itemsCount.getText().toString();
            count = Integer.parseInt(totalCount1) - 1;
            itemsCount.setText(String.valueOf(count));

            String itemsCount = totalCount.getText().toString();
            totalItemCount = Double.parseDouble(itemsCount) - Double.parseDouble(discountModel.getProductPrice());
            totalCount.setText(String.valueOf(totalItemCount));

                /*int newPrice = Integer.parseInt(totalCount2);
                newPrice = newPrice - Integer.parseInt(productPrice);
                totalCount.setText(String.valueOf(newPrice));*/

        }

        @Override
        public void failure(String error) {

        }

        @Override
        public void passItemValue(int value) {

        }
    };


    AsynResult<AllProductsModel> asynResultListenerRemove = new AsynResult<AllProductsModel>() {
        @Override
        public void success(AllProductsModel discountModel) {
            double subTotal = 0;
            double newTotalCount;
            String totalCount1 = itemsCount.getText().toString();
            databaseHelper.delete(discountModel);
            int eachItemCount = discountModel.getItemCount();
            int newItemCount = Integer.parseInt(totalCount1) - eachItemCount;
            itemsCount.setText(String.valueOf(newItemCount));

            String itemsCount = totalCount.getText().toString();

            subTotal = discountModel.getSubTotal();
            newTotalCount = Double.parseDouble(itemsCount) - subTotal;
            totalCount.setText(String.valueOf(newTotalCount));


            /*int eachItemCount = discountModel.getItemCount();
            String totalItemCountString = itemsCount.getText().toString();
            int totalItemCountInt = Integer.parseInt(totalItemCountString) - eachItemCount;
            itemsCount.setText(String.valueOf(totalItemCountInt));
            subTotal = discountModel.getSubTotal();
            priceCount = priceCount + subTotal;
            totalCount.setText(String.valueOf(priceCount));*/
        }

        @Override
        public void failure(String error) {

        }

        @Override
        public void passItemValue(int value) {

        }
    };

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.checkOutButton:
                Intent intent = new Intent(getContext(), CheckOutActivity.class);
                intent.putParcelableArrayListExtra("key", combinedList);
                startActivity(intent);

        }
    }
}