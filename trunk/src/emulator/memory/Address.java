package emulator.memory;

public class Address {
    public static final short RAMEND = 0x02ff;
    public static final int SPL = 0x5d;
    public static final int SPH = 0x5e;
    public static final int X_LOW = 0x1A;
    public static final int X_HIGH = 0x1B;
    public static final int Y_LOW = 0x1C;
    public static final int Y_HIGH = 0x1D;
    public static final int Z_LOW = 0x1E;
    public static final int Z_HIGH = 0x1F;
	private Address() {}
}
