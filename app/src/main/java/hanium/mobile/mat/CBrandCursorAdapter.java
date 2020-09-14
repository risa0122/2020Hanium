package hanium.mobile.mat;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CBrandCursorAdapter extends CursorAdapter {

    LayoutInflater inflater;
    Cursor cursor;

    public CBrandCursorAdapter(Context context, int layout, Cursor c) {
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cursor = c;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView brandNameView = (TextView)view.findViewById(R.id.brand_name);
        ImageView imageView = view.findViewById(R.id.imageView2);

        brandNameView.setText(cursor.getString(cursor.getColumnIndex(CBrandDBHelper.COL_NAME)));

        /*byte[] foodImage = cursor.getBlob(cursor.getColumnIndex(CBrandDBHelper.COL_IMAGE));
        Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
        imageView.setImageBitmap(bitmap);*/


        /*if(cursor.getBlob(cursor.getColumnIndex(CBrandDBHelper.COL_IMAGE)) == null){
            imageView2.setImageResource(R.mipmap.honeychip);
        }
        else{
            byte[] foodImage = cursor.getBlob(cursor.getColumnIndex(CBrandDBHelper.COL_IMAGE));
            Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
            imageView2.setImageBitmap(bitmap);
        }*/

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View listItemLayout = inflater.inflate(R.layout. brandview_layout,  parent, false);
        return listItemLayout;
    }
}


