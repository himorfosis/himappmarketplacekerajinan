package himorfosis.kerajinanaceh;

public class Koneksi {

    public static final String URL = "http://192.168.137.47/";

    // akun
    public static final String Login= URL + "jualkerajinan/api/Api.php?apicall=login";
    public static final String Register = URL + "jualkerajinan/api/Api.php?apicall=signup";
    public static final String Pelanggan = URL + "jualkerajinan/api/pelangganApi.php";
    public static final String updateProfil = URL + "jualkerajinan/api/profilUpdate.php";

    // get api
    public static final String ProdukApi = URL + "jualkerajinan/api/produkApi.php";
    public static final String kategoriApi = URL + "jualkerajinan/api/kategoriApi.php";
    public static final String pembayaranApi = URL + "jualkerajinan/api/pembayaranApi.php";
    public static final String bankApi = URL + "jualkerajinan/api/pembayaranTambah.php";
    public static final String pemesananApi = URL + "jualkerajinan/api/pemesananApi.php";
    public static final String alamatApi = URL + "jualkerajinan/api/alamatApi.php";


    // tambah data
    public static final String pembayaranTambah = URL + "jualkerajinan/api/pembayaranTambah.php";
    public static final String alamatTambah = URL + "jualkerajinan/api/alamatTambah.php";
    public static final String pemesananTambah = URL + "jualkerajinan/api/pemesananTambah.php";

    // get gambar
    public static final String getImage = URL + "jualkerajinan/uploaded/produk/";

    // upload konfirmasi pembayaran
    public static final String UPLOAD_BUKTI_PEMBAYARAN = URL + "jualkerajinan/api/buktiPembayaran.php";
    public static final String konfirmasiPembayaran = URL + "jualkerajinan/api/konfirmasiPembayaran.php";

    //Ongkos kirim
    public static final String provinsiPenerima = URL + "jualkerajinan/api/provPenerima.php";
    public static final String kabupatenPenerima = URL + "jualkerajinan/api/kabPenerima.php";
    public static final String biayaOngkir = URL + "jualkerajinan/api/biayaOngkir.php";


}
