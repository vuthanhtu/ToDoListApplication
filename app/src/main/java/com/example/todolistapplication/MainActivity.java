package com.example.todolistapplication;

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
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    ArrayList<CongViec> listCongViec;
    CongViecAdapter adapter;
    ListView listViewCV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewCV = (ListView)findViewById(R.id.lvCongViec);
        listCongViec = new ArrayList<>();
        adapter = new CongViecAdapter(this,R.layout.dong_cong_viec,listCongViec);
        listViewCV.setAdapter(adapter);
        databaseHelper = new DatabaseHelper(this,"ghichu.sqlite",null,1);
        databaseHelper.QueryData("CREATE TABLE IF NOT EXISTS CongViec2(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenCV VARCHAR(50));");
//        databaseHelper.QueryData("INSERT INTO CongViec2(TenCV) VALUES('Lam bai tap web')");
        getDataCongViec();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_cong_viec,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menuAdd){
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }
    private void getDataCongViec(){
        Cursor dataCongViec = databaseHelper.getData("SELECT * FROM CongViec2");
        listCongViec.clear();
        while(dataCongViec.moveToNext()){
            String ten = dataCongViec.getString(1);
            int id = dataCongViec.getInt(0);
            CongViec cv = new CongViec(id,ten);
            listCongViec.add(cv);

//            Toast.makeText(MainActivity.this,ten,Toast.LENGTH_LONG).show();
        }

        adapter.notifyDataSetChanged();
    }
    private void DialogThem(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // khong hien thi tieu de
        dialog.setContentView(R.layout.dialog_them_cong_viec);

        EditText edtThemCV = (EditText) dialog.findViewById(R.id.edtThemCongViec);
        Button btnThem = (Button) dialog.findViewById(R.id.btnThem);
        Button btnHuy = (Button) dialog.findViewById(R.id.btnHuy);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tencv = edtThemCV.getText().toString();
                if(tencv.equals("")){
                    Toast.makeText(MainActivity.this, "Không được bỏ trống tên công việc", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseHelper.QueryData("INSERT INTO CongViec2(TenCV) VALUES('"+tencv+"')");
                    dialog.dismiss();
                    getDataCongViec();
                }
            }
        });
        dialog.show();
    }
    public void DialogSua(int id,String ten){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sua_cong_viec);
        EditText edtSuaCV = (EditText)dialog.findViewById(R.id.edtSuaCongViec);
        Button btnSua = (Button)dialog.findViewById(R.id.btnSua);
        Button btnHuySua = (Button) dialog.findViewById(R.id.btnHuySua);
        edtSuaCV.setText(ten);
        btnHuySua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenMoi = edtSuaCV.getText().toString();
                databaseHelper.QueryData("UPDATE CongViec2 SET TenCV = '"+tenMoi+"' WHERE Id='"+id+"'");
                dialog.dismiss();
                getDataCongViec();
            }
        });
        dialog.show();
    }
    public void DialogXoaCV(int id, String ten){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa "+ten+" không");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databaseHelper.QueryData("DELETE FROM CongViec2 WHERE Id='"+id+"'");
                getDataCongViec();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogXoa.show();
    }
}