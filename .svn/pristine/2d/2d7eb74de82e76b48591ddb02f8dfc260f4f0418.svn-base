package emulator.command;

import java.util.HashMap;

public class CommandSet {
    private static HashMap<Integer, CommandEnum> commandMap = new HashMap<>();
    private static boolean listBuilt = false;
    public static void buildList() {
        for (CommandEnum e : CommandEnum.values())
            commandMap.put(e.getOpcode(), e);
        listBuilt = true;
    }
    public static Command getCmd(int mask) {
        if (!listBuilt) {
            System.out.println("***WARN: You did not call CommandSet.buildList()! First operation will be slow.");
            CommandSet.buildList();
        }
        return commandMap.get(mask).getCom();
    }
	public static boolean hasCmd(int mask) {
		return (commandMap.get(mask) != null);
	}
	
    private CommandSet() {
    }
}
