package com.fred.jianghun.blocks;

import com.fred.jianghun.Main;
import com.fred.jianghun.init.BlockInit;
import com.fred.jianghun.init.ItemInit;
import com.fred.jianghun.utils.IhasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import java.util.Random;

public class BlockBase extends Block implements IhasModel
{
    public BlockBase(String name, Material material)
    {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.ITEM_TAB);

        BlockInit.BLOCKS.add(this);
        ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));

        setHardness(5.0F);
        //setResistance(1000.0F);
        //setHarvestLevel("pickaxe", 1);
        //setLightLevel(1f);
        setLightOpacity(1);
    }

    public BlockBase(String name, Material material, CreativeTabs tabs)
    {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(tabs);;

        BlockInit.BLOCKS.add(this);
        ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));

        setHardness(5.0F);
        //setResistance(1000.0F);
        setHarvestLevel("pickaxe", 1);
        //setLightLevel(1f);
        setLightOpacity(1);
    }
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return super.getItemDropped(state, rand, fortune);
    }

    @Override
    public int quantityDropped(Random rand) {
        return super.quantityDropped(rand);
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}

