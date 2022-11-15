package com.fred.jianghun.init;

import com.fred.jianghun.Main;
import com.fred.jianghun.blocks.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class BlockInit {
    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public static final Block HUMUS_SAND = new BlockBase("the_sand", Material.SAND, Main.ITEM_TAB);


}
