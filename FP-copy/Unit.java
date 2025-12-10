public abstract class Unit {
    protected String nama;
    protected int maxHp;
    protected int hp;
    protected int ac;          
    protected int attackBonus; 
    
    public Unit(String nama, int hp, int ac, int attackBonus) {
        this.nama = nama;
        this.maxHp = hp;
        this.hp = hp;
        this.ac = ac;
        this.attackBonus = attackBonus;
    }

    public boolean isAlive() { return hp > 0; }
    public String getNama() { return nama; }
    public int getHp() { return hp; }

    public void heal(int amount) {
        this.hp += amount;
        if (this.hp > maxHp) this.hp = maxHp;
        System.out.println(nama + " memulihkan " + amount + " HP.");
    }
    
    public void restoreMana(int amount) {} // Dummy method untuk Mage

    public abstract void serang(Unit target);
}