package com.fred.jianghun.init;

import com.fred.jianghun.Main;
import com.fred.jianghun.items.ItemBase;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemInit {

    public static final List<Item> ITEMS = new ArrayList<Item>();

    public static final Item ORE = new ItemBase("ore", Main.ITEM_TAB);

}
