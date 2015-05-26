package com.example.brolin.hangdroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class GameActivity extends ActionBarActivity {

    String mWord;
    int mFailCounter = 0;
    int mGuessed = 0;
    int mPoints =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRandomWord();
    }

    /**
     * Recibe la letra introducido
     * @param v (Boton Pulsado)
     */
    public void checkLetter(View v){
        EditText myEditText = (EditText) findViewById(R.id.editLetter);
        String letter = myEditText.getText().toString();

        myEditText.setText("");

        if (letter.length()==1){
            searchLetter(letter.toUpperCase());
        }else{
            Toast.makeText(this,"Please Introduce a Letter",Toast.LENGTH_SHORT).show();
        }
    }

    public void searchLetter (String theletter){
        char introduced = theletter.charAt(0);
        boolean acerto = false;

        for (int i=0; i<mWord.length();i++){
            if(mWord.charAt(i)==introduced){
                acerto = true;
                mGuessed++;
                mPoints++;
                showLetterPosition(i,introduced);
            }
        }

        if (acerto==false){
            letterFailed(Character.toString(introduced));
        }

        if (mWord.length()==mGuessed){
            clearScreen();
        }
    }

    public void clearScreen(){
        TextView textViewFailed = (TextView) findViewById(R.id.wrongLetters);
        textViewFailed.setText("");
        mGuessed =0;
        mFailCounter=0;

        ImageView myimageview = (ImageView) findViewById(R.id.imageView);
        myimageview.setImageResource(R.drawable.hangdroid_0);

        setRandomWord();
    }

    public void setRandomWord(){
        String words = "ARSENAL-LIVERPOOL-MANCHESTER UNITED-MANCHESTER CITY-CHELSEA-BARCELONA-REAL MADRID-BAYER MUNICH-INTER-SPORTING";
        String[] arraywords = words.split("-");

        int randomNumber = (int) (Math.random() * arraywords.length);
        String randomWord = arraywords[randomNumber];

        mWord = randomWord;
        Log.d("MyLog","The Letter is: "+mWord);

        TextView textoMostrar = (TextView) findViewById(R.id.textoMostrar);
        textoMostrar.setText("");
        for (int i=0;i<mWord.length();i++){
            String textoprevio = textoMostrar.getText().toString();
            textoMostrar.setText(textoprevio+"-");
        }
    }

    public void letterFailed(String letterIntroduced){
        TextView textViewFailed = (TextView) findViewById(R.id.wrongLetters);
        String previousFail = textViewFailed.getText().toString();
        textViewFailed.setText(previousFail+" "+letterIntroduced);

        mFailCounter++;
        ImageView myimageview = (ImageView) findViewById(R.id.imageView);

        switch (mFailCounter){
            case 1: myimageview.setImageResource(R.drawable.hangdroid_1);
                break;
            case 2: myimageview.setImageResource(R.drawable.hangdroid_2);
                break;
            case 3: myimageview.setImageResource(R.drawable.hangdroid_3);
                break;
            case 4: myimageview.setImageResource(R.drawable.hangdroid_4);
                break;
            case 5: myimageview.setImageResource(R.drawable.hangdroid_5);
                break;
            case 6:
                //Toast.makeText(this,"GAME OVER",Toast.LENGTH_SHORT).show();
                Intent gameover = new Intent(this,GameOverActivity.class);
                gameover.putExtra("Points_var",mPoints);
                startActivity(gameover);
                finish();
                break;
        }

    }

    public void showLetterPosition(int posicion, char letra){
        TextView textoMostrar = (TextView) findViewById(R.id.textoMostrar);
        String textoprevio = textoMostrar.getText().toString();

        char[] myNameChars = textoprevio.toCharArray();
        myNameChars[posicion] = letra;
        textoprevio = String.valueOf(myNameChars);
        textoMostrar.setText(textoprevio);
    }

}
