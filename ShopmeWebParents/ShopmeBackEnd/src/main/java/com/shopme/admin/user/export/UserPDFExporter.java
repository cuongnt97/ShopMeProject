package com.shopme.admin.user.export;


import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import com.shopme.common.entities.User;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;


public class UserPDFExporter extends AbtractExporter {

    public void exportPDF(List<User> listUsers, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "application/pdf", ".pdf");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        //Header
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.blue);
        Paragraph paragraph = new Paragraph("List Users", font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        //Content pdf file
        PdfPTable pdfPTable = new PdfPTable(6);
        pdfPTable.setWidthPercentage(100f);
        pdfPTable.setSpacingBefore(10);
        pdfPTable.setWidths(new float[] {1.0f, 3.5f, 3.0f, 3.0f, 3.0f, 1.7f});


        writeTableHeader(pdfPTable);
        writeTableData(pdfPTable, listUsers);
        document.add(pdfPTable);


        //Close stream
        document.close();
    }

    private void writeTableHeader(PdfPTable pdfPTable){
        PdfPCell pdfPCell = new PdfPCell();
        pdfPCell.setBackgroundColor(Color.BLUE);
        pdfPCell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.white);

        pdfPCell.setPhrase(new Phrase("ID", font));
        pdfPTable.addCell(pdfPCell);

        pdfPCell.setPhrase(new Phrase("Email", font));
        pdfPTable.addCell(pdfPCell);

        pdfPCell.setPhrase(new Phrase("First Name", font));
        pdfPTable.addCell(pdfPCell);

        pdfPCell.setPhrase(new Phrase("Last Name", font));
        pdfPTable.addCell(pdfPCell);

        pdfPCell.setPhrase(new Phrase("Roles", font));
        pdfPTable.addCell(pdfPCell);

        pdfPCell.setPhrase(new Phrase("Enable", font));
        pdfPTable.addCell(pdfPCell);

    }

    private void writeTableData(PdfPTable pdfPTable, List<User> listUsers){
        for (User user : listUsers) {
            pdfPTable.addCell(String.valueOf(user.getRecid()));
            pdfPTable.addCell(String.valueOf(user.getEmail()));
            pdfPTable.addCell(String.valueOf(user.getFirstName()));
            pdfPTable.addCell(String.valueOf(user.getLastName()));
            pdfPTable.addCell(String.valueOf(user.getRoles().toString()));
            pdfPTable.addCell(String.valueOf(String.valueOf(user.isEnable())));
        }
    }
}
