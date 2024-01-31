package com.blog.Controller;

import java.awt.Color;
import java.io.IOException;


import com.blog.Entity.Post;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class UserPDFExporter {
    private List<Post> listPosts;  // Assuming Post is the class for your objects

    public UserPDFExporter(List<Post> listPosts) {
        this.listPosts = listPosts;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Post ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Title", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Body", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (Post post : listPosts) {
            table.addCell(String.valueOf(post.getId()));
            table.addCell(post.getTitle());
            table.addCell(post.getBody());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("List of Posts", font);
        p.setAlignment(Element.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(3);  // Assuming there are 3 columns for Post (ID, Title, Body)
        table.setWidthPercentage(100f);
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);
        document.close();
    }
}

