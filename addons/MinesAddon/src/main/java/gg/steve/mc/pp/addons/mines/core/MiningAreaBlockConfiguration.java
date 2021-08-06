package gg.steve.mc.pp.addons.mines.core;

import gg.steve.mc.pp.addons.mines.box.MiningAreaBoundingBox;
import gg.steve.mc.pp.utility.Log;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MiningAreaBlockConfiguration {
    private Map<ItemStack, Double> materialAndSpawnRate;
    private List<ItemStack> itemStackList;
    private Map<ItemStack, Integer> blocksToPlacePerMaterial;
    private Random random;

    public MiningAreaBlockConfiguration() {
        this.materialAndSpawnRate = new HashMap<>();
        this.random = new Random();
    }

    public void convertContainerToItemsMap(Inventory inventory) {
        Map<ItemStack, Double> itemAndAmount = new HashMap<>();
        double total = 0;
        for (int slot = 0; slot < inventory.getSize(); slot++) {
            ItemStack item = inventory.getItem(slot);
            if (item == null || item.getType() == Material.AIR) continue;
            int current = 0;
            if (itemAndAmount.containsKey(item)) current += itemAndAmount.get(item);
            itemAndAmount.put(item, (double) (item.getAmount() + current));
            total += item.getAmount();
        }
        if (total != 0) for (Map.Entry<ItemStack, Double> itemSet : itemAndAmount.entrySet()) {
            this.registerMiningAreaBlock(itemSet.getKey(), itemSet.getValue() / total);
        }
    }

    public boolean registerMiningAreaBlock(ItemStack item, double spawnRate) {
        if (this.materialAndSpawnRate == null || this.materialAndSpawnRate.containsKey(item)) return false;
        return this.materialAndSpawnRate.put(item, spawnRate) != null;
    }

    public boolean unregisterMiningAreaBlock(ItemStack item) {
        if (this.materialAndSpawnRate == null || this.materialAndSpawnRate.isEmpty() || !this.materialAndSpawnRate.containsKey(item))
            return false;
        return this.materialAndSpawnRate.remove(item) != null;
    }

    public boolean fillMiningArea(MiningAreaBoundingBox miningArea) {
        // TODO randomize generation
        if (!miningArea.isLoaded() || this.materialAndSpawnRate.size() == 0) return false;
        double totalBlocks = miningArea.getCoordinates().size();
        this.itemStackList = new ArrayList<>(this.materialAndSpawnRate.keySet());
        this.blocksToPlacePerMaterial = new HashMap<>();
        for (Map.Entry<ItemStack, Double> itemSet : this.materialAndSpawnRate.entrySet()) {
            blocksToPlacePerMaterial.put(itemSet.getKey(), (int) Math.ceil(itemSet.getValue() * totalBlocks));
        }
        for (Location location : miningArea.getCoordinates().values()) {
//            boolean skip = false;
//            for (ItemStack itemStack : this.itemStackList) {
//                if (location.getBlock().getType() == itemStack.getType() && location.getBlock().getState().getRawData() == (byte) itemStack.getDurability()) {
//                    skip = true;
//                    break;
//                }
//            }
//            if (skip) continue;
            ItemStack randomItemStack = this.getRandomItemStack();
            location.getBlock().setType(randomItemStack.getType());
            location.getBlock().getState().setRawData((byte) randomItemStack.getDurability());
        }
        return true;
    }

    private ItemStack getRandomItemStack() {
        ItemStack randomItemStack = this.itemStackList.size() > 1 ? this.itemStackList.get(0) : this.itemStackList.get(random.nextInt(itemStackList.size()));
        if (!this.blocksToPlacePerMaterial.containsKey(randomItemStack)) {
            this.itemStackList.remove(randomItemStack);
            return this.getRandomItemStack();
        } else {
            if (this.blocksToPlacePerMaterial.get(randomItemStack) == 1) {
                this.blocksToPlacePerMaterial.remove(randomItemStack);
                this.itemStackList.remove(randomItemStack);
            } else {
                this.blocksToPlacePerMaterial.put(randomItemStack, this.blocksToPlacePerMaterial.get(randomItemStack) - 1);
            }
            return randomItemStack;
        }
    }
}
