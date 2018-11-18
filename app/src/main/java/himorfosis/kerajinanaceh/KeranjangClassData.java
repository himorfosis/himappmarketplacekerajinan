package himorfosis.kerajinanaceh;

public class KeranjangClassData {


    private Integer id;
    private String idproduk;
    private String nama_produk;
    private String harga_produk;
    private String gambar_produk;
    private String jumlah;

    KeranjangClassData (Integer id, String idproduk, String nama_produk, String harga_produk, String gambar_produk, String jumlah  ) {

        super();
        this.id = id;
        this.idproduk = idproduk;
        this.nama_produk = nama_produk;
        this.harga_produk = harga_produk;
        this.gambar_produk = gambar_produk;
        this.jumlah = jumlah;

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public String getHarga_produk() {
        return harga_produk;
    }

    public void setHarga_produk(String harga_produk) {
        this.harga_produk = harga_produk;
    }

    public String getGambar_produk() {
        return gambar_produk;
    }

    public void setGambar_produk(String gambar_produk) {
        this.gambar_produk = gambar_produk;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getIdproduk() {
        return idproduk;
    }

    public void setIdproduk(String idproduk) {
        this.idproduk = idproduk;
    }
}
