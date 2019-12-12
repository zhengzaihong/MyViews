package dz.solc.myviews.adapter;

import android.content.Context;

import dz.solc.viewtool.adapter.CommonAdapter;

public class TestAdapter extends CommonAdapter<String> {
    public TestAdapter(Context context) {
        super(context,0);
    }

    @Override
    public void convert(ViewHolder holder, int position, String entity) {

    }
}
