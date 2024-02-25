package org.example;

import java.util.Scanner;


class Restoran{
    private final Makanan[] makanan;

    private byte id = 0;

    public Restoran(){
        makanan = new Makanan[10];
    }

    public void tampilMenuMakanan(){
        for(int i = 0; i<id;i++){
            if(!isOutOfStock(i)){
                System.out.println("["+i+"]"+this.makanan[i].getNama_makanan()+"["+this.makanan[i].getStok()+"]"+"\tRp. "+this.makanan[i].getHarga_makanan());
            }
        }
    }

    void tambahMenuMakanan(Makanan[] makanan){
        for(int i = 0;i < makanan.length;i++){
            this.makanan[id] = makanan[id];
            nextId();
        }
    }

    void pesanMakanan(byte id, int amount){
        if(this.makanan[id].getStok() >= amount){
            this.makanan[id].setStok(makanan[id].getStok() - amount);
            System.out.println("Anda berhasil memesan "+ this.makanan[id].getNama_makanan()+" sebanyak "+amount);
        }else {
            System.out.println("Maaf stok tidak cukup untuk memenuhi pesanan anda, stok yang tersisa dari " + this.makanan[id].getNama_makanan() + " sebanyak " + this.makanan[id].getStok());
        }
    }

    public boolean isOutOfStock(int id){
        return this.makanan[id].getStok() == 0;
    }

    public void nextId(){
        id++;
    }

    public Makanan[] getMakanan() {
        return makanan;
    }

    public byte getId() {
        return id;
    }
}



class Makanan extends Restoran{

    private int stok;
    private final double harga_makanan;
    private final String nama_makanan;

    Makanan(String nama, double harga, int stok){
        this.nama_makanan = nama;
        this.harga_makanan = harga;
        this.stok = stok;
    }

    public String getNama_makanan() {
        return nama_makanan;
    }

    public int getStok() {
        return stok;
    }

    public double getHarga_makanan() {
        return harga_makanan;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }


}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        byte input; int jumlah;
        Makanan[] menu1 = new Makanan[4];
        menu1[0] = new Makanan("Bala-Bala",1_000,20);
        menu1[1] = new Makanan("Gehu",2_500,10);
        menu1[2] = new Makanan("Tahu",1_500,1);
        menu1[3] = new Makanan("Molen",3_000,12);
        Restoran restoran1 = new Restoran();
        restoran1.tambahMenuMakanan(menu1);

        boolean isRunning = true;
        while(isRunning){
            restoran1.tampilMenuMakanan();
            System.out.println("Mau pesan apa? tekan "+ (restoran1.getId()+1) + " untuk keluar");
            input = scanner.nextByte();
            if (input == (restoran1.getId()+1)){
                isRunning = false;
            }else {
                System.out.println("Mau pesan berapa? tekan 0 untuk keluar");
                jumlah = scanner.nextInt();
                if (jumlah != 0){
                    restoran1.pesanMakanan(input, jumlah);
                }
            }
        }
    }
}