package com.example.tiendaj.controlador;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.Collection;
import java.util.ResourceBundle;
@MultipartConfig
@WebServlet(name = "imgController", value = "/img-controller")
public class ImgController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        ServletContext context = req.getServletContext();
        String ruta = context.getRealPath("/");
        System.out.println("Path: "+ruta);
        /*String ruta="";
        try {
            ResourceBundle properties = ResourceBundle.getBundle("com/example/tiendaj/util/config");
            ruta = properties.getString("RutaPrincipal");
            System.out.println(ruta);


            String carpetaNombre = Long.toString(System.currentTimeMillis());
            System.out.println("Carpeta: "+carpetaNombre);
            File folder = new File(ruta,carpetaNombre);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            Collection<Part> archivos = req.getParts();
            for(Part archivo: archivos){
                System.out.println("Part: "+archivo.getName());
                System.out.println("Part: "+archivo.getSubmittedFileName());
                System.out.println("Part: "+archivo.getSize());
                System.out.println("Part: "+archivo.getContentType());

                String nombreArchivo = archivo.getSubmittedFileName();
                System.out.println("Nombre:" +nombreArchivo);
                // Construir la ruta completa para guardar el archivo
                String rutaFinal = folder.getAbsolutePath() + File.separator + nombreArchivo;
                System.out.println("Ruta final: " + rutaFinal);

                try (InputStream is = archivo.getInputStream()) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer1 = new byte[4096];
                    int bytesLeidos;
                    while ((bytesLeidos = is.read(buffer1)) != -1) {
                        baos.write(buffer1, 0, bytesLeidos);
                    }
                    byte[] archivoEnBytes = baos.toByteArray();
                    System.out.println("Archivo guardado en un arreglo de bytes - Total: " + archivoEnBytes.length);


                    try(ByteArrayInputStream bais = new ByteArrayInputStream(archivoEnBytes);
                        FileOutputStream fos = new FileOutputStream(rutaFinal))
                    {
                        byte[] buffer2 = new byte[500];
                        int bytesLeidos2;
                        while((bytesLeidos2 = bais.read(buffer2)) != -1){
                            fos.write(buffer2,0,bytesLeidos2);
                        }
                        System.out.println("Archivo transmitido desde un arreglo de bytes");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
*/
    }
}
