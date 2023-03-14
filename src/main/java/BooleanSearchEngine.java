import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    private final Map<String, List<PageEntry>> DATA = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException, NullPointerException {
        for (File pdf : Objects.requireNonNull(pdfsDir.listFiles())) {
            PdfDocument doc = new PdfDocument(new PdfReader(pdf));
            for (int i = 0; i < doc.getNumberOfPages(); i++) {
                int currentPage = i + 1;
                String text = PdfTextExtractor.getTextFromPage(doc.getPage(currentPage));
                String[] words = text.split("\\P{IsAlphabetic}+");

                Map<String, Integer> freqs = new HashMap<>(); // мапа, где ключом будет слово, а значением - частота
                for (var word : words) { // перебираем слова
                    if (word.isEmpty()) {
                        continue;
                    }
                    word = word.toLowerCase();
                    freqs.put(word, freqs.getOrDefault(word, 0) + 1);
                }

                for (var entry : freqs.entrySet()) {
                    List<PageEntry> found;
                    if (DATA.containsKey(entry.getKey())) {
                        found = DATA.get(entry.getKey());
                    } else {
                        found = new ArrayList<>();
                    }
                    found.add(new PageEntry(pdf.getName(), currentPage, entry.getValue()));
                    found.sort(Collections.reverseOrder());
                    DATA.put(entry.getKey(), found);
                }
            }
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        return DATA.get(word.toLowerCase());
    }
}
