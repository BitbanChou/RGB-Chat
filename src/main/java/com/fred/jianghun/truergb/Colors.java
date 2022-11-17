package com.fred.jianghun.truergb;

import com.fred.jianghun.truergb.FormatColor;
import com.fred.jianghun.truergb.IColor;
import com.fred.jianghun.truergb.SimpleColor;
import net.minecraft.util.text.TextFormatting;

public class Colors {
    public static IColor of(int index) {
        return FormatColor.of(index);
    }

    public static IColor of(char index) {
        return FormatColor.of(index);
    }

    public static IColor of(TextFormatting formatting) {
        return FormatColor.of(formatting);
    }

    public static IColor of(String s) {
        return SimpleColor.of(s);
    }
}

