package SA;

public class Main {
    public static void main(String[] args) {
        SimulatedAnnealing sa = new SimulatedAnnealing(1000);
        Problem p = new Problem() {
            public double error;
            public double loss;
            //데이터 집합
            double[] x = {5, 10, 15, 20, 30, 40}; //독립변수 x = 용매의 무게
            double[] y = {1.226, 1.498, 1.822, 2.138, 2.662, 2.940}; //종속변수 y = 에탄올의 상대 점성계수

            @Override
            public double fit(double a) {
                double s = 0;
                for (int i = 0; i < x.length; i++) {
                    //에러 = 실제 데이터 값과 함수값의 차이
                    error = (y[i] - (a * x[i] + 1.037));

                    // 전체 에러 값을 제곱해서 더하여
                    // 손실 함수(아래로 볼록한 2차함수 형태)를 생성 = x축 : a0 / y축 : loss값
                    // => 손실 값이 가장 적은 a를 찾는다. 미분해서 0인 지점을 찾아야...  ?
                    loss += (error * error);
                    s = loss / x.length; //그냥.. 평균값을 내주었습니다
                }
                return s;
            }//가장 에러가 적은 a의 값을 찾는다

            @Override
            public boolean isNeighborBetter(double f0, double f1) {
                return f0 < f1;
            }
        };
        double a = sa.solve(p, 100, 0.99, -50, 50);
        System.out.println(a);

    }
}