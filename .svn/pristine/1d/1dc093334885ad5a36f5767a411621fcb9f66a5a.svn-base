package emulator.memory;

public class MemEEProm {
	private final byte[] mem = new byte[256];
	
	public byte read(int addr) {return read((short)addr);}
	public byte read(short addr) {return mem[addr];}

	public short readShort(int addrHi, int addrLo) {return readShort((short)addrHi, (short)addrLo);}
	public short readShort(short addrHi, short addrLo) {
		return (short)((mem[addrHi] << 8) + (mem[addrLo]));
	}
	
	public byte[] readAll() {return mem;}

	public void write(short addr, byte data) {}
}
