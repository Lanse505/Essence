package com.teamacronymcoders.essence.api.tool.legacy;

import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

public interface IModifiedTool {
    ActionResultType onItemUseModified(ItemUseContext context, boolean isRecursive);
}
