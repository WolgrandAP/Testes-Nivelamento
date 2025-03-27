import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Principal {

    public static void main(String[] args) {

        String pdf1 = "Anexo1.pdf";
        String localSalvoCsv = "tabela-csv";

        try(PDDocument documento = PDDocument.load(new File(pdf1))) {
            PDFTextStripper pdf = new PDFTextStripper();
            String textoExtraido = pdf.getText(documento);

            
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
