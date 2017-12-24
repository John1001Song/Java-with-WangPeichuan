function getHotelName(hotelId) {
    var xhttp = new XMLHttpRequest();

    document.getElementById("hotelName").innerHTML = this.responseText;

    xhttp.open("GET", "hotels?hotelId=" + hotelId, true);
    xhttp.send();
}