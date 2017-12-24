function reviewNewSubmit() {

//  <!-- reviews (reviewId, hotelId, userName, title, content, rating, isRecomd) -->
    var hotelId = document.forms["newReview"]["hotelId"].value;
    var hotelName = document.forms["newReview"]["hotelName"].value;
    var userName = document.forms["newReview"]["userName"].value;
    var title = document.forms["newReview"]["title"].value;
    var content = document.forms["newReview"]["content"].value;
    var rating = document.forms["newReview"]["rating"].value;
    var isRecomd = document.forms["newReview"]["isRecomd"].value;

    document.getElementById("newReview").submit();

}
