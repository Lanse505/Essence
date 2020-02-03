package lanse505.essence.utils.generator;

import javax.annotation.Nonnull;

public class WeightedGenerator implements Comparable<WeightedGenerator> {
    public final IGenerator generator;
    public final int weight;

    public WeightedGenerator(IGenerator generator, int weight) {
        this.generator = generator;
        this.weight = weight;
    }

    @Override
    public int compareTo(@Nonnull WeightedGenerator o) {
        int diff = weight - o.weight;
        if(diff != 0)
            return diff;

        return hashCode() - o.hashCode();
    }

    @Override
    public int hashCode() {
        return generator.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || (obj instanceof WeightedGenerator && ((WeightedGenerator) obj).generator == generator);
    }
}
