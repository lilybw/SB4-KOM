package dk.sdu.mmmi.cbse.managers;

import dk.sdu.mmmi.cbse.util.Function2;
import dk.sdu.mmmi.cbse.util.Ref;
import dk.sdu.mmmi.cbse.util.VoidFunc;
import dk.sdu.mmmi.cbse.util.VoidFunc2;

import java.util.Objects;
import java.util.function.Function;

public class AbilityManager<I,B> {

    private final VoidFunc2<Ref<B>, Float> behaviour;
    private final Function2<Ref<B>,I,Boolean> condition;
    private final Ref<B> refTrackedVariable;

    /**
     *
     * @param trackedVariable Any single simple type (or complex) of which a reference is made accessible in the condition and behaviour
     * @param condition Any function based on the tracked variable and deltaT that determines if the ability behaviour should be executed
     * @param behaviour
     */
    public AbilityManager(B trackedVariable, Function2<Ref<B>,I,Boolean> condition, VoidFunc2<Ref<B>,Float> behaviour)
    {
        this.behaviour = Objects.requireNonNull(behaviour);
        this.condition = Objects.requireNonNull(condition);
        this.refTrackedVariable = new Ref<>(trackedVariable);
    }

    public void evaluate(I inputs, float deltaT)
    {
        if(condition.apply(refTrackedVariable, inputs)){
            behaviour.apply(refTrackedVariable, deltaT);
        }
    }


}
