/*
 * Decompiled with CFR 0.152.
 */
package com.fred.jianghun.truergb;

public interface IColor {
    public int alpha();

    public int red();

    public int green();

    public int blue();

    default public int toInt() {
        return this.red() << 16 | this.green() << 8 | this.blue();
    }
}

