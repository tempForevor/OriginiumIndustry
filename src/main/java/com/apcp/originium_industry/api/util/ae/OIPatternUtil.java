package com.apcp.originium_industry.api.util.ae;

import appeng.api.crafting.IPatternDetails;
import appeng.api.crafting.PatternDetailsHelper;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.GenericStack;
import appeng.crafting.pattern.AEProcessingPattern;
import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.commmon.item.VirtualItemBehavior;
import com.gregtechceu.gtceu.common.item.IntCircuitBehaviour;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class OIPatternUtil {
    public static List<AEItemKey> getPatternSpecificalItems(IPatternDetails pattern, Function<AEItemKey, Boolean> tester){
        if(pattern == null){return new ArrayList<>(0);}
        List<AEItemKey> res = new ArrayList<>(pattern.getInputs().length);
        if(!(pattern instanceof AEProcessingPattern))return List.of();
        for(var input : pattern.getInputs()){
            var primaryInput = input.getPossibleInputs()[0];
            if(primaryInput.what() instanceof AEItemKey itemKey){
                if(tester.apply(itemKey)){
                    res.add(itemKey);
                }
            }
        }
        return res;
    }

    public static IPatternDetails getPatternWithoutSpecificalItem(IPatternDetails pattern, Level level, Function<AEItemKey, Boolean> tester) {
        if(pattern == null) return null;
        if(!(pattern instanceof AEProcessingPattern))return pattern;
        List<GenericStack> resArr = new ArrayList<>(pattern.getInputs().length);
        for(var input : pattern.getInputs()){
            var primaryInput = input.getPossibleInputs()[0];
            if(primaryInput.what() instanceof AEItemKey itemKey){
                if(tester.apply(itemKey)){
                    continue;
                }
            }
            resArr.add(primaryInput);
        }
        try {
            var patternStack = PatternDetailsHelper.encodeProcessingPattern(resArr.toArray(new GenericStack[0]), pattern.getOutputs());
            return PatternDetailsHelper.decodePattern(patternStack,level);
        }catch (Exception e){
            OIMod.LOGGER.error("An error occurs when removing specifical items from a pattern : \n {}",e.getMessage());
            return pattern;
        }
    }

    public static IPatternDetails getPatternWithoutVirtualItems(IPatternDetails pattern, Level level) {
        return getPatternWithoutSpecificalItem(pattern,level,(key)->VirtualItemBehavior.isVirtualItem(key.toStack()));
    }
    public static IPatternDetails getPatternWithoutCircuit(IPatternDetails pattern, Level level) {
        return getPatternWithoutSpecificalItem(pattern,level,(key)->IntCircuitBehaviour.isIntegratedCircuit(key.toStack()));
    }
    public static List<AEItemKey> getPatternVirtualItems(IPatternDetails pattern){
        return getPatternSpecificalItems(pattern,(key)->VirtualItemBehavior.isVirtualItem(key.toStack()));
    }
    public static @Nullable AEItemKey getPatternCircuit(IPatternDetails pattern){
        var res = getPatternSpecificalItems(pattern,(key)->IntCircuitBehaviour.isIntegratedCircuit(key.toStack()));
        if(res.isEmpty())return null;
        return res.get(0);
    }
}
