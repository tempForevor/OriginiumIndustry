package com.apcp.originium_industry.api.widget;

import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.utils.Position;
import com.lowdragmc.lowdraglib.utils.Size;
import lombok.Getter;

@Getter
public class TecInfoValueHolderWidget extends Widget {
    public int tecInfoId;

    public TecInfoValueHolderWidget(Position pos, Size size, int tecInfoId) {
        super(pos,size);
        this.tecInfoId = tecInfoId;
    }
}
