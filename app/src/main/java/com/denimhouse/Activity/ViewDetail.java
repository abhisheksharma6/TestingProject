package com.denimhouse.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denimhouse.DataBaseHelpers.DatabaseHelper;
import com.denimhouse.Models.AllProductsModel;
import com.denimhouse.Network.AppUrl;
import com.denimhouse.Fragments.ShoppingCartFragment;
import com.denimhouse.R;

import java.util.ArrayList;
import java.util.List;

public class ViewDetail extends AppCompatActivity implements View.OnClickListener{
    String description, name, price, image, size;
    TextView productDescription, productName, productPrice, productSize;
    TextView selectedSize, sizeText;
    TextView small,medium, large, xLarge, xxLarge;
    ImageView imageView, backButton;
    Button addToCart, change;
    private ArrayList<AllProductsModel> viewDetailModels;
    AsynResult<AllProductsModel> asynResultListener;
    AsynResult<AllProductsModel> asynResultListenerModel;
    AsynResult<AllProductsModel> asynResultListenerDetailView;
    List<AllProductsModel> allProductsModelList = new ArrayList<>();
    double total;
    double totalCount = 0;
    Context context;
    int sum = 0;
    AllProductsModel allProductsModel;
    ShoppingCartFragment shoppingCartFragment;
    DatabaseHelper databaseHelper;
    public String parent_id;



    public ViewDetail(Context context, ArrayList<AllProductsModel> viewDetailModels, AsynResult<AllProductsModel> asynResultListener, AsynResult<AllProductsModel> asynResultListenerModel){
        this.context = context;
        this.viewDetailModels = viewDetailModels;
        this.asynResultListener = asynResultListener;
        this.asynResultListenerModel = asynResultListenerModel;
    }

    public ViewDetail(Context context, AsynResult<AllProductsModel> asynResultListenerDetailView){
        this.context = context;
        this.asynResultListenerDetailView = asynResultListenerDetailView;

    }

    public ViewDetail(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail);

        Intent intent = getIntent();
        allProductsModel = (AllProductsModel) intent.getParcelableExtra("AllProductsModel");
        parent_id = intent.getStringExtra("Parent_id");
        databaseHelper = new DatabaseHelper(this);
        /* description = intent.getStringExtra("Description");
        name = intent.getStringExtra("Name");
        price = intent.getStringExtra("price");
        image = intent.getStringExtra("Image");*/

        productDescription = (TextView) findViewById(R.id.description);
        productName = (TextView) findViewById(R.id.productName);
        productPrice = (TextView) findViewById(R.id.prize);
        productSize = (TextView) findViewById(R.id.size);
        selectedSize = (TextView) findViewById(R.id.size);
        small = (TextView) findViewById(R.id.small);
        medium = (TextView) findViewById(R.id.medium);
        large = (TextView) findViewById(R.id.large);
        xLarge = (TextView) findViewById(R.id.xLarge);
        xxLarge = (TextView) findViewById(R.id.xxLarge);
        imageView = (ImageView) findViewById(R.id.image);
        addToCart = (Button) findViewById(R.id.addToCart);
        change = (Button) findViewById(R.id.change);
        backButton = (ImageView) findViewById(R.id.back_button_view_details);
        sizeText = (TextView) findViewById(R.id.sizeText);
        //viewDetailModels = new ArrayList<>();

        addToCart.setOnClickListener(this);
        change.setOnClickListener(this);
        small.setOnClickListener(this);
        medium.setOnClickListener(this);
        large.setOnClickListener(this);
        xLarge.setOnClickListener(this);
        xxLarge.setOnClickListener(this);
        backButton.setOnClickListener(this);

        setValues();
    }

    public void setValues(){
        productDescription.setText(allProductsModel.getProductDescription());
        productName.setText(allProductsModel.getProductName());
        productPrice.setText(allProductsModel.getProductPrice());
        Glide.with(this).load(AppUrl.IMAGE_URL + allProductsModel.getProductImage()).into(imageView);
    }


    public void addToCart(){
        size = selectedSize.getText().toString();
        allProductsModel.setSize(size);
        storeDataToDatabase();
        Toast.makeText(ViewDetail.this, "Your item has been added to shopping cart", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ViewDetail.this, MainDashboardActivity.class);
        intent.putExtra("productAdd",allProductsModel);
        startActivity(intent);
    }

    public void storeDataToDatabase(){

        if(parent_id.equals("1")){
            allProductsModel.setSubTotal(Double.valueOf(allProductsModel.getProductPrice()));
            allProductsModel.setItemCount(1);
            allProductsModel.setParent_id("1");
            allProductsModelList.add(allProductsModel);
            Boolean isItemExist = false;
            List<AllProductsModel> model = new ArrayList<>();
            model = databaseHelper.getData();
            for (AllProductsModel allProductsModels : model) {
                if (allProductsModel.getParent_id().equals(allProductsModels.getParent_id())
                        && allProductsModel.getId().equals(allProductsModels.getId())
                        && allProductsModel.getSize().contains(allProductsModels.getSize()))
                {
                    int old_value = allProductsModel.getItemCount();
                    int new_value = allProductsModels.getItemCount();
                    int total = old_value + new_value;
                    Double old_value_subtotal = allProductsModel.getSubTotal();
                    Double new_value_subtotal = allProductsModels.getSubTotal();
                    Double totalSubTotal = old_value_subtotal + new_value_subtotal;
                    allProductsModel.setSubTotal(totalSubTotal);
                    allProductsModel.setItemCount(total);
                    isItemExist = true;
                    break;
                } else {
                    isItemExist = false;
                }
            }
            if (isItemExist) {
                databaseHelper.updateRecord(allProductsModel);
            } else {
                databaseHelper.insert(allProductsModel, parent_id);
            }
        }

         else if(parent_id.equals("2")){
             allProductsModel.setSubTotal(Double.valueOf(allProductsModel.getProductPrice()));
             allProductsModel.setItemCount(1);
             allProductsModel.setParent_id("2");
             allProductsModelList.add(allProductsModel);
             Boolean isItemExist = false;
             List<AllProductsModel> model = new ArrayList<>();
             model = databaseHelper.getData();
             for (AllProductsModel allProductsModels : model) {
                 if (allProductsModel.getParent_id().equals(allProductsModels.getParent_id())
                         && allProductsModel.getId().equals(allProductsModels.getId())
                         && allProductsModel.getSize().contains(allProductsModels.getSize()))
                 {
                     int old_value = allProductsModel.getItemCount();
                     int new_value = allProductsModels.getItemCount();
                     int total = old_value + new_value;
                     Double old_value_subtotal = allProductsModel.getSubTotal();
                     Double new_value_subtotal = allProductsModels.getSubTotal();
                     Double totalSubTotal = old_value_subtotal + new_value_subtotal;
                     allProductsModel.setSubTotal(totalSubTotal);
                     allProductsModel.setItemCount(total);
                     isItemExist = true;
                     break;
                 } else {
                     isItemExist = false;
                 }
             }
             if (isItemExist) {
                 databaseHelper.updateRecord(allProductsModel);
             } else {
                 databaseHelper.insert(allProductsModel, parent_id);
             }

         }

         else if(parent_id.equals("3")) {
            allProductsModel.setSubTotal(Double.valueOf(allProductsModel.getProductPrice()));
            allProductsModel.setItemCount(1);
            allProductsModel.setParent_id("3");
            allProductsModelList.add(allProductsModel);
            Boolean isItemExist = false;
            List<AllProductsModel> model = new ArrayList<>();
            model = databaseHelper.getData();
            for (AllProductsModel allProductsModels : model) {
                if (allProductsModel.getParent_id().equals(allProductsModels.getParent_id())
                        && allProductsModel.getId().equals(allProductsModels.getId())
                        && allProductsModel.getSize().contains(allProductsModels.getSize()))
                {

                    int new_value = allProductsModel.getItemCount();
                    int old_value = allProductsModels.getItemCount();
                    int total = old_value + new_value;
                    Double new_value_subtotal = allProductsModel.getSubTotal();
                    Double old_value_subtotal = allProductsModels.getSubTotal();
                    Double totalSubTotal = old_value_subtotal + new_value_subtotal;
                    allProductsModel.setSubTotal(totalSubTotal);
                    allProductsModel.setItemCount(total);
                    isItemExist = true;
                    break;
                } else {
                    isItemExist = false;
                }
            }
            if (isItemExist) {
                databaseHelper.updateRecord(allProductsModel);
            } else {
                databaseHelper.insert(allProductsModel, parent_id);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.addToCart):
                if(selectedSize.getText().toString().equals("")){
                    Toast.makeText(ViewDetail.this, "Please select product size", Toast.LENGTH_SHORT).show();
                }
                else{
                    addToCart();
                }
                break;
            case (R.id.change):
                finish();
                break;
            case (R.id.small):
                String textValueSmall = small.getText().toString();
                sizeText.setText("Size:");
                selectedSize.setText(textValueSmall);
                small.setBackgroundResource(R.drawable.size_background_on_selection);
                medium.setBackgroundResource(R.drawable.size_background_two);
                large.setBackgroundResource(R.drawable.size_background_two);
                xLarge.setBackgroundResource(R.drawable.size_background_two);
                xxLarge.setBackgroundResource(R.drawable.size_background_two);
                break;
            case (R.id.medium):
                String textValueMedium = medium.getText().toString();
                sizeText.setText("Size:");
                selectedSize.setText(textValueMedium);
                medium.setBackgroundResource(R.drawable.size_background_on_selection);
                small.setBackgroundResource(R.drawable.size_background_two);
                large.setBackgroundResource(R.drawable.size_background_two);
                xLarge.setBackgroundResource(R.drawable.size_background_two);
                xxLarge.setBackgroundResource(R.drawable.size_background_two);
                break;
            case (R.id.large):
                String textValueLarge = large.getText().toString();
                sizeText.setText("Size:");
                selectedSize.setText(textValueLarge);
                large.setBackgroundResource(R.drawable.size_background_on_selection);
                medium.setBackgroundResource(R.drawable.size_background_two);
                small.setBackgroundResource(R.drawable.size_background_two);
                xLarge.setBackgroundResource(R.drawable.size_background_two);
                xxLarge.setBackgroundResource(R.drawable.size_background_two);

                break;
            case (R.id.xLarge):
                String textValueXLarge = xLarge.getText().toString();
                sizeText.setText("Size:");
                selectedSize.setText(textValueXLarge);
                xLarge.setBackgroundResource(R.drawable.size_background_on_selection);
                small.setBackgroundResource(R.drawable.size_background_two);
                large.setBackgroundResource(R.drawable.size_background_two);
                medium.setBackgroundResource(R.drawable.size_background_two);
                xxLarge.setBackgroundResource(R.drawable.size_background_two);

                break;
            case (R.id.xxLarge):
                String textValueXXLarge = xxLarge.getText().toString();
                sizeText.setText("Size:");
                selectedSize.setText(textValueXXLarge);
                xxLarge.setBackgroundResource(R.drawable.size_background_on_selection);
                small.setBackgroundResource(R.drawable.size_background_two);
                large.setBackgroundResource(R.drawable.size_background_two);
                xLarge.setBackgroundResource(R.drawable.size_background_two);
                medium.setBackgroundResource(R.drawable.size_background_two);
                break;
            case (R.id.back_button_view_details):
                finish();
                break;
        }
    }
}
