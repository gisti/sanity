package cri.sanity.screen;

import android.os.Bundle;
import cri.sanity.*;
import cri.sanity.pref.*;
import cri.sanity.util.*;


public class BlockerActivity extends ScreenActivity
{
	@Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    on("block", new Change(){ public boolean on(){
    	prefFilter().updateSum((Boolean)value);
    	return true;
    }});
    on(K.BLOCK_MODE+K.WS, new Change(){ public boolean on(){
    	if(A.SDK>8 && value.equals(Blocker.MODE_HANGUP+""))
    		Alert.msg(A.rawstr(R.raw.block_warn));
    	pref(K.BLOCK_SKIP       ).setEnabled(!value.equals(Blocker.MODE_SILENT+""));
    	pref(K.BLOCK_RESUME+K.WS).setEnabled( value.equals(Blocker.MODE_FLIGHT+""));
    	return true;
    }});
    final int mode = A.geti(K.BLOCK_MODE);
    pref(K.BLOCK_SKIP       ).setEnabled(mode != Blocker.MODE_SILENT);
  	pref(K.BLOCK_RESUME+K.WS).setEnabled(mode == Blocker.MODE_FLIGHT);
    fullOnly(K.BLOCK_MODE+K.WS, K.BLOCK_RESUME+K.WS, K.BLOCK_SKIP, K.BLOCK_NOTIFY);
  }
	
	@Override
	public void onResume()
	{
		super.onResume();
		setChecked("block", A.is(K.BLOCK_FILTER));
	}

	private PFilter prefFilter() { return (PFilter)pref("filter_block"); }

}
