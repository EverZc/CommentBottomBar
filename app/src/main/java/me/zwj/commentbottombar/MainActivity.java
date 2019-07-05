package me.zwj.commentbottombar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import me.pandazhang.filepicker.FilePicker;
import me.pandazhang.filepicker.activity.ImagePickActivityPicker;

public class MainActivity extends AppCompatActivity {
    public static final String IS_NEED_CAMERA = "IsNeedCamera";
    public static final int REQUEST_CODE_PICK_IMAGE = 1;
    private Button mButtom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtom=findViewById(R.id.buttom);
        mButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ImagePickActivityPicker.class);
                intent.putExtra(IS_NEED_CAMERA, true);
                int maxNumber = 3;
                intent.putExtra(FilePicker.MAX_NUMBER, maxNumber);
                startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
            }
        });
    }
}
