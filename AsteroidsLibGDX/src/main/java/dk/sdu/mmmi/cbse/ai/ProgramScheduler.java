package dk.sdu.mmmi.cbse.ai;

import dk.sdu.mmmi.cbse.main.Game;

import java.util.ArrayList;
import java.util.List;

public class ProgramScheduler<T>{

    @FunctionalInterface
    public interface AdvancementAlgorithm<T>{
        int apply(ProgramScheduler<T> manager);
    }
    public static AdvancementAlgorithm<?> RANDOM = e -> Game.rand.nextInt(e.programs.length);
    public static AdvancementAlgorithm<?> SEQUENTIAL = e -> (e.programCounter + 1) % e.programs.length;

    private final AdvancementAlgorithm<T> algorithm;
    private final Program<?,T>[] programs;
    private int programCounter = 0;
    private Program<?,T> currentProgram;

    public ProgramScheduler(AdvancementAlgorithm<T> algorithm, Program<?,T>[] programs)
    {
        this.algorithm = algorithm;
        this.programs = programs;
    }

    public void advance(){
        programCounter = algorithm.apply(this);
        currentProgram = programs[programCounter];
    }

    public void update()
    {

    }

    public void setPrograms(Program<?, T>[] programs) {
        
    }


    public int getCounter()
    {
        return programCounter;
    }

}
