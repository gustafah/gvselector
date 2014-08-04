package gugo.android.widgets.gvselector;

public class DialogInfo {
		private String title;
		private GVList gvlist;
		private OnGVSelectorClickListener listener;
	public ResourceKit dialog, button, list, listItem;

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
