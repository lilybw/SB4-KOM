package dk.sdu.mmmi.cbse.ai;

import java.util.Objects;
import java.util.function.Function;

/**
 * R is the type of the pageTable which should be retrieved from an object of type T using the context switch function.
 */
public class Program<R,T> {

    @FunctionalInterface
    public interface MachineCode<R>{
        /**
         * @return true if the program should not be repeated
         */
        boolean run(R ram, int timesRan);
    }

    private final ProgramScheduler manager;
    private final MachineCode<R> code;
    private final Function<T,R> contextSwitchFunc;
    private R pageTable;
    private int timesRan = 0;

    public Program(ProgramScheduler manager, MachineCode<R> code, Function<T,R> contextSwitchFunc)
    {
        this.code = Objects.requireNonNull(code);
        this.manager = manager;
        this.contextSwitchFunc = Objects.requireNonNull(contextSwitchFunc);
    }

    public void prepare(T obj)
    {
        pageTable = contextSwitchFunc.apply(obj);
    }

    public void execute()
    {
        if(code.run(pageTable,timesRan)) onEOF();
        timesRan++;
    }

    private void onEOF()
    {
        timesRan = 0;
        manager.advance();
    }

}
