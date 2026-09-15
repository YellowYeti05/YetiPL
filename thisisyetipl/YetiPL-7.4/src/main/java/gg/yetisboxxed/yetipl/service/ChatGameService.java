package gg.yetisboxxed.yetipl.service;

import gg.yetisboxxed.yetipl.util.Msg;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ChatGameService {
    private final JavaPlugin plugin; private final CoinsService coins; private BukkitTask task; private volatile String answer; private final AtomicBoolean claimed=new AtomicBoolean(true); private final Random random=new Random();
    public ChatGameService(JavaPlugin plugin,CoinsService coins){this.plugin=plugin;this.coins=coins;}
    public void start(){if(task!=null)return;long period=Math.max(1200,plugin.getConfig().getLong("chat-games.interval-ticks",6000));task=Bukkit.getScheduler().runTaskTimer(plugin,this::newGame,period,period);}
    public void stop(){if(task!=null)task.cancel();task=null;}
    public void newGame(){if(Bukkit.getOnlinePlayers().size()<plugin.getConfig().getInt("chat-games.minimum-players",1))return;int type=random.nextInt(3);String question;if(type==0){int a=random.nextInt(40)+2,b=random.nextInt(40)+2;answer=String.valueOf(a+b);question="Math: "+a+" + "+b+" = ?";}else if(type==1){String[] words={"yeti","minecraft","golden","preset","boxxed"};String w=words[random.nextInt(words.length)];List<Character> chars=new ArrayList<>();for(char c:w.toCharArray())chars.add(c);Collections.shuffle(chars);StringBuilder j=new StringBuilder();chars.forEach(j::append);answer=w;question="Unscramble: "+j;}else{answer="yetisboxxed";question="Type it: YETISBOXXED";}claimed.set(false);Bukkit.broadcast(Msg.gold("Chat Game • "+question+" • First correct answer wins "+plugin.getConfig().getLong("chat-games.reward-coins",50)+" ❄"));Bukkit.getScheduler().runTaskLater(plugin,()->{if(!claimed.get()){claimed.set(true);Bukkit.broadcast(Msg.error("Chat Game ended. Answer: "+answer));}},20*45L);}
    public boolean submit(Player p,String message){if(claimed.get()||answer==null||!message.trim().equalsIgnoreCase(answer))return false;if(!claimed.compareAndSet(false,true))return false;long reward=plugin.getConfig().getLong("chat-games.reward-coins",50);Bukkit.getScheduler().runTask(plugin,()->{coins.add(p.getUniqueId(),reward,"chat game win");Bukkit.broadcast(Msg.gold(p.getName()+" won the Chat Game! +"+reward+" ❄"));});return true;}
}
