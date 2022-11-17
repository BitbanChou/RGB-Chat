package com.fred.jianghun.truergb;

import net.minecraft.util.text.TextFormatting;

public class Utils {
    public static TextFormatting formattingOf(char c) {
        int index = "0123456789abcdefklmnor".indexOf(Character.toLowerCase(c));
        if (index < 0) {
            return null;
        }
        return TextFormatting.values()[index];
    }
}

