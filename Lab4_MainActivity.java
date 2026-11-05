package com.example.lab4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Step1：宣告 ActivityResultLauncher。
    // 內部負責處理 SecActivity 回傳結果
    private final ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // Step12：判斷回傳結果是否為 RESULT_OK，若是則執行以下程式碼
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Step13：取得回傳的 Intent，並從 Intent 中取得飲料名稱、甜度、冰塊的值
                        Intent intent = result.getData();
                        if (intent != null) {
                            String drink = intent.getStringExtra("drink");
                            String sugar = intent.getStringExtra("sugar");
                            String ice = intent.getStringExtra("ice");
                            // Step14：設定 tvMeal 的文字
                            TextView tvMeal = findViewById(R.id.tvMeal);
                            tvMeal.setText("飲料：" + drink + "\n\n甜度：" + sugar + "\n\n冰塊：" + ice);
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Step2：定義元件變數，並通過 findViewById 取得元件
        Button btnChoice = findViewById(R.id.btnChoice);
        // Step3：設定 btnChoice 的點擊事件
        btnChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Step4：宣告 Intent，透過 Intent 從 MainActivity 切換到 SecActivity
                Intent intent = new Intent(MainActivity.this, SecActivity.class);
                // Step5：使用 startForResult 啟動 Intent
                startForResult.launch(intent);
            }
        });
    }
}
