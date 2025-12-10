public class Monster extends Unit {
    public int xpReward; 

    public Monster(int lantai) {
        super("Musuh", 10, 10, 0); 
        scaleStats(lantai);
    }

    private void scaleStats(int lantai) {
        int tipe = Dadu.lempar(3);
        int bonusHp = lantai * 6; 
        int bonusAtk = lantai / 2;

        if (lantai == 10) { // BOSS FINAL
            this.nama = "DEMON LORD";
            this.maxHp = 250;
            this.hp = 250;
            this.ac = 18;
            this.attackBonus = 12;
            this.xpReward = 1000;
        } else {
            if (tipe == 1) {
                this.nama = "Goblin Lvl." + lantai;
                this.maxHp = 15 + bonusHp;
                this.ac = 12;
            } else if (tipe == 2) {
                this.nama = "Orc Lvl." + lantai;
                this.maxHp = 25 + bonusHp;
                this.ac = 14;
            } else {
                this.nama = "Skeleton Lvl." + lantai;
                this.maxHp = 10 + bonusHp;
                this.ac = 10;
            }
            this.hp = this.maxHp;
            this.attackBonus = 2 + bonusAtk;
            this.xpReward = 20 + (lantai * 8); 
        }
    }

    @Override
    public void serang(Unit target) {
        System.out.println(nama + " menyerang " + target.getNama() + "!");
        
        int roll = Dadu.lempar(20);
        int totalHit = roll + attackBonus;
        
        // Log Serangan Musuh
        System.out.print("   [Roll: " + roll + " + Atk: " + attackBonus + " = " + totalHit + "]");
        System.out.print(" vs [AC Kamu: " + target.ac + "]"); 

        if (totalHit >= target.ac) {
            System.out.println(" -> KENA!");
            int dmg = Dadu.lempar(6) + (attackBonus / 2);
            target.hp -= dmg;
            System.out.println("   -> Aww! Kamu terkena " + dmg + " damage.");
        } else {
            System.out.println(" -> MELESET!");
            System.out.println("   -> Kamu berhasil menangkis serangan!");
        }
    }
}