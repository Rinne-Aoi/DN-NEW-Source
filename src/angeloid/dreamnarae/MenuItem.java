package angeloid.dreamnarae;

import android.graphics.drawable.Drawable;

public class MenuItem {

	private String TweakName;	// Name
	private Drawable Icon;
	
	public MenuItem() {}
	
	public MenuItem(Drawable _icon, String _name) {
		Icon = _icon;
		TweakName = _name;
	}
	
	public Drawable getIcon() { return Icon; }
	
	public void setIcon(Drawable _icon) { Icon = _icon; }
	
	public String getName() { return TweakName; }
	
	public void setName(String _name) { TweakName = _name; }
}
