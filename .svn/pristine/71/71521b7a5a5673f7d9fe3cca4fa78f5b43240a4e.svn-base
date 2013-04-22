package emulator.memory;

public class MemData {
    private final byte[] mem = new byte[0x300];
    private static final int LBYTEMASK = 0x00ff;
    private static final int HBYTEMASK = 0xff00;

	public byte read(int addr) {return read((short) addr);}
    public byte read(short addr) {return mem[addr];}
    public short readShort(int addrHi, int addrLo) {return readShort((short) addrHi, (short) addrLo);}
    public short readShort(short addrHi, short addrLo) {return (short) ((mem[addrHi] << 8) + (mem[addrLo]));}
	public byte[] readAll() {return mem;}
	
    public short readX() {return readShort(Address.X_LOW, Address.X_HIGH);}
    public short readY() {return readShort(Address.Y_LOW, Address.Y_HIGH);}
    public short readZ() {return readShort(Address.Z_LOW, Address.Z_HIGH);}
    public void writeX(short d) {
		mem[Address.X_LOW] = (byte) (LBYTEMASK & d);
		mem[Address.X_HIGH] = (byte) ((HBYTEMASK & d) >>> 8);
    }
    public void writeY(short d) {
        mem[Address.Y_LOW] = (byte) (LBYTEMASK & d);
        mem[Address.Y_HIGH] = (byte) ((HBYTEMASK & d) >>> 8);
    }
    public void writeZ(short d) {
        mem[Address.Z_LOW] = (byte) (LBYTEMASK & d);
        mem[Address.Z_HIGH] = (byte) ((HBYTEMASK & d) >>> 8);
    }
    
    public void write(int addr, byte data) {write((short) addr, data);}
    public void write(short addr, byte data) {mem[addr] = data;}
    public void writeShort(int addrLow, int addrHi, short data) {
        mem[addrLow] = (byte) (LBYTEMASK & data);
        mem[addrHi] = (byte) ((HBYTEMASK & data) >>> 8);
    }
}
