THE FOLLOWING DOCUMENT DETAILS THE OPERATION OF 
ArduEmu ON A JAVA PLATFORM FOR RASPBERRY PI or
-----------------------------------------------
*Please view with text wrap enabled!

There are two main components to ArduEmu: The Emulator, and the ObjectPool. This is a more detailed summary of the execution of the Arduino emulator. For a summary, please read Summary. We detail the Emulator first:

EMULATOR
--------

The emulator is split into three primary components: the Memory, Processor, and Command packages. We detail each as follows:

  1. MEMORY - The Memory package contains three different types of memory, as well as a highly important Address class. The three main types of memory in the Arudino are data memory, program memory, and EEPROM. The EEPROM can be effectively disregarded, as this holds the bootloader, however, it is there in case it is needed. 
		a. DATA MEMORY - The data memory of the Arduino is held in the MemData class. This class links with the Addresses class, as this class holds specific relevant addresses. Here is implemented multiple read/write functions to a byte[] array of size 0x300 (equivalent to Arduino operating memory space). The ability to read 16-bit short values as well as 8-bit byte values is implemented, as this is critical to referencing program memory. Additionally, for convenience, three special registers referred to as X, Y, and Z registers have been assigned read/write functions.
		b. PROGRAM MEMORY - The program memory of the Arduino is held in the MemProg class. This class implements two things: First, the stored memory, in which the program is stored. This is a short[] of size 0x800. Read/write works similarly to data memory, with one caveat: The program memory implements a pointer. As the assembly code must be read out of program memory, a pointer to the current memory address is needed. From MemProg, the pointer can be read, written to, and advanced. 
		c. MemEEPROM - This memory should never be read from or written to, as this is bootloader space, and should be dealt with programmatically outside of assembly interpretation. However, it has been inserted for reference, should it be needed. This is a byte[] of size 256.
	
	2. PROCESSOR - The Processor package contains two classes: One serves as an interface to the processor itself, and the other serves to record various status registers intrinsic to the Atmel Mega328P processor. All specific opcodes and spec information are laid out at the following link: http://academy.cba.mit.edu/classes/embedded_programming/doc0856.pdf
		a. PROCESSOR - The processor class serves as a container for memory, as well as for the status registers local to the processor. It is from here where instructions can be invoked - however, the computations invoked by assembly calls do not actually occur here. This simply serves as an interface to them. An example main class is included here, and can be run for a short demonstration. 
		b. STATUS REGISTER - The status register enum details internal flags in the processor which modify the output and/or input of assembly calls. The definitions of the status registers are detailed within the class itself, and are available at the above source. The status register is actually a location in data memory; this location is read to and from using masking. 
	
	3. COMMAND - This package deals with the actual hard execution of commands on the processor. Commands are indexed in an enumeration, and loaded into a HashMap, where they can be searched in O(1) time. Both an enum and a HashMap are used for compactness and clarity; while the enum is not strictly necessary, it reads better to list the commands as-is.
		a. COMMAND - The Command abstract class allows the implementation of any given command based off abstract properties; each command must be run by multiple processors, ergo, they must be disconnected from the memory. Thus, memory is an argument to the Command. Though it only has one function, it is necessary to contain this function.
		b. COMMAND ENUMERATION - The CommandEnum abstract class allows the mapping of one command to an appropriate hexadecimal call. This allows for simplified calling of commands; instead of creating a class for a command, it can instead be invoked through CommandEnum: CommandEnum.[operator].getCom() will return the command class. CommandEnum.ADD.getCom().run(param), for instance, will invoke the ADD command on the given parameters.
		c. COMMAND HASHTABLE - The CommandSet class holds a HashTable lookup for Commands. This will simply associate a command with its opcode to facilitate easy lookup. It can also easily verify whether or not a command exists within the given set of commands.

OBJECT POOL
-----------

The ObjectPool is a design pattern used to reduce the load of instantiation. In our example, the Emulator is the object reused by the ObjectPool. The binary compiled file serves as the input to the Emulator, and the Emulator's output is captured using a PoolOutput object. 

This allows us to be able to run up to 35 processors on one VM. Each recycling is inexpensive, and so computation time is preserved. The Ardunio processors do not notice a load shift. When a new program is ready to run, it can simply be attached to an idling Emulator object in the pool.

	1. OBJECT POOL - The ObjectPool class is used to control and manage the object pool. A set of instantiations occurs at runtime initialization; past this point, objects are simply reused. Objects which are in the pool are idling, and can be bound when needed. Objects which are active are not available for use. Every N seconds, hard-coded, the ObjectPool will run a cleanup routine, in which it looks for active objects which have run past their lifetime. These, it kills, and cleans. These objects then can be used again. HOWEVER: The outputs of each object are not destroyed. These are kept in an ArrayList which can be consumed later when necessary (presumably for data dumping). 
	2. POOL OBJECT - The PoolObject class is an abstract class used to create pool objects. A PoolObject as three user-defined functions: init(), step(), and reset(); Init will initialize the object to a useful state. Step will invoke the next step exectuion of the object. Reset will be called on cleanup, as the object must cleanse itself for reuse.
	3. POOL INPUT - The PoolInput class is used to give a PoolObject its inputs. The PoolObject consumes the inputs provided by PoolInput, converted to assembly opcodes, and stores them in the program memory of the Processor.
	4. POOL OUTPUT - The PoolOutput class consumes the outputs from the Emulator. When the Emulator is recycled, the PoolOutput is appended to an ArrayList of PoolOutputs, to be dealt with appropriately.
