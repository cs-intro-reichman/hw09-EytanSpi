public class Main {
    
    public static void main(String[] args) {
        String word = "committee ";
        List list = new List();
        for (int i = 0; i<word.length(); i++) {
            list.update(word.charAt(i));
        }
        System.out.println(list);
        System.out.println(list.getSize());
        list.remove('i');
        System.out.println(list);
        System.out.println(list.getSize());

        list.remove('c');
        System.out.println(list);
        System.out.println(list.getSize());

        System.out.println(list.indexOf('c'));
        System.out.println(list);
    }

}
