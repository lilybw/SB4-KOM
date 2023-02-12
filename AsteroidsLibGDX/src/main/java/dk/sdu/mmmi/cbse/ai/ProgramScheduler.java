package dk.sdu.mmmi.cbse.ai;

import dk.sdu.mmmi.cbse.main.Game;

import java.util.ArrayList;
import java.util.List;

public class ProgramScheduler<T>{

    @FunctionalInterface
    public interface AdvancementAlgorithm{
        int apply(ProgramScheduler manager);
    }
    public static AdvancementAlgorithm RANDOM = e -> Game.rand.nextInt(e.programs.length);
    public static AdvancementAlgorithm SEQUENTIAL = e -> (e.programCounter + 1) % e.programs.length;

    @SuppressWarnings("rawtypes")
    public static Program NO_OP = new Program(null,(e,count) -> false, e -> null);

    private final AdvancementAlgorithm algorithm;
    private Program<?,T>[] programs;
    private int programCounter = 0;
    private Program<?,T> currentProgram;

    public ProgramScheduler(AdvancementAlgorithm algorithm)
    {
        this.algorithm = algorithm;
    }

    public ProgramScheduler(AdvancementAlgorithm algorithm, Program<?,T>[] programs)
    {
        this.algorithm = algorithm;
        this.programs = programs;
        currentProgram = programs[0];
    }

    //Called by each program when they reach EOF
    public void advance(){
        programCounter = algorithm.apply(this);
        currentProgram = programs[programCounter];
    }

    public void update()
    {

    }

    public void setPrograms(Program<?, T>[] programs) {
        this.programs = programs;
    }


    public int getCounter()
    {
        return programCounter;
    }

}
