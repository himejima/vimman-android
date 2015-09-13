package com.example.himejima.vimman;

import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.content.Intent;

public class ResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        Set<String> set = bundle.keySet();
        Boolean result = (Boolean) bundle.get("result");
        // Log.d("aaa", result.toString());

//        for (String entry : set) {
//            Log.d("ss", entry);
//            String val = (String)bundle.get(entry);
//            Log.d("aa", val);
//        }
        String answer_result;
        if (result) {
            answer_result = "o 正解です！";
        } else {
            answer_result = "x 残念、不正解です！";
        }

        TextView result_text = (TextView) findViewById(R.id.textView4);
        result_text.setText(answer_result);

        Button btn_continue = (Button) findViewById(R.id.button4);
        Button btn_end = (Button) findViewById(R.id.button5);


        btn_end.setOnClickListener(new ButtonClickListener());
        btn_continue.setOnClickListener(new ButtonClickListener());
    }

    class ButtonClickListener implements View.OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button4:
                    Log.v("onClick: ", "continue");
                    //出題画面へ遷移
                    // finish();
                    Intent intent2 = new Intent(getApplication(), QuestionActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.button5:
                    Log.v("onClick: ", "end");
//                    // ホーム画面へ遷移する
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
