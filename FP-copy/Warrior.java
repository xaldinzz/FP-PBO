public class Warrior extends Hero {
    public Warrior(String nama) {
        // HP Tinggi (60), AC Tinggi (16), Mana Sedikit (20)
        super(nama, 60, 16, 5, 20); 
    }

    @Override
    public void serang(Unit target) {
        System.out.println(nama + " menebas dengan " + equippedWeapon.name + "!");
        
        // Logika Hitung Dadu
        int roll = Dadu.lempar(20);
        int totalHit = roll + getDamageBonus();
        
        // Tampilkan Log
        System.out.print("   [Roll: " + roll + " + Bonus: " + getDamageBonus() + " = " + totalHit + "]");
        System.out.print(" vs [AC Musuh: " + target.ac + "]");

        if (totalHit >= target.ac) {
            System.out.println(" -> KENA!");
            int dmg = Dadu.lempar(10) + getDamageBonus(); 
            target.hp -= dmg;
            System.out.println("   -> " + target.getNama() + " terkena " + dmg + " damage fisik.");
        } else {
            System.out.println(" -> MELESET!");
        }
    }

    @Override
    public void specialSkill(Unit target) {
        // Skill: HEAVY SMASH
        if (mana >= 5) {
            mana -= 5;
            System.out.println(nama + " menggunakan HEAVY SMASH!");
            // Skill ini pasti kena tapi damage random besar
            int dmg = Dadu.lempar(12) + 5 + equippedWeapon.bonusDamage;
            target.hp -= dmg;
            System.out.println("   -> BRUKKK! " + target.getNama() + " hancur kena " + dmg + " damage!");
        } else {
            System.out.println("   -> Tenaga (Mana) habis! Gunakan serangan biasa.");
        }
    }
}