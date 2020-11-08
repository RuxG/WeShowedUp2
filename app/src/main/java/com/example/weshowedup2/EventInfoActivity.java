package com.example.weshowedup2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private DatePickerDialog pickerData;
    private TimePickerDialog pickerHour;
    private EditText eTextData, eTextHour;
    private ImageView imageView;
    private Button button;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    private Button saveButton;
    private EditText eventTitleEditText;
    private EditText eventTypeEditText;
    private EditText descriptionEditText;
    private EditText locationEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);


        eventTitleEditText = findViewById(R.id.event_title);
        eventTypeEditText = findViewById(R.id.event_type);
        descriptionEditText = findViewById(R.id.event_descriere);
        locationEditText = findViewById(R.id.event_location);

        eTextData = (EditText) findViewById(R.id.data);
        eTextData.setInputType(InputType.TYPE_NULL);
        eTextData.setOnClickListener(this);

        eTextHour = (EditText) findViewById(R.id.hour);
        eTextHour.setInputType(InputType.TYPE_NULL);
        eTextHour.setOnClickListener(this);

        imageView = (ImageView)findViewById(R.id.imageView);
        button = (Button)findViewById(R.id.buttonLoadPicture);
        button.setOnClickListener(this);

        saveButton = findViewById(R.id.event_save_button);
        saveButton.setOnClickListener(this);

    }
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.data:
                saveDataPicker();
                break;
            case R.id.hour:
                saveTimePicker();
                break;
            case R.id.buttonLoadPicture:
                openGallery();
                break;
            case R.id.event_save_button:
                saveEvent();
                break;
        }
    }

    public void saveDataPicker() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        pickerData = new DatePickerDialog(EventInfoActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        eTextData.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        pickerData.show();
    }

    public void saveTimePicker() {
        final Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);
        // time picker dialog
        pickerHour = new TimePickerDialog(EventInfoActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        eTextHour.setText(sHour + ":" + sMinute);
                    }
                }, hour, minutes, true);
        pickerHour.show();
    }

    public void saveEvent() {
        DatabaseReference referinta = FirebaseDatabase.getInstance().getReference("Events");

        DatabaseReference newEventRef = referinta.push();

        String titleEveniment = eventTitleEditText.getText().toString().trim();
        String tipEveniment = eventTypeEditText.getText().toString().trim();
        String dataEveniment = eTextData.getText().toString().trim();
        String oraEveniment = eTextHour.getText().toString().trim();
        String locatieEveniment = locationEditText.getText().toString().trim();
        String organizatorEveniment = "Ion";
        String descriereEveniment = descriptionEditText.getText().toString().trim();


        newEventRef.setValue(new Event(titleEveniment,tipEveniment, dataEveniment, oraEveniment,
                locatieEveniment, organizatorEveniment, descriereEveniment));

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot uniqueUserSnapshot : snapshot.getChildren()) {
                    if (uniqueUserSnapshot.child("interese").child(tipEveniment).getValue() != null){
                        String key = uniqueUserSnapshot.getKey();
                        usersRef.child(key).child("evenimente_recomandate").child(newEventRef.getKey())
                        .setValue("true");
                        Toast.makeText(EventInfoActivity.this, "bravo boss mare fan", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}