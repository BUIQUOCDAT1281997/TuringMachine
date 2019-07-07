import java.util.HashMap;

public class TapeDataType<E>  {

    private DoublyLinkedListImpl<E> doublyLinkedList;
    private HashMap<Integer,E> hashMap;

    public TapeDataType(){
        doublyLinkedList=new DoublyLinkedListImpl<>();
        hashMap= new HashMap<>();

    }



}
