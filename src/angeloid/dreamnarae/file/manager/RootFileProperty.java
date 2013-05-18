package angeloid.dreamnarae.file.manager;

public class RootFileProperty extends FileProperty {

	String FilePerm;

	public RootFileProperty() {}

	public RootFileProperty(String _icon, String _name, String _date, String _size, String _perm) {
		super(_icon, _name, _date, _size);
		FilePerm = _perm;
	}

	public String getPerm() { return FilePerm; }

	public void setPerm(String _perm) { FilePerm = _perm; }
}