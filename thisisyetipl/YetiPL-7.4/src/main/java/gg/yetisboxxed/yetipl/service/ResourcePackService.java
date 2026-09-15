package gg.yetisboxxed.yetipl.service;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public final class ResourcePackService {
    private final JavaPlugin plugin;
    public ResourcePackService(JavaPlugin plugin){this.plugin=plugin;}
    public void send(Player p){String url=plugin.getConfig().getString("resource-pack.url","");if(url.isBlank())return;boolean required=plugin.getConfig().getBoolean("resource-pack.required",true);Component prompt=Component.text("✦ YETIBOXXED ✦ YetiPL Gold Resource Pack • Required for custom visuals");try{for(Method m:p.getClass().getMethods()){if(!m.getName().equals("setResourcePack")||m.getParameterCount()!=5)continue;Class<?>[] t=m.getParameterTypes();Object[] args=new Object[5];boolean ok=true;for(int i=0;i<5;i++){if(t[i]==UUID.class)args[i]=UUID.nameUUIDFromBytes(url.getBytes(StandardCharsets.UTF_8));else if(t[i]==String.class)args[i]=url;else if(t[i]==byte[].class)args[i]=null;else if(t[i]==boolean.class||t[i]==Boolean.class)args[i]=required;else if(Component.class.isAssignableFrom(t[i]))args[i]=prompt;else{ok=false;break;}}if(ok){m.invoke(p,args);return;}}p.setResourcePack(url);}catch(Throwable e){plugin.getLogger().warning("Could not send required resource pack to "+p.getName()+": "+e.getMessage());}}
}
