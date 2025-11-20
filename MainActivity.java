package com.example.a112360122;

// 1. 補上所有必要的 import
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // !! 修正 1: 必須先設定 ContentView (載入 XML 畫面)
        setContentView(R.layout.activity_main);

        // !! 修正 2: 接著才能透過 findViewById 找到畫面上的元件
        Button btn = findViewById(R.id.button); // 連結 Button 元件

        // (這是 Android 12+ 的全螢幕/邊緣到邊緣設定，保持原樣)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // !! 修正 3: 將按鈕的點擊事件監聽器放在正確的位置
        btn.setOnClickListener(new View.OnClickListener() { // Button 點擊事件
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("請選擇功能");
                dialog.setMessage("請根據下方按鈕選擇要顯示的物件");

                dialog.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Dialog關閉", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.setNegativeButton("自定義Toast", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showToast(); // 呼叫下面的 showToast() 方法
                    }
                });

                dialog.setPositiveButton("顯示List", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showListDialog(); // 呼叫下面的 showListDialog() 方法
                    }
                });
                dialog.show();
            }
        }); // setOnClickListener 結束
    } // onCreate 結束

    // !! 修正 4: 補上您在上一張圖片中提供的 showToast() 方法
    private void showToast() {
        Toast toast = new Toast(MainActivity.this);
        toast.setGravity(Gravity.TOP, 0, 50);
        toast.setDuration(Toast.LENGTH_SHORT);
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_root));
        toast.setView(layout);
        toast.show();
    }

    // !! 修正 5: 補上 showListDialog() 方法的 "範例" (您尚未提供此程式碼)
    private void showListDialog() {
        final String[] list = {"message1", "message2", "message3", "message4", "message5"};
        AlertDialog.Builder dialog_list = new AlertDialog.Builder(MainActivity.this);
        dialog_list.setTitle("使用LIST呈現");
        dialog_list.setItems(list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "你選得是" + list[i], Toast.LENGTH_SHORT).show();
            }
        });
        dialog_list.show();
    }
}