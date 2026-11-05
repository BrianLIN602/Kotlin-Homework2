package com.example.lab6;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

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

        // 定義元件變數，並通過 findViewById 取得元件
        Button btnToast = findViewById(R.id.btnToast);
        Button btnSnackBar = findViewById(R.id.btnSnackBar);
        Button btnDialog1 = findViewById(R.id.btnDialog1);
        Button btnDialog2 = findViewById(R.id.btnDialog2);
        Button btnDialog3 = findViewById(R.id.btnDialog3);

        // 建立要顯示在列表上的字串陣列
        final String[] item = {"選項 1", "選項 2", "選項 3", "選項 4", "選項 5"};

        // 設定按鈕的點擊事件
        btnToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 呼叫 showToast 方法，顯示 Toast 訊息
                showToast("預設 Toast");
            }
        });

        btnSnackBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 建立 Snackbar 物件，並顯示 Snackbar 訊息
                Snackbar.make(v, "按鈕式 Snackbar", Snackbar.LENGTH_SHORT)
                        // 設定 Snackbar 按鈕的文字與點擊事件
                        .setAction("按鈕", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                showToast("已回應");
                            }
                        }).show();
            }
        });

        btnDialog1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 建立 AlertDialog 物件
                new AlertDialog.Builder(MainActivity.this)
                        // 設定標題
                        .setTitle("按鈕式 AlertDialog")
                        // 設定內容
                        .setMessage("AlertDialog 內容")
                        // 設定按鈕文字與點擊事件
                        .setNeutralButton("左按鈕", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                showToast("左按鈕");
                            }
                        })
                        .setNegativeButton("中按鈕", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                showToast("中按鈕");
                            }
                        })
                        .setPositiveButton("右按鈕", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                showToast("右按鈕");
                            }
                        }).show();
            }
        });

        btnDialog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 建立 AlertDialog 物件
                new AlertDialog.Builder(MainActivity.this)
                        // 設定標題
                        .setTitle("列表式 AlertDialog")
                        // 設定列表項目及點擊事件
                        .setItems(item, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // 顯示 Toast 訊息
                                showToast("你選的是" + item[i]);
                            }
                        }).show();
            }
        });

        btnDialog3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 宣告陣列 position 用來記錄選擇的項目（Java 匿名內部類別需為 final 或陣列）
                final int[] position = {0};
                // 建立 AlertDialog 物件
                new AlertDialog.Builder(MainActivity.this)
                        // 設定標題
                        .setTitle("單選式 AlertDialog")
                        // 設定列表項目及點擊事件，預設選擇第一個項目
                        .setSingleChoiceItems(item, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // 更新 position 的值
                                position[0] = i;
                            }
                        })
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 顯示 Toast 訊息
                                showToast("你選的是" + item[position[0]]);
                            }
                        }).show();
            }
        });
    }

    // 建立 showToast 方法，顯示 Toast 訊息
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
