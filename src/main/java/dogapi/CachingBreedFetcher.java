package dogapi;

import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    private final BreedFetcher fetcher;
    private final Map<String, List<String>> cache;
    private int callsMade = 0;

    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.fetcher = fetcher;
        this.cache = new HashMap<>();
    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        // Normalize so case/whitespace variants use the same cache entry
        final String key = (breed == null) ? "" : breed.toLowerCase().trim();

        // 1) Cache hit → return immediately, no delegate call
        List<String> cached = cache.get(key);
        if (cached != null) {
            return cached;
        }

        // 2) Cache miss → exactly ONE delegate call
        callsMade++; // (some tests also check the delegate’s own counter—this doesn’t affect it)
        List<String> result = fetcher.getSubBreeds(breed); // may throw

        // 3) Cache only successful lookups
        cache.put(key, result);
        return result;
    }

    public int getCallsMade() {
        return callsMade;
    }
}