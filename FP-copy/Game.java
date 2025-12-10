import java.util.Scanner;
import java.util.ArrayList;

public class Game {
    Scanner scan = new Scanner(System.in);
    Hero player;
    int currentFloor = 1;
    final int MAX_FLOOR = 10;
    boolean isRunning = true;

    public void mulai() {
        System.out.println("========================================");
        System.out.println("       TOWER OF DOOM: LEGENDS");
        System.out.println("========================================");
        System.out.println("Pilih Class:");
        System.out.println("[1] Warrior (Kuat Fisik, HP Tebal)");
        System.out.println("[2] Mage (Magic Sakit, HP Tipis)");
        System.out.print("Pilihan > ");
        String job = scan.nextLine();
        
        System.out.print("Masukkan Nama Hero: ");
        String namaHero = scan.nextLine();

        if (job.equals("1")) player = new Warrior(namaHero);
        else player = new Mage(namaHero);

        // --- GAME LOOP UTAMA ---
        while (currentFloor <= MAX_FLOOR && player.isAlive() && isRunning) {
            System.out.println("\n----------------------------------------");
            System.out.println("       MEMASUKI LANTAI " + currentFloor);
            System.out.println("----------------------------------------");

            ArrayList<Monster> enemies = generateEnemies(currentFloor);
            battle(enemies);

            if (player.isAlive()) {
                System.out.println("\n>>> Lantai " + currentFloor + " Bersih! <<<");
                if (currentFloor == MAX_FLOOR) {
                    System.out.println("SELAMAT! KAMU TELAH MENGALAHKAN DEMON LORD!");
                    System.out.println("Credits: Created by Naufaldi Faqih Abimanyu and Denzel Daniels.");
                    isRunning = false;
                } else {
                    player.rest(); // Heal dikit
                    currentFloor++;
                    System.out.println("Tekan Enter untuk lanjut...");
                    scan.nextLine();
                }
            } else {
                System.out.println("GAME OVER. Perjuanganmu berakhir di Lantai " + currentFloor);
                System.out.println("Credits: Created by Naufaldi Faqih Abimanyu and Denzel Daniels.");
                isRunning = false;
            }
        }
    }

    private ArrayList<Monster> generateEnemies(int floor) {
        ArrayList<Monster> list = new ArrayList<>();
        if (floor == 10) {
            list.add(new Monster(10)); // Boss
        } else {
            // Jumlah musuh bertambah di lantai tinggi
            int jumlah = 1;
            if (floor >= 4) jumlah = 2;
            if (floor >= 7) jumlah = 3;

            for (int i = 0; i < jumlah; i++) {
                list.add(new Monster(floor));
            }
        }
        System.out.println("MUNCUL " + list.size() + " MUSUH!");
        return list;
    }

    private void battle(ArrayList<Monster> enemies) {
        while (player.isAlive() && !enemies.isEmpty()) {
            // Tampilan Status
            System.out.println("\n[STATUS] " + player.getNama() + " (Lvl " + player.level + ")");
            System.out.println("HP: " + player.getHp() + " | Mana: " + player.mana);
            System.out.println("Weapon: " + player.equippedWeapon.getInfo());
            
            // Tampilan Musuh
            System.out.println("\n[MUSUH]");
            for (int i = 0; i < enemies.size(); i++) {
                System.out.println((i+1) + ". " + enemies.get(i).getNama() + " (HP: " + enemies.get(i).getHp() + ")");
            }

            System.out.println("\nAKSI: [1] Serang, [2] Skill, [3] Inventory");
            System.out.print("> ");
            String input = scan.nextLine();
            
            // Logic Aksi
            if (input.equals("3")) {
                // Buka Inventory
                if (player.inventory.isEmpty()) {
                    System.out.println("Tas kosong!");
                } else {
                    System.out.println("Tas: " + player.inventory);
                    System.out.print("Gunakan Potion? (y/n) > ");
                    if (scan.nextLine().equalsIgnoreCase("y")) player.pakaiPotion();
                }
                continue; // Skip turn musuh kalau cuma cek tas
            } 
            
            // Logic Serang
            int targetIdx = 0;
            if (enemies.size() > 1) {
                System.out.print("Pilih target (1-" + enemies.size() + ") > ");
                try {
                    targetIdx = Integer.parseInt(scan.nextLine()) - 1;
                } catch(Exception e) { targetIdx = 0; }
            }
            if (targetIdx < 0 || targetIdx >= enemies.size()) targetIdx = 0;
            
            Monster target = enemies.get(targetIdx);

            if (input.equals("1")) player.serang(target);
            else if (input.equals("2")) player.specialSkill(target);
            else { 
                System.out.println("Perintah salah!"); 
                continue; 
            }

            // Cek Musuh Mati
            if (!target.isAlive()) {
                System.out.println(target.getNama() + " TEWAS!");
                player.gainXp(target.xpReward);
                enemies.remove(targetIdx);
                
                // --- FITUR DROP LOOT & PILIH SENJATA ---
                handleLoot(); 
            }

            // Giliran Musuh
            if (!enemies.isEmpty()) {
                System.out.println("\n--- MUSUH MENYERANG ---");
                for (Monster m : enemies) {
                    m.serang(player);
                    if (!player.isAlive()) break;
                }
            }
        }
    }
    
    // Method khusus menangani drop item
    private void handleLoot() {
        int luck = Dadu.lempar(100);
        
        // 30% Chance Drop Potion
        if (luck <= 30) {
            player.inventory.add("Potion");
            System.out.println("   [LOOT] Musuh menjatuhkan Potion!");
        }
        // 25% Chance Drop Weapon
        else if (luck >= 75) {
            System.out.println("\n   !!! SENJATA JATUH !!!");
            Weapon dropItem = new Weapon(); // Generate Random
            
            System.out.println("   Ditemukan    : " + dropItem.getInfo());
            System.out.println("   Senjata Kamu : " + player.equippedWeapon.getInfo());
            
            System.out.print("   Ambil dan pakai senjata baru? (y/n) > ");
            String ambil = scan.nextLine();
            
            if (ambil.equalsIgnoreCase("y")) {
                player.equipWeapon(dropItem);
            } else {
                System.out.println("   -> Kamu meninggalkan senjata itu.");
            }
            System.out.println("");
        }
    }
}