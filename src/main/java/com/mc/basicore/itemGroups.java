package com.mc.basicore;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

import static org.bukkit.ChatColor.*;
import static org.bukkit.Material.*;

public class itemGroups {
    public static List<ChatColor> colors() {
        return Arrays.asList(
                BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE
        );
    }
    /*============================Tools==================================*/
    public static List<Material> tools() {
        List<Material> tools = new ArrayList<>();
        tools.addAll(shovels());
        tools.addAll(pickaxes());
        tools.addAll(axes());
        return tools;
    }
    public static List<Material> shovels() {
        List<Material> shovels = new ArrayList<>();
        shovels.add(WOODEN_SHOVEL);
        shovels.add(STONE_SHOVEL);
        shovels.add(IRON_SHOVEL);
        shovels.add(GOLDEN_SHOVEL);
        shovels.add(DIAMOND_SHOVEL);
        shovels.add(NETHERITE_SHOVEL);
        return shovels;
    }
    public static List<Material> pickaxes() {
        List<Material> shovels = new ArrayList<>();
        shovels.add(WOODEN_PICKAXE);
        shovels.add(STONE_PICKAXE);
        shovels.add(IRON_PICKAXE);
        shovels.add(GOLDEN_PICKAXE);
        shovels.add(DIAMOND_PICKAXE);
        shovels.add(NETHERITE_PICKAXE);
        return shovels;
    }
    public static List<Material> axes() {
        List<Material> shovels = new ArrayList<>();
        shovels.add(WOODEN_AXE);
        shovels.add(STONE_AXE);
        shovels.add(IRON_AXE);
        shovels.add(GOLDEN_AXE);
        shovels.add(DIAMOND_AXE);
        shovels.add(NETHERITE_AXE);
        return shovels;
    }
    /*===================================================================*/
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
                new ItemStack(TROPICAL_FISH),
                new ItemStack(PUFFERFISH),
                new ItemStack(COD),
                new ItemStack(SALMON)
        };
    }
    public static RecipeChoice stones() {
        return new RecipeChoice.MaterialChoice(Arrays.asList(
                COBBLESTONE,
                GRANITE,
                DIORITE,
                ANDESITE,
                DEEPSLATE,
                MOSSY_COBBLESTONE,
                END_STONE
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
                new ItemStack(STICK)
        ));
    }
    public static ItemStack oak_stick() {
        ItemStack oak_stick = new ItemStack(STICK);
        ItemMeta oak_stick_meta = oak_stick.getItemMeta();
        Objects.requireNonNull(oak_stick_meta).setCustomModelData(1);
        oak_stick_meta.setLocalizedName("item.minecraft.oak_stick");
        oak_stick_meta.setDisplayName(RESET + "橡木棒");
        oak_stick.setItemMeta(oak_stick_meta);
        return oak_stick;
    }
    public static ItemStack spruce_stick() {
        ItemStack spruce_stick = new ItemStack(STICK);
        ItemMeta spruce_stick_meta = spruce_stick.getItemMeta();
        Objects.requireNonNull(spruce_stick_meta).setCustomModelData(1);
        spruce_stick_meta.setLocalizedName("item.minecraft.spruce_stick");
        spruce_stick_meta.setDisplayName(RESET + "杉木棒");
        spruce_stick.setItemMeta(spruce_stick_meta);
        return spruce_stick;
    }
    public static ItemStack birch_stick() {
        ItemStack birch_stick = new ItemStack(STICK);
        ItemMeta birch_stick_meta = birch_stick.getItemMeta();
        Objects.requireNonNull(birch_stick_meta).setCustomModelData(1);
        birch_stick_meta.setLocalizedName("item.minecraft.birch_stick");
        birch_stick_meta.setDisplayName(RESET + "樺木棒");
        birch_stick.setItemMeta(birch_stick_meta);
        return birch_stick;
    }
    public static ItemStack jungle_stick() {
        ItemStack jungle_stick = new ItemStack(STICK);
        ItemMeta jungle_stick_meta = jungle_stick.getItemMeta();
        Objects.requireNonNull(jungle_stick_meta).setCustomModelData(1);
        jungle_stick_meta.setLocalizedName("item.minecraft.jungle_stick");
        jungle_stick_meta.setDisplayName(RESET + "叢林木棒");
        jungle_stick.setItemMeta(jungle_stick_meta);
        return jungle_stick;
    }
    public static ItemStack acacia_stick() {
        ItemStack acacia_stick = new ItemStack(STICK);
        ItemMeta acacia_stick_meta = acacia_stick.getItemMeta();
        Objects.requireNonNull(acacia_stick_meta).setCustomModelData(1);
        acacia_stick_meta.setLocalizedName("item.minecraft.acacia_stick");
        acacia_stick_meta.setDisplayName(RESET + "相思木棒");
        acacia_stick.setItemMeta(acacia_stick_meta);
        return acacia_stick;
    }
    public static ItemStack dark_oak_stick() {
        ItemStack dark_oak_stick = new ItemStack(STICK);
        ItemMeta dark_oak_stick_meta = dark_oak_stick.getItemMeta();
        Objects.requireNonNull(dark_oak_stick_meta).setCustomModelData(1);
        dark_oak_stick_meta.setLocalizedName("item.minecraft.dark_oak_stick");
        dark_oak_stick_meta.setDisplayName(RESET + "黑橡木棒");
        dark_oak_stick.setItemMeta(dark_oak_stick_meta);
        return dark_oak_stick;
    }
    public static ItemStack crimson_stick() {
        ItemStack crimson_stick = new ItemStack(STICK);
        ItemMeta crimson_stick_meta = crimson_stick.getItemMeta();
        Objects.requireNonNull(crimson_stick_meta).setCustomModelData(1);
        crimson_stick_meta.setLocalizedName("item.minecraft.crimson_stick");
        crimson_stick_meta.setDisplayName(RESET + "緋紅蕈棒");
        crimson_stick.setItemMeta(crimson_stick_meta);
        return crimson_stick;
    }
    public static ItemStack warp_stick() {
        ItemStack warp_stick = new ItemStack(STICK);
        ItemMeta dark_oak_stick_meta = warp_stick.getItemMeta();
        Objects.requireNonNull(dark_oak_stick_meta).setCustomModelData(1);
        dark_oak_stick_meta.setLocalizedName("item.minecraft.warp_stick");
        dark_oak_stick_meta.setDisplayName(RESET + "扭曲蕈棒");
        warp_stick.setItemMeta(dark_oak_stick_meta);
        return warp_stick;
    }
    public static ItemStack apple_pie() {
        ItemStack apple_pie = new ItemStack(PUMPKIN_PIE);
        ItemMeta apple_pie_meta = apple_pie.getItemMeta();
        Objects.requireNonNull(apple_pie_meta).setCustomModelData(1);
        apple_pie_meta.setLocalizedName("item.minecraft.apple_pie");
        apple_pie_meta.setDisplayName(RESET + "蘋果派");
        apple_pie.setItemMeta(apple_pie_meta);
        return apple_pie;
    }
    public static ItemStack wooden_hammer() {
        ItemStack wooden_hammer = new ItemStack(WOODEN_PICKAXE);
        ItemMeta wooden_hammer_meta = wooden_hammer.getItemMeta();
        Objects.requireNonNull(wooden_hammer_meta).setCustomModelData(1);
        wooden_hammer_meta.setLocalizedName("item.minecraft.wooden_hammer");
        wooden_hammer_meta.setLocalizedName(RESET + "木錘");
        wooden_hammer.setItemMeta(wooden_hammer_meta);
        return wooden_hammer;
    }
    public static ItemStack stone_hammer() {
        ItemStack stone_hammer = new ItemStack(STONE_PICKAXE);
        ItemMeta stone_hammer_meta = stone_hammer.getItemMeta();
        Objects.requireNonNull(stone_hammer_meta).setCustomModelData(1);
        stone_hammer_meta.setLocalizedName("item.minecraft.stone_hammer");
        stone_hammer_meta.setLocalizedName(RESET + "石錘");
        stone_hammer.setItemMeta(stone_hammer_meta);
        return stone_hammer;
    }
    public static ItemStack iron_hammer() {
        ItemStack iron_hammer = new ItemStack(IRON_PICKAXE);
        ItemMeta iron_hammer_meta = iron_hammer.getItemMeta();
        Objects.requireNonNull(iron_hammer_meta).setCustomModelData(1);
        iron_hammer_meta.setLocalizedName("item.minecraft.iron_hammer");
        iron_hammer_meta.setLocalizedName(RESET + "鐵錘");
        iron_hammer.setItemMeta(iron_hammer_meta);
        return iron_hammer;
    }
    public static ItemStack golden_hammer() {
        ItemStack golden_hammer = new ItemStack(GOLDEN_PICKAXE);
        ItemMeta golden_hammer_meta = golden_hammer.getItemMeta();
        Objects.requireNonNull(golden_hammer_meta).setCustomModelData(1);
        golden_hammer_meta.setLocalizedName("item.minecraft.golden_hammer");
        golden_hammer_meta.setLocalizedName(RESET + "金錘");
        golden_hammer.setItemMeta(golden_hammer_meta);
        return golden_hammer;
    }
    public static ItemStack diamond_hammer() {
        ItemStack diamond_hammer = new ItemStack(DIAMOND_PICKAXE);
        ItemMeta diamond_hammer_meta = diamond_hammer.getItemMeta();
        Objects.requireNonNull(diamond_hammer_meta).setCustomModelData(1);
        diamond_hammer_meta.setLocalizedName("item.minecraft.diamond_hammer");
        diamond_hammer_meta.setDisplayName(RESET + "鑽石錘");
        diamond_hammer.setItemMeta(diamond_hammer_meta);
        return diamond_hammer;
    }
    public static ItemStack netherite_hammer() {
        ItemStack netherite_hammer = new ItemStack(NETHERITE_PICKAXE);
        ItemMeta netherite_hammer_meta = netherite_hammer.getItemMeta();
        Objects.requireNonNull(netherite_hammer_meta).setCustomModelData(1);
        netherite_hammer_meta.setLocalizedName("item.minecraft.netherite_hammer");
        netherite_hammer_meta.setDisplayName(RESET + "獄髓錘");
        netherite_hammer.setItemMeta(netherite_hammer_meta);
        return netherite_hammer;
    }
    public static ItemStack enchanted_iron_ingot() {
        ItemStack enchanted_iron_ingot = new ItemStack(IRON_INGOT);
        ItemMeta enchanted_iron_ingot_meta = enchanted_iron_ingot.getItemMeta();
        Objects.requireNonNull(enchanted_iron_ingot_meta).setCustomModelData(1);
        enchanted_iron_ingot_meta.setLocalizedName("item.minecraft.enchanted_iron_ingot");
        enchanted_iron_ingot.setItemMeta(enchanted_iron_ingot_meta);
        return enchanted_iron_ingot;
    }
    public static ItemStack chipped_emerald() {
        ItemStack chipped_emerald = new ItemStack(EMERALD);
        ItemMeta chipped_emerald_meta = chipped_emerald.getItemMeta();
        Objects.requireNonNull(chipped_emerald_meta).setCustomModelData(1);
        chipped_emerald_meta.setLocalizedName("item.minecraft.chipped_emerald");
        chipped_emerald.setItemMeta(chipped_emerald_meta);
        return chipped_emerald;
    }
    /*=================Tree blocks=====================*/
    public static List<Material> lockable() {
        return Arrays.asList(CHEST,BARREL,FURNACE,SMOKER,BLAST_FURNACE);
    }
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
    public static List<Material> Stems() {
        List<Material> stems = new ArrayList<>();
        for (TreeStructure tree:Trees()) {
            stems.addAll(tree.Stems);
        }
        return stems;
    }
    public static List<TreeStructure> Trees() {
        return Arrays.asList(
                Oaks(),Spruces(),Birches(),Jungles(),Acacias(),DarkOaks(),ManGroves(),Cherries(),Crimsons(),Warps()
        );
    }
    public static TreeStructure Cherries() {
        return new TreeStructure(
                Arrays.asList(CHERRY_LOG, CHERRY_WOOD),
                Collections.singletonList(CHERRY_LEAVES),
                2.3f, 2.3f, 2.83f) {
            @Override
            public Block[] getTree() {
                return new Block[0];
            }
        };
    }
    public static TreeStructure Warps() {
        return new TreeStructure(
                Collections.singletonList(WARPED_STEM),
                Arrays.asList(WARPED_WART_BLOCK,SHROOMLIGHT,TWISTING_VINES),
                2.3f, 2.3f, 2.83f) {
            @Override
            public Block[] getTree() {
                return new Block[0];
            }
        };
    }
    public static TreeStructure Crimsons() {
        return new TreeStructure(
                Collections.singletonList(CRIMSON_STEM),
                Arrays.asList(NETHER_WART_BLOCK,WEEPING_VINES,SHROOMLIGHT),
                2.3f, 2.3f, 2.83f) {
            @Override
            public Block[] getTree() {
                return new Block[0];
            }
        };
    }
    public static TreeStructure Oaks() {
        return new TreeStructure(
                Arrays.asList(OAK_LOG, OAK_WOOD),
                Arrays.asList(OAK_LEAVES, VINE),
                2.3f, 2.3f, 2.83f) {
            @Override
            public Block[] getTree() {
                return new Block[0];
            }
        };
    }
    public static TreeStructure Spruces() {
        return new TreeStructure(
                Arrays.asList(SPRUCE_LOG, SPRUCE_WOOD),
                Collections.singletonList(SPRUCE_LEAVES),
                2.3f, 2.3f, 2.3f) {
            @Override
            public Block[] getTree() {
                return new Block[0];
            }
        };
    }
    public static TreeStructure Birches() {
        return new TreeStructure(
                Arrays.asList(BIRCH_LOG, BIRCH_WOOD),
                Collections.singletonList(BIRCH_LEAVES),
                2.3f, 2.3f, 2.3f) {
            @Override
            public Block[] getTree() {
                return new Block[0];
            }
        };
    }
    public static TreeStructure Jungles() {
        return new TreeStructure(
                Arrays.asList(JUNGLE_LOG, JUNGLE_WOOD),
                Arrays.asList(JUNGLE_LEAVES, VINE, COCOA),
                2.3f, 2.3f, 2.3f) {
            @Override
            public Block[] getTree() {
                return new Block[0];
            }
        };
    }
    public static TreeStructure Acacias() {
        return new TreeStructure(
                Arrays.asList(ACACIA_LOG, ACACIA_WOOD),
                Collections.singletonList(ACACIA_LEAVES),
                2.3f, 2.3f, 2.3f) {
            @Override
            public Block[] getTree() {
                return new Block[0];
            }
        };
    }
    public static TreeStructure DarkOaks() {
        return new TreeStructure(
                Arrays.asList(DARK_OAK_LOG, DARK_OAK_WOOD),
                Collections.singletonList(ACACIA_LEAVES),
                2.3f, 2.3f, 2.3f) {
            @Override
            public Block[] getTree() {
                return new Block[0];
            }
        };
    }
    public static TreeStructure ManGroves() {
        return new TreeStructure(
                Arrays.asList(MANGROVE_LOG, MANGROVE_WOOD, MANGROVE_ROOTS),
                Arrays.asList(MANGROVE_LEAVES, VINE),
                2.3f, 2.3f, 2.3f) {
            @Override
            public Block[] getTree() {
                return new Block[0];
            }
        };
    }
    /*=================Mine blocks=====================*/
    public static abstract class MineData {
        public List<Material> blocks;
        public Float Range;
        public Float Height;
        public int Count = 0;
        public MineData(List<Material> target, Float range, Float height) {
            blocks= target;
            Range = range;
            Height = height;
        }
        public int getDurabilityCost() {
            return Count;
        }
    }
    public static List<Material> MineTargets() {
        List<Material> temp = new ArrayList<>();
        for (MineData mine: Mines()) {
            temp.addAll(mine.blocks);
        }
        return temp;
    }
    public static List<MineData> Mines() {
        ArrayList<MineData> mines = new ArrayList<>();
        mines.add(Stones());
        mines.add(Iron_Ore());
        mines.add(Coal());
        mines.add(Gold_Ore());
        mines.add(Copper_Ore());
        mines.add(RedStone_Ore());
        mines.add(Emerald_Ore());
        mines.add(Lapis_Ore());
        mines.add(Quartz_Ore());
        mines.add(Diamond_Ore());
        mines.add(GlowStone());
        return mines;
    }
    public static MineData Stones() {
        return new MineData(Arrays.asList(
                STONE,
                NETHERRACK,
                DEEPSLATE,
                BLACKSTONE,
                GRANITE,
                CALCITE,
                DIORITE,
                ANDESITE,
                TUFF,
                END_STONE
        ), 3f, 2f) {};
    }
    public static MineData Iron_Ore() {
        return new MineData(Arrays.asList(IRON_ORE, DEEPSLATE_IRON_ORE), 6f, 6f) {};
    }
    public static MineData Coal() {
        return new MineData( Arrays.asList(COAL_ORE, DEEPSLATE_COAL_ORE), 6f, 6f) {};
    }
    public static MineData Gold_Ore() {
        return new MineData(Arrays.asList(GOLD_ORE, DEEPSLATE_GOLD_ORE, NETHER_GOLD_ORE), 6f, 6f) {};
    }
    public static MineData Copper_Ore() {
        return new MineData(Arrays.asList(COPPER_ORE, DEEPSLATE_COPPER_ORE), 6f, 6f) {};
    }
    public static MineData RedStone_Ore() {
        return new MineData(Arrays.asList(REDSTONE_ORE, DEEPSLATE_REDSTONE_ORE), 6f, 6f) {};
    }
    public static MineData Emerald_Ore() {
        return new MineData(Arrays.asList(EMERALD_ORE, DEEPSLATE_EMERALD_ORE), 6f, 6f) {};
    }
    public static MineData Lapis_Ore() {
        return new MineData(Arrays.asList(LAPIS_ORE, DEEPSLATE_LAPIS_ORE), 6f, 6f) {};
    }
    public static MineData Quartz_Ore() {
        return new MineData(Collections.singletonList(NETHER_QUARTZ_ORE), 6f, 6f) {};
    }
    public static MineData Diamond_Ore() {
        return new MineData(Arrays.asList(DIAMOND_ORE, DEEPSLATE_DIAMOND_ORE), 6f, 6f) {};
    }
    public static MineData GlowStone() {
        return new MineData(Collections.singletonList(GLOWSTONE), 6f, 6f) {};
    }
}
