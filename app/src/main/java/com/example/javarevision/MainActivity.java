package com.example.javarevision;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Optional;


public class MainActivity<personDao> extends AppCompatActivity {

    private Button addBtn, clearBtn, mixBtn;
    private EditText firstText, lastText, yearOfBirthText;
    private CheckBox genderCheckBoxMale, genderCheckBoxFemale;
    private TextView listPerson,olderPerson;
    private String sexe=null;
    private static Dao<Person> personDao;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindingUIWidgets();
        personDao = new PersonDao();
        btnState();

        /*
         *to select only one checkbox
         * genderCheckBoxMale or genderCheckBoxFemale
         * */
        genderCheckBoxMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(genderCheckBoxMale.isChecked()){
                    genderCheckBoxFemale.setChecked(false);
                    genderCheckBoxMale.setChecked(true);
                    sexe="M";
                }


            }
        });

        genderCheckBoxFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(genderCheckBoxFemale.isChecked()){
                    genderCheckBoxFemale.setChecked(true);
                    genderCheckBoxMale.setChecked(false);
                    sexe="F";
                }
            }
        });
    }

    private void bindingUIWidgets() {
        addBtn = findViewById(R.id.add_btn);
        clearBtn = findViewById(R.id.clear_btn);
        mixBtn = findViewById(R.id.mix_btn);


        listPerson=findViewById(R.id.listPerson);
        olderPerson=findViewById(R.id.older_Person);

        firstText = findViewById(R.id.first_edit_text);
        lastText = findViewById(R.id.last_edit_text);
        genderCheckBoxFemale = findViewById(R.id.gender_female_chkbox);
        genderCheckBoxMale = findViewById(R.id.gender_male_chkbox);
        yearOfBirthText = findViewById(R.id.yob_edit_text);

    }

    private CheckBox readGenderCheckBox() {
        if(genderCheckBoxMale.isChecked()){
            return genderCheckBoxMale;
        }
        if(genderCheckBoxFemale.isChecked()){
            return genderCheckBoxFemale;
        }
        return null;
    }

    public void addForm(View view) {
        if(validatingFormEntriesCheck()){

          personDao.save(new Person(firstText.getText().toString(),
                   lastText.getText().toString(),
                   convertEditTextToInteger(yearOfBirthText),sexe));
        }
        popUpMessage(firstText, lastText);
      //  mixForm( view);
        clearFormulaire(view);
        btnState();

    }

    private void popUpMessage(EditText editText1, EditText editText2) {



        Toast.makeText(this,"Save with succes!",Toast.LENGTH_SHORT).show();
    }

    private int convertEditTextToInteger(EditText year) {
        return Integer.valueOf(year.getText().toString());
    }

    /*
    *   Data is valid if and only if there is at least either a firstname
    *   or a lastname otherwise validation is rejected
    * */
    private boolean validatingFormEntriesCheck() {
        return isValidatedData();
    }

    private boolean isValidatedData() {
        boolean validatedData = false;
        if(!firstText.getText().toString().isEmpty()){
            validatedData = true;
        }else if(!lastText.getText().toString().isEmpty()){
            validatedData = true;
        }else if(readGenderCheckBox() != null){
            validatedData = true;
        }
        return validatedData;
    }


    // to clear the input data in the form
    public void clearFormulaire(View view) {
        firstText.setText("");
        lastText.setText("");
        genderCheckBoxFemale.setChecked(false);
        genderCheckBoxMale.setChecked(false);
        yearOfBirthText.setText("");

    }

   // @RequiresApi(api = Build.VERSION_CODES.N)
   // to clear the input data in Dao
    public void clearForm(View view){
        Toast.makeText(this,"All data delete with succes!",Toast.LENGTH_SHORT).show();
       personDao.deleteAllP();

        listPerson.setText("");
        olderPerson.setText("");
        btnState();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void mixForm(View view) {
        StringBuilder personStringBuilder = new StringBuilder();

        for (Person persons : personDao.getAll()) {
            personStringBuilder.append(persons.toString() + "\n");



        }
        listPerson.setText(personStringBuilder);

        showOlder();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showOlder(){
        Person p1 =getPerson(personDao.beforeLastIndex());
        Person p2 =getPerson(personDao.lastIndex());
        int qt =personDao.nbrePerson();

        if(qt<2){

            olderPerson.setText(p1.getName()+" "+p1.getEmail()+" is the unique value");
        }else{

            if(p1.getAge()>p2.getAge()){

                olderPerson.setText(" "+p2.getName()+ " "+p2.getEmail()+" is older than "+p1.getName()+" "+p1.getEmail());

            }else{
                olderPerson.setText(" "+p1.getName()+ " "+p1.getEmail()+" is older than "+p2.getName()+" "+p2.getEmail());
            }

        }


    }

    //------to show all registered persons


    private void btnState(){
        if(personDao.nbrePerson()<1){
            clearBtn.setEnabled(false);
            mixBtn.setEnabled(false);
        }else{
            clearBtn.setEnabled(true);
            mixBtn.setEnabled(true);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static Person getPerson(long id) {

	        Optional<Person> person = personDao.get(id);

	        return person.orElseGet(
	          () -> new Person("non-existing user", "no-email",0,""));
	    }




}