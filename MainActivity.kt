package com.example.lab4

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
// 引入您的專案中自動產生的 ViewBinding 類別
import com.example.lab4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // 建議將用於 Intent 的 Key 定義為常數，以避免打錯字。
    // 更好的做法是將它們定義在 SecActivity 的 companion object 中。
    companion object {
        const val KEY_DRINK = "drink"
        const val KEY_SUGAR = "sugar"
        const val KEY_ICE = "ice"
    }

    // 宣告一個 lateinit 變數來存放 ViewBinding 物件。
    // 這將取代所有 findViewById 的呼叫。
    private lateinit var binding: ActivityMainBinding

    // 註冊一個 ActivityResultLauncher 來處理從 SecActivity 回傳的結果。
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // 判斷回傳的結果碼是否為 OK。
        if (result.resultCode == Activity.RESULT_OK) {
            // 使用 'let' 作用域函式來安全地處理可能為 null 的 intent。
            result.data?.let { intent ->
                // 從 intent 中取得資料，並使用 Elvis 運算子 (?:) 提供預設值以避免 null。
                val drink = intent.getStringExtra(KEY_DRINK) ?: "未提供"
                val sugar = intent.getStringExtra(KEY_SUGAR) ?: "未提供"
                val ice = intent.getStringExtra(KEY_ICE) ?: "未提供"

                // 透過 ViewBinding 直接存取 TextView，並使用字串模板更新 UI。
                binding.tvMeal.text = "飲料：$drink\n\n甜度：$sugar\n\n冰塊：$ice"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 使用 ViewBinding 來載入並設定畫面佈局。
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 設定 WindowInsets 以適配全螢幕 (Edge-to-Edge) 顯示。
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 透過 ViewBinding 設定按鈕的點擊監聽器。
        binding.btnChoice.setOnClickListener {
            // 建立 Intent 以啟動 SecActivity。
            val intent = Intent(this, SecActivity::class.java)
            // 啟動 Activity 並等待結果回傳。
            startForResult.launch(intent)
        }
    }
}
