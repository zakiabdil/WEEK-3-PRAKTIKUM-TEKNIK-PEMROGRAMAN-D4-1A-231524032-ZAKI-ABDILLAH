package org.example;

import java.util.Scanner;


class Warung {
    private final Produk[] produk;
    private final Penjualan[] penjualan;
    private byte id = 0;

    public Warung(){
        produk = new Produk[10];
        penjualan = new Penjualan[10];
    }

    public void tampilListProduk(){
        for(int i = 0; i<id;i++){
            if(!isOutOfStock(i)){
                System.out.println("["+i+"]"+this.produk[i].getNama_produk()+"["+this.produk[i].getQty()+"]"+"\tRp. "+this.produk[i].getHarga());
            }
        }
    }

    void tambahListProduk(Produk[] produk){
        for(int i = 0; i < produk.length; i++){
            this.produk[id] = produk[id];
            nextId();
        }
    }

    void pesanProduk(byte id, int amount){
        if(this.produk[id].getQty() >= amount){
            this.produk[id].setQty(produk[id].getQty() - amount);
            System.out.println("Anda berhasil memesan "+ this.produk[id].getNama_produk()+" sebanyak "+amount);
        }else {
            System.out.println("Maaf qty tidak cukup untuk memenuhi pesanan anda, qty yang tersisa dari " + this.produk[id].getNama_produk() + " sebanyak " + this.produk[id].getQty());
        }
    }

    public boolean isOutOfStock(int id){
        return this.produk[id].getQty() == 0;
    }

    public void nextId(){
        id++;
    }

    public Produk getMakanan(byte id) {
        return produk[id];
    }


    public byte getId() {
        return id;
    }
}

class Penjualan{
    private int total_harga;
    private int qty;
    private String nama_barang;
    private final Warung warung;
    Penjualan(Warung warung){
        this.warung = warung;
    }

    void beliProduk(byte id, int qty, Person person){
        if ((qty <= warung.getMakanan(id).getQty()) && ((warung.getMakanan(id).getHarga() * qty) <= person.getMoney())){
            if(person.getInventori().getCapacity() != 0){
                person.setMoney(person.getMoney() - (int)(warung.getMakanan(id).getHarga() * qty));
                person.addProduk(warung.getMakanan(id).getNama_produk(),warung.getMakanan(id).getHarga(),qty);
                System.out.println("Anda berhasil membeli "+warung.getMakanan(id).getNama_produk() + " sebanyak "+qty+" dengan total harga "+(warung.getMakanan(id).getHarga() * qty));
                System.out.println("Sisa uang anda : "+person.getMoney());
                warung.getMakanan(id).setQty(warung.getMakanan(id).getQty() - qty);
            }
        }else {
            System.out.println("Barang yang anda inginkan tidak ada atau uang anda tidak cukup");
        }
    }
}

class Person{
    private Inventori inventori;
    private Wallet wallet;
    private String name;
    Person(String name, Inventori inventori, Wallet wallet){
        this.name = name;
        this.inventori = inventori;
        this.wallet = wallet;
    }

    public void addProduk(String nama, int harga, int qty){
        this.inventori.addProduk(nama,harga,qty);
    }

    public void displayMoney() {
        System.out.println(this.wallet.getAmount());
    }

    public int getMoney(){
        return this.wallet.getAmount();
    }

    protected void setMoney(Integer amount){
        this.wallet.setAmount(amount);
    }

    public Inventori getInventori() {
        return inventori;
    }
}

class Inventori{
    private Produk[] produk;
    private String name;
    private byte capacity;

        Inventori(String name){
        this.produk = new Produk[100];
        this.name = name;
        this.capacity = 100;
    }

    public Produk getProduk(byte id) {
        return this.produk[id];
    }

    public void addProduk(String nama, int harga, int qty){
        this.produk[(100-this.capacity)] = new Produk(nama,harga,qty);
        reduceCapacity();
    }

    public void displayInventori(){
            if (capacity == 100){
                System.out.println("Anda tidak memiliki apa apa");
            }else {
                for(int i = 0;i < (100-this.capacity);i++){
                    System.out.printf("[%d]%s : %d\n\n", i, this.produk[i].getNama_produk(), this.produk[i].getQty());
                }
            }
    }

    public byte getCapacity() {
        return this.capacity;
    }

    public void reduceCapacity(){this.capacity--;}
}

class Wallet{
    private int amount;
    private String owner;

    Wallet(String owner, int amount){
        this.amount = amount;
        this.owner = owner;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

class Produk extends Warung {

    private int qty;
    private final int harga;
    private final String nama_produk;

    Produk(String nama, int harga, int qty){
        this.nama_produk = nama;
        this.harga = harga;
        this.qty = qty;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public int getQty() {
        return qty;
    }

    public int getHarga() {
        return harga;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        byte input; int jumlah;
        Produk[] menu1 = new Produk[10];
        menu1[0] = new Produk("Bala-Bala",1_000,20);
        menu1[1] = new Produk("Gehu",2_500,24);
        menu1[2] = new Produk("Tahu",1_500,15);
        menu1[3] = new Produk("Molen",2_500,17);
        menu1[4] = new Produk("Tempe",1_500,13);
        menu1[5] = new Produk("Kol Goreng",3_500,20);
        menu1[6] = new Produk("Ayam Goreng",6_500,12);
        menu1[7] = new Produk("Ayam Opor",7_000,10);
        menu1[8] = new Produk("Capcay",3_000,24);
        menu1[9] = new Produk("Tempe Bacem",2_500,14);
        Warung warung1 = new Warung();
        warung1.tambahListProduk(menu1);

        Inventori inventori1 = new Inventori("Budi");
        Wallet wallet1 = new Wallet("Budi",43_300);
        Person person1 = new Person("Budi",inventori1,wallet1);

        Penjualan penjualan1 = new Penjualan(warung1);
        boolean isRunning = true;
        while(isRunning){
            warung1.tampilListProduk();
            System.out.println("["+(warung1.getId())+"]Cek Uang");
            System.out.println("["+(warung1.getId()+1)+"]Cek Inventori");
            System.out.println("["+(warung1.getId()+2)+"]Keluar");
            System.out.println("Mau pesan apa? tekan "+ (warung1.getId()+2) + " untuk keluar");
            input = scanner.nextByte();
            if (input == (warung1.getId()+2)){
                isRunning = false;
            } else if (input == ((warung1.getId()))) {
                person1.displayMoney();
            } else if ((warung1.getId()+1) == input) {
                person1.getInventori().displayInventori();
            } else if(input < warung1.getId() && input >= 0){
                System.out.println("Mau pesan berapa? tekan 0 untuk keluar");
                jumlah = scanner.nextInt();
                if (jumlah != 0){
                    penjualan1.beliProduk(input,jumlah,person1);
                }
            }
        }
    }
}