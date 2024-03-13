package dtw;

import java.util.Vector;
import javafx.geometry.Point2D;

public class DTW {

    public DTW() {}

    public Matrix D(Vector<Point2D> r, Vector<Point2D> t) {

        Vector<Point2D> R = process(r);
        Vector<Point2D> T = process(t);

        int n = R.size();
        int m = T.size();

        Matrix dtw = new Matrix(n,m,true);

        dtw.couple[0][0] = null;

        for(int i = 1; i<n; i++) {
            dtw.items[i][0] = dtw.items[i-1][0] + c(R.elementAt(i),T.elementAt(0));
            dtw.couple[i][0] = new Couple(i-1,0);
        }

        for(int j = 1; j<m; j++) {
            dtw.items[0][j] = dtw.items[0][j-1] + c(R.elementAt(0),T.elementAt(j));
            dtw.couple[0][j] = new Couple(j-1,0);
        }

        for(int i = 1; i<n; i++)
            //for(int j=Math.max(1,i-500); j<Math.min(m,i+500); j++) {
            for(int j=1; j<m; j++) { 
                double min = Math.min(Math.min(dtw.items[i-1][j],dtw.items[i][j-1]),dtw.items[i-1][j-1]);
                dtw.items[i][j] = c(R.elementAt(i),T.elementAt(j)) + min;
                if (min == dtw.items[i-1][j]) dtw.couple[i][j] = new Couple(i-1,j);
                else if(min == dtw.items[i][j-1]) dtw.couple[i][j] = new Couple(i,j-1);
                else dtw.couple[i][j] = new Couple(i-1,j-1);
            }

        return dtw;

    }

    public static double c(Point2D p1, Point2D p2) {

        return Math.sqrt( 
                    Math.pow((p2.getX() - p1.getX()), 2) 
                    + Math.pow((p2.getY() - p1.getY()), 2)     
                );
    }


    public static Vector<Point2D> process(Vector<Point2D> v) {

        Vector<Point2D> V = new Vector<Point2D>(v);

        double X = 0; double Y = 0;
        double maxX = 0; double maxY = 0;
        double minX = 10000; double minY = 10000;
        int N = V.size();
        for(int i = 0;i<N; i++) {
            double x = V.elementAt(i).getX();
            double y = V.elementAt(i).getY();
            X += x; Y += y;
            if (x > maxX) maxX = x;
            if (y > maxY) maxY = y;
            if (x < minX) minX = x;
            if (y < minY) minY = y;
        }
        X /= N;
        Y /= N;
        Point2D centroid = new Point2D(X,Y);
        centroid = normLinearInterpolation(centroid, minX, maxX, minY, maxY);
        for(int i = 0;i<N; i++) {
            Point2D actual = V.elementAt(i);
            actual = normLinearInterpolation(actual, minX, maxX, minY, maxY);
            V.set( i, new Point2D(actual.getX()-centroid.getX(), actual.getY()-centroid.getY()) );
        }

        return V;
    }

    public static Point2D normLinearInterpolation(Point2D p,double minX,double maxX,double minY, double maxY) {
        double newX = ((p.getX() - minX) / (maxX - minX));
        double newY = ((p.getY() - minY) / (maxY - minY));
        return new Point2D(newX, newY);
    }
    
}
