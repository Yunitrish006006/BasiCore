package com.mc.basicore;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class itemGroups {
    public static List<Material> shovels() {
        List<Material> shovels = new ArrayList<>();
        shovels.add(Material.WOODEN_SHOVEL);
        shovels.add(Material.STONE_SHOVEL);
        shovels.add(Material.IRON_SHOVEL);
        shovels.add(Material.GOLDEN_SHOVEL);
        shovels.add(Material.DIAMOND_SHOVEL);
        shovels.add(Material.NETHERITE_SHOVEL);
        return shovels;
    }
    public static List<Material> axes() {
        List<Material> shovels = new ArrayList<>();
        shovels.add(Material.WOODEN_AXE);
        shovels.add(Material.STONE_AXE);
        shovels.add(Material.IRON_AXE);
        shovels.add(Material.GOLDEN_AXE);
        shovels.add(Material.DIAMOND_AXE);
        shovels.add(Material.NETHERITE_AXE);
        return shovels;
    }
    public static ItemStack[] hammers() {
        return new ItemStack[]{
                wooden_hammer(),
                stone_hammer(),
                iron_hammer(),
                golden_hammer(),
                diamond_hammer(),
                netherite_hammer()
        };
    }
    public static ItemStack[] fishes() {
        return new ItemStack[]{
                new ItemStack(Material.TROPICAL_FISH),
                new ItemStack(Material.PUFFERFISH),
                new ItemStack(Material.COD),
                new ItemStack(Material.SALMON)
        };
    }
    public static RecipeChoice stones() {
        return new RecipeChoice.MaterialChoice(Arrays.asList(
                Material.COBBLESTONE,
                Material.GRANITE,
                Material.DIORITE,
                Material.ANDESITE,
                Material.DEEPSLATE,
                Material.MOSSY_COBBLESTONE,
                Material.END_STONE
        ));
    }
    public static RecipeChoice sticks() {
        return new RecipeChoice.ExactChoice(Arrays.asList(
                oak_stick(),
                acacia_stick(),
                dark_oak_stick(),
                crimson_stick(),
                warp_stick(),
                jungle_stick(),
                spruce_stick(),
                birch_stick(),
                new ItemStack(Material.STICK)
        ));
    }
    public static ItemStack oak_stick() {
        ItemStack oak_stick = new ItemStack(Material.STICK);
        ItemMeta oak_stick_meta = oak_stick.getItemMeta();
        Objects.requireNonNull(oak_stick_meta).setCustomModelData(1);
        oak_stick_meta.setLocalizedName("item.minecraft.oak_stick");
        oak_stick_meta.setDisplayName(ChatColor.RESET + "橡木棒");
        oak_stick.setItemMeta(oak_stick_meta);
        return oak_stick;
    }
    public static ItemStack spruce_stick() {
        ItemStack spruce_stick = new ItemStack(Material.STICK);
        ItemMeta spruce_stick_meta = spruce_stick.getItemMeta();
        Objects.requireNonNull(spruce_stick_meta).setCustomModelData(1);
        spruce_stick_meta.setLocalizedName("item.minecraft.spruce_stick");
        spruce_stick_meta.setDisplayName(ChatColor.RESET + "杉木棒");
        spruce_stick.setItemMeta(spruce_stick_meta);
        return spruce_stick;
    }
    public static ItemStack birch_stick() {
        ItemStack birch_stick = new ItemStack(Material.STICK);
        ItemMeta birch_stick_meta = birch_stick.getItemMeta();
        Objects.requireNonNull(birch_stick_meta).setCustomModelData(1);
        birch_stick_meta.setLocalizedName("item.minecraft.birch_stick");
        birch_stick_meta.setDisplayName(ChatColor.RESET + "樺木棒");
        birch_stick.setItemMeta(birch_stick_meta);
        return birch_stick;
    }
    public static ItemStack jungle_stick() {
        ItemStack jungle_stick = new ItemStack(Material.STICK);
        ItemMeta jungle_stick_meta = jungle_stick.getItemMeta();
        Objects.requireNonNull(jungle_stick_meta).setCustomModelData(1);
        jungle_stick_meta.setLocalizedName("item.minecraft.jungle_stick");
        jungle_stick_meta.setDisplayName(ChatColor.RESET + "叢林木棒");
        jungle_stick.setItemMeta(jungle_stick_meta);
        return jungle_stick;
    }
    public static ItemStack acacia_stick() {
        ItemStack acacia_stick = new ItemStack(Material.STICK);
        ItemMeta acacia_stick_meta = acacia_stick.getItemMeta();
        Objects.requireNonNull(acacia_stick_meta).setCustomModelData(1);
        acacia_stick_meta.setLocalizedName("item.minecraft.acacia_stick");
        acacia_stick_meta.setDisplayName(ChatColor.RESET + "相思木棒");
        acacia_stick.setItemMeta(acacia_stick_meta);
        return acacia_stick;
    }
    public static ItemStack dark_oak_stick() {
        ItemStack dark_oak_stick = new ItemStack(Material.STICK);
        ItemMeta dark_oak_stick_meta = dark_oak_stick.getItemMeta();
        Objects.requireNonNull(dark_oak_stick_meta).setCustomModelData(1);
        dark_oak_stick_meta.setLocalizedName("item.minecraft.dark_oak_stick");
        dark_oak_stick_meta.setDisplayName(ChatColor.RESET + "黑橡木棒");
        dark_oak_stick.setItemMeta(dark_oak_stick_meta);
        return dark_oak_stick;
    }
    public static ItemStack crimson_stick() {
        ItemStack crimson_stick = new ItemStack(Material.STICK);
        ItemMeta crimson_stick_meta = crimson_stick.getItemMeta();
        Objects.requireNonNull(crimson_stick_meta).setCustomModelData(1);
        crimson_stick_meta.setLocalizedName("item.minecraft.crimson_stick");
        crimson_stick_meta.setDisplayName(ChatColor.RESET + "緋紅蕈棒");
        crimson_stick.setItemMeta(crimson_stick_meta);
        return crimson_stick;
    }
    public static ItemStack warp_stick() {
        ItemStack warp_stick = new ItemStack(Material.STICK);
        ItemMeta dark_oak_stick_meta = warp_stick.getItemMeta();
        Objects.requireNonNull(dark_oak_stick_meta).setCustomModelData(1);
        dark_oak_stick_meta.setLocalizedName("item.minecraft.warp_stick");
        dark_oak_stick_meta.setDisplayName(ChatColor.RESET + "扭曲蕈棒");
        warp_stick.setItemMeta(dark_oak_stick_meta);
        return warp_stick;
    }
    public static ItemStack apple_pie() {
        ItemStack apple_pie = new ItemStack(Material.PUMPKIN_PIE);
        ItemMeta apple_pie_meta = apple_pie.getItemMeta();
        Objects.requireNonNull(apple_pie_meta).setCustomModelData(1);
        apple_pie_meta.setLocalizedName("item.minecraft.apple_pie");
        apple_pie_meta.setDisplayName(ChatColor.RESET + "蘋果派");
        apple_pie.setItemMeta(apple_pie_meta);
        return apple_pie;
    }
    public static ItemStack cocoa() {
        ItemStack cocoa = new ItemStack(Material.POTION);
        PotionMeta cocoa_meta = (PotionMeta) cocoa.getItemMeta();
        PotionEffect cocoa_effect_1 = new PotionEffect(PotionEffectType.REGENERATION,(int)(Math.random()*480),0,true,true,false);
        PotionEffect cocoa_effect_2 = new PotionEffect(PotionEffectType.SATURATION,(int)(Math.random()*480),0,true,true,false);
        Objects.requireNonNull(cocoa_meta).addCustomEffect(cocoa_effect_1,true);
        Objects.requireNonNull(cocoa_meta).addCustomEffect(cocoa_effect_2,true);
        cocoa_meta.setLocalizedName("item.minecraft.cocoa");
        cocoa_meta.setColor(Color.MAROON);
        cocoa_meta.setDisplayName(ChatColor.RESET + "熱可可");
        cocoa.setItemMeta(cocoa_meta);
        return cocoa;
    }
    public static ItemStack wooden_hammer() {
        ItemStack wooden_hammer = new ItemStack(Material.WOODEN_PICKAXE);
        ItemMeta wooden_hammer_meta = wooden_hammer.getItemMeta();
        Objects.requireNonNull(wooden_hammer_meta).setCustomModelData(1);
        wooden_hammer_meta.setLocalizedName("item.minecraft.wooden_hammer");
        wooden_hammer_meta.setLocalizedName(ChatColor.RESET + "木錘");
        wooden_hammer.setItemMeta(wooden_hammer_meta);
        return wooden_hammer;
    }
    public static ItemStack stone_hammer() {
        ItemStack stone_hammer = new ItemStack(Material.STONE_PICKAXE);
        ItemMeta stone_hammer_meta = stone_hammer.getItemMeta();
        Objects.requireNonNull(stone_hammer_meta).setCustomModelData(1);
        stone_hammer_meta.setLocalizedName("item.minecraft.stone_hammer");
        stone_hammer_meta.setLocalizedName(ChatColor.RESET + "石錘");
        stone_hammer.setItemMeta(stone_hammer_meta);
        return stone_hammer;
    }
    public static ItemStack iron_hammer() {
        ItemStack iron_hammer = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta iron_hammer_meta = iron_hammer.getItemMeta();
        Objects.requireNonNull(iron_hammer_meta).setCustomModelData(1);
        iron_hammer_meta.setLocalizedName("item.minecraft.iron_hammer");
        iron_hammer_meta.setLocalizedName(ChatColor.RESET + "鐵錘");
        iron_hammer.setItemMeta(iron_hammer_meta);
        return iron_hammer;
    }
    public static ItemStack golden_hammer() {
        ItemStack golden_hammer = new ItemStack(Material.GOLDEN_PICKAXE);
        ItemMeta golden_hammer_meta = golden_hammer.getItemMeta();
        Objects.requireNonNull(golden_hammer_meta).setCustomModelData(1);
        golden_hammer_meta.setLocalizedName("item.minecraft.golden_hammer");
        golden_hammer_meta.setLocalizedName(ChatColor.RESET + "金錘");
        golden_hammer.setItemMeta(golden_hammer_meta);
        return golden_hammer;
    }
    public static ItemStack diamond_hammer() {
        ItemStack diamond_hammer = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta diamond_hammer_meta = diamond_hammer.getItemMeta();
        Objects.requireNonNull(diamond_hammer_meta).setCustomModelData(1);
        diamond_hammer_meta.setLocalizedName("item.minecraft.diamond_hammer");
        diamond_hammer_meta.setDisplayName(ChatColor.RESET + "鑽石錘");
        diamond_hammer.setItemMeta(diamond_hammer_meta);
        return diamond_hammer;
    }
    public static ItemStack netherite_hammer() {
        ItemStack netherite_hammer = new ItemStack(Material.NETHERITE_PICKAXE);
        ItemMeta netherite_hammer_meta = netherite_hammer.getItemMeta();
        Objects.requireNonNull(netherite_hammer_meta).setCustomModelData(1);
        netherite_hammer_meta.setLocalizedName("item.minecraft.netherite_hammer");
        netherite_hammer_meta.setDisplayName(ChatColor.RESET + "獄髓錘");
        netherite_hammer.setItemMeta(netherite_hammer_meta);
        return netherite_hammer;
    }
    public static ItemStack enchanted_iron_ingot() {
        ItemStack enchanted_iron_ingot = new ItemStack(Material.IRON_INGOT);
        ItemMeta enchanted_iron_ingot_meta = enchanted_iron_ingot.getItemMeta();
        Objects.requireNonNull(enchanted_iron_ingot_meta).setCustomModelData(1);
        enchanted_iron_ingot_meta.setLocalizedName("item.minecraft.enchanted_iron_ingot");
        enchanted_iron_ingot.setItemMeta(enchanted_iron_ingot_meta);
        return enchanted_iron_ingot;
    }
    public static ItemStack chipped_emerald() {
        ItemStack chipped_emerald = new ItemStack(Material.EMERALD);
        ItemMeta chipped_emerald_meta = chipped_emerald.getItemMeta();
        Objects.requireNonNull(chipped_emerald_meta).setCustomModelData(1);
        chipped_emerald_meta.setLocalizedName("item.minecraft.chipped_emerald");
        chipped_emerald.setItemMeta(chipped_emerald_meta);
        return chipped_emerald;
    }
    /*=================Tree blocks=====================*/
    public static abstract class TreeStructure {
        public List<Material> Stems = new ArrayList<>();
        public List<Material> Bushes = new ArrayList<>();
        public Float StemRange;
        public Float BushRange;
        public Float Range;
        public int bushCount = 0;
        public int stemCount = 0;
        public TreeStructure(List<Material> stems, List<Material> bushes, Float stem_range, Float bush_range, Float range) {
            Stems.addAll(stems);
            Bushes.addAll(bushes);
            StemRange = stem_range;
            BushRange = bush_range;
            Range = range;
        }
        public abstract Block[] getTree();
        public int getDurabilityCost() {
            return (int)(bushCount*0.05+stemCount);
        }
    }
    public static List<TreeStructure> Trees() {
        ArrayList<TreeStructure> trees = new ArrayList<>();
        trees.add(Oaks());
        trees.add(Spruces());
        trees.add(Birches());
        trees.add(Jungles());
        trees.add(Acacias());
        trees.add(DarkOaks());
        trees.add(ManGroves());
        return trees;
    }
    public static TreeStructure Oaks() {
        return new TreeStructure(
                Arrays.asList(Material.OAK_LOG, Material.OAK_WOOD),
                Arrays.asList(Material.OAK_LEAVES, Material.VINE),
                2.3f, 2.3f, 2.83f) {
            @Override
            public Block[] getTree() {
                return new Block[0];
            }
        };
    }
    public static TreeStructure Spruces() {
        return new TreeStructure(
                Arrays.asList(Material.SPRUCE_LOG, Material.SPRUCE_WOOD),
                Collections.singletonList(Material.SPRUCE_LEAVES),
                2.3f, 2.3f, 2.3f) {
            @Override
            public Block[] getTree() {
                return new Block[0];
            }
        };
    }
    public static TreeStructure Birches() {
        return new TreeStructure(
                Arrays.asList(Material.BIRCH_LOG, Material.BIRCH_WOOD),
                Collections.singletonList(Material.BIRCH_LEAVES),
                2.3f, 2.3f, 2.3f) {
            @Override
            public Block[] getTree() {
                return new Block[0];
            }
        };
    }
    public static TreeStructure Jungles() {
        return new TreeStructure(
                Arrays.asList(Material.JUNGLE_LOG, Material.JUNGLE_WOOD),
                Arrays.asList(Material.JUNGLE_LEAVES, Material.VINE, Material.COCOA),
                2.3f, 2.3f, 2.3f) {
            @Override
            public Block[] getTree() {
                return new Block[0];
            }
        };
    }
    public static TreeStructure Acacias() {
        return new TreeStructure(
                Arrays.asList(Material.ACACIA_LOG, Material.ACACIA_WOOD),
                Collections.singletonList(Material.ACACIA_LEAVES),
                2.3f, 2.3f, 2.3f) {
            @Override
            public Block[] getTree() {
                return new Block[0];
            }
        };
    }
    public static TreeStructure DarkOaks() {
        return new TreeStructure(
                Arrays.asList(Material.DARK_OAK_LOG, Material.DARK_OAK_WOOD),
                Collections.singletonList(Material.ACACIA_LEAVES),
                2.3f, 2.3f, 2.3f) {
            @Override
            public Block[] getTree() {
                return new Block[0];
            }
        };
    }
    public static TreeStructure ManGroves() {
        return new TreeStructure(
                Arrays.asList(Material.MANGROVE_LOG, Material.MANGROVE_WOOD, Material.MANGROVE_ROOTS),
                Arrays.asList(Material.MANGROVE_LEAVES, Material.VINE),
                2.3f, 2.3f, 2.3f) {
            @Override
            public Block[] getTree() {
                return new Block[0];
            }
        };
    }
}
