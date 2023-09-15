package appeng.util.inv;

import appeng.helpers.NonBlockingItems;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import java.util.Iterator;


public class BlockingItemHandler extends BlockingInventoryAdaptor {
    protected final IItemHandler itemHandler;
    private final String domain;

    public BlockingItemHandler(IItemHandler itemHandler, String domain) {
        this.itemHandler = itemHandler;
        this.domain = domain;
    }

    boolean isBlockableItem(ItemStack stack) {
        Object2ObjectOpenHashMap<Item, IntSet> map = NonBlockingItems.INSTANCE.getMap().get(domain);
        if (map.get(stack.getItem()) != null) {
            return !map.get(stack.getItem()).contains(stack.getMetadata());
        }
        return true;
    }

    @Override
    public boolean containsBlockingItems() {
        int slots = this.itemHandler.getSlots();
        for (int slot = 0; slot < slots; slot++) {
            ItemStack is = this.itemHandler.getStackInSlot(slot);
            if (!is.isEmpty() && isBlockableItem(is)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<ItemSlot> iterator() {
        return new ItemHandlerIterator(this.itemHandler);
    }
}