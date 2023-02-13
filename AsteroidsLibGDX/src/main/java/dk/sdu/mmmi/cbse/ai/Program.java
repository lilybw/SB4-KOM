package dk.sdu.mmmi.cbse.ai;

import dk.sdu.mmmi.cbse.util.Function2;

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
        boolean run(R context, int timesRan, float deltaT);
    }

    @FunctionalInterface
    public interface ContextChangeFunction<T,R>{
        R gather(T object, R previousContext);
    }

    private final ProgramScheduler<T> manager;
    private final MachineCode<R> code;
    private final ContextChangeFunction<T,R> contextSwitchFunc;
    private R context;
    private int timesRan = 0;
    private final float executionDelay;
    private String name = "unknown";

    public Program(ProgramScheduler<T> manager,  float executionDelay, String name, ContextChangeFunction<T,R> contextSwitchFunc, MachineCode<R> code)
    {
        this.code = Objects.requireNonNull(code);
        this.manager = manager;
        this.contextSwitchFunc = Objects.requireNonNull(contextSwitchFunc);
        this.executionDelay = executionDelay;
        this.name = name;
    }

    public Program(ProgramScheduler<T> manager,  float executionDelay, ContextChangeFunction<T,R> contextSwitchFunc, MachineCode<R> code)
    {
        this(manager,executionDelay,"unknown program",contextSwitchFunc,code);
    }

    public float prepare(T obj)
    {
        context = contextSwitchFunc.gather(obj, context);
        return executionDelay;
    }

    public void execute(float deltaT)
    {
        if(code.run(context,timesRan,deltaT)) onEOF();
        timesRan++;
    }

    private void onEOF()
    {
        timesRan = 0;
        context = null;
        manager.advance();
    }

    @Override
    public String toString()
    {
        return "Program{name: "+name+"}";
    }

}
