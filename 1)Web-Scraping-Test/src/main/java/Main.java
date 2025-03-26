import java.io.*;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {

        String pdfUrl1 = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos/Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf";
        String pdfUrl2 = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos/Anexo_II_DUT_2021_RN_465.2021_RN628.2025_RN629.2025.pdf";

        String file1 = "AnexoI.pdf";
        String file2 = "AnexoII.pdf";

        try {

            downloadFile(pdfUrl1, file1);
            downloadFile(pdfUrl2, file2);

            zipFiles("anexos.zip", file1, file2);
            System.out.println("Download e compactação concluídos. Arquivo gerado: anexos.zip");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void downloadFile(String fileUrl, String fileName) throws IOException {
        System.out.println("Baixando: " + fileUrl);
        URL url = new URL(fileUrl);
        try (InputStream in = new BufferedInputStream(url.openStream());
             FileOutputStream fileOut = new FileOutputStream(fileName)) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                fileOut.write(buffer, 0, bytesRead);
            }
            System.out.println(fileName + " foi baixado com sucesso.");
        }
    }


    private static void zipFiles(String zipFileName, String file1, String file2) throws IOException {
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFileName))) {

            try (FileInputStream fis = new FileInputStream(file1)) {
                ZipEntry zipEntry = new ZipEntry(file1);
                zipOut.putNextEntry(zipEntry);

                byte[] buffer = new byte[4096];
                int length;
                while ((length = fis.read(buffer)) >= 0) {
                    zipOut.write(buffer, 0, length);
                }
            }

            try (FileInputStream fis = new FileInputStream(file2)) {
                ZipEntry zipEntry = new ZipEntry(file2);
                zipOut.putNextEntry(zipEntry);

                byte[] buffer = new byte[4096];
                int length;
                while ((length = fis.read(buffer)) >= 0) {
                    zipOut.write(buffer, 0, length);
                }
            }
        }
    }

}
