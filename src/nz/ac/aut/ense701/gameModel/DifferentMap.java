package nz.ac.aut.ense701.gameModel;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author joshl
 * @version 2017
 */
public class DifferentMap {
    private static final String MAP_FILE_NAME = "IslandData.txt";
    private static final String TEXT_FORMAT = "UTF-8";
    private final int mapRows;
    private final int mapCols;
    private final MapDataTypes mapDataTypes;
    
    public DifferentMap(int mapRows, int mapCols) {
        this.mapRows = mapRows;
        this.mapCols = mapCols;
        mapDataTypes = new MapDataTypes();
        mapDataTypes.loadTypesFromFile();
    }
    
    public PrintWriter CreateFile() {
        PrintWriter pw = null;
        
        try {
            pw = new PrintWriter(MAP_FILE_NAME, TEXT_FORMAT);
        } catch (FileNotFoundException ex) {
            System.out.println("File not found " + ex.getMessage());
        } catch (UnsupportedEncodingException ex) {
            System.out.println("File encoding not supported " + ex.getMessage());
        }
        
        return pw;
    }
    
    
}
