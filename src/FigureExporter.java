import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class FigureExporter {
    static void exportToFile(ArrayList<Figure> figures, String pathToFile) throws IOException {
        StringBuilder fileContent = new StringBuilder();

        for (Figure figure : figures) {
            fileContent.append(figure.exportAsString());
            fileContent.append("\r\n");
        }

        FileWriter fileWriter = new FileWriter(pathToFile);
        fileWriter.write(fileContent.toString());
        fileWriter.close();
    }
}
