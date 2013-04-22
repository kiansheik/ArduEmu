package emulator.command;

import emulator.memory.MemData;
import emulator.memory.MemEEProm;
import emulator.memory.MemProg;

public abstract class Command {
    public abstract void run(MemData memData, MemProg memProg, MemEEProm memEEProm, short inst);
}
