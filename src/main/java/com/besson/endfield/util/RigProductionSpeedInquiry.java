package com.besson.endfield.util;

import com.besson.endfield.compat.custom.ShreddingRecipeCategory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class RigProductionSpeedInquiry {
    private static final String COAL_ITEM_KEY = "item.minecraft.coal";
    private static final String RAW_COPPER_ITEM_KEY = "item.minecraft.raw_copper";
    private static final String RAW_GOLD_ITEM_KEY = "item.minecraft.raw_gold";
    private static final String RAW_IRON_ITEM_KEY = "item.minecraft.raw_iron";
    private static final String REDSTONE_ITEM_KEY = "item.minecraft.redstone";
    private static final String EMERALD_ITEM_KEY = "item.minecraft.emerald";
    private static final String DIAMOND_ITEM_KEY = "item.minecraft.diamond";
    private static final String LAPIS_LAZULI_ITEM_KEY = "item.minecraft.lapis_lazuli";
    private static final String ANCIENT_DEBRIS_ITEM_KEY = "item.minecraft.ancient_debris";
    private static final String ANCIENT_DEBRIS_BLOCK_KEY = "block.minecraft.ancient_debris";
    private static Map<String ,Integer> process_inquiry_table;
    static {
        process_inquiry_table = new ConcurrentHashMap<String ,Integer>();
        process_inquiry_table.put(COAL_ITEM_KEY ,40);
        process_inquiry_table.put(RAW_COPPER_ITEM_KEY ,40);
        process_inquiry_table.put(RAW_GOLD_ITEM_KEY ,80);
        process_inquiry_table.put(RAW_IRON_ITEM_KEY ,80);
        process_inquiry_table.put(REDSTONE_ITEM_KEY ,40);
        process_inquiry_table.put(EMERALD_ITEM_KEY ,160);
        process_inquiry_table.put(DIAMOND_ITEM_KEY ,160);
        process_inquiry_table.put(LAPIS_LAZULI_ITEM_KEY ,40);
        process_inquiry_table.put(ANCIENT_DEBRIS_ITEM_KEY,600);
        process_inquiry_table.put(ANCIENT_DEBRIS_BLOCK_KEY,600);
    }
    public static void  registerNewItem(String id ,Integer maxprocess){
        process_inquiry_table.put(id ,maxprocess);
    }
    public static int inquiryMaxProcess_normal(String id) {
        Optional<Integer> result = Optional.ofNullable(process_inquiry_table.get(id));
        if(result.isPresent()){
            return result.get();
        }else {
            return 40;
        }
    }
    public static int inquiryMaxProcess_highspeed(String id) {
        if (ANCIENT_DEBRIS_ITEM_KEY.equals(id) || ANCIENT_DEBRIS_BLOCK_KEY.equals(id)) {
            return inquiryMaxProcess_normal(id);
        }
        return Math.max(inquiryMaxProcess_normal(id) - 20 ,0);
    }
}
