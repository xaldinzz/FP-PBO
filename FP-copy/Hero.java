import java.util.ArrayList;

public abstract class Hero extends Unit {
    protected int maxMana; 
    protected int mana;
    
    protected int level;
    protected int xp;
    protected int xpToNextLevel;
    public ArrayList<String> inventory;
    
    // BARU: Slot Senjata
    public Weapon equippedWeapon; 

    public Hero(String nama, int hp, int ac, int attackBonus, int mana) {
        super(nama, hp, ac, attackBonus);
        this.maxMana = mana;
        this.mana = mana;
        this.level = 1;
        this.xp = 0;
        this.xpToNextLevel = 50;
        this.inventory = new ArrayList<>();
        this.inventory.add("Potion");
        
        // Modal awal: Senjata Jelek
        this.equippedWeapon = new Weapon("Kayu Ranting", 0);
    }
    
    // BARU: Method ganti senjata
    public void equipWeapon(Weapon newWeapon) {
        System.out.println("   -> Mengganti " + this.equippedWeapon.name + " dengan " + newWeapon.name);
        this.equippedWeapon = newWeapon;
    }

    // Method hitung total damage (Stat dasar + Senjata)
    public int getDamageBonus() {
        return this.attackBonus + this.equippedWeapon.bonusDamage;
    }

    // ... (Sisa method gainXp, levelUp, dll sama seperti sebelumnya) ...
    // Biar ringkas saya tidak copy paste bagian gainXp/levelUp yang lama,
    // Pastikan method levelUp, gainXp, pakaiPotion, rest tetap ada ya!
    
    public void gainXp(int amount) {
        this.xp += amount;
        System.out.println("   -> Mendapatkan " + amount + " XP.");
        if (this.xp >= xpToNextLevel) levelUp();
    }
    
    private void levelUp() {
        this.level++;
        this.xp -= this.xpToNextLevel;
        this.xpToNextLevel = (int)(this.xpToNextLevel * 1.5);
        this.maxHp += 15;
        this.hp = maxHp;
        this.maxMana += 10;
        this.mana = maxMana;
        this.attackBonus += 1;
        System.out.println("\n*** LEVEL UP! Level " + level + " ***");
    }
    
    public void pakaiPotion() {
        if (inventory.contains("Potion")) {
            inventory.remove("Potion");
            heal(30);
            System.out.println("   -> GLUK GLUK! HP Pulih.");
        } else { System.out.println("Potion habis!"); }
    }
    
    public void rest() {
        heal(maxHp/3);
        this.mana += maxMana/2;
        if (this.mana > maxMana) this.mana = maxMana;
        System.out.println("Istirahat... HP/Mana pulih.");
    }

    public abstract void specialSkill(Unit target);
}