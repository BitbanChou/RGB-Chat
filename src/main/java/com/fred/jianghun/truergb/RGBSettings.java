package com.fred.jianghun.truergb;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import java.util.function.ToIntFunction;
import net.minecraft.util.text.TextFormatting;
import java.util.regex.Matcher;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import net.minecraft.util.Tuple;
import java.util.List;
import java.util.regex.Pattern;

public class RGBSettings
{
    public static RGBSettings EMPTY;
    public static final Pattern PATTERN;
    private final List<IColor> colors;
    private int length;
    private Boolean bold;
    private Boolean italic;
    private Boolean underlined;
    private Boolean strikethrough;
    private Boolean obfuscated;

    public static List<Tuple<String, RGBSettings>> split(final String string) {
        if (string == null || string.isEmpty()) {
            return Collections.singletonList(new Tuple((Object)"", (Object)RGBSettings.EMPTY));
        }
        int index = 0;
        final List<Tuple<String, RGBSettings>> result = new ArrayList<Tuple<String, RGBSettings>>();
        final Matcher matcher = RGBSettings.PATTERN.matcher(string);
        RGBSettings lastSettings = RGBSettings.EMPTY;
        while (index < string.length()) {
            if (!matcher.find(index)) {
                final String subString = string.substring(index);
                lastSettings.addLength(subString.length());
                result.add((Tuple<String, RGBSettings>)new Tuple((Object)subString, (Object)lastSettings));
                break;
            }
            final String subString = string.substring(index, matcher.start());
            if (!subString.isEmpty()) {
                lastSettings.addLength(subString.length());
                result.add((Tuple<String, RGBSettings>)new Tuple((Object)subString, (Object)lastSettings));
            }
            final String format = matcher.group();
            if (format.startsWith("#")) {
                final String fString = matcher.group("rgb");
                lastSettings = new RGBSettings((List<IColor>)Arrays.stream(fString.split("-")).map(Colors::of).collect(Collectors.toList()));
            }
            else {
                if (!format.startsWith("§")) {
                    throw new IllegalStateException("Format: " + format);
                }
                final TextFormatting formatting = Utils.formattingOf(format.charAt(1));
                if (formatting == null) {
                    throw new NullPointerException("Format: " + format);
                }
                lastSettings = lastSettings.withFormat(formatting);
            }
            index = matcher.end();
        }
        return result;
    }

    public RGBSettings(final IColor color) {
        this(Collections.singletonList(color));
    }

    public RGBSettings(final List<IColor> colors) {
        this.colors = colors;
    }

    protected int warpIndex(final int index) {
        return index;
    }

    @Nullable
    public IColor getColorAt(final int index) {
        final int colorsLength = this.colors.size();
        if (colorsLength == 0) {
            return null;
        }
        if (colorsLength == 1) {
            return this.colors.get(0);
        }
        final int warpedIndex = this.warpIndex(index);
        final int preIndex = Math.max(0, Math.min(colorsLength - 1, (colorsLength - 1) * warpedIndex / Math.max(1, this.getLength() - 1)));
        final int postIndex = Math.max(0, Math.min(colorsLength - 1, preIndex + 1));
        float percent;
        if (preIndex == postIndex) {
            percent = 0.0f;
        }
        else {
            percent = Math.max(0.0f, Math.min(1.0f, (warpedIndex * (colorsLength - 1) - this.getLength() * preIndex) / ((postIndex - preIndex) * (float)this.getLength())));
        }
        final IColor pre = this.colors.get(preIndex);
        final IColor post = this.colors.get(postIndex);
        if (pre.equals(post)) {
            return pre;
        }

        final ToIntFunction<ToIntFunction<IColor>> mix = toIni -> Math.round(toIni.applyAsInt(pre) * (1.0f - percent) + toIni.applyAsInt(post) * percent);
        return new SimpleColor(mix.applyAsInt(IColor::red), mix.applyAsInt(IColor::green), mix.applyAsInt(IColor::blue));
    }

    public RGBSettings withFormat(final TextFormatting formatting) {
        if (formatting.isColor()) {
            return new RGBSettings(Colors.of(formatting));
        }
        if (formatting.isFancyStyling()) {
            return new WithFormat(this, new TextFormatting[] { formatting });
        }
        if (formatting == TextFormatting.RESET) {
            return RGBSettings.EMPTY;
        }
        throw new IllegalStateException(formatting.toString());
    }

    public boolean isFixedColor() {
        return this.colors.size() <= 1;
    }

    public int getLength() {
        return this.length;
    }

    public void addLength(final int toAdd) {
        this.length += toAdd;
    }

    public void setBold(final Boolean bold) {
        this.bold = bold;
    }

    public void setItalic(final Boolean italic) {
        this.italic = italic;
    }

    public void setUnderlined(final Boolean underlined) {
        this.underlined = underlined;
    }

    public void setStrikethrough(final Boolean strikethrough) {
        this.strikethrough = strikethrough;
    }

    public void setObfuscated(final Boolean obfuscated) {
        this.obfuscated = obfuscated;
    }

    public Boolean getFormatState(final TextFormatting formatting) {
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
            default: {
                return null;
            }
        }
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
        //return Stream.of(new TextFormatting[] { TextFormatting.BOLD, TextFormatting.ITALIC, TextFormatting.UNDERLINE, TextFormatting.STRIKETHROUGH, TextFormatting.OBFUSCATED }).filter(formatting -> this.getFormatState(formatting) == Boolean.TRUE).map((Function<? super TextFormatting, ?>)TextFormatting::toString).collect(Collectors.joining());
        return Stream.of(TextFormatting.BOLD, TextFormatting.ITALIC, TextFormatting.UNDERLINE, TextFormatting.STRIKETHROUGH, TextFormatting.OBFUSCATED).filter(formatting -> this.getFormatState((TextFormatting)formatting) == Boolean.TRUE).map(TextFormatting::toString).collect(Collectors.joining());
    }

    static {
        RGBSettings.EMPTY = new RGBSettings(Collections.emptyList()) {
            @Nullable
            @Override
            public SimpleColor getColorAt(final int index) {
                return null;
            }

            @Override
            public int getLength() {
                return 0;
            }

            @Override
            public void addLength(final int toAdd) {
            }

            @Override
            public void setBold(final Boolean bold) {
            }

            @Override
            public void setItalic(final Boolean italic) {
            }

            @Override
            public void setUnderlined(final Boolean underlined) {
            }

            @Override
            public void setStrikethrough(final Boolean strikethrough) {
            }

            @Override
            public void setObfuscated(final Boolean obfuscated) {
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
        PATTERN = Pattern.compile("(#(?<rgb>([0-9a-fA-F]{6})(-([0-9a-fA-F]{6}))*)|§(?<format>[0-9a-fA-FklmnorKLMNOR]))");
    }

    private static class WithFormat extends RGBSettings
    {
        private final RGBSettings parent;
        private final int startIndex;

        public WithFormat(final RGBSettings parent) {
            super(parent.colors);
            this.parent = parent;
            this.startIndex = parent.getLength();
        }

        public WithFormat(final RGBSettings parent, final TextFormatting... formattingArray) {
            this(parent);
            for (final TextFormatting formatting : formattingArray) {
                switch (formatting) {
                    case OBFUSCATED: {
                        this.setObfuscated(true);
                        break;
                    }
                    case BOLD: {
                        this.setBold(true);
                        break;
                    }
                    case STRIKETHROUGH: {
                        this.setStrikethrough(true);
                        break;
                    }
                    case UNDERLINE: {
                        this.setUnderlined(true);
                        break;
                    }
                    case ITALIC: {
                        this.setItalic(true);
                        break;
                    }
                }
            }
        }

        @Override
        protected int warpIndex(final int index) {
            return this.startIndex + index;
        }

        @Override
        public void addLength(final int toAdd) {
            this.parent.addLength(toAdd);
        }

        @Override
        public int getLength() {
            return this.parent.getLength();
        }

        private Boolean getFlag(final Function<RGBSettings, Boolean> getter) {
            final Boolean flag = getter.apply(this);
            if (flag == null) {
                return getter.apply(this.parent);
            }
            return flag;
        }

        @Override
        public Boolean getBold() {
            return this.getFlag(rgbSettings -> rgbSettings.bold);
        }

        @Override
        public Boolean getItalic() {
            return this.getFlag(rgbSettings -> rgbSettings.italic);
        }

        @Override
        public Boolean getUnderlined() {
            return this.getFlag(rgbSettings -> rgbSettings.underlined);
        }

        @Override
        public Boolean getStrikethrough() {
            return this.getFlag(rgbSettings -> rgbSettings.strikethrough);
        }

        @Override
        public Boolean getObfuscated() {
            return this.getFlag(rgbSettings -> rgbSettings.obfuscated);
        }
    }
}
