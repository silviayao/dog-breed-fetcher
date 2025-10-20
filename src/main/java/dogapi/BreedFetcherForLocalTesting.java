package dogapi;

import java.util.List;

/**
 * A minimal implementation of the BreedFetcher interface for testing purposes.
 * It increments its internal counter once per call. It returns two sub-breeds
 * for "hound" and throws for anything else.
 */
public class BreedFetcherForLocalTesting implements BreedFetcher {
    private int callCount = 0;

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        callCount++; // exactly once per invocation
        if ("hound".equalsIgnoreCase(breed)) {
            return List.of("afghan", "basset");
        }
        throw new BreedNotFoundException(breed);
    }

    public int getCallCount() {
        return callCount;
    }
}