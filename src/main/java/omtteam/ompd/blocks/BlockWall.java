package omtteam.ompd.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import omtteam.omlib.blocks.BlockAbstractMiscWall;
import omtteam.ompd.OpenModularPassiveDefence;
import omtteam.ompd.init.ModBlocks;
import omtteam.ompd.tileentity.TileEntityPassiveOwnedBlock;
import omtteam.ompd.util.BlockHelper;

import javax.annotation.Nullable;
import java.util.List;

public class BlockWall extends BlockAbstractMiscWall {
    public static final PropertyInteger TIER = PropertyInteger.create("tier", 1, 5);

    public BlockWall() {
        super();
        this.setCreativeTab(OpenModularPassiveDefence.modularPassiveDefenseTab);
        this.setSoundType(SoundType.STONE);
        this.setHarvestLevel("pickaxe", 2);
        this.setDefaultState(this.blockState.getBaseState().withProperty(TIER, 1));
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityPassiveOwnedBlock();
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }


    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TIER, UP, NORTH, EAST, SOUTH, WEST, VARIANT);
    }

    @Override
    @SuppressWarnings("unchecked")
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TIER, meta + 1);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TIER) - 1;
    }


    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        return BlockHelper.onBlockActivated(world,pos,state,player,hand,heldItem,side, hitX,hitY,hitZ);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        BlockHelper.onBlockPlacedBy(worldIn,pos,state,placer,stack,this);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(TIER) - 1;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(ModBlocks.wall, 1, state.getValue(TIER) - 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void getSubBlocks(Item item, CreativeTabs tab, List subItems) {
        for (int i = 0; i < 5; i++) {
            subItems.add(new ItemStack(ModBlocks.wall, 1, i));
        }
    }
}
