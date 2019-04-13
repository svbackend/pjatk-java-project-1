public interface IExportable {
    static String getExportDelimiter() {
        return ";";
    }

    String exportAsString();
}