package com.anshul.expandablelistview.slice;

import com.anshul.expandablelistview.ResourceTable;
import com.anshul.expandablelistview.expand.ExpandAbilitySlice;
import com.anshul.expandablelistview.multicheck.MultiCheckAbilitySlice;
import com.anshul.expandablelistview.multitype.MultiTypeListAbilitySlice;
import com.anshul.expandablelistview.multitypeandcheck.MultiTypeAndCheckAbilitySlice;
import com.anshul.expandablelistview.singlecheck.SingleCheckAbilitySlice;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;

/**
 * MainAbility that calls each.
 * of the different type of Ability
 */
public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        Button expand = (Button) findComponentById(ResourceTable.Id_expand_button);
        expand.setClickedListener(list -> present(new ExpandAbilitySlice(), new Intent()));

        Button multitype = (Button) findComponentById(ResourceTable.Id_multitype_button);
        multitype.setClickedListener(list -> present(new MultiTypeListAbilitySlice(), new Intent()));

        Button multicheck = (Button) findComponentById(ResourceTable.Id_multicheck_button);
        multicheck.setClickedListener(list -> present(new MultiCheckAbilitySlice(), new Intent()));

        Button singlecheck = (Button) findComponentById(ResourceTable.Id_singlecheck_button);
        singlecheck.setClickedListener(list -> present(new SingleCheckAbilitySlice(), new Intent()));

        Button multitypecheck = (Button) findComponentById(ResourceTable.Id_multitypecheck_button);
        multitypecheck.setClickedListener(list -> present(new MultiTypeAndCheckAbilitySlice(), new Intent()));
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
