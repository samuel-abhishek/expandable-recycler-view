package com.thoughtbot.expandablerecyclerview.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.LayoutScatter;
import com.thoughtbot.expandablerecyclerview.ResourceTable;
import com.thoughtbot.expandablerecyclerview.expand.ExpandAbilityHelper;

/**
 * Expand Ability Slice.
 */
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