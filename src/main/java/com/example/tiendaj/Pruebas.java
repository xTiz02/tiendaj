package com.example.tiendaj;

import com.example.tiendaj.modelo.dao.ProveedorDao;
import com.example.tiendaj.modelo.dao.impl.ProveedorDaoImpl;
import com.example.tiendaj.modelo.entidades.almacen.Proveedor;
import com.example.tiendaj.modelo.entidades.almacen.TipoProveedor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Pruebas {
    public static void main(String[] args) {

        List<String> lista = new ArrayList<>();
        lista.add("Elemento1");
        lista.add("Elemento2");
        lista.add("Elemento3");
        lista.add("Elemento4");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el elemento que desea buscar: ");
        String elementoBuscado = scanner.nextLine();
        for (String elemento : lista) {
            if (elemento.contains(elementoBuscado)) {
                System.out.println("El elemento: " + elemento + " :se encuentra en la lista");
            }
        }


        scanner.close();

        //array de bytes



        /*String[] cat = {"1","2","3"};
        if (cat.length != 0) {
            StringBuilder idIn = new StringBuilder();
            for (String catId : cat) {
                idIn.append(catId).append(",");
            }

            // Eliminar la última coma si existe
            if (idIn.length() > 0) {
                idIn.deleteCharAt(idIn.length() - 1);
            }

            System.out.println(idIn.toString());
        }else {
            //no tiene ides
            String id = cat[0];
            System.out.println(id);
        }
//entrar al archivo config de resources:
        LocalDateTime fecha = LocalDateTime.now();
        System.out.println(fecha);


        try {
            ResourceBundle properties = ResourceBundle.getBundle("com/example/tiendaj/util/config");
            String url = properties.getString("RutaPrincipal");
            System.out.println(url);





        } catch (Exception e) {
            System.out.println("Error: " + e);
        }*/

    }
}
