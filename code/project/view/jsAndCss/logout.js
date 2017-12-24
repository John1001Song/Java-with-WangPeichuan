function logout() {

    var request = new XMLHttpRequest();
//    var cookies = document.cookie.split(";");
//    document.cookie = "name=; expires=Thu, 01 Jan 1970 00:00:00 GMT; login=false";
    request.open("GET", "logout", true);
    request.send();

}
