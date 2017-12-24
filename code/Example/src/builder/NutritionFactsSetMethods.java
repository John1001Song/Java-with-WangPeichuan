package builder;

/** Creating an object without Builder pattern, using default constructor and set methods.
 *  The issues with this approach:
 *  - object might be in inconsistent state and
 *  - it is not possible to make the class immutable.
 *  Builder pattern (see class NutritionFacts) overcomes both of these issues, and
 *  is a better solution.
 *  From "Effective Java" by Joshua Bloch. */
public class NutritionFactsSetMethods {
        // Parameters initialized to default values (if any)
        private int servingSize  = -1; // Required; no default value
        private int servings     = -1; // Required; no default value
        private int calories     = 0;
        private int fat          = 0;
        private int sodium       = 0;
        private int carbohydrate = 0;

        public NutritionFactsSetMethods() { }
        // Setters
        public void setServingSize(int val)  { servingSize = val; }
        public void setServings(int val)     { servings = val; }
        public void setCalories(int val)     { calories = val; }
        public void setFat(int val)          { fat = val; }
        public void setSodium(int val)       { sodium = val; }
        public void setCarbohydrate(int val) { carbohydrate = val; }


    public static void main(String[] args) {
        NutritionFactsSetMethods nf = new NutritionFactsSetMethods();
        nf.setServings(2);
        nf.setServingSize(3);
        nf.setFat(1);

        // Does not have the option of enforcing consistency
        // Can not make a class immutable
    }
}
