import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class CleanSolution {

	public static void main(String[] args) throws IOException {
		final Set<String> allWords = readDictionary();

		final Set<String> sixLetterWords = getAllSixLetterWords(allWords);

		final Set<List<String>> allTuples = getAllTuples(sixLetterWords);

		final Set<List<String>> allValidCombinations = getAllTuplesContainedInSet(allWords, allTuples);

		printTuples(allValidCombinations);
	}

	private static Set<String> readDictionary() throws IOException {
		return new HashSet<>(Files.readAllLines(Paths.get("wordlist.txt"), StandardCharsets.ISO_8859_1));
	}

	private static Set<String> getAllSixLetterWords(Set<String> allWords) {
		return allWords.stream()
				.filter(word -> word.length() == 6)
				.collect(Collectors.toSet());
	}

	private static Set<List<String>> getAllTuples(Set<String> sixLetterWords) {
		return sixLetterWords.stream()
					.map(CleanSolution::convertToTuples)
					.flatMap(Collection::stream)
					.collect(Collectors.toSet());
	}

	private static Set<List<String>> convertToTuples(String word) {
		final HashSet<List<String>> wordTuples = new HashSet<>();
		for (int i = 1; i < word.length(); i++) {
			final ArrayList<String> tuple = new ArrayList<>();
			tuple.add(word.substring(0, i));
			tuple.add(word.substring(i));
			wordTuples.add(tuple);
		}
		return wordTuples;
	}

	private static Set<List<String>> getAllTuplesContainedInSet(Set<String> allWords, Set<List<String>> allTuples) {
		return allTuples.stream()
				.filter(allWords::containsAll)
				.collect(Collectors.toSet());
	}

	private static void printTuples(Set<List<String>> allValidCombinations) {
		allValidCombinations.stream()
				.map(tuple -> String.format("%s, %s -> %s", tuple.get(0), tuple.get(1), tuple.get(0)+tuple.get(1)))
				.forEach(System.out::println);
	}
}
