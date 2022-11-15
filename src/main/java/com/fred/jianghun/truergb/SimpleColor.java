/*
 * Decompiled with CFR 0.152.
 */
package com.fred.jianghun.truergb;

import com.fred.jianghun.truergb.FormatColor;
import com.fred.jianghun.truergb.IColor;
import java.util.Objects;

public class SimpleColor implements IColor {
    private final int alpha;
    private final int red;
    private final int green;
    private final int blue;

    public static IColor of(String s) {
        if (s.length() == 6) {
            return new SimpleColor(Integer.valueOf(s.substring(0, 2), 16), Integer.valueOf(s.substring(2, 4), 16), Integer.valueOf(s.substring(4, 6), 16));
        }
        if (s.length() == 8) {
            return new SimpleColor(Integer.valueOf(s.substring(0, 2), 16), Integer.valueOf(s.substring(2, 4), 16), Integer.valueOf(s.substring(4, 6), 16), Integer.valueOf(s.substring(6, 8), 16));
        }
        return FormatColor.WHITE;
    }

    public SimpleColor(int alpha, int red, int green, int blue) {
        this.alpha = alpha;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public SimpleColor(int red, int green, int blue) {
        this(255, red, green, blue);
    }

    @Override
    public int alpha() {
        return this.alpha;
    }

    @Override
    public int red() {
        return this.red;
    }

    @Override
    public int green() {
        return this.green;
    }

    @Override
    public int blue() {
        return this.blue;
    }

    @Override
    public int toInt() {
        //return this.alpha << 24 | this.red << 16 | this.green << 8 | this.blue;
        return this.red << 16 | this.green << 8 | this.blue;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        SimpleColor color = (SimpleColor)o;
        return this.alpha == color.alpha && this.red == color.red && this.green == color.green && this.blue == color.blue;
    }

    public int hashCode() {
        return Objects.hash(this.alpha, this.red, this.green, this.blue);
    }
}

