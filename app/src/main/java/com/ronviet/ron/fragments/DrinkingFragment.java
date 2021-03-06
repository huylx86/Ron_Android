package com.ronviet.ron.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ronviet.ron.R;
import com.ronviet.ron.adapters.ProductRecyclerViewAdapter;
import com.ronviet.ron.models.ProductInfo;
import com.ronviet.ron.models.TableInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DrinkingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DrinkingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrinkingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private TableInfo mTableInfo;

    private ProductRecyclerViewAdapter mAdapterProduct;
    private List<ProductInfo> mLstProducts;

    public DrinkingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FoodFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DrinkingFragment newInstance(TableInfo tableInfo) {
        DrinkingFragment fragment = new DrinkingFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, tableInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTableInfo = (TableInfo) getArguments().getSerializable(ARG_PARAM1);
        }
        dummyData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_product, container, false);

        RecyclerView recyclerView = (RecyclerView)root.findViewById(R.id.recycler_view_product);
        GridLayoutManager tableLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(tableLayoutManager);
        mAdapterProduct = new ProductRecyclerViewAdapter(getContext(), mLstProducts, mTableInfo, false);
        recyclerView.setAdapter(mAdapterProduct);

        return root;
    }

    private void dummyData()
    {
        mLstProducts = new ArrayList<>();
        for(int i=0; i<20; i++) {
            ProductInfo info = new ProductInfo();
            info.setId(i);
            info.setName("Product Drinking " + i);
            info.setDes("Des " + i);
            mLstProducts.add(info);
        }
    }

}
