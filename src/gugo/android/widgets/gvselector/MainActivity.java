package gugo.android.widgets.gvselector;

import android.app.Activity;
import android.graphics.Color;
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
		
		gvSimple.setSelectedItemResources(-1, Color.rgb(255, 0, 0), android.R.attr.textAppearanceMedium, R.string.hello, 50);
		
		gvSimple.setDialogResources(-1, R.string.app_name, 10);
		
		gvSimple.setButtonResource(Color.rgb(128, 128, 128), Color.BLACK, R.string.hello, 20, -1);
		
		gvSimple.setListResource(Color.BLUE, Color.DKGRAY, 2, -1, true);
		
		gvSimple.setListItemResource(-1, Color.GREEN, -1, android.R.attr.textAppearanceLarge, 10);
		
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
