package lanse505.essence.core.blocks.infuser;

import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.block.BasicTileBlock;
import net.minecraft.block.material.Material;

public class InfuserBlock extends BasicTileBlock<InfuserTile> {

    public InfuserBlock() {
        super(Properties.create(Material.ROCK), InfuserTile.class);
    }

    @Override
    public IFactory<InfuserTile> getTileEntityFactory() {
        return InfuserTile::new;
    }
}
