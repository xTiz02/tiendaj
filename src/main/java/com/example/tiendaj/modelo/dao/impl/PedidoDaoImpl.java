package com.example.tiendaj.modelo.dao.impl;

import com.example.tiendaj.modelo.dao.PedidoDao;
import com.example.tiendaj.modelo.dao.ProductoDao;
import com.example.tiendaj.modelo.dao.UsuarioDao;
import com.example.tiendaj.modelo.entidades.Carrito;
import com.example.tiendaj.modelo.entidades.Cliente;
import com.example.tiendaj.modelo.entidades.Pedido;
import com.example.tiendaj.modelo.entidades.Producto;
import com.example.tiendaj.modelo.entidades.almacen.DetalleEntrega;
import com.example.tiendaj.modelo.entidades.almacen.DetallePedido;
import com.example.tiendaj.util.Conexion;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;





import java.sql.SQLException;


public class PedidoDaoImpl implements PedidoDao {
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    @Override
    public int guardarPedido(Pedido pedido, List<Carrito> lista) {
        int idPedido = 0;
        Connection con = null;
        PreparedStatement pst = null;

        try {
            con = Conexion.getConexion();
            con.setAutoCommit(false); // Deshabilitar el autocommit para manejar la transacción manualmente

            String sql = "insert into pedido (id_cliente,id_paypal,id_transaccion,nombre_paypal_cliente,calle_paypal_cliente,direccion_paypal_cliente,ciudad_paypal_cliente,codPostal_paypal_cliente,provincia_paypal_cliente,email_paypal_cliente,total,fechaPedido,estado) \n" +
                    " values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setInt(1, pedido.getCliente().getId());
            pst.setString(2, pedido.getId_paypal_cliente());
            pst.setString(3, pedido.getId_transaccion());
            pst.setString(4, pedido.getNombre_paypal_cliente());
            pst.setString(5, pedido.getCalle_paypal_cliente());
            pst.setString(6, pedido.getDireccion_paypal_cliente());
            pst.setString(7, pedido.getCiudad_paypal_cliente());
            pst.setString(8, pedido.getCodPostal_paypal_cliente());
            pst.setString(9, pedido.getProvincia_paypal_cliente());
            pst.setString(10, pedido.getEmail_paypal_cliente());
            pst.setDouble(11, pedido.getTotal());
            pst.setTimestamp(12, Timestamp.valueOf(pedido.getFechaPedido()));//cambiar fecha
            pst.setString(13, pedido.getEstado());
            int i = pst.executeUpdate();
            if (i == 1) {
                rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    idPedido = rs.getInt(1);
                }

                for (Carrito c : lista) {
                    String sql2 = "insert into detalle_pedido (id_pedido,id_producto,nombre,precio,cantidad,estado) values (?,?,?,?,?,'NG')";
                    double precio = c.getProducto().getPrecio() - c.getProducto().getPrecio() * c.getProducto().getDescuento() / 100;
                    pst = con.prepareStatement(sql2);
                    pst.setInt(1, idPedido);
                    pst.setInt(2, c.getProducto().getId());
                    pst.setString(3, c.getProducto().getNombre());
                    pst.setDouble(4, precio);
                    pst.setInt(5, c.getCantidad());
                    i = pst.executeUpdate();
                }
            }

            con.commit(); // Confirmar la transacción si todo fue exitoso
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (con != null) {
                    con.rollback(); // Realizar un rollback si ocurre una excepción
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return idPedido;
    }


    @Override
    public List<Pedido> listarPedidos() {
        UsuarioDao usuarioDao = new UsuarioDaoImpl();
        List<Pedido> lista = new ArrayList<>();
        try{
            con = Conexion.getConexion();
            String sql = "select * from pedido";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()){
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt(1));
                pedido.setCliente(usuarioDao.buscarUsuario(rs.getInt(2)).getCliente());
                //pedido.setEmpleado(empleadoDao.buscarEmpelado(rs.getInt(3)));
                pedido.setId_paypal_cliente(rs.getString(4));
                pedido.setId_transaccion(rs.getString(5));
                pedido.setNombre_paypal_cliente(rs.getString(6));
                pedido.setCalle_paypal_cliente(rs.getString(7));
                pedido.setDireccion_paypal_cliente(rs.getString(8));
                pedido.setCiudad_paypal_cliente(rs.getString(9));
                pedido.setCodPostal_paypal_cliente(rs.getString(10));
                pedido.setProvincia_paypal_cliente(rs.getString(11));
                pedido.setEmail_paypal_cliente(rs.getString(12));
                pedido.setTotal(rs.getDouble(13));
                pedido.setFechaPedido(rs.getTimestamp(14).toLocalDateTime());
                pedido.setEstado(rs.getString(15));
                lista.add(pedido);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Pedido> listarPedidosPorCliente(int idCliente) {
        UsuarioDao usuarioDao = new UsuarioDaoImpl();
        List<Pedido> lista = new ArrayList<>();
        try{
            con = Conexion.getConexion();
            String sql = "select * from pedido where id_cliente = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1,idCliente);
            rs = pst.executeQuery();
            while (rs.next()){
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt(1));
                pedido.setCliente(usuarioDao.buscarUsuario(rs.getInt(2)).getCliente());
                //pedido.setEmpleado(empleadoDao.buscarEmpelado(rs.getInt(3)));
                pedido.setId_paypal_cliente(rs.getString(4));
                pedido.setId_transaccion(rs.getString(5));
                pedido.setNombre_paypal_cliente(rs.getString(6));
                pedido.setCalle_paypal_cliente(rs.getString(7));
                pedido.setDireccion_paypal_cliente(rs.getString(8));
                pedido.setCiudad_paypal_cliente(rs.getString(9));
                pedido.setCodPostal_paypal_cliente(rs.getString(10));
                pedido.setProvincia_paypal_cliente(rs.getString(11));
                pedido.setEmail_paypal_cliente(rs.getString(12));
                pedido.setTotal(rs.getDouble(13));
                pedido.setFechaPedido(rs.getTimestamp(14).toLocalDateTime());
                pedido.setEstado(rs.getString(15));
                lista.add(pedido);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<DetallePedido> listarDetallePedido(int idPedido) {
        ProductoDao productoDao = new ProductoDaoImpl();
        List<DetallePedido> lista = new ArrayList<>();
        try{
            con = Conexion.getConexion();
            String sql = "select * from detalle_pedido where id_pedido = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1,idPedido);
            rs = pst.executeQuery();
            while (rs.next()){
                DetallePedido detallePedido = new DetallePedido();
                detallePedido.setId(rs.getInt(1));
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt(2));
                detallePedido.setPedido(pedido);
                detallePedido.setProducto(productoDao.buscarProducto(rs.getInt(3)));
                detallePedido.setNombre(rs.getString(4));
                detallePedido.setPrecio(rs.getDouble(5));
                detallePedido.setCantidad(rs.getInt(6));
                detallePedido.setEstado(rs.getString(7));
                lista.add(detallePedido);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Pedido buscarPedido(int idPedido) {
        Pedido pedido = null;
        UsuarioDao usuarioDao = new UsuarioDaoImpl();
        try{
            con = Conexion.getConexion();
            String sql = "select * from pedido where id = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1,idPedido);
            rs = pst.executeQuery();
            if (rs.next()) {
                pedido = new Pedido();
                pedido.setId(rs.getInt(1));
                pedido.setCliente(usuarioDao.buscarUsuario(rs.getInt(2)).getCliente());
                //pedido.setEmpleado(empleadoDao.buscarEmpelado(rs.getInt(3)));
                pedido.setId_paypal_cliente(rs.getString(4));
                pedido.setId_transaccion(rs.getString(5));
                pedido.setNombre_paypal_cliente(rs.getString(6));
                pedido.setCalle_paypal_cliente(rs.getString(7));
                pedido.setDireccion_paypal_cliente(rs.getString(8));
                pedido.setCiudad_paypal_cliente(rs.getString(9));
                pedido.setCodPostal_paypal_cliente(rs.getString(10));
                pedido.setProvincia_paypal_cliente(rs.getString(11));
                pedido.setEmail_paypal_cliente(rs.getString(12));
                pedido.setTotal(rs.getDouble(13));
                pedido.setFechaPedido(rs.getTimestamp(14).toLocalDateTime());
                pedido.setEstado(rs.getString(15));
            }
        }catch (Exception e){
                e.printStackTrace();
            }
        return pedido;
    }

    @Override
    public DetallePedido buscarDetallePedido(int idDetallePedido) {
        DetallePedido detallePedido = null;
        ProductoDao productoDao = new ProductoDaoImpl();
        try{
            con = Conexion.getConexion();
            String sql = "select * from detalle_pedido where id = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1,idDetallePedido);
            rs = pst.executeQuery();
            if (rs.next()) {
                detallePedido = new DetallePedido();
                detallePedido.setId(rs.getInt(1));
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt(2));
                detallePedido.setPedido(pedido);
                detallePedido.setProducto(productoDao.buscarProducto(rs.getInt(3)));
                detallePedido.setNombre(rs.getString(4));
                detallePedido.setPrecio(rs.getDouble(5));
                detallePedido.setCantidad(rs.getInt(6));
                detallePedido.setEstado(rs.getString(7));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return detallePedido;
    }

    @Override
    public int actualizarEstadoDetPedido(int idDetPedido, String estado) {
        try{
            con = Conexion.getConexion();
            String sql = "update detalle_pedido set estado = ? where id = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1,estado);
            pst.setInt(2,idDetPedido);
            int i = pst.executeUpdate();
            if(i==1){
                return 1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int actualizarEstadoPedido(int idPedido, String estado) {
        try{
            con = Conexion.getConexion();
            String sql = "update pedido set estado = ? where id = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1,estado);
            pst.setInt(2,idPedido);
            int i = pst.executeUpdate();
            if(i==1){
                return 1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void generarReporte(Pedido pedido, List<Carrito> lista) {
        try {
            Date date = new Date();
            FileOutputStream archivo;
            String url = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
            System.out.println(url);
            File salida = new File(url + File.separator + "venta.pdf");
            archivo = new FileOutputStream(salida);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);


            doc.open();
            //Image img = Image.getInstance("/src/main/java/com/example/tiendaj/img/logoWeb2.png");
            //Fecha
            Paragraph fecha = new Paragraph();
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLUE);
            fecha.add(Chunk.NEWLINE);
            fecha.add("Id: " + pedido.getId() + "\nFecha: "
                    + new SimpleDateFormat("dd/MM/yyyy").format(date) + "\n\n");
            PdfPTable Encabezado = new PdfPTable(3);
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);
            float[] columnWidthsEncabezado = new float[]{30f, 70f, 40f};
            Encabezado.setWidths(columnWidthsEncabezado);
            Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);
            //Encabezado.addCell(img);
            Encabezado.addCell("");
            //info empresa
            Encabezado.addCell("Ruc:    " + 918237198 + "\nNombre: " + "W-SHOP" + "\nTeléfono: " + "913249464" + "\nDirección: " + "Av Giron Nuevo" + "\n\n");
            //
            Encabezado.addCell(fecha);
            doc.add(Encabezado);
            //cliente
            Paragraph cli = new Paragraph();
            cli.add(Chunk.NEWLINE);
            cli.add("DATOS DEL CLIENTE" + "\n\n");
            doc.add(cli);

            PdfPTable cliente = new PdfPTable(4);
            cliente.setWidthPercentage(100);
            cliente.getDefaultCell().setBorder(0);
            float[] columnWidthsProveedor = new float[]{25f, 25f, 25f, 25f};
            cliente.setWidths(columnWidthsProveedor);
            cliente.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell cliNom = new PdfPCell(new Phrase("Nombre", negrita));
            PdfPCell cliDNI = new PdfPCell(new Phrase("DNI/RUC", negrita));
            PdfPCell cliTel = new PdfPCell(new Phrase("Télefono", negrita));
            PdfPCell cliDir = new PdfPCell(new Phrase("Dirección", negrita));
            cliNom.setBorder(Rectangle.NO_BORDER);
            cliDNI.setBorder(Rectangle.NO_BORDER);
            cliTel.setBorder(Rectangle.NO_BORDER);
            cliDir.setBorder(Rectangle.NO_BORDER);

            cliente.addCell(cliNom);
            cliente.addCell(cliDNI);
            cliente.addCell(cliTel);
            cliente.addCell(cliDir);


            Cliente s = pedido.getCliente();


            cliente.addCell(s.getNombre());
            cliente.addCell(s.getDni());
            cliente.addCell(s.getTelefono());
            cliente.addCell(pedido.getCiudad_paypal_cliente()+" "+pedido.getDireccion_paypal_cliente()+" "+pedido.getCalle_paypal_cliente() + "\n\n");


            doc.add(cliente);

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
            double p = 0, subtotal = 0;
            for(Carrito c : lista){
                double precio = c.getProducto().getPrecio() - c.getProducto().getPrecio() * c.getProducto().getDescuento() / 100;
                tabla.addCell(String.valueOf(c.getCantidad()));
                tabla.addCell(c.getProducto().getNombre());
                tabla.addCell(String.valueOf(precio));
                p = Math.round(c.getSubTotal()*100)/100.0;
                tabla.addCell(String.valueOf(p));
                subtotal += p;
            }
            doc.add(tabla);
            Paragraph info = new Paragraph();
            //total e impuesto del total
            info.add(Chunk.NEWLINE);//salto de linea
            double totaligv = Math.round(subtotal * 0.18 * 100) / 100;
            info.add("Subtotal: " + subtotal + "\nIGV: " + totaligv + "\nTotal: " + (subtotal+totaligv) + "\n\n");
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
}
