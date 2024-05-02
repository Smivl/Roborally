package model.board.Maps;

import com.opencsv.CSVReader;
import model.board.components.Orientation;
import model.board.components.Position;
import model.board.tile.*;

import java.io.FileReader;
import java.util.List;
import java.util.Locale;

public class MapTranslator {

    private static String[][] readCSV(String filePath) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> lines = reader.readAll();
            int nRows = lines.size();
            int nColumns = lines.get(0).length;
            for(int i = 0; i < lines.size(); i++){
                String[] line = lines.get(i);
                if (line.length != nColumns) {
                    throw new IllegalArgumentException("CSV file is not n x n. The given file has " +
                            line.length + " columns at line " + i + "! Expected " + nColumns + ".");
                }
            }

            String[][] matrix = new String[nRows][nColumns];
            for (int i = 0; i<nRows; i++){
                matrix[i]=lines.get(i);
            }
            return matrix;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Tile[][] csvToMap(String filePath) {
        try {
            String[][] csv = readCSV(filePath);
            if (csv == null) {
                return null;
            }
            int nRows = csv.length;
            int nColumns = csv[0].length;
            Tile[][] board = new Tile[nRows][nColumns];
            for (int i = 0; i < nRows; i++) {
                for (int j = 0; j < nColumns; j++) {
                    board[i][j] = stringToTile(csv[i][j], new Position(j, nRows - 1 - i));
                    System.out.println("Tile at position " + new Position(j, nRows - 1 - i) + " is " + csv[i][j]);
                }
            }
            return board;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Tile stringToTile(String string, Position position) throws Exception {
        string = string.toLowerCase(Locale.ROOT);
        switch (string) {
            case ("belt_north_1") -> {
                return new Belt(position).setOrientation(Orientation.NORTH).setPower(1);
            }
            case ("belt_south_1") -> {
                return new Belt(position).setOrientation(Orientation.SOUTH).setPower(1);
            }
            case ("belt_east_1") -> {
                return new Belt(position).setOrientation(Orientation.EAST).setPower(1);
            }
            case ("belt_west_1") -> {
                return new Belt(position).setOrientation(Orientation.WEST).setPower(1);
            }
            case ("belt_north_2") -> {
                return new Belt(position).setOrientation(Orientation.NORTH).setPower(2);
            }
            case ("belt_south_2") -> {
                return new Belt(position).setOrientation(Orientation.SOUTH).setPower(2);
            }
            case ("belt_east_2") -> {
                return new Belt(position).setOrientation(Orientation.EAST).setPower(2);
            }
            case ("belt_west_2") -> {
                return new Belt(position).setOrientation(Orientation.WEST).setPower(2);
            }
            case ("laser_vertical") -> {
                return new Laser(position).setHorizontal(false);
            }
            case ("laser_horizontal") -> {
                return new Laser(position).setHorizontal(true);
            }
            case ("gear_clockwise") -> {
                return new RotatingGear(position).setClockwise(true);
            }
            case ("gear_counter") -> {
                return new RotatingGear(position).setClockwise(false);
            }
        }
        return TileFactory.create(string, position);
    }
}

