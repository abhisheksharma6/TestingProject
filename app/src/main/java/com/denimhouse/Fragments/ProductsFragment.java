package com.denimhouse.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.denimhouse.Activity.AsynResult;
import com.denimhouse.Activity.MainDashboardActivity;
import com.denimhouse.DataBaseHelpers.DatabaseHelper;
import com.denimhouse.Models.AllProductsModel;
import com.denimhouse.Activity.ViewDetail;
import com.denimhouse.Adapters.AllProductAdapter;
import com.denimhouse.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by vishal mahajan on 8/12/2017.
 */
public class ProductsFragment extends Fragment {

    public static final String FRAGMENT_TAG = "ProductsFragment";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Handler mHandler;
    EditText search;
    private RecyclerView recyclerView;
    private AllProductAdapter madapter;
    private ArrayList<AllProductsModel> allProductList;
    AsynResult<AllProductsModel> asynResultListener;
    AsynResult<AllProductsModel> asynResultListenerMinus;
    Context context;
    ProgressBar progressBar;
    AllProductsModel allProductModel,allProductsModel;
    List<AllProductsModel> allProductsModelList = new ArrayList<>();
    LinearLayout searchBar;
    DatabaseHelper databaseHelper;
    public String parent_id = "3";

    public ProductsFragment(Context context, AsynResult<AllProductsModel> asynResultListener,AllProductsModel allProductsModel) {
        // Required empty public constructor
        this.context = context;
        this.asynResultListener = asynResultListener;
       this.allProductsModel = allProductsModel;
        this.asynResultListenerMinus = asynResultListenerMinus;

    }

    public ProductsFragment() {


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_3, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        search = (EditText) view.findViewById(R.id.search);
        searchBar = (LinearLayout) view.findViewById(R.id.searchBar);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        databaseHelper = new DatabaseHelper(context);

        allProductList = new ArrayList<>();
        search.setVisibility(View.GONE);
        setAdapter();
        progressBar.setVisibility(View.VISIBLE);
        getAllProducts();

        ((MainDashboardActivity) getActivity()).passVal(new AsynResult<AllProductsModel>() {
            @Override
            public void success(AllProductsModel allProductsModel) {

            }

            @Override
            public void failure(String error) {

            }

            @Override
            public void passItemValue(int value) {
                if (value == 1) {
                    doSearch();
                } else if (value == 0 && search.getVisibility() == View.VISIBLE) {
                    if (allProductList.size() > 0) {
                        allProductList.clear();
                        search.getText().clear();
                        search.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        getAllProducts();
                        madapter.notifyDataSetChanged();
                    } else {
                        search.getText().clear();
                        getAllProducts();
                        search.setVisibility(View.GONE);
                    }

                }
            }
        });

        //madapter = new AllProductAdapter(getContext(), allProductList, asynResultListener);


        // doSearch();

        mHandler = new Handler();
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_1,
                R.color.refresh_progress_1);
        // mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (allProductList.size() > 0) {
                            allProductList.clear();
                            search.setVisibility(View.GONE);
                            getAllProducts();
                            madapter.notifyDataSetChanged();
                        }

                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);

            }
        });

  /*      view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        search.setVisibility(View.GONE);
                        return true;
                    }
                }
                return true;
            }
        });*/


        return view;
    }

    private void clearBackStack() {
        FragmentManager manager = getFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    private void doSearch() {
        //recyclerView.setVisibility(View.GONE);
        search.setVisibility(View.VISIBLE);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = search.getText().toString().toLowerCase(Locale.getDefault());
                filter(text);
               /* String text = search.getText().toString().toLowerCase(Locale.getDefault());
                filter(text);*/
       /*         String text = s.toString().toLowerCase();
                final List<String> filteredList = new ArrayList<>();
                for(int i=0; i< allProductList.size(); i++){
                    final String text1 = allProductList.get(i).getProductName().toLowerCase();
                    if(text1.contains(text)){
                        filteredList.add(allProductList.get(i).getProductName());
                    }
                }

                madapter.notifyDataSetChanged();*/

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
    }

    public void filter(String text) {

        //new array list that will hold the filtered data
        ArrayList<AllProductsModel> filterdNames = new ArrayList<>();
        if (allProductList.size() > 0) {
            filterdNames.addAll(allProductList);
            allProductList.clear();
        }
        //looping through existing elements
        for (AllProductsModel s : filterdNames) {
            //if the existing elements contains the search input
            if (s.getProductName().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                allProductList.add(s);

            }
        }

        //calling a method of the adapter class and passing the filtered list
        // madapter.filterList(filterdNames);
        madapter.notifyDataSetChanged();

    }

    /* public void filter(String charText) {
         final List<AllProductsModel> filteredModelList = new ArrayList<>();
         if(filteredModelList.size() > 0)
             filteredModelList.clear();
          charText = charText.toLowerCase(Locale.getDefault());


         for (AllProductsModel model : allProductList) {
             final String text = model.getProductName().toLowerCase();
             if (text.contains(charText)) {
                 filteredModelList.add(model);
             }
         }




      *//*    if (charText.length() == 0) {
            allProductList.addAll(filteredList);
        } else {
            for (AllProductsModel name : allProductList) {
                if (name.getProductName().toLowerCase(Locale.getDefault()).contains(charText)) {

                    filteredList.add(name);
                }
            }
        }*//*

       //search.setVisibility(View.GONE);

    }
*/
    public void setAdapter() {
        madapter = new AllProductAdapter(getContext(), allProductList, asynResultListener, asynResultListenerMinus, asynResultDetailView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.hasFixedSize();
        recyclerView.setAdapter(madapter);
    }

   /* AsynResult<AllProductsModel> asynResultListenerModel = new AsynResult<AllProductsModel>() {
        @Override
        public void success(AllProductsModel allProductsModel) {
            allProductsModel.setParent_id("3");
            allProductsModelList.add(allProductsModel);
            Boolean isItemExist = false;
            List<AllProductsModel> model = new ArrayList<>();
            model = databaseHelper.getData();
            for (AllProductsModel allProductsModels : model) {
                if (allProductsModel.getParent_id().equals(allProductsModels.getParent_id()) && allProductsModel.getId().equals(allProductsModels.getId())) {
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


            ((MainDashboardActivity) getActivity()).dispatchInformations3(allProductsModelList);
        }

        @Override
        public void failure(String error) {

        }

        @Override
        public void passItemValue(int value) {

        }
    };
*/
    AsynResult<AllProductsModel> asynResultDetailView = new AsynResult<AllProductsModel>() {
        @Override
        public void success(AllProductsModel allProductsModel) {
            allProductsModel.setSubTotal(Double.valueOf(allProductsModel.getProductPrice()));
            Intent intent=new Intent(context.getApplicationContext(), ViewDetail.class);
            intent.putExtra("AllProductsModel",allProductsModel);
            intent.putExtra("Parent_id", "3");
            startActivity(intent);
            }
        @Override
        public void failure(String error) {

        }

        @Override
        public void passItemValue(int value) {

        }
    };

    public void getAllProducts() {

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String URL = "http://vertosys.com/denimhouse/webservices/products.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                Log.e("Response", response.toString());
                progressBar.setVisibility(View.GONE);
                try {
                    response.getString("message");
                    Log.e("Data", response.getString("message"));
                    JSONArray jsonObj = new JSONArray(response.getString("data"));
                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject jsonObject1 = jsonObj.getJSONObject(i);
                        String id = jsonObject1.getString("id");
                        String productName = jsonObject1.getString("prod_name");
                        String categoryId = jsonObject1.getString("category_id");
                        String productPrice = jsonObject1.getString("prod_price");
                        String discountType = jsonObject1.getString("discount_type");
                        String discountValue = jsonObject1.getString("discount_value");
                        String discountPrice = jsonObject1.getString("discount_price");
                        String productDescription = jsonObject1.getString("prod_desc");
                        String productImage = jsonObject1.getString("prod_image");
                        String status = jsonObject1.getString("status");
                        String categoryName = jsonObject1.getString("category_name");

                        allProductModel = new AllProductsModel(id, productName, productDescription, productPrice, productImage, 0, 0,null);
                        allProductList.add(allProductModel);

                    }
                    madapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(jsonObjectRequest);
    }

/*    public boolean onBackPressed() {
        // currently visible tab Fragment
        OnBackPressListener currentFragment = (OnBackPressListener) adapter.getRegisteredFragment(pager.getCurrentItem());

        if (currentFragment != null) {
            // lets see if the currentFragment or any of its childFragment can handle onBackPressed
            return currentFragment.onBackPressed();
        }

        // this Fragment couldn't handle the onBackPressed call
        return false;
    }*/

}
