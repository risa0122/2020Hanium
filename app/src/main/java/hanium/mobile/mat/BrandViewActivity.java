package hanium.mobile.mat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class BrandViewActivity extends Activity {

    ArrayList<ChickenBrandDTO> cbrandlist;

    GridView gridView;
    CBrandDBHelper helper;
    Cursor cursor;
    CBrandCursorAdapter adapter;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chicken_brand);
        gridView = (GridView)findViewById(R.id.gridView);

        //list = new ArrayList<FoodDTO>();
        //foodList = new ArrayList<FoodDTO>();
        //list.addAll(foodList);

        helper = new CBrandDBHelper(this);

        adapter = new CBrandCursorAdapter(BrandViewActivity.this, R.layout.activity_chicken_brand, null);
        gridView.setAdapter(adapter);

//		리스트 뷰 클릭 처리
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(BrandViewActivity.this, MenuListViewActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

//		리스트 뷰 롱클릭 처리
        /*gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final long targetId = id;	// id 값을 다이얼로그 객체 내부에서 사용하기 위하여 상수로 선언
                TextView tvName = view.findViewById(R.id.groupNameView);	// 리스트 뷰의 클릭한 위치에 있는 뷰 확인

                String dialogMessage = "'" + tvName.getText().toString() + "' 음식 삭제?";	// 클릭한 위치의 뷰에서 문자열 값 확인

                new AlertDialog.Builder(GroupHomeActivity.this).setTitle("삭제 확인")
                        .setMessage(dialogMessage)
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SQLiteDatabase db = helper.getWritableDatabase();

                                String whereClause = GroupDBHelper.COL_GID + "=?";
                                String[] whereArgs = new String[] { String.valueOf(targetId) };

                                db.delete(GroupDBHelper.TABLE_NAME, whereClause, whereArgs);
                                helper.close();
                                readAllContacts();		// 삭제 상태를 반영하기 위하여 전체 목록을 다시 읽음
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();


                return true;
            }
        });*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        readAllContacts();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        cursor 사용 종료
        if (cursor != null) cursor.close();
    }


    private void readAllContacts() {
//        DB에서 데이터를 읽어와 Adapter에 설정
        SQLiteDatabase db = helper.getReadableDatabase();
        cursor = db.rawQuery("select * from " + CBrandDBHelper.TABLE_NAME, null);

        adapter.changeCursor(cursor);
        helper.close();
    }
}
