import java.util.*;

public class Arvore {

    static Node root;
    static Node root2;

    static class Node {

        int data;
        Node left;
        Node right;

        public Node(){
        }
        public Node(int k){
            data = k;
        }

    }

    static Node newNode(int data) {
        Node node = new Node();
        node.data = data;
        node.left = null;
        node.right = null;
        return (node);
    }

    public static void createTree(){
        root = newNode(1);
        root.left = newNode(3);
        root.right = newNode(2);
        root.left.left = newNode(5);
    }

    public static void print(Node root) {
        List<List<String>> lines = new ArrayList<List<String>>();
        List<Node> level = new ArrayList<Node>();
        List<Node> next = new ArrayList<Node>();

        level.add(root);
        int nn = 1;
        int widest = 0;
        while (nn != 0) {
            List<String> line = new ArrayList<String>();
            nn = 0;
            for (Node n : level) {
                if (n == null) {
                    line.add(null);
                    next.add(null);
                    next.add(null);
                } else {
                    String aa = Integer.toString(n.data);
                    line.add(aa);
                    if (aa.length() > widest) {
                        widest = aa.length();
                    }
                    next.add(n.left);
                    next.add(n.right);
                    if (n.left != null) nn++;
                    if (n.right != null) nn++;
                }
            }

            if (widest % 2 == 1) widest++;
            lines.add(line);
            List<Node> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {
                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (j < line.size() && line.get(j) != null) c = '└';
                        }
                    }
                    System.out.print(c);

                    // lines and spaces
                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? " " : "─");
                        }
                        System.out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                System.out.println();
            }

            // print line of numbers
            for (int j = 0; j < line.size(); j++) {
                String f = line.get(j);
                if (f == null) f = "";
                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

                // a number
                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();

            perpiece /= 2;
        }
    }

    private static boolean add(Node root, int k) {
        while(root != null){
            if(k < root.data) {
                if (root.left == null) {
                    root.left = new Node(k);
                    return true;
                }
                root = root.left;
            }


            if(k > root.data) {
                if (root.right == null) {
                    root.right = new Node(k);
                    return true;
                }
                root = root.right;
            }
        }
        return false;
    }

    public static int getDiameter(Node root) {
        if (root == null)
            return 0;

        int lheight = height(root.left);
        int rheight = height(root.right);

        int ldiameter = getDiameter(root.left);
        int rdiameter = getDiameter(root.right);

        return Math.max(lheight + rheight + 1,
                Math.max(ldiameter, rdiameter));
    }

    public static <E> int countNonTerminalNodes(Node curr){
        if(curr == null || (curr.right == null && curr.left == null))
            return 0;

        return countNonTerminalNodes(curr.left) + countNonTerminalNodes(curr.right) + 1;
    }

    public static Node mergeTrees(Node root1, Node root2) {

        if(root1 == null && root2 == null)
            return null;

        if(root1 != null && root2 != null){
            root1.data += root2.data;
        }

        if(root1 == null && root2 != null)
            return root2;

        if(root1 != null && root2 == null)
            return root1;

        root1.left =  mergeTrees(root1.left, root2.left);
        root1.right = mergeTrees(root1.right, root2.right);

        return root1;
    }

    public static Node removeLowerThan(Node root, int elem) {

        if(root == null)
            return null;

        root.left = removeLowerThan(root.left, elem);
        root.right = removeLowerThan(root.right, elem);

        if(root.data < elem)
            return root.right;

        return root;
    }

    public static Node lowestCommonAncestor(Node root, Integer n1, Integer n2){
        
        while(root != null){

            if(root.data < n1 && root.data < n2)
                root = root.right;

            else if(root.data > n1 && root.data > n2)
                root = root.left;
            else
                break;

        }

        return root;

    }

    public static Node findNode(Node root, int k) {
        Node curr = root;
        Stack<Node> s = new Stack<>();
        while(curr != null || !s.isEmpty()){
            while (curr !=  null){
                s.push(curr);
                curr = curr.left;
            }

            k--;
            curr = s.pop();

            if(k == 0)
                return curr;
            curr = curr.right;
        }
        return null;
    }

    public static int height(Node root){
        return root == null ? 0 : Math.max(height(root.left), height(root.right)) + 1;
    }

    public static boolean withSum(Node root, int sum){
        return sum(root, sum, 0 ) != null;

    }

    public static Node sum(Node root, int sum, int count){

        if(root == null)
            return null;

        count += root.data;

        if(sum == count && root.left == null && root.right == null)
            return root;

        Node curr = sum(root.left, sum, count);
            if(curr != null)
                return curr;

        curr = sum(root.right, sum, count);
        return curr;

    }

    public static boolean isBalanced(Node root){

        if(root == null)
            return true;

        return Math.abs(height(root.left) - height(root.right)) <= 1 && isBalanced(root.left) && isBalanced(root.right);

    }

    public static Integer kSmallest(Node root, int k){
        return findNode(root, k) != null ? findNode(root, k).data : null;
    }

    public static int countMultiples(Node root, Integer k){

        int count = 0;

        if(root == null)
            return 0;

        if(root.data % k == 0)
            count++;

        count += countMultiples(root.left, k);
        count += countMultiples(root.right, k);

        return count;
    }

    public static boolean checkfor(Node root, int value){
        if(root == null)
            return false;

        if(root.data == value)
            return true;

        return checkfor(root.left, value) || checkfor(root.right, value) ;

    }

    public static boolean isEquals(Node root, int[] ar){

        HashSet<Integer> map = new HashSet<>();
        for (int i = 0; i < ar.length; i++) {
            map.add(ar[i]);
        }
        return helper(root, map);
    }

    private static boolean helper(Node root, HashSet<Integer> map) {
        if(root == null)
            return true;
        if(!map.contains(root.data))
            return false;
        return helper(root.left, map) && helper(root.right, map);
    }

    public static boolean isComplete(Node root){
        return isCompleteAux(root) != -1;
    }

    public static int isCompleteAux(Node root){
        if(root == null) return 0;
        return height(root.left) == height(root.right) ? 0 : -1;
    }

    public static int[] findMode(Node root){

        if (root == null) return new int[0];
        int[] res = new int[100];
        inorder(root, res);
        return res;
    }

    private static void inorder(Node root, int[] list) {
        if (root == null) return;
        int count = list[root.data];
        list[root.data] = ++count;
        inorder(root.left, list);
        inorder(root.right, list);

    }

    public static Node sortedArrayToBST(int[] nums) {

        Node root = null;

        for (int i = nums.length/2; i >= 0; i--) {
            root = insert(root, nums[i]);
        }
        for (int i = nums.length/2 + 1; i < nums.length  ; i++) {
            root = insert(root,nums[i]);
        }

        return root;
    }

    public static Node insert(Node root, int val){

        if(root == null){
            root = new Node(val);
            return root;
        }

        if( root.data >= val){
            root.left = insert(root.left, val);
        }

        else
            root.right = insert(root.right, val);

        return root;
    }

    public static Node arrayToBst(int[] nums, int start, int end){

        if (start > end) {
            return null;
        }
        int mid = (start + end) / 2;
        Node node = new Node(nums[mid]);
        node.left = arrayToBst(nums, start, mid - 1);
        node.right = arrayToBst(nums, mid + 1, end);
        return node;
    }

    public static boolean leafSimilar(Node root1, Node root2) {
        List<Integer> a = getLeafValueSequence(root1);
        List<Integer> b = getLeafValueSequence(root2);
        return a.equals(b);
    }

    private static List<Integer> getLeafValueSequence(Node root) {
        List<Integer> list = new ArrayList<>();
        getLeaves(root, list);
        return list;
    }

    private static void getLeaves(Node root, List<Integer> seq) {
        if(root == null)
            return;
        getLeaves(root.left, seq);
        if(root.left==null && root.right==null)
            seq.add(root.data);
        getLeaves(root.right, seq);
    }

    public boolean isValidBST(Node root) {

        if(root == null)
            return true;

        if((root.right != null && root.right.data <= root.data) || (root.left != null && root.left.data > root.data))
            return false;

        return isValidBST(root.left) && isValidBST(root.right);

    }

    public static Integer upper(Node root, int k){

        if(root == null)
            return null;

        Integer aux;
        if(root.data > k){
            aux = upper(root.left, k);
        } else if(root.data < k){
                aux = upper(root.right, k);
	    }else{
            return k;
        }
        return (aux == null)? root.data : aux;
    }

    public static void main(String[] args) {
        int a[] = {1, 2, 5, 7, 9, 11, 20};
        root = arrayToBst(a, 0 ,  a.length - 1);
        print(root);



    }

}
