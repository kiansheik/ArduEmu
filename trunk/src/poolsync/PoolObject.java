package poolsync;

public abstract class PoolObject {
	protected PoolInput poolInput = null;
	protected PoolOutput poolOutput = null;
	protected boolean killed = false;
	
	protected final void setPoolIO(PoolInput pIn, PoolOutput pOut) {poolInput = pIn; poolOutput = pOut;}
	protected PoolOutput getPoolOutput() {return this.poolOutput;}
	protected PoolInput getPoolInput() {return this.poolInput;}
	
	protected final void linkOutput(PoolOutput p) {this.poolOutput = p;}
	protected final void linkInput(PoolInput p) {this.poolInput = p;}
	
	public final void kill() {this.killed = true;}
	public final boolean isKilled() {return killed;}
	
	public abstract void init();
	public abstract void step();
	public abstract void reset();
	protected final void clean() {
		poolInput = null;
		poolOutput = null;
		killed = false;
		this.reset();
	}
	public abstract boolean isDone();
}
