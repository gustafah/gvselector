package gugo.android.widgets.gvselector;

import android.text.*;
import android.view.*;
import android.widget.*;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.AdapterView.OnItemClickListener;

public class GVSelectorSimple extends EditText
{	
	Context mContext;
	ResourceKit dialog, button, list, listItem, selected;
	private String title;
	private GVList mList = new GVList();
	private int selectedId = 0;
	OnGVSelectorClickListener mListener;

	public GVSelectorSimple(Context context) {this(context, null);}
	public GVSelectorSimple(Context context, AttributeSet attrs) {this(context, attrs, 0);}
	public GVSelectorSimple(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		init();
		organizeAttrs(attrs);
	}
	
	private void organizeAttrs(AttributeSet attrs){
		if (attrs == null) {
            return;
        }

        TypedArray a = null;
        try {
            a = getContext().obtainStyledAttributes(attrs, R.styleable.gvAttrs);
            dialog.backgroundRes = a.getInt(R.styleable.gvAttrs_dialog_background, -1);
            dialog.textRes = a.getResourceId(R.styleable.gvAttrs_dialog_title, -1);
            dialog.padding = a.getInt(R.styleable.gvAttrs_dialog_padding, -1);

            button.textRes = a.getResourceId(R.styleable.gvAttrs_button_text, -1);
        } finally {
            if (a != null) {
                a.recycle(); // ensure this is always called
            }
        }
	}

	public void showSelector(){
		askInfo();
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
		//if(focused)
			//askInfo();
		//if(selected.padding > -1) setPadding(selected.padding, selected.padding, selected.padding, selected.padding);
	}

	public void setOnItemClickListener(OnGVSelectorClickListener listener){
		this.mListener = listener;
	}

	private void init(){
		if(dialog==null) dialog = new ResourceKit();
		if(list==null) list = new ResourceKit();
		if(button==null) button = new ResourceKit();
		if(listItem==null) listItem = new ResourceKit();
		if(selected==null) selected = new ResourceKit();
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

	public void setSelectedItemResources(int back, int color, int appearance, int text, int padding, int textSize){
		selected.backgroundRes = back;
		selected.colorRes = color;
		selected.appearanceRes = appearance;
		selected.textRes = text;
		selected.padding = padding;
		selected.textSize = textSize;
		
		if(selected.backgroundRes > -1) setBackgroundResource(selected.backgroundRes);
		if(selected.colorRes != -1) setTextColor(selected.colorRes);
		if(selected.appearanceRes > -1) setTextAppearance(mContext, selected.appearanceRes);
		if(selected.textRes > -1) setText(selected.textRes);
		if(selected.textSize > -1) setTextSize(selected.textSize);
		if(selected.padding > -1) setPadding(selected.padding, selected.padding, selected.padding, selected.padding);
	}

	public void setDialogResources(int back, int title, int padding){
		dialog.backgroundRes = back;
		dialog.textRes = title;
		dialog.padding = padding;
	}

	public void setButtonResource(int back, int color, int text, int padding, int appearance, int textSize){
		button.backgroundRes = back;
		button.colorRes = color;
		button.textRes = text;
		button.padding = padding;
		button.appearanceRes = appearance;
		button.textSize = textSize;
	}

	public void setListResource(int back, int dividerRes, int dividerWidth, int listSelectorRes, boolean isFastScroll){
		list.backgroundRes = back;
		list.dividerRes = dividerRes;
		list.dividerWidth = dividerWidth;
		list.listSelectorRes = listSelectorRes;
		list.isFastScroll = isFastScroll;
	}

	public void setListItemResource(int back, int color, int text, int appearance, int padding, int textSize){
		listItem.backgroundRes = back;
		listItem.colorRes = color;
		listItem.padding = padding;
		listItem.appearanceRes = appearance;
		listItem.textSize = textSize;
	}

	OnClickListener onClick = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			askInfo();
			if(selected.padding > -1) setPadding(selected.padding, selected.padding, selected.padding, selected.padding);
		}
	};

	private void askInfo(){
		DialogInfo info = new DialogInfo();
		info.setList(mList);
		info.setTitle(title);
		info.setListener(mListener);
		info.setResourceKit(dialog, button, list, listItem);
		ListDialog dialog = new ListDialog(mContext, info);
		dialog.show();
	}

	private class DialogInfo {
		private String title;
		private GVList gvlist;
		private OnGVSelectorClickListener listener;
		private ResourceKit dialog, button, list, listItem;

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
			return gvlist;
		}
		public void setList(GVList list) {
			this.gvlist = list;
		}

		public void setResourceKit(ResourceKit dialog, ResourceKit button, ResourceKit list, ResourceKit listItem){
			this.dialog = dialog;
			this.button = button;
			this.list = list;
			this.listItem = listItem;
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
			if(mInfo.dialog.textRes > -1) setTitle(mInfo.dialog.textRes);
			if(mInfo.dialog.backgroundRes > -1) setBackgroundResource(mInfo.dialog.backgroundRes);
			if(mInfo.dialog.padding > -1) setPadding(mInfo.dialog.padding, mInfo.dialog.padding, mInfo.dialog.padding, mInfo.dialog.padding);
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
			try {
				if(mInfo.button.backgroundRes != -1) btnCancel.setBackgroundResource(mInfo.button.backgroundRes);
			} catch (Exception e) {
				if(mInfo.button.backgroundRes != -1) btnCancel.setBackgroundColor(mInfo.button.backgroundRes);
			}
			if(mInfo.button.colorRes != -1) btnCancel.setTextColor(mInfo.button.colorRes);
			if(mInfo.button.appearanceRes > -1) btnCancel.setTextAppearance(mContext, mInfo.button.appearanceRes);
			if(mInfo.button.padding > -1) btnCancel.setPadding(mInfo.button.padding, mInfo.button.padding, mInfo.button.padding, mInfo.button.padding);
			if(mInfo.button.textRes > -1) btnCancel.setText(mInfo.button.textRes);
			if(mInfo.button.textSize > -1) btnCancel.setTextSize(mInfo.button.textSize);
			//System.out.println(mInfo.button.textRes + " > -1 = " + (mInfo.button.textRes > -1) + "(" + mContext.getString(mInfo.button.textRes) + ")");
			btnCancel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mInfo.getListener().onCancelClick();
					dismiss();
				}
			});

			ListView listview = (ListView)findViewById(R.id.listview);
			try {
				if(mInfo.list.backgroundRes != -1) listview.setBackgroundResource(mInfo.list.backgroundRes);
			} catch (Exception e) {
				if(mInfo.list.backgroundRes != -1) listview.setBackgroundColor(mInfo.list.backgroundRes);
			}
			try {
				if(mInfo.list.dividerRes != -1) listview.setDivider(mContext.getResources().getDrawable(mInfo.list.dividerRes));
			} catch (Exception e) {
				if(mInfo.list.dividerRes != -1) listview.setDivider(new ColorDrawable(mInfo.list.dividerRes));
			}
			if(mInfo.list.dividerWidth > -1) listview.setDividerHeight(mInfo.list.dividerWidth);
			if(mInfo.list.listSelectorRes != -1) listview.setSelector(mInfo.list.listSelectorRes);
			listview.setFastScrollEnabled(mInfo.list.isFastScroll);
			listview.setOnItemClickListener(this);
			mAdapter = new GVAdapter(mContext, mFiltered, mInfo.listItem);
			listview.setAdapter(mAdapter);

		}
	}

	private class GVAdapter extends BaseAdapter
	{
		private Context mContext;
		private GVList mList;
		private ResourceKit mItemRes;

		public GVAdapter(Context c, GVList list, ResourceKit itemRes){
			mContext = c;
			mList = list;
			mItemRes = itemRes;
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
			if(mItemRes.backgroundRes != -1) tvItem.setBackgroundResource(mItemRes.backgroundRes);
			if(mItemRes.colorRes != -1) tvItem.setTextColor(mItemRes.colorRes);
			if(mItemRes.textSize != -1) tvItem.setTextSize(mItemRes.textSize);
			if(mItemRes.appearanceRes > -1) tvItem.setTextAppearance(mContext, mItemRes.appearanceRes);
			if(mItemRes.padding > -1) tvItem.setPadding(mItemRes.padding, mItemRes.padding, mItemRes.padding, mItemRes.padding);
			tvItem.setText((String) getItem(p1));

			return row;
		}
	}

	private class ResourceKit {
		//general
		public int backgroundRes = -1;
		public int colorRes = -1;
		public int textRes = -1;
		public int padding = -1;
		public int textSize = -1;

		//list
		public int dividerRes = -1;
		public int dividerWidth = -1;
		public int listSelectorRes = -1;
		public boolean isFastScroll = true;

		//listItem
		public int appearanceRes = -1;
	}
}
