package com.example.sqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SqliteActivity extends Activity {
    private Button createButton;
    private Button insertButton;
    private Button updateButton;
    private Button updateRecordButton;
    private Button queryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        createButton = (Button) findViewById(R.id.createDatabase);
        updateButton = (Button) findViewById(R.id.updateDatabase);
        insertButton = (Button) findViewById(R.id.insert);
        updateRecordButton = (Button) findViewById(R.id.update);
        queryButton = (Button) findViewById(R.id.query);
        createButton.setOnClickListener(new CreateListener());
        updateButton.setOnClickListener(new UpdateListener());
        insertButton.setOnClickListener(new InsertListener());
        updateRecordButton.setOnClickListener(new UpdateRecordListener());
        queryButton.setOnClickListener(new QueryListener());
    }

    private class CreateListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //创建一个DatabaseHelper对象
            DatabaseHelper dbHelper = new DatabaseHelper(SqliteActivity.this,"test_db");
            //只有调用了DatabaseHelper对象的getReadableDatabase()方法，或者是getWritableDatabase()方法之后，才会创建，或打开一个数据库
            SQLiteDatabase db = dbHelper.getReadableDatabase();//SQLite内存数据库SQLitedatabase，SQLiteDatabase对象对数据库进行一系列的操作，如新建一个表，插入一条数据等
            System.out.println("创建数据库.......");//这里是为了在控制台上容易看清执行了这一步贰设置的提醒语句
        }
    }

    private class UpdateListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            DatabaseHelper dbHelper = new DatabaseHelper(SqliteActivity.this,"test_db",2);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            System.out.println("更新数据库......");
        }
    }

    private class InsertListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ContentValues cv = new ContentValues();
            //想该对象当中插入键值对，其中键是列名，值是希望插入到这一列的值，值必须和数据库当中的数据类型一致
            cv.put("id",1);
            cv.put("name","zhangsan");
            DatabaseHelper dbHelper = new DatabaseHelper(SqliteActivity.this,"test_db",2);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //调用insert方法，将数据插入到数据库中去
            db.insert("user",null,cv);
            System.out.println("插入数据.....");
        }
    }
    //更新操作就相当于执行SQL语句当中的update语句
    private class UpdateRecordListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            DatabaseHelper dbHelper = new DatabaseHelper(SqliteActivity.this,"test_db");
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("name","zhangsanfeng");
            //第一个参数是要更新的表名
            //第二个参数是一个ContentValeus对象
            //第三个参数是where子句
            db.update("user",cv,"id=?",new String[]{"1"});
            System.out.println("更新数据......");
        }
    }

    private class QueryListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            System.out.println("aaa--------");
            Log.d("myDebug","MyFirstDebugMsg");
            DatabaseHelper dbHelper = new DatabaseHelper(SqliteActivity.this,"test_db");
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            // 第一个参数String：表名
            // 第二个参数String[]:要查询的列名
            // 第三个参数String：查询条件
            // 第四个参数String[]：查询条件的参数
            // 第五个参数String:对查询的结果进行分组
            // 第六个参数String：对分组的结果进行限制
            // 第七个参数String：对查询的结果进行排序
            Cursor cursor = db.query("user",new String[]{"id","name"},"id=?",new String[]{"1"},null,null,null);
            // 将光标移动到下一行，从而判断该结果集是否还有下一条数据，如果有则返回true，没有则返回false
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex("name"));
                System.out.println("query--->"+name);
                System.out.println("查询数据.......");
            }
        }
    }
}
//在创建或者操作数据库前，都要通过DatabaseHelpe类中的getReadbleDatabase()和getWritbleDatabase()两个方法在创建需要的数据库，
// 前者是创建一个只读数据库，后者是创建一个可写数据库。
