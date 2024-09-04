import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.kampuskuapp.DAO.DatabaseHelper
import com.example.kampuskuapp.DAO.MahasiswaDao
import com.example.kampuskuapp.R
import com.example.kampuskuapp.databinding.ActivityUpdateMahasiswaBinding
import com.example.kampuskuapp.model.Mahasiswa

class UpdateMahasiswaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateMahasiswaBinding
    private lateinit var mahasiswaDao: MahasiswaDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_mahasiswa)

        // Inisialisasi database dan DAO
        mahasiswaDao = MahasiswaDao(this)

        // Ambil data mahasiswa dari intent
        val nomor = intent.getStringExtra("nomor")
        val mahasiswa = nomor?.let { mahasiswaDao.getMahasiswaByNomor(it) }

        mahasiswa?.let {
            binding.etNim.setText(it.nomor)
            binding.etNama.setText(it.nama)
            binding.etAlamat.setText(it.alamat)
            binding.etJurusan.setText(it.jurusan)
        }

        binding.btnSimpan.setOnClickListener {
            val mahasiswaBaru = Mahasiswa(
                nomor = binding.etNim.text.toString(),
                nama = binding.etNama.text.toString(),
                alamat = binding.etAlamat.text.toString(),
                jurusan = binding.etJurusan.text.toString()
            )

            updateMahasiswa(mahasiswaBaru)
            finish()
        }
    }

    private fun updateMahasiswa(mahasiswa: Mahasiswa) {
        Thread {
            mahasiswaDao.update(mahasiswa)
        }.start()
    }
}
