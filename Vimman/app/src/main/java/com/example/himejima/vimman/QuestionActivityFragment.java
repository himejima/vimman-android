package com.example.himejima.vimman;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Intent;

/**
 * A placeholder fragment containing a simple view.
 */
public class QuestionActivityFragment extends Fragment {
    TextView mQuestionText;
    Button btnSample;
    Button btnSubmit;
    EditText mAnswer;
    String[] answers;
    String question;

    public QuestionActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // return inflater.inflate(R.layout.fragment_question, container, false);
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        mQuestionText = (TextView) view.findViewById(R.id.textView3);
        btnSample = (Button) view.findViewById(R.id.button2);
        btnSubmit = (Button) view.findViewById(R.id.button3);
        mAnswer = (EditText) view.findViewById(R.id.editText);

        btnSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchQuestionTask().execute();
            }
        });
        btnSubmit.setVisibility(View.GONE);
        mAnswer.setVisibility(View.GONE);

        // 答え合わせ
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean rightFlag = false;
                Editable myAnswer = mAnswer.getText();
                Log.d("input answer", myAnswer.toString());
                for (String val : answers) {
                    Log.d("test", val);
                    if (myAnswer.toString().equals(val)) {
                        rightFlag = true;
                        break;
                    }
                }
                // TODO: 結果画面への遷移
                Log.d("result: ", rightFlag.toString());

                Intent intent = new Intent(getActivity(), ResultActivity.class);
                // intent.putExtra("result", rightFlag.toString());
                intent.putExtra("result", rightFlag);
                startActivity(intent);
            }
        });

        return view;
    }

    public class FetchQuestionTask extends AsyncTask<Void, Void, String[]> {

        private final String LOG_TAG = FetchQuestionTask.class.getSimpleName();

        private String[] getQuestionDataFromJson(String questionStr) throws JSONException {
            JSONObject questionJson = new JSONObject(questionStr);
            JSONArray questionArray = questionJson.getJSONArray("result");

            String[] resultStrs = new String[2];
            for (int i = 0; i < questionArray.length(); i++) {
                JSONObject test = questionArray.getJSONObject(i);
                String content = test.getString("content");

                // JSONObject answerObject = test.getJSONArray("answers").getJSONObject(0);
                // String answer = answerObject.getString("content");
                // Log.v(LOG_TAG, "Answer entry: " + answer);
                JSONArray answerArray = test.getJSONArray("answers");
                for (int j = 0; j < answerArray.length(); j++) {
                    JSONObject answerObject = answerArray.getJSONObject(j);
                    String answer = answerObject.getString("content");
                    // Log.v(LOG_TAG, "Answer Entry: " + answer);
                }

                resultStrs[i] = content;
            }

//            for (String s : resultStrs) {
//                Log.v(LOG_TAG, "Question entry: " + s);
//            }
            return resultStrs;
        }

        @Override
        protected String[] doInBackground(Void... params) {
            Log.d(LOG_TAG, "pass1");
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String questionJsonStr = null;

            try {
                URL url = new URL("http://192.168.52.101/api/questions/");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                Log.d(LOG_TAG, "pass2");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                questionJsonStr = buffer.toString();

                // Log.d(LOG_TAG, questionJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getQuestionDataFromJson(questionJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage());
                e.printStackTrace();;
            }

            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
//            Log.d(LOG_TAG, "pass3");
            // set question to view
            if (result != null) {
                for (String val : result) {
                    Log.d(LOG_TAG, "post: " + val);
                    // 問題をセットする
                }
            }
            btnSample.setVisibility(View.GONE);
            question = "Vimの問題です";
            answers = new String[2];
            answers[0] = "abc";
            answers[1] = "xyz";
            mQuestionText.setText(question);
            btnSubmit.setVisibility(View.VISIBLE);
            mAnswer.setVisibility(View.VISIBLE);
        }
    }
}
