package poolsync;

public abstract class PoolOutput {
	private String output = "";
	private final String name;
	
	public PoolOutput(String name) {this.name = name;}
	
	public abstract void accept(String s);
	public abstract void accept(int i);
	public abstract void accept(long i);
	public abstract void accept(double d);
	
	public final String getName() {return name;}
	
	public final void concat(String s) {output = output + s.replaceAll("\\|", "");}
	public final String flush() {
		String s = String.valueOf(output.toCharArray());
		output = "";
		return s;
	}
}
