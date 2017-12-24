package shapes;

/** An class that represents a Platonic Solid. A subclass of class Shape3D.
 * Stores the number of edges for each face, the number of faces at the vertex and the edge length.
 * See the following links:
 *  https://en.wikipedia.org/wiki/Platonic_solid#Radii.2C_area.2C_and_volume
 *  https://en.wikipedia.org/wiki/Regular_polyhedron
 *  http://math.wikia.com/wiki/Platonic_solid
 */
public class PlatonicSolid extends Shape3D {

    private int verticesPerFace;
    private int facesPerVertice;
    private double edgeLength;

    /** Constructor for PlatonicSolid
     *
     * @param verticesPerFace
     *      - the number of vertices meeting at each face
     * @param facesPerVertice
     *      - number of faces meeting at each vertex
     * @param edgeLength
     *      - the length of the edge
     */
     public PlatonicSolid(int verticesPerFace, int facesPerVertice, double edgeLength){
        this.verticesPerFace = verticesPerFace;
        this.facesPerVertice = facesPerVertice;
        this.edgeLength = edgeLength;
    }


    /**
     * Classify which solid it is, based on the number of vertices and faces
     *
     * @param verticesPerFace
     *      - the number of vertices meeting at each face
     * @param facesPerVertice
     *      - number of faces meeting at each vertex
     */
    public String filter(int verticesPerFace, int facesPerVertice){

        if ( verticesPerFace == 3 && facesPerVertice == 3) {
            return "tetrahedron";
        }else if (verticesPerFace == 4 && facesPerVertice == 3) {
            return "cube";
        }else if (verticesPerFace == 3 && facesPerVertice == 4) {
            return "octahedron";
        }else if (verticesPerFace == 5 && facesPerVertice == 3) {
            return "dodecahedron";
        }else if (verticesPerFace == 3 && facesPerVertice == 5) {
            return "icosahedron";
        }else return "Solid is not found in the five catergories.";

    }

    /**
     * PlatonicSolid overrides this method inherited from Shape.
     *
     * @return surface area of the platonic solid
     */
     @Override
    public double area() {

        double res = 0;
        //System.out.println(filter(this.verticesPerFace, this.facesPerVertice));
        switch (filter(this.verticesPerFace, this.facesPerVertice)) {

            case "tetrahedron": {
                res = Math.sqrt(3.0) * Math.pow(this.edgeLength, 2.0);
                break;
            }

            case "cube": {
                res = 6.0 * Math.pow(edgeLength, 2.0);
                break;
            }

            case "octahedron": {
                res = 2.0 * Math.sqrt(3.0) * Math.pow(edgeLength, 2.0);
                break;
            }

            case "dodecahedron": {
                res = 3.0 * Math.sqrt(25 + 10 * Math.sqrt(5.0)) * Math.pow(edgeLength, 2.0);
                break;
            }

            case "icosahedron": {
                res = 5.0 * Math.sqrt(3.0) * Math.pow(edgeLength, 2.0);
                break;
            }
        }


        return res;
    }

    /**
     * PlatonicSolid overrides this method inherited from Shape3D. Returns the volume of this platonic solid.
     *
     * @return volume of the platonic solid
     */
    @Override
    public double volume() {

        double res = 0;

        switch (filter(this.verticesPerFace, this.facesPerVertice)) {

            case "tetrahedron": {
                res = Math.sqrt(2.0)/12.0 * Math.pow(this.edgeLength, 3.0);
                break;
            }

            case "cube": {
                res = Math.pow(edgeLength, 3.0);
                break;
            }

            case "octahedron": {
                res = Math.sqrt(2.0)/3.0 * Math.pow(edgeLength, 3.0);
                break;
            }

            case "dodecahedron": {
                res = (15 + 7 * Math.sqrt(5))/4.0 * Math.pow(edgeLength, 3.0);
                break;
            }

            case "icosahedron": {
                res = 5.0/12 * (3 + Math.sqrt(5.0)) * Math.pow(edgeLength, 3.0);
                break;
            }
        }


        return res;
    }
}
