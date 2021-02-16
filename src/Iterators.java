import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class Iterators {

    public static class AscComparator<I extends Number> implements Comparator<Integer> {
        @Override
        public int compare(Integer x, Integer y) {
            if (x < y) {
                return -1;
            }
            if (x > y) {
                return 1;
            }
            return 0;
        }
    }
    public static class DescComparator<I extends Number>  implements Comparator<Integer> {
        @Override
        public int compare(Integer x, Integer y) {
            if (x > y) {
                return -1;
            }
            if (x < y) {
                return 1;
            }
            return 0;
        }
    }

    public static <E> Iterable<E> skipUntil(Iterable<E> iter, Predicate<E> pred) {
        return new Iterable<>() {
            @Override
            public Iterator<E> iterator() {
                return new Iterator<>() {
                    E curr;
                    boolean found = false;
                    final Iterator<E> it = iter.iterator();

                    @Override
                    public boolean hasNext() {

                        if (curr != null)
                            return true;

                        if(!found) {
                            curr = it.next();
                            while (!pred.test(curr) && !found && it.hasNext()){
                                curr = it.next();
                            }
                            found = true;
                            return true;
                        }

                        if (it.hasNext()) {
                            curr = it.next();
                            return true;
                        }

                        return false;

                    }

                    @Override
                    public E next() {
                        if (!hasNext()) {
                            throw new NoSuchElementException();
                        }
                        E aux = curr;
                        curr = null;
                        return aux;

                    }

                };

            }
        };
    }

    public static <E> Iterable<E> rangeWithStep(Iterable<E> src, int step){

        return new Iterable<E>(){

            @Override
            public Iterator<E> iterator(){
                return new Iterator<E>(){

                    E curr;
                    int count = step;
                    final Iterator<E> it = src.iterator();

                    public boolean hasNext(){
                        if(curr != null)
                            return true;

                        if(it.hasNext()) {

                            if (count == step) {
                                curr = it.next();
                                count = 1;
                                return true;
                            }

                            it.next();
                            count++;
                            return true;

                        }

                        return false;

                    }

                    public E next(){
                        if(!hasNext())
                            throw new NoSuchElementException();

                        E aux = curr;
                        curr = null;
                        return aux;
                    }


                };


            }

        };

    }

    public static Iterable<Integer> intersection(Iterable<Integer> s1, Iterable<Integer> s2) {

        return new Iterable<>() {
            @Override
            public Iterator<Integer> iterator() {
                return new Iterator<>() {
                    Integer curr;
                    final Iterator<Integer> it = s1.iterator();
                    final Iterator<Integer> it2 = s2.iterator();
                    Integer a = null;
                    Integer b = null;

                    @Override
                    public boolean hasNext() {

                        if(curr != null)
                            return true;

                        if (it.hasNext() && a == null) {
                            a = it.next();
                        }

                        if (it2.hasNext() && b == null) {
                            b = it2.next();

                        }

                        while (it.hasNext() || it2.hasNext()) {

                            if (a > b && it2.hasNext()) {
                                b = it2.next();

                            } else if (b > a && it.hasNext()) {
                                a = it.next();
                            }

                            if (a.equals(b)) {
                                curr = a;
                                a = null;
                                b = null;
                                return true;

                            }


                        }

                        return false;

                    }

                    @Override
                    public Integer next() {
                        if (!hasNext())
                            throw new NoSuchElementException();

                        Integer aux = curr;
                        curr = null;
                        return aux;

                    }

                };
            }
        };
    }

    public static Iterable<Integer> lambda(Iterable<Integer> s, Predicate<Integer> pred){

        return new Iterable<Integer>() {
            @Override
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>() {

                    Integer curr;
                    final Iterator<Integer> it = s.iterator();
                    @Override
                    public boolean hasNext() {
                        if(curr != null)
                            return true;

                        while(it.hasNext()){
                            Integer aux =  it.next();
                            if(pred.test(aux)) {
                                curr = aux;
                                return true;
                            }
                        }
                        return false;
                    }

                    @Override
                    public Integer next() {
                        if(!hasNext())
                            throw new NoSuchElementException();
                        Integer aux = curr;
                        curr = null;
                        return aux;
                    }
                };
            }
        };

    }

    public static Iterable<Integer> sequence(Iterable<Integer> s1, Iterable<Integer> s2){
        return new Iterable<>() {
            @Override
            public Iterator<Integer> iterator() {
                return new Iterator<>() {

                    final Iterator<Integer> it = s1.iterator();
                    final Iterator<Integer> it2 = s2.iterator();
                    Integer curr;

                    @Override
                    public boolean hasNext() {

                        if(curr != null)
                            return true;

                        if(it.hasNext()){
                            curr = it.next();
                            return true;
                        }
                        else if (it2.hasNext()) {
                            curr = it2.next();
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public Integer next() {
                        if (!hasNext())
                            throw new NoSuchElementException();
                        Integer aux = curr;
                        curr = null;
                        return aux;
                    }
                };
            }
        };
    }

    public static <T> Iterable<T> accumulateSequence(Iterable<T> src, T init, BiFunction<T,T,T> function){
        return new Iterable<>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<>() {

                    final Iterator<T> it = src.iterator();
                    T curr;

                    @Override
                    public boolean hasNext(){

                        if(curr != null)
                            return true;

                        if(it.hasNext())
                            curr = it.next();
                        else
                            return false;

                        for(int i = 1; i < (Integer) init && it.hasNext(); i++)
                            curr = it.next();


                        while(it.hasNext()){
                            curr = function.apply(curr, it.next());
                        }

                        return true;
                    }

                    @Override
                    public T next() {
                        if (!hasNext())
                            throw new NoSuchElementException();
                        T aux = curr;
                        curr = null;
                        return aux;
                    }
                };
            }
        };
    }

    public static Iterable<Integer> duo(Iterable<Integer> s, int duo){

        return new Iterable<Integer>() {
            @Override
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>() {

                    int count = 0;
                    Integer curr;
                    final Iterator<Integer> it = s.iterator();

                    @Override
                    public boolean hasNext() {
                        if(curr != null)
                            return true;

                        while(it.hasNext()){

                            Integer aux = it.next();

                            if(count++ < duo){
                                curr = aux;
                                return true;
                            }

                            count = 0;

                        }

                        return false;
                    }

                    @Override
                    public Integer next() {
                        if(!hasNext())
                            throw new NoSuchElementException();
                        Integer aux = curr;
                        curr = null;
                        return aux;
                    }
                };
            }
        };

    }

    public static Iterable<Integer> reverse(Iterable<Integer> s) {
        return new Iterable<>() {
            @Override
            public Iterator<Integer> iterator() {
                return new Iterator<>() {

                    final Iterator<Integer> it = s.iterator();
                    final Stack<Integer> stack = new Stack<>();
                    Integer curr;

                    @Override
                    public boolean hasNext() {
                        if(curr !=  null)
                            return true;
                        while(it.hasNext()){
                            stack.push(it.next());
                        }
                        if(stack.isEmpty())
                            return false;
                        curr = stack.pop();
                        return true;
                    }

                    @Override
                    public Integer next() {
                        if(!hasNext())
                            throw new NoSuchElementException();
                        Integer aux = curr;
                        curr = null;
                        return aux;
                    }
                };
            }
        };
    }

    public static Iterable<Integer> inOrder(Iterable<Integer> s, int x){
        return new Iterable<Integer>() {
            @Override
            public Iterator<Integer> iterator() {
                Comparator<Integer> cmp = (x%2 == 0)? new AscComparator<Integer>() : new DescComparator<>();
                return new Iterator<Integer>() {

                    Integer curr;
                    final PriorityQueue<Integer> pqueue = new PriorityQueue<>(cmp);
                    final Iterator<Integer> it = s.iterator();

                    @Override
                    public boolean hasNext() {

                        if(curr != null)
                            return true;

                        while(it.hasNext()){
                            pqueue.add(it.next());
                        }
                        if(pqueue.isEmpty())
                            return false;
                        curr = pqueue.poll();
                        return true;
                    }

                    @Override
                    public Integer next() {
                        if(!hasNext())
                            throw new NoSuchElementException();
                        Integer aux = curr;
                        curr = null;
                        return aux;
                    }
                };
            }
        };

    }

    public static Iterable<Integer> doUntil(Iterable<Integer> iter, Predicate<Integer> pred){

        return new Iterable<Integer>() {
            @Override
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>() {

                    Integer curr;
                    boolean done = true;
                    final Iterator<Integer> it = iter.iterator();

                    @Override
                    public boolean hasNext() {
                        if(curr != null)
                            return true;

                        if(it.hasNext() && done) {
                            curr = it.next();
                            if (pred.test(curr)) {
                                done = false;
                                return false;
                            }
                            return true;
                        }

                        return false;
                    }

                    @Override
                    public Integer next() {
                        if(!hasNext())
                            throw new NoSuchElementException();
                        Integer aux = curr;
                        curr = null;
                        return aux;
                    }
                };
            }
        };

    }

    public static Iterable<Integer> pairValue(Iterable<Integer> s){
        return new Iterable<Integer>() {
            @Override
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>() {

                    Integer curr;
                    final Iterator<Integer> it = s.iterator();

                    @Override
                    public boolean hasNext() {
                        if(curr != null)
                            return true;
                        if(it.hasNext()){
                            curr = it.next();
                            while(curr % 2 != 0 && it.hasNext())
                                curr = it.next();
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public Integer next() {
                        if(!hasNext())
                            throw new NoSuchElementException();
                        Integer aux = curr;
                        curr = null;
                        return aux;
                    }
                };
            }
        };

    }

    public static Iterable<Integer> sum(Iterable<Integer> s){
        return new Iterable<Integer>() {
            @Override
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>() {

                    Integer curr;
                    int sum = 0;
                    final Iterator<Integer> it = s.iterator();

                    @Override
                    public boolean hasNext() {
                        if(curr != null)
                            return true;
                        if(it.hasNext()){
                            curr = it.next();
                            int aux = curr;
                            curr += sum;
                            sum += aux;
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public Integer next() {
                        if(!hasNext())
                            throw new NoSuchElementException();
                        Integer aux = curr;
                        curr = null;
                        return aux;
                    }
                };
            }
        };

    }

    public static Iterable<Integer> duoSum(Iterable<Integer> s, Iterable<Integer> s2){
        return new Iterable<Integer>() {
            @Override
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>() {

                    Integer curr;
                    final Iterator<Integer> it = s.iterator();
                    final Iterator<Integer> it2 = s2.iterator();

                    @Override
                    public boolean hasNext() {

                        if (curr != null)
                            return true;

                        if (it.hasNext() && it2.hasNext()) {
                            curr = it.next() + it2.next();
                            return true;
                        }
                        if (!it2.hasNext() && it.hasNext()) {
                            curr = it.next();
                            return true;
                        }
                        if(it2.hasNext()) {
                            curr = it2.next();
                            return true;
                        }
                        return false;


                    }

                    @Override
                    public Integer next() {
                        if(!hasNext())
                            throw new NoSuchElementException();
                        Integer aux = curr;
                        curr = null;
                        return aux;
                    }
                };
            }
        };

    }

    public static Iterable<Integer> diff(Iterable<Integer> s, Iterable<Integer> s2){
        return new Iterable<Integer>() {
            @Override
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>() {

                    Integer curr;
                    final Iterator<Integer> it = s.iterator();
                    final Iterator<Integer> it2 = s2.iterator();
                    Integer aux;
                    Integer aux2;

                    @Override
                    public boolean hasNext() {
                        if (curr != null)
                            return true;

                        if(!it.hasNext() && !it2.hasNext())
                            return false;

                        if (it.hasNext() && aux == null)
                            aux = it.next();

                        if (it2.hasNext() && aux2 == null)
                            aux2 = it2.next();

                        while (aux.equals(aux2) && (it.hasNext() || it2.hasNext())) {
                            /*
                            PARA FAZER COM QUE FAÇAM A UNIAO (also comentar segunda condiçao do while)
                            curr = aux;
                            aux = null;
                            aux2 = null;
                            return true;

                             */

                            if(it.hasNext() && it2.hasNext()) {

                                aux = it.next();
                                aux2 = it2.next();

                            } else if(it.hasNext()){
                                aux = it.next();
                                aux2 = Integer.MAX_VALUE;
                            } else {
                                aux2 = it2.next();
                                aux = Integer.MAX_VALUE;
                            }


                        }

                        if (aux < aux2) {
                            curr = aux;
                            aux = null;
                            return true;
                        } else if(aux2 < aux){
                            curr = aux2;
                            aux2 = null;
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public Integer next() {
                        if(!hasNext())
                            throw new NoSuchElementException();
                        Integer aux = curr;
                        curr = null;
                        return aux;
                    }

                };
            }
        };
    }

    public static Iterable<Integer> duoSumPos(Iterable<Integer> s, Iterable<Integer> s2){
        return () -> new Iterator<Integer>() {

            Integer curr;
            final Iterator<Integer> it = s.iterator();
            final Iterator<Integer> it2 = s2.iterator();
            Integer aux;
            Integer aux2;

            @Override
            public boolean hasNext() {
                if (curr != null)
                    return true;

                if(!it.hasNext() && !it2.hasNext())
                    return false;

                if (it.hasNext() && aux == null)
                    aux = it.next();

                if (it2.hasNext() && aux2 == null)
                    aux2 = it2.next();

                if(aux.equals(aux2)) {
                        curr = aux + aux2;
                        if(it.hasNext()) {
                            aux = it.next();
                        }
                        if(it2.hasNext())
                            aux2 = it2.next();

                        return true;
                }

                if (aux < aux2) {
                    curr = aux;
                    aux = null;
                    return true;
                } else {
                    curr = aux2;
                    aux2 = null;
                    return true;
                }
            }

            @Override
            public Integer next() {
                if(!hasNext())
                    throw new NoSuchElementException();
                Integer aux = curr;
                curr = null;
                return aux;
            }
        };

    }

    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(2);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(10);
        list.add(15);
        LinkedList<Integer> list2 = new LinkedList<>();
        list2.add(2);
        list2.add(5);
        list2.add(15);

         for(Integer x : duoSumPos(list, list2)) {
            System.out.println(x);
        }
    }

 }


