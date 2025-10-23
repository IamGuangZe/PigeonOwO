package owo.pigeon.features.modules.hypixel.Skyblock.Dungeon.F7;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.utils.hypixel.skyblock.Floor;
import owo.pigeon.utils.hypixel.skyblock.Island;
import owo.pigeon.utils.hypixel.skyblock.SkyblockUtil;

import static owo.pigeon.utils.WorldUtil.*;

public class SetClipBlock extends Module {
    public SetClipBlock() {
        super("SetClipBlock", Category.HYPIXEL, -1);
    }

    private final Block air = Blocks.air;
    private final Block rail = Blocks.rail;
    private final Block glass = Blocks.glass;
    private final Block stone_slab = Blocks.stone_slab;
    private final Block ender_chest = Blocks.ender_chest;
    private final Block oak_fence_gate = Blocks.oak_fence_gate;

    @Override
    public void onTick() {
        if (isNotNull()) {
            if (SkyblockUtil.isFloor(Floor.F7) || SkyblockUtil.isFloor(Floor.M7)) {
                if (SkyblockUtil.isInBoss(7) || SkyblockUtil.isIsland(Island.SinglePlayer)) {
                    switch (SkyblockUtil.getF7Stage()) {
                        case 1:
                            fillBlock(new BlockPos(77, 220, 35),
                                    new BlockPos(78, 220, 36), air);
                            fillBlock(new BlockPos(77, 221, 33),
                                    new BlockPos(77, 221, 36), ender_chest);
                            fillBlock(new BlockPos(67, 221, 51),
                                    new BlockPos(67, 237, 51), glass);
                            fillBlock(new BlockPos(79, 221, 51),
                                    new BlockPos(79, 237, 51), glass);
                            setBlock(new BlockPos(78, 221, 36), glass);
                            break;
                        case 2:
                            fillBlock(new BlockPos(99, 168, 46),
                                    new BlockPos(101, 165, 47), air);
                            fillBlock(new BlockPos(99, 167, 45),
                                    new BlockPos(101, 167, 46), air);
                            fillBlock(new BlockPos(99, 169, 46),
                                    new BlockPos(101, 169, 46), ender_chest);
                            break;
                        case 3:
                            fillBlock(new BlockPos(89, 131, 44),
                                    new BlockPos(91, 133, 45), air);
                            fillBlock(new BlockPos(96, 120, 122),
                                    new BlockPos(97, 122, 123), air);
                            fillBlock(new BlockPos(17, 120, 128),
                                    new BlockPos(18, 122, 129), air);
                            fillBlock(new BlockPos(11, 120, 49),
                                    new BlockPos(12, 122, 50), air);
                            fillBlock(new BlockPos(54, 110, 110),
                                    new BlockPos(54, 113, 111), air);
                            fillBlock(new BlockPos(93, 115, 44),
                                    new BlockPos(93, 133, 44), glass);
                            fillBlock(new BlockPos(67, 106, 131),
                                    new BlockPos(71, 106, 135), rail);
                            fillBlock(new BlockPos(20, 106, 135),
                                    new BlockPos(24, 106, 139), rail);
                            fillBlock(new BlockPos(42, 106, 36),
                                    new BlockPos(48, 106, 38), rail);
                            fillBlock(new BlockPos(91, 131, 44),
                                    new BlockPos(91, 133, 44), oak_fence_gate);
                            setBlock(new BlockPos(54, 114, 111), ender_chest);
                            break;
                        case 4:
                            fillBlock(new BlockPos(58, 61, 76),
                                    new BlockPos(59, 64, 76), air);
                            fillBlock(new BlockPos(54, 63, 73),
                                    new BlockPos(54, 64, 74), air);
                            setBlock(new BlockPos(59, 65, 76), ender_chest);
                            setBlock(new BlockPos(54, 64, 72), stone_slab);
                            setBlock(new BlockPos(54, 66, 74), stone_slab);
                    }
                }
            }
        }
    }
}
