package emulator.memory;

public class MemProg {
	private final short[] mem = new short[0x800];
	
	public short read(int addr) {return read((short)addr);}
	public short read(short addr) {return mem[addr];}

	public short[] readAll() {return mem;}
	
	public void write(int addr, short data) {write((short)addr, data);}
	public void write(short addr, short data) {mem[addr] = data;}
}
