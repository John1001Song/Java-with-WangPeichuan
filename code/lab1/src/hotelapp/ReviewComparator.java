package hotelapp;
import java.util.*;

public class ReviewComparator implements Comparator<Review> {
    @Override
    public int compare(Review r1, Review r2) {
        if (r1.getDate().compareTo(r2.getDate()) == 0) {
            if (r1.getUserName().compareTo(r2.getUserName()) == 0) {
                if (r1.getReviewID().compareTo(r2.getReviewID()) == 0) {
                    return 0;
                }else if (r1.getReviewID().compareTo(r2.getReviewID()) < 0){
                    return -1;
                }else return 1;
            }else if (r1.getUserName().compareTo(r2.getUserName()) < 0) {
                return -1;
            }else return 1;
        }else if (r1.getDate().compareTo(r2.getDate()) < 0) {
            return 1;
        }else return -1;

    }
}
