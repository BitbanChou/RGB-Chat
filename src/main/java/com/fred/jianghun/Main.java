package com.fred.jianghun;


import com.fred.jianghun.init.RegistryHandler;
import com.fred.jianghun.proxy.ProxyBase;
import com.fred.jianghun.tabs.ItemTab;
import com.fred.jianghun.truergb.TrueRGBSimpleRenderer;
import com.fred.jianghun.utils.GradientGeneration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Mod(modid = Main.MODID, name = Main.NAME, version = Main.VERSION)
public class Main
{
    public static final String MODID = "jianghun";
    public static final String NAME = "RGB";
    public static final String VERSION = "1.0";
    public static final String CLIENT_PROXY = "com.fred.jianghun.proxy.ClientProxy";
    public static final String SERVER_PROXY = "com.fred.jianghun.proxy.ServerProxy";

    private static Logger logger;

    @Mod.Instance
    public static Main instance;

    @SidedProxy(clientSide = CLIENT_PROXY,serverSide = SERVER_PROXY)
    public static ProxyBase proxy;

//    @EventHandler
//    public void preInit(FMLPreInitializationEvent event)
//    {
//        logger = event.getModLog();
//    }

//    @EventHandler
//    public void preInit(FMLPreInitializationEvent event) {
//
//        //RegistryHandler.preInitRegistries(event);
//    }
//
//    @EventHandler
//    public void init(FMLInitializationEvent event)
//    {
//        // some example code
//        //logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
//    }
    private TrueRGBSimpleRenderer renderer;

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        this.renderer = new TrueRGBSimpleRenderer(mc.gameSettings, new ResourceLocation("textures/font/ascii.png"), mc.renderEngine, false);
        ((SimpleReloadableResourceManager)mc.getResourceManager()).registerReloadListener((IResourceManagerReloadListener)this.renderer);
//        this.renderer2 = new TrueGradientSimpleRenderer(mc.gameSettings, new ResourceLocation("textures/font/ascii.png"), mc.renderEngine, false);
//        ((SimpleReloadableResourceManager)mc.getResourceManager()).registerReloadListener((IResourceManagerReloadListener)this.renderer2);

    }

//    @Mod.EventHandler
//    public void onPlayerChat(FMLEvent event) {
//        Minecraft mc = Minecraft.getMinecraft();
//        EntityPlayer player = mc.player; // .getPlayer();
//
//        this.renderer2 = new TrueGradientSimpleRenderer(mc.gameSettings, new ResourceLocation("textures/font/ascii.png"), mc.renderEngine, false);
//    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.fontRenderer = this.renderer;
        if (mc.gameSettings.language != null) {
            this.renderer.setUnicodeFlag(mc.isUnicode());
            this.renderer.setBidiFlag(mc.getLanguageManager().isCurrentLanguageBidirectional());
        }
    }
    public static CreativeTabs ITEM_TAB = new ItemTab();

}
