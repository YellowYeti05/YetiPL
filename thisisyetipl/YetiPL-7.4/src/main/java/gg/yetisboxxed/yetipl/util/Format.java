package gg.yetisboxxed.yetipl.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Format {
    private static final Pattern AMP_HEX=Pattern.compile("&#([0-9a-fA-F]{6})");
    private static final Pattern AMP_X=Pattern.compile("&x&([0-9a-fA-F])&([0-9a-fA-F])&([0-9a-fA-F])&([0-9a-fA-F])&([0-9a-fA-F])&([0-9a-fA-F])");
    private static final Pattern SEC_X=Pattern.compile("§x§([0-9a-fA-F])§([0-9a-fA-F])§([0-9a-fA-F])§([0-9a-fA-F])§([0-9a-fA-F])§([0-9a-fA-F])");
    private Format(){}
    public static String birdflopToMini(String input){if(input==null)return "";String s=input;Matcher m=AMP_HEX.matcher(s);StringBuffer b=new StringBuffer();while(m.find())m.appendReplacement(b,Matcher.quoteReplacement("<#"+m.group(1)+">"));m.appendTail(b);s=b.toString();s=convertX(s,AMP_X);s=convertX(s,SEC_X);return s;}
    private static String convertX(String s,Pattern p){Matcher m=p.matcher(s);StringBuffer b=new StringBuffer();while(m.find()){String hex=m.group(1)+m.group(2)+m.group(3)+m.group(4)+m.group(5)+m.group(6);m.appendReplacement(b,Matcher.quoteReplacement("<#"+hex+">"));}m.appendTail(b);return b.toString();}
    public static Component component(String input){try{return MiniMessage.miniMessage().deserialize(birdflopToMini(input));}catch(Exception e){return Component.text(input==null?"":input);}}
    public static String legacy(String input){return LegacyComponentSerializer.legacySection().serialize(component(input));}
    public static String formatSafe(String input){return legacy(input).replace("%","%%");}
}
