import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class FigureImporter {
    static ArrayList<Figure> importFromFile(String pathToFile) throws IOException {
        ArrayList<Figure> figures = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(pathToFile));
        String line = reader.readLine();
        while (line != null && !line.equals("")) {
            figures.add(FigureFactory.importFigureFromString(line));
            line = reader.readLine();
        }
        reader.close();

        return figures;
    }
}