import java.util.Random;

public class Weapon {
    public String name;
    public int bonusDamage;
    public String rarity; // Common, Rare, Legendary
    
    private Random rand = new Random();

    // Constructor untuk bikin senjata random
    public Weapon() {
        int gacha = rand.nextInt(100) + 1; // Roll 1-100

        if (gacha > 95) { 
            // 5% Chance - LEGENDARY
            this.rarity = "LEGENDARY";
            this.bonusDamage = rand.nextInt(10) + 15; // +15 sampai +24 damage
            this.name = generateName("Legendary");
        } else if (gacha > 75) {
            // 20% Chance - RARE
            this.rarity = "RARE";
            this.bonusDamage = rand.nextInt(5) + 5; // +5 sampai +9 damage
            this.name = generateName("Rare");
        } else {
            // 75% Chance - COMMON
            this.rarity = "Common";
            this.bonusDamage = rand.nextInt(3) + 1; // +1 sampai +3 damage
            this.name = generateName("Common");
        }
    }
    
    // Constructor khusus untuk senjata awal (Starter)
    public Weapon(String name, int dmg) {
        this.name = name;
        this.bonusDamage = dmg;
        this.rarity = "Starter";
    }

    private String generateName(String tier) {
        String[] nouns = {"Pedang", "Kapak", "Tombak", "Tongkat", "Belati"};
        String noun = nouns[rand.nextInt(nouns.length)];
        
        if (tier.equals("LEGENDARY")) return noun + " NAGA EMAS";
        if (tier.equals("RARE")) return noun + " Baja Hitam";
        return noun + " Karatan";
    }
    
    public String getInfo() {
        return "[" + rarity + "] " + name + " (+ " + bonusDamage + " Dmg)";
    }
}