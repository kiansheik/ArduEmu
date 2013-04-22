package emulator.processor;

import emulator.memory.MemData;

public enum StatusRegister {
    C((byte) 1),
    Z((byte) (1 << 1)),
    N((byte) (1 << 2)),
    V((byte) (1 << 3)),
    S((byte) (1 << 4)),
    H((byte) (1 << 5)),
    T((byte) (1 << 6)),
    I((byte) (1 << 7));
    private final byte currentRegister;
    private static final int SREG = 0x5f;
    public boolean read(MemData m) {
        return (m.read(SREG) & currentRegister) > 0;
    }
    public void write(boolean bit, MemData m) {
        if (bit)
            m.write(SREG, (byte) (m.read(SREG) | currentRegister));
        else
            m.write(SREG, (byte) (m.read(SREG) & ~currentRegister));
    }
    StatusRegister(byte a) {
        this.currentRegister = a;
    }
}
