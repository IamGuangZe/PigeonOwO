package owo.pigeon.features.modules.hypixel.Skyblock.Dungeon.F7;

import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockPos;
import owo.pigeon.features.Category;
import owo.pigeon.features.Module;
import owo.pigeon.utils.hypixel.skyblock.Floor;
import owo.pigeon.utils.hypixel.skyblock.Island;
import owo.pigeon.utils.hypixel.skyblock.SkyblockUtil;

import java.util.HashSet;
import java.util.Set;

import static owo.pigeon.utils.WorldUtil.isNotNull;
import static owo.pigeon.utils.WorldUtil.setBlock;

public class PillarsHelper extends Module {
    public PillarsHelper() {
        super("PillarsHelper", Category.HYPIXEL, -1);

        for (int i = 0; i < 4; i++){
            BlockPos pillar = pillars[i];
            coordinates[i] = new HashSet<>();
            for (int dx = pillar.getX() - 3; dx <= pillar.getX() + 3; dx++) {
                for (int dy = pillar.getY(); dy <= pillar.getY() + 37; dy++) {
                    for (int dz = pillar.getZ() - 3; dz <= pillar.getZ() + 3; dz++) {
                        coordinates[i].add(new BlockPos(dx, dy, dz));
                    }
                }
            }
        }
    }
    private final Set<BlockPos>[] coordinates = new Set[4];

    private final BlockPos[] pillars = {
            new BlockPos(46, 169, 41), //green
            new BlockPos(46, 169, 65), //yellow
            new BlockPos(100, 169, 65), //purple
            new BlockPos(100, 169, 41) //red
    };
    private final int[] pillarscolor = {5,4,10,14};

    @Override
    public void onUpdate() {
        if (isNotNull()) {
            if (SkyblockUtil.isInBoss(Floor.F7) || SkyblockUtil.isInBoss(Floor.M7) || SkyblockUtil.isIsland(Island.SinglePlayer)) {
                if (SkyblockUtil.getF7Stage() == 2) {
                    replaceDiorite();
                }
            }
        }
    }

    private void replaceDiorite() {
        for (Set<BlockPos> coordinate :coordinates) {
            for (BlockPos pos : coordinate) {
                if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockStone) {
                    replaceglass(pos, coordinate);
                }
            }
        }
    }

    private void replaceglass(BlockPos pos, Set<BlockPos> coordinate) {
        IBlockState state = Blocks.stained_glass.getDefaultState()
                .withProperty(Blocks.stained_glass.COLOR, EnumDyeColor.byMetadata(pillarscolor[getPillarIndex(coordinate)]));
        setBlock(pos, state);
    }

    private int getPillarIndex(Set<BlockPos> coordinate) {
        for (int i = 0; i < coordinates.length; i++) {
            if (coordinates[i].equals(coordinate)) {
                return i; // 返回对应的石柱索引
            }
        }
        return -1; // 如果找不到，返回 -1
    }

}
