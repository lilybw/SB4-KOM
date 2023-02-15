package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.entities.IEntity;
import dk.sdu.mmmi.cbse.util.ManagedBufferedCollection;

import java.util.HashSet;

public class World {

    public final ManagedBufferedCollection<IEntity> entities = new ManagedBufferedCollection<>(new HashSet<>());
    public float xMin, yMin, xMax, yMax;


}
