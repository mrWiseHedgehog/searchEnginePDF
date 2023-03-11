public class PageEntry implements Comparable<PageEntry> {
    private final String pdfName;
    private final int page;
    private final int count;

    public PageEntry(String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
    }

    @Override
    public int compareTo(PageEntry pageEntry) {
        return this.count - pageEntry.count;
    }

    @Override
    public String toString() {
        return "PageEntry {PDF = " + pdfName + ", page is " + page + ", count is " + count;
    }
}
