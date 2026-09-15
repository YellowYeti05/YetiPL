package gg.yetisboxxed.yetipl;

import gg.yetisboxxed.yetipl.command.FeatureCommand;
import gg.yetisboxxed.yetipl.command.UpdatesCommand;
import gg.yetisboxxed.yetipl.command.YetiPLCommand;
import gg.yetisboxxed.yetipl.config.ConfigValidator;
import gg.yetisboxxed.yetipl.diagnostics.ErrorReporter;
import gg.yetisboxxed.yetipl.diagnostics.SelfTestService;
import gg.yetisboxxed.yetipl.listener.CoreListener;
import gg.yetisboxxed.yetipl.module.ModuleManager;
import gg.yetisboxxed.yetipl.module.SimpleModule;
import gg.yetisboxxed.yetipl.service.*;
import gg.yetisboxxed.yetipl.util.Items;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;

public final class YetiPLPlugin extends JavaPlugin {
    private ErrorReporter errors; private ModuleManager modules; private SelfTestService selfTests;
    private ItemEditorService itemEditor; private CosmeticService cosmetics; private PermissionsBridgeService permissionsBridge; private MentionService mentions; private SelectionService selections; private CoinsService coins; private WarpService warps; private PresetService presets; private CustomItemService customItems; private ShopService shop; private CrateService crates; private GenService gens; private ArchaeologyService archaeology; private RegionService regions; private PortalService portals; private RankService ranks; private BugService bugs; private BanService bans; private PotionService potions; private HologramService holograms; private VaultService vaults; private ChatFilterService chatFilter; private LagProtectorService lagProtector; private ItemDisguiseService itemDisguises; private ModerationService moderation; private TrimService trims; private DiscService discs; private ShopkeeperService shopkeepers; private RulesService rules; private MenuService menus; private WorldEditService worldEdit; private ChatGameService chatGames; private ScoreboardService scoreboard; private ResourcePackService resourcePack;
    private NamespacedKey wandKey;

    @Override public void onEnable(){
        saveDefaultConfig();
        this.errors=new ErrorReporter(this,getConfig().getInt("error-handling.max-recent-errors",100)); this.modules=new ModuleManager(errors); this.selfTests=new SelfTestService(modules); this.wandKey=new NamespacedKey(this,"selection_wand");
        initServices(); registerModules(); wireCommands(); getServer().getPluginManager().registerEvents(new CoreListener(this),this);
        safeEnable("presets",()->presets.enable()); safeEnable("holograms",()->holograms.load()); safeEnable("lag-protector",()->lagProtector.start()); safeEnable("chat-games",()->chatGames.start()); safeEnable("scoreboard",()->scoreboard.start()); safeEnable("cosmetics",()->cosmetics.start());
        List<String> issues=ConfigValidator.validate(getConfig());issues.forEach(i->getLogger().warning("Config issue: "+i));if(getConfig().getBoolean("startup.run-self-test",true))selfTests.run().forEach(f->getLogger().warning("Self-test: "+f));
        getComponentLogger().info(Component.text("✦ YetiPL 7.4 enabled ✦",NamedTextColor.GOLD));getLogger().info("Target: Paper 26.2 | Java 25 | Error isolation: enabled | Systems registered: "+modules.snapshot().size());
    }
    private void initServices(){selections=new SelectionService();coins=new CoinsService(this);warps=new WarpService(this);presets=new PresetService(this,coins);customItems=new CustomItemService(this);shop=new ShopService(this,coins);crates=new CrateService(this);gens=new GenService(this);archaeology=new ArchaeologyService(this);regions=new RegionService(this);portals=new PortalService(this);ranks=new RankService(this);bugs=new BugService(this);bans=new BanService(this);potions=new PotionService(this);holograms=new HologramService(this);vaults=new VaultService(this);chatFilter=new ChatFilterService(this);lagProtector=new LagProtectorService(this);itemDisguises=new ItemDisguiseService();moderation=new ModerationService();trims=new TrimService(this);discs=new DiscService(this);shopkeepers=new ShopkeeperService(this);rules=new RulesService(this);menus=new MenuService(this,presets,coins,shop,customItems);worldEdit=new WorldEditService(selections,getConfig().getInt("worldedit.max-blocks",100000));chatGames=new ChatGameService(this,coins);scoreboard=new ScoreboardService(this,coins,presets,ranks);resourcePack=new ResourcePackService(this);itemEditor=new ItemEditorService(this);cosmetics=new CosmeticService(this,coins);permissionsBridge=new PermissionsBridgeService(this);mentions=new MentionService(this);}
    private void registerModules(){for(String id:List.of("diagnostics","updates","worldedit","regions","warps","portals","ranks","vaults","enderchests","presets","yeti-coins","shop","custom-items","crates","gens","archaeology","potions","holograms","chat-filter","chat-games","mentions","moderation","anti-cheat","lag-protector","resource-pack","trims","shopkeepers","music-discs","bugs","item-disguise","scoreboard","workstations","item-editor","cosmetics","permissions-bridge")){SimpleModule m=new SimpleModule(id,title(id));modules.register(m);if(getConfig().getBoolean("modules."+id,true))modules.enable(id);}}
    private String title(String id){return java.util.Arrays.stream(id.split("-")).map(x->Character.toUpperCase(x.charAt(0))+x.substring(1)).collect(java.util.stream.Collectors.joining(" "));}
    private void wireCommands(){YetiPLCommand yc=new YetiPLCommand(this);Objects.requireNonNull(getCommand("yetipl")).setExecutor(yc);Objects.requireNonNull(getCommand("yetipl")).setTabCompleter(yc);Objects.requireNonNull(getCommand("updates")).setExecutor(new UpdatesCommand());FeatureCommand feature=new FeatureCommand(this);for(String c:List.of("wand","setwarp","warp","warps","delwarp","preset","upgrades","yeticoins","shop","customitems","crate","bindcrate","bindcratekey","gen","arch","rg","portal","potionmaker","holo","rules","discord","chatfilter","chatcooldown","chatcolor","prefix","affix","rank","bugs","seebugs","itemdisguise","disguise","ban","banip","banlist","vanish","spectate","freeze","anvil","craft","grindstone","smith","blastfurnace","smoker","workstation","head","enchant","ride","trim","commands","vault","pv","enderchest","ec","shopkeeper","disc","nakedkilling","yac","nbt","lpgui","mentions","chatgames")){var pc=getCommand(c);if(pc!=null){pc.setExecutor(feature);pc.setTabCompleter(feature);}}}
    private void safeEnable(String module,Runnable r){try{r.run();}catch(Throwable t){errors.report(module,t);}}
    @Override public void onDisable(){if(chatGames!=null)chatGames.stop();if(scoreboard!=null)scoreboard.stop();if(cosmetics!=null)cosmetics.stop();if(lagProtector!=null)lagProtector.stop();if(modules!=null)modules.disableAll();}
    public ItemStack wand(){ItemStack i=Items.named(Material.GOLDEN_AXE,"✦ YetiPL Selection Wand ✦","Left-click: Position 1","Right-click: Position 2");var m=i.getItemMeta();m.getPersistentDataContainer().set(wandKey,PersistentDataType.BYTE,(byte)1);i.setItemMeta(m);return i;}
    public boolean isWand(ItemStack i){return i!=null&&i.hasItemMeta()&&i.getItemMeta().getPersistentDataContainer().has(wandKey,PersistentDataType.BYTE);}
    public ErrorReporter errors(){return errors;} public ModuleManager modules(){return modules;} public SelfTestService selfTests(){return selfTests;} public SelectionService selections(){return selections;} public CoinsService coins(){return coins;} public WarpService warps(){return warps;} public PresetService presets(){return presets;} public CustomItemService customItems(){return customItems;} public ShopService shop(){return shop;} public CrateService crates(){return crates;} public GenService gens(){return gens;} public ArchaeologyService archaeology(){return archaeology;} public RegionService regions(){return regions;} public PortalService portals(){return portals;} public RankService ranks(){return ranks;} public BugService bugs(){return bugs;} public BanService bans(){return bans;} public PotionService potions(){return potions;} public HologramService holograms(){return holograms;} public VaultService vaults(){return vaults;} public ChatFilterService chatFilter(){return chatFilter;} public LagProtectorService lagProtector(){return lagProtector;} public ItemDisguiseService itemDisguises(){return itemDisguises;} public ModerationService moderation(){return moderation;} public TrimService trims(){return trims;} public DiscService discs(){return discs;} public ShopkeeperService shopkeepers(){return shopkeepers;} public RulesService rules(){return rules;} public MenuService menus(){return menus;} public WorldEditService worldEdit(){return worldEdit;} public ChatGameService chatGames(){return chatGames;} public ResourcePackService resourcePack(){return resourcePack;} public ItemEditorService itemEditor(){return itemEditor;} public CosmeticService cosmetics(){return cosmetics;} public PermissionsBridgeService permissionsBridge(){return permissionsBridge;} public MentionService mentions(){return mentions;}
}
