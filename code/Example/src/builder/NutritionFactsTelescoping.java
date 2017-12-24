package builder;

/** Creating an object without Builder pattern, using "telescoping" approach
 *  (constructors with different number of parameters - in this example 2, 3, 4,..)
 *  The issue with this approach is that it is hard to read and write client code.
 *  From "Effective Java" by Joshua Bloch. */
public class NutritionFactsTelescoping {
     private final int servingSize;  // (mL)            required
        private final int servings;     // (per container) required
        private final int calories;     //                 optional
        private final int fat;          // (g)             optional
        private final int sodium;       // (mg)            optional
        private final int carbohydrate; // (g)             optional

        public NutritionFactsTelescoping (int servingSize, int servings) {
            this(servingSize, servings, 0);
        }

        public NutritionFactsTelescoping (int servingSize, int servings,
                              int calories) {
            this(servingSize, servings, calories, 0);
        }

        public NutritionFactsTelescoping (int servingSize, int servings,
                              int calories, int fat) {
            this(servingSize, servings, calories, fat, 0);
        }

        public NutritionFactsTelescoping (int servingSize, int servings,
                              int calories, int fat, int sodium) {
            this(servingSize, servings, calories, fat, sodium, 0);
        }

        public NutritionFactsTelescoping (int servingSize, int servings,
                              int calories, int fat, int sodium, int carbohydrate) {
            this.servingSize  = servingSize;
            this.servings     = servings;
            this.calories     = calories;
            this.fat          = fat;
            this.sodium       = sodium;
            this.carbohydrate = carbohydrate;
        }

    public static void main(String[] args) {
        NutritionFactsTelescoping cocaCola =
                new NutritionFactsTelescoping(240, 8, 100, 0, 35, 27);

    }
}
