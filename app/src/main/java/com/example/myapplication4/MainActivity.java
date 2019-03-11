package com.example.myapplication4;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private static final String FILE = "Results.txt";
    final String LOG_TAG = "myLogs";

    Button btnAdd, btnRead, btnClear, btnUpd, btnDel, btnSort;
    EditText etName, etEmail, etID, etSurname;
    RadioGroup rgSort;

    DBHelper dbHelper;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        btnUpd = (Button) findViewById(R.id.btnUpd);
        btnUpd.setOnClickListener(this);

        btnDel = (Button) findViewById(R.id.btnDel);
        btnDel.setOnClickListener(this);

        btnSort = (Button) findViewById(R.id.btnSort);
        btnSort.setOnClickListener(this);

        rgSort = (RadioGroup) findViewById(R.id.rgSort);

        etName = (EditText) findViewById(R.id.etName);
        etSurname = (EditText) findViewById(R.id.etSurname);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etID = (EditText) findViewById(R.id.etID);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);
    }

    public void onClick(View v) {

        // создаем объект для данных
        ContentValues cv = new ContentValues();

        // получаем данные из полей ввода
        String name = etName.getText().toString();
        String surname = etSurname.getText().toString();
        String email = etEmail.getText().toString();
        String id = etID.getText().toString();

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c = null;

        switch (v.getId()) {
            case R.id.btnAdd:
                Log.d(LOG_TAG, "--- Insert in mytable: ---");
                // подготовим данные для вставки в виде пар: имя поля - значение
                cv.put("name", name);
                cv.put("surname", surname);
                cv.put("email", email);
                // вставляем запись и получаем ее ID
                //Второй аргумент метода используется, при вставке в таблицу пустой строки (у нас null)
                //Метод insert возвращает ID вставленной строки, мы его сохраняем в rowID и выводим в лог
                long rowID = db.insert("mytable", null, cv);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                break;
            case R.id.btnRead:

                Log.d(LOG_TAG, "--- Rows in mytable: ---");
                // делаем запрос всех данных из таблицы mytable, получаем Cursor
                c = db.query("mytable", null, null, null, null, null, null);

                break;
            case R.id.btnClear:
                Log.d(LOG_TAG, "--- Clear mytable: ---");
                // удаляем все записи
                int clearCount = db.delete("mytable", null, null);
                Log.d(LOG_TAG, "deleted rows count = " + clearCount);
                break;
            case R.id.btnUpd:
                if (id.equalsIgnoreCase("")) {
                    break;
                }
                Log.d(LOG_TAG, "--- Update mytable: ---");
                // подготовим значения для обновления
                cv.put("name", name);
                cv.put("surname", surname);
                cv.put("email", email);
                // обновляем по id
                int updCount = db.update("mytable", cv, "id = ?",
                        new String[] { id });
                Log.d(LOG_TAG, "updated rows count = " + updCount);
                break;
            case R.id.btnDel:
                if (id.equalsIgnoreCase("")) {
                    break;
                }
                Log.d(LOG_TAG, "--- Delete from mytable: ---");
                // удаляем по id
                int delCount = db.delete("mytable", "id = " + id, null);
                Log.d(LOG_TAG, "deleted rows count = " + delCount);
                break;
                //сортировка
            case R.id.btnSort:
                String orderBy = null;
                // сортировка по
                switch (rgSort.getCheckedRadioButtonId()) {
                    case R.id.rName:
                        Log.d(LOG_TAG, "--- Сортировка по имени ---");
                        orderBy = "name";
                        break;
                    case R.id.rSurname:
                        Log.d(LOG_TAG, "--- Сортировка по фамилии ---");
                        orderBy = "surname";
                        break;
                    case R.id.rEmail:
                        Log.d(LOG_TAG, "--- Сортировка по email ---");
                        orderBy = "email";
                        break;
                }
                c = db.query("mytable", null, null, null, null, null, orderBy);
        }
        try {
            // ставим позицию курсора на первую строку выборки
            // если в выборке нет строк, вернется false
            if (c.moveToFirst()) {

                // определяем номера столбцов по имени в выборке
                int idColIndex = c.getColumnIndex("id");
                int nameColIndex = c.getColumnIndex("name");
                int surnameColIndex = c.getColumnIndex("surname");
                int emailColIndex = c.getColumnIndex("email");

                //Запись в файл
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(FILE,MODE_PRIVATE)));

                do {
                    // получаем значения по номерам столбцов и пишем все в лог
                    bw.write(
                            "ID = " + c.getInt(idColIndex) + ", name = "
                                    + c.getString(nameColIndex) + ", surname = "
                                    + c.getString(surnameColIndex) + ", email = "
                                    + c.getString(emailColIndex));
                    // переход на следующую строку
                    // а если следующей нет (текущая - последняя), то false -
                    // выходим из цикла
                    Log.d(LOG_TAG,
                            "ID = " + c.getInt(idColIndex) + ", name = "
                                    + c.getString(nameColIndex) + ", surname = "
                                    + c.getString(surnameColIndex) + ", email = "
                                    + c.getString(emailColIndex));
                } while (c.moveToNext());

                bw.close();
            } else
                Log.d(LOG_TAG, "0 rows");
            c.close();

            //Считывание из файла
            BufferedReader br = new BufferedReader( new InputStreamReader(openFileInput(FILE)));

            String str = "";

            while ((str = br.readLine()) != null) {
                Log.i("--- DB ---", str);
            }

            br.close();
        } catch (Exception e) {
            Log.i("--- DB ---",e.getMessage());
        }

        // закрываем подключение к БД
        dbHelper.close();
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            // конструктор суперкласса
            super(context, "myDB", null, 1);
        }

        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate database ---");
            // создаем таблицу с полями
            db.execSQL("create table mytable ("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "surname text,"
                    + "email text" + ");");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
