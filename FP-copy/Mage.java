public class Mage extends Hero {
    public Mage(String nama) {
        // HP Tipis (35), AC Rendah (12), Mana Banyak (60)
        super(nama, 35, 12, 3, 60); 
    }

    @Override
    public void serang(Unit target) {
        System.out.println(nama + " memukul dengan " + equippedWeapon.name + ".");
        
        int roll = Dadu.lempar(20);
        int totalHit = roll + getDamageBonus();
        
        System.out.print("   [Roll: " + roll + " + Bonus: " + getDamageBonus() + " = " + totalHit + "]");
        System.out.print(" vs [AC Musuh: " + target.ac + "]");

        if (totalHit >= target.ac) {
            System.out.println(" -> KENA!");
            int dmg = Dadu.lempar(6) + getDamageBonus(); 
            target.hp -= dmg;
            System.out.println("   -> " + target.getNama() + " terkena " + dmg + " damage.");
        } else {
            System.out.println(" -> MELESET!");
        }
    }

    @Override
    public void specialSkill(Unit target) {
        // Skill: FIREBALL
        if (mana >= 15) {
            mana -= 15;
            // Magic damage ditambah sedikit power senjata
            int magicBonus = equippedWeapon.bonusDamage / 2; 
            int dmg = Dadu.lempar(20) + 8 + magicBonus;
            
            System.out.println(nama + " cast FIREBALL! (Mana sisa: " + mana + ")");
            target.hp -= dmg;
            System.out.println("   -> BOOM! " + target.getNama() + " terbakar (" + dmg + " Magic Damage)!");
        } else {
            System.out.println("   -> Mana tidak cukup (Butuh 15)!");
        }
    }
}