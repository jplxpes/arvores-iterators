import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class Iterators {

    public static <E> Iterable<E> skipUntil(Iterable<E> iter, Predicate<E> pred) {
        return new Iterable<>() {
            @Override
            public Iterator<E> iterator() {
                return new Iterator<>() {
                    E curr;
                    boolean found = false;
                    Iterator<E> it = iter.iterator();

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
                    Iterator<E> it = src.iterator();

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
                    Iterator<Integer> it = s1.iterator();
                    Iterator<Integer> it2 = s2.iterator();
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

    public static Iterable<Integer> test(Iterable<Integer> s1, Iterable<Integer> s2){
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

    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();

        list.add(1);
        list.add(2);
        list.add(5);
        list.add(7);
        list.add(8);

        for ( Integer x : rangeWithStep(list, 2)) {
            System.out.println(x);

        }
    }

 }


