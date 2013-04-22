package poolsync;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

class CleanupTimer extends TimerTask {
	private final ObjectPool link;
	
	public CleanupTimer(ObjectPool o) {
		super(); this.link = o;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("Cleanup executing...");
			link.cleanup();
		} catch(NullPointerException e) {
			System.err.println("Cleanup failed!");
			e.printStackTrace();
		}
	}
}

public class ObjectPool {
	private volatile ArrayList<PoolObject> idle = new ArrayList<>();
	private ArrayList<PoolObject> active = new ArrayList<>();
	private volatile ArrayList<PoolOutput> outputList = new ArrayList<>();
	private volatile PoolObject masterObject = null;
	
	private static final int CLEANUP_DELAY = 6000;
	private final Timer t;
	public ObjectPool(int size, Class<? extends PoolObject> c) {
		t = new Timer(); t.schedule(new CleanupTimer(this), CLEANUP_DELAY);
		idle.ensureCapacity(size);
		for(int i = 0; i < size; i++)
			try {
				idle.add(c.newInstance());
			} catch (InstantiationException | IllegalAccessException ex) {
				throw new Error("Could not instantiate the object pool! \nMake sure PoolObject is public!");
			}
	}
	
	public synchronized PoolObject allocateMaster(PoolInput pIn, PoolOutput pOut) {
		PoolObject p = idle.get(0); idle.remove(0);
		p.setPoolIO(pIn, pOut); this.masterObject = p;
		return p;
	}
	
	public synchronized void dropMaster() {
		idle.add(masterObject);
		masterObject.clean();
		masterObject = null;
	}
	
	public synchronized PoolObject allocate(PoolInput pIn, PoolOutput pOut) throws NoSuchFieldException {
		if(idle.size() < 1+(masterObject==null?1:0)) throw new NoSuchFieldException("The pool has run out of PoolObjects!");
		PoolObject p = idle.get(0); idle.remove(0);
		p.setPoolIO(pIn, pOut); active.add(p);
		return p;
	}
	
	public synchronized void iteration() {
		if(!getMasterAvailable()) masterObject.step();
		System.out.println("Active: " + active);
		for(PoolObject p : active) p.step();
	}
	
	public synchronized void stop() {
		t.cancel();
	}
	
	protected synchronized void cleanup() {
		if(!getMasterAvailable()) if(masterObject.isDone()) this.dropMaster();
		for(int i = 0; i < active.size(); i++) {
			if(active.get(i).isDone()) {
				PoolObject p = active.get(i); active.remove(i);
				System.out.println("Marshalling object " + p + "; remaining: " + active);
				outputList.add(p.getPoolOutput());
				p.clean(); idle.add(p); i--;
			}
		}
		t.schedule(new CleanupTimer(this), CLEANUP_DELAY);
	}
	
	public synchronized ArrayList<PoolOutput> consumeOuptuts() {
		ArrayList<PoolOutput> c = new ArrayList<>();
		for(PoolOutput p : outputList) c.add(p);
		outputList = new ArrayList<>();
		return c;
	}
	
	public boolean getMasterAvailable() {return (this.masterObject == null);}
	public int getIdleCount() {return idle.size();}
	public int getActiveCount() {return active.size();}
	public ArrayList<PoolObject> getIdleObjects() {return idle;}
	public ArrayList<PoolObject> getActiveObjects() {return active;}
}
