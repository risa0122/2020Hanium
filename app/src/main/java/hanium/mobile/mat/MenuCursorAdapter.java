package hanium.mobile.mat;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuCursorAdapter extends CursorAdapter {
    LayoutInflater inflater;
    Cursor cursor;

    public MenuCursorAdapter(Context context, int layout, Cursor c) {
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cursor = c;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView brandNameView = (TextView)view.findViewById(R.id.menu_brand_name);
        TextView menuNameView = (TextView)view.findViewById(R.id.menu_menu_name);
        ImageView imageView = view.findViewById(R.id.menuImage);

        brandNameView.setText(cursor.getString(cursor.getColumnIndex(MenuDBHelper.COL_BRAND)));
        menuNameView.setText(cursor.getString(cursor.getColumnIndex(MenuDBHelper.COL_NAME)));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View listItemLayout = inflater.inflate(R.layout.menuview_layout,  parent, false);
        return listItemLayout;
    }
}
