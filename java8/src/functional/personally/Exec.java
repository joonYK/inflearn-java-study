package functional.personally;

public class Exec {
    public static void main(String[] args) {
        //java8 이전에 사용하던 방식 (익명 내부 클래스)
        RunPrint runPrint = new RunPrint() {
            @Override
            public void doIt() {
                System.out.println("java8 이전에 사용하던 방식");
            }
        };

        //함수형 인터페이스를 인라인으로 표현한 오브젝트
        RunPrint runPrint1 = () -> System.out.println("java8의 람다 표현식");
        RunPrint runPrint2 = () -> {
            System.out.println("람다 표현식");
            System.out.println("여러 줄 사용");
        };

        runPrint.doIt();
        runPrint1.doIt();
        runPrint2.doIt();


        //함수형 프로그래밍은 입력받은 값이 동일한 경우 값이 같아야 한다.
        RunAdd runAdd = number -> number + 10;

        //1을 넣었으면 계속 11이 나와야 함.
        //이것을 보장해줄 수 없는 상황이 발생하거나 그런 여지가 있으면 함수형 프로그래밍이라 하기 힘듬.
        System.out.println(runAdd.doInt(1));
        System.out.println(runAdd.doInt(1));
        System.out.println(runAdd.doInt(1));

        /*
         * 예를 들어, 함수 안에서 함수 바깥에 있는 값에 의존하는 경우 순수한 함수라고 하기 어려움.
         * 순수 함수를 위해선 함수 밖에 있는 값을 참조하거나 변경하면 안 된다.
         * 함수 내부에서 쓰는 값, 전달받은 값으로만 작업해야 한다.
         */
        RunAdd runAdd_notFunctional = new RunAdd() {
            //함수 밖에 선언된 상태값
            //어떤 상태값에 의존함.
            //final로 선언하거나 상태가 변경되지 않도록 사용하면 무방.
            int baseNumber = 10;

            @Override
            public int doInt(int number) {
                baseNumber++;
                return number + baseNumber;
            }
        };

        System.out.println(runAdd_notFunctional.doInt(10));
        System.out.println(runAdd_notFunctional.doInt(10));
        System.out.println(runAdd_notFunctional.doInt(10));

    }
}
