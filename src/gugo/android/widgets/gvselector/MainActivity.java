package gugo.android.widgets.gvselector;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		GVSelectorSimple gvSimple = (GVSelectorSimple) findViewById(R.id.gvselector_test);
		gvSimple.setOnItemClickListener(new GVSelectorSimple.OnGVSelectorClickListener(){

				@Override
				public void onItemClick(int itemId)
				{
					Toast.makeText(MainActivity.this, "clicou em " + itemId, Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onCancelClick()
				{
					Toast.makeText(MainActivity.this, "clicou em cancelar", Toast.LENGTH_SHORT).show();
				}
		});
		
		GVList lista = new GVList();
		lista.add(1,"Gustavo");
		lista.add(2,"Rafael");
		lista.add(3,"Morganna");
		
		gvSimple.setList(lista);
		gvSimple.setTitle("nomes");
	}
	
}
