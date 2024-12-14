package com.example.tiendaj.modelo.dao.impl;

import com.example.tiendaj.modelo.dao.CategoriaDao;
import com.example.tiendaj.modelo.dao.ProveedorDao;
import com.example.tiendaj.modelo.entidades.Categoria;
import com.example.tiendaj.modelo.entidades.Producto;
import com.example.tiendaj.modelo.dao.ProductoDao;
import com.example.tiendaj.util.Conexion;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ProductoDaoImpl implements ProductoDao {
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;

    @Override
    public List<Producto> flitrarPorDescuento(List<Producto> lista, int cantidad) {
        List<Producto> listaFiltrada = lista.stream()
                .sorted(Comparator.comparingDouble(Producto::getDescuento).reversed())
                .limit(cantidad)
                .collect(Collectors.toList());
        return listaFiltrada;
    }

    @Override
    public List<Producto> flitrarPorNombre(List<Producto> l, String palabra) {
        List<Producto> listaFiltrada = l.stream()
                .filter(p -> p.getNombre().contains(palabra))//sin toLowerCase() la palabra debe ser exacta
                .collect(Collectors.toList());
        return listaFiltrada;
    }

    @Override
    public List<Producto> listarProductosActivos() {
        List<Producto> lista=new ArrayList<>();

        try {
            String sql = "SELECT inv.id AS inventario_id, inv.stock AS inventario_stock, inv.fechaModificado AS inventario_fecha, prod.id AS producto_id, \n" +
                    "prod.nombre AS producto_nombre,\n" +
                    "prod.caracteristicas AS producto_caracteristicas,\n" +
                    "prod.descripcion AS producto_descripcion,\n" +
                    " prod.especificaciones AS producto_especificaciones,\n" +
                    " prod.precio AS producto_precio,\n" +
                    " prod.descuento AS producto_descuento,\n" +
                    " cat.id AS categoria_id,\n" +
                    "cat.categoria AS categoria_nombre,\n" +
                    "cat.descripcion AS categoria_desc\n" +
                    "FROM inventario AS inv \n" +
                    "INNER JOIN producto AS prod ON inv.id_producto = prod.id \n" +
                    "INNER JOIN producto_categoria AS cat ON prod.id_categoria = cat.id\n" +
                    "WHERE inv.activo = 1;";
            con = Conexion.getConexion();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                Producto p = new Producto();
                p.setId(rs.getInt("producto_id"));
                p.setNombre(rs.getString("producto_nombre"));
                p.setCaracteristicas(rs.getString("producto_caracteristicas"));
                p.setDescripcion(rs.getString("producto_descripcion"));
                p.setEspecificaciones(rs.getString("producto_especificaciones"));
                p.setPrecio(rs.getDouble("producto_precio"));
                p.setDescuento(rs.getDouble("producto_descuento"));
                p.setCategoria(new Categoria( rs.getInt("categoria_id"), rs.getString("categoria_nombre"), rs.getString("categoria_desc")));
                //guardar ruta de imagenes
                String ruta="";
                List<String> imagenes = new ArrayList<>();
                try {
                    ResourceBundle properties = ResourceBundle.getBundle("com/example/tiendaj/util/config");
                    ruta = properties.getString("RutaPrincipal");

                    String img = ruta + "producto" + p.getId() + "/producto" + p.getId() + ".jpg";
                    if (!(new File(img).exists())) {
                        img = "img/productos/producto"+p.getId()+"/producto"+p.getId()+".jpeg";
                        imagenes.add(img);
                    }else {
                        img = "img/productos/producto"+p.getId()+"/producto"+p.getId()+".jpg";
                        imagenes.add(img);
                    }

                    for(int i=1; i<10;i++) {
                        String img2 = ruta + "producto" + p.getId() + "/producto" + p.getId() + "-" + i + ".jpg";
                        if (!(new File(img2).exists())) {
                            img2 = ruta + "producto" + p.getId() + "/producto" + p.getId() + "-" + i + ".jpeg";
                            if (!(new File(img2).exists())) {
                                break;
                            }else {
                                img2 = "img/productos/producto"+p.getId()+"/producto"+p.getId()+"-"+i+".jpeg";
                                imagenes.add(img2);
                            }
                        }else {
                            img2 = "img/productos/producto"+p.getId()+"/producto"+p.getId()+"-"+i+".jpg";
                            imagenes.add(img2);
                        }

                    }
                    p.setImagenes(imagenes);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                lista.add(p);
            }
            rs.close();
            pst.close();
            con.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Producto buscarProductoPorId(List<Producto> lista,int id) {
        Producto p = new Producto();
        p=lista.stream().filter(pr -> pr.getId() == id).findFirst().orElse(null);
        return p;
    }

    @Override
    public Producto buscarProducto(int id) {
        Producto p = null;
        ProveedorDao productoDao = new ProveedorDaoImpl();
        CategoriaDao categoriaDao = new CategoriaDaoImpl();
        try{
            String sql = "select *from producto where id = ?;";
            con = Conexion.getConexion();
            pst = con.prepareStatement(sql);
            pst.setInt(1,id);
            rs = pst.executeQuery();
            if(rs.next()){
                p = new Producto();
                p.setId(rs.getInt(1));
                p.setProveedor(productoDao.buscarProveedor(rs.getInt(2)));
                p.setCategoria(categoriaDao.buscarCategoria(rs.getInt(3)));
                p.setNombre(rs.getString(4));
                p.setCaracteristicas(rs.getString(5));
                p.setDescripcion(rs.getString(6));
                p.setEspecificaciones(rs.getString(7));
                p.setPrecio(rs.getDouble(8));
                p.setDescuento(rs.getDouble(9));

            }
rs.close();

            pst.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return p;
    }

    @Override
    public List<Producto> filtrarPorCategoria(List<Producto> lista,String[] cat, double precio) {
        //cat = {"todos"} o {"1","2","3",...} o null
        List<Producto> listaFiltrada = lista.stream()
                .filter(p -> p.getPrecio() < precio)
                .filter(p -> {
                    if (cat != null && !cat[0].equals("todos")) {
                        for (String catId : cat) {
                            if (p.getCategoria().getId() == Integer.parseInt(catId)) {
                                return true;
                            }
                        }
                    } else {//cat es null o es todos
                        return true;
                    }
                    return false;//no cumple con la condicion
                })
                .collect(Collectors.toList());

        return listaFiltrada;
    }



}
