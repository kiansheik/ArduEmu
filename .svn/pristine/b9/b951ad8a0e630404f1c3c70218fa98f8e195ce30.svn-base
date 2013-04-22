package emulator;

public class EmuMath {

    private static final int LBYTEMASK = 0x00ff;
    private static final int HBYTEMASK = 0xff00;
    public static int getRrAddrCat1(short inst) {
        return ((inst & 0x200) >>> 5) | 0xf & inst;
    }
    public static int getRdAddrCat1(short inst) {
        return (inst >>> 4) & 0x1f;
    }
    public static boolean isAddCarryBit(int rd, int rr, int result, int bit) {
        return (((rd & rr) | (rr & ~result) | (~result & rd)) & (1 << bit)) != 0;
    }
    public static boolean isAddOverflow(int rd, int rr, int result) {
        return (((rd & rr & ~result) | (~rd & ~rr & result)) & 0x80) != 0;
    }
    public static boolean isSubCarryBit(int rd, int rr, int result, int bit) {
        return (((~rd & rr) | (rr & result) | (result & ~rd)) & (1 << bit)) != 0;
    }
    public static boolean isSubOverflow(int rd, int rr, int result) {
        return (((rd & ~rr & ~result) | (~rd & rr & result)) & 0x80) != 0;
    }
    public static int byteToInt(byte b) {
        return (LBYTEMASK & (int) b);
    }
    public static int boolToInt(boolean b) {
        return b ? 1 : 0;
    }
    private EmuMath() {
    }
}
