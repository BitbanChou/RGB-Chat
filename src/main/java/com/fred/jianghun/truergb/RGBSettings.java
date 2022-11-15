/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.util.Tuple
 *  net.minecraft.util.text.TextFormatting
 */
package com.fred.jianghun.truergb;

import com.fred.jianghun.truergb.Colors;
import com.fred.jianghun.truergb.IColor;
import com.fred.jianghun.truergb.SimpleColor;
import com.fred.jianghun.truergb.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.TextFormatting;

public class RGBSettings {
    public static RGBSettings EMPTY = new RGBSettings(Collections.emptyList()){

        @Override
        @Nullable
        public SimpleColor getColorAt(int index) {
            return null;
        }

        @Override
        public int getLength() {
            return 0;
        }

        @Override
        public void addLength(int toAdd) {
        }

        @Override
        public void setBold(Boolean bold) {
        }

        @Override
        public void setItalic(Boolean italic) {
        }

        @Override
        public void setUnderlined(Boolean underlined) {
        }

        @Override
        public void setStrikethrough(Boolean strikethrough) {
        }

        @Override
        public void setObfuscated(Boolean obfuscated) {
        }

        @Override
        public Boolean getBold() {
            return null;
        }

        @Override
        public Boolean getItalic() {
            return null;
        }

        @Override
        public Boolean getUnderlined() {
            return null;
        }

        @Override
        public Boolean getStrikethrough() {
            return null;
        }

        @Override
        public Boolean getObfuscated() {
            return null;
        }
    };
    public static final Pattern PATTERN = Pattern.compile("(#(?<rgb>([0-9a-fA-F]{8})(-([0-9a-fA-F]{8}))*)|\u00a7(?<format>[0-9a-fA-FklmnorKLMNOR]))");
    private final List<IColor> colors;
    private int length;
    private Boolean bold;
    private Boolean italic;
    private Boolean underlined;
    private Boolean strikethrough;
    private Boolean obfuscated;

    public static List<Tuple<String, RGBSettings>> split(String string) {
        if (string == null || string.isEmpty()) {
            return Collections.singletonList(new Tuple((Object)"", (Object)EMPTY));
        }
        int index = 0;
        ArrayList<Tuple<String, RGBSettings>> result = new ArrayList<Tuple<String, RGBSettings>>();
        Matcher matcher = PATTERN.matcher(string);
        RGBSettings lastSettings = EMPTY;
        while (index < string.length()) {
            String subString;
            if (matcher.find(index)) {
                String format;
                subString = string.substring(index, matcher.start());
                if (!subString.isEmpty()) {
                    lastSettings.addLength(subString.length());
                    result.add((Tuple<String, RGBSettings>)new Tuple((Object)subString, (Object)lastSettings));
                }
                if ((format = matcher.group()).startsWith("#")) {
                    String fString = matcher.group("rgb");
                    lastSettings = new RGBSettings(Arrays.stream(fString.split("-")).map(Colors::of).collect(Collectors.toList()));
//                    for(IColor x:Arrays.stream(fString.split("-")).map(Colors::of).collect(Collectors.toList()))
//                        System.out.println(x);
                }
                else if (format.startsWith("\u00a7")) {
                    TextFormatting formatting = Utils.formattingOf(format.charAt(1));
                    if (formatting == null) {
                        throw new NullPointerException("Format: " + format);
                    }
                    lastSettings = lastSettings.withFormat(formatting);
                }
                else {
                    throw new IllegalStateException("Format: " + format);
                }
                index = matcher.end();
                continue;
            }
            subString = string.substring(index);
            lastSettings.addLength(subString.length());
            result.add((Tuple<String, RGBSettings>)new Tuple((Object)subString, (Object)lastSettings));
            break;
        }
//        System.out.println(lastSettings);

//        for(IColor x:result.get(0).getSecond().colors)
//            System.out.println(x.toInt());
        return result;
    }

    public static int hexToInteger(String s)
    {
        int ans=0,k=1;
        for(int i=5;i>=0;i--)
        {
            if(s.charAt(i)>='0' && s.charAt(i)<='9') ans+=k*(s.charAt(i)-'0');
            else if(s.charAt(i)>='a' && s.charAt(i)<='z'){
                ans+=k*((s.charAt(i)-'a')+10);
            }
            else{
                ans+=k*((s.charAt(i)-'A')+10);
            }
            k*=16;
        }
        return ans;
    }


    public static List<Tuple<String, RGBSettings>> split2(String string) {
        if (string == null || string.isEmpty()) {
            return Collections.singletonList(new Tuple((Object)"", (Object)EMPTY));
        }

        ArrayList<Tuple<String, RGBSettings>> result = new ArrayList<Tuple<String, RGBSettings>>();
        String subString="";
        List<String> colorSet=new ArrayList<>();
        int index = 0,len=string.length();

        for(int i=0;i<len;i++)
        {
            if(colorSet.size()<1 && string.charAt(i)=='#')
            {
                int j=i+1,flag=0;
                String oneColor="";
                while(j<len)
                {
                    if(oneColor.length()<8 && (string.charAt(j)>='0' && string.charAt(j)<='9'
                            || string.charAt(j)>='a' && string.charAt(j)<='z' || string.charAt(j)>='A' && string.charAt(j)<='Z')) {
                        oneColor += string.charAt(j);
                        j++;
                    }
                    if(oneColor.length()==8)
                    {
                        flag=1;
                        colorSet.add(oneColor);
                        i=j-1;
                        break;
                    }
                }
                if(flag==0)
                {
                    RGBSettings lastSettings = new RGBSettings(SimpleColor.of("FFFFFFFF"));
                    lastSettings.addLength(1);
                    result.add((Tuple<String, RGBSettings>)new Tuple((Object)string.substring(i,i+1),lastSettings));
                }
            }
            else if(colorSet.size()>0 && string.charAt(i)=='-')
            {
                int j=i+1,flag=0;
                String oneColor="";
                while(j<len)
                {
                    if(oneColor.length()<8 && (string.charAt(j)>='0' && string.charAt(j)<='9'
                            || string.charAt(j)>='a' && string.charAt(j)<='z' || string.charAt(j)>='A' && string.charAt(j)<='Z')) {
                        oneColor += string.charAt(j);
                        j++;
                    }
                    if(oneColor.length()==8)
                    {
                        flag=1;
                        colorSet.add(oneColor);
                        i=j-1;
                        break;
                    }
                }
                if(flag==0)
                {
                    RGBSettings lastSettings = new RGBSettings(SimpleColor.of("FFFFFFFF"));
                    lastSettings.addLength(1);
                    result.add((Tuple<String, RGBSettings>)new Tuple((Object)string.substring(i,i+1),lastSettings));
                }
            }
            else{
                int j=i;
                String target="";
                while(j<len)
                {
                    if(string.charAt(j)!='#')
                    {
                        target+=string.charAt(j);
                        j++;
                    }
                }
                if(colorSet.size()>1)
                {
                    int partlen=target.length()/(colorSet.size()-1);
                    int part=target.length()/partlen-1;
                    for(int k=0;k<target.length();k++)
                    {
                        int p=Math.min(part,k/partlen);
                        int ca=hexToInteger(colorSet.get(p+1).substring(2)),cb=hexToInteger(colorSet.get(p).substring(2));
                        int d=Math.abs((cb-ca))/partlen;
                        String thisWordColor="";
                        if(ca>cb) thisWordColor=colorSet.get(p).substring(0,2)+Integer.toHexString(cb+(k%partlen)*d);
                        else thisWordColor=colorSet.get(p).substring(0,2)+Integer.toHexString(cb-(k%partlen)*d);

                        RGBSettings lastSettings = new RGBSettings(SimpleColor.of(thisWordColor));
                        lastSettings.addLength(1);
                        result.add((Tuple<String, RGBSettings>)new Tuple((Object)target.substring(k,k+1),lastSettings));
                    }
                    colorSet.clear();
                }
                else if(colorSet.size()==1)
                {
                    RGBSettings lastSettings = new RGBSettings(SimpleColor.of(colorSet.get(0)));
                    lastSettings.addLength(target.length());
                    result.add((Tuple<String, RGBSettings>)new Tuple((Object)target,lastSettings));
                    colorSet.clear();
                }
                else{
                    RGBSettings lastSettings = new RGBSettings(SimpleColor.of("FFFFFFFF"));
                    lastSettings.addLength(1);
                    result.add((Tuple<String, RGBSettings>)new Tuple((Object)target,lastSettings));
                }
                //System.out.println(j+" "+len);
                if(j==len) break;
                i=j-1;
            }
        }

        //System.out.println(lastSettings);
        return result;
    }


    public RGBSettings(IColor color) {
        this(Collections.singletonList(color));
    }

    public RGBSettings(List<IColor> colors) {
        this.colors = colors;
    }

    protected int warpIndex(int index) {
        return index;
    }

    @Nullable
    public IColor getColorAt(int index) {
        IColor post;
        int postIndex;
        int colorsLength = this.colors.size();
        if (colorsLength == 0) {
            return null;
        }
        if (colorsLength == 1) {
            return this.colors.get(0);
        }
        int warpedIndex = this.warpIndex(index);
        int preIndex = Math.max(0, Math.min(colorsLength - 1, (colorsLength - 1) * warpedIndex / Math.max(1, this.getLength() - 1)));
        float percent = preIndex == (postIndex = Math.max(0, Math.min(colorsLength - 1, preIndex + 1))) ? 0.0f : Math.max(0.0f, Math.min(1.0f, (float)(warpedIndex * (colorsLength - 1) - this.getLength() * preIndex) / ((float)(postIndex - preIndex) * (float)this.getLength())));
        IColor pre = this.colors.get(preIndex);
        if (pre.equals(post = this.colors.get(postIndex))) {
            return pre;
        }
        ToIntFunction<ToIntFunction> mix = toIni -> Math.round((float)toIni.applyAsInt(pre) * (1.0f - percent) + (float)toIni.applyAsInt(post) * percent);
        return new SimpleColor(this.colors.get(0).red(),this.colors.get(0).green(),this.colors.get(0).blue());
    }

    public RGBSettings withFormat(TextFormatting formatting) {
        if (formatting.isColor()) {
            return new RGBSettings(Colors.of(formatting));
        }
        if (formatting.isFancyStyling()) {
            return new WithFormat(this, formatting);
        }
        if (formatting == TextFormatting.RESET) {
            return EMPTY;
        }
        throw new IllegalStateException(formatting.toString());
    }

    public boolean isFixedColor() {
        return this.colors.size() <= 1;
    }

    public int getLength() {
        return this.length;
    }

    public void addLength(int toAdd) {
        this.length += toAdd;
    }

    public void setBold(Boolean bold) {
        this.bold = bold;
    }

    public void setItalic(Boolean italic) {
        this.italic = italic;
    }

    public void setUnderlined(Boolean underlined) {
        this.underlined = underlined;
    }

    public void setStrikethrough(Boolean strikethrough) {
        this.strikethrough = strikethrough;
    }

    public void setObfuscated(Boolean obfuscated) {
        this.obfuscated = obfuscated;
    }

    public Boolean getFormatState(TextFormatting formatting) {
        switch (formatting) {
            case BOLD: {
                return this.getBold();
            }
            case ITALIC: {
                return this.getItalic();
            }
            case UNDERLINE: {
                return this.getUnderlined();
            }
            case STRIKETHROUGH: {
                return this.getStrikethrough();
            }
            case OBFUSCATED: {
                return this.getObfuscated();
            }
        }
        return null;
    }

    public Boolean getBold() {
        return this.bold;
    }

    public Boolean getItalic() {
        return this.italic;
    }

    public Boolean getUnderlined() {
        return this.underlined;
    }

    public Boolean getStrikethrough() {
        return this.strikethrough;
    }

    public Boolean getObfuscated() {
        return this.obfuscated;
    }

    public String getFormatString() {
        return Stream.of(TextFormatting.BOLD, TextFormatting.ITALIC, TextFormatting.UNDERLINE, TextFormatting.STRIKETHROUGH, TextFormatting.OBFUSCATED).filter(formatting -> this.getFormatState((TextFormatting)formatting) == Boolean.TRUE).map(TextFormatting::toString).collect(Collectors.joining());
    }

    private static class WithFormat
    extends RGBSettings {
        private final RGBSettings parent;
        private final int startIndex;

        public WithFormat(RGBSettings parent) {
            super(parent.colors);
            this.parent = parent;
            this.startIndex = parent.getLength();
        }

        public WithFormat(RGBSettings parent, TextFormatting ... formattingArray) {
            this(parent);
            block7: for (TextFormatting formatting : formattingArray) {
                switch (formatting) {
                    case OBFUSCATED: {
                        this.setObfuscated(true);
                        continue block7;
                    }
                    case BOLD: {
                        this.setBold(true);
                        continue block7;
                    }
                    case STRIKETHROUGH: {
                        this.setStrikethrough(true);
                        continue block7;
                    }
                    case UNDERLINE: {
                        this.setUnderlined(true);
                        continue block7;
                    }
                    case ITALIC: {
                        this.setItalic(true);
                        continue block7;
                    }
                }
            }
        }

        @Override
        protected int warpIndex(int index) {
            return this.startIndex + index;
        }

        @Override
        public void addLength(int toAdd) {
            this.parent.addLength(toAdd);
        }

        @Override
        public int getLength() {
            return this.parent.getLength();
        }

        private Boolean getFlag(Function<RGBSettings, Boolean> getter) {
            Boolean flag = getter.apply(this);
            if (flag == null) {
                return getter.apply(this.parent);
            }
            return flag;
        }

        @Override
        public Boolean getBold() {
            return this.getFlag(rgbSettings -> ((RGBSettings)rgbSettings).bold);
        }

        @Override
        public Boolean getItalic() {
            return this.getFlag(rgbSettings -> ((RGBSettings)rgbSettings).italic);
        }

        @Override
        public Boolean getUnderlined() {
            return this.getFlag(rgbSettings -> ((RGBSettings)rgbSettings).underlined);
        }

        @Override
        public Boolean getStrikethrough() {
            return this.getFlag(rgbSettings -> ((RGBSettings)rgbSettings).strikethrough);
        }

        @Override
        public Boolean getObfuscated() {
            return this.getFlag(rgbSettings -> ((RGBSettings)rgbSettings).obfuscated);
        }
    }
}

