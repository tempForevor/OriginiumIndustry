package com.apcp.originium_industry;

import com.apcp.originium_industry.api.gtaddon.OIItem;
import com.apcp.originium_industry.api.tectree.TecTree;
import com.apcp.originium_industry.config.OIConfigHolder;
import com.apcp.originium_industry.data.block.OICustomBlocks;
import com.apcp.originium_industry.data.item.OIItems;
import com.apcp.originium_industry.data.machine.OIMachines;
import com.apcp.originium_industry.data.material.OIExtendMaterial;
import com.apcp.originium_industry.commmon.recipe_type.OICustomRecipeType;
import com.apcp.originium_industry.data.tectree.OITecTreeItems;
import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialRegistryEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.event.PostMaterialEvent;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import com.gregtechceu.gtceu.api.sound.SoundEntry;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.minecraftforge.fml.loading.FMLLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(OIMod.MOD_ID)
@SuppressWarnings("removal")
public class OIMod {

    public static final String MOD_ID = "originium_industry";
    public static final String CONFIG_ID = MOD_ID;
    public static final Logger LOGGER = LogManager.getLogger();
    public static GTRegistrate OIREGISTRATE = GTRegistrate.create(OIMod.MOD_ID);
    public static TecTree TEC_TREE = new TecTree();

    public OIMod() {

        //noinspection ConstantValue
        if (true) {
            IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

            modEventBus.addListener(this::commonSetup);
            modEventBus.addListener(this::clientSetup);

            modEventBus.addListener(this::addMaterialRegistries);
            modEventBus.addListener(this::addMaterials);
            modEventBus.addListener(this::modifyMaterials);

            modEventBus.addGenericListener(GTRecipeType.class, this::registerRecipeTypes);
            modEventBus.addGenericListener(MachineDefinition.class, this::registerMachines);
            modEventBus.addGenericListener(SoundEntry.class, this::registerSounds);

            // Most other events are fired on Forge's bus.
            // If we want to use annotations to register event listeners,
            // we need to register our object like this!
            MinecraftForge.EVENT_BUS.register(this);

            OIConfigHolder.init();
            OIItem.itemDeferredRegister.register(modEventBus);
            OIItems.init();
            OITecTreeItems.init(TEC_TREE);
            OICustomBlocks.init();
        }

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> LOGGER.info("Hello from common setup! This is *after* registries are done, so we can do this:"));
        OIAEAddon.onCellRegistry();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("Hey, we're on Minecraft version {}!", Minecraft.getInstance().getLaunchedVersion());
    }

    /**
     * Create a ResourceLocation in the format "modid:path"
     *
     * @return ResourceLocation with the namespace of your mod
     */
    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static boolean isDataGen() {
        return FMLLoader.getLaunchHandler().isData();
    }
    public static boolean isDev(){return !isProd();}
    public static boolean isProd(){return FMLLoader.isProduction();}

    /**
     * Create a material manager for your mod using GT's API.
     * You MUST have this if you have custom materials.
     * Remember to register them not to GT's namespace, but your own.
     *
     */
    private void addMaterialRegistries(MaterialRegistryEvent event) {
        GTCEuAPI.materialManager.createRegistry(OIMod.MOD_ID);
    }

    /**
     * You will also need this for registering custom materials
     * Call init() from your Material class(es) here
     *
     */
    private void addMaterials(MaterialEvent event) {
        // CustomMaterials.init();
        OIExtendMaterial.init(event);
    }

    /**
     * (Optional) Used to modify pre-existing materials from GregTech
     *
     */
    private void modifyMaterials(PostMaterialEvent event) {
        // CustomMaterials.modify();
    }

    /**
     * Used to register your own new RecipeTypes.
     * Call init() from your RecipeType class(es) here
     *
     */
    private void registerRecipeTypes(GTCEuAPI.RegisterEvent<ResourceLocation, GTRecipeType> event) {
        OICustomRecipeType.init();
    }

    /**
     * Used to register your own new machines.
     * Call init() from your Machine class(es) here
     *
     */
    private void registerMachines(GTCEuAPI.RegisterEvent<ResourceLocation, MachineDefinition> event) {
        // CustomMachines.init();
        LogManager.getLogger().info("[OIRegisterMachines] Registering machines");
        _registerMachines();
    }
    private void _registerMachines(){
        OIMachines.init();
    }

    /**
     * Used to register your own new sounds
     * Call init from your Sound class(es) here
     *
     */
    public void registerSounds(GTCEuAPI.RegisterEvent<ResourceLocation, SoundEntry> event) {
        // CustomSounds.init();
    }
}
