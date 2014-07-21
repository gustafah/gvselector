package gugo.android.widgets.gvselector;

import android.text.*;
import android.view.*;
import android.widget.*;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.AdapterView.OnItemClickListener;

public class GVSelectorSimple extends EditText
{	
	Context mContext;
	ResourceKit dialog, button, list, listItem;
    private String title;
    private GVList mList = new GVList();
    private int selectedId = 0;
    OnGVSelectorClickListener mListener;

    public GVSelectorSimple(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public GVSelectorSimple(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public GVSelectorSimple(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public void showSelector(){
        askInfo();
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if(focused)
            askInfo();
    }

    public void setOnItemClickListener(OnGVSelectorClickListener listener){
        this.mListener = listener;
    }

    private void init(){
        setOnClickListener(onClick);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getItemId(){
        return selectedId;
    }

    public void setItemId(int itemId){
        selectedId = itemId;
    }

    public void setList(GVList list) {
        mList.clear();
        if(list != null && list.size() > 0)
            mList.addAll(list);
//      Collections.sort(mList);
    }
	
	public void setDialogResources(int back, int color, int title){
		dialog.background = back;
	}

	public void setButtonBackgroundResource(int res){
		button.background = res;
	}

	public void setListBackgroundResource(int res){
		list.background = res;
	}

	public void setListItemBackgroundResource(int res){
		listItem.background = res;
	}

    OnClickListener onClick = new OnClickListener() {
        @Override
        public void onClick(View arg0) {
            askInfo();
        }
    };

    private void askInfo(){
        DialogInfo info = new DialogInfo();
        info.setList(mList);
        info.setTitle(title);
		info.setListener(mListener);
        ListDialog dialog = new ListDialog(mContext, info);
        dialog.show();
    }

    private class DialogInfo {
        private String title;
        private GVList list;
		private OnGVSelectorClickListener listener;

		public void setListener(OnGVSelectorClickListener listener)
		{
			this.listener = listener;
		}
		public OnGVSelectorClickListener getListener()
		{
			return listener;
		}
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public GVList getList() {
            return list;
        }
        public void setList(GVList list) {
            this.list = list;
        }
    }
	
	public interface OnGVSelectorClickListener{
        public void onItemClick(int itemId);
        public void onCancelClick();
    }
	
	private class ListDialog extends Dialog implements OnItemClickListener
	{
		
        Context mContext;
        OnGVSelectorClickListener mCallback;
        GVList mFiltered;
        DialogInfo mInfo;
        ListView listview;
        GVAdapter mAdapter;

        public ListDialog(Context c, DialogInfo info) {
            super(c);
            mContext = c;

            this.mInfo = info;
            mFiltered = new GVList();
            mFiltered.addAll(info.getList());
            setTitle(info.getTitle());
            //getWindow().requestFeature(Window.FEATURE_NO_TITLE);

            setContentView(R.layout.dialog_simple);
            createUI();
        }

		@Override
		public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
		{
			mInfo.getListener().onItemClick((int) p4);
			setText(mInfo.getList().getValueById((int) p4));
			dismiss();
		}

        private void createUI(){
            Button btnCancel = (Button) findViewById(R.id.button_cancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						mInfo.getListener().onCancelClick();
						dismiss();
					}
				});

            ListView listview = (ListView)findViewById(R.id.listview);
            listview.setFastScrollEnabled(true);
            listview.setOnItemClickListener(this);
            mAdapter = new GVAdapter(mContext, mFiltered);
            listview.setAdapter(mAdapter);

        }
	}
	
	private class GVAdapter extends BaseAdapter
	{
		private Context mContext;
		private GVList mList;
		
		public GVAdapter(Context c, GVList list){
			mContext = c;
			mList = list;
		}

		@Override
		public int getCount()
		{
			return mList.size();
		}

		@Override
		public Object getItem(int p1)
		{
			return mList.getValueById((int)getItemId(p1));
		}

		@Override
		public long getItemId(int p1)
		{
			return mList.getId(p1);
		}

		@Override
		public View getView(int p1, View p2, ViewGroup p3)
		{
			View row = p2;
			if (row == null) {
				LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.item_simple, p3, false);
			}
			
			TextView tvItem = (TextView) row.findViewById(R.id.textview);
			tvItem.setText((String) getItem(p1));
			
			return row;
		}
	}
	
	private class ResourceKit {
		public int background;
		public int color;
		public int text;
		
		//list
		public int divider;
		public int dividerWidth;
	}
}
