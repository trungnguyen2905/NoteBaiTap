package com.example.notebaitap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Database database;
    ListView lvNoiDung;
    ArrayList<NoiDung> arrayNoiDung;
    NoiDungAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lvNoiDung = (ListView) findViewById(R.id.listviewNoiDung);
        arrayNoiDung = new ArrayList<NoiDung>();

        adapter = new NoiDungAdapter(this,R.layout.dong_noi_dung,arrayNoiDung);
        lvNoiDung.setAdapter(adapter);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        //tao database ghi chu
        database = new Database(this, "note.sqlite", null, 1);
        // tao bang Noi dung
        database.QueryData("CREATE TABLE IF NOT EXISTS NoiDung(Id INTEGER PRIMARY KEY AUTOINCREMENT,TenTD VARCHAR(200),CurrentDate Date)");



        // insert  data
        database.QueryData("INSERT INTO NoiDung VALUES(null,'Hom qua la thu 2','"+ currentDate+"')");

        //select data
        getDataNoiDung();
    }

    private void DialogThem(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_noi_dung);

        final EditText edtTen = dialog.findViewById(R.id.editTextTenTD);
        Button btnThem = dialog.findViewById(R.id.btnThem);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenND = edtTen.getText().toString();
//                Calendar calendar = Calendar.getInstance();
//                String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
//                edtTen.setText(currentDate);
//                String dateCurrent = edtTen.getText().toString();
                if(tenND.equals("")){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập nội dung!", Toast.LENGTH_SHORT).show();
                }else
                {
                    database.QueryData("INSERT INTO NoiDung VALUES(null,'"+ tenND +"')");
                    Toast.makeText(MainActivity.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    getDataNoiDung();
                }
            }
        });

        dialog.show();

    }

    public void DialogXoaNoiDung(final String tennd, final int id){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa nội dung " +tennd+" không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QueryData("DELETE FROM NoiDung WHERE Id = '"+id+"'");
                Toast.makeText(MainActivity.this, "Đã xóa "+tennd, Toast.LENGTH_SHORT).show();
                getDataNoiDung();

            }
        });
        dialogXoa.setNegativeButton("không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();

    }

    public void DialogSuaNoiDung(String ten, final int id){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sua);

        final EditText edtTenND = (EditText) dialog.findViewById(R.id.editTextTenTDEdit);
        Button btnXacNhan = (Button) dialog.findViewById(R.id.btnXacNhan);
        final Button btnHuyEdt = (Button) dialog.findViewById(R.id.btnHuyEdit);

        edtTenND.setText(ten);
//        Calendar calendar = Calendar.getInstance();
//        currentDate = DateFormat.getDateInstance().format(calendar.getTime());
//        edtTenND.setText(currentDate);


        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenMoi = edtTenND.getText().toString().trim();
                String dateMoi = edtTenND.getText().toString().trim();
                database.QueryData("UPDATE NoiDung SET TenTD = '"+ tenMoi +"' WHERE Id = '" +id+ "' " );
                Toast.makeText(MainActivity.this, "Da cap nhat", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                getDataNoiDung();

            }
        });

        btnHuyEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void getDataNoiDung(){
        Cursor dataNoiDung = database.GetData("SELECT * FROM NoiDung");
        arrayNoiDung.clear();
        while (dataNoiDung.moveToNext()){
            String ten = dataNoiDung.getString(1);
            int id = dataNoiDung.getInt(0);
            arrayNoiDung.add(new NoiDung(id, ten));
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_noidung, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.menuAdd){
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }


}
