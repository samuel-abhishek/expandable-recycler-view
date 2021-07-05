package com.anshul.expandablelistview.multitype;

import com.anshul.expandablelistview.ResourceTable;
import com.example.expandablelist.ExpandableListAdapter;
import com.example.expandablelist.ExpandableListContainer;
import com.example.expandablelist.util.ResUtil;
import java.util.ArrayList;
import java.util.HashMap;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.Image;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.ScrollView;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;
import ohos.agp.utils.TextAlignment;
import ohos.agp.window.dialog.ToastDialog;

/**
 * Multi Type Ability Slice.
 */
public class MultiTypeListAbilitySlice extends AbilitySlice {
    private ExpandableListContainer myGroupContainer;
    private ArrayList<String> myFinalGroupNameItem = new ArrayList<>();
    private ArrayList<String> myGroupNameItem = new ArrayList<>();
    private ArrayList<String> myTempGroupNameItem = new ArrayList<>();
    private ArrayList<String> myTempChildNameItem = new ArrayList<>();
    private ArrayList<Integer> myGroupImageItem = new ArrayList<>();
    private ArrayList<String> myFavouriteItem = new ArrayList<>();

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        ComponentContainer rootView = (ComponentContainer) LayoutScatter.getInstance(this)
                .parse(ResourceTable.Layout_ability_multitype_list, null, false);
        super.setUIContent(rootView);
        initViews(rootView);
    }

    /**
     * This method is used to init view components.
     *
     * @param rootView parent view
     */
    private void initViews(ComponentContainer rootView) {
        getGroupItems();
        getGroupIcons();
        getFavouriteItems();
        ScrollView parentLayout = (ScrollView) rootView.findComponentById(ResourceTable.Id_root_multitype);
        parentLayout.setBackground(getShapeElement(ResUtil.getColor(this, ResourceTable.Color_back_white)));
        myGroupContainer = (ExpandableListContainer)
                rootView.findComponentById(ResourceTable.Id_lcGroupItems_multitype);
        prepareExpandableListAdapter();
    }

    /**
     * This method is used to prepare group items.
     */
    private void getGroupItems() {
        myGroupNameItem.add(ResUtil.getString(this, ResourceTable.String_item_Rock));
        myGroupNameItem.add(ResUtil.getString(this, ResourceTable.String_item_Jazz));
        myGroupNameItem.add(ResUtil.getString(this, ResourceTable.String_item_Classic));
        myGroupNameItem.add(ResUtil.getString(this, ResourceTable.String_item_Salsa));
        myGroupNameItem.add(ResUtil.getString(this, ResourceTable.String_item_Bluegrass));
        myFinalGroupNameItem.addAll(myGroupNameItem);
    }

    /**
     * This method is used to prepare group items images.
     */
    private void getGroupIcons() {
        myGroupImageItem.add(ResourceTable.Media_rock);
        myGroupImageItem.add(ResourceTable.Media_jazz);
        myGroupImageItem.add(ResourceTable.Media_classic);
        myGroupImageItem.add(ResourceTable.Media_salsa);
        myGroupImageItem.add(ResourceTable.Media_bluegrass);
    }

    /**
     * This method is used to prepare Favourite child items.
     */
    private void getFavouriteItems() {
        myFavouriteItem.add(ResUtil.getString(this, ResourceTable.String_item_child_Rock1));
        myFavouriteItem.add(ResUtil.getString(this, ResourceTable.String_item_child_Rock4));
        myFavouriteItem.add(ResUtil.getString(this, ResourceTable.String_item_child_Jazz1));
        myFavouriteItem.add(ResUtil.getString(this, ResourceTable.String_item_child_Jazz2));
        myFavouriteItem.add(ResUtil.getString(this, ResourceTable.String_item_child_Classic2));
        myFavouriteItem.add(ResUtil.getString(this, ResourceTable.String_item_child_Salsa1));
        myFavouriteItem.add(ResUtil.getString(this, ResourceTable.String_item_child_Bluegrass1));
    }


    /**
     * This method is used to prepare adapter for list data.
     */
    private void prepareExpandableListAdapter() {
        ExpandableListAdapter<String> expandableListAdapter = new ExpandableListAdapter<String>(this,
                ResourceTable.Layout_ability_listview_item, myGroupNameItem, myGroupImageItem) {
            @Override
            protected void bind(ViewHolder holder, String text, Integer image, int position) {
                if (!myTempChildNameItem.contains(text)) {
                    // Set green background
                    holder.makeInvisibleButton(ResourceTable.Id_checkbtn);
                    holder.setGroupItemBackground(ResourceTable.Id_groupContainer, ResourceTable.Color_back_white);
                    holder.setText(ResourceTable.Id_tvGroupTitle, text, Color.GRAY,
                            ResUtil.getIntDimen(MultiTypeListAbilitySlice.this, ResourceTable.Float_text_20fp));
                    holder.setGroupImage(ResourceTable.Id_ivGroupIcon, image, ShapeElement.RECTANGLE,
                            Image.ScaleMode.STRETCH, ResourceTable.Color_back_white);
                    if (!myTempGroupNameItem.contains(text)) {
                        // Set divider & plus icon
                        holder.setGroupImage(ResourceTable.Id_ArrowUpDownIcon, ResourceTable.Media_arrow_Down,
                                ShapeElement.OVAL, Image.ScaleMode.CENTER, ResourceTable.Color_back_white);
                    } else {
                        // Remove divider & minus icon
                        holder.setGroupImage(ResourceTable.Id_ArrowUpDownIcon, ResourceTable.Media_arrow_Up,
                               ShapeElement.OVAL, Image.ScaleMode.CENTER, ResourceTable.Color_back_white);
                    }
                } else {
                    // Add child items to list
                    holder.makeInvisibleButton(ResourceTable.Id_checkbtn);
                    holder.setText(ResourceTable.Id_tvGroupTitle, text, Color.GRAY,
                            ResUtil.getIntDimen(MultiTypeListAbilitySlice.this, ResourceTable.Float_text_15fp));
                    //to add image only for the favourite items
                    if (myFavouriteItem.contains(text)) {
                        holder.setGroupImage(ResourceTable.Id_childstar, ResourceTable.Media_star,
                                ShapeElement.OVAL, Image.ScaleMode.CENTER, ResourceTable.Color_back_white);
                    }
                }
            }
        };
        myGroupContainer.setItemProvider(expandableListAdapter);

        expandableListAdapter.setOnItemClickListener((component, position) -> {
            String clickedItem = myGroupNameItem.get(position);
            if (!myTempChildNameItem.contains(clickedItem)) {
                if (myTempGroupNameItem.contains(clickedItem)) {
                    int actualItemPosition = myFinalGroupNameItem.indexOf(clickedItem);
                    removeChildItems(actualItemPosition, clickedItem);
                    myTempGroupNameItem.remove(clickedItem);
                } else {
                    int actualItemPosition = myFinalGroupNameItem.indexOf(clickedItem);
                    addChildItems(actualItemPosition, clickedItem);
                    myTempGroupNameItem.add(clickedItem);
                }
                expandableListAdapter.setData(myGroupNameItem);
            } else {
                showToast();
            }
        });

    }

    /**
     * This method is used to add child items in list.
     *
     * @param actualPosition position of group item
     * @param clickedItem    name of clicked item
     */
    private void addChildItems(int actualPosition, String clickedItem) {
        String[] childItems = childItems().get(actualPosition);
        int itemPositionFromGroup = myGroupNameItem.indexOf(clickedItem);
        for (String item : childItems) {
            itemPositionFromGroup = itemPositionFromGroup + 1;
            myGroupNameItem.add(itemPositionFromGroup, item);
            myTempChildNameItem.add(item);
            myGroupImageItem.add(itemPositionFromGroup, ResourceTable.Media_star);
        }
    }

    /**
     * This method is used to remove child item.
     *
     * @param position    position of group item
     * @param clickedItem name of the clicked item
     */
    private void removeChildItems(int position, String clickedItem) {
        String[] items = childItems().get(position);
        int itemPositionFromGroup = myGroupNameItem.indexOf(clickedItem);
        for (String name : items) {
            myGroupNameItem.remove(itemPositionFromGroup + 1);
            myGroupImageItem.remove(itemPositionFromGroup + 1);
            myTempChildNameItem.remove(name);
        }
    }

    /**
     * This method is used to prepare map based on group item index.
     *
     * @return HashMap
     */
    private HashMap<Integer, String[]> childItems() {
        HashMap<Integer, String[]> map = new HashMap<>();
        map.put(0, new String[]{
                ResUtil.getString(this, ResourceTable.String_item_child_Rock1),
                ResUtil.getString(this, ResourceTable.String_item_child_Rock2),
                ResUtil.getString(this, ResourceTable.String_item_child_Rock3),
                ResUtil.getString(this, ResourceTable.String_item_child_Rock4)});
        map.put(1, new String[]{
                ResUtil.getString(this, ResourceTable.String_item_child_Jazz1),
                ResUtil.getString(this, ResourceTable.String_item_child_Jazz2),
                ResUtil.getString(this, ResourceTable.String_item_child_Jazz3)});
        map.put(2, new String[]{
                ResUtil.getString(this, ResourceTable.String_item_child_Classic1),
                ResUtil.getString(this, ResourceTable.String_item_child_Classic2),
                ResUtil.getString(this, ResourceTable.String_item_child_Classic3),
                ResUtil.getString(this, ResourceTable.String_item_child_Classic4)});
        map.put(3, new String[]{
                ResUtil.getString(this, ResourceTable.String_item_child_Salsa1),
                ResUtil.getString(this, ResourceTable.String_item_child_Salsa2),
                ResUtil.getString(this, ResourceTable.String_item_child_Salsa3),
                ResUtil.getString(this, ResourceTable.String_item_child_Salsa4)});
        map.put(4, new String[]{
                ResUtil.getString(this, ResourceTable.String_item_child_Bluegrass1),
                ResUtil.getString(this, ResourceTable.String_item_child_Bluegrass2),
                ResUtil.getString(this, ResourceTable.String_item_child_Bluegrass3),
                ResUtil.getString(this, ResourceTable.String_item_child_Bluegrass4)});
        return map;
    }

    /**
     * This method is used to show toast dialog based on the given message.
     */
    private void showToast() {
        ToastDialog toastDialog = new ToastDialog(this);
        toastDialog.setAlignment(TextAlignment.BOTTOM);
        toastDialog.setText(ResUtil.getString(this, ResourceTable.String_clicked_on_child_item));
        toastDialog.show();
    }

    /**
     * This method is used to prepare the background shape element based on color.
     *
     * @param color color for the shape element
     * @return ShapeElement
     */
    private ShapeElement getShapeElement(int color) {
        ShapeElement shapeElement = new ShapeElement();
        shapeElement.setShape(ShapeElement.RECTANGLE);
        shapeElement.setRgbColor(RgbColor.fromArgbInt(color));
        return shapeElement;
    }
}