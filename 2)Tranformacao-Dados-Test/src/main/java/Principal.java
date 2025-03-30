import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import com.opencsv.CSVWriter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Principal {
    public static void main(String[] args) {
        String pdfFile = "Anexo1.pdf";
        String csvFile = "Anexo1.csv";
        String zipFile = "Teste_Wolgrand.zip";

        try {
            // Extrai texto do PDF
            PDDocument document = PDDocument.load(new File(pdfFile));
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            document.close();


            // Processa os dados extraídos e salva em CSV
            FileWriter fileWriter = new FileWriter(csvFile);
            CSVWriter csvWriter = new CSVWriter(fileWriter);

            // Filtragem dos dados com base no padrão
            List<String[]> filteredData = filtrarDados(text);

            // Escrevendo no CSV
            for (String[] row : filteredData) {
                System.out.println(String.join(" | ", row));
                csvWriter.writeNext(row);
            }
            csvWriter.close();

            // Compacta o CSV em um ZIP
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            File fileToZip = new File(csvFile);
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            zipOut.close();
            fis.close();
            fos.close();

            // Remove o CSV original
            fileToZip.delete();

            System.out.println("Processo concluído! Arquivo salvo como " + zipFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String[]> filtrarDados(String text) {
        List<String[]> filteredData = new ArrayList<>();
        String[] lines = text.split("\n");

        // Expressão regular para reconhecer o padrão PROCEDIMENTO
        Pattern pattern = Pattern.compile(".*\\bPROCEDIMENTO\\b.*\\bRN\\b.*(OD|AMB|HCO|HSO|REF|PAC|DUT|SUBGRUPO|GRUPO|CAPÍTULO).*", Pattern.CASE_INSENSITIVE);

        for (String line : lines) {
            if (pattern.matcher(line).matches()) {
                String[] columns = line.split("\s{2,}"); // Assume múltiplos espaços como separador
                for (int i = 0; i < columns.length; i++) {
                    columns[i] = substituirAbreviacoes(columns[i]);
                }
                filteredData.add(columns);
            }
        }
        return filteredData;
    }

    private static String substituirAbreviacoes(String texto) {
        texto = texto.replace("OD", "Odontologia");
        texto = texto.replace("AMB", "Ambulatorial");
        return texto;
    }
}
