# ExpandableRecyclerView
Custom BaseItemProvider for expanding and collapsing groups.

# Source
This library is inspired by version 1.5 of [ExpandableRecyclerView](https://github.com/thoughtbot/expandable-recycler-view) library.

# Features
This library allows us to add child items within the group items in a List View. We can also set our favourite child items within a particular group.
This library also gives us the feature to select a single child item of a particular group item. We can also select multiple child items within a group.

# Dependency
How to add the dependency

# Usage
Let's say you are a rock star 🎸 and you want to build an app to show a list of your favorite Genres with a list of their top Artists.

First create the Ability Slices and call the helper class with view and context as arguments.
``` java
public class ExpandAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        ComponentContainer rootView = (ComponentContainer) LayoutScatter.getInstance(this)
                .parse(ResourceTable.Layout_ability_expandlist, null, false);
        super.setUIContent(rootView);
        ExpandAbilityHelper helper = new ExpandAbilityHelper(this, rootView);
        helper.initViews();
    }
}
```
Inside the helper class, Initialize the view components by `initViews` method.
``` java
public void initViews() {
        getGroupItems();
        getGroupIcons();
        tooglebtn = (Button) rootView.findComponentById(ResourceTable.Id_toogle);
        ScrollView parentLayout = (ScrollView) rootView.findComponentById(ResourceTable.Id_root_expand);
        parentLayout.setBackground(getShapeElement(ResUtil.getColor(context, ResourceTable.Color_white)));
        mGroupContainer = (ExpandableListContainer) rootView.findComponentById(ResourceTable.Id_lcGroupItems_expand);
        prepareExpandableListAdapter();
    }
```
Then Add all the Genre items in `mGroupNameItem` and their corresponding image in `mGroupImageItem `
``` java
private void getGroupItems() {
        mGroupNameItem.add(ResUtil.getString(context, ResourceTable.String_item_Rock));
        mGroupNameItem.add(ResUtil.getString(context, ResourceTable.String_item_Jazz));
        mGroupNameItem.add(ResUtil.getString(context, ResourceTable.String_item_Classic));
        mGroupNameItem.add(ResUtil.getString(context, ResourceTable.String_item_Salsa));
        mGroupNameItem.add(ResUtil.getString(context, ResourceTable.String_item_Bluegrass));
        mFinalGroupNameItem.addAll(mGroupNameItem);
    }
```
``` java
    private void getGroupIcons() {
        mGroupImageItem.add(ResourceTable.Media_rock);
        mGroupImageItem.add(ResourceTable.Media_jazz);
        mGroupImageItem.add(ResourceTable.Media_classic);
        mGroupImageItem.add(ResourceTable.Media_salsa);
        mGroupImageItem.add(ResourceTable.Media_bluegrass);
    }
```
Now the binding of the data to the view is done inside the `prepareExpandableListAdapter` method.
``` java
 ExpandableListAdapter<String> expandableListAdapter = new ExpandableListAdapter<String>(context,
                mGroupNameItem, mGroupImageItem, ResourceTable.Layout_ability_listview_item) {
            @Override
            protected void bind(ViewHolder holder, String text, Integer image, int position) {
                if (!mTempChildNameItem.contains(text)) {
                    // Set green background for parent/Group
                    holder.makeInvisibleButton(ResourceTable.Id_checkbtn);
                    holder.makeInvisibleImage(ResourceTable.Id_childstar);
                    holder.setGroupItemBackground(ResourceTable.Id_groupContainer, ResourceTable.Color_white);
                    holder.setText(ResourceTable.Id_tvGroupTitle, text, Color.GRAY,
                            ResUtil.getIntDimen(context, ResourceTable.Float_group_text_size));
                    holder.setGroupImage(ResourceTable.Id_ivGroupIcon, image,
                            ShapeElement.RECTANGLE, Image.ScaleMode.STRETCH, ResourceTable.Color_white);
                    if (!mTempGroupNameItem.contains(text)) {
                        // Set divider & arrow down icon
                        holder.setGroupImage(ResourceTable.Id_ArrowIcon, ResourceTable.Media_arrow_Down,
                                ShapeElement.OVAL, Image.ScaleMode.CENTER, ResourceTable.Color_white);
                    } else {
                        // Remove divider & arrow up icon
                        holder.setGroupImage(ResourceTable.Id_ArrowIcon, ResourceTable.Media_arrow_Up,
                                ShapeElement.OVAL, Image.ScaleMode.CENTER, ResourceTable.Color_white);
                    }
                } else {
                    // Add child items to list
                    holder.makeInvisibleButton(ResourceTable.Id_checkbtn);
                    holder.makeInvisibleImage(ResourceTable.Id_checkbtn);
                    holder.setText(ResourceTable.Id_tvGroupTitle, text, Color.GRAY,
                            ResUtil.getIntDimen(context, ResourceTable.Float_child_text_size));
                }
            }
        };
        mGroupContainer.setItemProvider(expandableListAdapter);
```
Then we set the onItemClickListener and call the `checkChild` method to check if the clicketItem is a GroupItem (i.e. Genre) or the ChildItem (i.e. Artist).
``` java
    expandableListAdapter.setOnItemClickListener((component, position) -> {
            String clickedItem = mGroupNameItem.get(position);
            checkChild(clickedItem, expandableListAdapter);
        });
```
`mTempGroupNameItem` contains all the GroupItem that are in expand state and `mTempChildNameItem` will contains child of such GroupItems.
While collapsing the group, we will remove the groupItem from mTempGroupNameItem and their childItems from mTempChildNameItem.

Now, if the clickedItem is not there in `mTempChildNameItem` , it means it is the GroupItem otherwise it is the Childitem.
``` java
private void checkChild(String clickedItem, ExpandableListAdapter<String> expandableListAdapter) {
        if (!mTempChildNameItem.contains(clickedItem)) {
            if (mTempGroupNameItem.contains(clickedItem)) {
                int actualItemPosition = mFinalGroupNameItem.indexOf(clickedItem);
                removeChildItems(actualItemPosition, clickedItem);
                mTempGroupNameItem.remove(clickedItem);
            } else {
                int actualItemPosition = mFinalGroupNameItem.indexOf(clickedItem);
                addChildItems(actualItemPosition, clickedItem);
                mTempGroupNameItem.add(clickedItem);
            }
            expandableListAdapter.setData(mGroupNameItem);
        } else {
            showToast();
        }
    }
```
If it is the GroupItem and it is not there in `mTempGroupNameItem`, that means we have to expand that GroupItem. For that, we will add this `clickedItem` to `mTempGroupNameItem` and their corresponding child items to `mTempChildNameItem`.
``` java
private void addChildItems(int actualPosition, String clickedItem) {
        String[] childItems = childItems().get(actualPosition);
        int itemPositionFromGroup = mGroupNameItem.indexOf(clickedItem);
        for (String item : childItems) {
            itemPositionFromGroup = itemPositionFromGroup + 1;
            mGroupNameItem.add(itemPositionFromGroup, item);
            mTempChildNameItem.add(item);
            mGroupImageItem.add(itemPositionFromGroup, ResourceTable.Media_star);
        }
    }
``` 
If the clickedItem is already there in `mTempGroupNameItem`, then we have to collapse that GroupItem and we have to remove it from `mTempGroupNameItem` and the 
corresponding child items from `mTempChildNameItem`.
``` java
private void removeChildItems(int position, String clickedItem) {
        String[] items = childItems().get(position);
        int itemPositionFromGroup = mGroupNameItem.indexOf(clickedItem);
        for (String name : items) {
            mGroupNameItem.remove(itemPositionFromGroup + 1);
            mGroupImageItem.remove(itemPositionFromGroup + 1);
            mTempChildNameItem.remove(name);
        }
    }
``` 
