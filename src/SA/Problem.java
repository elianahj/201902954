package SA;

public interface Problem {
    double fit(double a);
    boolean isNeighborBetter(double f0, double f1);
}
