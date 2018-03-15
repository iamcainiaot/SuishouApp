package com.example.geren;

import com.example.expresssender.R;
import com.example.expresssender.Geren;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class alter_name extends Activity implements OnClickListener {

	private ImageButton alter_name_back;
	private EditText edit_name;
	private Button alter_name_save;
	public static boolean isSaved = false;

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.alter_name);
		Init();
	}

	private void Init() {
		context = alter_name.this;
		alter_name_back = (ImageButton) findViewById(R.id.alter_name_back);
		alter_name_back.setOnClickListener(this);
		edit_name = (EditText) findViewById(R.id.edit_name);
		alter_name_save = (Button) findViewById(R.id.alter_name_save);
		alter_name_save.setOnClickListener(this);
	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.alter_name_back:

			/*
			 * // 跳转到上一界面 Intent intent1 = new Intent(alter_name.this,
			 * wode_info.class); Log.d("跳转跳转跳转", "从修改昵称跳转到个人资料");
			 * Toast.makeText(alter_name.this, "从修改昵称跳转到个人资料",
			 * Toast.LENGTH_SHORT) .show(); startActivity(intent1);
			 */

			// 2017-7-20-11:16 for test
			// 如果按下保存按钮则将新昵称传回并更新，否则返回但不更新
			Log.d("跳转跳转跳转", "从修改昵称跳转到个人资料");
			Intent intent1 = new Intent(context, wode_info.class);
			// intent1.putExtra("altered_name", edit_name.getText().toString());
			setResult(RESULT_OK, intent1);
			finish();
			// 2017-7-20-11:16 for test
			break;
		case R.id.alter_name_save:
			isSaved = true;
			// 若按下保存按钮则更新昵称
			if (isSaved) {
				Geren.show_name.setText("昵称:" + edit_name.getText().toString());
				Toast.makeText(context, "昵称修改成功", Toast.LENGTH_SHORT).show();
				// intent1.putExtra("altered_name", );
			}
			/*
			 * Toast.makeText(context, edit_name.getText().toString(),
			 * Toast.LENGTH_SHORT).show();
			 */
			break;
		default:
			break;
		}

	}

}
