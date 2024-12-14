package com.example.tiendaj.modelo.dao.impl;

import com.example.tiendaj.modelo.dao.InventarioDao;
import com.example.tiendaj.modelo.entidades.Categoria;
import com.example.tiendaj.modelo.entidades.Producto;
import com.example.tiendaj.modelo.entidades.almacen.Inventario;
import com.example.tiendaj.modelo.entidades.almacen.Proveedor;
import com.example.tiendaj.util.Conexion;

import javax.servlet.http.Part;
import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class InventarioDaoImpl implements InventarioDao {
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;

    @Override
    public List<Inventario> listarInventario() {
        List<Inventario> lista = new ArrayList<>();
        try {
            String sql = "SELECT inv.id AS inventario_id, inv.stock AS inventario_stock, inv.fechaModificado AS inventario_fecha, inv.activo AS inventario_activo, prod.id AS producto_id, \n" +
                    "prod.nombre AS producto_nombre,\n" +
                    "prod.caracteristicas AS producto_caracteristicas,\n" +
                    "prod.descripcion AS producto_descripcion,\n" +
                    " prod.especificaciones AS producto_especificaciones,\n" +
                    " prod.precio AS producto_precio,\n" +
                    " prod.descuento AS producto_descuento,\n" +
                    " cat.id AS categoria_id,\n" +
                    "cat.categoria AS categoria_nombre,\n" +
                    "prov.nombre AS proveedor_nombre,\n" +
                    "prov.id AS proveedor_id,\n" +
                    "cat.descripcion AS categoria_desc\n" +
                    "FROM inventario AS inv \n" +
                    "INNER JOIN producto AS prod ON inv.id_producto = prod.id \n" +
                    "INNER JOIN proveedor AS prov ON prod.id_proveedor = prov.id \n" +
                    "INNER JOIN producto_categoria AS cat ON prod.id_categoria = cat.id;";
            con = Conexion.getConexion();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {

                Inventario inv = new Inventario();
                inv.setId(rs.getInt("inventario_id"));
                inv.setStock(rs.getInt("inventario_stock"));
                inv.setActivo(rs.getInt("inventario_activo"));
                //inventaario_fecha es null
                if (rs.getTimestamp("inventario_fecha") != null) {
                    inv.setFechaModificado(rs.getTimestamp("inventario_fecha").toLocalDateTime());
                } else {
                    inv.setFechaModificado(null);
                }
                Producto p = new Producto();
                Proveedor proveedor = new Proveedor();
                proveedor.setId(rs.getInt("proveedor_id"));
                proveedor.setNombre(rs.getString("proveedor_nombre"));
                //agregar el tipo zZ
                p.setProveedor(proveedor);
                p.setId(rs.getInt("producto_id"));
                p.setNombre(rs.getString("producto_nombre"));
                p.setCaracteristicas(rs.getString("producto_caracteristicas"));
                p.setDescripcion(rs.getString("producto_descripcion"));
                p.setEspecificaciones(rs.getString("producto_especificaciones"));
                p.setPrecio(rs.getDouble("producto_precio"));
                p.setDescuento(rs.getDouble("producto_descuento"));
                p.setCategoria(new Categoria(rs.getInt("categoria_id"), rs.getString("categoria_nombre"), rs.getString("categoria_desc")));
                //guardar ruta de imagenes
                String ruta = "";
                List<String> imagenes = new ArrayList<>();
                try {
                    ResourceBundle properties = ResourceBundle.getBundle("com/example/tiendaj/util/config");
                    ruta = properties.getString("RutaPrincipal");

                    String img = ruta + "producto" + p.getId() + "/producto" + p.getId() + ".jpg";
                    if (!(new File(img).exists())) {
                        img = "img/productos/producto" + p.getId() + "/producto" + p.getId() + ".jpeg";
                        imagenes.add(img);
                    } else {
                        img = "img/productos/producto" + p.getId() + "/producto" + p.getId() + ".jpg";
                        imagenes.add(img);
                    }

                    for (int i = 1; i < 10; i++) {
                        String img2 = ruta + "producto" + p.getId() + "/producto" + p.getId() + "-" + i + ".jpg";
                        if (!(new File(img2).exists())) {
                            img2 = ruta + "producto" + p.getId() + "/producto" + p.getId() + "-" + i + ".jpeg";
                            if (!(new File(img2).exists())) {
                                break;
                            } else {
                                img2 = "img/productos/producto" + p.getId() + "/producto" + p.getId() + "-" + i + ".jpeg";
                                imagenes.add(img2);
                            }
                        } else {
                            img2 = "img/productos/producto" + p.getId() + "/producto" + p.getId() + "-" + i + ".jpg";
                            imagenes.add(img2);
                        }

                    }
                    p.setImagenes(imagenes);
                    inv.setProducto(p);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                lista.add(inv);
            }
            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Inventario buscarInventario(List<Inventario> lista, int id) {
        Inventario inv = null;
        inv = lista.stream().filter(inventario -> inventario.getId() == id).findFirst().orElse(null);
        return inv;
    }

    @Override
    public int agregarInventario(Inventario inv) {
        int i = 0;
        int idProducto = 0;
        try {
            con = Conexion.getConexion();
            con.setAutoCommit(false); // Desactiva el modo de autocommit
            String sql = "insert into producto values(null,?,?,?,?,?,?,?,?);";
            pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setInt(1, inv.getProducto().getProveedor().getId());
            pst.setInt(2, inv.getProducto().getCategoria().getId());
            pst.setString(3, inv.getProducto().getNombre());
            pst.setString(4, inv.getProducto().getCaracteristicas());
            pst.setString(5, inv.getProducto().getDescripcion());
            pst.setString(6, inv.getProducto().getEspecificaciones());
            pst.setDouble(7, inv.getProducto().getPrecio());
            pst.setDouble(8, inv.getProducto().getDescuento());
            i = pst.executeUpdate();
            if (i == 1) {
                i = 0;
                rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    idProducto = rs.getInt(1);
                    if (idProducto > 0) {
                        inv.getProducto().setId(idProducto);
                        idProducto = 0;
                    }
                }
                String sql2 = "insert into inventario values(null,?,?,?,?);";
                pst = con.prepareStatement(sql2);
                pst.setInt(1, inv.getProducto().getId());
                pst.setInt(2, inv.getStock());
                pst.setTimestamp(3, inv.getFechaModificado() == null ? null : Timestamp.valueOf(inv.getFechaModificado()));
                pst.setInt(4, inv.getActivo());
                i = pst.executeUpdate();
                if (i == 1) {
                    idProducto = inv.getProducto().getId();
                    con.commit(); // Confirma la transacción si ambas inserciones fueron exitosas
                } else {
                    con.rollback(); // Hace un rollback si la segunda inserción falló
                }
            } else {
                con.rollback(); // Hace un rollback si la primera inserción falló y retorna 0
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback(); // Hace un rollback en caso de excepción
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.setAutoCommit(true); // Reactiva el modo de autocommit
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return idProducto;
    }

    public void guardarImagenes(int idProducto, Collection<Part> archivos) {
        // producto6.jpg
        // producto6-1.jpg
        // producto6-2.jpg
        // .....

        Map<String, String> tipoImagen = new HashMap<>();
        tipoImagen.put("image/jpeg", "jpg");
        tipoImagen.put("image/png", "png");
        int n = 1;
        String ruta = "";
        try {
            ResourceBundle properties = ResourceBundle.getBundle("com/example/tiendaj/util/config");
            ruta = properties.getString("RutaPrincipal");

            String carpetaNombre = "producto" + idProducto;
            System.out.println("Carpeta: " + carpetaNombre);
            File folder = new File(ruta, carpetaNombre);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            //el primer archivo es la imagen principal
            for (int i = 0; i < archivos.size(); i++) {
                // Verifica si es una parte de archivo
                Part archivo = (Part) archivos.toArray()[i];
                if (archivo.getContentType() != null) {
                    String tipo = tipoImagen.get(archivo.getContentType());
                    if (i == 8) {
                        String nombreArchivo = "producto" + idProducto + "." + tipo;
                        System.out.println("Nombre:" + nombreArchivo);
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
                            try (ByteArrayInputStream bais = new ByteArrayInputStream(archivoEnBytes);
                                 FileOutputStream fos = new FileOutputStream(rutaFinal)) {
                                byte[] buffer2 = new byte[500];
                                int bytesLeidos2;
                                while ((bytesLeidos2 = bais.read(buffer2)) != -1) {
                                    fos.write(buffer2, 0, bytesLeidos2);
                                }
                                System.out.println("Archivo transmitido desde un arreglo de bytes");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        String nombreArchivo = "producto" + idProducto + "-" + n + "." + tipo;
                        System.out.println("Nombre:" + nombreArchivo);
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


                            try (ByteArrayInputStream bais = new ByteArrayInputStream(archivoEnBytes);
                                 FileOutputStream fos = new FileOutputStream(rutaFinal)) {
                                byte[] buffer2 = new byte[500];
                                int bytesLeidos2;
                                while ((bytesLeidos2 = bais.read(buffer2)) != -1) {
                                    fos.write(buffer2, 0, bytesLeidos2);
                                }
                                System.out.println("Archivo transmitido desde un arreglo de bytes");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        n++;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    @Override
    public int actualizarInventario(Inventario inv) {
        int i = 0;
        try{
            String sql ="UPDATE producto AS p \n" +
                    "INNER JOIN inventario AS i ON p.id = i.id_producto \n" +
                    "SET p.id_proveedor = ?,\n" +
                    "   p.id_categoria = ?,\n" +
                    "   p.nombre = ?,\n" +
                    "    p.caracteristicas = ?,\n" +
                    "    p.descripcion = ?,\n" +
                    "    p.especificaciones = ?,\n" +
                    "    p.precio = ?,  -- Nuevo precio\n" +
                    "    p.descuento = ?,  -- Nuevo descuento\n" +
                    "    i.fechaModificado = NOW()  -- Establece la fecha actual como nueva fecha de modificación en inventario\n" +
                    "WHERE p.id = ?;";
            con = Conexion.getConexion();
            pst = con.prepareStatement(sql);
            pst.setInt(1,inv.getProducto().getProveedor().getId());
            pst.setInt(2,inv.getProducto().getCategoria().getId());
            pst.setString(3,inv.getProducto().getNombre());
            pst.setString(4,inv.getProducto().getCaracteristicas());
            pst.setString(5,inv.getProducto().getDescripcion());
            pst.setString(6,inv.getProducto().getEspecificaciones());
            pst.setDouble(7,inv.getProducto().getPrecio());
            pst.setDouble(8,inv.getProducto().getDescuento());
            pst.setInt(9,inv.getProducto().getId());
            i = pst.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int aumentarStock(int idProducto, int cantidad) {
        int i = 0;
        try{
            String sql = "UPDATE inventario SET stock = stock + ? , fechaModificado = NOW() WHERE id_producto = ?;";
            con = Conexion.getConexion();
            pst = con.prepareStatement(sql);
            pst.setInt(1,cantidad);
            pst.setInt(2,idProducto);
            i = pst.executeUpdate();
        }catch (Exception e){
             e.printStackTrace();
        }
        return i;
    }

    @Override
    public int disminuirStock(int idProducto, int cantidad) {
        int i = 0;
        try{
            String sql = "UPDATE inventario SET stock = stock - ? , fechaModificado = NOW() WHERE id_producto = ?;";
            con = Conexion.getConexion();
            pst = con.prepareStatement(sql);
            pst.setInt(1,cantidad);
            pst.setInt(2,idProducto);
            i = pst.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int stockProducto(int id) {
        int stock = 0;
        try{
            String sql = "SELECT stock FROM inventario WHERE id_producto = ?;";
            con = Conexion.getConexion();
            pst = con.prepareStatement(sql);
            pst.setInt(1,id);
            rs = pst.executeQuery();
            if(rs.next()){
                stock = rs.getInt("stock");
            }
            rs.close();
            pst.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return stock;
    }

    @Override
    public int actualizarStock(int id, int cantidad) {
        int i = 0;
        try{
            int stockActual = stockProducto(id);
            String sql = "UPDATE inventario SET stock = ? WHERE id_producto = ?;";
            con = Conexion.getConexion();
            pst = con.prepareStatement(sql);
            pst.setInt(1,stockActual-cantidad);
            pst.setInt(2,id);
            i = pst.executeUpdate();
            pst.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int actualizarEstado(int idProducto, int estado) {
        int i = 0;
        try{
            String sql = "UPDATE inventario SET activo = ? WHERE id_producto = ?;";
            con = Conexion.getConexion();
            pst = con.prepareStatement(sql);
            pst.setInt(1,estado);
            pst.setInt(2,idProducto);
            i = pst.executeUpdate();
            pst.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int activarProducto(int idInventario) {
        int i = 0;
        try {
            String sql = "UPDATE inventario SET activo = 1 WHERE id = ?;";
            con = Conexion.getConexion();
            pst = con.prepareStatement(sql);
            pst.setInt(1, idInventario);
            i = pst.executeUpdate();
            pst.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int desactivarProducto(int idInventario) {
        int i = 0;
        try {
            String sql = "UPDATE inventario SET activo = 0 WHERE id = ?;";
            con = Conexion.getConexion();
            pst = con.prepareStatement(sql);
            pst.setInt(1, idInventario);
            i = pst.executeUpdate();
            pst.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public Inventario buscarInventarioPorId(int id) {


        return null;
    }
}





