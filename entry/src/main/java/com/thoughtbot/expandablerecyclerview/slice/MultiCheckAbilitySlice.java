package com.thoughtbot.expandablerecyclerview.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.LayoutScatter;
import com.thoughtbot.expandablerecyclerview.ResourceTable;
import com.thoughtbot.expandablerecyclerview.multicheck.MultiCheckHelper;

/**
 * Multi Check Ability Slice.
 */
public class MultiCheckAbilitySlice extends AbilitySlice {

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        ComponentContainer rootView = (ComponentContainer) LayoutScatter.getInstance(this)
                .parse(ResourceTable.Layout_ability_multicheck, null, false);
        super.setUIContent(rootView);
        MultiCheckHelper helper = new MultiCheckHelper(this, rootView);
        helper.initViews();
    }
}
