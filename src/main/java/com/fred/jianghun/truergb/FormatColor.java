/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.text.TextFormatting
 */
package com.fred.jianghun.truergb;

import com.fred.jianghun.truergb.IColor;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;

public enum FormatColor implements IColor
{
    BLACK(TextFormatting.BLACK),
    DARK_BLUE(TextFormatting.DARK_BLUE),
    DARK_GREEN(TextFormatting.DARK_GREEN),
    DARK_AQUA(TextFormatting.DARK_AQUA),
    DARK_RED(TextFormatting.DARK_RED),
    DARK_PURPLE(TextFormatting.DARK_PURPLE),
    GOLD(TextFormatting.GOLD),
    GRAY(TextFormatting.GRAY),
    DARK_GRAY(TextFormatting.DARK_GRAY),
    BLUE(TextFormatting.BLUE),
    GREEN(TextFormatting.GREEN),
    AQUA(TextFormatting.AQUA),
    RED(TextFormatting.RED),
    LIGHT_PURPLE(TextFormatting.LIGHT_PURPLE),
    YELLOW(TextFormatting.YELLOW),
    WHITE(TextFormatting.WHITE);

    private final TextFormatting formatting;

    public static FormatColor of(char index) {
        return FormatColor.of("0123456789abcdef".indexOf(Character.toLowerCase(index)));
    }

    public static FormatColor of(TextFormatting formatting) {
        return FormatColor.of(formatting.getColorIndex());
    }

    public static FormatColor of(int index) {
        if (index >= 0 && index <= 15) {
            return FormatColor.values()[index];
        }
        return WHITE;
    }

    private FormatColor(TextFormatting formatting) {
        if (!formatting.isColor()) {
            throw new IllegalArgumentException(formatting.getFriendlyName());
        }
        this.formatting = formatting;
    }

    public TextFormatting getFormatting() {
        return this.formatting;
    }

    @Override
    public int alpha() {
        return 255;
    }

    @Override
    public int red() {
        return this.getColorCode() >> 16 & 0xFF;
    }

    @Override
    public int green() {
        return this.getColorCode() >> 8 & 0xFF;
    }

    @Override
    public int blue() {
        return this.getColorCode() & 0xFF;
    }

    public int getColorCode() {
        return Minecraft.getMinecraft().fontRenderer.getColorCode("0123456789abcdef".charAt(this.formatting.getColorIndex()));
    }

    @Override
    public int toInt() {
        return 0xFF000000 | this.getColorCode();
    }
}

