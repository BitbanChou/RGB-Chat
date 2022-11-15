//package com.fred.jianghun.truergb;
//
//import net.minecraft.util.Tuple;
//
//import java.util.*;
//import java.util.regex.Pattern;
//
//import static com.fred.jianghun.truergb.RGBSettings.split;
//
//public class test {
//    public static final Pattern PATTERN = Pattern.compile("(#(?<rgb>([0-9a-fA-F]{8})(-([0-9a-fA-F]{8}))(-([0-9a-fA-F]{8}))*)|\u00a7(?<format>[0-9a-fA-FklmnorKLMNOR]))");
//    private static Integer color=0;
//    String s="#FFFF69B4-FFBBAACC萌芽引擎GOGOGO";
//    //#FFFF69B4-FFBBAACC-FFB0E0E6萌芽引擎GoGoGo
//    public static void main(String[] args) {
//        List<Tuple<String, RGBSettings>> settings = split("#FFFF69B4-FFBBAACC-FFB0E0E6萌芽引擎GoGoGo");
//        for (Tuple<String, RGBSettings> tuple : settings) {
//            String s = (String) tuple.getFirst();
//            RGBSettings set = (RGBSettings) tuple.getSecond();
//            System.out.println(set.getLength());
//            System.out.println("String: " + s);
//
//            Set<Integer> se=new HashSet();
//            List<Integer> sset = new ArrayList<>();
//
//            for (int j = 0; j < set.getLength(); j++) {
//                {
//                    int cc=set.getColorAt(j).toInt();
//                    System.out.println(set.getColorAt(j)+": "+cc);
//                    if(se.isEmpty() || !se.contains(cc))
//                    {
//                        sset.add(cc);
//                        se.add(cc);
//                        System.out.println(set.getColorAt(j).red() + ", " + set.getColorAt(j).green() + ", " + set.getColorAt(j).blue() + ", " + set.getColorAt(j).alpha());
//                    }
//                }
//            }
//
//            System.out.println("Set length: " + sset.size());
//
//            if (set.isFixedColor()) {
//                    int cColor = Optional.ofNullable(set.getColorAt(0)).map(IColor::toInt).orElse(color);
//                    //super.drawString(set.getFormatString() + s, this.posX, y, cColor, dropShadow);
//                    //System.out.println(cColor);
//                    continue;
//            }
//
//            List<Integer> colorSet = new ArrayList<>();
//
//                int partlen = (int) Math.ceil(1.0 * s.length() / (sset.size() - 1));
//                System.out.println("partlen: "+partlen);
//                for (int i = 0; i < s.length(); i++) {
//                    int pre=sset.get(Math.min(i / partlen, sset.size() - 1));
//                    int cur=sset.get(Math.min(i / partlen + 1, sset.size() - 1));
//                    int increment = ((cur - pre)/ partlen);
//                    colorSet.add((int)pre + (int) increment * (i % partlen));
//                    System.out.println("increment: "+increment+" ,grad: "+increment * (i % partlen));
//                    System.out.println("word: "+s.charAt(i)+",color: "+((int)pre + (int) increment * (i % partlen)));
////                colourGradient.setGradient(spectrum[i], spectrum[i + 1]);
////                colourGradient.setNumberRange(minNum + increment * i, minNum + increment * (i + 1));
////                colourGradients.add(colourGradient);
//                }
//
//                for (int i = 0; i < s.length(); ++i) {
//                    //cColor = Optional.ofNullable(set.getColorAt(i)).map(IColor::toInt).orElse(colorSet.get(i));
//                    int cColor = colorSet.get(i);
//                    //System.out.println(cColor);
//                    //super.drawString(set.getFormatString() + s.charAt(i), this.posX, y, cColor, dropShadow);
//                }
//
//        }
//        //System.out.println("hello world");
//    }
//
//
//}
