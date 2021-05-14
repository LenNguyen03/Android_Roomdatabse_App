package com.example.roomdatabase_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<Person> adapter;
    List<Person> personList = new ArrayList<>();
    Button btnAdd, btnUpdate, btnRemove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        btnAdd = findViewById(R.id.btnAdd);
        btnRemove = findViewById(R.id.btnRemove);
        btnUpdate = findViewById(R.id.btnUpdate);
        EditText editText = findViewById(R.id.editText);
        SearchView searchView = findViewById(R.id.searchBar);
//        Person person = new Person(1,"Nguyen Van Len");
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "Person_RoomDatabase")
                .allowMainThreadQueries()
                .build();


        PersonDao personDao = db.personDao();
//        personDao.insertAll(person);
        personList = personDao.getAll();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, personList);
        listView.setAdapter(adapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0, count = personList.size();
                String name = editText.getText().toString();
                for (int i = 0; i < personList.size(); i++) {
                    if (personList.get(i).getPerson() != name && editText.length() != 0) {
                        Person p = new Person(count + 1, name);
                        personDao.insertAll(p);
                        adapter.add(p);
                        Toast.makeText(MainActivity.this, "Add person is successfull !", Toast.LENGTH_SHORT).show();
                        flag++;
                        break;
                    }
                    else
                        flag = 0;
                }
                if(flag == 0)
                    Toast.makeText(MainActivity.this, "Person is duplicate !", Toast.LENGTH_SHORT).show();
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0;
                String name = editText.getText().toString();
                Person person = null;
                for (int i = 0; i < personList.size(); i++) {
                    if (personList.get(i).getPerson().equals(name)) {
                        person = personList.get(i);
                        personDao.delete(person);
                        personList.remove(i);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Remove person is successfull !", Toast.LENGTH_SHORT).show();
                        flag++;
                        break;
                    } else
                        flag = 0;
                }
                if (flag == 0)
                    Toast.makeText(MainActivity.this, "Invalid person to remove !", Toast.LENGTH_SHORT).show();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                MainActivity.this.adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                MainActivity.this.adapter.getFilter().filter(newText);
                return false;
            }
        });
    }



}
