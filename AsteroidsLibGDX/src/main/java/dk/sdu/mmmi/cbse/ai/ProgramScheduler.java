package dk.sdu.mmmi.cbse.ai;

import dk.sdu.mmmi.cbse.main.Game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProgramScheduler<T>{

    @FunctionalInterface
    public interface AdvancementAlgorithm{
        int apply(ProgramScheduler manager);
    }
    public static AdvancementAlgorithm RANDOM = e -> Game.rand.nextInt(e.programs.size());
    public static AdvancementAlgorithm SEQUENTIAL = e -> (e.programCounter + 1) % e.programs.size();

    @SuppressWarnings("rawtypes")
    public static Program NO_OP(float delay) {
        return new Program(null,delay,"no operation", (e1,e2) -> null, (e,count,deltaT) -> true);
    }

    private final AdvancementAlgorithm algorithm;
    private List<Program<?,T>> programs;
    private int programCounter = 0;
    private Program<?,T> currentProgram;
    private float timeSinceLastStep = 0;
    private float executionDelayOfCurrent = .3f;
    private boolean currentIsPrepared = false;

    public ProgramScheduler(AdvancementAlgorithm algorithm)
    {
        this(algorithm, null);
    }

    public ProgramScheduler(AdvancementAlgorithm algorithm, List<Program<?,T>> programs)
    {
        if(programs == null){
            programs = new ArrayList<>();
            programs.add(NO_OP(5));
        }
        this.algorithm = algorithm;
        this.programs = programs;
        currentProgram = programs.get(0);
    }


    public void setPrograms(List<Program<?, T>> programs) {
        this.programs = programs;
        advance();
    }

    //Called by each program when they reach EOF
    public void advance(){
        programCounter = algorithm.apply(this);
        currentProgram = programs.get(programCounter);
        timeSinceLastStep = 0;
        currentIsPrepared = false;
    }

    public void update(T obj, float deltaT)
    {
        timeSinceLastStep += deltaT;
        if(!currentIsPrepared){
            executionDelayOfCurrent = currentProgram.prepare(obj);
            currentIsPrepared = true;
            timeSinceLastStep = 0;
        }else{
            if(timeSinceLastStep >= executionDelayOfCurrent){
                currentProgram.execute(deltaT);
            }
        }
    }

    public int getCounter()
    {
        return programCounter;
    }

}
