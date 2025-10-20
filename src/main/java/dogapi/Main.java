package dogapi;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        String breed = "hound";
        BreedFetcher fetcher = new CachingBreedFetcher(new BreedFetcherForLocalTesting());
        System.out.println(breed + " has " + getNumberOfSubBreeds(breed, fetcher) + " sub breeds");

        breed = "cat";
        System.out.println(breed + " has " + getNumberOfSubBreeds(breed, fetcher) + " sub breeds");
    }

    /**
     * Return the number of sub breeds that the given dog breed has according to the
     * provided fetcher.
     * @param breed the name of the dog breed
     * @param breed
     * Fetcher the breedFetcher to use
     * @return the number of sub breeds. Zero should be returned if there are no sub breeds
     * returned by the fetcher
     */
    public static int getNumberOfSubBreeds(String breed, BreedFetcher breedFetcher) {
        try {
            List<String> subs = breedFetcher.getSubBreeds(breed);
            return subs.size();
        } catch (BreedFetcher.BreedNotFoundException e) {
            return 0; //return 0
        }
    }
}