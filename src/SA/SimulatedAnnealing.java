package SA;

import  java.util.*;

public class SimulatedAnnealing {
    private int niter; //반복횟수
    public ArrayList<Double> hist;
    public double a0; // y = a0*x +1.0373

    public SimulatedAnnealing(int niter) {
        this.niter = niter;
        hist = new ArrayList<>();
    }

    public double solve(Problem p, double t, double c, double lower, double upper) { //t=초기온도, c=냉각률, 해를 선정하기 위한 상한선과 하한선
        Random r = new Random();
        a0 = r.nextDouble() * (upper - lower) + lower;//후보해 a0 선택
        return solve(p, t, c, a0, lower, upper);
    }

    public double solve(Problem p, double t, double c, double a0, double lower, double upper) {
        Random r = new Random();
        double f0 = p.fit(a0);//후보해의 적합도
        hist.add(f0);

        for (int i=0; i<niter; i++) {
            int kt = (int) t;
            for(int j=0; j<kt; j++) {
                double a1 = r.nextDouble() * (upper - lower) + lower;//이웃해 a1 선택
                double f1 = p.fit(a1);//이웃해의 적합도

                if(p.isNeighborBetter(f0, f1)) { //이웃해 적합도 > 후보해 적합도 일 떄
                    a0 = a1;//이웃해 a1을 후보해로 설정
                    f0 = f1;
                    hist.add(f0);
                } else { //후보해 적합도 > 이웃해 적합도 일 때
                    double d = Math.abs(f1 - f0);
                    double p0 = Math.exp(-d/t); //확률 p
                    if(r.nextDouble() < p0) { //0~1 중 선택한 수 < 확률 p이면 이웃해 a1을 후보해로 설정
                        a0 = a1;
                        f0 = f1;
                        hist.add(f0);
                    }
                }
            }
            t *= c; //초기온도에 냉각률을 곱해서 다시 반복 => 반복횟수 niter만큼 반복 후 현재 후보해 a0 리턴
        }
        return a0;
    }
}
