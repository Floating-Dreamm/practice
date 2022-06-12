import lombok.Data;

/**
 * 链表反转
 */
public class ListInversion {
    public static void main(String[] args) {
        Node node5 = new Node(5, null);
        Node node4 = new Node(4, node5);
        Node node3 = new Node(3, node4);
        Node node2 = new Node(2, node3);
        Node node1 = new Node(1, node2);

//        Node result = normal(node1);
        Node result = inverse(node1);
        System.out.println(result);


    }

    private static Node inverse(Node head) {

        if (head.next == null){
            return head;
        }

        Node newHead = inverse(head.next);
        head.next.next = head;
        head.next = null;

        return newHead;
    }

    private static Node normal(Node head) {
        Node prve = null, next;
        Node curr = head;

        while (curr != null) {
            next = curr.next;
            curr.next = prve;
            prve = curr;
            curr = next;
        }

        return prve;
    }


    @Data
    static
    class Node {
        int val;
        Node next;

        public Node(int val, Node next) {
            this.val = val;
            this.next = next;
        }
    }
}
