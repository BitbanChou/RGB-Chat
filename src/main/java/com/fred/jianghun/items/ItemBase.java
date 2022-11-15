package com.fred.jianghun.items;

import com.fred.jianghun.Main;
import com.fred.jianghun.init.ItemInit;
import com.fred.jianghun.utils.IhasModel;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class ItemBase extends Item implements IhasModel {

    public ItemBase(String name, CreativeTabs tab)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(tab);

        ItemInit.ITEMS.add(this);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        ItemStack itemstack = playerIn.getHeldItem(handIn);
        for(int i=0;i<100;i++)
        {
            playerIn.motionY += 0.02D;
        }
        worldIn.playSound(playerIn, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_FIREWORK_LAUNCH, SoundCategory.NEUTRAL, 2.5F, 2.4F / (itemRand.nextFloat() * 0.4F + 0.8F));


        if (!playerIn.capabilities.isCreativeMode)
        {
            itemstack.shrink(1);
            if (itemstack.isEmpty())
            {
                playerIn.inventory.deleteStack(itemstack);
            }
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }

    @Override
    public void registerModels(){
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }

}
