package com.apcp.originium_industry.data.machine.part;

import com.apcp.originium_industry.config.OIConfigHolder;
import com.apcp.originium_industry.data.machine.OICustomMachines;
import com.gregtechceu.gtceu.api.capability.recipe.*;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.fancy.ConfiguratorPanel;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.api.machine.fancyconfigurator.ButtonConfigurator;
import com.gregtechceu.gtceu.api.machine.fancyconfigurator.CircuitFancyConfigurator;
import com.gregtechceu.gtceu.api.machine.fancyconfigurator.FancyInvConfigurator;
import com.gregtechceu.gtceu.api.machine.fancyconfigurator.FancyTankConfigurator;
import com.gregtechceu.gtceu.api.machine.feature.IDataStickInteractable;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.machine.trait.*;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;
import com.gregtechceu.gtceu.api.recipe.ingredient.SizedIngredient;
import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler;
import com.gregtechceu.gtceu.common.item.IntCircuitBehaviour;
import com.gregtechceu.gtceu.integration.ae2.gui.widget.AETextInputButtonWidget;
import com.gregtechceu.gtceu.integration.ae2.gui.widget.slot.AEPatternViewSlotWidget;
import com.gregtechceu.gtceu.integration.ae2.machine.MEBusPartMachine;
import com.gregtechceu.gtceu.integration.ae2.machine.MEPatternBufferProxyPartMachine;
import com.gregtechceu.gtceu.utils.GTMath;
import com.gregtechceu.gtceu.utils.ItemStackHashStrategy;

import com.lowdragmc.lowdraglib.gui.texture.GuiTextureGroup;
import com.lowdragmc.lowdraglib.gui.texture.TextTexture;
import com.lowdragmc.lowdraglib.gui.util.ClickData;
import com.lowdragmc.lowdraglib.gui.widget.ButtonWidget;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.IContentChangeAware;
import com.lowdragmc.lowdraglib.syncdata.ITagSerializable;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;

import appeng.api.crafting.IPatternDetails;
import appeng.api.crafting.PatternDetailsHelper;
import appeng.api.implementations.blockentities.PatternContainerGroup;
import appeng.api.inventories.InternalInventory;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridNodeListener;
import appeng.api.networking.crafting.ICraftingProvider;
import appeng.api.stacks.*;
import appeng.api.storage.MEStorage;
import appeng.api.storage.StorageHelper;
import appeng.crafting.pattern.EncodedPatternItem;
import appeng.crafting.pattern.ProcessingPatternItem;
import appeng.helpers.patternprovider.PatternContainer;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import it.unimi.dsi.fastutil.objects.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.*;

import javax.annotation.ParametersAreNonnullByDefault;

/* unused */
@SuppressWarnings("DataFlowIssue")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SuperMEPatternBufferPart extends MEBusPartMachine
        implements ICraftingProvider, PatternContainer, IDataStickInteractable {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            SuperMEPatternBufferPart.class, MEBusPartMachine.MANAGED_FIELD_HOLDER);
    public static int getRowSize(){
        return OIConfigHolder.INSTANCE.maxMEPatternRow;
    }
    public static int getColSize(){
        return OIConfigHolder.INSTANCE.maxMEPatternCol;
    }
    public static int getMaxPageSlots(){
        return getRowSize() * getColSize();
    }
    public static int getMaxPage(){
        return OIConfigHolder.INSTANCE.maxMEPatternPage;
    }
    public static int uiPos2SlotPos(int page,int row,int col){
        return getRowSize() * (page * getColSize() + col) + row;
    }
    public static int getMaxPatternCount(){
        return getMaxPage()*getMaxPageSlots();
    }
    public final InternalInventory internalPatternInventory = new InternalInventory() {

        @Override
        public int size() {
            return getMaxPatternCount();
        }

        @Override
        public ItemStack getStackInSlot(int slotIndex) {
            return patternInventory.getStackInSlot(slotIndex);
        }

        @Override
        public void setItemDirect(int slotIndex, ItemStack stack) {
            patternInventory.setStackInSlot(slotIndex, stack);
            patternInventory.onContentsChanged(slotIndex);
            onPatternChange(slotIndex);
        }
    };

    @Getter
    @Persisted
    @DescSynced
    // version.
    public CustomItemStackHandler patternInventory;

    @Getter
    @Persisted
    public NotifiableItemStackHandler shareInventory;

    @Getter
    @Persisted
    public NotifiableFluidTank shareTank;

    @Getter
    @Persisted
    public List<InternalSlot> internalInventory;

    public BiMap<IPatternDetails, InternalSlot> detailsSlotMap;

    @DescSynced
    @Persisted
    @Setter
    public String customName = "";

    public int displayPage = 0;

    public boolean needPatternSync;

    @Persisted
    public final Set<BlockPos> proxies = new ObjectOpenHashSet<>();
    public final Set<MEPatternBufferProxyPartMachine> proxyMachines = new ReferenceOpenHashSet<>();

    @Getter
    protected InternalSlotRecipeHandler internalRecipeHandler;

    @Nullable
    protected TickableSubscription updateSubs;

    public SuperMEPatternBufferPart(IMachineBlockEntity holder, Object... args) {
        super(holder, IO.IN, args);
        initInv();
        getMainNode().addService(ICraftingProvider.class, this);
    }

    public void initInv(){
        this.internalInventory = new ArrayList<>(getMaxPatternCount());
        this.detailsSlotMap = HashBiMap.create(getMaxPatternCount());
        this.patternInventory = new CustomItemStackHandler(getMaxPatternCount());
        this.patternInventory.setFilter(stack -> stack.getItem() instanceof ProcessingPatternItem);
        this.internalInventory.replaceAll(ignored -> new InternalSlot());
        this.shareInventory = new NotifiableItemStackHandler(this, 9, IO.IN, IO.NONE);
        this.shareTank = new NotifiableFluidTank(this, 9, 8 * FluidType.BUCKET_VOLUME, IO.IN, IO.NONE);
        this.internalRecipeHandler = new InternalSlotRecipeHandler(this, (InternalSlot[]) internalInventory.toArray());
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (getLevel() instanceof ServerLevel serverLevel) {
            serverLevel.getServer().tell(new TickTask(1, () -> {
                for (int i = 0; i < patternInventory.getSlots(); i++) {
                    var pattern = patternInventory.getStackInSlot(i);
                    var patternDetails = PatternDetailsHelper.decodePattern(pattern, getLevel());
                    if (patternDetails != null) {
                        this.detailsSlotMap.put(patternDetails, this.internalInventory.get(i));
                    }
                }
                needPatternSync = true;
            }));
        }
    }

    @Override
    public List<RecipeHandlerList> getRecipeHandlers() {
        return internalRecipeHandler.getSlotHandlers();
    }

    @Override
    public boolean isWorkingEnabled() {
        return true;
    }

    @Override
    public void setWorkingEnabled(boolean ignored) {}

    @Override
    public boolean isDistinct() {
        return true;
    }

    @Override
    public void setDistinct(boolean ignored) {}

    @Override
    public void onMainNodeStateChanged(IGridNodeListener.State reason) {
        super.onMainNodeStateChanged(reason);
        this.updateSubscription();
    }

    protected void updateSubscription() {
        if (getMainNode().isOnline()) {
            updateSubs = subscribeServerTick(updateSubs, this::update);
        } else if (updateSubs != null) {
            updateSubs.unsubscribe();
            updateSubs = null;
        }
    }

    protected void update() {
        if (needPatternSync) {
            ICraftingProvider.requestUpdate(getMainNode());
            this.needPatternSync = false;
        }
    }

    public void addProxy(MEPatternBufferProxyPartMachine proxy) {
        proxies.add(proxy.getPos());
        proxyMachines.add(proxy);
    }

    public void removeProxy(MEPatternBufferProxyPartMachine proxy) {
        proxies.remove(proxy.getPos());
        proxyMachines.remove(proxy);
    }

    @UnmodifiableView
    public Set<MEPatternBufferProxyPartMachine> getProxies() {
        if (proxyMachines.size() != proxies.size()) {
            proxyMachines.clear();
            for (var pos : proxies) {
                if (MetaMachine.getMachine((getLevel()), pos) instanceof MEPatternBufferProxyPartMachine proxy) {
                    proxyMachines.add(proxy);
                }
            }
        }
        return Collections.unmodifiableSet(proxyMachines);
    }

    private void refundAll(ClickData clickData) {
        if (!clickData.isRemote) {
            for (InternalSlot internalSlot : internalInventory) {
                internalSlot.refund();
            }
        }
    }

    private void onPatternChange(int index) {
        if (isRemote()) return;

        // remove old if applicable
        var internalInv = internalInventory.get(index);
        var newPattern = patternInventory.getStackInSlot(index);
        var newPatternDetails = PatternDetailsHelper.decodePattern(newPattern, getLevel());
        var oldPatternDetails = detailsSlotMap.inverse().get(internalInv);
        detailsSlotMap.forcePut(newPatternDetails, internalInv);
        if (oldPatternDetails != null && !oldPatternDetails.equals(newPatternDetails)) {
            internalInv.refund();
        }

        needPatternSync = true;
    }

    //////////////////////////////////////
    // ********** GUI ***********//
    //////////////////////////////////////
    /// region UI

    @Override
    public void attachConfigurators(ConfiguratorPanel configuratorPanel) {
        configuratorPanel.attachConfigurators(new ButtonConfigurator(
                new GuiTextureGroup(GuiTextures.BUTTON, GuiTextures.REFUND_OVERLAY), this::refundAll)
                .setTooltips(List.of(Component.translatable("gui.gtceu.refund_all.desc"))));
        if (isHasCircuitSlot() && isCircuitSlotEnabled()) {
            configuratorPanel.attachConfigurators(new CircuitFancyConfigurator(circuitInventory.storage));
        }
        configuratorPanel.attachConfigurators(new FancyInvConfigurator(
                shareInventory.storage, Component.translatable("gui.gtceu.share_inventory.title"))
                .setTooltips(List.of(
                        Component.translatable("gui.gtceu.share_inventory.desc.0"),
                        Component.translatable("gui.gtceu.share_inventory.desc.1"))));
        configuratorPanel.attachConfigurators(new FancyTankConfigurator(
                shareTank.getStorages(), Component.translatable("gui.gtceu.share_tank.title"))
                .setTooltips(List.of(
                        Component.translatable("gui.gtceu.share_tank.desc.0"),
                        Component.translatable("gui.gtceu.share_inventory.desc.1"))));
    }

    public class SlotWidgetGroup extends WidgetGroup {
        public int rowSize;
        public int colSize;

        public SlotWidgetGroup(int px,int py,int rowSize,int colSize) {
            super(px,py,18*rowSize,18*colSize);
            this.rowSize = rowSize;
            this.colSize = colSize;
            createWidget();
        }

        public void createWidget(){
            for (int y = 0; y < colSize; ++y) {
                for (int x = 0; x < rowSize; ++x) {
                    int finalI = uiPos2SlotPos(displayPage,x,y);
                    var slot = new AEPatternViewSlotWidget(patternInventory, finalI, 8 + x * 18, 14 + y * 18)
                            .setOccupiedTexture(GuiTextures.SLOT)
                            .setItemHook(stack -> {
                                if (!stack.isEmpty() && stack.getItem() instanceof EncodedPatternItem iep) {
                                    final ItemStack out = iep.getOutput(stack);
                                    if (!out.isEmpty()) {
                                        return out;
                                    }
                                }
                                return stack;
                            })
                            .setChangeListener(() -> onPatternChange(finalI))
                            .setBackground(GuiTextures.SLOT, GuiTextures.PATTERN_OVERLAY);
                    addWidget(slot);
                }
            }
        }
    }

    public WidgetGroup createSlotUI(){
        int rowSize = getRowSize();
        int colSize = getColSize();
        var group = new WidgetGroup(0, 0, 18 * rowSize + 32, 18 * colSize + 32);
        var slot_group = new SlotWidgetGroup(0,0,rowSize,colSize);
        group.addWidget(slot_group);
        return group;
    }

    public WidgetGroup mainUIGroup;

    public void refreshSlotUI(){
        var slot_group = mainUIGroup.getWidgetsByType(SlotWidgetGroup.class).get(0);
        mainUIGroup.removeWidget(slot_group);
        slot_group.clearAllWidgets();
        // TODO : How to release an UI?
        int rowSize = getRowSize();
        int colSize = getColSize();
        var new_slot_group = new SlotWidgetGroup(0,0,rowSize,colSize);
        mainUIGroup.addWidget(new_slot_group);
    }

    @Override
    public Widget createUIWidget() {
        var group = createSlotUI();
        // ME Network status
        group.addWidget(new LabelWidget(
                8,
                2,
                () -> this.isOnline ? "gtceu.gui.me_network.online" : "gtceu.gui.me_network.offline"));

        group.addWidget(new AETextInputButtonWidget(18 * getRowSize() + 8 - 70, 2, 70, 10)
                .setText(customName)
                .setOnConfirm(this::setCustomName)
                .setButtonTooltips(Component.translatable("gui.gtceu.rename.desc")));

        group.addWidget(new ButtonWidget(
                16, 18*getColSize() + 18,
                16, 16,
                new TextTexture("<[").setBackgroundColor(0xddddee).setColor(0x000000),
                clickData -> {
                    displayPage = (int) GTMath.clamp(displayPage - 1, 0, getMaxPage()-1);
                    refreshSlotUI();
                }
        ));
        group.addWidget(new ButtonWidget(
                18*getRowSize() - 16, 18*getColSize() + 18,
                16, 16,
                new TextTexture("]>").setBackgroundColor(0xddddee).setColor(0x000000),
                clickData -> {
                    displayPage = (int) GTMath.clamp(displayPage + 1, 0, getMaxPage()-1);
                    refreshSlotUI();
                }
        ));
        group.addWidget(new LabelWidget(
                18*getRowSize()/2,18*getColSize() + 18,
                ()->"Page : " + Integer.valueOf(displayPage).toString()
        ));
        mainUIGroup = group;
        return group;
    }

    /// endregion UI

    @Override
    public List<IPatternDetails> getAvailablePatterns() {
        return detailsSlotMap.keySet().stream().filter(Objects::nonNull).toList();
    }

    @Override
    public boolean pushPattern(IPatternDetails patternDetails, KeyCounter[] inputHolder) {
        if (!isFormed() || !getMainNode().isActive() || !detailsSlotMap.containsKey(patternDetails) ||
                !checkInput(inputHolder)) {
            return false;
        }

        var slot = detailsSlotMap.get(patternDetails);
        if (slot != null) {
            slot.pushPattern(patternDetails, inputHolder);
            return true;
        }
        return false;
    }

    @Override
    public boolean isBusy() {
        return false;
    }

    private boolean checkInput(KeyCounter[] inputHolder) {
        for (KeyCounter input : inputHolder) {
            var illegal = input.keySet().stream()
                    .map(AEKey::getType)
                    .map(AEKeyType::getId)
                    .anyMatch(id -> !id.equals(AEKeyType.items().getId()) && !id.equals(AEKeyType.fluids().getId()));
            if (illegal) return false;
        }
        return true;
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Override
    public @Nullable IGrid getGrid() {
        return getMainNode().getGrid();
    }

    @Override
    public InternalInventory getTerminalPatternInventory() {
        return internalPatternInventory;
    }

    @Override
    public PatternContainerGroup getTerminalGroup() {
        // Has controller
        if (isFormed()) {
            IMultiController controller = getControllers().first();
            MultiblockMachineDefinition controllerDefinition = controller.self().getDefinition();
            // has customName
            if (!customName.isEmpty()) {
                return new PatternContainerGroup(
                        AEItemKey.of(controllerDefinition.asStack()),
                        Component.literal(customName),
                        Collections.emptyList());
            } else {
                ItemStack circuitStack = isHasCircuitSlot() ? circuitInventory.storage.getStackInSlot(0) :
                        ItemStack.EMPTY;
                int circuitConfiguration = circuitStack.isEmpty() ? -1 :
                        IntCircuitBehaviour.getCircuitConfiguration(circuitStack);

                Component groupName = circuitConfiguration != -1 ?
                        Component.translatable(controllerDefinition.getDescriptionId())
                                .append(" - " + circuitConfiguration) :
                        Component.translatable(controllerDefinition.getDescriptionId());

                return new PatternContainerGroup(
                        AEItemKey.of(controllerDefinition.asStack()), groupName, Collections.emptyList());
            }
        } else {
            if (!customName.isEmpty()) {
                return new PatternContainerGroup(
                        AEItemKey.of(OICustomMachines.SUPER_ME_PATTERN_BUFFER.machine.getItem()),
                        Component.literal(customName),
                        Collections.emptyList());
            } else {
                return new PatternContainerGroup(
                        AEItemKey.of(OICustomMachines.SUPER_ME_PATTERN_BUFFER.machine.getItem()),
                        OICustomMachines.SUPER_ME_PATTERN_BUFFER.machine.get().getDefinition().getItem().getDescription(),
                        Collections.emptyList());
            }
        }
    }

    @Override
    public void onMachineRemoved() {
        clearInventory(patternInventory);
        clearInventory(shareInventory);
    }

    @Override
    public InteractionResult onDataStickShiftUse(Player player, ItemStack dataStick) {
        dataStick.getOrCreateTag().putIntArray("super_pos", new int[] { getPos().getX(), getPos().getY(), getPos().getZ() });
        return InteractionResult.SUCCESS;
    }

    public record BufferData(Object2LongMap<ItemStack> items, Object2LongMap<FluidStack> fluids) {}

    public BufferData mergeInternalSlots() {
        var items = new Object2LongOpenCustomHashMap<>(ItemStackHashStrategy.comparingAllButCount());
        var fluids = new Object2LongOpenHashMap<FluidStack>();
        for (InternalSlot slot : internalInventory) {
            slot.itemInventory.object2LongEntrySet().fastForEach(e -> items.addTo(e.getKey(), e.getLongValue()));
            slot.fluidInventory.object2LongEntrySet().fastForEach(e -> fluids.addTo(e.getKey(), e.getLongValue()));
        }
        return new BufferData(items, fluids);
    }

    public final class InternalSlotRecipeHandler {

        @Getter
        private final List<RecipeHandlerList> slotHandlers;

        public InternalSlotRecipeHandler(SuperMEPatternBufferPart buffer, InternalSlot[] slots) {
            this.slotHandlers = new ArrayList<>(slots.length);
            for (int i = 0; i < slots.length; i++) {
                var rhl = new InternalSlotRecipeHandler.SlotRHL(buffer, slots[i], i);
                slotHandlers.add(rhl);
            }
        }

        @Getter
        protected static class SlotRHL extends RecipeHandlerList {

            private final SlotItemRecipeHandler itemRecipeHandler;
            private final SlotFluidRecipeHandler fluidRecipeHandler;

            public SlotRHL(SuperMEPatternBufferPart buffer, InternalSlot slot, int idx) {
                super(IO.IN);
                itemRecipeHandler = new InternalSlotRecipeHandler.SlotItemRecipeHandler(buffer, slot, idx);
                fluidRecipeHandler = new InternalSlotRecipeHandler.SlotFluidRecipeHandler(buffer, slot, idx);
                addHandlers(buffer.getCircuitInventory(), buffer.getShareInventory(), buffer.getShareTank(),
                        itemRecipeHandler, fluidRecipeHandler);
                this.setGroup(RecipeHandlerGroupDistinctness.BUS_DISTINCT);
            }

            @Override
            public boolean isDistinct() {
                return true;
            }

            @Override
            public void setDistinct(boolean ignored, boolean notify) {
            }
        }

        @SuppressWarnings("DataFlowIssue")
        @Getter
        private static class SlotFluidRecipeHandler extends NotifiableRecipeHandlerTrait<FluidIngredient> {

            private final InternalSlot slot;
            private final int priority;

            private final int size = 81;
            private final RecipeCapability<FluidIngredient> capability = FluidRecipeCapability.CAP;
            private final IO handlerIO = IO.IN;
            private final boolean isDistinct = true;

            private SlotFluidRecipeHandler(SuperMEPatternBufferPart buffer, InternalSlot slot, int index) {
                super(buffer);
                this.slot = slot;
                this.priority = IFilteredHandler.HIGH + index + 1;
                slot.setOnContentsChanged(this::notifyListeners);
            }

            @Override
            public List<FluidIngredient> handleRecipeInner(IO io, GTRecipe recipe, List<FluidIngredient> left,
                                                           boolean simulate) {
                if (io != IO.IN || slot.isFluidEmpty()) return left;
                return (slot.handleFluidInternal(left, simulate));
            }

            @Override
            public List<Object> getContents() {
                return new ArrayList<>(slot.getFluids());
            }

            @Override
            public double getTotalContentAmount() {
                return slot.getFluids().stream().mapToLong(FluidStack::getAmount).sum();
            }
        }

        @SuppressWarnings("DataFlowIssue")
        @Getter
        private static class SlotItemRecipeHandler extends NotifiableRecipeHandlerTrait<Ingredient> {

            private final InternalSlot slot;
            private final int priority;

            private final int size = 81;
            private final RecipeCapability<Ingredient> capability = ItemRecipeCapability.CAP;
            private final IO handlerIO = IO.IN;
            private final boolean isDistinct = true;

            private SlotItemRecipeHandler(SuperMEPatternBufferPart buffer, InternalSlot slot, int index) {
                super(buffer);
                this.slot = slot;
                this.priority = IFilteredHandler.HIGH + index + 1;
                slot.setOnContentsChanged(this::notifyListeners);
            }



            @Override
            public List<Ingredient> handleRecipeInner(IO io, GTRecipe recipe, List<Ingredient> left, boolean simulate) {
                if (io != IO.IN || slot.isItemEmpty()) return left;
                return (slot.handleItemInternal(left, simulate));
            }

            @Override
            public List<Object> getContents() {
                return new ArrayList<>(slot.getItems());
            }

            @Override
            public double getTotalContentAmount() {
                return slot.getItems().stream().mapToLong(ItemStack::getCount).sum();
            }
        }

    }

    @Getter
    public class InternalSlot implements ITagSerializable<CompoundTag>, IContentChangeAware {

        @Getter
        @Setter
        private Runnable onContentsChanged = () -> {};

        private final Object2LongOpenCustomHashMap<ItemStack> itemInventory = new Object2LongOpenCustomHashMap<>(
                ItemStackHashStrategy.comparingAllButCount());
        private final Object2LongOpenHashMap<FluidStack> fluidInventory = new Object2LongOpenHashMap<>();
        private List<ItemStack> itemStacks = null;
        private List<FluidStack> fluidStacks = null;

        public InternalSlot() {}

        public boolean isItemEmpty() {
            return itemInventory.isEmpty();
        }

        public boolean isFluidEmpty() {
            return fluidInventory.isEmpty();
        }

        public void onContentsChanged() {
            itemStacks = null;
            fluidStacks = null;
            onContentsChanged.run();
        }

        private void add(AEKey what, long amount) {
            if (amount <= 0L) return;
            if (what instanceof AEItemKey itemKey) {
                var stack = itemKey.toStack();
                itemInventory.addTo(stack, amount);
            } else if (what instanceof AEFluidKey fluidKey) {
                var stack = fluidKey.toStack(1);
                fluidInventory.addTo(stack, amount);
            }
        }

        public List<ItemStack> getItems() {
            if (itemStacks == null) {
                itemStacks = new ArrayList<>();
                itemInventory.object2LongEntrySet().stream()
                        .map(e -> GTMath.splitStacks(e.getKey(), e.getLongValue()))
                        .forEach(itemStacks::addAll);
            }
            return itemStacks;
        }

        public List<FluidStack> getFluids() {
            if (fluidStacks == null) {
                fluidStacks = new ArrayList<>();
                fluidInventory.object2LongEntrySet().stream()
                        .map(e -> GTMath.splitFluidStacks(e.getKey(), e.getLongValue()))
                        .forEach(fluidStacks::addAll);
            }
            return fluidStacks;
        }

        public void refund() {
            var network = getMainNode().getGrid();
            if (network != null) {
                MEStorage networkInv = network.getStorageService().getInventory();
                var energy = network.getEnergyService();

                for (var it = itemInventory.object2LongEntrySet().iterator(); it.hasNext();) {
                    var entry = it.next();
                    var stack = entry.getKey();
                    var count = entry.getLongValue();
                    if (stack.isEmpty() || count == 0) {
                        it.remove();
                        continue;
                    }

                    var key = AEItemKey.of(stack);
                    if (key == null) continue;

                    long inserted = StorageHelper.poweredInsert(energy, networkInv, key, count, actionSource);
                    if (inserted > 0) {
                        count -= inserted;
                        if (count == 0) it.remove();
                        else entry.setValue(count);
                    }
                }

                for (var it = fluidInventory.object2LongEntrySet().iterator(); it.hasNext();) {
                    var entry = it.next();
                    var stack = entry.getKey();
                    var amount = entry.getLongValue();
                    if (stack.isEmpty() || amount == 0) {
                        it.remove();
                        continue;
                    }

                    var key = AEFluidKey.of(stack);
                    if (key == null) continue;

                    long inserted = StorageHelper.poweredInsert(energy, networkInv, key, amount, actionSource);
                    if (inserted > 0) {
                        amount -= inserted;
                        if (amount == 0) it.remove();
                        else entry.setValue(amount);
                    }
                }
                onContentsChanged();
            }
        }

        public void pushPattern(IPatternDetails patternDetails, KeyCounter[] inputHolder) {
            patternDetails.pushInputsToExternalInventory(inputHolder, this::add);
            onContentsChanged();
        }

        public @Nullable List<Ingredient> handleItemInternal(List<Ingredient> left, boolean simulate) {
            boolean changed = false;
            for (var it = left.listIterator(); it.hasNext();) {
                var ingredient = it.next();
                if (ingredient.isEmpty()) {
                    it.remove();
                    continue;
                }

                var items = ingredient.getItems();
                if (items.length == 0 || items[0].isEmpty()) {
                    it.remove();
                    continue;
                }

                int amount = items[0].getCount();
                for (var it2 = itemInventory.object2LongEntrySet().iterator(); it2.hasNext();) {
                    var entry = it2.next();
                    var stack = entry.getKey();
                    var count = entry.getLongValue();
                    if (stack.isEmpty() || count == 0) {
                        it2.remove();
                        continue;
                    }
                    if (!ingredient.test(stack)) continue;
                    int extracted = Math.min(GTMath.saturatedCast(count), amount);
                    if (!simulate && extracted > 0) {
                        changed = true;
                        count -= extracted;
                        if (count == 0) it2.remove();
                        else entry.setValue(count);
                    }
                    amount -= extracted;

                    if (amount <= 0) {
                        it.remove();
                        break;
                    }
                }

                if (amount > 0) {
                    if (ingredient instanceof SizedIngredient si) {
                        si.setAmount(amount);
                    } else {
                        items[0].setCount(amount);
                    }
                }
            }
            if (changed) onContentsChanged();
            return left.isEmpty() ? null : left;
        }

        public @Nullable List<FluidIngredient> handleFluidInternal(List<FluidIngredient> left, boolean simulate) {
            boolean changed = false;
            for (var it = left.listIterator(); it.hasNext();) {
                var ingredient = it.next();
                if (ingredient.isEmpty()) {
                    it.remove();
                    continue;
                }

                var fluids = ingredient.getStacks();
                if (fluids.length == 0 || fluids[0].isEmpty()) {
                    it.remove();
                    continue;
                }

                int amount = fluids[0].getAmount();
                for (var it2 = fluidInventory.object2LongEntrySet().iterator(); it2.hasNext();) {
                    var entry = it2.next();
                    var stack = entry.getKey();
                    var count = entry.getLongValue();
                    if (stack.isEmpty() || count == 0) {
                        it2.remove();
                        continue;
                    }
                    if (!ingredient.test(stack)) continue;
                    int extracted = Math.min(GTMath.saturatedCast(count), amount);
                    if (!simulate && extracted > 0) {
                        changed = true;
                        count -= extracted;
                        if (count == 0) it2.remove();
                        else entry.setValue(count);
                    }
                    amount -= extracted;

                    if (amount <= 0) {
                        it.remove();
                        break;
                    }
                }

                if (amount > 0) {
                    ingredient.setAmount(amount);
                }
            }

            if (changed) onContentsChanged();
            return left.isEmpty() ? null : left;
        }

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag tag = new CompoundTag();

            ListTag itemsTag = new ListTag();
            for (var entry : itemInventory.object2LongEntrySet()) {
                var ct = entry.getKey().serializeNBT();
                ct.putLong("real", entry.getLongValue());
                itemsTag.add(ct);
            }
            if (!itemsTag.isEmpty()) tag.put("inventory", itemsTag);

            ListTag fluidsTag = new ListTag();
            for (var entry : fluidInventory.object2LongEntrySet()) {
                var ct = entry.getKey().writeToNBT(new CompoundTag());
                ct.putLong("real", entry.getLongValue());
                fluidsTag.add(ct);
            }
            if (!fluidsTag.isEmpty()) tag.put("fluidInventory", fluidsTag);

            return tag;
        }

        @Override
        public void deserializeNBT(CompoundTag tag) {
            ListTag items = tag.getList("inventory", Tag.TAG_COMPOUND);
            for (Tag t : items) {
                if (!(t instanceof CompoundTag ct)) continue;
                var stack = ItemStack.of(ct);
                var count = ct.getLong("real");
                if (!stack.isEmpty() && count > 0) {
                    itemInventory.put(stack, count);
                }
            }

            ListTag fluids = tag.getList("fluidInventory", Tag.TAG_COMPOUND);
            for (Tag t : fluids) {
                if (!(t instanceof CompoundTag ct)) continue;
                var stack = FluidStack.loadFluidStackFromNBT(ct);
                var amount = ct.getLong("real");
                if (!stack.isEmpty() && amount > 0) {
                    fluidInventory.put(stack, amount);
                }
            }
        }
    }


}
