package com.alawi.ahmed.covid19updates;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class symActivity extends AppCompatActivity {


    TextView symParTV1;
    TextView symParTV2;
    TextView symParTV3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sym);


        symParTV1 = (TextView)findViewById(R.id.symParTV1);
        symParTV2 = (TextView)findViewById(R.id.symParTV2);
        symParTV3 = (TextView)findViewById(R.id.symParTV3);


        symParTV1.setText("COVID-19 is a respiratory condition caused by a coronavirus. Some people are infected but don’t notice any symptoms. Most people will have mild symptoms and get better on their own. But about 1 in 6 will have severe problems, such as trouble breathing. The odds of more serious symptoms are higher if you’re older or have another health condition like diabetes or heart disease.\n\nHere’s what to look for if you think you might have COVID-19.\n" +
                "\n");
        symParTV1.setMovementMethod(new ScrollingMovementMethod());


        symParTV2.setText("Researchers in China found that the most common symptoms among people who were hospitalized with COVID-19 include:\n" +
                "\n" +
                "Fever: 99%\n" +
                "Fatigue:70%\n" +
                "A dry cough: 59%\n" +
                "Loss of appetite: 40%\n" +
                "Body aches: 35%\n" +
                "Shortness of breath: 31%\n" +
                "Mucus or phlegm: 27%\n" +
                "Symptoms usually begin 2 to 14 days after you come into contact with the virus.\n" +
                "\n" +
                "Other symptoms may include:\n" +
                "\n" +
                "Sore throat\n" +
                "Headache\n" +
                "Chills, sometimes with shaking\n" +
                "Loss of smell or taste\n" +
                "Stuffy nose\n" +
                "Nausea or vomiting\n" +
                "Diarrhea\n");
        symParTV2.setMovementMethod(new ScrollingMovementMethod());



        symParTV3.setText("Call a doctor or hospital right away if you have one or more of these COVID-19 symptoms:\n" +
                "\n" +
                "Trouble breathing\n" +
                "Constant pain or pressure in your chest\n" +
                "Bluish lips or face\n" +
                "Sudden confusion\n" +
                "You need medical care as soon as possible. Call your doctor’s office or hospital before you go in. This will help them prepare to treat you and protect medical staff and other patients.\n" +
                "\n" +
                "Strokes have also been reported in some people who have COVID-19. Remember FAST:\n" +
                "\n" +
                "Face. Is one side of the person’s face numb or drooping? Is their smile lopsided?\n" +
                "Arms. Is one arm weak or numb? If they try to raise both arms, does one arm sag?\n" +
                "Speech. Can they speak clearly? Ask them to repeat a sentence.\n" +
                "Time. Every minute counts when someone shows signs of a stroke. Call 911 right away.\n" +
                "Lab tests can tell if COVID-19 is what’s causing your symptoms. But the tests can be hard to find, and there’s no treatment if you do have the disease. So you don’t need to get tested if you have no symptoms or only mild ones. Call your doctor or your local health department if you have questions.");
        symParTV3.setMovementMethod(new ScrollingMovementMethod());

    }
}
