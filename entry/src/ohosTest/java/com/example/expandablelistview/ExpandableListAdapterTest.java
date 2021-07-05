package com.example.expandablelistview;

import com.example.expandablelist.ExpandableListAdapter;
import ohos.app.Context;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class ExpandableListAdapterTest {
    public Context context;
    public List<String> names;
    public List<Integer> images;
    public int mLayoutId;
    public ExpandableListAdapter items;


    @Test
    public void testgetCountwithNull(){
        names= new ArrayList<>();
        items= new ExpandableListAdapter<String >(context, mLayoutId, names, images) {
            @Override
            protected void bind(ViewHolder holder, String s, Integer image, int position) {
            }
        };
        int res= items.getCount();
        assertEquals(0,res);
    }

    @Test
    public void testgetCount(){
        names= new ArrayList<>();
        names.add("Item1");
        names.add("Item2");
        items= new ExpandableListAdapter<String >(context, mLayoutId, names, images) {
            @Override
            protected void bind(ViewHolder holder, String s, Integer image, int position) {
            }
        };
        int res= items.getCount();
        assertEquals(2,res);
    }

    @Test
    public void testgetItemWithNull(){
        names= new ArrayList<>();
        items= new ExpandableListAdapter<String >(context, mLayoutId, names, images) {
            @Override
            protected void bind(ViewHolder holder, String s, Integer image, int position) {
            }
        };
        String res= (String) items.getItem(5);
        assertNull(res);
    }

    @Test
    public void testgetItem(){
        names= new ArrayList<>();
        names.add("Item1");
        names.add("Item2");
        items= new ExpandableListAdapter<String >(context, mLayoutId, names, images) {
            @Override
            protected void bind(ViewHolder holder, String s, Integer image, int position) {
            }
        };
        String res= (String) items.getItem(0);
        assertEquals("Item1",res);
    }

    @Test
    public void testgetItemWithOutOfBound(){
        names= new ArrayList<>();
        names.add("Item1");
        names.add("Item2");
        items= new ExpandableListAdapter<String >(context, mLayoutId, names, images) {
            @Override
            protected void bind(ViewHolder holder, String s, Integer image, int position) {
            }
        };
        String res= (String) items.getItem(5);
        assertNull(res);
    }

}
