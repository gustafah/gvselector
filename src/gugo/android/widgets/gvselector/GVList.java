package gugo.android.widgets.gvselector;

import android.util.SparseArray;

public class GVList extends SparseArray<String>
{

	public void addAll(GVList list)
	{
		for(int i = 0; i < list.size(); i++){
			put(list.keyAt(i), list.valueAt(i));
		}
	}
	
	public void add(int id, String value){
		put(id, value);
	}
	
	public int getId(int index){
		return keyAt(index);
	}
	
	public String getValueAt(int index){
		return valueAt(index);
	}
	
	public String getValueById(int id){
		return get(id);
	}
}
