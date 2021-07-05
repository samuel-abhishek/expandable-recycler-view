package com.anshul.expandablelistview.entities;

/**
 * To store the Parent of each Item.
 */
public class ParentChild {
    String parentItem;
    String childItem;

    public ParentChild(String parentItem, String childItem) {
        this.parentItem = parentItem;
        this.childItem = childItem;
    }

    public String getParentItem() {
        return parentItem;
    }

    public void setParentItem(String parentItem) {
        this.parentItem = parentItem;
    }

    public String getChildItem() {
        return childItem;
    }

    public void setChildItem(String childItem) {
        this.childItem = childItem;
    }

    public boolean isEquals(ParentChild obj) {
        return (this.parentItem.equals(obj.getParentItem()) && this.childItem.equals(obj.getChildItem()));
    }
}
