package gg.yetisboxxed.yetipl.service;

import gg.yetisboxxed.yetipl.storage.YamlStore;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class ChatFilterService {
    public enum Action{BLOCK,CENSOR,REPLACE}
    private final YamlStore store;
    public ChatFilterService(JavaPlugin plugin){store=new YamlStore(plugin,"chat-filter.yml");if(!store.yaml().contains("enabled")){store.yaml().set("enabled",true);store.save();}}
    public boolean enabled(){return store.yaml().getBoolean("enabled",true);}
    public void enabled(boolean b){store.yaml().set("enabled",b);store.save();}
    public void add(String word,Action action,String replacement){String k=key(word);store.yaml().set("rules."+k+".match",word);store.yaml().set("rules."+k+".action",action.name());store.yaml().set("rules."+k+".replacement",replacement);store.save();}
    public void remove(String word){store.yaml().set("rules."+key(word),null);store.save();}
    private String key(String s){return Base64.getUrlEncoder().withoutPadding().encodeToString(s.toLowerCase(Locale.ROOT).getBytes());}
    public Result apply(String message){if(!enabled())return new Result(false,message,null);var sec=store.yaml().getConfigurationSection("rules");if(sec==null)return new Result(false,message,null);String output=message;for(String k:sec.getKeys(false)){String b="rules."+k;String match=store.yaml().getString(b+".match","");if(match.isBlank())continue;String normalized=output.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]","");String needle=match.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]","");if(!normalized.contains(needle)&&!output.toLowerCase(Locale.ROOT).contains(match.toLowerCase(Locale.ROOT)))continue;Action a;try{a=Action.valueOf(store.yaml().getString(b+".action","BLOCK"));}catch(Exception e){a=Action.BLOCK;}if(a==Action.BLOCK)return new Result(true,message,match);String repl=a==Action.CENSOR?"****":store.yaml().getString(b+".replacement","****");output=output.replaceAll("(?i)"+java.util.regex.Pattern.quote(match),java.util.regex.Matcher.quoteReplacement(repl));}return new Result(false,output,null);}
    public record Result(boolean blocked,String message,String matched){}
}
