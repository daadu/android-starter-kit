package com.bhikadia.boilerplate;

import android.test.AndroidTestCase;

import com.bhikadia.boilerplate.data.AppContentProvider;
import com.bhikadia.boilerplate.data.ItemDbHandler;
import com.bhikadia.boilerplate.data.model.Item;

/**
 * Created by harsh on 13/12/15.
 */
public class TestItemDbHelper extends AndroidTestCase {
    public final String TAG = TestItemDbHelper.class.getSimpleName();
    public ItemDbHandler itemDbHandler;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        itemDbHandler = ItemDbHandler.getInstance(getContext());
    }

    public void testInsertAndGet(){
        //delete all items
        getContext().getContentResolver().delete(AppContentProvider.URI_ITEM, null, null);

        Item item = new Item();
        item.setId(1);
        item.setText("One");

       itemDbHandler.insert(item);

        Item item2 = itemDbHandler.get(item.getId());

        assertNotNull(item2);
        assertEquals(item2.getText(), "One");
        assertEquals(item.getId(), item2.getId());
        assertEquals(item.getText() , item2.getText());
    }

    public void testUpdate(){
        Item item = itemDbHandler.get(1);

        assertNotNull(item);
        assertEquals(item.getText(), "One");

        item.setText("One One");
        itemDbHandler.update(item);

        Item item2 = itemDbHandler.get(item.getId());

        assertNotNull(item2);
        assertEquals(item2.getText(), "One One");
    }

    public void testDelete(){
        Item item = itemDbHandler.get(1);
        assertNotNull(item);

        itemDbHandler.delete(item);

        Item item2 = itemDbHandler.get(1);
        assertNull(item2);

    }
}
