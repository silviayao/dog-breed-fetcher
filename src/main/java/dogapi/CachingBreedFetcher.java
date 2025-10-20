package dogapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CachingBreedFetcher implements BreedFetcher {
    private final BreedFetcher delegate;
    private final Map<String, List<String>> cache = new HashMap<>();
    private int callsMade = 0;

    public CachingBreedFetcher(BreedFetcher delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        final String key = (breed == null) ? "" : breed.toLowerCase().trim();

        // Cache hit → no delegate call and no increment
        List<String> hit = cache.get(key);
        if (hit != null) return hit;

        // Cache miss → we WILL call the delegate exactly once
        callsMade++;  // ✅ count the delegate call regardless of success/failure
        try {
            List<String> result = delegate.getSubBreeds(breed);  // may throw
            // Cache only successful results
            cache.put(key, result);
            return result;
        } catch (BreedNotFoundException e) {
            // Do NOT cache failures; just propagate
            throw e;
        }
    }

    public int getCallsMade() {
        return callsMade;
    }
}