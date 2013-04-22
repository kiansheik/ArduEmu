package emulator;

import poolsync.PoolObject;

public class Emulator extends PoolObject {
	@Override
	public void init() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	@Override
	public void step() {
		
	}

	@Override
	public void reset() {
		
	}

	@Override
	public boolean isDone() {
		return false;
	}
}
