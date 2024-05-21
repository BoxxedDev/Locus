package boxxed.locus.content.blockEntities;

import foundry.veil.api.client.color.ColorTheme;
import foundry.veil.api.client.tooltip.Tooltippable;
import foundry.veil.api.client.tooltip.VeilUIItemTooltipDataHolder;
import foundry.veil.api.client.tooltip.anim.TooltipTimeline;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class TestBE extends BlockEntity implements Tooltippable {
    public TestBE(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    @Override
    public List<Component> getTooltip() {
        return null;
    }

    @Override
    public boolean isTooltipEnabled() {
        return true;
    }

    @Override
    public CompoundTag saveTooltipData() {
        return null;
    }

    @Override
    public void loadTooltipData(CompoundTag tag) {

    }

    @Override
    public void setTooltip(List<Component> tooltip) {
        tooltip.add(Component.literal("wow"));
    }

    @Override
    public void addTooltip(Component tooltip) {

    }

    @Override
    public void addTooltip(List<Component> tooltip) {

    }

    @Override
    public void addTooltip(String tooltip) {

    }

    @Override
    public ColorTheme getTheme() {
        return null;
    }

    @Override
    public void setTheme(ColorTheme theme) {

    }

    @Override
    public void setBackgroundColor(int color) {

    }

    @Override
    public void setTopBorderColor(int color) {

    }

    @Override
    public void setBottomBorderColor(int color) {

    }

    @Override
    public boolean getWorldspace() {
        return true;
    }

    @Override
    public TooltipTimeline getTimeline() {
        return null;
    }

    @Override
    public ItemStack getStack() {
        return null;
    }

    @Override
    public int getTooltipWidth() {
        return 10;
    }

    @Override
    public int getTooltipHeight() {
        return 100;
    }

    @Override
    public int getTooltipXOffset() {
        return 0;
    }

    @Override
    public int getTooltipYOffset() {
        return 0;
    }

    @Override
    public List<VeilUIItemTooltipDataHolder> getItems() {
        return null;
    }
}
