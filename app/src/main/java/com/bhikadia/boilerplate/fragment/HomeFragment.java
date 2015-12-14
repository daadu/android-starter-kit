package com.bhikadia.boilerplate.fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.bhikadia.boilerplate.R;
import com.bhikadia.boilerplate.adapter.ItemListAdapter;
import com.bhikadia.boilerplate.api.BaseApi;
import com.bhikadia.boilerplate.api.ListApi;
import com.bhikadia.boilerplate.data.ItemDbHandler;
import com.bhikadia.boilerplate.util.RecyclerTouchListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private Cursor cursor;
    private ItemListAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListApi listApi;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        cursor = ItemDbHandler.getInstance(getActivity()).getAll();

        adapter = new ItemListAdapter(getActivity(), cursor);
        recyclerView.setAdapter(adapter);


        getLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                return ItemDbHandler.getInstance(getContext()).getAllCursorLoader();
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                adapter.swapCursor(data);
                cursor = data;
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                adapter.swapCursor(null);
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchItems();
            }
        });

        return rootView;
    }

    private void fetchItems() {
        swipeRefreshLayout.setRefreshing(true);
        if (listApi == null)
            listApi = new ListApi();

        listApi.setApiListener(new BaseApi.ApiListener() {
            @Override
            public void onSuccessful() {

            }

            @Override
            public void onError(String errorMessage) {

            }

            @Override
            public void onVolleyError(VolleyError error) {

            }

            @Override
            public void onParsedData(Object object) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        listApi.call();
    }


}
