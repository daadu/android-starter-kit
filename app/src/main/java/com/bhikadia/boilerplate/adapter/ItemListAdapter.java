package com.bhikadia.boilerplate.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bhikadia.boilerplate.R;
import com.bhikadia.boilerplate.data.ItemDbHandler;

/**
 * Created by harsh on 13/12/15.
 */
public class ItemListAdapter extends CursorRecyclerViewAdapter<ItemListAdapter.ViewHolder>{
    private final String TAG = ItemListAdapter.class.getSimpleName();

    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView, createdAt, updatedAt;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.text);
            createdAt = (TextView) view.findViewById(R.id.text_created_at);
            updatedAt = (TextView) view.findViewById(R.id.text_updated_at);
        }
    }

    public ItemListAdapter(Context context, Cursor cursor) {
        super(cursor);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        viewHolder.textView.setText(cursor.getString(cursor.getColumnIndex(ItemDbHandler.COL_TEXT)));
        viewHolder.createdAt.setText(cursor.getString(cursor.getColumnIndex(ItemDbHandler.COL_CREATED_AT)));
        viewHolder.updatedAt.setText(cursor.getString(cursor.getColumnIndex(ItemDbHandler.COL_UPDATED_AT)));

    }
}
