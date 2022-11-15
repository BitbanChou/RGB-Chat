package com.fred.jianghun.tabs;

import com.fred.jianghun.init.ItemInit;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ItemTab extends CreativeTabs {

    public ItemTab(){
        super("item_tab");
    }

    @Override
    public ItemStack getTabIconItem(){
        return new ItemStack(ItemInit.ORE);
    }


}
