$(document).ready(function() {
    $(".compra").click(function() {
        console.log("se hizo click en compra")
        var idp = $(this).parent().find("#idpro").val();//obtine el valor #idpro buscando dentro del elemento padre del .compra
        var url = "catalogo-controller";
        $.ajax({
            type: 'POST',
            url: url,
            data: {
                idp: idp,
                carrito: 'carrito' // Agrega la acción al objeto de datos
            },
            success: function (data, textStatus, jqXHR) {
                /*console.log("despues del post")
                console.log(data.numero)*/
                console.log("se agrego al carrito" + data.numero)
                $('#num_cart').text(data.numero.toString());
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("error")
            }
        });
    });

    $(".cantidad").change(function() {

        var idcar = $(this).parent().next().find("#idcar").val();
        var cantidad = $(this).val();
        var subtotal = $(this).parent().next().find("#subtotal");
        var url = "carrito-controller";
        $.ajax({
            type: 'POST',
            url: url,
            data: {
                idcar: idcar,
                cantidad: cantidad,
                accion: 'actualizar' // Agrega la acción al objeto de datos
            },
            success: function (data, textStatus, jqXHR) {
                /*console.log("despues del post")
                console.log(data.numero)*/
                console.log("se cambio el producto" + data.subtotal+ ", " + data.totalc)
                subtotal.text(data.subtotal.toString());
                $('#total').text("$"+data.totalc.toString());
            }
        });
    });


    $("#btn-elimina").click(function() {
        var idcar = $(this).parent().find("#btn-elimina").val();
        var url = 'carrito-controller';
        $.ajax({
            type: 'POST',
            url: url,
            data: {
                idcar: idcar,
                accion: 'eliminar'
            },
            success: function(data) {
                if (data.ok) {
                    location.reload(); // Recargar la página después de eliminar
                }else{
                    console.log(data.toString());
                }
            }
        });
    });



});

function actualizarRango() {
    var numero = document.getElementById("numeroInput").value;
    document.getElementById("rangoInput").value = numero;
}

function actualizarTexto() {
    var numero = document.getElementById("rangoInput").value;
    document.getElementById("numeroInput").value = numero;
}






const checkboxTodos = document.querySelector('input[name="categoria[]"][value="todos"]');

// Obtén todos los checkboxes de categoría
const checkboxesCategoria = document.querySelectorAll('input[name="categoria[]"]:not([value="todos"])');

// Agrega un evento 'change' al checkbox "todos"
checkboxTodos.addEventListener('change', function() {
    if (this.checked) {
        // Si el checkbox "todos" está marcado, desmarca los demás
        checkboxesCategoria.forEach(function(checkbox) {
            checkbox.checked = false;
            checkbox.disabled = true;
        });
    } else {
        // Si el checkbox "todos" está desmarcado, habilita los demás
        checkboxesCategoria.forEach(function(checkbox) {
            checkbox.disabled = false;
        });
    }
});

// Agrega un evento 'change' a los checkboxes de categoría
checkboxesCategoria.forEach(function(checkbox) {
    checkbox.addEventListener('change', function() {
        if (this.checked) {
            // Si un checkbox de categoría está marcado, desmarca el checkbox "todos"
            checkboxTodos.checked = false;
        }
    });
});