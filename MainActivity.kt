package com.example.kalkulator // <-- GANTI DENGAN PACKAGE NAME ANDA

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder // <-- Ini akan error sementara

class MainActivity : AppCompatActivity() {

    // Deklarasi variabel untuk TextView yang menampilkan hasil
    private lateinit var tvResult: TextView
    private var lastNumeric: Boolean = false
    private var stateError: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Menghubungkan variabel tvResult dengan TextView di layout
        tvResult = findViewById(R.id.tvResult)

        // Set listener untuk tombol angka
        findViewById<Button>(R.id.btn0).setOnClickListener { onDigit(it as Button) }
        findViewById<Button>(R.id.btn1).setOnClickListener { onDigit(it as Button) }
        findViewById<Button>(R.id.btn2).setOnClickListener { onDigit(it as Button) }
        findViewById<Button>(R.id.btn3).setOnClickListener { onDigit(it as Button) }
        findViewById<Button>(R.id.btn4).setOnClickListener { onDigit(it as Button) }
        findViewById<Button>(R.id.btn5).setOnClickListener { onDigit(it as Button) }
        findViewById<Button>(R.id.btn6).setOnClickListener { onDigit(it as Button) }
        findViewById<Button>(R.id.btn7).setOnClickListener { onDigit(it as Button) }
        findViewById<Button>(R.id.btn8).setOnClickListener { onDigit(it as Button) }
        findViewById<Button>(R.id.btn9).setOnClickListener { onDigit(it as Button) }

        // Set listener untuk tombol operator
        findViewById<Button>(R.id.btnAdd).setOnClickListener { onOperator(it as Button) }
        findViewById<Button>(R.id.btnSubtract).setOnClickListener { onOperator(it as Button) }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { onOperator(it as Button) }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { onOperator(it as Button) }

        // Set listener untuk tombol clear dan equals
        findViewById<Button>(R.id.btnClear).setOnClickListener { onClear() }
        findViewById<Button>(R.id.btnEquals).setOnClickListener { onEqual() }
    }

    /**
     * Fungsi yang dipanggil saat tombol angka ditekan
     */
    private fun onDigit(view: Button) {
        if (stateError) {
            // Jika sebelumnya ada error, ganti teksnya dengan angka baru
            tvResult.text = view.text
            stateError = false
        } else {
            // Jika tidak, tambahkan angka ke teks yang sudah ada
            tvResult.append(view.text)
        }
        // Set flag bahwa input terakhir adalah numerik
        lastNumeric = true
    }

    /**
     * Fungsi yang dipanggil saat tombol operator ditekan
     */
    private fun onOperator(view: Button) {
        // Hanya tambahkan operator jika input terakhir adalah angka
        if (lastNumeric && !stateError) {
            tvResult.append(view.text)
            lastNumeric = false
            lastDot = false // Reset flag titik desimal
        }
    }

    /**
     * Fungsi untuk membersihkan layar
     */
    private fun onClear() {
        this.tvResult.text = "0"
        lastNumeric = false
        stateError = false
        lastDot = false
    }

    /**
     * Fungsi untuk menghitung hasil
     */
    private fun onEqual() {
        // Lakukan perhitungan hanya jika input terakhir adalah angka
        if (lastNumeric && !stateError) {
            val txt = tvResult.text.toString()
            try {
                // Gunakan library exp4j untuk evaluasi ekspresi matematika
                val expression = ExpressionBuilder(txt).build()
                val result = expression.evaluate()
                tvResult.text = result.toString()
                lastDot = true // Hasil bisa jadi desimal
            } catch (ex: ArithmeticException) {
                // Tangani error pembagian dengan nol
                tvResult.text = "Error"
                stateError = true
                lastNumeric = false
            }
        }
    }
}