package emulator.processor;

import emulator.command.CommandSet;
import emulator.memory.Address;
import emulator.memory.MemData;
import emulator.memory.MemEEProm;
import emulator.memory.MemProg;

public class Processor {

    /**
     * Data memory
     * 0x0000 - 0x001f -> registers
     * 0x0020 - 0x005f -> 64 I/O registers
     * 0x0060 - 0x00ff -> 160 external I/O registers
     * 0x0100 - 0x02ff -> 512 bytes of internal SRAM
     */
    private MemProg memProg;
    private MemData memData;
    private MemEEProm memEEProm;
    
	private int PC = 0;
    private long CYCLES = 0L;
    
	public Processor() {
        reset();
    }
    private void reset() {
		memProg = new MemProg();
		memData = new MemData();
		memEEProm = new MemEEProm();
        writeSP(Address.RAMEND);
        CYCLES = 0L;
        PC = 0;
        resetSReg();
    }
    public void resetSReg() {
        for (StatusRegister r : StatusRegister.values())
            r.write(true, memData);
    }
	
    public void invokeInst(short inst) {
        // Cat 0
        // ---- ---- ---- ----
        int maskInt = (0xffff & inst);
		if(CommandSet.hasCmd(maskInt)) {
			CommandSet.getCmd(maskInt).run(memData, memProg, memEEProm, inst); 
			/*CYCLES++;*/ return;
		}
		maskInt = (inst & 0xfc00);
		if(CommandSet.hasCmd(maskInt)) {
			CommandSet.getCmd(maskInt).run(memData, memProg, memEEProm, inst); 
			/*CYCLES++;*/ return;
		}
    }
	
	public short readSP() {
        return (short) (0x07ff & memData.readShort(Address.SPL, Address.SPH));
    }
    public void writeSP(short sp) {
        memData.writeShort(Address.SPL, Address.SPH, (short) (0x07ff & sp));
    }
    public void incSP() {
        writeSP((short) (readSP() + 2));
    }
    public void decSP() {
        writeSP((short) (readSP() - 2));
    }
    public static void main(String[] args) {
        Processor p = new Processor();
        int Rd = 0xf;
        int Rr = 0x9;
        p.memData.write(Rd, (byte) 32);
        p.memData.write(Rr, (byte) 15);
        int inst = 0x0c00 | ((Rr & 0x10) << 5) | (Rd << 4) | (Rr & 0xf);
        System.out.printf("0x%x\n", inst);
        p.invokeInst((short) inst);
        int expected = 32 + 15;
        System.out.println("stuff: " + (expected == p.memData.read(Rd)));
    }
}