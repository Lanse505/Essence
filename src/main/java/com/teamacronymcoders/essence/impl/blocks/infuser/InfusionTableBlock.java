package lanse505.essence.impl.blocks.infuser;

import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.block.BasicTileBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class InfusionTableBlock extends BasicTileBlock<InfusionTableTile> {

    public InfusionTableBlock() {
        super(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(3.5F), InfusionTableTile.class);
    }

    @Override
    public IFactory<InfusionTableTile> getTileEntityFactory() {
        return InfusionTableTile::new;
    }
}
