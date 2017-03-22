package com.example.avto;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



public class MainActivity extends Activity {
	/*private final static*/ String url; // = "http://nbrb.by/Services/XmlExRates.aspx?ondate=07/08/2016";
	EditText LitrN, LitrN2, LitrN3, LitrN4, LitrN5, NaSummu;
	TextView Cena1, NaSummuUSD;
	View butOk1;
	LinearLayout Lay;
	public String l, l1, k2;
	DBHelper mDatabaseHelper;
	SQLiteDatabase mSqLiteDatabase;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 butOk1 = (Button) findViewById(R.id.butOk1);
		LitrN = (EditText) findViewById(R.id.editText1);
		LitrN2 = (EditText) findViewById(R.id.editText2);
		LitrN3 = (EditText) findViewById(R.id.editText3);
		LitrN4 = (EditText) findViewById(R.id.EditText01);
		Cena1 = (TextView) findViewById(R.id.TextView03);	
		NaSummu = (EditText) findViewById(R.id.EditText02);
		NaSummuUSD = (TextView) findViewById(R.id.TextView05);
		//Lay = (LinearLayout) findViewById(R.id.LinearLayout);
		final TextView infoTextView1 = (TextView)findViewById(R.id.textView4);
		Date d = new Date();
		SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");  // для чтения из сайта
        SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");  // для вывода на экран
        LitrN3.setText(format2.format(d));
         url = "http://nbrb.by/Services/XmlExRates.aspx?ondate="+format1.format(d);
         l1=format2.format(d);
		new MysincTasc(this, url).execute();
		mDatabaseHelper = new DBHelper(this, "mydatabase2.db", null, 1);
		mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
 
		//прячим клаву при старте. по умолчанию она на последнем textedit
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		LitrN2.requestFocus(); //тыкаем фокус что бы не на последнем - т.к. текст выделяется при получении фокуса. 
		LitrN4.setSelectAllOnFocus(true); //выделить весь текст при получении фокуса 
		NaSummu.setSelectAllOnFocus(true);	
		
		vivod();
	
		OnClickListener oclBtn = new OnClickListener() {
				@Override
				public void onClick(View v) {
				zapis();
				vivod();
				}
		 };	
			
		 
		 
			butOk1.setOnClickListener(oclBtn);
			
			infoTextView1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					infoTextView1.setText("редактируем - сделаю потом");	
				}
			}
			);
			LitrN4.setOnKeyListener(new View.OnKeyListener()
			{ 
			  
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					// TODO Auto-generated method stub
					//butOk1.setEnabled(true);
					if(event.getAction() == KeyEvent.ACTION_UP)
							{
							    double k=Double.parseDouble((LitrN2.getText().toString()))*(Double.parseDouble(LitrN4.getText().toString()));
								k2=String.format(Locale.ENGLISH,"%.2f", k);//Double.toString(k);
							    NaSummu.setText(k2);
							    k=k/Double.parseDouble((LitrN.getText().toString()));
							    k2=String.format(Locale.ENGLISH,"%.2f",k);//Double.toString(k);
							    NaSummuUSD.setText(k2);
						    return true;
							}
					return false;
				} 
				
			} 
			);
			

			
			NaSummu.setOnKeyListener(new View.OnKeyListener()
			{
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					
					// TODO Auto-generated method stub
						if (event.getAction() == KeyEvent.ACTION_UP)//  & ((keyCode == 8) | (keyCode == 9)| (keyCode == 10) | (keyCode == 11) | (keyCode == 12) | (keyCode == 13) | (keyCode == 14) | (keyCode == 15) | (keyCode == 16) | (keyCode == 7) | (keyCode == 67) | (keyCode == 66)))
							{
							    double k=Double.parseDouble((NaSummu.getText().toString()))/(Double.parseDouble(LitrN2.getText().toString()));
								k2=String.format(Locale.ENGLISH,"%.2f", k);//Double.toString(k);
							    LitrN4.setText(k2);
							    k=Double.parseDouble((NaSummu.getText().toString()))/Double.parseDouble((LitrN.getText().toString()));
							    k2=String.format(Locale.ENGLISH,"%.2f", k);//Double.toString(k);
							    NaSummuUSD.setText(k2);
							    
//							    if (keyCode == 66) // убираем клаву после ввода
//							    {InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//							    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//							    butOk1.setEnabled(false);
//							    }
							    return true;
							}
					return false;
				}
				
			}
			);
			
			
	}
	
	
	




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//	//	if (id == R.id.action_exit) {
//			 System.exit(0);return true;
//			
//		}
//	//	return super.onOptionsItemSelected(item);
//	};

	public void callBackData(String result) { //результат потока выполняющего парсинг курса доллара
		// TODO Auto-generated method stub
		result=String.format(Locale.ENGLISH,"%.2f", Double.parseDouble(result)); //округляем
		LitrN.setText( result);
		LitrN2.setText("1.19");
		LitrN4.setText("20");
		double k = Double.parseDouble(LitrN.getText().toString());
		k=Double.parseDouble(LitrN2.getText().toString())/k;
		k2= String.format(Locale.ENGLISH,"%.2f", k);//Double.toString(k);
		Cena1.setText(k2);
		k=Double.parseDouble((LitrN2.getText().toString()))*(Double.parseDouble(LitrN4.getText().toString()));
		k2=String.format(Locale.ENGLISH,"%.2f", k);//Double.toString(k);
		NaSummu.setText(k2);
		k=k/Double.parseDouble((LitrN.getText().toString()));
	    k2=String.format(Locale.ENGLISH,"%.2f", k);//Double.toString(k);
	    NaSummuUSD.setText(k2);
	    
	}
	public void zapis(){
		ContentValues values = new ContentValues();
	    values.put(DBHelper.date_COLUMN, l1);                                  //записываем в базу дата
	    values.put(DBHelper.kurs_USD, LitrN.getText().toString());          //записываем в базу курс
	    values.put(DBHelper.cost_l__BYN, LitrN2.getText().toString());      //записываем в базу цена за литр в рубл
	    values.put(DBHelper.cost_l__USD, Cena1.getText().toString());		//записываем в базу цена за литр в USD
	    values.put(DBHelper.litrov_zalito, LitrN4.getText().toString());			 //записываем в базу сколько заправил литров
	    values.put(DBHelper.cena_USD, NaSummuUSD.getText().toString());		//записываем в базу стоимость заправки в USD
	    values.put(DBHelper.cena_BYN, NaSummu.getText().toString());		//записываем в базу стоимость заправки в BYN
	    
	    mSqLiteDatabase.insert("toplivo2", null, values);
	}    
	 public void vivod(){


	    Cursor cursor = mSqLiteDatabase.query("toplivo2", new String[] {mDatabaseHelper._ID, mDatabaseHelper.date_COLUMN,
	    		mDatabaseHelper.kurs_USD , mDatabaseHelper.cost_l__BYN, mDatabaseHelper.cost_l__USD, mDatabaseHelper.litrov_zalito,
	    		mDatabaseHelper.cena_USD, mDatabaseHelper.cena_BYN },
        null, null,
        null, null, null) ;
	    cursor.moveToLast();
		int i;
			for (i=1;i<=cursor.getCount();i++)	{
			String ID = cursor.getString(cursor.getColumnIndex(mDatabaseHelper._ID));
			String date_COLUMN = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.date_COLUMN));
			String kurs_USD = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.kurs_USD));
			String cost_l__BYN = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.cost_l__BYN));
			String cost_l__USD = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.cost_l__USD));
			String litrov_zalito = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.litrov_zalito));
			String cena_USD = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.cena_USD));
			String cena_BYN = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.cena_BYN));
				switch (i){
				case 1:
				{TextView infoTextView = (TextView)findViewById(R.id.textView4);
				infoTextView.setText(" " + ID
				//		+ " " + date_COLUMN + " " +
				//		kurs_USD + " "+ cost_l__BYN + " "+ cost_l__BYN + " "+ cost_l__USD
						+"     "+ litrov_zalito + "     "+ cena_USD + "     "+ cena_BYN);}
				break;
				case 2:
				{TextView infoTextView = (TextView)findViewById(R.id.textView5);
				infoTextView.setText(" " + ID
				//		+ " " + date_COLUMN + " " +
				//		kurs_USD + " "+ cost_l__BYN + " "+ cost_l__BYN + " "+ cost_l__USD
						+"     "+ litrov_zalito + "     "+ cena_USD + "     "+ cena_BYN);}
				break;
				case 3:
				{TextView infoTextView = (TextView)findViewById(R.id.textView6);
				infoTextView.setText(" " + ID
				//		+ " " + date_COLUMN + " " +
				//		kurs_USD + " "+ cost_l__BYN + " "+ cost_l__BYN + " "+ cost_l__USD
						+"     "+ litrov_zalito + "     "+ cena_USD + "     "+ cena_BYN);}
				break;
				case 4:
				{TextView infoTextView = (TextView)findViewById(R.id.textView7);
				infoTextView.setText(" " + ID
				//		+ " " + date_COLUMN + " " +
				//		kurs_USD + " "+ cost_l__BYN + " "+ cost_l__BYN + " "+ cost_l__USD
						+"     "+ litrov_zalito + "     "+ cena_USD + "     "+ cena_BYN);}
				break;
				case 5:
				{TextView infoTextView = (TextView)findViewById(R.id.textView8);
				infoTextView.setText(" " + ID
				//		+ " " + date_COLUMN + " " +
				//		kurs_USD + " "+ cost_l__BYN + " "+ cost_l__BYN + " "+ cost_l__USD
						+"     "+ litrov_zalito + "     "+ cena_USD + "     "+ cena_BYN);}
				break;

				case 6:
				{TextView infoTextView = (TextView)findViewById(R.id.textView9);
				infoTextView.setText(" " + ID
				//		+ " " + date_COLUMN + " " +
				//		kurs_USD + " "+ cost_l__BYN + " "+ cost_l__BYN + " "+ cost_l__USD
						+"     "+ litrov_zalito + "     "+ cena_USD + "     "+ cena_BYN);}
				break;
				case 7:
				{TextView infoTextView = (TextView)findViewById(R.id.textView10);
				infoTextView.setText(" " + ID
				//		+ " " + date_COLUMN + " " +
				//		kurs_USD + " "+ cost_l__BYN + " "+ cost_l__BYN + " "+ cost_l__USD
						+"     "+ litrov_zalito + "     "+ cena_USD + "     "+ cena_BYN);}
				break;

				case 8:
				{TextView infoTextView = (TextView)findViewById(R.id.textView12);
				infoTextView.setText(" " + ID
				//		+ " " + date_COLUMN + " " +
				//		kurs_USD + " "+ cost_l__BYN + " "+ cost_l__BYN + " "+ cost_l__USD
						+"     "+ litrov_zalito + "     "+ cena_USD + "     "+ cena_BYN);}
				break;

				}
cursor.moveToPrevious();
}
cursor.close();  //   закрываем курсор
	 }
	
}
