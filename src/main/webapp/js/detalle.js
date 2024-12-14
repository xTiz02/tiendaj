$(document).ready(function() {
    $(".compra").click(function () {
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



});


const allHoverImages = document.querySelectorAll('.hover-container div img');
const imgContainer = document.querySelector('.img-container');

window.addEventListener('DOMContentLoaded', () => {
    allHoverImages[0].parentElement.classList.add('active');
});

allHoverImages.forEach((image) => {
    image.addEventListener('mouseover', () =>{
        imgContainer.querySelector('img').src = image.src;
        resetActiveImg();
        image.parentElement.classList.add('active');
    });
});

function resetActiveImg(){
    allHoverImages.forEach((img) => {
        img.parentElement.classList.remove('active');
    });
}