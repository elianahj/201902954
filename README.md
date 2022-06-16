# Simulated Annealing_201902954한현진
## 모의 담금질 기법
> 높은 온도에서 액체 상태인 물질이 온도가 점차 낮아지면서 결정체로 변하는 과정을 모방한 해 탐색 알고리즘  
> 해 탐색 방식 : 특정한 패턴 없이 이루어지다가 점점 더 규칙적인 방식으로 이루어짐 => **이웃해를 정의하여야 함**
### 모의 담금질 방식
1. 임의의 후보해 a0를 선택
2. 충분히 높은 값의 t를 설정
   - t가 높으면 확률 p도 크게 하여 안 좋은 값으로도 자유롭게 탐색할 수 있도록 함
   - t가 0이 되면 p도 0으로 하여 안 좋은 이웃 해를 선택하지 않도록 함
3. 후보해 a0에 이웃하는 해 중에서 임의로 a1을 선택  
   - 가까운 해를 선택하기 위해 상한선과 하한선을 설정  
4. a0과 a1의 적합도 계산  
4-1. 적합도를 계산했을 때, a1의 적합도가 a0보다 더 좋다면 a1을 후보해로 함  
4-2. 그렇지 않다면, 후보해 a0와 이웃해 a1의 차이 = d 를 계산  
   - d와 확률 p는 반비례 => 가까운 이웃해를 선택하도록 함  
   => 따라서 확률 p = e^(-d/t)
   - 0~1 사이에서 랜덤하게 수를 선택하고, 그 수가 확률 p보다 작다면 a1을 후보해로 함
5. t에 냉각률 0.99를 곱하여 3~4번을 for루프로 반복
6. 위의 과정을 반복횟수 niter만큼 for루프로 반복 후 현재 후보해 a0을 리턴

#### Java 구현
```java
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
```  

### 데이터 집합
> 에탄올의 상대 점성계수를 사용
- 용매의 무게 x의 비율에 따른 에탄올의 점성계수 y  

 ![image](https://user-images.githubusercontent.com/80517119/173869404-538dcbb2-d1ff-4ade-90c3-6ce8f73eaeff.png)  
- 두 변수 x,y의 관계를 설명하는 수학적 모형(1차 회귀식) 가정  

<img src = "https://user-images.githubusercontent.com/80517119/173870361-7871009b-d4cd-44d9-bd26-dd27938f6366.png" width="70%"></img>  

 => 데이터 집합을 통해 1차 회귀식을 **y = 0.0505x + 1.0373**으로 가정  
 
### 모의 담금질
> b를 1로 고정시킨 후(직관적으로 추정 가능), a의 값을 모의 담금질을 통해 추정  
- 실제 데이터 값과 추정한 a의 값으로 ax+1 연산을 한 값의 차이 = 에러
- 에러 값을 제곱해서 더하는 것을 반복하여 전체 에러 값을 계산 => 아래로 볼록한 2차함수의 형태이므로 에러가 최소가 되는 a값을 찾을 수 있습니다  

#### 실행 결과

<img src = "https://user-images.githubusercontent.com/80517119/174146685-70df659c-9306-4ef5-a09e-558578d87c8b.jpg" width="70%"></img>  

=> a0 = 0.0500214
 
### 모의 담금질 결과
1. 사용한 데이터 집합  

 <img src = "https://user-images.githubusercontent.com/80517119/174120022-380d9c6c-7df0-4300-a36f-5a766f839857.JPG" width="40%"></img>  
 
2. 설정한 1차 회귀식  

 <img src = "https://user-images.githubusercontent.com/80517119/174120751-6399fe63-0067-4e9e-8f77-d2b6ba4da537.png" width="50%"></img>   
 
3. 모의 담금질 기법 결과  

 <img src = "https://user-images.githubusercontent.com/80517119/174121006-38e7bd2c-11c0-4790-8ba1-279587ec6385.png" width="50%"></img>   

=> 설정했던 1차 회귀식보다, 데이터 집합에 근소하게 더 가까워진 결과 값을 나타냅니다

 
