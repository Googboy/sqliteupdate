package com.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 潘硕 on 2017/10/19.
 */
//在Android中要想使用Sqlite数据库，首先应该创建一个类继承SQLiteOpenHelper类，我们把这个类命名为DatabaseHelper，它作为一个访问Sqlite的助手类，
// 提供了两方面的功能：第一:getReadableDatabase()/getWritableDatabase()可以获得SQLiteDatabase对象，通过该对象可以对数据库进行操作；
// 第二:提供OnCreate()和onUpgrade()两个回调函数，允许我们在创建和升级数据库时，进行自己的操作；
class DatabaseHelper extends SQLiteOpenHelper{
    private static final int VERSION = 2;

    public DatabaseHelper(Context context, String name) {
        this(context,name,VERSION);
    }

    public DatabaseHelper(Context context, String name, int version) {
        this(context,name,null,version);

    }
    //在SQLiteOepnHelper的子类当中，必须有该构造函数
    //第一个参数：Context类型，上下文对象。这里也就是activity了
    //第二个参数：String类型，数据库的名称
    //第三个参数：CursorFactory类型
    //第四个参数：int类型，数据库版本
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        //必须通过super调用父类当中的构造函数
        super(context, name, factory, version);
    }



    //该函数是在第一次创建数据库的时候执行,实际上是在第一次得到SQLiteDatabse对象的时候，才会调用这个方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("create a database");
        db.execSQL("create table user(id int,name varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("update a database");
    }
   /*
    // 创建或打开一个可以只读的数据库，返回 SQLiteDatabase对象
    @Override
    public synchronized SQLiteDatabase getReadableDatabase() {
        // TODO Auto-generated method stub
        return super.getReadableDatabase();
    }

    // 创建或打开一个可以读写的数据库，返回 SQLiteDatabase对象
 @Override
 public synchronized SQLiteDatabase getWritableDatabase() {
  // TODO Auto-generated method stub
  return super.getWritableDatabase();
 }
    */


}
