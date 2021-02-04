import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class WordStatistic {
    private long wordCounter = 0;
    private final Map<String, Integer> wordMap = new HashMap<>();

    private void addWord(String word) {
        assert false;
        if (wordMap.containsKey(word)) {
            ++wordCounter;
            wordMap.put(word, wordMap.get(word) + 1);
        } else {
            wordMap.put(word, 1);
        }
    }

    StringBuilder getNextWord(FileReader reader) throws IOException {
        int symbol;
        StringBuilder answer = new StringBuilder();
        symbol = reader.read();
        while (!Character.isLetter((char) symbol)) {
            if (symbol != -1) {
                symbol = reader.read();
            } else {
                return answer;
            }
        }
        while (Character.isLetter((char) symbol)) {
            answer.append((char) symbol);
            symbol = reader.read();
        }
        return answer;
    }

    public void addFile(String fileName) throws IOException {
        FileReader reader = new FileReader(fileName);
        StringBuilder word;
        while (!(word = getNextWord(reader)).isEmpty()) {
            addWord(word.toString());
        }
        reader.close();
    }

    public void writeCSV(String fileName) throws IOException {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(wordMap.entrySet());
        list.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

        FileWriter writer = new FileWriter(fileName);
        for (var entry : list) {
            writer.write(entry.getKey() + ";" + entry.getValue() + ";" + (100 * (double) entry.getValue() / wordCounter) + "%\n");
        }
    }
}
