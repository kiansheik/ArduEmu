package emulator.command;

import emulator.EmuMath;
import emulator.memory.MemData;
import emulator.memory.MemEEProm;
import emulator.memory.MemProg;
import emulator.processor.StatusRegister;

public enum CommandEnum {
	EICALL(0x9519, new ComEICALL()),
    EIJMP(0x9419, new ComEIJMP()),
    ELPM(0x95D8, new ComELPM()),
    ESPM(0x95F8, new ComESPM()),
    ICALL(0x9509, new ComICALL()),
    IJMP(0x9409, new ComIJMP()),
    LPM(0x95C8, new ComLPM()),
    NOP(0x0000, new ComNOP()),
    RET(0x9508, new ComRET()),
    RETI(0x9518, new ComRETI()),
    SLEEP(0x9588, new ComSLEEP()),
    SPM(0x95E8, new ComSPM()),
    WDR(0x95A8, new ComWDR()),
    BREAK(0x9598, new ComBREAK()),
    ADC(0x1c00, new ComADC()),
    ADD(0x0c00, new ComADD()),
    AND(0x2000, new ComAND()),
    CP(0x1400, new ComCP()),
    CPC(0x0400, new ComCPC()),
    CPSE(0x1000, new ComCPSE()),
    EOR(0x2400, new ComEOR()),
    MOV(0x2C00, new ComMOV()),
    MUL(0x9C00, new ComMUL()),
    OR(0x2800, new ComOR()),
    SBC(0x0800, new ComSBC()),
    SUB(0x1800, new ComSUB());
	
	private final int opcode;
	private final Command com;

	public final int getOpcode() {return opcode;}
	public final Command getCom() {return com;}

	CommandEnum(int instr, Command c) {
		this.opcode = instr;
		this.com = c;
	}
}

class ComEICALL extends Command {
	@Override
	public void run(MemData memData, MemProg memProg, MemEEProm memEEProm, short instr) {
		throw new UnsupportedOperationException("Not supported yet. ComEICall");
	}
}

class ComEIJMP extends Command {
	@Override
	public void run(MemData memData, MemProg memProg, MemEEProm memEEProm, short instr) {
		throw new UnsupportedOperationException("Not supported yet. ComEIJMP");
	}
}

class ComELPM extends Command {
	@Override
	public void run(MemData memData, MemProg memProg, MemEEProm memEEProm, short instr) {
		throw new UnsupportedOperationException("Not supported yet. ComELPM");
	}
}

class ComESPM extends Command {
	@Override
	public void run(MemData memData, MemProg memProg, MemEEProm memEEProm, short instr) {
		throw new UnsupportedOperationException("Not supported yet. ComESPM");
	}
}

class ComICALL extends Command {
	@Override
	public void run(MemData memData, MemProg memProg, MemEEProm memEEProm, short instr) {
		throw new UnsupportedOperationException("Not supported yet. ComICALL");
	}
}

class ComIJMP extends Command {
	@Override
	public void run(MemData memData, MemProg memProg, MemEEProm memEEProm, short instr) {
		throw new UnsupportedOperationException("Not supported yet. ComIJMP");
	}
}

class ComLPM extends Command {
	@Override
	public void run(MemData memData, MemProg memProg, MemEEProm memEEProm, short instr) {
		short load = memProg.read((short) (memData.readZ() / 2));
		memData.write(0, (byte) ((memData.readZ() & 0x1) == 1 ? (load >>> 8) : load));
	}
}

class ComNOP extends Command {
	@Override
	public void run(MemData memData, MemProg memProg, MemEEProm memEEProm, short instr) {
	}
}

class ComRET extends Command {
	@Override
	public void run(MemData memData, MemProg memProg, MemEEProm memEEProm, short instr) {
		//incSP();
	}
}

class ComRETI extends Command {
	@Override
	public void run(MemData memData, MemProg memProg, MemEEProm memEEProm, short instr) {
		CommandEnum.RET.getCom().run(memData, memProg, memEEProm, instr);
		StatusRegister.I.write(true, memData);
	}
}

class ComSLEEP extends Command {
	@Override
	public void run(MemData memData, MemProg memProg, MemEEProm memEEProm, short instr) {
		throw new UnsupportedOperationException("Not supported yet. SLEEP");
	}
}

class ComSPM extends Command {
	@Override
	public void run(MemData memData, MemProg memProg, MemEEProm memEEProm, short instr) {
		short r0r1 = memData.readShort(0, 1);
		short z = memData.readZ();
		memProg.write(z, r0r1);
	}
}

class ComWDR extends Command {
	@Override
	public void run(MemData memData, MemProg memProg, MemEEProm memEEProm, short instr) {
		throw new UnsupportedOperationException("Not supported yet. WDR");
	}
}

class ComBREAK extends Command {
	@Override
	public void run(MemData memData, MemProg memProg, MemEEProm memEEProm, short instr) {
		throw new UnsupportedOperationException("Not supported yet. BREAK");
	}
}

class ComADC extends Command {
	@Override
	public void run(MemData memData, MemProg memProg, MemEEProm memEEProm, short instr) {
		int r = EmuMath.getRrAddrCat1(instr);
		int d = EmuMath.getRdAddrCat1(instr);
		int rd = memData.read(d);
		int rr = memData.read(r);
		int result = rd + rr + EmuMath.boolToInt(StatusRegister.C.read(memData));
		StatusRegister.H.write(EmuMath.isAddCarryBit(rd, rr, result, 3), memData);
		StatusRegister.V.write(EmuMath.isAddOverflow(rd, rr, result), memData);
		StatusRegister.N.write((result & 0x80) != 0, memData);
		StatusRegister.S.write(StatusRegister.N.read(memData) ^ StatusRegister.V.read(memData), memData);
		StatusRegister.Z.write((result & 0xff) == 0, memData);
		StatusRegister.C.write(EmuMath.isAddCarryBit(rd, rr, result, 7), memData);
		memData.write(d, (byte) result);
	}
}

class ComADD extends Command {
	@Override
	public void run(MemData memData, MemProg memProg, MemEEProm memEEProm, short instr) {
		int r = EmuMath.getRrAddrCat1(instr);
		int d = EmuMath.getRdAddrCat1(instr);
		int rd = memData.read(d);
		int rr = memData.read(r);
		int result = rd + rr;
		StatusRegister.H.write(EmuMath.isAddCarryBit(rd, rr, result, 3), memData);
		StatusRegister.V.write(EmuMath.isAddOverflow(rd, rr, result), memData);
		StatusRegister.N.write((result & 0x80) != 0, memData);
		StatusRegister.S.write(StatusRegister.N.read(memData) ^ StatusRegister.V.read(memData), memData);
		StatusRegister.Z.write((result & 0xff) == 0, memData);
		StatusRegister.C.write(EmuMath.isAddCarryBit(rd, rr, result, 7), memData);
		memData.write(d, (byte) result);
	}
}

class ComAND extends Command {
	@Override
	public void run(MemData memData, MemProg memProg, MemEEProm memEEProm, short instr) {
		int r = EmuMath.getRrAddrCat1(instr);
		int d = EmuMath.getRdAddrCat1(instr);
		int rd = memData.read(d);
		int rr = memData.read(r);
		int result = rd & rr;
		StatusRegister.V.write(false, memData);
		StatusRegister.N.write((result & 0x80) != 0, memData);
		StatusRegister.S.write(StatusRegister.N.read(memData) ^ StatusRegister.V.read(memData), memData);
		StatusRegister.Z.write((result & 0xff) == 0, memData);
		memData.write(d, (byte) result);
	}
}

class ComCP extends Command {
	@Override
	public void run(MemData memData, MemProg memProg, MemEEProm memEEProm, short instr) {
		int r = EmuMath.getRrAddrCat1(instr);
		int d = EmuMath.getRdAddrCat1(instr);
		int rd = memData.read(d);
		int rr = memData.read(r);
		int result = rd - rr;
		StatusRegister.H.write(EmuMath.isSubCarryBit(rd, rr, result, 3), memData);
		StatusRegister.V.write(EmuMath.isSubOverflow(rd, rr, result), memData);
		StatusRegister.N.write((result & 0x80) != 0, memData);
		StatusRegister.S.write(StatusRegister.N.read(memData) ^ StatusRegister.V.read(memData), memData);
		StatusRegister.Z.write((result & 0xff) == 0, memData);
		StatusRegister.C.write(EmuMath.isSubCarryBit(rd, rr, result, 7), memData);
		memData.write(d, (byte) result);
	}
}

class ComCPC extends Command {
	@Override
	public void run(MemData memData, MemProg memProg, MemEEProm memEEProm, short instr) {
		int r = EmuMath.getRrAddrCat1(instr);
		int d = EmuMath.getRdAddrCat1(instr);
		int rd = memData.read(d);
		int rr = memData.read(r);
		int result = rd - rr - EmuMath.boolToInt(StatusRegister.C.read(memData));
		StatusRegister.H.write(EmuMath.isSubCarryBit(rd, rr, result, 3), memData);
		StatusRegister.V.write(EmuMath.isSubOverflow(rd, rr, result), memData);
		StatusRegister.N.write((result & 0x80) != 0, memData);
		StatusRegister.S.write(StatusRegister.N.read(memData) ^ StatusRegister.V.read(memData), memData);
		StatusRegister.Z.write((result & 0xff) == 0 ? StatusRegister.Z.read(memData) : false, memData);
		StatusRegister.C.write(EmuMath.isSubCarryBit(rd, rr, result, 7), memData);
		memData.write(d, (byte) result);
	}
}

class ComCPSE extends Command {
	@Override
	public void run(MemData memData, MemProg memProg, MemEEProm memEEProm, short inst) {
		throw new UnsupportedOperationException("Not supported yet. ComCPSE");
	}
}

class ComEOR extends Command {
	@Override
	public void run(MemData memData, MemProg memProg, MemEEProm memEEProm, short inst) {
		int r = EmuMath.getRrAddrCat1(inst);
		int d = EmuMath.getRdAddrCat1(inst);
		int rd = memData.read(d);
		int rr = memData.read(r);
		int result = rd ^ rr;
		StatusRegister.V.write(false, memData);
		StatusRegister.N.write((result & 0x80) != 0, memData);
		StatusRegister.S.write(StatusRegister.N.read(memData) ^ StatusRegister.V.read(memData), memData);
		StatusRegister.Z.write((result & 0xff) == 0, memData);
		memData.write(d, (byte) result);
	}
}

class ComMOV extends Command {
	@Override
	public void run(MemData memData, MemProg memProg, MemEEProm memEEProm, short inst) {
		int r = EmuMath.getRrAddrCat1(inst);
		int d = EmuMath.getRdAddrCat1(inst);
		int rr = memData.read(r);
		memData.write(d, (byte) rr);
	}
}

class ComMUL extends Command {
	@Override
	public void run(MemData memData, MemProg memProg, MemEEProm memEEProm, short inst) {
		int r = EmuMath.getRrAddrCat1(inst);
		int d = EmuMath.getRdAddrCat1(inst);
		int rr = (0xff & memData.read(r));
		int rd = (0xff & memData.read(d));
		int result = rr * rd;
		StatusRegister.C.write((result & 0x8000) != 0, memData);
		StatusRegister.Z.write((result & 0xff) == 0, memData);
		memData.writeShort(0, 1, (short) result);
	}
}

class ComOR extends Command {
	@Override
	public void run(MemData memData, MemProg memProg, MemEEProm memEEProm, short inst) {
		int r = EmuMath.getRrAddrCat1(inst);
		int d = EmuMath.getRdAddrCat1(inst);
		int rd = memData.read(d);
		int rr = memData.read(r);
		int result = rr | rd;
		StatusRegister.V.write(false, memData);
		StatusRegister.N.write((result & 0x80) != 0, memData);
		StatusRegister.S.write(StatusRegister.N.read(memData) ^ StatusRegister.V.read(memData), memData);
		StatusRegister.Z.write((result & 0xff) == 0, memData);
		memData.write(rd, (byte) result);
	}
}

class ComSBC extends Command {
	@Override
	public void run(MemData memData, MemProg memProg, MemEEProm memEEProm, short inst) {
		throw new UnsupportedOperationException("Not supported yet. SBC");
	}
}

class ComSUB extends Command {
	@Override
	public void run(MemData memData, MemProg memProg, MemEEProm memEEProm, short inst) {
		throw new UnsupportedOperationException("Not supported yet. SUB");
	}
}
