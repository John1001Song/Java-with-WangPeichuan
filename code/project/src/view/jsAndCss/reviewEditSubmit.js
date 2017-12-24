function myFunction() {

//    var xhttp = new XMLHttpRequest();
    var reviewId = document.forms["editedReview"]["reviewId"].value;
    var hotelId = document.forms["editedReview"]["hotelId"].value;
    var userName = document.forms["editedReview"]["userName"].value;
    var title = document.forms["editedReview"]["title"].value;
    var content = document.forms["editedReview"]["content"].value;
    var rating = document.forms["editedReview"]["rating"].value;
    var isRecomd = document.forms["editedReview"]["isRecomd"].value;

//    xhttp.open("POST", "reviewEdit?reviewId=" + reviewId
//                        + "&hotelId=" + hotelId
//                        + "&userName=" + userName
//                        + "&title=" + title
//                        + "&content=" + content
//                        + "&rating=" + rating
//                        + "&isRecomd=" + isRecomd, true);
//    xhttp.send();
    document.getElementById("editedReview").submit();
    alert("rating value: " + rating);

}
