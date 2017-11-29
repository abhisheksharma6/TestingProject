package com.denimhouse.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denimhouse.DataBaseHelpers.DatabaseHelper;
import com.denimhouse.Models.AllProductsModel;
import com.denimhouse.Models.TodayDealModel;
import com.denimhouse.Adapters.TabsPagerAdapter;
import com.denimhouse.Fragments.MyAccountFragment;
import com.denimhouse.Fragments.ProductsFragment;
import com.denimhouse.Fragments.ShoppingCartFragment;
import com.denimhouse.Fragments.DiscountFragment;
import com.denimhouse.Fragments.TodayDealsFragment;
import com.denimhouse.R;

import java.util.ArrayList;
import java.util.List;

public class MainDashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    public static final String MyPREFERENCES = "Session";
    SharedPreferences sharedpreferences;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private static ViewPager viewPager;
    TextView counter, heading, itemCount1;
    RelativeLayout basket;
    ShoppingCartFragment shoppingCartFragment;
    List<AllProductsModel> discountModelList;
    List<AllProductsModel> todayDealModelList;
    List<AllProductsModel> allProductModelList;
    ArrayList<AllProductsModel> viewDetailModelList;
    AsynResult<AllProductsModel> asynResult;
    LinearLayout linearLayout;
    LinearLayout linearLayout1;
    LinearLayout searchBar;
    AllProductsModel viewDetailsModels;
    double totalCount1 = 0, totalCount2 = 0, totalCount3 = 0, totalCount = 0;
    int checkCounterValue, totalCountOfItem = 0, counter1;
    Context context;
    int count = 0, c;
    double total = 0, t = 0;
    TodayDealModel todayDealModel;
    String title;
    DatabaseHelper databaseHelper;
    double overAllCount = 0;
    Boolean isPressed = true;
    String description,name,size,image,price;
    AllProductsModel allProductsModel;
    ViewDetail viewDetail;

    public MainDashboardActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(2);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        counter = (TextView) findViewById(R.id.textCounter);
        heading = (TextView) findViewById(R.id.heading);
        linearLayout = (LinearLayout) findViewById(R.id.shopping_cart);
        linearLayout1 = (LinearLayout) findViewById(R.id.all_products);
        itemCount1 = (TextView) findViewById(R.id.itemCount1);
        basket = (RelativeLayout) findViewById(R.id.relativeLayout);
        searchBar = (LinearLayout) findViewById(R.id.searchBar);
        basket.setOnClickListener(this);
        linearLayout.setOnClickListener(this);
        linearLayout1.setOnClickListener(this);
        searchBar.setOnClickListener(this);
        databaseHelper = new DatabaseHelper(this);
        viewDetail = new ViewDetail(this, asynResultDetailView);

       /* final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showingShoppingCartFragment();
            }
        }, 5000);*/

        Intent intent = getIntent();
        allProductsModel = (AllProductsModel) intent.getParcelableExtra("productAdd");

        // use this condition later
      /* if(allProductsModel.getSize()!=null){
           int count = Integer.parseInt(counter.getText().toString());
           counter.setText(count+1);
           itemCount1.setText(count+1);

       }*/

     /*   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        ArrayList<AllProductsModel> model;
        model = new ArrayList<>(databaseHelper.getData());
        if (model != null && model.size() > 0) {
            for (int i = 0; i < model.size(); i++) {
                c = model.get(i).getItemCount();
                t = model.get(i).getSubTotal();
                count = count + c;
                overAllCount = overAllCount + t;
            }

        }
      //  count= databaseHelper.getTotalItems();

       /* String str = String.valueOf(databaseHelper.getCount());

        count = Integer.valueOf(str);*/

        counter.setText(String.valueOf(count));
        itemCount1.setText(String.valueOf(count));

        //  todayDealModel = (TodayDealModel) getIntent().getSerializableExtra("Data");

    }

    /*public void checkDuplicateItems(){
        for(int i=0; i<discountModelList.size(); i++){
            itemTitle = discountModelList.get(i).getTitle();
        }
    }*/

    public void passVal(AsynResult<AllProductsModel> asynResult) {
        this.asynResult1 = asynResult;
    }

    @Override
    public void onBackPressed() {

        getSupportActionBar().show();
        tabLayout.setVisibility(View.VISIBLE);
        heading.setText("DENIM HOUSE");
        basket.setVisibility(View.VISIBLE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (count != 0) {
            super.onBackPressed();
        } else if (count == 0) {
            if(isPressed) {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
              //  isPressed = false;
            }else{
                int Result = 0;
                asynResult1.passItemValue(Result);
                isPressed = true;
            }
           // finish();
           /* */
        } else {
           /* FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            transaction.replace(R.id.fragment_Container, tabFragment2, null);
            transaction.addToBackStack(null);
            transaction.commit();*/
            getSupportFragmentManager().popBackStack();
        }
    }

    public void dispatchInformations1(List<AllProductsModel> discountModels) {
        this.discountModelList = discountModels;

    }

    public void dispatchInformations2(List<AllProductsModel> todayDealModel) {
        this.todayDealModelList = todayDealModel;
    }


    AsynResult<AllProductsModel> asynResult1 = new AsynResult<AllProductsModel>() {
        @Override
        public void success(AllProductsModel discountModel) {
            totalCount1 = discountModel.getTotalCount();
            String value = counter.getText().toString();
            totalCountOfItem = Integer.parseInt(value) + counter1;
            counter.setText(String.valueOf(totalCountOfItem));
            itemCount1.setText(String.valueOf(totalCountOfItem));

            discountModel.setTotalCount(totalCount1);
           /* totalCount1 = discountModel.getTotalCount();

            if (counter.getText().equals("0")) {
                counter.setText(String.valueOf(counter1));
                itemCount1.setText(String.valueOf(counter1));

            } else {
                String count = counter.getText().toString();
                int totalCount = Integer.parseInt(count) + counter1;
                counter.setText(String.valueOf(totalCount));
                itemCount1.setText(String.valueOf(totalCount));
            }
            discountModel.setTotalCount(totalCount1);*/
        }

        @Override
        public void failure(String error) {

        }

        @Override
        public void passItemValue(int value) {
            counter1 = value;

        }
    };


    AsynResult<AllProductsModel> asynResultDetailView = new AsynResult<AllProductsModel>() {
        @Override
        public void success(AllProductsModel allProductsModel) {
            int count = Integer.parseInt(counter.getText().toString());
            count = count + 1;
            counter.setText(String.valueOf(count));
            itemCount1.setText(String.valueOf(count));
        }

        @Override
        public void failure(String error) {

        }

        @Override
        public void passItemValue(int value) {

        }
    };



    AsynResult<AllProductsModel> asynResult2 = new AsynResult<AllProductsModel>() {
        @Override
        public void success(AllProductsModel todayDealModel) {
            // double subTotal = todayDealModel.getSubTotal();
            totalCount2 = todayDealModel.getTotalCount();
            String value = counter.getText().toString();
            totalCountOfItem = Integer.parseInt(value) + counter1;
            counter.setText(String.valueOf(totalCountOfItem));
            itemCount1.setText(String.valueOf(totalCountOfItem));

            todayDealModel.setTotalCount(totalCount2);
            /*if (counter.getText().equals("0")) {
                counter.setText(String.valueOf(counter1));
                itemCount1.setText(String.valueOf(counter1));

            } else {
                String count = counter.getText().toString();
                int totalCount = Integer.parseInt(count) + counter1;
                counter.setText(String.valueOf(totalCount));
                itemCount1.setText(String.valueOf(totalCount));
            }*/


            /*count = String.valueOf(getCounter1());
            counter.setText(count);
            itemCount1.setText(count);*/
        }

        @Override
        public void failure(String error) {

        }

        @Override
        public void passItemValue(int value) {
            counter1 = value;
        }
    };

  /*  AsynResult<AllProductsModel> asynResultMinus2 = new AsynResult<AllProductsModel>() {
        @Override
        public void success(AllProductsModel todayDealModel) {

            totalCount2 = todayDealModel.getTotalCount();
           // String Counter = counter.getText().toString();
            counter.setText(String.valueOf(counter1));
            itemCount1.setText(String.valueOf(counter1));
            String count = counter.getText().toString();
            int totalCount = Integer.parseInt(count) - counter1;
            counter.setText(String.valueOf(totalCount));
            itemCount1.setText(String.valueOf(totalCount));

            todayDealModel.setTotalCount(totalCount2);
        }

        @Override
        public void failure(String error) {

        }

        @Override
        public void passItemValue(int value) {
            counter1 = value;
        }
    };
*/

    AsynResult<AllProductsModel> asynResult3 = new AsynResult<AllProductsModel>() {
        @Override
        public void success(AllProductsModel allProductModel) {
            // double subTotal = allProductModel.getSubTotal();

            totalCount3 = allProductModel.getTotalCount();
            String value = counter.getText().toString();
            totalCountOfItem = Integer.parseInt(value) + counter1;
            counter.setText(String.valueOf(totalCountOfItem));
            itemCount1.setText(String.valueOf(totalCountOfItem));

            allProductModel.setTotalCount(totalCount3);
            /*if (counter.getText().equals("0")) {
                counter.setText(String.valueOf(counter1));
                itemCount1.setText(String.valueOf(counter1));

            } else {
                String count = counter.getText().toString();
                int totalCount = Integer.parseInt(count) + counter1;
                counter.setText(String.valueOf(totalCount));
                itemCount1.setText(String.valueOf(totalCount));
            }*/

            /*count = String.valueOf(getCounter1());
            counter.setText(count);
            itemCount1.setText(count);*/
        }

        @Override
        public void failure(String error) {

        }

        @Override
        public void passItemValue(int value) {
            counter1 = value;
        }
    };

    /*AsynResult<AllProductsModel> asynResultMinus3 = new AsynResult<AllProductsModel>() {
        @Override
        public void success(AllProductsModel allProductModel) {

            totalCount3 = allProductModel.getTotalCount();
            //String Counter = counter.getText().toString();
            //counter.setText(String.valueOf(counter1));
            //itemCount1.setText(String.valueOf(counter1));
            counter.setText(String.valueOf(counter1));
            itemCount1.setText(String.valueOf(counter1));
            String count = counter.getText().toString();
            int totalCount = Integer.parseInt(count) - counter1;
            counter.setText(String.valueOf(totalCount));
            itemCount1.setText(String.valueOf(totalCount));

            allProductModel.setTotalCount(totalCount3);
        }

        @Override
        public void failure(String error) {

        }

        @Override
        public void passItemValue(int value) {
            counter1 = value;
        }
    };
*/

   /*  public void onSearchBarListener(AsynResult1 callback){
        this.callback = callback;
    }*/

    public String passCountValue() {
        return counter.getText().toString();
    }

    public double passTotalCountValue() {
        if (totalCount1 != 0 || totalCount2 != 0 || totalCount3 != 0)
            overAllCount = overAllCount + totalCount1 + totalCount2 + totalCount3;
        return overAllCount;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.myAccount) {
            // calling My Account Fragment
            showingMyAccountFragment();

        } else if (id == R.id.logOut) {
            databaseHelper.removeAll();
            SessionManager sessionManager = new SessionManager(MainDashboardActivity.this);
            sessionManager.logoutUser();

        }

        /*else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {

        TabsPagerAdapter adapter = new TabsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ProductsFragment(this, asynResult3,allProductsModel), "All Products");
        adapter.addFragment(new DiscountFragment(this, asynResult1), "Discounts");
        adapter.addFragment(new TodayDealsFragment(this, asynResult2), "Today Deals");
        viewPager.setAdapter(adapter);
    }


    public void showingShoppingCartFragment() {
        tabLayout.setVisibility(View.GONE);
        heading.setText("Shopping Cart");
        basket.setVisibility(View.INVISIBLE);

        shoppingCartFragment = new ShoppingCartFragment(discountModelList, todayDealModelList, allProductModelList);
        Bundle bundle = new Bundle();
        shoppingCartFragment.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_Container, shoppingCartFragment, null);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public void showingMyAccountFragment() {
        tabLayout.setVisibility(View.GONE);
        heading.setText("My Account");
        basket.setVisibility(View.INVISIBLE);
        MyAccountFragment myAccountFragment = new MyAccountFragment();
        Bundle bundle = new Bundle();
        myAccountFragment.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_Container, myAccountFragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void searchBar() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        int Result = 1;
        viewPager.setCurrentItem(0);
        if (asynResult1 != null)
            asynResult1.passItemValue(Result);

        if (count != 0) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportActionBar().show();
            tabLayout.setVisibility(View.VISIBLE);
            heading.setText("DENIM HOUSE");
            basket.setVisibility(View.VISIBLE);
        }

    }

    public void allProducts() {
        int count1 = getSupportFragmentManager().getBackStackEntryCount();
        viewPager.setCurrentItem(0);

        if (count1 != 0) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportActionBar().show();
            tabLayout.setVisibility(View.VISIBLE);
            heading.setText("DENIM HOUSE");
            basket.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relativeLayout:
                //checkCounterValue = Integer.parseInt(counter.getText().toString());
                if(counter.getText().equals("0")){
                    Toast.makeText(MainDashboardActivity.this, "You dont have any item on shopping cart", Toast.LENGTH_SHORT).show();
                }else{
                    showingShoppingCartFragment();
                }
                break;

            case R.id.shopping_cart:
                int count2 = getSupportFragmentManager().getBackStackEntryCount();
                //checkCounterValue = Integer.parseInt(counter.getText().toString());
                if(counter.getText().equals("0")){
                    Toast.makeText(MainDashboardActivity.this, "You dont have any item on shopping cart", Toast.LENGTH_SHORT).show();
                }else{
                    if(count2 != 0) {
                        Toast.makeText(MainDashboardActivity.this, "You are already on shopping cart", Toast.LENGTH_SHORT).show();

                    }else{
                        showingShoppingCartFragment();
                    }
                }
                break;

            case R.id.all_products:
                int count1 = getSupportFragmentManager().getBackStackEntryCount();
                if(viewPager.getCurrentItem() != 0) {
                    allProducts();
                }else if(count1 != 0){
                    allProducts();
                }
                else{
                    Toast.makeText(MainDashboardActivity.this, "You are already on all products cart", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.searchBar:
                isPressed = false;
                 searchBar();
                break;
            //  fragment = new ProductsFragment();
               /* fragment = (ProductsFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
                getSupportFragmentManager().beginTransaction().detach(fragment).attach(fragment).commit();*/

            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container, fragment, null).commit();

        }

    }

}
