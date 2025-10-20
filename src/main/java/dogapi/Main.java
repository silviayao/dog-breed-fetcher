package dogapi;

import java.util.List;

public class Main {
    public static int getNumberOfSubBreeds(String breed, BreedFetcher fetcher) {
        try {
            List<String> subs = fetcher.getSubBreeds(breed);
            return subs.size();
        } catch (BreedFetcher.BreedNotFoundException e) {
            return 0; // invalid breed → 0
        }
    }

    public static void main(String[] args) {
        // optional demo
    }
}