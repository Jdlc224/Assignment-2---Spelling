
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Spelling {
    List<List<String>> output = new ArrayList<>();
    Trie tree = new Trie();
    boolean check = false;
    String[] columnNames = new String[2];
    int checker = 0;
    
    public void fetchData() throws IOException {
        File file = new File("C:\\Users\\Lenovo\\Desktop\\unigram_freq.csv");
        try {
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);
            Iterator<Row> itr = sheet.iterator();

            while (itr.hasNext()) {
                String word = "";
                double frequency = 0;
                Row row = itr.next();
                Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    if (check == false) {
                        columnNames[0] = cell.getStringCellValue();
                        cell = cellIterator.next();
                        columnNames[1] = cell.getStringCellValue();
                        cell = cellIterator.next();
                        check = true;
                        break;
                    }

                    if (checker == 0) {
                        word = cell.getStringCellValue();
                        checker++;
                    } else if (checker == 1) {
                        frequency = cell.getNumericCellValue();
                        checker++;
                    }
                }
                if (!"".equals(word)) {
                    tree.insert(word);
                }
                checker = 0;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

    }
    
    public List<List<String>> suggest(String token, int count){
        List<List<String>> list = new ArrayList<>();
        Trie prefixTree = new Trie();

        prefixTree.insert("car");
        prefixTree.insert("cat");
        prefixTree.insert("cab");

        if (prefixTree.startsWith(token) == true) {
            TrieNode tn = prefixTree.searchNode(token);
            prefixTree.wordsFinderTraversal(tn, 0);
            prefixTree.displayFoundWords(count);

        }
        return list;
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the token:");
        String token = input.next();
        System.out.println("Enter the count:");
        int count = input.nextInt();
        
        Spelling obj = new Spelling();
        obj.suggest(token, count);
    }
}
