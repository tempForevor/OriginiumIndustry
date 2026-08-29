package com.apcp.originium_industry.commmon.machine.single.uiholder;

import com.apcp.originium_industry.api.tectree.OITecInfo;
import com.apcp.originium_industry.api.tectree.TecTree;
import com.apcp.originium_industry.api.tectree.TecTreeContainer;
import com.apcp.originium_industry.api.widget.TecInfoValueHolderWidget;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.texture.TextTexture;
import com.lowdragmc.lowdraglib.gui.util.ClickData;
import com.lowdragmc.lowdraglib.gui.widget.*;
import com.lowdragmc.lowdraglib.gui.widget.layout.Align;
import com.lowdragmc.lowdraglib.utils.LocalizationUtils;
import com.lowdragmc.lowdraglib.utils.Position;
import com.lowdragmc.lowdraglib.utils.Size;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@SuppressWarnings({"unused", "DataFlowIssue"})
public class TecTreeHolder extends OIUIHolderMachine implements SearchComponentWidget.IWidgetSearch<OITecInfo>{
    public String search_content = "";
    public int selectedId = 0;
    public int latestId = -1;
    public Component latestComponent = Component.empty();
    public boolean selectLock = false;

    public TecTreeContainer tecTreeContainer;
    public WeakReference<TecTree> tecTreeRef;

    public SearchComponentWidget<OITecInfo> searchBar;

    public DraggableScrollableWidgetGroup resultList;
    public Map<Integer,SelectableWidgetGroup> section_conv;

    public TextTextureWidget informationDisplayer;

    public TecTreeHolder(IMachineBlockEntity holder,TecTree tecTree){
        super(holder,ui->ui);
        tecTreeRef = new WeakReference<>(tecTree);
    }

    public void updateSearchContent(String search_content){
        this.search_content = search_content;
    }
    public void onSelectTecInfoItem(SelectableWidgetGroup section){
        if(selectLock)return;
        selectLock = true;
        for(var widget : section.widgets){
            if(widget instanceof TecInfoValueHolderWidget tecInfoValueHolderWidget){
                selectedId = tecInfoValueHolderWidget.getTecInfoId();
//                if(selectedId == latestId){
//                    selectLock = false;
//                    return;
//                }
                informationDisplayer.setText(getNewInformation());
                informationDisplayer.updateScreen();
                informationDisplayer.getTextTexture().updateTick();
                informationDisplayer.setSizeHeight(informationDisplayer.getTextTexture().getLines()*30);
//                informationDisplayer.setSelfPosition(0,0);
                selectLock = false;
                return;
            }
        }
        selectLock = false;
        throw new RuntimeException("[OITecTree] Unknown Selection!Is the ui modified by other codes?");
    }
    public void onSelectTecInfoBtn(ClickData data,SelectableWidgetGroup section){
        onSelectTecInfoItem(section);
    }

    public Component getSelectInfoComp(){
        if (tecTreeRef.get() == null){
            throw new RuntimeException("[OITecTree] No information available!");
        }
        OITecInfo res = tecTreeRef.get().tecInfoItems.get(selectedId);
        if(res == null){return Component.empty();}
        return Component.translatable(res.generateLocalizeInfoId());
    }

    public Component getNewInformation(){
        if(selectedId!=latestId){
            latestId = selectedId;
            latestComponent = getSelectInfoComp();
        }
        return latestComponent;
    }

    public SelectableWidgetGroup createSelectableInfo(DraggableScrollableWidgetGroup parent,int sepX,int sepY,int sizeX,int sizeY){
        var maxy = 0;
        for(var sec : parent.widgets){
            maxy = Math.max(maxy,sec.getSelfPositionY() + sizeY);
        }
        maxy += sepY;
        return new SelectableWidgetGroup(sepX,maxy,sizeX,sizeY);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public ModularUI createUI(Player player) {
        ModularUI ui = new ModularUI(this,player);
        ui.setFullScreen();
        ui.background(GuiTextures.CLIPBOARD_PAPER_BACKGROUND);

        searchBar = new SearchComponentWidget<>(10,10,150,30,this);
        ui.widget(searchBar);

        resultList = new DraggableScrollableWidgetGroup(10,50,150,350);
        resultList.setScrollable(true);
        resultList.setDraggable(false);
        resultList.setScrollWheelDirection(DraggableScrollableWidgetGroup.ScrollWheelDirection.VERTICAL);
        ui.widget(resultList);

        informationDisplayer = new TextTextureWidget(0,0,350,350,"");
        informationDisplayer.textureStyle(
                textTexture -> textTexture.setType(TextTexture.TextType.LEFT)
        );

        var info_scroll_group = new WidgetGroup(170,10,350,350){
            @Override
            public boolean mouseWheelMove(double mouseX, double mouseY, double wheelDelta) {
                if (this.isMouseOverElement(mouseX, mouseY)) {
                    if (super.mouseWheelMove(mouseX, mouseY, wheelDelta)) {
                        this.setFocus(true);
                        return true;
                    } else {
                        this.setFocus(true);
                        if (this.isFocus()) {
                            int moveDelta = (int)(-Mth.clamp(wheelDelta, -1.0F, 1.0F) * (double)13.0F);
                            for(var widget : this.widgets){
                                widget.addSelfPosition(0,moveDelta);
                            }
                        }
                        return true;
                    }
                } else {
                    this.setFocus(false);
                    return false;
                }
            }
        };
//        info_scroll_group.setDraggable(false);
//        info_scroll_group.setScrollable(true);
//        info_scroll_group.setScrollWheelDirection(DraggableScrollableWidgetGroup.ScrollWheelDirection.VERTICAL);
        ui.widget(info_scroll_group);
        info_scroll_group.addWidget(informationDisplayer);

        section_conv = new HashMap<>();

        if(tecTreeRef.get() == null){
            throw new RuntimeException("[OITecTree] Uninitialized TecTreeHolder!");
        }
        if (tecTreeRef.get() != null) {
            tecTreeRef.get().tecInfoItems.values().forEach(tecInfoItem -> {
                var section = createSelectableInfo(resultList, 0, 10, 130, 30);
                var btn_tex = new TextTexture(Component.translatable(tecInfoItem.generateLocalizeNameId()).getString());
                btn_tex.setType(TextTexture.TextType.LEFT);
                var btn = new ButtonWidget(0, 0,130,30,btn_tex,(data)->onSelectTecInfoBtn(data,section));
                var holder = new TecInfoValueHolderWidget(Position.ORIGIN, Size.ZERO, tecInfoItem.id);
                btn.setAlign(Align.CENTER);
                section.addWidget(btn);
                section.addWidget(holder);
//                section.setOnSelected(this::onSelectTecInfoItem);
                resultList.addWidget(section);
                section_conv.put(tecInfoItem.id, section);
            });
        }
        return ui;
    }

    @Override
    public String resultDisplay(OITecInfo value) {
        return LocalizationUtils.format(value.generateLocalizeNameId()) + "(" + value.generateLocalizeNameId() + ")";
    }

    @Override
    public void selectResult(OITecInfo value) {
        if(section_conv.containsKey(value.id)){
            onSelectTecInfoItem(section_conv.get(value.id));
        }
    }

    @Override
    public void search(String word, Consumer<OITecInfo> find) {
        var tecTree = tecTreeRef.get();
        for(var id : section_conv.keySet()){
            var item = tecTree.get(id);
            if(item.generateLocalizeNameId().contains(word) || LocalizationUtils.format(item.generateLocalizeNameId()).contains(word)){
                find.accept(item);
            }
        }
    }
}
