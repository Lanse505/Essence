package com.teamacronymcoders.essence.api.modifier;

import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

public interface IModifiedTool {
    public ActionResultType onItemUseModified(ItemUseContext context, boolean isRecursive);
}