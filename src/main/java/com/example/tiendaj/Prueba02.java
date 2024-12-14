package com.example.tiendaj;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;


import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Prueba02 {
    public static void main(String[] args) {
        /*// Datos de muestra para la venta
        String nombreCliente = "Juan Pérez";
        String fechaVenta = "2023-10-08";
        double totalVenta = 500.0;

        // Ruta donde se guardará el archivo PDF
        String rutaPDF = "reporte_venta.pdf";

        // Crear un documento PDF
        Document document;
            document = new Document();

        try {
            // Crear el archivo PDF y asociarlo al documento
            PdfWriter.getInstance(document, new FileOutputStream(rutaPDF));

            // Abrir el documento para escribir
            document.open();

            // Agregar contenido al PDF
            document.add(new Paragraph("Reporte de Venta"));
            document.add(new Paragraph("===================="));
            document.add(new Paragraph("Nombre del Cliente: " + nombreCliente));
            document.add(new Paragraph("Fecha de Venta: " + fechaVenta));
            document.add(new Paragraph("Total de Venta: $" + totalVenta));

            // Cerrar el documento
            document.close();

            System.out.println("El reporte se ha generado exitosamente en: " + rutaPDF);
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }*/

        /*
        // Datos de muestra para la venta en línea
        String nombreCliente = "Juan Pérez";
        String fechaVenta = "2023-10-08";
        double totalVenta = 500.0;

        // Ruta donde se guardará el archivo PDF
        String rutaPDF = "reporte_venta_online.pdf";

        // Crear un documento PDF
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);

        try {
            // Crear el archivo PDF y asociarlo al documento
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(rutaPDF));

            // Agregar encabezado y pie de página personalizados
            HeaderFooterPageEvent event = new HeaderFooterPageEvent();
            writer.setPageEvent(event);

            // Abrir el documento para escribir
            document.open();

            // Agregar contenido al PDF
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA, 14, BaseColor.GRAY);

            Paragraph title = new Paragraph("Informe de Venta Online", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Paragraph subtitle = new Paragraph("Detalles de la Venta", subtitleFont);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subtitle);

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(20f);
            table.setSpacingAfter(20f);

            PdfPCell cell1 = new PdfPCell(new Phrase("Nombre del Cliente"));
            cell1.setBorderColor(BaseColor.GRAY);
            cell1.setPadding(10);
            table.addCell(cell1);

            PdfPCell cell2 = new PdfPCell(new Phrase(nombreCliente));
            cell2.setBorderColor(BaseColor.GRAY);
            cell2.setPadding(10);
            table.addCell(cell2);

            PdfPCell cell3 = new PdfPCell(new Phrase("Fecha de Venta"));
            cell3.setBorderColor(BaseColor.GRAY);
            cell3.setPadding(10);
            table.addCell(cell3);

            PdfPCell cell4 = new PdfPCell(new Phrase(fechaVenta));
            cell4.setBorderColor(BaseColor.GRAY);
            cell4.setPadding(10);
            table.addCell(cell4);

            PdfPCell cell5 = new PdfPCell(new Phrase("Total de Venta"));
            cell5.setBorderColor(BaseColor.GRAY);
            cell5.setPadding(10);
            table.addCell(cell5);

            PdfPCell cell6 = new PdfPCell(new Phrase("$" + totalVenta));
            cell6.setBorderColor(BaseColor.GRAY);
            cell6.setPadding(10);
            table.addCell(cell6);

            document.add(table);

            // Cerrar el documento
            document.close();

            System.out.println("El informe se ha generado exitosamente en: " + rutaPDF);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }*/
        try {
            Date date = new Date();
            FileOutputStream archivo;
            String url = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();//ruta de escritorio del usuario actual de la pc
            System.out.println(url);
            File salida = new File("lokd" + File.separator + "venta.pdf");
            archivo = new FileOutputStream(salida);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            Image img = Image.getInstance("src/main/java/com/example/tiendaj/img/logoWeb2.png");
            //Fecha
            Paragraph fecha = new Paragraph();
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLUE);
            fecha.add(Chunk.NEWLINE);
            fecha.add("Folio: " + 1 + "\nFecha: "
                    + new SimpleDateFormat("dd/MM/yyyy").format(date) + "\n\n");
            PdfPTable Encabezado = new PdfPTable(4);
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);
            float[] columnWidthsEncabezado = new float[]{20f, 30f, 70f, 40f};
            Encabezado.setWidths(columnWidthsEncabezado);
            Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);
            Encabezado.addCell(img);
            Encabezado.addCell("");
            //info empresa
            Encabezado.addCell("Ruc:    " + 192746172 + "\nNombre: " + "W-SHOP" + "\nTeléfono: " + "91871923" + "\nDirección: " + "Av Giron Nuevo" + "\n\n");
            //
            Encabezado.addCell(fecha);
            doc.add(Encabezado);
            //cliente
            Paragraph cli = new Paragraph();
            cli.add(Chunk.NEWLINE);
            cli.add("DATOS DEL CLIENTE" + "\n\n");
            doc.add(cli);

            PdfPTable proveedor = new PdfPTable(4);
            proveedor.setWidthPercentage(100);
            proveedor.getDefaultCell().setBorder(0);
            float[] columnWidthsProveedor = new float[]{25f, 25f, 25f, 25f};
            proveedor.setWidths(columnWidthsProveedor);
            proveedor.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell cliNom = new PdfPCell(new Phrase("Nombre", negrita));
            PdfPCell cliDNI = new PdfPCell(new Phrase("DNI/RUC", negrita));
            PdfPCell cliTel = new PdfPCell(new Phrase("Télefono", negrita));
            PdfPCell cliDir = new PdfPCell(new Phrase("Dirección", negrita));
            cliNom.setBorder(Rectangle.NO_BORDER);
            cliDNI.setBorder(Rectangle.NO_BORDER);
            cliTel.setBorder(Rectangle.NO_BORDER);
            cliDir.setBorder(Rectangle.NO_BORDER);

            proveedor.addCell(cliNom);
            proveedor.addCell(cliDNI);
            proveedor.addCell(cliTel);
            proveedor.addCell(cliDir);
            proveedor.addCell("Juan Perez");
            proveedor.addCell("12345678");
            proveedor.addCell("813749183");
            proveedor.addCell("Lima-Chorrillos av Cordillera Occidental" + "\n\n");


            doc.add(proveedor);

            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);
            tabla.getDefaultCell().setBorder(0);
            float[] columnWidths = new float[]{10f, 50f, 15f, 15f};
            tabla.setWidths(columnWidths);
            tabla.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell c1 = new PdfPCell(new Phrase("Cant.", negrita));
            PdfPCell c2 = new PdfPCell(new Phrase("Descripción.", negrita));
            //cantidad
            PdfPCell c3 = new PdfPCell(new Phrase("P. unt.", negrita));
            PdfPCell c4 = new PdfPCell(new Phrase("P. Total", negrita));
            c1.setBorder(Rectangle.NO_BORDER);
            c2.setBorder(Rectangle.NO_BORDER);
            c3.setBorder(Rectangle.NO_BORDER);
            c4.setBorder(Rectangle.NO_BORDER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);
            tabla.addCell(c2);
            tabla.addCell(c3);
            tabla.addCell(c4);
             for(int i = 0; i < 5; i++){
                    double subTotal = 80;//4*20
                    tabla.addCell("4");
                    tabla.addCell("Camara");
                    tabla.addCell("20");
                    tabla.addCell(String.valueOf(subTotal));
            }
            doc.add(tabla);
            Paragraph info = new Paragraph();
            //total e impuesto del total
            info.add(Chunk.NEWLINE);
            double totaligv = Math.round(320 * 0.18 * 100) / 100;
            info.add("Subtotal: " + 320 + "\nIGV: " + totaligv + "\nTotal: " + (320+totaligv) + "\n\n");
            info.setAlignment(Element.ALIGN_RIGHT);
            doc.add(info);
            doc.close();
            archivo.close();
            Desktop.getDesktop().open(salida);
        } catch (DocumentException | IOException e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }











    }

    /*static class HeaderFooterPageEvent extends PdfPageEventHelper {
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();

            // Agregar encabezado
            PdfPTable header = new PdfPTable(1);
            header.setTotalWidth(PageSize.A4.getWidth() - 80);
            PdfPCell cell = new PdfPCell(new Phrase("Venta Online - Informe", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.GRAY)));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.addCell(cell);
            header.writeSelectedRows(0, -1, 40, PageSize.A4.getHeight() - 20, cb);

            // Agregar pie de página
            PdfPTable footer = new PdfPTable(1);
            footer.setTotalWidth(PageSize.A4.getWidth() - 80);
            PdfPCell cell2 = new PdfPCell(new Phrase("Página " + writer.getPageNumber(), FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.GRAY)));
            cell2.setBorder(Rectangle.NO_BORDER);
            cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            footer.addCell(cell2);
            footer.writeSelectedRows(0, -1, 40, 30, cb);
        }
    }*/

}
